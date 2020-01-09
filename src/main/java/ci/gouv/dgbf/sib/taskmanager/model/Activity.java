package ci.gouv.dgbf.sib.taskmanager.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Activity extends AbstractEntity {

    public String code;
    public String label;
    public String description;
    public LocalDateTime start_date;
    public LocalDateTime end_date;
    public int intVersion;
    @ManyToOne
    @JoinColumn(name = "id_task", referencedColumnName = "id")
    public Task OTask;

    @OneToMany(mappedBy = "OActivity")
    public List<VersionActivity> lstVersionActivities = new ArrayList<>();

    public float activityCompletionRate;

    @Override
    public String toString(){
        return "Activity{"+
                ", id="+this.id+
                ", label="+this.label+
                ", status="+this.status+
                " }";
    }

    @PreUpdate
    public void setEntityForUpdate() {
        super.setEntityForUpdate();
        this.intVersion++;
    }
}
