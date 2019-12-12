package com.dgbf.sib.training.quarkus.dao.impl;

import com.dgbf.sib.training.quarkus.model.Task;
import com.dgbf.sib.training.quarkus.dao.UserDao;
import com.dgbf.sib.training.quarkus.exception.user.UserExistException;
import com.dgbf.sib.training.quarkus.exception.user.UserNotCreateException;
import com.dgbf.sib.training.quarkus.exception.user.UserNotExistException;
import com.dgbf.sib.training.quarkus.model.User;
import com.sun.media.jfxmedia.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class UserDaoImpl implements UserDao {

    public static List<User> users = new ArrayList<>();

    static {
        users.add(new User(1, "Labité", "Désiré", "user", "****"));
        users.add(new User(2, "Digbeu", "Aboulaye", "user1", "****"));
        users.add(new User(3, "Kouadio", "Félix", "user2", "****"));
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public User findById(int id) throws UserNotExistException {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        throw new UserNotExistException("Cet utilisateur d'Id : " + id + " n'existe pas");
    }

    @Override
    public User findByLogin(String login) {
        if (login == null || login.equals("")) {
            return null;
        } else {
            for (User oUser : users) {
                if (oUser.getLogin().equals("login")) {
                    return oUser;
                }
            }
        }
        return null;
    }

    @Override
    public User save(User user) throws UserNotCreateException, UserExistException {
        if (this.findByLogin(user.getLogin()) == null) {
            user.setId(this.getNextId());
            if (users.add(user)) return user;
            else throw new UserNotCreateException("La création de l'utilisateur a échoué");
        } else {
            throw new UserExistException("Le login " + user.getLogin() + " existe déja");
        }
    }

//    @Override
//    public Boolean delete(User user) throws UserNotExistException{
//        if(this.findById(user.getId())!= null) return users.remove(users);
//        else throw new UserNotExistException("Ce utilisateur d'Id : "+user.getId()+" n'existe pas");
//    }

    @Override
    public Boolean delete(User user) throws UserNotExistException {
        if (this.findById(user.getId()) != null) {
            for (User ouser : users) {
                if (user.getId() == ouser.getId()) {
                    int index = users.indexOf(ouser);
                    return users.remove(index) != null;
                }
            }
        } else throw new UserNotExistException("Ce utilisateur d'Id : " + user.getId() + " n'existe pas");
        return false;
    }

    @Override
    public User update(User user) throws UserNotExistException {
        User oUser = this.findById(user.getId());
        if (oUser != null) {
            int index = users.indexOf(oUser);
            users.set(index, user);
            if (users.contains(user)) return user;
            else return null;
        } else throw new UserNotExistException("Ce utilisateur d'Id : " + user.getId() + " n'existe pas");
    }

    @Override
    public Boolean assignateTasksToUser(List<Task> lstTasks, User user) throws UserNotExistException {
        User oUser = this.findById(user.getId());
        if (oUser != null) {
            int index = users.indexOf(user);
            oUser.getLstTasks().addAll(lstTasks);
            users.add(index, oUser);
            return true;
        } else {
            throw new UserExistException("Cet utilsateur n'existe pas");
        }
    }

    @Override
    public User doLogin(String login, String pwd) throws UserNotExistException {
        for (User oUser : users) {
            if (oUser.getLogin().equals(login) && oUser.getPwd().equals(pwd))
                System.out.println("yes login");
                return oUser;
        }
        throw new UserNotExistException("Login ou mot de passe incorrect");
    }


    @Override
    public int getNextId() {
        int nextId = users.size() + 1;
        if (users.isEmpty()) {
            return 1;
        } else {

            Boolean isExist = false;
            do {
                isExist = false;
                for (User user : users) {
                    if (user.getId() == nextId) {
                        isExist = true;
                        nextId++;
                        break;
                    }
                }

            } while (isExist == true);
        }
        return nextId;
    }
}
