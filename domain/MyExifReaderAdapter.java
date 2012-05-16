package domain;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MyExifReaderAdapter implements IExifReaderAdapter {
    
    // TODO: This class must implement the singleton pattern.

    public static MyExifReaderAdapter makeExifReaderAdapter() {
        
        return null;
    }

    public Map<String, String> getExifAttributes(File jpegFile)
            throws IOException {
        
        return null;
    }

    public List<String> getExifTags(File jpegFile) throws IOException {
        
        return null;
    }

    
}
