package javaray.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * Description: This class provides a JPanel which displays an image. It updates
 * as the image is altered, and stores the image so that it can be saved later.
 */

public class ImageDisplay extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;

	private BufferedImage img; // Image we are writing to

	// Constructor
	public ImageDisplay(int w, int h) {
		this.setSize(w, h);
		this.setPreferredSize(new Dimension(w, h));
		img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	}

	// allows other classes to alter img
	public void setPixel(int x, int y, Color c) {
		img.setRGB(x, y, c.getRGB());
		// redisplay image periodically
		if (x == 0 && y % 2 == 0)
			repaint();
	}

	// draw image on screen
	public void paint(Graphics g) {
		g.drawImage(img, 0, 0, this);
	}

	public void dispose() {
		System.exit(0);
	}

	// returns the image to requestor
	public BufferedImage getImg() {
		return img;
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	// clicking the mouse will give the pixel location and color for debugging
	public void mouseClicked(MouseEvent e) {
		e.translatePoint(-4, -23);
		int color = img.getRGB(e.getX(), e.getY());
		double r = ((color >> 16) & 255) / 255.0;
		double g = ((color >> 8) & 255) / 255.0;
		double b = ((color) & 255) / 255.0;
		System.out.println("[" + e.getX() + ", " + e.getY() + "] -> (" + r + ", " + g + ", " + b + ")");
	}

}
