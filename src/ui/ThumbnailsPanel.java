package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ThumbnailsPanel extends JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = -3135688155282317704L;
    private Frame parent;
    private JPanel innerPanel;
    private JScrollPane scrl;
    private final static int gap = 5;
    
    public ThumbnailsPanel(Frame parent) {
        this.parent = parent;
        this.innerPanel = new JPanel();
        this.innerPanel.setBackground(Color.BLACK);
        scrl = new JScrollPane(innerPanel);
        // scrl.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
        this.add(scrl);
        this.setBackground(Color.BLACK);
        // innerPanel.setBorder(BorderFactory.createLineBorder(Color.CYAN));
    }
    public void setThumbnails(List<String> list) {
        System.out.println("in ThumbnailsPanel.setThumnails start.");
        innerPanel.removeAll();
        innerPanel.validate();
        innerPanel.repaint();
        int elementSize = Thumbnail.thumbSize;   
        int nElements = list.size();
        int nRows = (int) Math.ceil(nElements / 3.0);
        int innerWidth = elementSize * 3 + 4 * gap;
        int innerHeight = nRows * elementSize + (nRows + 1) * gap;
        int scrlWidth = getOuterWidth();
        int scrlHeight = elementSize * 4 + 6 * gap;
        // scrl.setPreferredSize(new Dimension(scrlWidth,scrlHeight));
        int width = ((MainFrame) parent).getThumbnailsWidth();
        int height = ((MainFrame) parent).getThumbnailsHeight();
        scrl.setPreferredSize(new Dimension(width, height));
        scrl.setMinimumSize(new Dimension(width, height));
        scrl.setMaximumSize(new Dimension(width, height));
        innerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, gap, gap));
        innerPanel.setMaximumSize(new Dimension(innerWidth, innerHeight));
        innerPanel.setMinimumSize(new Dimension(innerWidth, innerHeight));
        innerPanel.setPreferredSize(new Dimension(innerWidth, innerHeight));
        
        for (String file : list)
            this.innerPanel.add(ThumbnailFactory.factory.makeThumbnail(this, file));
        innerPanel.validate();
        // thumbnailClicked(Thumbnail.getThumbnail(this, pictureFilenames.get(0)));
        System.out.println("Thumbnails added.");
        if (list.size() > 0) 
            ((MainFrame) parent).thumbnailClicked(list.get(0));
    }
    protected static int getOuterWidth() {
        return Thumbnail.thumbSize * 3 + 8 * gap;
    }
    public void thumbnailClicked(Object source) {
        String filename = ((Thumbnail) source).getFilename();
        System.err.println("Thumbnail " + filename + " has been clicked.");
        ((MainFrame) parent).thumbnailClicked(filename);
    }
}
