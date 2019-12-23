package ci.gouv.dgbf.sib.taskmanager.model;

import javax.persistence.*;

@Entity
public class VersionTask extends HistoryVersion {


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
