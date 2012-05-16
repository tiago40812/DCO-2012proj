package ui;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import domain.Library;

public class Main {

    protected static Library lib;

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        try {
            // Set System L&F
            // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println(e);
        } catch (ClassNotFoundException e) {
            System.err.println(e);// handle exception
        } catch (InstantiationException e) {
            System.err.println(e);// handle exception
        } catch (IllegalAccessException e) {
            System.err.println(e);// handle exception
        }
        // TODO: read the configuration (initial collection, default collection directory etc...) form a local file.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                lib = new Library(new File("collection.dat"));
                MainFrame myMainFrame = new MainFrame(null);
                lib.loadCollection();
                myMainFrame.initialize();
                myMainFrame.setVisible(true);
            }
        });
    }
    
}
