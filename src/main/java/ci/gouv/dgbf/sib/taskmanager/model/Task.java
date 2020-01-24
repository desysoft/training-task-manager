package ci.gouv.dgbf.sib.taskmanager.model;

import ci.gouv.dgbf.sib.taskmanager.tools.ParametersConfig;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Entity
public class Task extends AbstractEntity {

    public String code;
    public String name;
    public String description;
    public Float nbreestimatehours;
    public int intVersion;
    @ManyToOne
    @JoinColumn(name = "id_projectPerson", referencedColumnName = "id")
    public ProjectPerson OProjectPerson;

    @OneToMany(mappedBy = "OTask")
    public List<Activity> lstActivities = new ArrayList<>();

    @OneToMany(mappedBy = "OTask")
    public List<VersionTask> lstVersionTasks  = new ArrayList<>();

    public String p_key_project_id;

    @Transient
    public float count_activity;

    @Transient
    public float taskCompletionRate=0;

    @Override
    public String toString() {
        return "Task {" +
                " id = " + super.id +
                ", name = " + this.name +
                " }";
    }



    @PrePersist
    public void initializeEntity() {
        super.initializeEntity();
        this.status = ParametersConfig.status_enable;
    }

    public void setTaskCompletionRate(){
        List<Activity> lstActivities = Activity.list("OTask.id = ?1 AND status <> ?2", this.id,ParametersConfig.status_delete);
        count_activity = lstActivities.size();
        if(count_activity>0){
            List<Activity> lst = lstActivities.stream().filter(activity -> activity.status.equals(ParametersConfig.status_complete)).collect(Collectors.toList());
            taskCompletionRate = lst.size() * 100 / count_activity;
        }
    }

    @PreUpdate
    public void setEntityForUpdate() {
        super.setEntityForUpdate();
        this.intVersion++;
    }
}
