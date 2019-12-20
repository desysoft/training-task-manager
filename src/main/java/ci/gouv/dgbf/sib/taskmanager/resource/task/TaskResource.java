package ci.gouv.dgbf.sib.taskmanager.resource.task;


import ci.gouv.dgbf.sib.taskmanager.dao.ActivityDao;
import ci.gouv.dgbf.sib.taskmanager.exception.task.TaskExistException;
import ci.gouv.dgbf.sib.taskmanager.dao.TaskDao;
import ci.gouv.dgbf.sib.taskmanager.exception.task.TaskCodeExistException;
import ci.gouv.dgbf.sib.taskmanager.exception.task.TaskNotExistException;
import ci.gouv.dgbf.sib.taskmanager.model.Activity;
import ci.gouv.dgbf.sib.taskmanager.model.Task;
import ci.gouv.dgbf.sib.taskmanager.model.Users;
import ci.gouv.dgbf.sib.taskmanager.resource.exception.TacheNonTrouveException;
import ci.gouv.dgbf.sib.taskmanager.resource.exception.TacheOperationFailedException;
import org.jboss.resteasy.annotations.jaxrs.PathParam;


import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/task")
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class TaskResource {

    @Inject
    TaskDao OTaskDao;

    @Inject
    ActivityDao OActivityDao;

    @GET
    @Path("/find")
    public List<Task> listeDesTaches() {
        List<Task> lesTaches = OTaskDao.listAll();
        if (lesTaches.isEmpty()) throw new TacheNonTrouveException("Aucune tache n'est encore créée");
        return lesTaches;
    }

    @GET
    @Path("/find/{id}")
    public Task trouverTacheParSonId(@PathParam("id") String id) {
        Task task = OTaskDao.findById(id);
        if (task != null)
            return task;
        else throw new WebApplicationException("Tache introuvable", Response.Status.NOT_FOUND);
    }

    @GET
    @Path("/findByCode/{code}")
    public Task trouverTacheParSonCode(@PathParam("code") String code) {
        Task oTask = OTaskDao.findByCode(code);
        if (oTask == null) throw new TaskCodeExistException("Code " + code + " introuvable dans la liste des tâches");
        else return oTask;
    }

    @GET
    @Path("/usertask")
    public List<Task> obtenirLesTachesDeUtilisateur(Users user) {
        return OTaskDao.find("OUser", user).list();
    }

    @POST
    @Path("/add")
    public Task ajouterUneTache(Task Otask) throws WebApplicationException {
        OTaskDao.persist(Otask);
        if (OTaskDao.isPersistent(Otask)) {
            return Otask;
        } else throw new WebApplicationException("Tâche non créée", Response.Status.EXPECTATION_FAILED);
    }


    @PUT
    @Path("/assignate/{id_tache}/activity/{id_activity}")
    public Response assignerActiviteAUneTache(@PathParam("id_tache") Long id, @PathParam("id_activity") Long id_activity) {
        Task oTask = OTaskDao.findById(id);
        if (oTask != null) {
            Activity OActivity = OActivityDao.findById(id_activity);
            if (OActivity != null) {
                if (!OActivityDao.addActivityInTask(oTask, OActivity))
                    throw new WebApplicationException("Assignation d'activité à la tache échoué", Response.Status.EXPECTATION_FAILED);
                else return Response.ok().build();
            }else throw new WebApplicationException("Activité introuvable", Response.Status.NOT_FOUND);
        } else throw new WebApplicationException("Tache introuvable", Response.Status.NOT_FOUND);
    }


    @DELETE
    @Path("/delete/{id_tache}")
    public Response supprimerUneTache(@PathParam("id_tache") Long id_tache){
        try{
            Task oTask = OTaskDao.findById(id_tache);
            if(oTask!=null){
                OTaskDao.delete(oTask);
                return Response.ok().build();
            }else throw new WebApplicationException("Tâche introuvable", Response.Status.NOT_FOUND);
        }catch (Exception e){
            return Response.notModified().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
