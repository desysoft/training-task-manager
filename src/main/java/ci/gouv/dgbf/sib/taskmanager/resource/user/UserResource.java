package ci.gouv.dgbf.sib.taskmanager.resource.user;


import ci.gouv.dgbf.sib.taskmanager.dao.UserDao;
import ci.gouv.dgbf.sib.taskmanager.model.User;
import ci.gouv.dgbf.sib.taskmanager.objectvalue.UserData;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class UserResource {


    @Inject
    UserDao OuserDao;

    @GET
    public List<User> obtenirListDesUtilisateurs() {
        return OuserDao.findAllUser();
    }

    @GET
    @Path("{id}")
    public User trouverUtilisateurParId(@PathParam("id") String id) {
        return OuserDao.findByIdCustom(id).orElse(null);
    }



    @GET
    @Path("login/{login}")
    public User trouverUtilisateurParLogin(@PathParam("login") String login) {
        return OuserDao.findByLogin(login).orElse(null);
    }

    @GET
    @Path("/search/{value}")
    public List<User> RechercherParMotCle(@PathParam("value") String value) {
        return OuserDao.findAllUser(value);
    }

    @POST
    @Path("{createdBy}")
    public User ajouterUtilisateur(User user, @PathParam("createdBy") String createdBy) {
        return OuserDao.addUser(user, createdBy);
    }




    @PUT
    @Path("{updatedBy}")
    public User modifierUnUtilisateur(User user, @PathParam("updatedBy") String updatedBy) {
        return OuserDao.updateUser(user, updatedBy);
    }

    @POST
    @Path("/login")
    public User seConnecter(UserData oUserData) {
        return OuserDao.doLogin(oUserData.getLogin(), oUserData.getPwd());
    }

    @PUT
    @Path("block/{updatedBy}")
    public User bloquerUtilisateur(User user, @PathParam("updatedBy") String updatedBy) {
        return OuserDao.blockUser(user, updatedBy);
    }

    @DELETE
    @Path("{deletedBy}")
    public Boolean supprimerUtilisateur(User user, @PathParam("deletedBy") String deletedBy) {
        return OuserDao.deleteUser(user, deletedBy);
    }
}
