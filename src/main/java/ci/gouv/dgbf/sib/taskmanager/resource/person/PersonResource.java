package ci.gouv.dgbf.sib.taskmanager.resource.person;


import ci.gouv.dgbf.sib.taskmanager.dao.PersonDao;
import ci.gouv.dgbf.sib.taskmanager.model.Person;
import ci.gouv.dgbf.sib.taskmanager.model.Project;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("persons")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

    @Inject
    PersonDao OPersonDao;

    @GET
    public List<Person> listeDesPersonnes(){
        return OPersonDao.findAllPersons();
    }

    @GET
    @Path("{id}")
    public Person trouverUnePersonneParSonId(@PathParam("id") String id){
        return OPersonDao.findByIdCustom(id).orElse(null);
    }

    @GET
    @Path("search/{search_value}")
    public List<Person> rechercherDesPersonnes(@PathParam("search_value") String search_value){
        return OPersonDao.findAllPersons(search_value);
    }

    @GET
    @Path("{id_person}/projects")
    public List<Project> obtenirLesProjetsDSurLequelTravailleUnePersonne(@PathParam("id_person") String id_person) {
        return OPersonDao.findAllPersonProjects(id_person);
    }

    @POST
    public Person ajouterUnPersonne(Person person){
        return OPersonDao.addProject(person);
    }

    @PUT
    public Person modifierUnePersonne(Person person){
        return OPersonDao.updatePerson(person);
    }


}
