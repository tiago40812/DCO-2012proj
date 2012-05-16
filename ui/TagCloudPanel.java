package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.omg.IOP.TaggedProfileHolder;

import domain.TagStatus;

public class TagCloudPanel extends JPanel implements ActionListener {
    // private ArrayList<String> selectedTags;
    // private ArrayList<String> notSelectedTags;
    // private Stack<String> history;
    private Frame parent;
    private JPanel cloudPanel;
    private JButton undoButton;
    // private JButton 
    public TagCloudPanel(Frame parent) {
        undoButton = new JButton("Undo");
        undoButton.addActionListener(this);
        undoButton.setAlignmentX(CENTER_ALIGNMENT);
        cloudPanel = new JPanel();
        cloudPanel.setAlignmentX(CENTER_ALIGNMENT);
        cloudPanel.setBackground(Color.BLACK);
        // selectedTags = new ArrayList<String>();
        // notSelectedTags = new ArrayList<String>();
        // history = new Stack<String>();
        this.parent = parent;
        // this.setMinimumSize(new Dimension())
        int buttonHeight = 30;
        int gap = 10;
        int width = ((MainFrame) parent).getTagCloudWidth();
        int height = ((MainFrame) parent).getTagCloudHeight();
        // System.out.println(new Dimension(width-gap, height-gap));
        cloudPanel.setLayout(new FlowLayout());
        // cloudPanel.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
        // cloudPanel.setPreferredSize(new Dimension(width-gap, height-gap-buttonHeight));
        // cloudPanel.setMaximumSize(new Dimension(width-gap, height-gap-buttonHeight));
        cloudPanel.setMinimumSize(new Dimension(width-gap, height-gap-buttonHeight));
        // this.setPreferredSize(new Dimension(width, height));
        // this.setMaximumSize(new Dimension(width, height));
        this.setMinimumSize(new Dimension(width, height));
        // this.setMinimumSize(new Dimension(ThumbnailsPanel.getOuterWidth(), this.getHeight()));
        // this.setPreferredSize(new Dimension(ThumbnailsPanel.getOuterWidth(), this.getHeight()));
        // this.setBackground(Color.RED);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));       
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(Color.BLACK);
        add(cloudPanel); 
        add(undoButton);
    }

    private void addTag(Tag tag) {
        tag.setParent(this);
        this.cloudPanel.add(tag);
        // System.out.println("in TagCloudPanel.addTag: " + tag);
    }
    /*
    public void tagClicked1(Object source) {
        history.push(this.toString());
        String tag = ((Tag) source).getText();
        System.out.println("in TagCloudPanel.tagClicked Tag " + tag + " has been clicked.");
        if (selectedTags.contains(tag)) {
            selectedTags.remove(tag);
            notSelectedTags.add(tag);
        } else if (notSelectedTags.contains(tag)) {
            notSelectedTags.remove(tag);
        } else {
            selectedTags.add(tag);
        }
        ((MainFrame) parent).getInfoLine().setCenterLabel("history: " + history.peek() + " now " + this);
        ((MainFrame) parent).tagClicked(tag);
    }
    */
    protected void tagClicked(Object source) {
        String tagText = ((Tag) source).getText();
        ((MainFrame) parent).tagClicked(tagText);
    }
    /*
    private void fromString(String selection) {
        String[] tags = selection.split(",");
        selectedTags.clear();
        notSelectedTags.clear();
        for (String tag : tags) {
            System.out.printf("in TagCloudPanel.setTags tag: [%s] selection: [%s]\n", tag, selection);
            if (tag.length() > 0)
                if (tag.charAt(0) == '|') 
                      notSelectedTags.add(tag);
                else selectedTags.add(tag);
        }
    }
    */
    /*
    public void recoverLastTagSelection() {
        try {
            String lastSelection = history.pop();
            fromString(lastSelection);
            // ((MainFrame) parent).newTagSelection(selectedTags, notSelectedTags);
            ((MainFrame) parent).getInfoLine().setCenterLabel("history: " + history.peek() + " now " + this);
            Tag.resetStatuses();
            Tag.setStatus(selectedTags, TagStatus.SELECTED);
            Tag.setStatus(notSelectedTags, TagStatus.NOTSELECTED);
        } catch (EmptyStackException e) {
            selectedTags.clear();
            notSelectedTags.clear();
            Tag.resetStatuses();
            // ((MainFrame) parent).newTagSelection(selectedTags, notSelectedTags);
            ((MainFrame) parent).getInfoLine().setCenterLabel("history: " + history.peek() + " now " + this);
        }
        
    }
    */
    public void initialize(Map<String, Double> map) {
        ArrayList<String> tags = new ArrayList<String>(map.keySet());
        Collections.sort(tags);
        this.cloudPanel.removeAll();
        this.cloudPanel.validate();
        this.cloudPanel.repaint();
        for (String tag : tags)
            addTag(Tag.makeTag(tag, map.get(tag)));
        this.cloudPanel.repaint();
        this.cloudPanel.validate();
        this.validate();this.repaint();
    }
    /*
    public String toString() {
        StringBuilder result = new StringBuilder("");
        for (String tag : selectedTags) {
            result.append(tag + ",");
        }
        for (String tag : notSelectedTags) {
            result.append("|" + tag + ",");
        }
        return result.toString();
    }
    */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == undoButton) {
            System.out.println("Undo button pressed.");
            ((MainFrame) parent).recoverLastTagSelection();
        } else {
            System.err.println("The feature " + e.getSource()
                    + " is not implemented yet.");
        }
    }

    /**
     * Asks the MainFrame object what is the status of tag text. 
     * @param text
     * @return status of the tag.
     */
    protected TagStatus getTagStatus(String text) {
        return ((MainFrame) parent).getTagStatus(text);
    }
}
