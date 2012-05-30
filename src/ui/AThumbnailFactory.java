package ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public abstract class AThumbnailFactory {
    protected abstract int getThumbnailWidth();

    protected abstract int getThumbnailHeight();

    
    private static File getThumbnailFile(File fullSizeFile) {
        String tempdir = System.getProperty("java.io.tmpdir");
        if (!(tempdir.endsWith("/") || tempdir.endsWith("\\")))
            tempdir = tempdir + System.getProperty("file.separator");
        tempdir += fullSizeFile.getAbsolutePath()
                .replace(System.getProperty("file.separator"), "!");
        return new File(tempdir);
    }

    protected ImageIcon getThumbnailIcon(String fullSizeFilename) {
        File fullSizeFile = new File(fullSizeFilename);
        File thumbnailImageFile = getThumbnailFile(fullSizeFile);
        try {
            System.out
                    .println("in AThumbnail.getThumbnailIcon thumbnailImageFile: "
                            + thumbnailImageFile.getAbsolutePath());
            if (thumbnailImageFile.exists()) {
                return new ImageIcon(thumbnailImageFile.getAbsolutePath());
            } else {
                BufferedImage full = ImageIO.read(fullSizeFile);
                Image newThumbnail = full
                        .getScaledInstance(getThumbnailWidth() - 4,
                                           getThumbnailHeight() - 4,
                                           Image.SCALE_SMOOTH);
                ImageIcon newThumbnailIcon = new ImageIcon(newThumbnail);
                ImageIO.write(getBufferedImageFromImage(newThumbnail), "jpeg",
                              thumbnailImageFile);
                return newThumbnailIcon;
            }
        } catch (IOException e) {
            System.err.println("Error reading image " + fullSizeFilename);
            e.printStackTrace();
        }
        return null;
    }

    private BufferedImage getBufferedImageFromImage(Image img) {
        // This line is important, this makes sure that the image is
        // loaded fully
        img = new ImageIcon(img).getImage();

        // Create the BufferedImage object with the width and height of the
        // Image
        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img
                .getHeight(null), BufferedImage.TYPE_INT_RGB);

        // Create the graphics object from the BufferedImage
        Graphics g = bufferedImage.createGraphics();
        // Draw the image on the graphics of the BufferedImage
        g.drawImage(img, 0, 0, null);
        // Dispose the Graphics
        g.dispose();
        // return the BufferedImage
        return bufferedImage;
    }
}
