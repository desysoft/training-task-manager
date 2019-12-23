package ci.gouv.dgbf.sib.taskmanager.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
public class Users extends PanacheEntity {

    @Id
    public String id;
    public String nom;
    public String prenom;
    public String login;
    public String pwd;
    public String status;
    public LocalDateTime dt_lastconnection;
    public LocalDateTime dt_created;
    public LocalDateTime dt_updated;
    @OneToMany(mappedBy = "OUser")
    @Basic(fetch = FetchType.LAZY)
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
        UUID oUuid = UUID.randomUUID();
        this.id = Long.toString(oUuid.getMostSignificantBits(),94)+'-'+Long.toString(oUuid.getLeastSignificantBits(),94);
        this.dt_created = LocalDateTime.now();
    }

    @PreUpdate
    public void setDt_updated(){
        this.dt_updated = LocalDateTime.now();
    }
}
