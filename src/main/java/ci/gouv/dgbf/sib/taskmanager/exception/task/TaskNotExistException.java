package ci.gouv.dgbf.sib.taskmanager.exception.task;

public class TaskNotExistException extends RuntimeException {
    public TaskNotExistException(String message) {
        super(message);
    }
}
