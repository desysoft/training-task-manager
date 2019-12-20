package ci.gouv.dgbf.sib.taskmanager.dao;

import ci.gouv.dgbf.sib.taskmanager.model.Activity;
import ci.gouv.dgbf.sib.taskmanager.model.Task;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.Optional;

@ApplicationScoped
public class TaskDao implements PanacheRepository<Task> {


    public Task findByCode(String code){
        return find("code", code).firstResult();
    }

    public Task findById(String Id){
        return find("id", Id).firstResult();
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
