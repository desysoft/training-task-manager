package ci.gouv.dgbf.sib.taskmanager.dao;

import ci.gouv.dgbf.sib.taskmanager.model.Activity;
import ci.gouv.dgbf.sib.taskmanager.model.Operation;
import ci.gouv.dgbf.sib.taskmanager.model.Task;
import ci.gouv.dgbf.sib.taskmanager.tools.ParametersConfig;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class ActivityDao extends AbstractDao implements PanacheRepositoryBase<Activity, String> {

    @Inject
    VersionActivityDao OVersionActivityDao;

    @Inject
    OperationDao OOperationDao;

//    @Inject
//    TaskDao OTaskDao;

    public List<Activity> findAllActivity() {
        return find("status <> ?1", ParametersConfig.status_delete).list();
    }

    public Optional<Activity> findByCode(String code) {
        if (code == null || code.equals(""))
            return null;
        return find("code =?1 AND status <> ?2", code, ParametersConfig.status_delete).stream().findFirst();
    }

    public Optional<Activity> findByIdCustom(String id) {
        System.out.println("Activity findByIdCustom === id "+id);
        if (id == null || id.equals(""))
            return null;
        return find("id = ?1 and status <> ?2 ", id, ParametersConfig.status_delete).stream().findFirst();
    }

    public List<Activity> findAllByTask(String id_tache) {
        return this.getEm().createQuery("SELECT t FROM Activity t WHERE t.OTask.id = ?1 AND t.status <> ?2")
                .setParameter(1, id_tache)
                .setParameter(2, ParametersConfig.status_delete)
                .getResultList();
    }

    public Activity addActivity(Activity activity) {
        try {
            if (!this.findByCode(activity.code).isPresent()) {
                activity.status = ParametersConfig.status_enable;
                this.persist(activity);
                this.setMessage(ParametersConfig.PROCESS_SUCCES);
                this.setDetailMessage(ParametersConfig.SUCCES_CREATE);
                return activity;
            } else {
                this.setMessage(ParametersConfig.PROCESS_FAILED);
                this.setDetailMessage(ParametersConfig.codeAlreadyExist);
                return null;
            }
        } catch (Exception e) {
            this.setMessage(ParametersConfig.PROCESS_FAILED);
            this.setDetailMessage(ParametersConfig.FAILED_CREATE);
            e.printStackTrace();
            return null;
        }
    }

    public List<Activity> findAllByStatus(String status) {
        return Activity.list("status", status);
    }


    public Activity addActivityInTask(Task task, Activity activity) {
        try {
            Operation oOperation = OOperationDao.findByIdCustom(ParametersConfig.id_assignateActivityToTaskOperation);
            OVersionActivityDao.addVersionActivity(activity, oOperation);
            activity.OTask = task;
            this.persist(activity);
            this.setMessage(ParametersConfig.PROCESS_SUCCES);
            this.setDetailMessage(ParametersConfig.SUCCES_UPDATE);
            return activity;
        } catch (Exception e) {
            this.setMessage(ParametersConfig.PROCESS_FAILED);
            this.setDetailMessage(ParametersConfig.FAILED_UPDATE);
            e.printStackTrace();
            return null;
        }
    }



    public boolean deleteActivity(Activity activity) {
        try {
            activity.status = ParametersConfig.status_delete;
            return this.isPersistent(activity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean deleteActivity(String id) {
        try {
            if(id==null || id.equals("")){
                this.setMessage(ParametersConfig.PROCESS_FAILED);
                this.setDetailMessage(ParametersConfig.genericParameterNullMessage);
                return false;
            }
            Activity oActivity = this.findByIdCustom(id).orElse(null);
            if(oActivity==null){
                this.setMessage(ParametersConfig.PROCESS_FAILED);
                this.setDetailMessage(ParametersConfig.genericNotFoundMessage);
                return false;
            }
            oActivity.status = ParametersConfig.status_delete;
            this.persist(oActivity);
            this.setMessage(ParametersConfig.PROCESS_SUCCES);
            this.setDetailMessage(ParametersConfig.SUCCES_DELETE);
            return true;
        } catch (Exception e) {
            this.setMessage(ParametersConfig.PROCESS_FAILED);
            this.setDetailMessage(ParametersConfig.FAILED_DELETE);
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public Activity updateActivity(Activity activity) {
        try {
            Activity oActivity = this.findByIdCustom(activity.id).orElse(null);
            if (oActivity == null) {
                this.setMessage(ParametersConfig.PROCESS_FAILED);
                this.setDetailMessage(ParametersConfig.genericNotFoundMessage);
                return oActivity;
            }
            System.out.println("updateActivity +++ oActivity = "+oActivity);
            OVersionActivityDao.addVersionActivity(oActivity, OOperationDao.findByIdCustom(ParametersConfig.id_updateOperation));
            if (oActivity.label != activity.label)
                oActivity.label = activity.label;
            if (oActivity.OTask != activity.OTask)
                oActivity.OTask = activity.OTask;
            if (oActivity.description != activity.description)
                oActivity.description = activity.description;
            if (oActivity.start_date != activity.start_date)
                oActivity.start_date = activity.start_date;
            if (oActivity.end_date != activity.end_date)
                oActivity.end_date = activity.end_date;
            this.persist(oActivity);
            this.setMessage(ParametersConfig.PROCESS_SUCCES);
            this.setDetailMessage(ParametersConfig.SUCCES_UPDATE);
            return oActivity;
        } catch (Exception e) {
            this.setMessage(ParametersConfig.PROCESS_FAILED);
            this.setDetailMessage(ParametersConfig.FAILED_UPDATE);
            e.printStackTrace();
            return null;
        }
    }

    public Boolean updateActivityCompletionRate(Activity activity, float completionReate) {
        try {
            Activity oActivity = this.findByIdCustom(activity.id).orElse(null);
            if (oActivity == null) return false;
            oActivity.activityCompletionRate = completionReate;
            if (completionReate >= 100) oActivity.status = ParametersConfig.status_complete;
            persist(oActivity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
