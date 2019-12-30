package ci.gouv.dgbf.sib.taskmanager.dao;

import ci.gouv.dgbf.sib.taskmanager.model.Operation;
import ci.gouv.dgbf.sib.taskmanager.model.Project;
import ci.gouv.dgbf.sib.taskmanager.model.VersionProject;
import ci.gouv.dgbf.sib.taskmanager.tools.ParametersConfig;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class VersionProjectDao implements PanacheRepositoryBase<VersionProject, String> {

    public List<VersionProject> findAllVersionProjects(){
        return find("status = ?1", ParametersConfig.status_enable).list();
    }

    public VersionProject findByIdCustom(String id){
        return find("id = ?1 AND status = ?2", id, ParametersConfig.status_enable).firstResult();
    }

    public List<VersionProject> findAllByProject(String id_project){
        return find("OProject.id = ?1 AND status = ?2", id_project, ParametersConfig.status_enable).list();
    }

    public void addVersionProject(Project oProject, Operation oOperation){
        VersionProject oVersionProject = new VersionProject();
        System.out.println("addVersionProject +++ leadProject === "+oProject.OPerson);
        oVersionProject.OProject = oProject;
        oVersionProject.OOperation = oOperation;
        oVersionProject.intVersion = oProject.intVersion;
        oVersionProject.description = oOperation.name+" - "+ oProject.name;
        oVersionProject.id_Person = (oProject.OPerson==null)?null:oProject.OPerson.id;
        persist(oVersionProject);
    }
}
