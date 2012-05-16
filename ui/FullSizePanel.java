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
        System.out.println("in FullSizePanel.setPicture " + filename);
        picture = filename;
        // this.removeAll();
        ImageIcon fullSizeIcon = Thumbnail.getFullSizeImageIcon(filename);
        double ratio = (imageWidth > imageHeight) ?((this.getWidth() - 40.0)/ imageWidth) : ((this.getHeight() - 40.0)/ imageHeight) ;
        // System.out.printf("ratio = %s, imageWidth %d, imageHeight %d\n", ratio, imageWidth, imageHeight);
        p.setIcon(new ImageIcon(Thumbnail.getScaledImage(fullSizeIcon.getImage(), 
                                                         (int) Math.round(imageWidth * ratio), 
                                                         (int) Math.round(imageHeight * ratio))));
    }
    
    protected String currentPicture() {
        return picture;
    }
}
