package domain;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IExifReaderAdapter {
/*teste
    /**
     * This method is responsible for extracting and returning a set of relevant tags
     * from the EXIF data. The set of tags that should be returned include (but
     * is not limited to this list) : the date (as two tags: year and month), the
     * "ISO Speed Ratings" (the tag should have the form similar to "isoXXX" where
     * XXX is the value associated with the attribute and the "F-Number"
     * attribute, the tag being similar to "FXX.X" where XX.X is the value of
     * the attribute.
     * 
     * @param jpegFile
     * @return
     * @throws IOException
     */
    public List<String> getExifTags(File jpegFile) throws IOException;

    /**
     * The method returns a Map with key equals to the set of attributes
     * previously selected during the creation of the EXIF reader. The values of the Map are
     * the corresponding values for the picture. 
     * 
     * @requires jpegFile is a file that contains a picture in JPEG format.
     * @ensures the Map that is returned contains the value of the attributes
     *          that were previously selected using selectExifAttributes.
     * @param jpegFile
     * @return
     * @throws IOException
     */
    public Map<String, String> getExifAttributes(File jpegFile) throws IOException;
    
    // -----------------------------------------------------------------
    // The following method specifications are for future extensions of the
    // application and will not be implemented during this iteration.

    /**
     * A jpeg image may contain a large number of EXIF attributes. Not all
     * attributes carry useful information for a given application. This method
     * is used to specify the subset of exif attributes that should be retrieved
     * by successive calls to the getExifAttributes method.
     * 
     * @requires attributes is a List of strings that belong to the set of Exif
     *           attributes.
     * @ensures for any jpegFile, getExifAttributes(jpegFile).keySet() is a
     *          subset of attributes.
     * @param attributes
     * 
     */
    // public void setExifAttributes(List<String> attributes);

    

    /**
     * @return the list of valid Exif attributes.
     */
    // public List<String> getValidExifAttributes();

}
