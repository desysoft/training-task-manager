package ci.gouv.dgbf.sib.taskmanager.objectvalue;

import ci.gouv.dgbf.sib.taskmanager.model.Task;
import ci.gouv.dgbf.sib.taskmanager.model.Users;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class UserTasks implements Serializable {

    private Users OUser;

    private List<Task> lstTasks;

    @JsonCreator
    public UserTasks() {
    }

    @JsonCreator
    public UserTasks(@JsonProperty("user") Users OUser, @JsonProperty("lsttasks") List<Task> lstTasks) {
        this.OUser = OUser;
        this.lstTasks = lstTasks;
    }

    public Users getOUser() {
        return OUser;
    }

    public void setOUser(Users OUser) {
        this.OUser = OUser;
    }

    public List<Task> getLstTasks() {
        return lstTasks;
    }

    public void setLstTasks(List<Task> lstTasks) {
        this.lstTasks = lstTasks;
    }
}
