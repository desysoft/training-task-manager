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
public class ActivityDao extends AbstractDao implements PanacheRepositoryBase<Activity, String> {

    @Inject
    EntityManager em;

    public List<Activity> findAllActivity(){
        return find("status <> ?1", ParametersConfig.status_delete).list();
    }

    public Activity findByCode(String code){
        return find("code =?1 AND status <> ?2", code, ParametersConfig.status_delete).firstResult();
    }

    public Activity findByIdCustom(String Id){
        return find("id = ?1 and status <> ?2 ", Id, ParametersConfig.status_delete).firstResult();
    }

    public List<Activity> findAllByTask(String id_tache){
        return em.createQuery("SELECT t FROM Activity t WHERE t.OTask.id = ?1 AND t.status <> ?2")
                .setParameter(1, id_tache)
                .setParameter(2, ParametersConfig.status_delete)
                .getResultList();
    }

    public List<Activity> findAllByStatus(String status){
        return Activity.list("status", status);
    }


    public Boolean addActivityInTask(Task OTask, Activity OActivity){
        try {
            OActivity.OTask = OTask;
            this.persist(OActivity);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
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

    public Boolean updateActivityCompletionRate(Activity activity, float completionReate){
        try {
            Activity oActivity = this.findByIdCustom(activity.id);
            if(oActivity==null) return false;
            oActivity.activityCompletionRate = completionReate;
            if(completionReate>=100) oActivity.status = ParametersConfig.status_complete;
            persist(oActivity);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
