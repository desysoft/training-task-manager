package ci.gouv.dgbf.sib.taskmanager.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import ci.gouv.dgbf.sib.taskmanager.model.Task;


@Entity
@DiscriminatorValue("user")
public class User extends Person {

    @Column(unique = true)
    public String login;
    public String pwd;
    public String status;
    public LocalDateTime dt_lastconnection;

//    @OneToMany(mappedBy = "OUser", fetch = FetchType.LAZY)
//    public List<Task> lstTasks  = new ArrayList<>();

    @Override
    public String toString(){
        return "User{ "+
                "id = "+this.id+
                ", name = "+super.firstName + " "+super.lastName+
                " }";
    }
}
