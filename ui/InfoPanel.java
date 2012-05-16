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

public class InfoPanel extends JPanel {
    private JFrame parent;
    private JLabel fileValue;
    private JPanel tags;
    
    public InfoPanel(JFrame parent) {
        this.parent = parent;
        JLabel file = new JLabel("File: ");
        tags = new JPanel();
        tags.setBorder(BorderFactory.createTitledBorder("Tags"));
        fileValue = new JLabel("---");
        GridBagLayout mainLayout = new GridBagLayout();
        this.setLayout(mainLayout);
        // thumbnailsPanel
        GridBagConstraints ctr = new GridBagConstraints();
        ctr.fill = GridBagConstraints.BOTH;
        ctr.gridx = 0;
        ctr.gridy = 0;
        ctr.weightx = 0.1;
        // ctr.weighty = 0.2;
        this.add(file,ctr);
        
        ctr = new GridBagConstraints();
        ctr.fill = GridBagConstraints.BOTH;
        ctr.gridx = 1;
        ctr.gridy = 0;
        ctr.weightx = 0.9;
        // ctr.weighty = 0.2;
        this.add(fileValue,ctr);

        ctr = new GridBagConstraints();
        ctr.fill = GridBagConstraints.BOTH;
        ctr.gridx = 0;
        ctr.gridy = 2;
        ctr.gridwidth = 2;
        // ctr.weighty = 0.2;
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
        // tt.add("asdasd"); tt.add("ikjj");
        // setInfo("blabla", tt);
    }
    
    protected void setInfo(String filename, List<String> list) {
        int index = filename.lastIndexOf(File.separatorChar);
        System.out.println(filename + " index " + index);
        fileValue.setText("");
        this.tags.removeAll();
        this.validate();
        if (index == -1) {
           fileValue.setText(filename);
        } else {
            fileValue.setText(filename.substring(index+1));
        }
        this.tags.removeAll();
        for (int i = 0; i < list.size(); i++) 
            this.tags.add(new JLabel(list.get(i)));
        this.validate();
    }

}
