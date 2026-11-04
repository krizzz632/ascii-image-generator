package ascii_art;

import ascii_output.*;
import exception.AsciiArtException;
import image.Image;
import image_char_matching.SubImgCharMatcher;
import java.io.IOException;
import java.util.Scanner;

/** MAIN FILE: run this class. Command-line shell for the ASCII art generator. */
public class Shell {
    private static final String PROMPT = ">>> ";
    private static final int DEFAULT_RES = 128;

    private final SubImgCharMatcher matcher = new SubImgCharMatcher("0123456789".toCharArray());
    private Image image;
    private int resolution = DEFAULT_RES;
    private AsciiOutput output = new ConsoleAsciiOutput();

    public static void main(String[] args) {
        Shell shell = new Shell();
        if (args.length > 0) shell.loadImage(args[0]);
        shell.run();
    }

    public void run() {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.print(PROMPT);
            if (!in.hasNextLine()) return;
            String[] p = in.nextLine().trim().split("\\s+");
            try {
                switch (p[0]) {
                    case "exit": return;
                    case "chars": for (char c : matcher.getChars()) System.out.print(c + " "); System.out.println(); break;
                    case "add": changeChars(p, true); break;
                    case "remove": changeChars(p, false); break;
                    case "res": changeRes(p); break;
                    case "image": if (p.length < 2) throw new AsciiArtException("Did not execute due to incorrect format."); loadImage(p[1]); break;
                    case "output": changeOutput(p); break;
                    case "asciiArt": runAlgorithm(); break;
                    case "": break;
                    default: throw new AsciiArtException("Did not execute due to incorrect command.");
                }
            } catch (AsciiArtException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void loadImage(String path) {
        try { image = new Image(path); }
        catch (IOException e) { System.out.println("Did not execute due to problem with image file."); }
    }

    private void changeChars(String[] p, boolean add) throws AsciiArtException {
        String err = "Did not " + (add ? "add" : "remove") + " due to incorrect format.";
        if (p.length < 2) throw new AsciiArtException(err);
        String a = p[1];
        int from, to;
        if (a.equals("all")) { from = 32; to = 126; }
        else if (a.equals("space")) { from = to = ' '; }
        else if (a.length() == 1) { from = to = a.charAt(0); }
        else if (a.length() == 3 && a.charAt(1) == '-') {
            from = Math.min(a.charAt(0), a.charAt(2)); to = Math.max(a.charAt(0), a.charAt(2));
        } else throw new AsciiArtException(err);
        if (from < 32 || to > 126) throw new AsciiArtException(err);
        for (int c = from; c <= to; c++) { if (add) matcher.addChar((char) c); else matcher.removeChar((char) c); }
    }

    private void changeRes(String[] p) throws AsciiArtException {
        if (p.length < 2) { System.out.println("Resolution set to " + resolution + "."); return; }
        int next = resolution;
        if (p[1].equals("up")) next *= 2; else if (p[1].equals("down")) next /= 2;
        else throw new AsciiArtException("Did not change resolution due to incorrect format.");
        int min = image == null ? 1 : Math.max(1, image.getWidth() / image.getHeight());
        int max = image == null ? Integer.MAX_VALUE : image.getWidth();
        if (next < min || next > max) throw new AsciiArtException("Did not change resolution due to exceeding boundaries.");
        resolution = next;
        System.out.println("Resolution set to " + resolution + ".");
    }

    private void changeOutput(String[] p) throws AsciiArtException {
        if (p.length < 2) throw new AsciiArtException("Did not change output method due to incorrect format.");
        switch (p[1]) {
            case "console": output = new ConsoleAsciiOutput(); break;
            case "html": output = new HtmlAsciiOutput("out.html", "Courier New"); break;
            default: throw new AsciiArtException("Did not change output method due to incorrect format.");
        }
    }

    private void runAlgorithm() throws AsciiArtException {
        if (image == null) throw new AsciiArtException("Did not execute due to problem with image file.");
        if (matcher.getChars().size() < 2) throw new AsciiArtException("Did not execute. Charset is too small.");
        if (resolution > image.getWidth()) resolution = image.getWidth();
        output.out(new AsciiArtAlgorithm(image, resolution, matcher).run());
    }
}
