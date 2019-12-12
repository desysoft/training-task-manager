package com.dgbf.sib.training.quarkus.web.activity;

import com.dgbf.sib.training.quarkus.dao.ActivityDao;
import com.dgbf.sib.training.quarkus.exception.activity.ActivityCodeExistException;
import com.dgbf.sib.training.quarkus.exception.activity.ActivityNotExistException;
import com.dgbf.sib.training.quarkus.model.Activity;
import com.dgbf.sib.training.quarkus.model.Task;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/activity")
public class ActivityResource {

    @Inject
    ActivityDao OActivityDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/find")
    public List<Activity> listeDesActivites(){
        List<Activity> lesActivites =OActivityDao.findAll();
        return lesActivites;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/find/{id}")
    public Activity trouverActiviteParSonId(@PathParam("id") int id){
        try {
            Activity Activity =  OActivityDao.findById(id);
            return Activity;
        }catch (ActivityNotExistException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/findByCode/{code}")
    public Activity trouverActiviteParSonCode(@PathParam("code") String code){
        Activity oActivity = OActivityDao.findByCode(code);
        if(oActivity==null) throw new ActivityCodeExistException("Code "+code+ " introuvable dans la liste des activités");
        else return oActivity;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/taskactivite/{task_id}")
    public List<Activity> obtenirLesActivitesDeLaTache(@PathParam("task_id") int id_tache){
        return OActivityDao.findAllByTask(id_tache);
    }
}
