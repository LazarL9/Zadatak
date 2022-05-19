public class CyclicRelationshipException extends RuntimeException{
    public CyclicRelationshipException() {
        super("Djete od djeteta ne može biti roditelj!");
    }
    public CyclicRelationshipException(String message) {
        super(message);
    }
    public CyclicRelationshipException(String message, Throwable cause) {
        super(message, cause);
    }
    public CyclicRelationshipException(Throwable cause) {
        super(cause);
    }

}
