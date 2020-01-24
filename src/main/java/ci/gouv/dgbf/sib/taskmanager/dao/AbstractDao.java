package ci.gouv.dgbf.sib.taskmanager.dao;


import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

public abstract class AbstractDao {

    String message;
    String detailMessage;
    @Inject
    EntityManager em;

    

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
