package ci.gouv.dgbf.sib.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "t_task")
public class Task extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_sequence")
    @SequenceGenerator(name = "task_sequence", sequenceName = "t_task_id_seq", allocationSize = 1, initialValue = 5)
    public Long id;
    public String code;
    public String name;
    public String description;
    public Float nbreestimatehours;
    public String status;
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    @ManyToOne
    public User OUser;
    public LocalDateTime dt_created;
    public LocalDateTime dt_updated;
    @OneToMany(cascade={CascadeType.ALL},mappedBy = "OTask")
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
        this.dt_created = LocalDateTime.now();
    }

    @PreUpdate
    public void setDt_updated(){
        this.dt_updated = LocalDateTime.now();
    }
}
