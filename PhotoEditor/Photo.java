package PhotoEditor;

public class Photo {
	private int[][] data;
	private int width;
	private int height;
	
	public Photo(int width, int height) {
		this(width, height, new int[width][height]);
	}
	
	public Photo(int width, int height, int[][] data) {
		this.data = data;
		this.width = width;
		this.height = height;
	}
	
	public int getPixel(int x, int y) {
		if (x >= 0 && x < width && y >= 0 && y < height)
			return data[x][y];
		PhotoEditor.failure("No pixel at " + x + ", " + y);
		return 0;
	}
	
	public int getRed(int x, int y) {
		return ( getPixel(x, y) >> 16) & 0xff;
	}
	
	public int getGreen(int x, int y) {
		return ( getPixel(x, y) >> 8) & 0xff;
	}
	
	public int getBlue(int x, int y) {
		return getPixel(x, y) & 0xff;
	}
	
	public void setPixel(int x, int y, int value) {
		if (x >= 0 && x < width && y >= 0 && y < height)
			data[x][y] = value; 
		else
			PhotoEditor.failure("No pixel at " + x + ", " + y);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int[][] getData() {
		return data;
	}
}