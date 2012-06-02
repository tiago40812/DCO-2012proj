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


import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import ui.MainFrame;
import ui.MemoFile;

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
    public boolean slideShow = true;
	private Timer timer ;
 
	public List<String> newtag = new ArrayList();
	public HashMap tagsPicture = new HashMap();
	public HashMap<String, Double> allTags = new HashMap<String, Double>();
	public String tagz = new String();
	public ArrayList<String> collection = new ArrayList<String>();
	public ArrayList<String> selection = new ArrayList<String>();
	public String currentPicture;
	private String nextPicture;
	private String previousPicture;
	private HashMap<String, TagStatus> tag_selected = new HashMap<String, TagStatus>();
	private ArrayList<HashMap<String, TagStatus>> history_tags = new ArrayList<HashMap<String, TagStatus>>();
	
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
				String s = sc.next();
				collection.add(s);
				selection.add(s);
			}

		} catch (FileNotFoundException e) {
			System.err.println("Ficheiro ao encontrado");
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
		for (int i = 0; i < filenames.length; i++) {
			collection.add(filenames[i].toString());
			selection.add(filenames[i].toString());
			newtag = getPictureTags();
		}
		this.setChanged();
		this.notifyObservers("addPictures");
	}

	/**
	 * Adds a tag to the current picture.
	 *
	 * @param tag @requires @ensures
	 *
	 */
	public void addTagToPicture(String tag) {

		this.tagz = tag;
		newtag.add(tag);
		if(tagsPicture.isEmpty())
			tagsPicture.put(currentPicture.toString(), tag);
		else
			tag = (String) tagsPicture.get(currentPicture.toString()).toString()+".7=";

		this.setChanged();
		this.notifyObservers("addTagToPicture");


	}

	/**
	 * Removes the specified tag from the current picture.
	 *
	 * @param tag @requires @ensures if tag is not an EXIF tag, tag is not part
	 * of current picture's tagsPicture.
	 *
	 */
	public void removeTagFromPicture(String tag) {
		this.tagz = tag;
		if(tagsPicture.isEmpty())
			tagsPicture.put(currentPicture.toString(), tag);
		else
			tag = (String) tagsPicture.get(currentPicture.toString()).toString()+".7=";

		this.setChanged();
		this.notifyObservers("addTagToPicture");

	}

	/**
	 * @return current picture's width.
	 */
	public int getPictureWidth() {
		try {
			HashMap<String, String> exif = MyExifReaderAdapter.makeExifReaderAdapter().getExifAttributes(new File(currentPicture));
			if (!exif.isEmpty()) {
				if (exif.containsKey("Exif Image Width")) {
					String[] pixels = exif.get("Exif Image Width").split(" ");
					return Integer.parseInt(pixels[0]);
				} else if (exif.containsKey("Image Width")) {
					String[] pixels = exif.get("Image Width").split(" ");	
					return Integer.parseInt(pixels[0]);
				}
			}
		} 
		catch (IOException e) {
			System.err.println("Nao encontra imagem");
		} 
		return 1;
	}

	/**
	 * @return current picture's height
	 */
	public int getPictureHeight() {
		try {
			HashMap<String, String> exif = MyExifReaderAdapter.makeExifReaderAdapter().getExifAttributes(new File(currentPicture));
			if (!exif.isEmpty()) {
				if (exif.containsKey("Exif Image Height")) {
					String[] pixels = exif.get("Exif Image Height").split(" ");	
					return Integer.parseInt(pixels[0]);
				} else if (exif.containsKey("Image Height")) {
					String[] pixels = exif.get("Image Height").split(" ");	
					return Integer.parseInt(pixels[0]);
				}
			}
		} catch (IOException e) {
			System.err.println("Nao encontra imagem");
		} 
		return 1;
	}

	/**
	 * Returns the orientation of the current picture as defined in the EXIF
	 * metadata.
	 *
	 * @return current picture's orientation
	 * @throws IOException 
	 */
	public String getPictureOrientation()  {
		try {
			HashMap<String, String> exif = MyExifReaderAdapter.makeExifReaderAdapter().getExifAttributes(new File(currentPicture));
			if (!exif.isEmpty()) {
				if (exif.containsKey("Orientation")) {	
					return exif.get("Orientation");
				}
			}
		} catch (IOException e) {
			System.err.println("Nao encontra imagem");
		}
		return "normal)";
	}

	public List<String> getPictureTags(String filename) throws ImageProcessingException, IOException {
		List<String> teste = new ArrayList();
		return MyExifReaderAdapter.makeExifReaderAdapter().getExifTags(new File(filename));
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
		for (int i = 0; i < this.getSelectedPictures().size(); i++) {
			fw.write(this.getSelectedPictures().get(i).toString() + "\n");
		}
		fw.flush();
		fw.close();

		setChanged();
		notifyObservers();
	}

	/**
	 * Initializes the current collection with the contents of file.
	 *
	 * @param file @requires file exits and contains a collection. @ensures the
	 * current collection's filename is file. The contents of the collection
	 * corresponds to the contents of the file.
	 */
	public void importCollection(File file) {
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
		//selection
	}

	/**
	 * Method called when the user selects a tag in the cloud.
	 *
	 * @param tagText
	 */
	public void tagClicked(String tagText) {
		HashMap copyOfOriginal=new HashMap(tag_selected);
		System.out.println("tagClicked = " + tagText);

		history_tags.add(copyOfOriginal);
		if (tag_selected.containsKey(tagText) ){
			if (tag_selected.get(tagText).equals(TagStatus.SELECTED)){
				tag_selected.put(tagText, TagStatus.NOTSELECTED);
			} else {
				tag_selected.put(tagText, TagStatus.SELECTED);
			}
		} else {
			tag_selected.put(tagText, TagStatus.SELECTED);
		}
		getCurrentTagSelection();

		this.setChanged();
		this.notifyObservers("tagSelection");
	}

	/**
	 * Returns the size (number of pictures) of the current selection.
	 *
	 * @return size
	 */
	public int selectionSize() {
		return selection.size();
	}

	/**
	 * Returns the size (number of pictures) of the collection.
	 *
	 * @return size
	 */
	public int size() {
		return collection.size();
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
		try {		
			allTags = new HashMap<String, Double>();
			for (int i = 0; i<collection.size(); i++) {
				List<String> mytags;
				mytags = MyExifReaderAdapter.makeExifReaderAdapter().getExifTags(new File(collection.get(i)));
				for (int j = 0; j<mytags.size(); j++) {
					if (allTags.containsKey(mytags.get(j))){
						if ((double)allTags.get(mytags.get(j)).intValue()<1){
							allTags.put(mytags.get(j), ((double) allTags.get(mytags.get(j)).intValue())+0.1);
						}
					}else{ 
						allTags.put(mytags.get(j), (double) (0));
					}
				}
			}
		} catch (IOException e) {
			System.err.println("ficheiro nao encontrado");
		}
		
		return allTags;
	}

	/**
	 * Returns the list of pictures (filenames) present in the current
	 * selection.
	 *
	 * @return list of pictures
	 */
	public List<String> getSelectedPictures() {
		return selection;
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
		if (history_tags.size()>0)
			tag_selected = history_tags.remove(history_tags.size()-1);
		getCurrentTagSelection();
		this.setChanged();
		this.notifyObservers("tagSelection");
	}

	/**
	 * Returns the status of tag text.
	 *
	 * @param text
	 * @return tag's status.
	 */
	public TagStatus getTagStatus(String text) {
		//System.out.println("string inside getTagStatus = " + text);
		if (tag_selected.containsKey(text)) {
		return tag_selected.get(text);
		}
		return TagStatus.NOTSELECTED;
	}

	/**
	 * Returns a textual representation of the current selection of tags. This
	 * must be a human-readable string similar to "tagi, not(tagj), tagk"
	 *
	 * @return
	 */
	public String getCurrentTagSelection() {

		try {
			selection = new ArrayList<String>();
			for (int i = 0; i<collection.size(); i++) {
			List<String> mytags;
			mytags = MyExifReaderAdapter.makeExifReaderAdapter().getExifTags(new File(collection.get(i)));
			for (int j = 0; j<mytags.size(); j++) {
				if (tag_selected.containsKey(mytags.get(j)) && tag_selected.get(mytags.get(j)).equals(TagStatus.SELECTED))
					if (!selection.contains(collection.get(i))) {
						selection.add(collection.get(i));
					}
			}
			if (!tag_selected.containsValue(TagStatus.SELECTED)) {
				selection = collection;
			}
		}
		} catch (IOException e) {
			System.err.println("ficheiro nao encontrado");
		}
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
		return currentPicture;
	}

	/**
	 * Sets the current picture to the following picture in the current
	 * selection. When the end of the current selection is reached, start again
	 * with the first picture in the selection.
	 */
	public void nextPicture() {
		changePicture(true);
		setCurrentPicture(nextPicture);
		this.setChanged();
		this.notifyObservers("nextPicture");
	}

	/**
	 * Sets the current picture to the previous picture in the current
	 * selection. When the beginning of the current selection is reached, start
	 * again with the last picture in the selection.
	 *
	 */
	public void previousPicture() {
		changePicture(false);
		setCurrentPicture(previousPicture);
		this.setChanged();
		this.notifyObservers("nextPicture");
	}

	/**
	 * Sets the current picture to the picture indicated by the parameter.
	 *
	 * @param picture @requires picture must be a filename of a picture and be
	 * part of the current selection. @ensures
	 * getCurrentPicture().equals(picture)
	 */
	public void setCurrentPicture(String picture) {
		currentPicture = picture;
		this.setChanged();
		this.notifyObservers("setPicture");
	}

	/**
	 * Starts the slide show. If a slide show is already running the call has no
	 * effect.
	 *
	 * @requires @ensures a slide show is running.
	 */
    public void startSlideShow() {
		changePicture(true);
		setCurrentPicture(nextPicture);
		
		this.setChanged();
		this.notifyObservers("startSlideShow");
		
		if (slideShow == true ) {
	        timer = new Timer();
			time();
			slideShow = false;
		}


    }
    
    private void time() {
		int delay = 2000;   // delay for 5 sec.
		int period = 2000;  // repeat every sec.

		timer.scheduleAtFixedRate( new TimerTask() {
	        public void run() {
	        	startSlideShow();
	        }
	    }, delay, period);
    }
	

    /**
     * Stops the slide show.
     *
     * @requires @ensures the slide show stops.
     */
    public void stopSlideShow() {
    	slideShow = true;
    	timer.cancel();
    }
    
	private void changePicture(boolean flag)
	{
		try{
			for(int i=0; i<selection.size();i++)
				if(selection.get(i).equals(currentPicture))
					if(flag)
						nextPicture = selection.get(i+1);
					else 
						previousPicture = selection.get(i-1);
		}
		catch(Exception e)
		{
			if(flag)
				nextPicture = selection.get(0);
			else
				previousPicture = selection.get(selection.size()-1);
		}
	}
}
