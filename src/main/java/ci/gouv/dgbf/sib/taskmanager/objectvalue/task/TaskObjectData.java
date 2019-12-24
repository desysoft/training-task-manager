package ci.gouv.dgbf.sib.taskmanager.objectvalue.task;

import ci.gouv.dgbf.sib.taskmanager.model.Operation;
import ci.gouv.dgbf.sib.taskmanager.model.Task;
import ci.gouv.dgbf.sib.taskmanager.model.VersionTask;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TaskObjectData implements Serializable {

    private String id;
    private String code;
    private String name;
    private String description;
    private float nbreestimatehours;
    private int intVersion;

    private List<VersionTask> lstVersionTasks;

    public TaskObjectData() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getNbreestimatehours() {
        return nbreestimatehours;
    }

    public void setNbreestimatehours(Float nbreestimatehours) {
        this.nbreestimatehours = nbreestimatehours;
    }

    public int getIntVersion() {
        return intVersion;
    }

    public void setIntVersion(int intVersion) {
        this.intVersion = intVersion;
    }

    public List<VersionTask> getLstVersionTasks() {
        return lstVersionTasks;
    }

    public void setLstVersionTasks(List<VersionTask> lstVersionTasks) {
        this.lstVersionTasks = lstVersionTasks;
    }
}
