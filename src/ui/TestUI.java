package ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class TestUI extends JPanel {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("This is a Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panelA = new JPanel();
        panelA.setBorder(BorderFactory.createLineBorder(Color.RED));
        JPanel panelAA = new JPanel();
        JScrollPane scrl = new JScrollPane(panelA);
        
        panelAA.add(scrl);
        
        JLabel labelA = new JLabel("A is above B.");
        // panelA.add(labelA);
        int elementSize = 80;
        int gap = 5;      
        int nElements = 40;
        int nRows = (int) Math.ceil(nElements / 3.0);
        int innerWidth = elementSize * 3 + 4 * gap;
        int innerHeight = nRows * elementSize + (nRows + 1) * gap;
        scrl.setPreferredSize(new Dimension(innerWidth + 4 * gap, elementSize * 4 + 6 * gap));
        panelA.setLayout(new FlowLayout(FlowLayout.LEFT, gap, gap));
        panelA.setMaximumSize(new Dimension(innerWidth, innerHeight));
        panelA.setMinimumSize(new Dimension(innerWidth, innerHeight));
        panelA.setPreferredSize(new Dimension(innerWidth, innerHeight));
        for (int i = 0; i < nElements; i++) {
            JButton b = new JButton("B" + i);
            b.setMinimumSize(new Dimension(elementSize, elementSize));
            b.setMaximumSize(new Dimension(elementSize, elementSize));
            b.setPreferredSize(new Dimension(elementSize, elementSize));
            panelA.add(b);
        }
        JPanel panelB = new JPanel();
        panelB.setPreferredSize(new Dimension(innerWidth + 4 * gap, panelB.getHeight()));
        panelB.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        JLabel labelB = new JLabel("B is below A.");
        panelB.add(labelB);
        for (int i = 0; i < 4; i++) panelB.add(new JLabel("Label" + i));
        
        JPanel panelC = new JPanel();
        panelC.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        JLabel labelC = new JLabel("C is on the right of A and B.");
        panelC.add(labelC);
        
        Container pane = frame.getContentPane();
        GridBagLayout mainLayout = new GridBagLayout();
        pane.setLayout(mainLayout);
        
        GridBagConstraints ctr = new GridBagConstraints();
        ctr.fill = GridBagConstraints.BOTH;
        ctr.gridx = 0;
        ctr.gridy = 0;
        // ctr.weightx = 0.2;
        // ctr.weighty = 0.7;
        pane.add(panelAA,ctr);
        
        ctr = new GridBagConstraints();
        ctr.fill = GridBagConstraints.BOTH;
        ctr.gridx = 0;
        ctr.gridy = 1;
        // ctr.weightx = 0.2;
        // ctr.weighty = 0.2;
        pane.add(panelB,ctr);
        
        ctr = new GridBagConstraints();
        ctr.fill = GridBagConstraints.BOTH;
        ctr.gridx = 1;
        ctr.gridy = 0;
        ctr.weightx = 0.9;
        ctr.weighty = 0.5;
        ctr.gridheight = 2;
        pane.add(panelC,ctr);
        pane.setMinimumSize(new Dimension(900,700));
        pane.setMaximumSize(new Dimension(900,700));
        pane.setPreferredSize(new Dimension(900,700));
        frame.pack();
        frame.setVisible(true);
    }

}
