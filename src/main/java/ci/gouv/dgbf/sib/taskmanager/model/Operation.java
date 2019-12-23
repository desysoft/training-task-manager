package ci.gouv.dgbf.sib.taskmanager.model;


import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Operation extends AbstractEntity {

    public String name;
    public String description;

    @Override
    public String toString(){
        return "Operation {"+
                " id = "+this.id+
                ", name = "+this.name+
                " }";
    }

    @PrePersist
    public void initializeEntity(){
        super.initializeEntity();
        this.id = super.id;
    }

    @PreUpdate
    public void setDt_updated(){
        super.setEntityForUpdate();
    }
}
