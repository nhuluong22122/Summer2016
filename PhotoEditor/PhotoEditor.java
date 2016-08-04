package PhotoEditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class PhotoEditor {

	static JFrame mainFrame;
	static JFileChooser imageChooser = null;
	static Map <JFrame, BufferedImage> frameMap = new HashMap <JFrame, BufferedImage> ();
	static JFrame backgroundFrame;
	
	static int[][] sourcePixels;
	static JFrame lastSourceFrame;
	
	public static void main(String[] args) {
		loadFiles();
	}
	
	private static void loadFiles() {
		File[] files = new File("images").listFiles();
		for (File f : files) {
			String name = f.getName().toLowerCase();
			if (name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png"))
				openImageFromFile(f.getPath());
		}
	}
	
	private static void createFileChooser() {
		try {
			if(imageChooser == null)
				imageChooser = new JFileChooser();
		} catch (Exception ex) {
			failure("Could not create file chooser (error below).");
			failure(ex.getMessage());
		}
	}

	private static void openImageFromFile(String filename) {
		BufferedImage img = loadImage(filename);
		if (img == null) failure(filename + " could not be opened.");
		else openImage(img, filename);
	}
	
	private static void openImage(BufferedImage image, String title) {
		JFrame frame = new JFrame();
		frame.setLocationByPlatform(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle(title);
		addMenus(frame);
		putFrameImage(frame, image);
		frame.setVisible(true);
	}

	private static void putFrameImage(JFrame frame, BufferedImage image) {
		frameMap.put(frame, image);
		frame.getContentPane().removeAll();
		JLabel label;
		if (image != null)
			label = new JLabel(new ImageIcon(image));
		else
			label = new JLabel("No image");
		frame.getContentPane().add(label, BorderLayout.CENTER);
		frame.pack();
	}
	
	public static void addMenus(final JFrame frame) {
		// Run a specific effect.
		ActionListener effectAction = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				String cmd = ((JMenuItem) (ae.getSource())).getText();
				BufferedImage sourceImage = frameMap.get(frame);
				
				sourcePixels = getRGBPixels(sourceImage);
				lastSourceFrame = frame;

				int[][] background = getRGBPixels(frameMap.get(backgroundFrame));
				if (background == null) {
					background = new int[sourcePixels.length][sourcePixels[0].length];
					warning("No background image selected.");
				}
				loading("Running effect " + cmd);
				Photo result = PhotoEffect.process(cmd, 
						new Photo(sourcePixels.length, sourcePixels[0].length, sourcePixels), 
						new Photo(background.length, background[0].length, background));
				//int[][] result = Effects.process(cmd, sourcePixels, background);
				if (result == null)
					failure("No image returned from effect.");
				else {
					success("Successfully ran effect " + cmd);
					sourceImage = setRGBPixels(sourceImage, result.getData());
					putFrameImage(frame, sourceImage);
				}
			}
		};
		
		
		JMenuBar menubar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		JMenu backgroundMenu = new JMenu("Background");
		menubar.add(fileMenu);
		menubar.add(editMenu);
		menubar.add(backgroundMenu);
		
		JMenuItem openItem = new JMenuItem("Open");
		JMenuItem saveAsItem = new JMenuItem("SaveAs");
		JMenuItem quitItem = new JMenuItem("Exit");
		JMenuItem backgroundItem = new JMenuItem("Select this");
		JMenuItem backgroundClearItem = new JMenuItem("Clear");
		JMenuItem undoItem = new JMenuItem("Undo");
		JMenuItem copyItem = new JMenuItem("Copy");
		JMenuItem pasteItem = new JMenuItem("Paste");
		JMenuItem captureScreenItem = new JMenuItem("Capture Screen");

		fileMenu.add(openItem);
		fileMenu.add(saveAsItem);
		fileMenu.add(quitItem);
		editMenu.add(copyItem);
		editMenu.add(pasteItem);
		editMenu.add(captureScreenItem);
		editMenu.add(undoItem);
		backgroundMenu.add(backgroundItem);
		backgroundMenu.add(backgroundClearItem);

		String[][] menuData = PhotoEffect.getMenu();
		for (String[] menu : menuData) {
			if (menu != null && menu.length > 0) {
				JMenu m = new JMenu(menu[0]);
				for (int i = 1 ; i < menu.length ; i++) {
					JMenuItem mi = new JMenuItem(menu[i]);
					mi.addActionListener(effectAction);
					m.add(mi);
				}
				menubar.add(m);
			}
		}
		
		quitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frame.dispose();
				System.exit(0);
			}
		});
		openItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createFileChooser();
				if (imageChooser != null && imageChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
					openImageFromFile(imageChooser.getSelectedFile().getPath());
			}
		});
		captureScreenItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BufferedImage img = captureScreen();
				if (img != null)
					openImage(img, "screen-capture");
			}
		});

		saveAsItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				createFileChooser();
				if (imageChooser != null && imageChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION)
					try {
						File file = imageChooser.getSelectedFile();
						if (!file.exists())
							saveImage(frameMap.get(frame), file);
						else
							failure("Can't overwrite an existing file.");
					} catch (IOException e) {
						failure("IO exception.");
						e.printStackTrace();
					}
			}
		});

		backgroundItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backgroundFrame = frame;
			}
		});
		backgroundClearItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backgroundFrame = null;
			}
		});

		undoItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lastSourceFrame != null && sourcePixels != null) {
					BufferedImage sourceImage = frameMap.get(lastSourceFrame);
					int[][] tmp = getRGBPixels(sourceImage);
					sourceImage = setRGBPixels(sourceImage, sourcePixels);
					putFrameImage(lastSourceFrame, sourceImage);
					sourcePixels = tmp;
				}
			}
		});
		copyItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setClipboardImage(frameMap.get(frame));
			}
		});

		pasteItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BufferedImage image = getClipboardImage();
				if (image != null)
					openImage(image, "");
			}
		});
		frame.setJMenuBar(menubar);
		frame.pack();
	}
	
	public static BufferedImage loadImage(String filename) {
		try {
			loading(filename);
			return ImageIO.read(PhotoEditor.class.getResourceAsStream(filename));
		} catch (Exception e) { }
		try {
			return ImageIO.read(new FileInputStream(filename));
		} catch (Exception e) {
			failure(filename + " not found.");
			e.printStackTrace();
			return null;
		}
	}

	public static void saveImage(BufferedImage img, File file)
			throws IOException {
		if (img == null)
			failure("Null image provided.");
		else {
			String format = file.getPath().toLowerCase();
			int lastIndex = format.lastIndexOf('.');
			if (lastIndex >= 0)
				format = format.substring(lastIndex + 1);
			if (format.equals("jpg"))
				format = "jpeg";
			loading("Writing " + format + " at " + file.getPath());
			ImageIO.write(img, format, file);
			success("Write completed.");
		}
	}

	/**
	 * Sets the pixels of the image using the RGB 2D array.
	 */
	public static BufferedImage setRGBPixels(BufferedImage img, int[][] pixels) {
		// assumes width >0
		if (pixels == null) {
			failure("Your pixel array is null!");
			return img;
		}
		int width = pixels.length, height = pixels[0].length;
		BufferedImage target = (img != null && 
				img.getWidth() == pixels.length && 
				img.getHeight() == pixels[0].length) ? img : new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				target.setRGB(i, j, pixels[i][j]);
		return target;
	}

	/**
	 * Gets the BufferedImage as a 2D array of RGB pixel values.
	 */
	public static int[][] getRGBPixels(BufferedImage img) {
		if (img == null)
			return null;
		int[][] result = null;
		try {
			PixelGrabber g = new PixelGrabber(img, 0, 0, -1, -1, true);
			g.grabPixels();
			int[] pixels = (int[]) g.getPixels();

			int w = g.getWidth(), h = g.getHeight();
			result = new int[w][h];

			for (int j = 0, count = 0; j < h; j++)
				for (int i = 0; i < w; i++)
					result[i][j] = pixels[count++];

			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static void setClipboardImage(Image image) {
		if (image == null)
			return;
		final BufferedImage bImage = toBufferedImage(image);
		Transferable t = new Transferable() {
			// Returns supported flavors
			public DataFlavor[] getTransferDataFlavors() {
				return new DataFlavor[] { DataFlavor.imageFlavor };
			}

			// Returns true if flavor is supported
			public boolean isDataFlavorSupported(DataFlavor flavor) {
				return DataFlavor.imageFlavor.equals(flavor);
			}

			public Object getTransferData(DataFlavor flavor)
					throws UnsupportedFlavorException, IOException {
				if (!DataFlavor.imageFlavor.equals(flavor)) {
					throw new UnsupportedFlavorException(flavor);
				}
				return bImage;
			}
		};
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(t, null);
	}
	
	public static BufferedImage toBufferedImage(Image src) {
		BufferedImage result = new BufferedImage(src.getWidth(null), src
				.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics g = result.getGraphics();
		g.drawImage(src, 0, 0, null);
		g.dispose();
		return result;
	}
	
	public static BufferedImage getClipboardImage() {
		Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard()
				.getContents(null);

		try {
			if (t != null && t.isDataFlavorSupported(DataFlavor.imageFlavor)) {
				BufferedImage img = (BufferedImage) t
						.getTransferData(DataFlavor.imageFlavor);
				return img;
			}
		} catch (Exception e) {
			JOptionPane.showConfirmDialog(null, "Failed:" + e.getMessage());
		}
		return null;
	}
	
	public static BufferedImage captureScreen() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		Rectangle screenRect = new Rectangle(screenSize);
		Robot robot;
		try {
			robot = new Robot();
			Thread.sleep(2000);
			return robot.createScreenCapture(screenRect);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void success(String message) { System.out.println("[ SUCCESS ] " + message); }
	public static void failure(String message) { System.out.println("[ FAILURE ] " + message); }
	public static void warning(String message) { System.out.println("[ WARNING ] " + message); }
	public static void loading(String message) { System.out.println("[ LOADING ] " + message); }
}
