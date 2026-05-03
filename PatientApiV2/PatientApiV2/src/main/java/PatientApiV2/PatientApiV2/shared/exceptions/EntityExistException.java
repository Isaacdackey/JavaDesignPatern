package PatientApiV2.PatientApiV2.shared.exceptions;

public class EntityExistException extends RuntimeException {
    public EntityExistException(String message) {
        super(message);
    }
}
