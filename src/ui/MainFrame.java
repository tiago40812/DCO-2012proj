package ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.drew.imaging.ImageProcessingException;

import domain.TagStatus;

public class MainFrame extends JFrame implements ActionListener, Observer {
	// private File librariesDir;
	// UI elements (panels).
	private ThumbnailsPanel    thumbnailsPanel;
	private FullSizePanel      fullSizePanel;
	private TagCloudPanel      tagCloudPanel;
	private CommandsPanel      commandsPanel;
	private PictureInfoPanel   pictureInfoPanel;
	private InfoLine           infoLine;
	private ArrayList<String>  selectedTags;
	private ArrayList<String>  notSelectedTags;

	// Other UI elements with class visibility.
	private JButton            openButton;
	private JButton            saveButton;
	private final JFileChooser fc          = new JFileChooser();
	private JMenuItem          addToCollectionItem;
	private JMenuItem          addPictureItem;
	private JMenuItem          loadCollectionItem;
	private JMenuItem          saveCollectionAsItem;
	private JMenuItem          saveCollectionItem;
	private JMenuItem          saveAlbumItem;
	private JMenuItem          loadAlbumItem;

	private int                frameWidth  = 1000;
	private int                frameHeight = 660;

	/**
	 * The MainFrame class is responsible for the creation and the communication
	 * between the UI and the domain. The argument is a string that indicates
	 * the default directory for loading and saving collections. If null the
	 * home directory is used.
	 * 
	 */
	public MainFrame() {
		Main.lib.addObserver(this);
	}

	/**
	 * Method responsible for the initialization of the UI.
	 */
	public void initialize() {
		selectedTags = new ArrayList<String>();
		notSelectedTags = new ArrayList<String>();

		//
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		//
		addToCollectionItem = new JMenuItem("Add pictures...", 'A');
		fileMenu.add(addToCollectionItem);
		addToCollectionItem.addActionListener(this);

		addPictureItem = new JMenuItem("Add picture...");
		fileMenu.add(addPictureItem);
		addPictureItem.addActionListener(this);

		saveCollectionItem = new JMenuItem("Save collection");
		fileMenu.add(saveCollectionItem);
		saveCollectionItem.addActionListener(this);

		fileMenu.addSeparator();
		saveAlbumItem = new JMenuItem("Save album...");
		fileMenu.add(saveAlbumItem);
		saveAlbumItem.addActionListener(this);

		loadAlbumItem = new JMenuItem("Load album...");
		fileMenu.add(loadAlbumItem);
		loadAlbumItem.addActionListener(this);

		fileMenu.addSeparator();
		saveCollectionAsItem = new JMenuItem("Export collection...");
		fileMenu.add(saveCollectionAsItem);
		saveCollectionAsItem.addActionListener(this);

		loadCollectionItem = new JMenuItem("Import collection...");
		fileMenu.add(loadCollectionItem);
		loadCollectionItem.addActionListener(this);

		fileMenu.addSeparator();
		JMenuItem quitItem = new JMenuItem("Quit", 'Q');
		fileMenu.add(quitItem);
		quitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainFrame.quitApplication();
			}
		});
		//
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
		//
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		openButton = new JButton("Open a File...");
		openButton.addActionListener(this);
		saveButton = new JButton("Save a File...");
		saveButton.addActionListener(this);

		fullSizePanel = new FullSizePanel(this);
		// fullSizePanel.setBorder(BorderFactory.createLineBorder(Color.CYAN));

		thumbnailsPanel = new ThumbnailsPanel(this);
		// thumbnailsPanel.setBorder(BorderFactory.createLineBorder(Color.RED));

		tagCloudPanel = new TagCloudPanel(this);
		// tagCloudPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));

		commandsPanel = new CommandsPanel(this);
		pictureInfoPanel = new PictureInfoPanel(this);
		infoLine = new InfoLine(this);
		infoLine.setLeftLabel(Main.lib.selectionSize()
				+ " pictures in selection.");

		// infoLine.setBorder(BorderFactory.createLineBorder(Color.GREEN));

		Container pane = getContentPane();

		GridBagLayout mainLayout = new GridBagLayout();
		pane.setLayout(mainLayout);
		// thumbnailsPanel
		GridBagConstraints ctr = new GridBagConstraints();
		ctr.fill = GridBagConstraints.VERTICAL;
		ctr.gridx = 0;
		ctr.gridy = 0;
		// ctr.weightx = 0.2;
		// ctr.weighty = 0.2;
		pane.add(thumbnailsPanel, ctr);
		// tagCloudPanel
		ctr = new GridBagConstraints();
		ctr.fill = GridBagConstraints.VERTICAL;
		ctr.gridx = 0;
		ctr.gridy = 1;
		// ctr.weightx = 0.2;
		ctr.weighty = 0.2;
		pane.add(tagCloudPanel, ctr);

		// commandsPanel
		ctr = new GridBagConstraints();
		ctr.fill = GridBagConstraints.VERTICAL;
		ctr.gridx = 2;
		ctr.gridy = 0;
		// ctr.weightx = 0.2;
		// ctr.weighty = 0.2;
		pane.add(commandsPanel, ctr);
		// infoPanel
		ctr = new GridBagConstraints();
		ctr.fill = GridBagConstraints.VERTICAL;
		ctr.gridx = 2;
		ctr.gridy = 1;
		// ctr.weightx = 0.2;
		// ctr.weighty = 0.2;
		pane.add(pictureInfoPanel, ctr);

		ctr = new GridBagConstraints();
		ctr.fill = GridBagConstraints.HORIZONTAL;
		ctr.gridx = 0;
		ctr.gridy = 3;
		ctr.gridwidth = 3;
		ctr.anchor = GridBagConstraints.SOUTH;
		pane.add(infoLine, ctr);

		ctr = new GridBagConstraints();
		ctr.fill = GridBagConstraints.BOTH;
		ctr.gridx = 1;
		ctr.gridy = 0;
		ctr.weightx = 0.9;
		ctr.weighty = 0.5;
		ctr.gridheight = 3;
		pane.add(fullSizePanel, ctr);
		pane.setMinimumSize(new Dimension(frameWidth, frameHeight));
		pane.setMaximumSize(new Dimension(frameWidth, frameHeight));
		pane.setPreferredSize(new Dimension(frameWidth, frameHeight));

		tagCloudPanel.initialize(Main.lib.getTagsInSelection());
		System.out.println("in MainFrame.initialize tag cloud initialized.");
		thumbnailsPanel.setThumbnails(Main.lib.getSelectedPictures());
		System.out.println("in MainFrame.initialize thumbnails done.");
		// newPictureSelection(Main.lib.getSelectedPictures().get(0));
		// this.setBackground(Color.RED);
		pane.setBackground(Color.BLACK);
		pack();
	}

	// Methods for UI setup.-------------------------------------

	protected int getLeftColumnWidth() {
		return ThumbnailsPanel.getOuterWidth();
	}

	protected int getTagCloudWidth() {
		return getLeftColumnWidth();
	}

	protected int getTagCloudHeight() {
		return (this.getFrameHeight() - getInfoLineHeight()) / 2;
	}

	protected int getThumbnailsHeight() {
		return getTagCloudHeight();
	}

	protected int getThumbnailsWidth() {
		return getLeftColumnWidth();
	}

	protected int getInfoLineHeight() {
		return 27;
	}

	protected int getInfoLineWidth() {
		return this.getFrameWidth();
	}

	// Methods for UI setup.--(end)------------------------------

	/**
	 * This method is called when the user clicks on a tag.
	 * 
	 * @param tag
	 */
	protected void tagClicked(String tag) {
		Main.lib.tagClicked(tag);
	}

	/**
	 * This method is called when the user clicks on a thumbnail.
	 * 
	 * @param filename
	 */
	protected void thumbnailClicked(String filename) {
		System.out.printf("in MainFrame.thumbnailClicked(%s)\n", filename);
		Main.lib.setCurrentPicture(filename);
	}

	public void actionPerformed(ActionEvent e) {
		File userDir = new File(System.getProperty("user.home"));
		if (e.getSource() == addToCollectionItem) {
			// ----- Add a set of pictures to the collection.
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.setCurrentDirectory(userDir);
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				File[] files = file.listFiles(new PictureFilter());
				Main.lib.addPicturesToCollection(files);
			} else {
				System.err.println("Open command cancelled by user.");
			}
		} else if (e.getSource() == addPictureItem) {
			// ----- Add a single picture to the collection.
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fc.setCurrentDirectory(userDir);
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				if (MainFrame.isPictureFile(file)) {
					File[] files = new File[1];
					files[0] = file;
					Main.lib.addPicturesToCollection(files);
				} else {
					System.err.println("File " + file + " is not a jpeg file.");
				}
			} else {
				System.err.println("Open command cancelled by user.");
			}
		} else if (e.getSource() == saveAlbumItem) {
			// ----- Save an album (current selection).
			fc.setCurrentDirectory(userDir);
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int returnVal = fc.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				// This is where a real application would save the file.
				System.err.println("Saving: " + file.getName() + ".");
				try {
					Main.lib.saveAlbum(file);
				} catch (IOException ex) {
					// TODO: add user notification.
					ex.printStackTrace();
				}
			} else {
				System.err.println("Save command cancelled by user.");
			}
		} else if (e.getSource() == loadAlbumItem) {
			// ----- Loads a previously saved album.
			fc.setCurrentDirectory(userDir);
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				System.err.println("Loading: " + file.getName() + ".");
				Main.lib.loadAlbum(file);
			}
		} else if (e.getSource() == saveCollectionItem) {
			try {
				Main.lib.saveCollection();
			} catch (IOException ex) {
				// TODO: add user notification.
				ex.printStackTrace();
			}
		} else if (e.getSource() == saveCollectionAsItem) {
			// ----- Save an collection in a file.
			fc.setCurrentDirectory(userDir);
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int returnVal = fc.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				// This is where a real application would save the file.
				System.err.println("Saving: " + file.getName() + ".");
				try {
					Main.lib.exportCollection(file);
				} catch (IOException ex) {
					// TODO: add user notification.
					ex.printStackTrace();
				}
			} else {
				System.err.println("Save command cancelled by user.");
			}
		} else if (e.getSource() == loadAlbumItem) {
			// ----- Loads a previously saved album.
			fc.setCurrentDirectory(userDir);
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				System.err.println("Loading: " + file.getName() + ".");
				Main.lib.importCollection(file);
			}
		}
	}

	private static boolean isPictureFile(File file) {
		String filename = file.getName();
		int i = filename.lastIndexOf('.');
		// System.out.println("in isPicturefile " + ((i>=1) ?
		// filename.substring(i) : i));
		return (i >= 1 && (filename.substring(i).equals(".jpeg")
				|| filename.substring(i).equals(".jpg")
				|| filename.substring(i).equals(".JPG") 
				|| filename.substring(i).equals(".JPEG")));
	}

	private static void quitApplication() {
		System.exit(0);
	}

	protected InfoLine getInfoLine() {
		return infoLine;
	}

	private void updateInfoLine() {
		boolean saved = Main.lib.isSaved();
		String infoString = (saved ? " -- " : " ** ")
				+ Main.lib.selectionSize() + " pictures in selection (total "
				+ Main.lib.size() + ")";
		infoLine.setLeftLabel(infoString);
	}

	@Override
	public void update(Observable o, Object arg) {
		switch(arg.toString())
		{
		case "setPicture":
			System.out.print("tas aqui");

			fullSizePanel.setPicture(((domain.Library)o).currentPicture,((domain.Library)o).getPictureWidth(),((domain.Library)o).getPictureHeight());
			try {
				this.pictureInfoPanel.setInfo(((domain.Library)o).currentPicture, ((domain.Library)o).getPictureTags(((domain.Library)o).currentPicture));
			} catch (ImageProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			break;
		
		case "addPictures":
			thumbnailsPanel.setThumbnails(Main.lib.getSelectedPictures());
			break;
		
		case "nextPicture":
			fullSizePanel.setPicture(((domain.Library)o).currentPicture,((domain.Library)o).getPictureWidth(),((domain.Library)o).getPictureHeight());
			try {
				this.pictureInfoPanel.setInfo(((domain.Library)o).currentPicture, ((domain.Library)o).getPictureTags(((domain.Library)o).currentPicture));
			} catch (ImageProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		
		case "addTagToPicture":
			System.out.println("Tas aqui");
			Tag.makeTag(((domain.Library)o).tagz, Double.parseDouble("99"));
			this.pictureInfoPanel.setInfo("Mama a gaita", ((domain.Library)o).newtag);

			break;

		}
	}

	protected int getFrameWidth() {
		return frameWidth;
	}

	protected int getFrameHeight() {
		return frameHeight;
	}

	protected void addTag(String tag) {
		Main.lib.addTagToPicture(tag);
	}

	protected void removeTag(String tag) {
		Main.lib.removeTagFromPicture(tag);
	}

	protected void recoverLastTagSelection() {
		Main.lib.recoverLastTagSelection();
	}

	protected TagStatus getTagStatus(String text) {
		return Main.lib.getTagStatus(text);
	}

	protected void nextPicture() {
		Main.lib.nextPicture();
	}

	protected void previousPicture() {
		Main.lib.previousPicture();
	}

	protected void startSlideShow() {
		Main.lib.startSlideShow();
	}

	protected void stopSlideShow() {
		Main.lib.stopSlideShow();
	}

	protected int getPictureWidth() {
		return Main.lib.getPictureWidth();
	}

	protected int getPictureHeight() {
		return Main.lib.getPictureHeight();
	}

	protected String getPictureOrientation() {
		return Main.lib.getPictureOrientation();
	}
}
