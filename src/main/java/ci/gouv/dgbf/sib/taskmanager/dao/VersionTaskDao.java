package ci.gouv.dgbf.sib.taskmanager.dao;

import ci.gouv.dgbf.sib.taskmanager.model.Operation;
import ci.gouv.dgbf.sib.taskmanager.model.Task;
import ci.gouv.dgbf.sib.taskmanager.model.VersionTask;
import ci.gouv.dgbf.sib.taskmanager.tools.ParametersConfig;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class VersionTaskDao  extends AbstractDao implements PanacheRepositoryBase<VersionTask, String> {

    public List<VersionTask> findAllVersionTasks(){
        return find("status = ?1", ParametersConfig.status_enable).list();
    }

    public VersionTask findByIdCustom(String id){
        return find("id = ?1 AND status = ?2", id, ParametersConfig.status_enable).firstResult();
    }

    public List<VersionTask> findAllByTask(String id_task){
        return find("OTask.id = ?1 AND status = ?2", id_task, ParametersConfig.status_enable).list();
    }

    public void addVersionTask(Task oTask, Operation oOperation){
        VersionTask oVersionTask = new VersionTask();
        oVersionTask.OTask = oTask;
        oVersionTask.OOperation = oOperation;
        oVersionTask.intVersion = oTask.intVersion;
        oVersionTask.name = oTask.name;
        oVersionTask.description = oOperation.name+" - "+ oTask.name;
        oVersionTask.nbreestimatehours = oTask.nbreestimatehours;
        oVersionTask.id_ProjectPerson = (oTask.OProjectPerson==null)?null:oTask.OProjectPerson.id;
        oVersionTask.p_key_project_id = oTask.p_key_project_id;
        oVersionTask.id_Person = (oTask.OProjectPerson==null)?null:oTask.OProjectPerson.id;
        persist(oVersionTask);
    }
}
