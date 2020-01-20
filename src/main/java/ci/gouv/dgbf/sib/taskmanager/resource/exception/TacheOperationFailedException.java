package ci.gouv.dgbf.sib.taskmanager.resource.exception;

import javax.ws.rs.WebApplicationException;

public class TacheOperationFailedException extends WebApplicationException {
    public TacheOperationFailedException(String s) {
        super(s);
    }
}
