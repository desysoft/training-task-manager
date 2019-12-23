package ci.gouv.dgbf.sib.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
public class Task extends PanacheEntity {

    @Id
    public String id;
    public String code;
    public String name;
    public String description;
    public Float nbreestimatehours;
    public String status;
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    @ManyToOne
    public Users OUser;
    public LocalDateTime dt_created;
    public LocalDateTime dt_updated;
    @OneToMany(mappedBy = "OTask")
    public List<Activity> lstActivities = new ArrayList<>();


    @Override
    public String toString(){
        return "Task {"+
                " id = "+this.id+
                ", name = "+this.name+
                " }";
    }

    @PrePersist
    public void setDtCreated(){
        UUID oUuid = UUID.randomUUID();
        this.id = Long.toString(oUuid.getMostSignificantBits(),94)+'-'+Long.toString(oUuid.getLeastSignificantBits(),94);
        this.dt_created = LocalDateTime.now();
    }

    @PreUpdate
    public void setDt_updated(){
        this.dt_updated = LocalDateTime.now();
    }
}
