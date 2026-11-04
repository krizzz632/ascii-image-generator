package ascii_art;

import image.Image;
import image_char_matching.SubImgCharMatcher;

public class AsciiArtAlgorithm {
    private final Image image;
    private final int resolution;
    private final SubImgCharMatcher matcher;

    public AsciiArtAlgorithm(Image image, int resolution, SubImgCharMatcher matcher) {
        this.image = image; this.resolution = resolution; this.matcher = matcher;
    }

    public char[][] run() {
        int size = image.getWidth() / resolution;
        int rows = image.getHeight() / size;
        char[][] out = new char[rows][resolution];
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < resolution; c++)
                out[r][c] = matcher.getCharByImageBrightness(image.blockBrightness(r, c, size));
        return out;
    }
}
