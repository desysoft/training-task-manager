package ci.gouv.dgbf.sib.taskmanager.dao;

import ci.gouv.dgbf.sib.taskmanager.exception.task.TaskNotExistException;
import ci.gouv.dgbf.sib.taskmanager.model.*;
import ci.gouv.dgbf.sib.taskmanager.tools.ParametersConfig;
import ci.gouv.dgbf.sib.taskmanager.exception.operation.OperationNotExistException;
import com.sun.org.apache.regexp.internal.RE;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import org.omg.CORBA.PUBLIC_MEMBER;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class TaskDao  extends AbstractDao implements PanacheRepositoryBase<Task, String> {

    @Inject
    EntityManager em;

    @Inject
    OperationDao OOperationDao;

    @Inject
    VersionTaskDao OVersionTaskDao;

    @Inject
    ProjectPersonDao OProjectPersonDao;

    @Inject
    ActivityDao OActivityDao;




    public List<Task> findAllTask() {
        return find("status = ?1", ParametersConfig.status_enable).list();
    }


    public List<Task> findAllTask(String search_value) {
        return find("(name LIKE :search_value OR description LIKE :search_value OR code LIKE :search_value) AND status = :status", Parameters.with("search_value","%"+ search_value+"%").and("status", ParametersConfig.status_enable)).list();
    }

    public List<Task> findAllByProject(String id_project){
        List<Task> lstTasks =  em.createQuery("SELECT t FROM Task t WHERE t.OProjectPerson.OProject.id = ?1 AND t.status = ?2")
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

    public Task addTask(Task task){
        try {
            Task oTask = this.findByCode(task.code).orElse(null);
            if(oTask!=null){
                System.out.println("Code existant");
                this.setMessage(ParametersConfig.PROCESS_FAILED);
                this.setDetailMessage(ParametersConfig.FAILED_CREATE);
                return null;
            }
            this.persist(task);
            this.setMessage(ParametersConfig.PROCESS_SUCCES);
            this.setDetailMessage(ParametersConfig.SUCCES_CREATE);
            return task;
        }catch (Exception e){
            this.setMessage(ParametersConfig.PROCESS_SUCCES);
            this.setDetailMessage(ParametersConfig.FAILED_CREATE);
            e.printStackTrace();
            return null;
        }
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
                if (OTask.OProjectPerson != null)
                    oTask.OProjectPerson = OTask.OProjectPerson;
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


    public Boolean assignateTaskToPerson(Task task, ProjectPerson projectPerson, String updatedBy){
        try {
            Task oTask = findByIdCustom(task.id).orElse(null);

            if (oTask != null) {
                Operation oOperation = OOperationDao.findByIdCustom(ParametersConfig.id_assignateTaskOperation);
                ProjectPerson oProjectPerson =OProjectPersonDao.findByIdCustom(projectPerson.id).orElse(null);
                if(oProjectPerson!=null){
                    OVersionTaskDao.addVersionTask(oTask, oOperation);
                    oTask.OProjectPerson = projectPerson;
                    oTask.updatedBy = updatedBy;
                    this.persist(oTask);
                    this.setMessage(ParametersConfig.PROCESS_SUCCES);
                    this.setDetailMessage(ParametersConfig.SUCCES_LINKED);
                    return true;
                }else {
                    this.setMessage(ParametersConfig.PROCESS_FAILED);
                    this.setDetailMessage(ParametersConfig.personNotFoundMessage);
                    return false;
                }
            }else {
                this.setMessage(ParametersConfig.PROCESS_FAILED);
                this.setDetailMessage(ParametersConfig.taskTableNameInApplication+" "+ParametersConfig.genericNotFoundMessage);
                return false;
            }
        }catch (Exception e){
            this.setMessage(ParametersConfig.PROCESS_FAILED);
            this.setDetailMessage(ParametersConfig.FAILED_LINKED_TASK_TO_PERSON);
            e.printStackTrace();
            return false;
        }
    }


    public Boolean assignateListTaskToPerson(List<Task> lstTasks, ProjectPerson projectPerson){
        try {
            Operation oOperation = OOperationDao.findByIdCustom(ParametersConfig.id_assignateTaskOperation);
            ProjectPerson oProjectPerson =OProjectPersonDao.findByIdCustom(projectPerson.id).orElse(null);
            if(oProjectPerson!=null){
                lstTasks.stream().forEach(task -> {
                    Task oTask = findByIdCustom(task.id).orElse(null);
                    if (oTask != null) {
                        OVersionTaskDao.addVersionTask(oTask, oOperation);
                        oTask.OProjectPerson = projectPerson;
                        this.persist(oTask);
                    }
                });
                this.setMessage(ParametersConfig.PROCESS_SUCCES);
                this.setDetailMessage(ParametersConfig.SUCCES_LINKED);
                return true;
            }else {
                this.setMessage(ParametersConfig.PROCESS_FAILED);
                this.setDetailMessage(ParametersConfig.personNotFoundMessage);
                return false;
            }
        }catch (Exception e){
            this.setMessage(ParametersConfig.PROCESS_FAILED);
            this.setDetailMessage(ParametersConfig.FAILED_LINKED_TASK_TO_PERSON);
            e.printStackTrace();
            return false;
        }
    }

    public Optional<Task> getTaskWithRateCompleteTask(List<Task> lst){
        return lst.stream().peek(task -> task.setTaskCompletionRate()).findFirst();
    }

    public VersionTaskDao getOVersionTaskDao() {
        return OVersionTaskDao;
    }

    public OperationDao getOOperationDao() {
        return OOperationDao;
    }
}
