package ui;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;


public class Thumbnail extends JButton {
       // private static HashMap<String,Thumbnail> thumbnails = new HashMap<String, Thumbnail>();
       private static HashMap<String, ImageIcon> fullSizeImages = new HashMap<String, ImageIcon>();
       protected static final int thumbSize = 80;
       private ThumbnailsPanel parent;
       private String filename;
       
       protected Thumbnail(ThumbnailsPanel parent, String filename, ImageIcon image) {
           super(image);
           this.parent = parent;
           this.filename = filename;
           this.addActionListener(new Action());
       }
       /*
       public static Thumbnail getThumbnail(ThumbnailsPanel parent, String pictureFilename) {
           if (thumbnails.containsKey(pictureFilename)) {
               // System.out.println("Thumbnail is in table (" + thumbnails.get(pictureFilename)+ ")");
               return thumbnails.get(pictureFilename);
           } else {
               System.out.println("in Thumbnail.getThumbnail" + pictureFilename);
               ImageIcon fullSizeImage = (new ImageIcon(pictureFilename));
               // fullSizeImages.put(pictureFilename, fullSizeImage);
               ImageIcon thumb = new ImageIcon(getScaledImage(fullSizeImage.getImage(),thumbSize-4,thumbSize-4));
               Thumbnail newThumbnail = new Thumbnail(parent, pictureFilename, thumb);
               newThumbnail.setMaximumSize(new Dimension(thumbSize,thumbSize));
               newThumbnail.setMinimumSize(new Dimension(thumbSize,thumbSize));
               newThumbnail.setPreferredSize(new Dimension(thumbSize,thumbSize));
               thumbnails.put(pictureFilename, newThumbnail);
               return newThumbnail;
           }
       }
       */
       protected String getFilename () {
           return filename;
       }
       protected static ImageIcon getFullSizeImageIcon(String filename) {
           if (fullSizeImages.containsKey(filename)) {
               System.out.println("in Thumbnail.getFullSizeImageIcon Image Icon : " + fullSizeImages.get(filename).getClass());
               return fullSizeImages.get(filename);
           } else 
               return new ImageIcon(filename);
       }
       protected static Image getFullSizeImage(String filename) {
           return Thumbnail.getFullSizeImageIcon(filename).getImage();
       }
 
       private class Action implements ActionListener {
           @Override
           public void actionPerformed(ActionEvent e) {
               ((Thumbnail) e.getSource()).parent.thumbnailClicked(e.getSource());
           }
       }
       /**
        * Resizes an image using a Graphics2D object backed by a BufferedImage.
        * @param srcImg - source image to scale
        * @param w - desired width
        * @param h - desired height
        * @return - the new resized image
        */
       protected static Image getScaledImage(Image srcImg, int w, int h){
           BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
           Graphics2D g2 = resizedImg.createGraphics();
           g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
           g2.drawImage(srcImg, 0, 0, w, h, null);
           g2.dispose();
           return resizedImg;
       }
}
