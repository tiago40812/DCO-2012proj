package domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import ui.MainFrame;

/**
 * The Library class is the main class in the domain package. It is responsible
 * for the communication between user interfaces (graphical or not) and the
 * domain. Among the entities manipulated in (or through) this class, there is:
 * A collection of pictures, a current selection (a subset of the collection), a
 * current picture. Specific information regarding this picture may be obtained
 * and tags may be assigned or removed from it.
 *
 * @author tl
 *
 */
public class Library extends Observable {

   ArrayList<String> pictures = new ArrayList<String>();
   
    
    /**
     * Creates a picture library from the the contents of filename.
     *
     * @param filename
     */
    public Library(File filename) {
        
        //pictures = filename.listFiles();
try {
       Scanner sc = new Scanner(filename);

        while (sc.hasNext()) {
            pictures.add(sc.next());
        }

     } catch (FileNotFoundException e){

     }   
    }

    /**
     * Add pictures to the current collection. The new pictures are also added
     * to the current selection.
     *
     * @param filenames @requires The file names exist and contain pictures.
     * @ensures the new pictures are added to the collection and to the current
     * selection.
     *
     */
    public void addPicturesToCollection(File[] filenames) {
    }

    /**
     * Adds a tag to the current picture.
     *
     * @param tag @requires @ensures
     *
     */
    public void addTagToPicture(String tag) {
    }

    /**
     * Removes the specified tag from the current picture.
     *
     * @param tag @requires @ensures if tag is not an EXIF tag, tag is not part
     * of current picture's tags.
     *
     */
    public void removeTagFromPicture(String tag) {
    }

    /**
     * @return current picture's width.
     */
    public int getPictureWidth() {

        return 1;
    }

    /**
     * @return current picture's height
     */
    public int getPictureHeight() {
        return 1;
    }

    /**
     * Returns the orientation of the current picture as defined in the EXIF
     * metadata.
     *
     * @return current picture's orientation
     */
    public String getPictureOrientation() {
        return "";
    }

    public List<String> getPictureTags(String filename) {
        return null;
    }

    /**
     * @return the set of tags associated to the current picture.
     */
    public List<String> getPictureTags() {
        return null;
    }

    /**
     * Saves the current collection.
     *
     * @throws IOException
     */
    public void saveCollection() throws IOException {
    }

    /**
     * Loads the contents of the collection.
     *
     * @requires this has a filename that contains a description of a
     * collection. @ensures the previous collection is lost and replaced by the
     * collection described in filename.
     */
    public void loadCollection() {
        
    }

    /**
     * Saves the collection in a file.
     *
     * @param filename
     * @throws IOException @requires @ensures the current collection's name is
     * filename.
     */
    public void exportCollection(File filename) throws IOException {
        FileWriter fw = new FileWriter(filename);  
        for(int i=0;i<this.getSelectedPictures().size();i++)
            fw.write(this.getSelectedPictures().get(i).toString() + "\n");
        fw.flush();
        fw.close();
    }

    /**
     * Initializes the current collection with the contents of file.
     *
     * @param file @requires file exits and contains a collection. @ensures the
     * current collection's filename is file. The contents of the collection
     * corresponds to the contents of the file.
     */
    public void importCollection(File file) throws FileNotFoundException {        
    }

    /**
     * Saves the current selection in a file. If no subset of pictures was
     * selected, saves the whole collection.
     *
     * @param destination
     * @throws IOException
     */
    public void saveAlbum(File destination) throws IOException {
    }

    /**
     * Loads an album from a file.
     *
     * @param album @requires The album file contains a description of a set of
     * pictures as saved using saveAlbum(File destination). @ensures The set of
     * pictures contained in the album becomes the current the selection.
     */
    public void loadAlbum(File album) {
    }

    private void actualizeCurrentSelection() {
    }

    /**
     * Method called when the user selects a tag in the cloud.
     *
     * @param tagText
     */
    public void tagClicked(String tagText) {
    }

    /**
     * Returns the size (number of pictures) of the current selection.
     *
     * @return size
     */
    public int selectionSize() {
        return 0;
    }

    /**
     * Returns the size (number of pictures) of the collection.
     *
     * @return size
     */
    public int size() {
        return 0;
    }

    /**
     * Returns true if the collection in memory corresponds to the collection
     * described in its file. If not returns false.
     *
     * @return
     */
    public boolean isSaved() {
        return false;
    }

    /**
     * Returns the set of tags present in the current selection along with the
     * relative frequency of tags.
     *
     * @return The result is returned as a Map with keys corresponding to tags
     * and values to frequencies. @ensures The sum of map's values are equal to
     * one.
     *
     */
    public Map<String, Double> getTagsInSelection() {
        return new HashMap<String, Double>();
    }

    /**
     * Returns the list of pictures (filenames) present in the current
     * selection.
     *
     * @return list of pictures
     */
    public List<String> getSelectedPictures() {
//        String[] strPictures = new String[pictures.length];     
        
//        for(int i=0; i < pictures.length;i++)
//                    System.out.println(pictures[i] + "\nasas");
//        
//                for(int i=0; i < pictures.length;i++){
                    //strPictures[i] = pictures[i].toString();
//                    list.add(pictures[i].toString());
//                }
                
       // ArrayList<String> list = (ArrayList<String>) Arrays.asList(strPictures);
        //return new ArrayList<String>();
        return pictures;
    }

    /**
     * Changes the filename of the collection.
     *
     * @param filename
     */
    public void setCollectionFilename(File filename) {
    }

    /**
     * Replaces the current selection of tags by the previous selection of tags.
     */
    public void recoverLastTagSelection() {
    }

    /**
     * Returns the status of tag text.
     *
     * @param text
     * @return tag's status.
     */
    public TagStatus getTagStatus(String text) {
        return null;
    }

    /**
     * Returns a textual representation of the current selection of tags. This
     * must be a human-readable string similar to "tagi, not(tagj), tagk"
     *
     * @return
     */
    public String getCurrentTagSelection() {
        return null;
    }

    /**
     * Returns a textual representation of the last selection of tags. This must
     * be a human-readable string similar to "tagi, not(tagj), tagk"
     *
     * @return
     */
    public String getLastTagSelection() {
        return null;
    }

    /**
     * Returns a filename (string) that corresponds to the current picture.
     *
     * @return
     */
    public String getCurrentPictureFilename() {
        return null;
    }

    /**
     * Sets the current picture to the following picture in the current
     * selection. When the end of the current selection is reached, start again
     * with the first picture in the selection.
     */
    public void nextPicture() {
        
    }

    /**
     * Sets the current picture to the previous picture in the current
     * selection. When the beginning of the current selection is reached, start
     * again with the last picture in the selection.
     *
     */
    public void previousPicture() {
    }

    /**
     * Sets the current picture to the picture indicated by the parameter.
     *
     * @param picture @requires picture must be a filename of a picture and be
     * part of the current selection. @ensures
     * getCurrentPicture().equals(picture)
     */
    public void setCurrentPicture(String picture) {
    }

    /**
     * Starts the slide show. If a slide show is already running the call has no
     * effect.
     *
     * @requires @ensures a slide show is running.
     */
    public void startSlideShow() {
    }

    /**
     * Stops the slide show.
     *
     * @requires @ensures the slide show stops.
     */
    public void stopSlideShow() {
        
    }
}
