package org.cresse.raytracer.logic;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * Description:
 *    This class writes a BufferedImage to a file as a JPEG.
 */

public class ImageSaver {

  private BufferedImage image;

  //set the image contents
  public void setImage(BufferedImage image){
    this.image=image;
  }

  //save the file as filename
  public void saveAs(String filename){
    try{
      if(!filename.endsWith(".bmp")) filename+=".bmp";
      ImageIO.write(image, "bmp", new File(filename));
    } catch(Exception e){
      e.printStackTrace();
    }
  }
}