package ci.gouv.dgbf.sib.taskmanager.objectvalue.project;

import ci.gouv.dgbf.sib.taskmanager.model.Person;
import ci.gouv.dgbf.sib.taskmanager.model.Project;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class ProjectPersons implements Serializable {

    private Project OProject;
    private List<Person> lstPerson;

    @JsonCreator
    public ProjectPersons() {
    }

    @JsonCreator
    public ProjectPersons(@JsonProperty("project") Project OProject, @JsonProperty("persons") List<Person> lstPerson) {
        this.OProject = OProject;
        this.lstPerson = lstPerson;
    }

    public Project getOProject() {
        return OProject;
    }

    public void setOProject(Project OProject) {
        this.OProject = OProject;
    }

    public List<Person> getLstPerson() {
        return lstPerson;
    }

    public void setLstPerson(List<Person> lstPerson) {
        this.lstPerson = lstPerson;
    }
}
