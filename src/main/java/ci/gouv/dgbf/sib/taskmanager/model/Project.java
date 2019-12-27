package ci.gouv.dgbf.sib.taskmanager.model;

import ci.gouv.dgbf.sib.taskmanager.tools.ParametersConfig;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.ejb.Local;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Project extends AbstractEntity {

    public String name;
    public String description;
    public LocalDateTime dt_startProject;
    public LocalDateTime dt_endProject;
    public int intVersion;
    @ManyToOne
    @JoinColumn(name = "id_projectLead", referencedColumnName = "id")
    public Person OPerson;

    @OneToMany(mappedBy = "OProject", fetch = FetchType.EAGER)
    public List<Task> lstTasks = new ArrayList<>();

    @Override
    public String toString(){
        return "Project {"+
                " id = "+this.id+
                ", name = "+this.name+
                " }";
    }

    @PrePersist
    public void initializeEntity() {
        super.initializeEntity();
        this.status = ParametersConfig.status_enable;
    }

    @PreUpdate
    public void setEntityForUpdate() {
        super.setEntityForUpdate();
        this.intVersion++;
    }
}
