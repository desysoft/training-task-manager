package ci.gouv.dgbf.sib.taskmanager.model;

import ci.gouv.dgbf.sib.taskmanager.tools.ParametersConfig;

import javax.persistence.*;
import java.util.List;

@Entity
public class ProjectPerson extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "id_project", referencedColumnName = "id")
    public Project OProject;

    @ManyToOne
    @JoinColumn(name = "id_person", referencedColumnName = "id")
    public Person OPerson;

    public String description;

    @OneToMany(mappedBy = "OProjectPerson")
    public List<Task> lstTasks;

    @PrePersist
    public void initializeEntity() {
        super.initializeEntity();
        this.status = ParametersConfig.status_enable;
        this.description = this.OProject.name + " - " + this.OPerson.lastName+" "+this.OPerson.firstName;
    }

    @Override
    public String toString(){
        return "ProjetPerson { "+
                "id = "+this.id+
                ", description = "+this.description +
                " }";
    }

}
