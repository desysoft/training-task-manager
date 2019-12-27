package ci.gouv.dgbf.sib.taskmanager.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("project")
public class VersionProject extends Version {


    @ManyToOne
    @JoinColumn(name = "id_project", referencedColumnName = "id")
    public Project OProject;
    public String description;

    @Override
    public String toString(){
        return "VersionProject {"+
                " id = "+this.id+
                ", name = "+this.description+
                " }";
    }


}
