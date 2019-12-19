package ci.gouv.dgbf.sib.taskmanager.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "t_user")
public class User extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "t_user_id_seq")
    @SequenceGenerator(name = "t_user_id_seq", sequenceName = "t_user_id_seq", allocationSize = 1)
    public Long id;
    public String nom;
    public String prenom;
    public String login;
    public String pwd;
    public String status;
    public LocalDateTime dt_lastconnection;
    public LocalDateTime dt_created;
    public LocalDateTime dt_updated;
    @OneToMany(cascade={CascadeType.ALL},mappedBy = "OUser")
    public List<Task> lstTasks = new ArrayList<>();


    @Override
    public String toString(){
        return "User{ "+
                "id = "+this.id+
                ", name = "+this.nom+
                " }";
    }

    @PrePersist
    public void setDt_created(){
        this.dt_created = LocalDateTime.now();
    }

    @PreUpdate
    public void setDt_updated(){
        this.dt_updated = LocalDateTime.now();
    }
}
