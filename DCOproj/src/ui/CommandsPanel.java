package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CommandsPanel extends JPanel implements ActionListener {
    private JFrame     parent;
    private JButton    addTagButton;
    private JButton    removeTagButton;
    private JTextField tagInput;
    private JButton    nextButton;
    private JButton    prevButton;
    private JButton    startButton;
    private JButton    stopButton;

    private final int  theHeight = 200;

    protected CommandsPanel(JFrame parent) {
        this.parent = parent;
        // Remove & Add tags
        JPanel addAndRemoveTagPanel = new JPanel();
        JPanel buttons = new JPanel();
        this.addTagButton = new JButton("Add Tag");
        this.removeTagButton = new JButton("Remove Tag");
        addTagButton.addActionListener(this);
        removeTagButton.addActionListener(this);
        tagInput = new JTextField(22);
        addAndRemoveTagPanel.add(tagInput);
        buttons.add(addTagButton);
        buttons.add(removeTagButton);
        addAndRemoveTagPanel.add(buttons);
        // this.setBackground(Color.RED);
        int gapx = 15;
        int height = 90;
        addAndRemoveTagPanel.setPreferredSize(new Dimension(ThumbnailsPanel
                .getOuterWidth()
                - gapx, height));
        addAndRemoveTagPanel.setMinimumSize(new Dimension(ThumbnailsPanel
                .getOuterWidth()
                - gapx, height));
        addAndRemoveTagPanel.setMaximumSize(new Dimension(ThumbnailsPanel
                .getOuterWidth()
                - gapx, height));
        addAndRemoveTagPanel
                .setBorder(BorderFactory.createTitledBorder("Tags"));
        // SlideShow
        JPanel slideShowPanel = new JPanel();
        startButton = new JButton("Start");
        startButton.addActionListener(this);
        stopButton = new JButton("Stop");
        stopButton.addActionListener(this);
        prevButton = new JButton("<");
        prevButton.addActionListener(this);
        nextButton = new JButton(">");
        nextButton.addActionListener(this);
        slideShowPanel.add(startButton);
        slideShowPanel.add(stopButton);
        slideShowPanel.add(prevButton);
        slideShowPanel.add(nextButton);
        height = 65;
        slideShowPanel.setPreferredSize(new Dimension(ThumbnailsPanel
                .getOuterWidth()
                - gapx, height));
        slideShowPanel.setMinimumSize(new Dimension(ThumbnailsPanel
                .getOuterWidth()
                - gapx, height));
        slideShowPanel.setMaximumSize(new Dimension(ThumbnailsPanel
                .getOuterWidth()
                - gapx, height));
        slideShowPanel.setBorder(BorderFactory.createTitledBorder("SlideShow"));
        this.add(addAndRemoveTagPanel);
        this.add(slideShowPanel);
        this.setPreferredSize(new Dimension(ThumbnailsPanel.getOuterWidth(),
                theHeight));
        this.setMinimumSize(new Dimension(ThumbnailsPanel.getOuterWidth(),
                theHeight));
        this.setMaximumSize(new Dimension(ThumbnailsPanel.getOuterWidth(),
                theHeight));

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addTagButton) {
            System.out.println("Add tag button pressed. textfield = "
                    + tagInput.getText());
            ((MainFrame) this.parent).addTag(tagInput.getText());
        } else if (e.getSource() == removeTagButton) {
            System.out.println("Remove tag button pressed."
                    + tagInput.getText());
        } else if (e.getSource() == nextButton) {
            System.out.println("Next button pressed.");
            ((MainFrame) parent).nextPicture();
        } else if (e.getSource() == prevButton) {
            System.out.println("Previous button pressed.");
            ((MainFrame) parent).previousPicture();
        } else if (e.getSource() == startButton) {
            ((MainFrame) parent).startSlideShow();
        } else if (e.getSource() == stopButton) {
            ((MainFrame) parent).stopSlideShow();
        } else {
            System.err.println("The feature " + e.getSource()
                    + " is not implemented yet.");
        } 
    }
}
