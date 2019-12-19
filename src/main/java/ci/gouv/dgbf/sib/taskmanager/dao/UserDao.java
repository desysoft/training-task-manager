package ci.gouv.dgbf.sib.taskmanager.dao;

import ci.gouv.dgbf.sib.taskmanager.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class UserDao implements PanacheRepositoryBase<User, Long> {
    public List<User> findAllUser(){
        return list("status","enable");
    }

    public User findByLogin(String login){
        return find("login", login).firstResult();
    }

    public User doLogin(String login, String pwd){
        User oUser = User.find("login  = ?1 AND pwd = ?2 AND status = ?3 ",login, pwd,"enable").firstResult();
        if(oUser!=null){
            oUser.dt_lastconnection = LocalDateTime.now();
            this.persist(oUser);
        }
        return oUser;
    }
}
