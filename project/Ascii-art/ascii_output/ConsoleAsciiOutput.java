package ascii_output;

public class ConsoleAsciiOutput implements AsciiOutput {
    @Override public void out(char[][] image) {
        for (char[] row : image) System.out.println(new String(row));
    }
}
