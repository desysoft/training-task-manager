package ci.gouv.dgbf.sib.taskmanager.objectvalue;

import java.io.Serializable;

public class UserData implements Serializable {

    private String id;
    private String login;
    private String pwd;

    public UserData() {
    }

    public UserData(String login, String pwd) {
        this.login = login;
        this.pwd = pwd;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
