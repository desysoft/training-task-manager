package ci.gouv.dgbf.sib.taskmanager.model;


import javax.persistence.*;

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

}
