package com.dgbf.sib.training.quarkus.web.user;


import com.dgbf.sib.training.quarkus.dao.UserDao;
import com.dgbf.sib.training.quarkus.exception.user.UserExistException;
import com.dgbf.sib.training.quarkus.exception.user.UserNotCreateException;
import com.dgbf.sib.training.quarkus.exception.user.UserNotExistException;
import com.dgbf.sib.training.quarkus.model.Task;
import com.dgbf.sib.training.quarkus.objectvalue.UserData;
import com.dgbf.sib.training.quarkus.web.exception.UtilisateurOperationFailedException;
import com.dgbf.sib.training.quarkus.web.exception.UtilisateursNonTrouveException;
import com.dgbf.sib.training.quarkus.model.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/user")
public class UserResource {

    @Inject
    UserDao userDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/find")
    public List<User> obtenirListDesUtilisateurs() {
        List<User> lst = userDao.findAll();
        if (lst.isEmpty()) throw new UtilisateursNonTrouveException("Aucun utilisateur créé");
        return lst;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/find/{id}")
    public User trouverUtilisateurParId(@PathParam("id") int id) {
        try {
            return userDao.findById(id);
        } catch (UserNotExistException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/findByLogin/{login}")
    public User trouverUtilisateurParLogin(@PathParam("login") String login){
        User ouser =  userDao.findByLogin(login);
        if(ouser != null) return ouser;
        else throw new UtilisateursNonTrouveException("Ce login est inexistant");
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/add")
    public User ajouterUtilisateur(User user){
        try {
            return userDao.save(user);
        }catch (UserNotCreateException | UserExistException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete")
    public User supprimerUtilisateur(User user){
        try {
            if(!userDao.delete(user)){
                throw new UtilisateurOperationFailedException("Echec dans la suppression de l'utilisateur");
            }else return user;
        }catch (UserNotExistException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public User modifierUnUtilisateur(User user){
        try{
            User oUser = userDao.update(user);
            if(oUser==null){
                throw new UtilisateurOperationFailedException("Echec dans la suppression de l'utilisateur");
            }else return oUser;
        }catch (UserNotExistException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login")
    public User seConnecter(UserData oUserData){
        try{
            return userDao.doLogin(oUserData.getLogin(),oUserData.getPwd());
        }catch (UserNotExistException e){
            e.getMessage();
            return null;
        }
    }

//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("/logger")
//    public User seLogger(String login, String pwd){
//        try{
//            return userDao.doLogin(login,pwd);
//        }catch (UserNotExistException e){
//            e.getMessage();
//            return null;
//        }
//    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/assignate")
    public void assignerDesTachesAUnUtilsateur(List<Task> lstTasks, User user){
        try {
            if(!userDao.assignateTasksToUser(lstTasks, user)) throw new UtilisateurOperationFailedException("Echec dans l'assignation des tâches a cet utilsateur");
        }catch (UserNotExistException e){
            System.out.println(e.getMessage());
        }
    }





}
