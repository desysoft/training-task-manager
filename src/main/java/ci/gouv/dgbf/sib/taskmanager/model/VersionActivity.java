package ci.gouv.dgbf.sib.taskmanager.model;

import javax.persistence.*;

@Entity
public class VersionActivity extends HistoryVersion {
//    @Id
//    public String id;
    @ManyToOne
    @JoinColumn(name = "id_activity", referencedColumnName = "id")
    public Activity OActivity;
    public String description;

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
        return "VersionActivity {"+
                " id = "+this.id+
                ", name = "+this.description+
                " }";
    }
}
