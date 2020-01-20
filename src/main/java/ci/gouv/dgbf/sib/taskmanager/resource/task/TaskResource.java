package ci.gouv.dgbf.sib.taskmanager.resource.task;


import ci.gouv.dgbf.sib.taskmanager.dao.ActivityDao;
import ci.gouv.dgbf.sib.taskmanager.dao.TaskDao;
import ci.gouv.dgbf.sib.taskmanager.model.Activity;
import ci.gouv.dgbf.sib.taskmanager.model.Person;
import ci.gouv.dgbf.sib.taskmanager.model.Task;
import ci.gouv.dgbf.sib.taskmanager.model.User;
import ci.gouv.dgbf.sib.taskmanager.objectvalue.task.TaskActivities;
import ci.gouv.dgbf.sib.taskmanager.resource.exception.TacheNonTrouveException;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class TaskResource {

    @Inject
    TaskDao OTaskDao;

    @GET
    public List<Task> listeDesTaches() {
        System.out.println("listeDesTaches");
        return OTaskDao.findAllTask();
    }

    @POST
    @Path("search/{value}")
    public List<Task> rechercherTacheParMotCle(@PathParam("value") String value) {
        return OTaskDao.findAllTask(value);
    }

    @GET
    @Path("{id}")
    public Task trouverTacheParSonId(@PathParam("id") String id) {
        Task oTask = OTaskDao.findByIdCustom(id).orElse(null);
        if(oTask!=null) oTask.setTaskCompletionRate();
        return oTask;
    }

    @GET
    @Path("code/{code}")
    public Task trouverTacheParSonCode(@PathParam("code") String code) {
        Task oTask =  OTaskDao.findByCode(code).orElse(null);
        if(oTask!=null) oTask.setTaskCompletionRate();
        return oTask;
    }

    @GET
    @Path("/user")
    public List<Task> obtenirLesTachesDeUtilisateur(Person person) {
        return OTaskDao.getPersonTasks(person);
    }

    @GET
    @Path("{id_task}/activities")
    public List<Activity> obtenirLesActivitesDuneTache(@PathParam("id_task") String id){
        return OTaskDao.findAllActivitiesByTask(id);
    }

    @POST
    public Task ajouterUneTache(Task task) {
        return OTaskDao.addTask(task);
    }

    @PUT
    public Task modifierUneTache(Task task) {
        return OTaskDao.updateTask(task);
    }

    @PUT
    @Path("activities")
    public Task ajouterDesActivitesAUneTache(TaskActivities taskActivities) {
        return OTaskDao.addListActivityInTask(taskActivities.getOTask(), taskActivities.getLstActivities());
    }


    @DELETE
    @Path("{id}")
    public void supprimerUneTache(@PathParam("id") String id) {
        OTaskDao.deleteTask(id);
    }
}
