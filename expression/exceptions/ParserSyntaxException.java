package expression.exceptions;

public class ParserSyntaxException extends IllegalArgumentException {
    public ParserSyntaxException(String message) {
        super(message);
    }
}
