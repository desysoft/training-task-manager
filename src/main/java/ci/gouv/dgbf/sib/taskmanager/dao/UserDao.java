package ci.gouv.dgbf.sib.taskmanager.dao;

import ci.gouv.dgbf.sib.taskmanager.exception.user.UserExistException;
import ci.gouv.dgbf.sib.taskmanager.model.User;
import ci.gouv.dgbf.sib.taskmanager.tools.ParametersConfig;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
@Transactional
public class UserDao  extends AbstractDao implements PanacheRepositoryBase<User, String> {


    @Inject
    TaskDao OTaskDao;

    public List<User> findAllUser() {
        return list("status", "enable");
    }

    public List<User> findAllUser(String search_value) {
        return find("(firstName LIKE :search_value OR lastName LIKE :search_value OR contact LIKE :search_value) AND status LIKE :status", Parameters.with("search_value", "%" + search_value + "%").and("status", ParametersConfig.status_enable)).list();
    }


    public User findByIdCustom(String id) {
        return find("id = ?1 AND status = ?2", id, ParametersConfig.status_enable).firstResult();
    }

    public User findByLogin(String login) {
        return find("login = ?1 AND status = ?2", login, ParametersConfig.status_enable).firstResult();
    }

    public User doLogin(String login, String pwd) {
        User oUser = User.find("login  = ?1 AND pwd = ?2 AND status = ?3 ", login, pwd, ParametersConfig.status_enable).firstResult();
        if (oUser != null) {
            oUser.dt_lastconnection = LocalDateTime.now();
            this.persist(oUser);
        }
        return oUser;
    }

    public Boolean addUser(User user, String createdBy) {
        try {
            user.status = ParametersConfig.status_enable;
            user.createdBy = createdBy;
            this.persist(user);
            return this.isPersistent(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean updateUser(User user, String updatedBy) throws UserExistException {
        try {
            User oUser = this.findByIdCustom(user.id);
            if (oUser == null) throw new UserExistException("Utilisateur introuvable");
            if (user.lastName != null && !user.lastName.equals(""))
                oUser.lastName = user.lastName;
            if (user.firstName != null && !user.firstName.equals(""))
                oUser.firstName = user.firstName;
            if (user.login != null && !user.login.equals(""))
                oUser.login = user.login;
            if (user.pwd != null && !user.pwd.equals(""))
                oUser.pwd = user.pwd;
            oUser.updatedBy = updatedBy;
            this.persist(oUser);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean blockUser(User user, String updatedBy) throws UserExistException {
        try {
            User oUser = this.findByIdCustom(user.id);
            if (oUser == null) throw new UserExistException("Utilisateur introuvable");
            oUser.status = ParametersConfig.status_block;
            oUser.updatedBy = updatedBy;
            this.persist(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean deleteUser(User user, String updatedBy) throws UserExistException {
        try {
            User oUser = this.findByIdCustom(user.id);
            if (oUser == null) throw new UserExistException("Utilisateur introuvable");
            oUser.status = ParametersConfig.status_delete;
            oUser.updatedBy = updatedBy;
            this.persist(oUser);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//    public Boolean assignateTasksToUser(Users user, List<Task> lstTasks, String updatedBy) throws UserExistException {
//        try {
//            Users oUsers = this.findByIdCustom(user.id);
//            if (oUsers == null) throw new UserExistException("Utilisateur introuvable");
//            System.out.println("assignateTaskToUser +++ Taille lstTasks === " + lstTasks.size());
//            for (Task oTask : lstTasks) {
//                System.out.println("oTask === " + oTask);
//                Task oneTask = OTaskDao.findByIdCustom(oTask.id).orElse(null);
//                System.out.println("oneTask === " + oneTask);
//                if (oneTask.OUser == null || oneTask.OUser.id != oUsers.id) {
//                    oneTask.OUser = oUsers;
//                    oneTask.updatedBy = updatedBy;
//                    OTaskDao.persist(oneTask);
//                }
//            }
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }



//    public Boolean assignateTaskToUser(Users user, Task task, String updatedBy) throws UserExistException {
//        try {
//            Users oUsers = this.findByIdCustom(user.id);
//            if (oUsers == null) throw new UserExistException("Utilisateur introuvable");
//            Task oTask = OTaskDao.findByIdCustom(task.id).orElse(null);
//            System.out.println("oneTask === " + oTask);
//            if (oTask.OUser == null || oTask.OUser.id != oUsers.id) {
//                oTask.OUser = oUsers;
//                oTask.updatedBy = updatedBy;
//                OTaskDao.persist(oTask);
//            }
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
}
