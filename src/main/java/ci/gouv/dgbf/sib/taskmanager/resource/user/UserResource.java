package ci.gouv.dgbf.sib.taskmanager.resource.user;


import ci.gouv.dgbf.sib.taskmanager.dao.TaskDao;
import ci.gouv.dgbf.sib.taskmanager.exception.user.UserNotCreateException;
import ci.gouv.dgbf.sib.taskmanager.model.Users;
import ci.gouv.dgbf.sib.taskmanager.objectvalue.UserData;
import ci.gouv.dgbf.sib.taskmanager.dao.UserDao;
import ci.gouv.dgbf.sib.taskmanager.exception.user.UserExistException;
import ci.gouv.dgbf.sib.taskmanager.exception.user.UserNotExistException;
import ci.gouv.dgbf.sib.taskmanager.model.Task;
import ci.gouv.dgbf.sib.taskmanager.resource.exception.UtilisateursNonTrouveException;
import ci.gouv.dgbf.sib.taskmanager.tools.ParametersConfig;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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

    @Inject
    TaskDao OTaskDao;

    @GET
    @Path("/find")
    public List<Users> obtenirListDesUtilisateurs() {
        List<Users> lst = userDao.findAllUser();
        if (lst.isEmpty()) throw new UtilisateursNonTrouveException("Aucun utilisateur créé");
        return lst;
    }

    @GET
    @Path("/find/{id}")
    public Users trouverUtilisateurParId(@PathParam("id") String id) {
        try {
            return userDao.findById(id);
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
    @Path("/add")
    public Response ajouterUtilisateur(Users user) {
        try {
            user.status = "enable";
            userDao.persist(user);
            if (userDao.isPersistent(user)) {
                URI oUri = UriBuilder.fromPath("/user/find").path("{id}").build(user.id);
                return Response.created(oUri).build();
            } else {
                return Response.noContent().build();
            }
        } catch (UserNotCreateException | UserExistException e) {
            System.out.println(e.getMessage());
            return Response.noContent().build();
        }
    }


    @PUT
    @Path("/delete/{id_user}")
    public Response supprimerUtilisateur(@PathParam("id_user") String id_user) {
        try {
            Users oUser = userDao.findById(id_user);
            if (oUser != null) {
                oUser.status = ParametersConfig.status_delete;
                userDao.persist(oUser);
                return Response.ok().build();
            } else throw new WebApplicationException("Utilisateur introuvable", Response.Status.NO_CONTENT);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.notModified().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/update/{id_user}")
    public Response modifierUnUtilisateur(@PathParam("id_user") String id, Users user) {
        try {
            Users oUser = userDao.findById(id);
            if (oUser != null) {
                oUser.login = user.login;
                oUser.firstName = user.firstName;
                oUser.lastName = user.lastName;
                oUser.pwd = user.pwd;
                userDao.persist(oUser);
                URI oUri = UriBuilder.fromPath("/user/find").path("{id}").build(user.id);
                return Response.ok().contentLocation(oUri).build();
            } else {
                throw new WebApplicationException("Utilisateur introuvable", Response.Status.NO_CONTENT);
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
    @Path("/assignate/{id_user}")
    public Response assignerDesTachesAUnUtilsateur(@PathParam("id_user") String id_user, List<Task> lstTasks) {
        try {
            Users oUser = userDao.findById(id_user);
            for (Task oTask : lstTasks) {
                Task oneTask = OTaskDao.findById(oTask.id);
                if (oneTask.OUser == null || oneTask.OUser.id != oUser.id) {
                    oneTask.OUser = oUser;
                    OTaskDao.persist(oneTask);
                }
            }
            return Response.ok().build();
        } catch (UserNotExistException e) {
            return Response.notModified().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
