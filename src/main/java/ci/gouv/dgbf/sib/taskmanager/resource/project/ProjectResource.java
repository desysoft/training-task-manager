package ci.gouv.dgbf.sib.taskmanager.resource.project;

import ci.gouv.dgbf.sib.taskmanager.dao.ProjectDao;
import ci.gouv.dgbf.sib.taskmanager.model.Person;
import ci.gouv.dgbf.sib.taskmanager.model.Project;
import ci.gouv.dgbf.sib.taskmanager.model.Task;
import ci.gouv.dgbf.sib.taskmanager.objectvalue.project.ProjectPersonTasks;
import ci.gouv.dgbf.sib.taskmanager.objectvalue.project.ProjectPersons;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("projects")
@Produces(MediaType.APPLICATION_JSON)
public class ProjectResource {

    @Inject
    ProjectDao OProjectDao;

    @GET
    public List<Project> listeDesProjets() {
        return OProjectDao.findAllProject();
    }

    @GET
    @Path("{id}")
    public Project trouverProjetParSonId(@PathParam("id") String id) {
        return OProjectDao.findByIdCustom(id).orElse(null);
    }

    @GET
    @Path("search/{search_value}")
    public List<Project> rechercherProjetParMotCle(@PathParam("search_value") String search_value) {
        return OProjectDao.findAllProject(search_value);
    }

    @GET
    @Path("{id}/tasks")
    public List<Task> obtenirLesTachesDuProjet(@PathParam("id") String id_project) {
        return OProjectDao.findAllTasksProject(id_project);
    }

    @GET
    @Path("{id}/persons")
    public List<Person> obtenirLespersonnesTravaillantSurUnProjet(@PathParam("id") String id_project) {
        return OProjectDao.findAllPersonOfProject(id_project);
    }

    @POST
    public Project ajouterUnProjet(Project project) {
        return OProjectDao.addProject(project);
    }

    @PUT
    public Project modifierUnProjet(Project project) {
        return OProjectDao.updateProject(project);
    }

    @PUT
    @Path("{id_project}/person/{id_person}/task/{id_task}")
    public Project assignerAunePersonneUneTacheSurProjet(@PathParam("id_project") String id_project, @PathParam("id_person") String id_person, @PathParam("id_task") String id_task) {
        Task task = new Task();
        task.id = id_task;
        Project project = new Project();
        project.id = id_project;
        Person person = new Person();
        person.id = id_person;
        project = OProjectDao.assignateTaskToPersonInProjet(task, person, project);
        if (project != null) project.setProjectCompletionRate();
        return project;
    }

    @PUT
    @Path("person/tasks/{updatedBy}")
    public Project assignerTacheAUnePersonneSurUnProjet(ProjectPersonTasks projectPersonTasks, String updatedBy) {
        final Project[] oProject = {null};
        projectPersonTasks.getLstTasks().stream().forEach(task -> {
            Project project = OProjectDao.assignateTaskToPersonInProjet(task, projectPersonTasks.getOPerson(), projectPersonTasks.getOProject());
            oProject[0] = project;
            System.out.println("OProjectDao DetailMessage === " + OProjectDao.getDetailMessage());
        });
        oProject[0].setProjectCompletionRate();
        return oProject[0];
    }

    @POST
    @Path("persons/{createdBy}")
    @Transactional
    public Response assignerDesPersonnesAUnProjet(ProjectPersons projectPersons, @PathParam("createdBy") String createdBy) {
        try {
            projectPersons.getLstPerson().stream().forEach(person -> {
                OProjectDao.getOProjectPersonDao().addProjectPerson(projectPersons.getOProject().id, person.id, createdBy);
            });
            return Response.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().tag(OProjectDao.getDetailMessage()).build();
        }
    }

//    @PUT
//    @Path("tasks/{updatedBy}")
//    public Response ajouterDesTachesAUnProjet(ProjectPersonTasks projectPersonTasks, @PathParam("updatedBy") String updatedBy) {
//        try {
//            OProjectDao.addTasksInProjet(projectPersonTasks.getLstTasks(), projectPersonTasks.getOProject(), updatedBy);
//            return Response.ok().build();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Response.serverError().tag(OProjectDao.getDetailMessage()).build();
//        }
//    }

    @PUT
    @Path("{id}/tasks/{updatedBy}")
    public Project ajouterDesTachesAUnProjet(@PathParam("id") String id, List<Task> lstTasks, @PathParam("updatedBy") String updatedBy) {
            Project oProject = new Project();
            oProject.id =id;
            return OProjectDao.addTasksInProjet(lstTasks, oProject, updatedBy);
    }

    @PUT
    @Path("designateLead/{id_project}")
    public Project designerResponsableProjet(@PathParam("id_project") String id_project, Person person) {
        return OProjectDao.designateLeadProject(id_project, person);
    }


    @DELETE
    @Path("{id}")
    public Boolean supprimerUnProjet(@PathParam("id") String id_project) {
         return OProjectDao.deleteProject(id_project);
    }
}
