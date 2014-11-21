package org.cresse.raytracer;


import javax.swing.JFrame;

import org.cresse.raytracer.display.ImageDisplay;
import org.cresse.raytracer.logic.Tracer;
import org.cresse.raytracer.scene.Scene;
import org.cresse.raytracer.scene.SceneParser;

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