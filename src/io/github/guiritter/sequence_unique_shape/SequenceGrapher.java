package io.github.guiritter.sequence_unique_shape;

import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

/**
 *
 * @author Guilherme Alan Ritter
 */
public final class SequenceGrapher {

    private final int WHITE[] = {255, 255, 255};

    public final File folder;

    private int i;

    private BufferedImage image;

    public final int imageSize;

    private int maximumX;

    private int maximumY;

    private int minimumX;

    private int minimumY;

    public final int pointAmount;

    public FitLinear pointToPointFX;

    public FitLinear pointToPointFY;

    private final double pointX[];

    private final double pointY[];

    public final FitLinear positionToPixelX;

    /**
     * ye olde y image coordinate being the opposite of the y cartesian coordinate
     */
    public final FitLinear positionToPixelY;

    private WritableRaster raster;

    private int x;

    private int y;

    public void graph(int sequence[]) throws IOException {
        image = new BufferedImage(imageSize, imageSize, TYPE_INT_RGB);
        raster = image.getRaster();
        for (i = 0; i < pointAmount; i++) {
            pointToPointFX = new FitLinear(pointX[sequence[i]], pointY[sequence[i]], pointX[sequence[(i + 1) % pointAmount]], pointY[sequence[(i + 1) % pointAmount]]);
            pointToPointFY = new FitLinear(pointY[sequence[i]], pointX[sequence[i]], pointY[sequence[(i + 1) % pointAmount]], pointX[sequence[(i + 1) % pointAmount]]);
            minimumX = (int) Math.floor(Math.min(pointX[sequence[i]], pointX[sequence[(i + 1) % pointAmount]]));
            maximumX = (int) Math.floor(Math.max(pointX[sequence[i]], pointX[sequence[(i + 1) % pointAmount]]));
            minimumY = (int) Math.floor(Math.min(pointY[sequence[i]], pointY[sequence[(i + 1) % pointAmount]]));
            maximumY = (int) Math.floor(Math.max(pointY[sequence[i]], pointY[sequence[(i + 1) % pointAmount]]));
            for (x = minimumX; x <= maximumX; x++) {
                try {
                    raster.setPixel(x, (int) Math.floor(pointToPointFX.f(x)), WHITE);
                } catch (ArrayIndexOutOfBoundsException ex) {}
            }
            for (y = minimumY; y <= maximumY; y++) {
                try {
                    raster.setPixel((int) Math.floor(pointToPointFY.f(y)), y, WHITE);
                } catch (ArrayIndexOutOfBoundsException ex) {}
            }
        }
        ImageIO.write(image, "png", folder.toPath().resolve(Arrays.toString(sequence).replace("[", "").replace("]", "").replace(",", "") + ".png").toFile());
    }

    public SequenceGrapher(int imageSize, int pointAmount, File folder) {
        if (!folder.isDirectory()) {
            throw new RuntimeException("\"folder\" must be a folder");
        }
        this.folder = folder;
        this.imageSize = imageSize;
        this.pointAmount = pointAmount;
        positionToPixelX = new FitLinear(-1, ((double) imageSize) * 0.1, 1, ((double) imageSize) * 0.9);
        positionToPixelY = new FitLinear(-1, ((double) imageSize) * 0.9, 1, ((double) imageSize) * 0.1);
        pointX = new double[pointAmount];
        pointY = new double[pointAmount];
        double angle = Math.PI / 2.0;
        double angleDistance = (2.0 * Math.PI) / ((double) pointAmount);
        pointX[0] = ((double) imageSize) / 2.0;
        pointY[0] = ((double) imageSize) * 0.1;
        for (i = 1; i < pointAmount; i++) {
            angle += angleDistance;
            angle %= (2.0 * Math.PI);
            pointX[i] = positionToPixelX.f(Math.cos(angle));
            pointY[i] = positionToPixelY.f(Math.sin(angle));
        }
    }

    public static void main(String args[]) throws IOException {
//        new SequenceGrapher(512, 5, null);
        SequenceGrapher grapher = new SequenceGrapher(512, 7, new File("C:/Users/GuiR/Downloads/test/"));
        grapher.graph(new int[]{0, 1, 2, 3, 4, 5, 6});
    }
}
