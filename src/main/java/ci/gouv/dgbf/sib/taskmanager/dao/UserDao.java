package ci.gouv.dgbf.sib.taskmanager.dao;

import ci.gouv.dgbf.sib.taskmanager.exception.user.UserExistException;
import ci.gouv.dgbf.sib.taskmanager.model.Task;
import ci.gouv.dgbf.sib.taskmanager.model.Users;
import ci.gouv.dgbf.sib.taskmanager.tools.ParametersConfig;
import com.sun.org.apache.regexp.internal.RE;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
@Transactional
public class UserDao implements PanacheRepositoryBase<Users, String> {


    @Inject
    TaskDao OTaskDao;

    public List<Users> findAllUser() {
        return list("status", "enable");
    }

    public List<Users> findAllUser(String search_value) {
        return find("(firstName LIKE :search_value OR lastName LIKE :search_value OR contact LIKE :search_value) AND status LIKE :status", Parameters.with("search_value", "%" + search_value + "%").and("status", ParametersConfig.status_enable)).list();
    }


    public Users findByIdCustom(String id) {
        return find("id = ?1 AND status = ?2", id, ParametersConfig.status_enable).firstResult();
    }

    public Users findByLogin(String login) {
        return find("login = ?1 AND status = ?2", login, ParametersConfig.status_enable).firstResult();
    }

    public Users doLogin(String login, String pwd) {
        Users oUser = Users.find("login  = ?1 AND pwd = ?2 AND status = ?3 ", login, pwd, ParametersConfig.status_enable).firstResult();
        if (oUser != null) {
            oUser.dt_lastconnection = LocalDateTime.now();
            this.persist(oUser);
        }
        return oUser;
    }

    public Boolean addUser(Users users, String createdBy) {
        try {
            users.status = ParametersConfig.status_enable;
            users.createdBy = createdBy;
            this.persist(users);
            return this.isPersistent(users);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean updateUser(Users user, String updatedBy) throws UserExistException {
        try {
            Users oUser = this.findByIdCustom(user.id);
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

    public Boolean blockUser(Users user, String updatedBy) throws UserExistException {
        try {
            Users oUsers = this.findByIdCustom(user.id);
            if (oUsers == null) throw new UserExistException("Utilisateur introuvable");
            oUsers.status = ParametersConfig.status_block;
            oUsers.updatedBy = updatedBy;
            this.persist(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean deleteUser(Users users, String updatedBy) throws UserExistException {
        try {
            Users oUsers = this.findByIdCustom(users.id);
            if (oUsers == null) throw new UserExistException("Utilisateur introuvable");
            oUsers.status = ParametersConfig.status_delete;
            oUsers.updatedBy = updatedBy;
            this.persist(oUsers);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean assignateTasksToUser(Users user, List<Task> lstTasks, String updatedBy) throws UserExistException {
        try {
            Users oUsers = this.findByIdCustom(user.id);
            if (oUsers == null) throw new UserExistException("Utilisateur introuvable");
            System.out.println("assignateTaskToUser +++ Taille lstTasks === " + lstTasks.size());
            for (Task oTask : lstTasks) {
                System.out.println("oTask === " + oTask);
                Task oneTask = OTaskDao.findByIdCustom(oTask.id);
                System.out.println("oneTask === " + oneTask);
                if (oneTask.OUser == null || oneTask.OUser.id != oUsers.id) {
                    oneTask.OUser = oUsers;
                    oneTask.updatedBy = updatedBy;
                    OTaskDao.persist(oneTask);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean assignateTaskToUser(Users user, Task task, String updatedBy) throws UserExistException {
        try {
            Users oUsers = this.findByIdCustom(user.id);
            if (oUsers == null) throw new UserExistException("Utilisateur introuvable");
            Task oTask = OTaskDao.findByIdCustom(task.id);
            System.out.println("oneTask === " + oTask);
            if (oTask.OUser == null || oTask.OUser.id != oUsers.id) {
                oTask.OUser = oUsers;
                oTask.updatedBy = updatedBy;
                OTaskDao.persist(oTask);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
