package com.dgbf.sib.training.quarkus.web.task;


import com.dgbf.sib.training.quarkus.dao.TaskDao;
import com.dgbf.sib.training.quarkus.exception.task.TaskCodeExistException;
import com.dgbf.sib.training.quarkus.exception.task.TaskExistException;
import com.dgbf.sib.training.quarkus.exception.task.TaskNotExistException;
import com.dgbf.sib.training.quarkus.model.Activity;
import com.dgbf.sib.training.quarkus.model.Task;
import com.dgbf.sib.training.quarkus.model.User;
import com.dgbf.sib.training.quarkus.web.exception.TacheNonTrouveException;
import com.dgbf.sib.training.quarkus.web.exception.TacheOperationFailedException;
import org.jboss.resteasy.annotations.jaxrs.PathParam;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/task")
public class TaskResource {

    @Inject
    TaskDao OTaskDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/find")
    public List<Task> listeDesTaches(){
        List<Task> lesTaches =OTaskDao.findAll();
        if(lesTaches.isEmpty()) throw new TacheNonTrouveException("Aucune tache n'est encore créée");
        return lesTaches;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/find/{id}")
    public Task trouverTacheParSonId(@PathParam("id") int id){
        try {
            Task task =  OTaskDao.findById(id);
            return task;
        }catch (TaskNotExistException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/findByCode/{code}")
    public Task trouverTacheParSonCode(@PathParam("code") String code){
        Task oTask = OTaskDao.findByCode(code);
        if(oTask==null) throw new TaskCodeExistException("Code "+code+ " introuvable dans la liste des tâches");
        else return oTask;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/usertask")
    public List<Task> obtenirLesTachesDeUtilisateur(User user){
        return OTaskDao.findAllByUser(user);
    }

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Task ajouterUneTache(Task Otask){
        try {
            return OTaskDao.save(Otask);
        } catch (TaskCodeExistException | TaskExistException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @PUT
    @Path("/assignate/{id_tache}")
    public void assignerActiviteAUneTache(@PathParam("id_tache") int id, Activity oActivity){
        Task oTask = OTaskDao.findById(id);
        if(oTask!=null){
            if(!OTaskDao.addActivityInTask(oTask, oActivity))
                throw new TacheOperationFailedException("Assignation d'activité à la tache échoué");
        }
    }





}
