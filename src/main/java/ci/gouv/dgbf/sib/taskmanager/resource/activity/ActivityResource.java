package ci.gouv.dgbf.sib.taskmanager.resource.activity;

import ci.gouv.dgbf.sib.taskmanager.dao.ActivityDao;
import ci.gouv.dgbf.sib.taskmanager.model.Activity;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/activities")
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class ActivityResource {

    @Inject
    ActivityDao OActivityDao;

    @GET
    public List<Activity> listeDesActivites() {
        List<Activity> lesActivites = OActivityDao.findAllActivity();
        return lesActivites;
    }

    @GET
    @Path("{id}")
    public Activity trouverActiviteParSonId(@PathParam("id") String id) {
        return OActivityDao.findByIdCustom(id).get();
    }

    @GET
    @Path("?code={code}")
    public Activity trouverActiviteParSonCode(@PathParam("code") String code) {
        return OActivityDao.findByCode(code).get();
    }

    @GET
    @Path("taskactivite/{task_id}")
    public List<Activity> obtenirLesActivitesDeLaTache(@PathParam("task_id") String id_tache) {
        return OActivityDao.findAllByTask(id_tache);
    }

    @POST
    public Activity ajouterUneActivte(Activity activity) {
        return OActivityDao.addActivity(activity);
    }

    @PUT
    public Activity modifierUneActivite(Activity OActivity) {
        return OActivityDao.updateActivity(OActivity);
    }

    @DELETE
    @Path("{id}")
    public void supprimerUneActivite(@PathParam("id") String id) {
        OActivityDao.deleteActivity(id);
    }
}
