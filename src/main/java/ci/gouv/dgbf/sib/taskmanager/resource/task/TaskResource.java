package ci.gouv.dgbf.sib.taskmanager.resource.task;


import ci.gouv.dgbf.sib.taskmanager.dao.ActivityDao;
import ci.gouv.dgbf.sib.taskmanager.dao.TaskDao;
import ci.gouv.dgbf.sib.taskmanager.exception.task.TaskCodeExistException;
import ci.gouv.dgbf.sib.taskmanager.model.Activity;
import ci.gouv.dgbf.sib.taskmanager.model.Task;
import ci.gouv.dgbf.sib.taskmanager.model.User;
import ci.gouv.dgbf.sib.taskmanager.objectvalue.task.TaskActivities;
import ci.gouv.dgbf.sib.taskmanager.resource.exception.TacheNonTrouveException;
import org.jboss.logging.annotations.Param;
import org.jboss.resteasy.annotations.jaxrs.PathParam;


import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

@Path("/tasks")
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class TaskResource {

    @Inject
    TaskDao OTaskDao;

    @Inject
    ActivityDao OActivityDao;

    @GET
    public List<Task> listeDesTaches() {
        System.out.println("listeDesTaches");
        List<Task> lesTaches = OTaskDao.findAllTask();
        if (lesTaches.isEmpty()) throw new TacheNonTrouveException("Aucune tache n'est encore créée");
        return lesTaches;
    }

    @POST
    @Path("search/{value}")
    public List<Task> rechercherTacheParMotCle(@PathParam("value") String value) {
        return OTaskDao.findAllTask(value);
    }

    @GET
    @Path("{id}")
    public Task trouverTacheParSonId(@PathParam("id") String id) {
        Task task = OTaskDao.findByIdCustom(id).orElse(null);
        if (task != null)
            return task;
        else throw new WebApplicationException("Tache introuvable", Response.Status.NOT_FOUND);
    }

    @GET
    @Path("/code/{code}")
    public Task trouverTacheParSonCode(@PathParam("code") String code) {
        System.out.println("Resource ++++ trouverTacheParSonCode");
        return OTaskDao.findByCode(code).orElse(null);
//        if (oTask == null) throw new TaskCodeExistException("Code " + code + " introuvable dans la liste des tâches");
//        else return oTask;
    }

    @GET
    @Path("/usertask")
    public List<Task> obtenirLesTachesDeUtilisateur(User user) {
        return OTaskDao.find("OUser", user).list();
    }

    @POST
    public Task ajouterUneTache(Task task) {
        return OTaskDao.addTask(task);
    }

    @PUT
    public Task modifierUneTache(Task task) {
        return OTaskDao.updateTask(task);
    }


//    @PUT
//    @Path("{id_tache}/activity/{id_activity}")
//    public Task ajouterDesActivitesAUneTache(@PathParam("id_tache") String id, @PathParam("id_activity") String id_activity) {
//        return OTaskDao.addActivityInTask(id, id_activity);
//    }

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
