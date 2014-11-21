package javaray;

import javaray.display.ImageDisplay;
import javaray.logic.Tracer;
import javaray.scene.Scene;
import javaray.scene.SceneParser;

import javax.swing.JFrame;

/**
 * Description:
 *    This class is the entry point for executing the ray tracer.
 *    It opens a frame and binds the ray tracer to the display.
 */

public class Main {
	public static void main(String args[]) {
		SceneParser parser = new SceneParser();
		Scene scene = parser.parse();
		ImageDisplay display = new ImageDisplay(scene.getCamera().getWidth(), scene.getCamera().getHeight());
		JFrame f = new JFrame();
		f.addMouseListener(display);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setContentPane(display);
		f.pack();
		f.setVisible(true);
		Tracer rt = new Tracer(display, scene);
		rt.start();

	}
}