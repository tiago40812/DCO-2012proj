package ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoLine extends JPanel {
    private JFrame parent;
    private JLabel left;
    private JLabel center;
    private JLabel right;
    
    public InfoLine(JFrame parent) {
        this.parent = parent;
        setLayout(new GridBagLayout());
        left = new JLabel("[left]");
        right = new JLabel("");
        center = new JLabel("");
        GridBagConstraints ctr = new GridBagConstraints();
        ctr.fill = GridBagConstraints.HORIZONTAL;
        ctr.gridx = 0;
        ctr.gridy = 0;
        ctr.weightx = 0.1;
        // ctr.weighty = 0.2;
        this.add(left,ctr);
        ctr = new GridBagConstraints();
        ctr.fill = GridBagConstraints.HORIZONTAL;
        ctr.gridx = 1;
        ctr.gridy = 0;
        ctr.weightx = 0.1;
        // ctr.weighty = 0.2;
        this.add(center,ctr);
        ctr = new GridBagConstraints();
        ctr.fill = GridBagConstraints.HORIZONTAL;
        ctr.gridx = 2;
        ctr.gridy = 0;
        ctr.weightx = 0.1;
        // ctr.weighty = 0.2;
        this.add(right,ctr);
        int width = ((MainFrame) parent).getInfoLineWidth();
        int height = ((MainFrame) parent).getInfoLineHeight();
        this.setPreferredSize(new Dimension(width,height));
        this.setMinimumSize(new Dimension(width,height));
        this.setMaximumSize(new Dimension(width,height));
    }

    protected void setLeftLabel(String text) {
        left.setText(text);
        left.validate();
    }
    protected void setCenterLabel(String text) {
        center.setText(text);
        center.validate();
    }
    protected void setRightLabel(String text) {
        right.setText(text);
        right.validate();
    }
}
