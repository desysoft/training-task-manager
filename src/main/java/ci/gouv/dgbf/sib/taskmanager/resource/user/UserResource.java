package ci.gouv.dgbf.sib.taskmanager.resource.user;


import ci.gouv.dgbf.sib.taskmanager.dao.TaskDao;
import ci.gouv.dgbf.sib.taskmanager.exception.user.UserNotCreateException;
import ci.gouv.dgbf.sib.taskmanager.model.Users;
import ci.gouv.dgbf.sib.taskmanager.objectvalue.UserData;
import ci.gouv.dgbf.sib.taskmanager.dao.UserDao;
import ci.gouv.dgbf.sib.taskmanager.exception.user.UserExistException;
import ci.gouv.dgbf.sib.taskmanager.exception.user.UserNotExistException;
import ci.gouv.dgbf.sib.taskmanager.model.Task;
import ci.gouv.dgbf.sib.taskmanager.objectvalue.UserTasks;
import ci.gouv.dgbf.sib.taskmanager.resource.exception.UtilisateursNonTrouveException;
import ci.gouv.dgbf.sib.taskmanager.tools.ParametersConfig;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class UserResource {


    @Inject
    UserDao userDao;


    @GET
    @Path("/find")
    public List<Users> obtenirListDesUtilisateurs() {
        List<Users> lst = userDao.findAllUser();
        if (lst.isEmpty()) throw new WebApplicationException("Aucun utilisateur créé", Response.noContent().build());
        return lst;
    }

    @GET
    @Path("/find/{id}")
    public Users trouverUtilisateurParId(@PathParam("id") String id) {
        try {
            return userDao.findByIdCustom(id);
        } catch (UserNotExistException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    @GET
    @Path("/findByLogin/{login}")
    public Users trouverUtilisateurParLogin(@PathParam("login") String login) {
        Users ouser = userDao.findByLogin(login);
        if (ouser != null) return ouser;
        else throw new WebApplicationException("Ce login est inexistant", Response.Status.NO_CONTENT);
    }

    @GET
    @Path("/search/{value}")
    public List<Users> RechercherParMotCle(@PathParam("value") String value) {
        List<Users> lst = userDao.findAllUser(value);
        if (lst.size() == 0) throw new WebApplicationException("Aucun utilisateur trouvé", Response.Status.NO_CONTENT);
        else return lst;
    }

    @POST
    @Path("/add/{createdBy}")
    public Response ajouterUtilisateur(Users user, @PathParam("createdBy") String createdBy) {
        try {
            if (userDao.addUser(user, createdBy)) {
                URI oUri = UriBuilder.fromPath("/user/find").path("{id}").build(user.id);
                return Response.created(oUri).build();
            } else {
                return Response.noContent().build();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.noContent().build();
        }
    }


    @PUT
    @Path("/delete/{updatedBy}")
    public Response supprimerUtilisateur(Users users, @PathParam("updatedBy") String updatedBy) {
        try {
            if (userDao.deleteUser(users, updatedBy))
                return Response.ok().build();
            else return Response.notModified().build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/update/{updatedBy}")
    public Response modifierUnUtilisateur(Users user, @PathParam("updatedBy") String updatedBy) {
        try {
            if (userDao.updateUser(user, updatedBy)) {
                URI oUri = UriBuilder.fromPath("/user/find").path("{id}").build(user.id);
                return Response.ok().contentLocation(oUri).build();
            } else {
                return Response.notModified().build();
            }
        } catch (UserNotExistException e) {
            System.out.println(e.getMessage());
            return Response.notModified().build();
        }
    }

    @POST
    @Path("/login")
    public Users seConnecter(UserData oUserData) {
        try {
            return userDao.doLogin(oUserData.getLogin(), oUserData.getPwd());
        } catch (UserNotExistException e) {
            e.getMessage();
            return null;
        }
    }

    @PUT
    @Path("/assignate/{updatedBy}")
    public Response assignerTachesAUnUtilsateur(UserTasks userTasks,  @PathParam("updatedBy") String updatedBy) {
        try {
            if( userDao.assignateTaskToUser(userTasks.getOUser(), userTasks.getLstTasks().get(0), updatedBy))
                return Response.ok().build();
            else return Response.notModified().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/assignatetasks/{updatedBy}")
    public Response assignerDesTachesAUnUtilsateur(UserTasks userTasks, @PathParam("updatedBy") String updatedBy) {
        try {
           if( userDao.assignateTasksToUser(userTasks.getOUser(), userTasks.getLstTasks(), updatedBy))
            return Response.ok().build();
           else return Response.notModified().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("block/{updatedBy}")
    public Response bloquerUtilisateur(Users user, @PathParam("updatedBy") String updatedBy) {
        try {
            if (userDao.blockUser(user, updatedBy)) {
                URI oUri = UriBuilder.fromPath("user/find/").path("{id}").build(user.id);
                return Response.ok().contentLocation(oUri).build();
            } else
                return Response.notModified().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }
}
