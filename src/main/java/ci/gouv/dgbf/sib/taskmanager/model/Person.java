package ci.gouv.dgbf.sib.taskmanager.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.checkerframework.checker.units.qual.A;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Person extends AbstractEntity {

    public String firstName;
    public String lastName;
    public String contact;

    @Override
    public String toString(){
        return "Person {"+
                " id = "+this.id+
                ", Name = "+this.firstName+ " "+this.lastName+
                " }";
    }
}
