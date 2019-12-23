package ci.gouv.dgbf.sib.taskmanager.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Activity extends PanacheEntityBase {

    @Id
    public String id;
    public String code;
    public String label;
    public String description;
    public LocalDateTime start_date;
    public LocalDateTime end_date;
    public String status;
    public LocalDateTime dt_created;
    public LocalDateTime dt_updated;
    @ManyToOne
    @JoinColumn(name = "id_task", referencedColumnName = "id")
    public Task OTask;

    @Override
    public String toString(){
        return "Activity{"+
                ", id="+this.id+
                ", label="+this.label+
                " }";
    }

    @PrePersist
    public void setDt_created(){
        UUID oUuid = UUID.randomUUID();
        this.id = Long.toString(oUuid.getMostSignificantBits(),94)+'-'+Long.toString(oUuid.getLeastSignificantBits(),94);
        this.dt_created = LocalDateTime.now();
    }

    @PreUpdate
    public void setDt_updated(){
        this.dt_updated = LocalDateTime.now();
    }
}
