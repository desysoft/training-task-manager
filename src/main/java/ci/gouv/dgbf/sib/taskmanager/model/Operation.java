package ci.gouv.dgbf.sib.taskmanager.model;


import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Operation extends AbstractEntity {

    public String name;
    public String description;

    @OneToMany(mappedBy = "OOperation", fetch = FetchType.LAZY)
    public List<Version> lstVersions = new ArrayList<>();

    @Override
    public String toString(){
        return "Operation {"+
                " id = "+this.id+
                ", name = "+this.name+
                " }";
    }
}
