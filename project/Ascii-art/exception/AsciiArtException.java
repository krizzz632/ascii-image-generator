package exception;

/** Thrown for any user-facing error; the message is printed by the shell. */
public class AsciiArtException extends Exception {
    public AsciiArtException(String message) { super(message); }
}
