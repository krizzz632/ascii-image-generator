package image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/** Loads an image and pads it (with white) so width/height are powers of two. */
public class Image {
    private final Color[][] pixels;
    private final int width, height;

    public Image(String path) throws IOException {
        BufferedImage img = ImageIO.read(new File(path));
        if (img == null) throw new IOException("not an image");
        width = pow2(img.getWidth());
        height = pow2(img.getHeight());
        pixels = new Color[height][width];
        int offY = (height - img.getHeight()) / 2, offX = (width - img.getWidth()) / 2;
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                int ix = x - offX, iy = y - offY;
                boolean inside = ix >= 0 && iy >= 0 && ix < img.getWidth() && iy < img.getHeight();
                pixels[y][x] = inside ? new Color(img.getRGB(ix, iy)) : Color.WHITE;
            }
    }

    private static int pow2(int n) {
        int p = 1;
        while (p < n) p <<= 1;
        return p;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    /** Average grayscale brightness (0..1) of the square block at (row, col) of size 'size'. */
    public double blockBrightness(int row, int col, int size) {
        double sum = 0;
        for (int y = row * size; y < (row + 1) * size; y++)
            for (int x = col * size; x < (col + 1) * size; x++) {
                Color c = pixels[y][x];
                sum += (c.getRed() * 0.2126 + c.getGreen() * 0.7152 + c.getBlue() * 0.0722) / 255.0;
            }
        return sum / (size * size);
    }
}
