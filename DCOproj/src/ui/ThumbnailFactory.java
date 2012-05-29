package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ThumbnailFactory extends MemoFile {

    protected static ThumbnailFactory factory                = new ThumbnailFactory();
    private File                      fullSizeFile;
    private int                       defaultThumbnailWidth  = 80;
    private int                       defaultThumbnailHeight = 80;

    private ThumbnailFactory() {
    };

    public ThumbnailFactory makeThumbnailFactory() {
        return factory;
    }

    protected Thumbnail makeThumbnail(ThumbnailsPanel parent,
            String fullSizeFilename) {
        System.out.println("in ThumbnailFactory.makeThumbnail image : "
                + fullSizeFilename);
        fullSizeFile = new File(fullSizeFilename);
        Thumbnail newThumbnail = new Thumbnail(parent, fullSizeFilename,
                (ImageIcon) factory.getResult());
        newThumbnail.setMaximumSize(new Dimension(getThumbnailWidth(),
                getThumbnailWidth()));
        newThumbnail.setMinimumSize(new Dimension(getThumbnailWidth(),
                getThumbnailWidth()));
        newThumbnail.setPreferredSize(new Dimension(getThumbnailWidth(),
                getThumbnailWidth()));
        return newThumbnail;
    }

    protected int getThumbnailWidth() {
        return defaultThumbnailWidth;
    }

    protected int getThumbnailHeight() {
        return defaultThumbnailHeight;
    }

    @Override
    protected File getResultFile() {
        // Na versão para o projecto convem que os thumnails de cada projecto
        // sejam guardados na pasta local ao projecto.
        /*
         * String tempdir = System.getProperty("user.home") +
         * "/.imagetagger/thumbnails/"; if (!(tempdir.endsWith("/") ||
         * tempdir.endsWith("\\"))) tempdir = tempdir +
         * System.getProperty("file.separator");
         */
        String tempdir = "";
        tempdir += fullSizeFile.getAbsolutePath()
                .replace(System.getProperty("file.separator"), "!")
                .substring(1);
        return new File(tempdir);
    }

    @Override
    protected Object readResult(File file) {
        return new ImageIcon(file.getAbsolutePath());
    }

    @Override
    protected Object executeOperation() {
        System.out.println("in ThumbnailFactory.executeOperation start.");
        BufferedImage full = null;
        try {
            full = ImageIO.read(fullSizeFile);
        } catch (IOException e) {
            System.err.println("Could not read image " + fullSizeFile);
            e.printStackTrace();
        }
        Image newThumbnail = full.getScaledInstance(getThumbnailWidth() - 4,
                                                    getThumbnailHeight() - 4,
                                                    Image.SCALE_SMOOTH);
        ImageIcon newThumbnailIcon = new ImageIcon(newThumbnail);
        System.out.println("in ThumbnailFactory.executeOperation done.");
        return newThumbnailIcon;
    }

    @Override
    protected void writeResult(File file, Object result) {
        try {
            ImageIO.write(getBufferedImageFromImage(((ImageIcon) result)
                    .getImage()), "jpeg", file);
        } catch (IOException e) {
            System.err.println("Could write image " + file);
            e.printStackTrace();
        }

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
