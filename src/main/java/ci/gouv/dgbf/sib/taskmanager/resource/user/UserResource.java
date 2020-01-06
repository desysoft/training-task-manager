package ci.gouv.dgbf.sib.taskmanager.resource.user;


import ci.gouv.dgbf.sib.taskmanager.dao.TaskDao;
import ci.gouv.dgbf.sib.taskmanager.model.User;
import ci.gouv.dgbf.sib.taskmanager.objectvalue.project.ProjectPersonTasks;
import ci.gouv.dgbf.sib.taskmanager.objectvalue.UserData;
import ci.gouv.dgbf.sib.taskmanager.dao.UserDao;
import ci.gouv.dgbf.sib.taskmanager.exception.user.UserNotExistException;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class UserResource {


    @Inject
    UserDao userDao;

    @Inject
    TaskDao OTaskDao;


    @GET
    @Path("/find")
    public List<User> obtenirListDesUtilisateurs() {
        List<User> lst = userDao.findAllUser();
        if (lst.isEmpty()) throw new WebApplicationException("Aucun utilisateur créé", Response.noContent().build());
        return lst;
    }

    @GET
    @Path("/find/{id}")
    public User trouverUtilisateurParId(@PathParam("id") String id) {
        try {
            return userDao.findByIdCustom(id);
        } catch (UserNotExistException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    @GET
    @Path("/findByLogin/{login}")
    public User trouverUtilisateurParLogin(@PathParam("login") String login) {
        User ouser = userDao.findByLogin(login);
        if (ouser != null) return ouser;
        else throw new WebApplicationException("Ce login est inexistant", Response.Status.NO_CONTENT);
    }

    @GET
    @Path("/search/{value}")
    public List<User> RechercherParMotCle(@PathParam("value") String value) {
        List<User> lst = userDao.findAllUser(value);
        if (lst.size() == 0) throw new WebApplicationException("Aucun utilisateur trouvé", Response.Status.NO_CONTENT);
        else return lst;
    }

    @POST
    @Path("/add/{createdBy}")
    public Response ajouterUtilisateur(User user, @PathParam("createdBy") String createdBy) {
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
    public Response supprimerUtilisateur(User user, @PathParam("updatedBy") String updatedBy) {
        try {
            if (userDao.deleteUser(user, updatedBy))
                return Response.ok().build();
            else return Response.notModified().build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/update/{updatedBy}")
    public Response modifierUnUtilisateur(User user, @PathParam("updatedBy") String updatedBy) {
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
    public User seConnecter(UserData oUserData) {
        try {
            return userDao.doLogin(oUserData.getLogin(), oUserData.getPwd());
        } catch (UserNotExistException e) {
            e.getMessage();
            return null;
        }
    }

    @PUT
    @Path("/assignate/{updatedBy}")
    public Response assignerTachesAUnUtilsateur(ProjectPersonTasks projectPersonTasks, @PathParam("updatedBy") String updatedBy) {
        try {
//            if( OTaskDao.assignateTaskToPerson( projectPersonTasks.getLstTasks().get(0),projectPersonTasks.getOProjectPerson(), updatedBy))
                return Response.ok().build();
//            else return Response.notModified().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/assignatetasks/{updatedBy}")
    public Response assignerDesTachesAUnUtilsateur(ProjectPersonTasks projectPersonTasks, @PathParam("updatedBy") String updatedBy) {
        try {
//           if( OTaskDao.assignateTaskToPerson(projectPersonTasks.getLstTasks().get(0), projectPersonTasks.getOProjectPerson(), updatedBy))
            return Response.ok().build();
//           else return Response.notModified().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("block/{updatedBy}")
    public Response bloquerUtilisateur(User user, @PathParam("updatedBy") String updatedBy) {
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
