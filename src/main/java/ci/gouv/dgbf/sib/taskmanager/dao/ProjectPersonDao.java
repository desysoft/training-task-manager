package ci.gouv.dgbf.sib.taskmanager.dao;

import ci.gouv.dgbf.sib.taskmanager.model.Person;
import ci.gouv.dgbf.sib.taskmanager.model.Project;
import ci.gouv.dgbf.sib.taskmanager.model.ProjectPerson;
import ci.gouv.dgbf.sib.taskmanager.model.Task;
import ci.gouv.dgbf.sib.taskmanager.tools.ParametersConfig;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ApplicationScoped
@Transactional
public class ProjectPersonDao extends AbstractDao implements PanacheRepositoryBase<ProjectPerson, String> {

    @Inject
    TaskDao OTaskDao;


    public Optional<ProjectPerson> findByIdCustom(String id) {
        return find("id = ?1 AND status = ?2", id, ParametersConfig.status_enable).stream().findFirst();
    }

    public List<ProjectPerson> findAllProjectPerson() {
        return find("status", ParametersConfig.status_enable).list();
    }

    public List<ProjectPerson> findAllProjectPerson(String id_project, String id_person, String search_value) {
        List<ProjectPerson> lstProjectPerson = new ArrayList<>();
        try {
            if (id_project == null || id_project.equals(""))
                id_project = "%%";
            if (id_person == null || id_person.equals(""))
                id_person = "%%";
            search_value = (search_value == null || search_value.equals("")) ? "%%" : "%" + search_value + "%";
            lstProjectPerson = find("OProject.id LIKE :id_project AND OPerson.id LIKE :id_person AND description LIKE :search_value AND status LIKE :status  ",
                    Parameters.with("id_project", id_project)
                            .and("id_person", id_person)
                            .and("search_value", search_value)
                            .and("status", ParametersConfig.status_enable)).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstProjectPerson;
    }

    public Boolean addProjectPerson(String id_project, String id_person, String createdBy) {
        try {
            System.out.println("addProjectPerson +++ id_project === "+id_project);
            System.out.println("addProjectPerson +++ id_person === "+id_person);
            Project oProject = Project.find("id = ?1 AND status = ?2", id_project, ParametersConfig.status_enable).firstResult();
            if (oProject == null) {
                this.setMessage(ParametersConfig.FAILED_CREATE);
                this.setDetailMessage(ParametersConfig.projectNotFoundMessage);
                return false;
            }

            Person oPerson = Person.find("id = ?1 AND status = ?2 ", id_person, ParametersConfig.status_enable).firstResult();
            if (oPerson == null) {
                this.setMessage(ParametersConfig.FAILED_CREATE);
                this.setDetailMessage(ParametersConfig.personNotFoundMessage);
                return false;
            }
            List<ProjectPerson> lst = this.findAllProjectPerson(id_project, id_person, "");
            if (lst.size() == 0) {
                ProjectPerson oProjectPerson = new ProjectPerson();
                oProjectPerson.OProject = oProject;
                oProjectPerson.OPerson = oPerson;
                oProjectPerson.createdBy = createdBy;
                this.persist(oProjectPerson);
                this.setMessage(ParametersConfig.PROCESS_SUCCES);
                this.setDetailMessage(ParametersConfig.SUCCES_CREATE);
                return true;
            } else {
                this.setMessage(ParametersConfig.FAILED_CREATE);
                this.setDetailMessage(ParametersConfig.PROJECT_PERSON_ALREADY_EXIST);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean deleteProjectPerson(String id) {
        try {
            ProjectPerson oProjectPerson = this.findByIdCustom(id).orElse(null);
            if (oProjectPerson == null) {
                this.setMessage(ParametersConfig.PROCESS_FAILED);
                this.setDetailMessage(ParametersConfig.genericNotFoundMessage);
                return false;
            }
            oProjectPerson.status = ParametersConfig.status_delete;
            this.persist(oProjectPerson);
            this.setMessage(ParametersConfig.PROCESS_SUCCES);
            this.setDetailMessage(ParametersConfig.SUCCES_DELETE);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean deleteProjectPerson(ProjectPerson projectPerson) {
        try {
            projectPerson.status = ParametersConfig.status_delete;
            List<Task> lstTask = projectPerson.lstTasks;
            lstTask.stream().forEach(task -> OTaskDao.deleteTask(task));
            this.persist(projectPerson);
            this.setMessage(ParametersConfig.PROCESS_SUCCES);
            this.setDetailMessage(ParametersConfig.SUCCES_DELETE);
            return true;
        } catch (Exception e) {
            this.setMessage(ParametersConfig.PROCESS_FAILED);
            this.setDetailMessage("deleteProjectPerson ===> "+ParametersConfig.FAILED_DELETE);
            e.printStackTrace();
            return false;
        }
    }

}
