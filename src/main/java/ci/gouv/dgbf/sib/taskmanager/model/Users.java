package ci.gouv.dgbf.sib.taskmanager.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import ci.gouv.dgbf.sib.taskmanager.model.Task;


@Entity
public class Users extends Person {

//    @Id
//    public String id;
    public String login;
    public String pwd;
    public String status;
    public LocalDateTime dt_lastconnection;

    @OneToMany(mappedBy = "OUser")
    @Basic(fetch = FetchType.LAZY)
    public List<Task> lstTasks;
    //public List<Task> lstTasks = new ArrayList<>();

    @Override
    public String toString(){
        return "User{ "+
                "id = "+this.id+
                ", name = "+super.firstName + " "+super.lastName+
                " }";
    }

    @PrePersist
    public void initializeEntity(){
        super.initializeEntity();
//        this.id = super.id;
    }

    @PreUpdate
    public void setDt_updated(){
        super.setEntityForUpdate();
    }

//    @PrePersist
//    public void setDt_created(){
//        UUID oUuid = UUID.randomUUID();
//        this.id = Long.toString(oUuid.getMostSignificantBits(),94)+'-'+Long.toString(oUuid.getLeastSignificantBits(),94);
//        this.dt_created = LocalDateTime.now();
//    }

//    @PreUpdate
//    public void setDt_updated(){
//        this.dt_updated = LocalDateTime.now();
//    }
}
