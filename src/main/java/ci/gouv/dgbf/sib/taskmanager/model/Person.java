package ci.gouv.dgbf.sib.taskmanager.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "person_type")
public class Person extends AbstractEntity {

    public String firstName;
    public String lastName;
    public String contact;


    @OneToMany(mappedBy = "OPerson", fetch = FetchType.LAZY)
    public List<Project> lstProjects;

    @OneToMany(mappedBy = "OPerson")
    public List<ProjectPerson> lstProjectPerson;

    @Override
    public String toString(){
        return "Person {"+
                " id = "+this.id+
                ", Name = "+this.firstName+ " "+this.lastName+
                " }";
    }
}
