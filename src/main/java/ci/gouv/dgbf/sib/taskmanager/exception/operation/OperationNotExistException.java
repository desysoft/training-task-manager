package ci.gouv.dgbf.sib.taskmanager.exception.operation;

public class OperationNotExistException extends RuntimeException {
    public OperationNotExistException(String message) {
        super(message);
    }
}
