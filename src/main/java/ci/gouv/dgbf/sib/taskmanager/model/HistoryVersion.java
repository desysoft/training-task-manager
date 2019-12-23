package ci.gouv.dgbf.sib.taskmanager.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class History extends PanacheEntity {

    @Id
    public String id;
    public String description;
    public LocalDateTime dt_created;
    public LocalDateTime dt_updated;
    public String status;

}
