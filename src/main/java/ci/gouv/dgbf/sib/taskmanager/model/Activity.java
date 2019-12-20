package ci.gouv.dgbf.sib.taskmanager.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Activity extends PanacheEntity {

    @Id
    @Basic(optional = false)
    @Column(name = "ID", nullable = false, length = 20)
    public String id;
    @Column(name = "CODE", length = 20)
    public String code;
    @Column(name = "LABEL", length = 250)
    public String label;
    @Column(name = "DESCRIPTION", length = 500)
    public String description;
    @Column(name = "START_DATE")
    public LocalDateTime startDate;
    @Column(name = "END_DATE")
    public LocalDateTime endDate;
    @Column(name = "DT_CREATED")
    public LocalDateTime dtCreated;
    @Column(name = "DT_UPDATED")
    public LocalDateTime dtUpdated;
    @Column(name = "STATUS", length = 20)
    public String status;
    @JoinColumn(name = "ID_TASK", referencedColumnName = "ID")
    @ManyToOne
    public Task idTask;

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
        this.dtCreated = LocalDateTime.now();
    }

    @PreUpdate
    public void setDt_updated(){
        this.dtUpdated = LocalDateTime.now();
    }
}
