package domain;

import com.drew.imaging.ImageMetadataReader;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyExifReaderAdapter implements IExifReaderAdapter {


    public static MyExifReaderAdapter makeExifReaderAdapter() {

        return null;
    }

    public Map<String, String> getExifAttributes(File jpegFile) throws IOException {
        Metadata metadata = null;
        Map<String, String> mp = null;
        try {
            metadata = ImageMetadataReader.readMetadata(jpegFile);
        } catch (ImageProcessingException ex) {
        }
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                mp.put(tag.toString().substring(0, tag.toString().indexOf("-") - 1), tag.toString().substring(tag.toString().indexOf("-") + 1, tag.toString().length()));
                System.out.println(mp.toString());
            }

        }
        return mp;
    }

    public List<String> getExifTags(File jpegFile) throws IOException {
     Metadata metadata = null;
        List<String> mp = null;
        try {
            metadata = ImageMetadataReader.readMetadata(jpegFile);
        } catch (ImageProcessingException ex) {
        }
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                mp.add(tag.toString().substring(tag.toString().indexOf("-") + 1, tag.toString().length()));
            }

        }
        return mp;
    }
}
