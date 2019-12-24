package ci.gouv.dgbf.sib.taskmanager.dao;

import ci.gouv.dgbf.sib.taskmanager.model.Users;
import ci.gouv.dgbf.sib.taskmanager.tools.ParametersConfig;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class UserDao implements PanacheRepositoryBase<Users, String> {


    public List<Users> findAllUser() {
        return list("status", "enable");
    }

    public List<Users> findAllUser(String search_value) {
        return find("(firstName LIKE :search_value OR lastName LIKE :search_value OR contact LIKE :search_value) AND status LIKE :status", Parameters.with("search_value", "%"+search_value+"%").and("status", ParametersConfig.status_enable)).list();
    }


    public Users findById(String id) {
        return find("id = ?1 AND status = ?2",id, ParametersConfig.status_enable).firstResult();
    }

    public Users findByLogin(String login) {
        return find("login = ?1 AND status = ?2",login, ParametersConfig.status_enable).firstResult();
    }

    public Users doLogin(String login, String pwd) {
        Users oUser = Users.find("login  = ?1 AND pwd = ?2 AND status = ?3 ", login, pwd, ParametersConfig.status_enable).firstResult();
        if (oUser != null) {
            oUser.dt_lastconnection = LocalDateTime.now();
            this.persist(oUser);
        }
        return oUser;
    }
}
