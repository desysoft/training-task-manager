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
        return OActivityDao.findByIdCustom(id).orElse(null);
    }

    @GET
    @Path("/search/{search_value}")
    public List<Activity> RechercherDesActivies(@PathParam("search_value") String search_value) {
        System.out.println("RechercherDesActivies");
        return OActivityDao.findAllActivity(search_value);
    }

    @GET
    @Path("/code/{code}")
    public Activity trouverActiviteParSonCode(@PathParam("code") String code) {
        return OActivityDao.findByCode(code).orElse(null);
    }

    @POST
    public Activity ajouterUneActivte(Activity activity) {
        return OActivityDao.addActivity(activity);
    }

    @PUT
    public Activity modifierUneActivite(Activity OActivity) {
        Activity oActivity =  OActivityDao.updateActivity(OActivity);
        System.out.println("Resource modifierUneActivite ===== "+OActivityDao.getDetailMessage());
        return oActivity;
    }

    @DELETE
    @Path("{id}")
    public void supprimerUneActivite(@PathParam("id") String id) {
        OActivityDao.deleteActivity(id);
    }
}
