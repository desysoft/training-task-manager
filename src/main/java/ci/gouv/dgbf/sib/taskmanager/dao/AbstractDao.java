package ci.gouv.dgbf.sib.taskmanager.dao;


import javax.persistence.EntityManager;

public abstract class AbstractDao {

    String message;
    String detailMessage;
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
}
