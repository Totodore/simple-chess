package fr.scriptis.simplechess.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Class for manipulating images (adding filters and effects)
 */
@AllArgsConstructor
public class ImageUtils {

    @Getter
    private BufferedImage image;

    public static ImageUtils fromSvg(InputStream stream, int width, int height) throws IOException {
        // Create a PNG transcoder.
        Transcoder t = new PNGTranscoder();

        // Set the transcoding hints.
        t.addTranscodingHint(PNGTranscoder.KEY_WIDTH, (float) width);
        t.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, (float) height);
        try {
            // Create the transcoder input.
            TranscoderInput input = new TranscoderInput(stream);

            // Create the transcoder output.
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(outputStream);

            // Save the image.
            t.transcode(input, output);

            // Flush and close the stream.
            outputStream.flush();
            outputStream.close();

            // Convert the byte stream into an image.
            byte[] imgData = outputStream.toByteArray();
            return new ImageUtils(ImageIO.read(new ByteArrayInputStream(imgData)));

        } catch (IOException | TranscoderException e) {
            e.printStackTrace();
        } finally {
            stream.close();
        }
        return null;
    }

    public ImageUtils applyGaussianBlur(int radius, float sigma) {
        BufferedImage dest = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        ConvolveOp op = getGaussianBlurFilter(radius, sigma, true);
        op.filter(image, dest);
        image = dest;
        dest.flush();
        return this;
    }

    public ImageUtils applyScale(float xRatio, float yRatio) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage dest = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale(xRatio, yRatio);
        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        image = scaleOp.filter(image, dest);
        dest.flush();
        return this;
    }

    public ImageUtils applyScale(float ratio) {
        return applyScale(ratio, ratio);
    }

    private static ConvolveOp getGaussianBlurFilter(int radius, float sigma, boolean horizontal) {
        if (radius < 1) {
            throw new IllegalArgumentException("Radius must be >= 1");
        }

        int size = radius * 2 + 1;
        float[] data = new float[size];

        float twoSigmaSquare = 2.0f * sigma * sigma;
        float sigmaRoot = (float) Math.sqrt(twoSigmaSquare * Math.PI);
        float total = 0.0f;

        for (int i = -radius; i <= radius; i++) {
            float distance = i * i;
            int index = i + radius;
            data[index] = (float) Math.exp(-distance / twoSigmaSquare) / sigmaRoot;
            total += data[index];
        }

        for (int i = 0; i < data.length; i++) {
            data[i] /= total;
        }

        Kernel kernel = horizontal ? new Kernel(size, 1, data) : new Kernel(1, size, data);
        return new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
    }
}
