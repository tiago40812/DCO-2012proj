package ui;

import java.io.File;
import java.io.FilenameFilter;

public class PictureFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".jpg"));
        }
}

