package ci.gouv.dgbf.sib.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
public class Task extends PanacheEntity {

    @Id
    @Basic(optional = false)
    @Column(name = "ID", nullable = false, length = 20)
    public String id;
    @Column(name = "CODE", length = 20)
    public String code;
    @Column(name = "NAME", length = 500)
    public String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "NBREESTIMATEHOURS", precision = 4, scale = 2)
    public BigDecimal nbreestimatehours;
    @Column(name = "DT_CREATED")
    public LocalDateTime dtCreated;
    @Column(name = "DT_UPDATED")
    public LocalDateTime dtUpdated;
    @Column(name = "STATUS", length = 20)
    public String status;
    @JoinColumn(name = "ID_USER", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    public Users idUser;
    @OneToMany(mappedBy = "idTask")
    public List<Activity> activityList;


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
        this.dtCreated = LocalDateTime.now();
    }

    @PreUpdate
    public void setDt_updated(){
        this.dtUpdated = LocalDateTime.now();
    }
}
