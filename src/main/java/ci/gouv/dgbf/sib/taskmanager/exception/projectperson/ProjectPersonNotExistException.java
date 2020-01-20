package ci.gouv.dgbf.sib.taskmanager.exception.projectperson;

public class ProjectPersonNotExistException extends RuntimeException {
    public ProjectPersonNotExistException(String message) {
        super(message);
    }
}
