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
public class Task extends AbstractEntity {

    public String code;
    public String name;
    public String description;
    public Float nbreestimatehours;
    public int intVersion;
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    @ManyToOne
    public Users OUser;
    @OneToMany(mappedBy = "OTask")
    public List<Activity> lstActivities = new ArrayList<>();


    @Override
    public String toString() {
        return "Task {" +
                " id = " + super.id +
                ", name = " + this.name +
                " }";
    }

    @PreUpdate
    public void setEntityForUpdate() {
        super.setEntityForUpdate();
        this.intVersion++;
    }

}
