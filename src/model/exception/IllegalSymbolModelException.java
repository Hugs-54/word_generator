package model.exception;

public class IllegalSymbolModelException extends Exception {

    public IllegalSymbolModelException(String message) {
        super(message);
    }

    public IllegalSymbolModelException() {
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
