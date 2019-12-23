package ci.gouv.dgbf.sib.taskmanager.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.ejb.Local;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Project extends AbstractEntity {

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
    public void initializeEntity(){
        super.initializeEntity();
//        this.id = super.id;
    }

    @PreUpdate
    public void setDt_updated(){
        super.setEntityForUpdate();
    }

    @Override
    public String toString(){
        return "Project {"+
                " id = "+this.id+
                ", name = "+this.name+
                " }";
    }
}
