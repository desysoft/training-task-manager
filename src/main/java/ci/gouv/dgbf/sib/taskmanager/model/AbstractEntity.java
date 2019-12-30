package ci.gouv.dgbf.sib.taskmanager.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@MappedSuperclass
public abstract class AbstractEntity extends PanacheEntityBase {

    @Id
    public String id;
    public LocalDateTime dt_created;
    public LocalDateTime dt_updated;
    public String createdBy;
    public String updatedBy;
    public String status;



    public String generateEntityId(){
        UUID oUuid = UUID.randomUUID();
        return oUuid.toString().replace("-","").toUpperCase();
    }

    @PrePersist
    public void initializeEntity() {
        this.id = generateEntityId();
        this.dt_created = LocalDateTime.now();
    }

    @PreUpdate
    public void setEntityForUpdate() {
        this.dt_updated = LocalDateTime.now();
    }

}
