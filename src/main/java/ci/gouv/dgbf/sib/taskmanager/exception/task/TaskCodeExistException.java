package ci.gouv.dgbf.sib.taskmanager.exception.task;

public class TaskCodeExistException extends RuntimeException {
    public TaskCodeExistException(String s) {
        super(s);
    }
}
