package org.cresse.raytracer.scene;

import java.awt.Color;

import javax.vecmath.Point3d;

/**
 * Description:
 *    Stores all info on the light
 */

public class Light {

  private Point3d position;
  private Color color;

  public Light(){}

  public Light(Point3d position, Color color){
    this.position=position;
    this.color=color;
  }

  public void setPosition(Point3d position){ this.position=position; }
  public Point3d getPosition(){ return position; }

  public void setColor(Color color){ this.color=color; }
  public Color getColor(){ return color; }

}