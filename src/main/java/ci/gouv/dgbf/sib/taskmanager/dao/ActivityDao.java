package ci.gouv.dgbf.sib.taskmanager.dao;

import ci.gouv.dgbf.sib.taskmanager.model.Activity;
import ci.gouv.dgbf.sib.taskmanager.model.Task;
import ci.gouv.dgbf.sib.taskmanager.tools.ParametersConfig;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ActivityDao implements PanacheRepositoryBase<Activity, String> {

    @Inject
    EntityManager em;

    public Activity findByCode(String code){
        return find("code", code).firstResult();
    }

    public Activity findById(String Id){
        return find("id", Id).firstResult();
    }

    public List<Activity> findAllByTask(String id_tache){
        return streamAll().filter(activity -> activity.OTask.id==id_tache).collect(Collectors.toList());
    }

    public Boolean addActivityInTask(Task OTask, Activity OActivity){
        int count_activity = OTask.lstActivities.size();
        OActivity.OTask = OTask;
        System.out.println("addActivityInTask count before ==== "+count_activity);
        //OTask.lstActivities.add(OActivity);
        this.persist(OActivity);
        System.out.println("addActivityInTask count after ==== "+OTask.lstActivities.size());
        return OTask.lstActivities.size()>count_activity;
    }

    public boolean deleteActivity(Activity activity){
        try {
            activity.status = ParametersConfig.status_delete;
            return this.isPersistent(activity);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteActivity(String id){
        try {
            Activity oActivity = this.findById(id);
            oActivity.status = ParametersConfig.status_delete;
            return this.isPersistent(oActivity);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
