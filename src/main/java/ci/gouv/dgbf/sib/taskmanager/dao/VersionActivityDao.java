package ci.gouv.dgbf.sib.taskmanager.dao;

import ci.gouv.dgbf.sib.taskmanager.model.Operation;
import ci.gouv.dgbf.sib.taskmanager.model.Activity;
import ci.gouv.dgbf.sib.taskmanager.model.VersionActivity;
import ci.gouv.dgbf.sib.taskmanager.tools.ParametersConfig;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class VersionActivityDao extends AbstractDao implements PanacheRepositoryBase<VersionActivity, String> {

    public List<VersionActivity> findAllVersionActivities(){
        return find("status = ?1", ParametersConfig.status_enable).list();
    }

    public Optional<VersionActivity> findByIdCustom(String id){
        return find("id = ?1 AND status = ?2", id, ParametersConfig.status_enable).stream().findFirst();
    }

    public void addVersionActivity(Activity oActivity, Operation oOperation){
        VersionActivity oVersionActivity = new VersionActivity();
        oVersionActivity.OActivity = oActivity;
        oVersionActivity.OOperation = oOperation;
        oVersionActivity.intVersion = oActivity.intVersion;
        oVersionActivity.label = oActivity.label;
        System.out.println("oOperation.name === "+oOperation.name);
        System.out.println("oActivity.label === "+oActivity.label);
        oVersionActivity.description = oOperation.name+" - "+ oActivity.label;
        oVersionActivity.start_date = oActivity.start_date;
        oVersionActivity.end_date = oActivity.end_date;
        oVersionActivity.id_task = (oActivity.OTask==null)?null:oActivity.OTask.id;
        persist(oVersionActivity);
    }
}
