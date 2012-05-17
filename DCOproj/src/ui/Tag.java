package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import domain.TagStatus;


public class Tag extends JButton {
    private static HashMap<String,Tag> tagCollection = new HashMap<String,Tag>();
    private TagCloudPanel parent;
    // private TagStatus status;
    private final Border maybeBorder = new LineBorder(Color.BLACK, 1);
    private final Border selectedBorder = new LineBorder(Color.GREEN, 1);
    private final Border notSelectedBorder = new LineBorder(Color.RED, 1);
    private final int fontMaxSize = 70;
    private final int fontMinSize = 10;
    // private final String fontFamily = "URW Bookman L";
    private final String fontFamily = "Lucida Sans Regular";
    // private final String fontFamily = "URW Gothic L";
    // private final String fontFamily = "URW Chancery L";
    
    private Tag(String text, double size) {
        super(text); 
        int fontSize = (int) Math.round(fontMinSize + fontMaxSize * size);
        Font newButtonFont = new Font(fontFamily,Font.PLAIN,fontSize);
        System.out.println("in Tag.Tag new instance " + newButtonFont.getName() + 
                           text + " " + fontSize + ", " + size + "************************************* ");
        this.setFont(newButtonFont);
        setBorder(maybeBorder);
        this.addActionListener(new Action());
        // status = TagStatus.MAYBE;
        this.setForeground(Color.WHITE);
        this.setOpaque(true);
        this.setContentAreaFilled(false);
        this.validate();this.repaint();
        // this.setPreferredSize(new Dimension(this.getWidth() + 10, this.getHeight() + 10));
        this.setMargin(new Insets(10,13,10,13));
    }
    protected static Tag makeTag(String text, double size) {
        Tag theTag;
        if (tagCollection.containsKey(text)) {
             theTag = tagCollection.get(text);
             theTag.setSize(size);
        } else {
            theTag = new Tag(text, size);
            tagCollection.put(text, theTag);
        }
        return theTag;
    }
    protected void setSize(double size) {
      //Create font.
        //Font Name : Default button font
        //Font Style : Default button font style
        //Font Size : 16
        // Font newButtonFont=new Font(this.getFont().getName(),Font.ITALIC+Font.BOLD,size);
        
        int fontSize = (int) Math.round(fontMinSize + fontMaxSize * size);
        Font newButtonFont=new Font(fontFamily,// this.getFont().getName(),
                                    Font.PLAIN,fontSize);
        // System.out.println("in Tag.Tag new instance " + newButtonFont.getName() + 
        //                    this.getText() + " " + fontSize + ", " + size + "************************************* ");
        //Set JButton font using new created font
        this.setFont(newButtonFont);
    }
    protected void setParent(TagCloudPanel tagCloudPanel) {
        parent = tagCloudPanel;
    }
    /*
    protected void setStatus(TagStatus status) {
        // System.out.println("in Tag.setStatus before " + this + " status " + status);
        this.status = status;
        switch (status) {
            case MAYBE: this.setBorder(maybeBorder); this.setOpaque(false); break;
            case SELECTED: this.setBorder(selectedBorder);  this.setOpaque(false); break;
            case NOTSELECTED: this.setBorder(notSelectedBorder);  this.setOpaque(false); break;
        }
        this.setContentAreaFilled(false);
        // System.out.println("in Tag.setStatus after " + this + " status " + status);
        this.validate();
    }
    */
    private void setStatus() {
        TagStatus status = parent.getTagStatus(this.getText());
        switch (status) {
            case MAYBE: this.setBorder(maybeBorder); this.setOpaque(false); break;
            case SELECTED: this.setBorder(selectedBorder);  this.setOpaque(false); break;
            case NOTSELECTED: this.setBorder(notSelectedBorder);  this.setOpaque(false); break;
        }
        this.setContentAreaFilled(false);
        // System.out.println("in Tag.setStatus after " + this + " status " + status);
        this.validate();
    }
    /*
    protected static void setStatus(List<String> tags, TagStatus status) {
        for (String tag : tags) {
            if (tagCollection.containsKey(tag))
                tagCollection.get(tag).setStatus(status);
        }
    }
    protected static void resetStatuses() {
        for (Tag tag : tagCollection.values())
            tag.setStatus(TagStatus.MAYBE);
    }
    */
    private class Action implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            Tag tag = (Tag) e.getSource();
            ((Tag) e.getSource()).parent.tagClicked(e.getSource());
            /*
            switch (tag.status) {
                case MAYBE: tag.setStatus(TagStatus.SELECTED); break;
                case SELECTED: tag.setStatus(TagStatus.NOTSELECTED); break;
                case NOTSELECTED: tag.setStatus(TagStatus.MAYBE); break;
            }
            */
        }
    }
    public void paint(Graphics g) {
        // System.out.print("painting tag " + this);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        setStatus();
        super.paint(g2);
    }
    public String toString() {
       return "[" + this.getText() + "@" + this.hashCode()+ "," + 
               parent.getTagStatus(getText()) + "]";
    }
}
