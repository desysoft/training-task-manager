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

    @ManyToOne
    @JoinColumn(name = "id_projectLead", referencedColumnName = "id")
    public Person OPerson;

    @Override
    public String toString(){
        return "Project {"+
                " id = "+this.id+
                ", name = "+this.name+
                " }";
    }
}
