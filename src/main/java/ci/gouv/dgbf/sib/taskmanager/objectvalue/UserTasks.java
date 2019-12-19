package ci.gouv.dgbf.sib.taskmanager.objectvalue;

import ci.gouv.dgbf.sib.taskmanager.model.Task;
import ci.gouv.dgbf.sib.taskmanager.model.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class UserTasks implements Serializable {

    private User OUser;

    private List<Task> lstTasks;

    @JsonCreator
    public UserTasks() {
    }

    @JsonCreator
    public UserTasks(@JsonProperty("user") User OUser, @JsonProperty("lsttasks") List<Task> lstTasks) {
        this.OUser = OUser;
        this.lstTasks = lstTasks;
    }

    public User getOUser() {
        return OUser;
    }

    public void setOUser(User OUser) {
        this.OUser = OUser;
    }

    public List<Task> getLstTasks() {
        return lstTasks;
    }

    public void setLstTasks(List<Task> lstTasks) {
        this.lstTasks = lstTasks;
    }
}
