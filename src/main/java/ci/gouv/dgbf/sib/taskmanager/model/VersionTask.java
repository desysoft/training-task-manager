package ci.gouv.dgbf.sib.taskmanager.model;

import javax.persistence.*;

@Entity
@DiscriminatorValue("task")
public class VersionTask extends Version {


    public String name;
    public String description;
    public Float nbreestimatehours;
    public String id_ProjectPerson;
    public String p_key_project_id;
    @ManyToOne
    @JoinColumn(name = "id_task", referencedColumnName = "id")
    public Task OTask;

    @Override
    public String toString(){
        return "VersionTast {"+
                " id = "+this.id+
                ", name = "+this.description+
                " }";
    }



}
