package PhotoEditor;

import gravity.Window;

public class PhotoEffect {
	
	/**
	 * Resize the array image to the new width and height
	 * You are going to need to figure out how to map between a pixel
	 * in the destination image to a pixel in the source image.
	 */
	public static Photo resize(Photo image, int newWidth, int newHeight) {
		Photo newImage = new Photo(newWidth, newHeight);
		
		for (int x = 0 ; x < newWidth ; x++) {
			for (int y = 0 ; y < newHeight ; y++) {
				int newPixel = image.getPixel(x * image.getWidth() / newWidth, y * image.getHeight() / newHeight);
				newImage.setPixel(x, y, newPixel);
			}
		}
		return newImage;
	}
	
	/**
	 * Halfs the width and height of the input image.
	 */
	public static Photo half(Photo image) {
		return resize(image, image.getWidth() / 2, image.getHeight() / 2);
	}

	/**
	 * Resizes the input image to the same size as the reference image.
	 */
	public static Photo resize(Photo image, Photo reference) {
		return resize(image, reference.getWidth(), reference.getHeight());
	}

	/**
	 * Make a copy of the input image.
	 */
	public static Photo copy(Photo image) {
		return resize(image, image.getWidth(), image.getHeight());
	}
	
	/**
	 * An example filter. Modify the code in the specified area to change how it works. 
	 * @param image
	 * @return
	 */
	public static Photo exampleFilter(Photo image) {
		for (int x = 0 ; x < image.getWidth() ; x = x + 1) {
			for (int y = 0 ; y < image.getHeight() ; y = y + 1) {
				// Modify code here
				// ----------------
				int red = image.getRed(x, y) * 40 / 50;
				int green = image.getGreen(x, y) * 10 / 50;
				int blue = image.getBlue(x, y);
				
				// The createPixel method takes in red, green, and blue as an input, and returns a
				// corresponding pixel.
				int pixel = createPixel(red, green, blue);
				
				image.setPixel(x, y, pixel);
				// ----------------
			}
		}
		return image;
	}
	
	
	/**
	 *	Add some funk to the image.
	 */
	public static Photo funky(Photo image, Photo background) {
		for (int x = 0 ; x < image.getWidth() ; x = x + 1) {
			for (int y = 0 ; y < image.getHeight() ; y = y + 1) {
				// Modify code here
				// ----------------
				int red = image.getRed(x, y) * 2 / 3 +Window.random(-20, 20);
				int green = image.getGreen(x, y) * 2 / 3 + Window.random(-20, 20);
				int blue = image.getBlue(x, y) * 2 / 3 + Window.random(-20, 20);
				
				// The createPixel method takes in red, green, and blue as an input, and returns a
				// corresponding pixel.
				int pixel = createPixel(red, green, blue);
				
				image.setPixel(x, y, pixel);
				// ----------------
			}
		}
		return image;
	}
	
    /**
     * Convert the image to grayscale. This is done by going to every pixel and taking the
     * average of R, G, and B, and setting each of R, G, and B to the average.
     */
	public static Photo grayscale(Photo image) {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				int red = image.getRed(x, y);
				int blue = image.getBlue(x, y);
				int green = image.getGreen(x, y);
				
				int average = (red + green + blue) / 3;
				
				int pixel= createPixel(average, average, average);
				
				image.setPixel(x , y, pixel);
				
			
			}
		}
		return image;
	}
	
	/**
	 * To brighten, add to the R, G, and B values for each pixel.
	 */
	public static Photo brighten(Photo image) {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				int red = image.getRed(x, y);
				int green = image.getGreen(x, y);
				int blue = image.getBlue(x, y);

				red = red + 10;
				blue = blue + 10;
				green = green + 10;
				//We want to increase red/blue/green because max for white is 255
				if (red > 255) {
					red = 255;
				}
				if (green > 255) {
					green = 255;
				}
				if (blue > 255) {
					blue = 255;
				}
				int pixel = createPixel(red, green, blue);
				image.setPixel(x, y, pixel);
			}
		}
		return image;
	}
	
	/**
	 * To darken, subtract from the R, G, and B values for each pixel.
	 */
	public static Photo darken(Photo image) {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				int red = image.getRed(x, y);
				int green = image.getGreen(x, y);
				int blue = image.getBlue(x, y);

				red = red - 10;
				blue = blue - 10;
				green = green - 10;

				if (red < 0) {
					red = 0;
				}
				if (green < 0) {
					green = 0;
				}
				if (blue < 0) {
					blue = 0;
				}
				int pixel = createPixel(red, green, blue);
				image.setPixel(x, y, pixel);
			}
		}

		return image;
	}
	
	/**
	 *	Flips the image over the x-axis.
	 */
	public static Photo flip(Photo image) {
		//System.out.println(image.getWidth() + " " + image.getHeight());
		for (int x = 0; x < image.getWidth(); x++) { 
			for (int y = 0; y < image.getHeight() / 2; y++) {
				//Go through the pixels but height / 2 

				int swapy = image.getHeight() - 1 - y;//Because the last index we can go is 1 above the middle
				//x - axis

				int mypixel = image.getPixel(x, y);//Get current pixel
				int swappixel = image.getPixel(x, swapy);//Get pixel at the swap

				image.setPixel(x, y, swappixel);//Set current to swapped
				image.setPixel(x, swapy, mypixel);//Set swapped position to current
			}
		}
		return image;
	}

	/**
	 *	Mirrors the image over the y-axis.
	 */
	public static Photo mirror(Photo image) {
		for (int x = 0; x < image.getWidth() / 2; x++) {
			for (int y = 0; y < image.getHeight(); y++) {

				int swapx = image.getWidth() - 1 - x;

				// int myred = image.getRed(x, y);
				// int mygreen = image.getGreen(x, y);
				// int myblue = image.getBlue(x, y);
				//
				// int swapred = image.getRed(swapx, y);
				// int swapgreen = image.getGreen(swapx, y);
				// int swapblue = image.getBlue(swapx, y);

				int mypixel = image.getPixel(x, y); // createPixel(myred,
													// mygreen, myblue);
				int swappixel = image.getPixel(swapx, y);// createPixel(swapred,
															// swapgreen,
															// swapblue);

				image.setPixel(x, y, swappixel);
				image.setPixel(swapx, y, mypixel);

			}
		}
		return image;
	}

	/**
	 *	Rotates the image left.
	 */
	public static Photo rotateLeft(Photo image) {
		Photo newImage = new Photo(image.getHeight(), image.getWidth());//change the width and height of the photo
		//System.out.println(image.getHeight() + " "  + image.getWidth());
		for (int x = 0 ; x < image.getWidth() ; x++) {
			for (int y = 0 ; y < image.getHeight() ; y++) {
				int newx = y;
				int newy = image.getWidth() - x - 1; //Because the max we can go is 319 
				int pixel = image.getPixel(x, y);
				newImage.setPixel(newx, newy, pixel);
			}
		}
		return newImage;
	}

	/**
	 *	Rotates the image right.
	 */
	public static Photo rotateRight(Photo image) {
		// Let me save you some time.
		return rotateLeft(rotateLeft(rotateLeft(image)));
	}

	/** 
	 * Merge the red,blue,green components from two images.
	 */
	public static Photo merge(Photo front, Photo back) {
		back = resize(back, front);//Set the size of background to the front

		for (int x = 0; x < front.getWidth(); x++) {
			for (int y = 0; y < front.getHeight(); y++) {//Go through the pixels

				int fred = front.getRed(x, y);
				int fblue = front.getBlue(x, y);
				int fgreen = front.getGreen(x, y);

				int bred = back.getRed(x, y);
				int bblue = back.getBlue(x, y);
				int bgreen = back.getGreen(x, y);

				int ared = (fred + bred) / 2;
				int ablue = (fblue + bblue) / 2;
				int agreen = (fgreen + bgreen) / 2;

				int pixel = createPixel(ared, ablue, agreen);
				front.setPixel(x, y, pixel);
			}
		}
		return front;
	}

	/**
	 * Replace the green areas of the foreground image with parts of the back
	 * image.
	 */
	public static Photo chromaKey(Photo front, Photo back) {
		// replace front pixel with back pixel if front pixel is green screen
		back = resize(back, front);
		for (int x = 0; x < front.getWidth(); x++) {
			for (int y = 0; y < front.getHeight(); y++) {
				//Go through every pixel in the array
				int red = front.getRed(x, y); 
				int green = front.getGreen(x, y);
				int blue = front.getBlue(x, y);

				// check if green screen
				if (green > 200 && red < green && blue < green) {// more green than any other color
					int pixel = back.getPixel(x, y);
					front.setPixel(x, y, pixel);
				}
			}
		}
		return front;
	}
	
	public static Photo process(String cmd, Photo source, Photo background) {
		try {
			if (cmd.equals("Half")) {			return PhotoEffect.half(source); 				}
			if (cmd.equals("Rotate")) {			return PhotoEffect.rotateLeft(source);			}
			if (cmd.equals("Flip")) {			return PhotoEffect.flip(source);				}
			if (cmd.equals("Mirror")) {			return PhotoEffect.mirror(source);				}
			if (cmd.equals("Example")) {		return PhotoEffect.exampleFilter(source);		}
			if (cmd.equals("Funky")) {			return PhotoEffect.funky(source,background);	}
			if (cmd.equals("Grayscale")) {		return PhotoEffect.grayscale(source);			}
			if (cmd.equals("Brighten")) {		return PhotoEffect.brighten(source);			}
			if (cmd.equals("Darken")) {			return PhotoEffect.darken(source);				}
			if (cmd.equals("Size To")) {		return PhotoEffect.resize(source, background);  }
			if (cmd.equals("Merge")) {			return PhotoEffect.merge(source, background);	 }
			if (cmd.equals("Chromakey")) {		return PhotoEffect.chromaKey(source, background);}
			if (cmd.equals("Vignette")) {		return PhotoEffect.vignette(source);}
			if (cmd.equals("Blur")) {		return PhotoEffect.blur(source);}
			if (cmd.equals("Warhol")) { 	return PhotoEffect.warhol(source);}
			if (cmd.equals("Pixelate")) {	return PhotoEffect.pixelate(source);}
			if (cmd.equals("Megaman")) { 	return PhotoEffect.megaman(source);}
			if (cmd.equals("Link")) { return PhotoEffect.link(source);}
			if (cmd.equals("Tswizzle")) { return PhotoEffect.tswizzle(source);}
		}
		catch (ArrayIndexOutOfBoundsException e) {
			PhotoEditor.failure("Array index went out of bounds.");
			PhotoEditor.failure(e.getMessage());
			return null;
		}
		return source;
	}
	private static Photo tswizzle(Photo image) {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				int red = image.getRed(x, y);
				int green = image.getGreen(x, y);
				int blue = image.getBlue(x, y);

				if (!(red > 200 && red > blue && red > green)) {
					int average = (red + blue + green) / 3;
					int pixel = createPixel(average, average, average);
					image.setPixel(x, y, pixel);
				}
			}
		}
		return image;
	}

	/**
	 * Make the area in ChromeKey grayscale except the green part
	 * @param image
	 * @return
	 */
	private static Photo link(Photo image) {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				int red = image.getRed(x, y);
				int green = image.getGreen(x, y);
				int blue = image.getBlue(x, y);

				if (!(green > 130 && green > blue && green > red)) {
					int average = (red + blue + green) / 3;
					int pixel = createPixel(average, average, average);
					image.setPixel(x, y, pixel);
				}
			}
		}
		return image;
	}

	private static Photo megaman(Photo image) {
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				int red = image.getRed(x, y);
				int green = image.getGreen(x, y);
				int blue = image.getBlue(x, y);
				//Select everything else that is not green
				//Because once we select all the green pixel and call not
				if (!(green > 20 && green > blue && green > red)) {
					int average = (red + blue + green) / 3;
					int pixel = createPixel(average, average, average);
					image.setPixel(x, y, pixel);
				}
			}
		}
		return image;
	}

	public static Photo pixelate(Photo image) {
		int size = 5;
		// visit every size pixels
		for (int x = 0; x < image.getWidth(); x += size) { // Every other pixels depending on the size
			for (int y = 0; y < image.getHeight(); y += size) {

				int red = 0;
				int green = 0;
				int blue = 0;
				int count = 0;
				// average all the pixels in the square
				for (int px = x; px < x + size; px++) {
					for (int py = y; py < y + size; py++) {
						if (px >= 0 & px < image.getWidth() && py >= 0
								&& py < image.getHeight()) {
							red += image.getRed(px, py);
							green += image.getGreen(px, py);
							blue += image.getBlue(px, py);
							count++;
						}
					}
				}
				// Calculate the average
				red = red / count;
				blue = blue / count;
				green = green / count;
				int pixel = createPixel(red, green, blue);
				// apply the average to all the pixels in the square
				for (int px = x; px < x + size; px++) {
					for (int py = y; py < y + size; py++) { // Same as previous for loop
						if (px >= 0 & px < image.getWidth() && py >= 0
								&& py < image.getHeight()) {
							image.setPixel(px, py, pixel);
						}
					}
				}
			}
		}
		return image;
	}


	private static Photo warhol(Photo image) {
		image = half(image);
		Photo image2 = copy(image);
		Photo image3 = copy(image);
		Photo image4 = copy(image);
		
		image2 = grayscale(image2);
		image3 = exampleFilter(image3);
		image4 = vignette(image4);
		
		Photo combined = new Photo(image.getWidth() * 2, image.getHeight() * 2);
		
		// image 1, top left - same width & height 
				for (int x = 0; x < image.getWidth(); x++) {
					for (int y = 0; y < image.getHeight(); y++) {
						combined.setPixel(x, y, image.getPixel(x, y));
					}
				}
				// image 2, top right - different x , same y 
				for (int x = 0; x < image.getWidth(); x++) {
					for (int y = 0; y < image.getHeight(); y++) {
						combined.setPixel(x + image.getWidth(), y,
								image2.getPixel(x, y));
					}
				}
				// image 3, bottom left - different y , same x
				for (int x = 0; x < image.getWidth(); x++) {
					for (int y = 0; y < image.getHeight(); y++) {
						combined.setPixel(x, y + image.getHeight(),
								image3.getPixel(x, y));
					}
				}
				// image 4, bottom right - different x, different y 
				for (int x = 0; x < image.getWidth(); x++) {
					for (int y = 0; y < image.getHeight(); y++) {
						combined.setPixel(x + image.getWidth(), y + image.getHeight(),
								image4.getPixel(x, y));
					}
				}
				return combined;
	}

	private static Photo blur(Photo image) {
		int distance = 20; //Intensity of blur we want
		Photo newimage = new Photo(image.getWidth(), image.getHeight());
		
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {

				int red = 0;
				int blue = 0;
				int green = 0;
				int count = 0;
				// visit the pixels within distance of this pixel
				for (int blurx = x - distance; blurx <= x + distance; blurx++) { 
					for (int blury = y - distance; blury <= y + distance; blury++) {

						if (blurx >= 0 && blurx < image.getWidth()
								&& blury >= 0 && blury < image.getHeight()) {
							//Check if these pixels are in the boundaries

							red = red + image.getRed(blurx, blury);
							green = green + image.getGreen(blurx, blury);
							blue = blue + image.getBlue(blurx, blury);
							count++;
							//Depending on how many pixels being used
						}

					}
				}

				red = red / count; //Get the average color of added layers
				blue = blue / count;
				green = green / count;

				int pixel = createPixel(red, green, blue);
				newimage.setPixel(x, y, pixel);

			}
		}
		return newimage;
	}

	private static Photo vignette(Photo image) {
		int centerx = image.getWidth() / 2;
		int centery = image.getHeight() / 2;
		for(int x = 0; x < image.getWidth(); x++){
			for(int y = 0; y < image.getHeight(); y++){
				//calculate the distance away from the center
				int distance = (int) Math.sqrt(Math.pow(x - centerx, 2)
						+ Math.pow(y - centery, 2));
				System.out.println(distance);
				distance = distance / 5; //Depending on the distance, the outermost part gets darker
				//Intensity of the black 
				int red = image.getRed(x, y);
				int green = image.getGreen(x, y);
				int blue = image.getBlue(x, y);

				red = red - distance;
				green = green - distance;
				blue = blue - distance;

				if (red < 0) {
					red = 0;
				}
				if (green < 0) {
					green = 0;
				}
				if (blue < 0) {
					blue = 0;
				}
				int pixel = createPixel(red, green, blue);
				image.setPixel(x, y, pixel);
			}
		}
		return image;
	}

	public static String[][] getMenu() {
		return new String[][] {
			{ "Size", 		/* Contents */ "Half", "Rotate", "Flip", "Mirror" },
			{ "Color", 		/* Contents */ "Example", "Funky", "Grayscale", "Brighten", "Darken" },
			{ "Combine", 	/* Contents */ "Size To", "Merge", "Chromakey" },
			{ "Effect",    /*Contents*/ "Vignette", "Blur", "Warhol","Pixelate", "Megaman", "Link","Tswizzle"}
		};
	}
	
	/**
	 * Utility method for combining RGB values.
	 */
	public static int createPixel(int red, int green, int blue) {
		return (red << 16 | green << 8 | blue);
	}
}