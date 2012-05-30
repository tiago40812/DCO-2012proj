package domain;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.drew.imaging.ImageProcessingException;

public class MyExifReaderAdapter implements IExifReaderAdapter {

    private static MyExifReaderAdapter theOne = null;
    private static ArrayList<String>   validAttributes;
    // private List<String>               selectedAttributes;

    private MyExifReaderAdapter() {

    }

    public static MyExifReaderAdapter makeExifReaderAdapter() {
        if (theOne == null) {
            theOne = new MyExifReaderAdapter();
            validAttributes = new ArrayList<String>();
            // validAttributes.add("Exposure Time");
            validAttributes.add("F-Number");
            validAttributes.add("Date/Time Original");
            validAttributes.add("Shutter Speed Value");
            validAttributes.add("Aperture Value");
            validAttributes.add("Metering Mode");
            validAttributes.add("Flash");
            validAttributes.add("Focal Length");
            validAttributes.add("Exif Image Width");
            validAttributes.add("Exif Image Height");
            validAttributes.add("Exposure Mode");
            validAttributes.add("White Balance Mode");
            validAttributes.add("Model");
            validAttributes.add("ISO Speed Ratings");
            validAttributes.add("Orientation");
            // validAttributes.add("Exposure Time");
            // validAttributes.add("Base ISO");
            // validAttributes.add("Focal Length");
            return theOne;
        } else {
            return theOne;
        }
    }

    public HashMap<String, String> getExifAttributes(File jpegFile)
            throws IOException {
        System.out.println("in MyExifReaderAdapter.getExifAttributes File "
                + jpegFile);
        HashMap<String, String> attrValuePairs = new HashMap<String, String>();
        try {
            com.drew.metadata.Metadata metadata = com.drew.imaging.ImageMetadataReader
                    .readMetadata(jpegFile);
            for (com.drew.metadata.Directory directory : metadata
                    .getDirectories()) {
                for (com.drew.metadata.Tag tag : directory.getTags()) {
                    System.out.println("Name(" + tag.getTagName() + ") desc("
                            + tag.getDescription() + ")");
                   // if (validAttributes.contains(tag.getTagName()))
                        attrValuePairs.put(tag.getTagName(), tag
                                .getDescription());
                }
            }
        } catch (ImageProcessingException e) {
            e.printStackTrace();
        } /*
           * catch (IOException e) { System.err.println("File: [" + jpegFile +
           * "]"); e.printStackTrace(); }
           */
        return attrValuePairs;
    }

    /*
     * public List<String> getValidExifAttributes() { return validAttributes; }
     */

    public List<String> getExifTags(File jpegFile) throws IOException {
        HashMap<String, String> attrValuePairs = getExifAttributes(jpegFile);
        ArrayList<String> theTags = new ArrayList<String>();
        // Name(Date/Time Original) desc(2010:08:01 20:55:22)
        String date[] = attrValuePairs.get("Date/Time Original").split(":");
        theTags.add(date[0]);
        theTags.add(numToMonth(date[1]));
        String isr = attrValuePairs.get("ISO Speed Ratings");
        if (isr != null)
            theTags.add("#ISO" + attrValuePairs.get("ISO Speed Ratings"));
        // // theTags.add("#ET" + attrValuePairs.get("Exposure Time"));
        // // theTags.add("#FL" + attrValuePairs.get("Focal Length"));
        theTags.add("#" + attrValuePairs.get("F-Number"));
        String ssv = null; // attrValuePairs.get("Shutter Speed Value");
        if (ssv != null) {
            ssv = ssv.split(" ")[0];
            theTags.add("#" + ssv);
        }
        // theTags.add("AISO" + attrValuePairs.get("Auto ISO"));
        return theTags;
    }

    private String numToMonth(String month) {
        if (month.equals("01")) return "January";
        if (month.equals("02")) return "February";
        if (month.equals("03")) return "March";
        if (month.equals("04")) return "April";
        if (month.equals("05")) return "May";
        if (month.equals("06")) return "June";
        if (month.equals("07")) return "July";
        if (month.equals("08")) return "August";
        if (month.equals("09")) return "September";
        if (month.equals("10")) return "October";
        if (month.equals("11")) return "November";
        if (month.equals("12")) return "December";
        return null;
    }
    /*
     * public void setExifAttributes(List<String> attributes) {
     * this.selectedAttributes = attributes; }
     */
}
