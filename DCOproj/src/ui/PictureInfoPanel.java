package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class PictureInfoPanel extends JPanel {
    private JFrame parent;
    private JLabel fileValue;
    private JPanel tags;
    private JLabel size;
    
    public PictureInfoPanel(JFrame parent) {
        this.parent = parent;
        this.setBackground(Color.BLACK);
        this.setForeground(Color.WHITE);
        JLabel file = new JLabel("File: ");
        file.setForeground(Color.WHITE);
        size = new JLabel("size");
        size.setForeground(Color.WHITE);
        tags = new JPanel();
        tags.setBorder(BorderFactory.createTitledBorder(null,"Tags",
                                                        TitledBorder.DEFAULT_JUSTIFICATION,
                                                        TitledBorder.DEFAULT_POSITION,
                                                        this.getFont(),
                                                        Color.WHITE));
        tags.setBackground(Color.BLACK);
        tags.setForeground(Color.WHITE);
        fileValue = new JLabel("---");
        fileValue.setForeground(Color.WHITE);
        GridBagLayout mainLayout = new GridBagLayout();
        this.setLayout(mainLayout);
        // 
        GridBagConstraints ctr = new GridBagConstraints();
        ctr.fill = GridBagConstraints.EAST;
        ctr.gridx = 0;
        ctr.gridy = 0;
        ctr.weightx = 0.1;
        ctr.weighty = 0.1;
        this.add(file,ctr);
        
        ctr = new GridBagConstraints();
        ctr.fill = GridBagConstraints.BOTH;
        ctr.gridx = 1;
        ctr.gridy = 0;
        ctr.weightx = 0.9;
        ctr.weighty = 0.1;
        this.add(fileValue,ctr);
        
        ctr = new GridBagConstraints();
        ctr.fill = GridBagConstraints.BOTH;
        ctr.gridx = 1;
        ctr.gridy = 1;
        ctr.weightx = 0.9;
        ctr.weighty = 0.1;
        this.add(size,ctr);

        ctr = new GridBagConstraints();
        ctr.fill = GridBagConstraints.BOTH;
        ctr.gridx = 0;
        ctr.gridy = 3;
        ctr.gridwidth = 2;
        ctr.weighty = 0.9;
        this.add(tags,ctr);
        
        ctr = new GridBagConstraints();
        ctr.fill = GridBagConstraints.BOTH;
        ctr.gridx = 1;
        ctr.gridy = 1;
        ctr.weightx = 0.5;
        // ctr.weighty = 0.2;
        // this.add(tagsValue,ctr);
        int theHeight = 300;
        this.setPreferredSize(new Dimension(ThumbnailsPanel.getOuterWidth(),
                                            theHeight));
                                    this.setMinimumSize(new Dimension(ThumbnailsPanel.getOuterWidth(),
                                            theHeight));
                                    this.setMaximumSize(new Dimension(ThumbnailsPanel.getOuterWidth(),
                                            theHeight));
        ArrayList<String> tt = new ArrayList<String>();
    }
    
    protected void setInfo(String filename, List<String> list) {
        int index = filename.lastIndexOf(File.separatorChar);
        // System.out.println("in PictureInfoPanel.setInfo " + filename + " index " + index);
        // size.setText(((MainFrame) this.parent).getPictureWidth() + "x" + ((MainFrame) this.parent).getPictureHeight());
        size.setText(((MainFrame) parent).getPictureOrientation());
        fileValue.setText("");
        this.tags.removeAll();
        this.validate();
        if (index == -1) {
           fileValue.setText(filename);
        } else {
            fileValue.setText(filename.substring(index+1));
        }
        this.tags.removeAll();
        this.tags.add(new JLabel("one"));
        this.tags.add(new JLabel("two"));
        this.tags.add(new JLabel("three"));
        this.tags.add(new JLabel("four"));
        for (int i = 0; i < list.size(); i++) {
            JLabel l = new JLabel(list.get(i));
            l.setForeground(Color.WHITE);
            this.tags.add(l);
        }
        this.tags.validate();
        this.validate();
    }

}
