package ci.gouv.dgbf.sib.taskmanager.dao;

import ci.gouv.dgbf.sib.taskmanager.exception.task.TaskNotExistException;
import ci.gouv.dgbf.sib.taskmanager.model.Activity;
import ci.gouv.dgbf.sib.taskmanager.model.Operation;
import ci.gouv.dgbf.sib.taskmanager.model.Project;
import ci.gouv.dgbf.sib.taskmanager.model.Task;
import ci.gouv.dgbf.sib.taskmanager.tools.ParametersConfig;
import ci.gouv.dgbf.sib.taskmanager.exception.operation.OperationNotExistException;
import com.sun.org.apache.regexp.internal.RE;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
@Transactional
public class TaskDao implements PanacheRepositoryBase<Task, String> {

    @Inject
    EntityManager em;

    @Inject
    OperationDao OOperationDao;

    @Inject
    VersionTaskDao OVersionTaskDao;

    @Inject
    ActivityDao OActivityDao;

    public List<Task> findAllTask() {
        //J'ai utilisé cela a cause de la methode peek du Stream qui me permet d'effectuer un traitement sur le champ TaskCompletionRate
        return find("status = ?1", ParametersConfig.status_enable).list();
    }


    public List<Task> findAllTask(String search_value) {
        return find("(name LIKE :search_value OR description LIKE :search_value OR code LIKE :search_value) AND status = :status", Parameters.with("search_value","%"+ search_value+"%").and("status", ParametersConfig.status_enable)).list();
    }

    public List<Task> findAllByProject(String id_project){
        List<Task> lstTasks =  em.createQuery("SELECT t FROM Task t WHERE t.OProject.id = ?1 AND t.status = ?2")
                .setParameter(1, id_project)
                .setParameter(2, ParametersConfig.status_enable)
                .getResultList();
        for(Task t : lstTasks){
            em.refresh(t);
        }
        return lstTasks;
    }

    public Optional<Task> findByCode(String code) {
        return getTaskWithRateCompleteTask(find("code LIKE :code AND status LIKE :status", Parameters.with("code", code).and("status", ParametersConfig.status_enable)).list());
    }

    public Optional<Task> findByIdCustom(String id) {
        return find("id = ?1 AND status = ?2", id, ParametersConfig.status_enable).stream().findFirst();
    }

    public Task updateTask(String id_task, String id_operation, Task OTask) throws TaskNotExistException, OperationNotExistException {
        Task oTask = findByIdCustom(id_task).orElse(null);
        if (oTask != null) {
            Operation oOperation = OOperationDao.findByIdCustom(id_operation);
            if (oOperation != null) {

                OVersionTaskDao.addVersionTask(oTask, oOperation);
                if (OTask.code != null && !OTask.code.equals(""))
                    oTask.code = OTask.code;
                if (OTask.name != null && !OTask.name.equals(""))
                    oTask.name = OTask.name;
                if (OTask.description != null && !OTask.description.equals(""))
                    oTask.description = OTask.description;
                if (OTask.nbreestimatehours != null && !OTask.nbreestimatehours.equals(""))
                    oTask.nbreestimatehours = OTask.nbreestimatehours;
                if (OTask.OUser != null)
                    oTask.OUser = OTask.OUser;
                this.persist(oTask);
                return oTask;
            } else throw new OperationNotExistException("Operation non existante");

        } else throw new TaskNotExistException("Tâche introuvable");
    }

    public Boolean addActivityInTask(Task OTask, Activity OActivity) {
        int count_activity = OTask.lstActivities.size();
        OActivity.OTask = OTask;
        System.out.println("addActivityInTask count before ==== " + count_activity);
        OTask.lstActivities.add(OActivity);
        this.persist(OTask);
        System.out.println("addActivityInTask count after ==== " + OTask.lstActivities.size());
        return OTask.lstActivities.size() > count_activity;
    }


    public boolean deleteTask(String id) throws TaskNotExistException {
        Task oTask = findByIdCustom(id).orElse(null);
        List<Activity> lstActivities = oTask.lstActivities;
        if (oTask != null) {
            oTask.status = ParametersConfig.status_delete;
            for (Activity a : lstActivities) {
                if (!OActivityDao.deleteActivity(a))
                    return false;
                break;
            }
            return isPersistent(oTask);
        } else return false;
    }

    public boolean deleteTask(Task task) {
        List<Activity> lstActivities = task.lstActivities;
        task.status = ParametersConfig.status_delete;
        for (Activity a : lstActivities) {
            if (!OActivityDao.deleteActivity(a))
                return false;
            break;
        }
        return isPersistent(task);
    }


    public Optional<Task> getTaskWithRateCompleteTask(List<Task> lst){
        return lst.stream().peek(task -> task.setTaskCompletionRate()).findFirst();
    }

    public void addTasksInProjet(List<Task> lstTasks, Project project){
        for(Task t : lstTasks){
            t.OProject  = project;
        }
        this.persist(lstTasks);
    }
}
