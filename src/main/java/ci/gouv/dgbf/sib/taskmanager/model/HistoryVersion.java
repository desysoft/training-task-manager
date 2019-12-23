package ci.gouv.dgbf.sib.taskmanager.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class HistoryVersion extends AbstractEntity {

    public String description;
    public int intVersion;
    @ManyToOne
    @JoinColumn(name = "id_operation", referencedColumnName = "id")
    public Operation OOperation;

    @Override
    public String toString(){
        return "Operation {"+
                " id = "+this.id+
                ", name = "+this.description+
                " }";
    }


    @PrePersist
    public void initializeEntity(){
        super.initializeEntity();
        this.intVersion = 1;
    }

    @PreUpdate
    public void setDt_updated(){
        super.setEntityForUpdate();
        this.intVersion++;
    }

}
