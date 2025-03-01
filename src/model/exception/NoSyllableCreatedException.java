package model.exception;

public class NoSyllableCreatedException extends RuntimeException {
    public NoSyllableCreatedException(String message) {
        super(message);
    }

  @Override
  public String toString() {
    return getMessage();
  }
}
