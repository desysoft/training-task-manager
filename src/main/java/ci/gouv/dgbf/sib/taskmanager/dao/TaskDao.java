package ci.gouv.dgbf.sib.taskmanager.dao;

import ci.gouv.dgbf.sib.taskmanager.exception.task.TaskNotExistException;
import ci.gouv.dgbf.sib.taskmanager.model.Activity;
import ci.gouv.dgbf.sib.taskmanager.model.Operation;
import ci.gouv.dgbf.sib.taskmanager.model.Task;
import ci.gouv.dgbf.sib.taskmanager.tools.ParametersConfig;
import ci.gouv.dgbf.sib.taskmanager.exception.operation.OperationNotExistException;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class TaskDao implements PanacheRepositoryBase<Task, String> {

    @Inject
    OperationDao OOperationDao;

    @Inject
    VersionTaskDao OVersionTaskDao;

    public Task findByCode(String code){
        return find("code LIKE :code AND status LIKE :status", Parameters.with("code",code).and("status", ParametersConfig.status_enable)).firstResult();
    }

    public Task findById(String id){
        return find("id = ?1 AND status = ?2", id, ParametersConfig.status_enable ).firstResult();
    }

    public Task updateTask(String id_task, String id_operation, Task OTask) throws TaskNotExistException, OperationNotExistException {
        Task oTask = findById(id_task);
        if (oTask != null) {
            Operation oOperation = OOperationDao.findById(id_operation);
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

    public Boolean addActivityInTask(Task OTask, Activity OActivity){
        int count_activity = OTask.lstActivities.size();
        OActivity.OTask = OTask;
        System.out.println("addActivityInTask count before ==== "+count_activity);
        OTask.lstActivities.add(OActivity);
        this.persist(OTask);
        System.out.println("addActivityInTask count after ==== "+OTask.lstActivities.size());
        return OTask.lstActivities.size()>count_activity;
    }

}
