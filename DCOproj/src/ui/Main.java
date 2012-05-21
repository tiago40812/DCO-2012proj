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
        File imagetaggerDir = new File(System.getProperty("user.home")
                + "/.imagetagger/");
        imagetaggerDir.mkdir();
        File thumbnailsDir = new File(System.getProperty("user.home")
                + "/.imagetagger/thumbnails/");
        thumbnailsDir.mkdir();
        try {
            // Set System L&F
            // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.setLookAndFeel(UIManager
                    .getCrossPlatformLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println(e);
        } catch (ClassNotFoundException e) {
            System.err.println(e);// handle exception
        } catch (InstantiationException e) {
            System.err.println(e);// handle exception
        } catch (IllegalAccessException e) {
            System.err.println(e);// handle exception
        }

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                File thumbnailsDir = new File("thumbnails");
                thumbnailsDir.mkdir();
                // Na versão para o projecto os ficheiros são guardados na pasta do projecto.
                // File theCollectionDir = 
                        // new File(System
                        // .getProperty("user.home") + "/.imagetagger/");
                // theCollectionDir.mkdirs();
                lib = new Library(/* new File(theCollectionDir.getName() + "collection.dat" */ 
                                  new File("collection.dat"));
                MainFrame myMainFrame = new MainFrame();
                lib.loadCollection();
                myMainFrame.initialize();
                myMainFrame.setVisible(true);
            }
        });
    }

}
