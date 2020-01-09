package ci.gouv.dgbf.sib.taskmanager.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("activity")
public class VersionActivity extends Version {


    @ManyToOne
    @JoinColumn(name = "id_activity", referencedColumnName = "id")
    public Activity OActivity;
    public String label;
    public String description;
    public LocalDateTime start_date;
    public LocalDateTime end_date;
    public String id_task;

    @Override
    public String toString(){
        return "VersionActivity {"+
                " id = "+this.id+
                ", name = "+this.description+
                " }";
    }


}
