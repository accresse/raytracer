package org.cresse.raytracer.scene;

import javax.vecmath.Vector3d;

/**
 * Description:
 *    Stores all info on the camera
 */

public class Camera {

  private Vector3d eye=new Vector3d(1.0, 1.0, 1.0);
  private Vector3d at=new Vector3d(0.0, 0.0, 0.0);
  private Vector3d up=new Vector3d(0.0, 0.0, 0.1);
  private double fovy=45.0;
  private int width=320;
  private int height=200;
  private double focalLength=1.0;               //where to focus
  private double fStop=100.0;                   //diameter of eye points
  private boolean pinhole=true;                 //if true don't use aperature

  public Camera(){}

  public Camera(Vector3d eye, Vector3d at, Vector3d up, double fovy, int width, int height){
    this.eye=eye;
    this.at=at;
    this.up=up;
    this.fovy=fovy;
    this.width=width;
    this.height=height;
  }

  public Vector3d getEye(){ return eye; }
  public void setEye(Vector3d eye){ this.eye=eye; }

  public Vector3d getAt(){ return at; }
  public void setAt(Vector3d at){ this.at=at; }

  public Vector3d getUp(){ return up; }
  public void setUp(Vector3d up){ this.up=up; }

  public double getFovy(){ return fovy; }
  public void setFovy(double fovy){ this.fovy=fovy; }

  public double getAspect(){ return ((double)width)/((double)height); }

  public int getWidth(){ return width; }
  public void setWidth(int width){ this.width=width; }

  public int getHeight(){ return height; }
  public void setHeight(int height){ this.height=height; }

  public void setFocalLength(double focalLength){ this.focalLength=focalLength; }
  public double getFocalLength(){ return focalLength; }

  public void setFStop(double fStop){ this.fStop=fStop; }
  public double getFStop(){ return fStop; }

  public void setPinhole(boolean pinhole){ this.pinhole=pinhole; }
  public boolean isPinhole(){ return pinhole; }

}