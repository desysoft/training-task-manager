package ci.gouv.dgbf.sib.taskmanager.resource.activity;

import ci.gouv.dgbf.sib.taskmanager.dao.ActivityDao;
import ci.gouv.dgbf.sib.taskmanager.exception.activity.ActivityCodeExistException;
import ci.gouv.dgbf.sib.taskmanager.exception.activity.ActivityNotExistException;
import ci.gouv.dgbf.sib.taskmanager.model.Activity;
import ci.gouv.dgbf.sib.taskmanager.model.Task;
import ci.gouv.dgbf.sib.taskmanager.resource.exception.TacheOperationFailedException;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

@Path("/activity")
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class ActivityResource {

    @Inject
    ActivityDao OActivityDao;

    @GET
    @Path("find")
    public List<Activity> listeDesActivites() {
        List<Activity> lesActivites = OActivityDao.listAll();
        return lesActivites;
    }

    @GET
    @Path("find/{id}")
    public Activity trouverActiviteParSonId(@PathParam("id") String id) {
        try {
            Activity oActivity = OActivityDao.findById(id);
            return oActivity;
        } catch (ActivityNotExistException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GET
    @Path("findByCode/{code}")
    public Activity trouverActiviteParSonCode(@PathParam("code") String code) {
        try {
            Activity oActivity = OActivityDao.findByCode(code);
            return oActivity;
        } catch (Exception e) {
            return null;
        }

    }

    @GET
    @Path("taskactivite/{task_id}")
    public List<Activity> obtenirLesActivitesDeLaTache(@PathParam("task_id") String id_tache) {
        return OActivityDao.findAllByTask(id_tache);
    }

    @POST
    @Path("add")
    public Response ajouterUneActivte(Activity OActivity) {
        OActivityDao.persist(OActivity);
        if (OActivityDao.isPersistent(OActivity)) {
            URI oUri = UriBuilder.fromPath("/activity/find").path("{id}").build(OActivity.id);
            return Response.created(oUri).build();
        } else {
            return Response.noContent().build();
        }
    }

    @PUT
    @Path("update/{id_activity}")
    public Response modifierUneActivite(@PathParam("id_activity") String id, Activity OActivity) {
        try {
            Activity oActivity = OActivityDao.findById(id);
            if (oActivity != null) {
                oActivity.code = OActivity.code;
                oActivity.label = OActivity.label;
                oActivity.description = OActivity.description;
                oActivity.start_date = OActivity.start_date;
                oActivity.end_date = OActivity.end_date;
                oActivity.OTask = OActivity.OTask;
                OActivityDao.persist(oActivity);
                URI oUri = UriBuilder.fromPath("activity/find/").path("{id}").build(oActivity.id);
                return Response.created(oUri).build();
            } else throw new WebApplicationException("Activité introuvable", Response.Status.NOT_FOUND);
        } catch (Exception e) {
            throw new WebApplicationException("Echec dans la modification de l'activité", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("delete/{id}")
    public Response supprimerUneActivite(@PathParam("id") String id) {
        try {
            Activity oActivity = OActivityDao.findById(id);
            if (oActivity != null) {
                OActivityDao.delete(oActivity);
                return Response.ok().build();
            } else throw new WebApplicationException("Activité introuvable", Response.Status.NOT_FOUND);
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }


}
