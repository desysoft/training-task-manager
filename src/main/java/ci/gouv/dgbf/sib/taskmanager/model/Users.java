package ci.gouv.dgbf.sib.taskmanager.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
public class Users extends PanacheEntity {

    @Id
    @Basic(optional = false)
    @Column(name = "ID", nullable = false, length = 20)
    public String id;
    @Column(name = "NOM", length = 50)
    public String nom;
    @Column(name = "PRENOM", length = 500)
    public String prenom;
    @Column(name = "LOGIN", length = 50)
    public String login;
    @Column(name = "PWD", length = 50)
    public String pwd;
    @Column(name = "DT_LASTCONNECTION")
    public LocalDateTime dtLastconnection;
    @Column(name = "DT_CREATED")
    public LocalDateTime dtCreated;
    @Column(name = "DT_UPDATED")
    public LocalDateTime dtUpdated;
    @Column(name = "STATUS", length = 20)
    public String status;
    @OneToMany(mappedBy = "idUser")
    public List<Task> taskList = new ArrayList<>();

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
        this.dtCreated = LocalDateTime.now();
    }

    @PreUpdate
    public void setDt_updated(){
        this.dtUpdated = LocalDateTime.now();
    }
}
