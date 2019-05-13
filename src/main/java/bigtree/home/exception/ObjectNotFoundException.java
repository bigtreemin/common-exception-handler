package bigtree.home.exception;

public class ObjectNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -5537506783915861667L;

    public ObjectNotFoundException(String message) {
        super(message);
    }
}
