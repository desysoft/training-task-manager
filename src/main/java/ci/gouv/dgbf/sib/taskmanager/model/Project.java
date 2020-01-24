package ci.gouv.dgbf.sib.taskmanager.model;

import ci.gouv.dgbf.sib.taskmanager.tools.ParametersConfig;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Project extends AbstractEntity {

    public String name;
    public String description;
    public LocalDateTime dt_startProject;
    public LocalDateTime dt_endProject;
    public int intVersion;
    @ManyToOne
    @JoinColumn(name = "id_projectLead", referencedColumnName = "id")
    public Person OPerson;

    @OneToMany(mappedBy = "OProject")
    public List<ProjectPerson> lstProjectPerson = new ArrayList<>();


    @Transient
    public float count_task;

    @Transient
    public float projectCompletionRate = 0;

    @Override
    public String toString() {
        return "Project {" +
                " id = " + this.id +
                ", name = " + this.name +
                " }";
    }

    @PrePersist
    public void initializeEntity() {
        super.initializeEntity();
        this.status = ParametersConfig.status_enable;
    }

    @PreUpdate
    public void setEntityForUpdate() {
        super.setEntityForUpdate();
        this.intVersion++;
    }


    public void setProjectCompletionRate() {
        List<Task> lstTasks = Task.list("OProjectPerson.OProject.id = ?1 AND status <> ?2", this.id, ParametersConfig.status_delete);
        count_task = lstTasks.size();
        if (count_task > 0) {
            List<Task> lst = lstTasks.stream().filter(task -> task.status.equals(ParametersConfig.status_complete)).collect(Collectors.toList());
            projectCompletionRate = lst.size() * 100 / count_task;
        }
    }
}
