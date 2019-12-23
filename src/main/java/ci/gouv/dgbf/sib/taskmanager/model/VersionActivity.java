package ci.gouv.dgbf.sib.taskmanager.model;

import javax.persistence.*;

@Entity
public class VersionActivity extends HistoryVersion {


    @ManyToOne
    @JoinColumn(name = "id_activity", referencedColumnName = "id")
    public Activity OActivity;
    public String description;

    @Override
    public String toString(){
        return "VersionActivity {"+
                " id = "+this.id+
                ", name = "+this.description+
                " }";
    }
}
