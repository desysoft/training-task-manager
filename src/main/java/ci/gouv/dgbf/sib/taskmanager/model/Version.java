package ci.gouv.dgbf.sib.taskmanager.model;


import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "version_type")
public class Version extends AbstractEntity {

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

}
