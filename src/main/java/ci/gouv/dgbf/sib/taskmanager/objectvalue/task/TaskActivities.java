package ci.gouv.dgbf.sib.taskmanager.objectvalue.task;

import ci.gouv.dgbf.sib.taskmanager.model.Activity;
import ci.gouv.dgbf.sib.taskmanager.model.Task;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class TaskActivities implements Serializable {

    private Task OTask;
    private List<Activity> lstActivities;


    @JsonCreator
    public TaskActivities() {
    }

    @JsonCreator
    public TaskActivities(@JsonProperty("task") Task OTask, @JsonProperty("activities") List<Activity> lstActivities) {
        this.OTask = OTask;
        this.lstActivities = lstActivities;
    }

    public Task getOTask() {
        return OTask;
    }

    public void setOTask(Task OTask) {
        this.OTask = OTask;
    }

    public List<Activity> getLstActivities() {
        return lstActivities;
    }

    public void setLstActivities(List<Activity> lstActivities) {
        this.lstActivities = lstActivities;
    }
}
