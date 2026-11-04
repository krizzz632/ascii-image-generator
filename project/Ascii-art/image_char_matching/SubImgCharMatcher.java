package image_char_matching;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/** Maps a brightness value (0..1) to the closest character in the active set. */
public class SubImgCharMatcher {
    private static final int SIZE = 16;
    private final Map<Character, Double> rawBrightness = new TreeMap<>();
    private final TreeSet<Character> chars = new TreeSet<>();

    public SubImgCharMatcher(char[] initial) { for (char c : initial) addChar(c); }

    public void addChar(char c) {
        chars.add(c);
        rawBrightness.computeIfAbsent(c, SubImgCharMatcher::measure);
    }

    public void removeChar(char c) { chars.remove(c); }
    public TreeSet<Character> getChars() { return chars; }

    /** Closest char by stretched brightness; ties -> smallest ASCII value. */
    public char getCharByImageBrightness(double brightness) {
        double min = Double.MAX_VALUE, max = -Double.MAX_VALUE;
        for (char c : chars) {
            min = Math.min(min, rawBrightness.get(c));
            max = Math.max(max, rawBrightness.get(c));
        }
        char best = chars.first();
        double bestDiff = Double.MAX_VALUE;
        for (char c : chars) {
            double v = (max == min) ? 0 : (rawBrightness.get(c) - min) / (max - min);
            double diff = Math.abs(v - brightness);
            if (diff < bestDiff) { bestDiff = diff; best = c; }
        }
        return best;
    }

    /** Fraction of white pixels when the char is drawn on a SIZE x SIZE black canvas. */
    private static double measure(char c) {
        BufferedImage img = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, SIZE));
        g.setColor(java.awt.Color.WHITE);
        g.drawString(String.valueOf(c), 0, SIZE - 3);
        g.dispose();
        int white = 0;
        for (int y = 0; y < SIZE; y++)
            for (int x = 0; x < SIZE; x++)
                if ((img.getRaster().getSample(x, y, 0)) > 127) white++;
        return 1.0 - white / (double) (SIZE * SIZE);
    }
}
