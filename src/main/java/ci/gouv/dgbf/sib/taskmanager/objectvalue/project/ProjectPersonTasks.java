package ci.gouv.dgbf.sib.taskmanager.objectvalue.project;

import ci.gouv.dgbf.sib.taskmanager.model.Person;
import ci.gouv.dgbf.sib.taskmanager.model.Project;
import ci.gouv.dgbf.sib.taskmanager.model.ProjectPerson;
import ci.gouv.dgbf.sib.taskmanager.model.Task;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class ProjectPersonTasks implements Serializable {

    private Project OProject;
    private Person OPerson;
    private List<Task> lstTasks;

    @JsonCreator
    public ProjectPersonTasks() {
    }

    @JsonCreator
    public ProjectPersonTasks(@JsonProperty("project") Project project, @JsonProperty("person") Person person, @JsonProperty("lsttasks") List<Task> lstTasks) {
        this.OProject = project;
        this.OPerson = person;
        this.lstTasks = lstTasks;
    }

    public Project getOProject() {
        return OProject;
    }

    public void setOProject(Project OProject) {
        this.OProject = OProject;
    }

    public Person getOPerson() {
        return OPerson;
    }

    public void setOPerson(Person OPerson) {
        this.OPerson = OPerson;
    }

    public List<Task> getLstTasks() {
        return lstTasks;
    }

    public void setLstTasks(List<Task> lstTasks) {
        this.lstTasks = lstTasks;
    }
}
