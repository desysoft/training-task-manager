package ci.gouv.dgbf.sib.taskmanager.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("task")
public class VersionTask extends Version {

    @ManyToOne
    @JoinColumn(name = "id_task", referencedColumnName = "id")
    public Task OTask;
    public String description;

    @Override
    public String toString(){
        return "VersionTast {"+
                " id = "+this.id+
                ", name = "+this.description+
                " }";
    }



}
