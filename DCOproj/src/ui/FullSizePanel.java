package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FullSizePanel extends JPanel {
    private JFrame parent;
    private JLabel p;
    private String picture;
    
    public FullSizePanel(JFrame parent) {
        this.parent = parent;
        p = new JLabel();
        GridBagLayout lo = new GridBagLayout();
        GridBagConstraints ctr = new GridBagConstraints();
        ctr.fill = GridBagConstraints.BOTH;
        ctr.gridx = 0;
        ctr.gridy = 0;
        
        this.setLayout(lo);
        this.add(p, ctr);
        this.setBackground(Color.BLACK);
        int width = ((MainFrame) (this.parent)).getFrameWidth() -  2 * ThumbnailsPanel.getOuterWidth();
        int height = ((MainFrame) (this.parent)).getFrameHeight();
        this.setSize(new Dimension(width,height));
        this.setMaximumSize(new Dimension(width,height));
        this.setMinimumSize(new Dimension(width,height));
        System.out.printf("in FullSizePanel.FullSizePanel FullSizePanel dimensions: %dx%d\n", width, height);
    }
    
    public void setPicture(String filename, int imageWidth, int imageHeight) {
        // System.out.println("in FullSizePanel.setPicture " + filename);
        double angle = 0.0;
        double ratio = 1.0;
        int w = this.getWidth();
        int h = this.getHeight();
        int iw = 0;
        int ih = 0;
        picture = filename;
        // this.removeAll();
        ImageIcon fullSizeIcon = Thumbnail.getFullSizeImageIcon(filename);
        // ratio = (imageWidth > imageHeight) ?((this.getWidth() - 40.0)/ imageWidth) : ((this.getHeight() - 40.0)/ imageHeight) ;
        String [] orientation = ((MainFrame) parent).getPictureOrientation().split(" ");
        if (orientation[orientation.length - 1].equals("CW)") && 
            orientation[orientation.length -2].equals("90")) {
            angle = Math.PI / 2;
            // ratio = (w > h) ? (h - 40.0) / imageWidth : (w - 40.0) / imageHeight;
            ratio = ((double)imageWidth)/imageHeight;
            if (w < h) {
                ih = w - 40;
                iw = (int) Math.round(ih * ratio);
            } else {
                iw = h - 40;
                ih = (int) Math.round(iw / ratio);
            }
            // System.out.printf("Dwidth %s Dheight %s ratio = %s, imageWidth %d, imageHeight %d iw %s ih %s\n", 
            //                   w - 40.0, h - 40.0, ratio, imageWidth, imageHeight, iw, ih);
            p.setIcon(new ImageIcon(Thumbnail.getScaledAndRotateImage(fullSizeIcon.getImage(), iw, ih, angle)));
        } else if (orientation[orientation.length - 1].equals("normal)")) {
            angle = 0;
            // ratio = (w - 40.0)/ imageWidth;
            ratio = ((double)imageWidth)/imageHeight;
            if (w < h) {
                iw = w - 40; 
                ih = (int) Math.round(iw / ratio); 
            } else {
                ih = h - 40;
                iw = (int) Math.round(ih * ratio);
            }
            // System.out.printf("Dwidth %s Dheight %s ratio = %s, imageWidth %d, imageHeight %d\n", 
            //                   w - 40.0, h - 40.0, ratio, imageWidth, imageHeight);
            p.setIcon(new ImageIcon(Thumbnail.getScaledAndRotateImage(fullSizeIcon.getImage(), iw, ih, angle)));
        } else {
            System.err.println("Unkown orientation type : " + ((MainFrame) parent).getPictureOrientation());
        }        
        
    }
    
    protected String currentPicture() {
        return picture;
    }
}
