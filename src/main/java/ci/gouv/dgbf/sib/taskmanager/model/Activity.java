package ci.gouv.dgbf.sib.taskmanager.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_activity")
public class Activity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "t_activity_id_seq")
    @SequenceGenerator(name = "t_activity_id_seq", sequenceName = "t_activity_id_seq", allocationSize = 1)
    public Long id;
    public String code;
    public String label;
    public String description;
    public LocalDateTime start_date;
    public LocalDateTime end_date;
    public String status;
    public LocalDateTime dt_created;
    public LocalDateTime dt_updated;

    @ManyToOne
    @JoinColumn(name = "id_task", referencedColumnName = "id")
    public Task OTask;

    @Override
    public String toString(){
        return "Activity{"+
                ", id="+this.id+
                ", label="+this.label+
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
