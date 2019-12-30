package ci.gouv.dgbf.sib.taskmanager.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.checkerframework.checker.units.qual.A;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "person_type")
public class Person extends AbstractEntity {

    public String firstName;
    public String lastName;
    public String contact;

    @OneToMany(mappedBy = "OPerson", fetch = FetchType.LAZY)
    public List<Project> lstProjects;

    @Override
    public String toString(){
        return "Person {"+
                " id = "+this.id+
                ", Name = "+this.firstName+ " "+this.lastName+
                " }";
    }
}
