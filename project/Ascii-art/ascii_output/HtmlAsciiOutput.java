package ascii_output;

import java.io.FileWriter;
import java.io.IOException;

public class HtmlAsciiOutput implements AsciiOutput {
    private final String fileName, font;

    public HtmlAsciiOutput(String fileName, String font) { this.fileName = fileName; this.font = font; }

    @Override public void out(char[][] image) {
        try (FileWriter w = new FileWriter(fileName)) {
            w.write("<!DOCTYPE html><html><body style=\"background:#fff\"><pre style=\"font-family:'"
                    + font + "',monospace;font-size:8px;line-height:8px;letter-spacing:0\">");
            for (char[] row : image) {
                for (char c : row) w.write(c == '<' ? "&lt;" : c == '>' ? "&gt;" : c == '&' ? "&amp;" : String.valueOf(c));
                w.write("\n");
            }
            w.write("</pre></body></html>");
        } catch (IOException e) {
            System.out.println("Did not execute due to incorrect format.");
        }
    }
}
