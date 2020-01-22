package ci.gouv.dgbf.sib.taskmanager.dao;

import ci.gouv.dgbf.sib.taskmanager.model.Person;
import ci.gouv.dgbf.sib.taskmanager.model.Project;
import ci.gouv.dgbf.sib.taskmanager.model.ProjectPerson;
import ci.gouv.dgbf.sib.taskmanager.tools.ParametersConfig;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class PersonDao extends AbstractDao implements PanacheRepositoryBase<Person, String> {

    @Inject
    ProjectPersonDao OProjectPersonDao;

    public List<Person> findAllPersons() {
        return find("status <> ?1", ParametersConfig.status_delete).list();
    }

    public List<Person> findAllPersons(String search_value){
        List<Person> lstPerson = new ArrayList<>();
        try {
            lstPerson = find("(firstName LIKE :search_value OR lastName LIKE :search_value OR contact LIKE :search_value) AND status <> :status",
                    Parameters.with("search_value",search_value)
                    .and("status", ParametersConfig.status_delete)).list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return lstPerson;
    }

    public Optional<Person> findByIdCustom(String id){
        return this.find("id = ?1 AND status <> ?2", id, ParametersConfig.status_delete).stream().findFirst();
    }

    public List<Project> findAllPersonProjects(String id_person) {
        List<ProjectPerson> lstProjectPerson =  OProjectPersonDao.findAllProjectPerson("",id_person,"");
        return lstProjectPerson.stream().map(projectPerson -> projectPerson.OProject).collect(Collectors.toList());
    }

    public Person addProject(Person person){
        try {
            this.persist(person);
            return person;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Person updatePerson(Person person){
        try {
            Person oPerson = this.findByIdCustom(person.id).orElse(null);
            if(oPerson==null){
                this.setMessage(ParametersConfig.PROCESS_FAILED);
                this.setDetailMessage(ParametersConfig.personNotFoundMessage);
                return oPerson;
            }
            if (person.firstName != null && !person.firstName.equals(""))
                oPerson.firstName = person.firstName;
            if (person.lastName != null && !person.lastName.equals(""))
                oPerson.lastName = person.lastName;
            if (person.contact != null && !person.contact.equals(""))
                oPerson.contact = person.contact;
            this.persist(oPerson);
            this.setMessage(ParametersConfig.PROCESS_SUCCES);
            this.setDetailMessage(ParametersConfig.SUCCES_UPDATE);
            return oPerson;
        }catch (Exception e){
            this.setMessage(ParametersConfig.PROCESS_FAILED);
            this.setDetailMessage(ParametersConfig.FAILED_UPDATE);
            e.printStackTrace();
        }
        return null;
    }



}
