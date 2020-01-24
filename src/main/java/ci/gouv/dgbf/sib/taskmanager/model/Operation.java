package ci.gouv.dgbf.sib.taskmanager.model;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

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
