package ci.gouv.dgbf.sib.taskmanager.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.ejb.Local;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Project extends PanacheEntity {
    @Id
    public String id;
    public String name;
    public String description;
    public LocalDateTime dt_startProject;
    public LocalDateTime dt_endProject;
    public LocalDateTime dt_created;
    public LocalDateTime dt_updated;
    public String status;

    @ManyToOne
    @JoinColumn(name = "id_projectLead", referencedColumnName = "id")
    public Person OPerson;

    @PrePersist
    public void setDtCreated(){
        UUID oUuid = UUID.randomUUID();
        this.id = Long.toString(oUuid.getMostSignificantBits(),94)+'-'+Long.toString(oUuid.getLeastSignificantBits(),94);
        this.dt_created = LocalDateTime.now();
    }

    @PreUpdate
    public void setDt_updated(){
        this.dt_updated = LocalDateTime.now();
    }

    @Override
    public String toString(){
        return "P {"+
                " id = "+this.id+
                ", name = "+this.name+
                " }";
    }
}
