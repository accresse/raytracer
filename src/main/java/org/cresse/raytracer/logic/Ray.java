package org.cresse.raytracer.logic;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

/**
 * Description:
 *    The ADT for a Ray.  Stores the ray as a Point3d and a Vector3d (as
 *    provided by the javax.vecmath package).  Provides other operations used by
 *    ray tracer on rays.
 */

public class Ray {

  private Point3d p=new Point3d(0.0,0.0,0.0); //start position
  private Vector3d d=new Vector3d(1.0,0.0,0.0); //direction

  public Ray(){}

  //just sets these values
  public Ray(Point3d p, Vector3d d){
    this.p=p;
    this.d=d;
    unitize();//length 1
  }

  //makes a ray starting at 'origin' and headed towards the point 'towards'
  public Ray(Point3d origin, Point3d towards){
    this.p=new Point3d(origin);
    Vector3d head=new Vector3d(towards);
    head.sub(origin);
    this.d=head;
    unitize();
  }

  //Copy constructor
  public Ray(Ray r){
    this.p=new Point3d(r.getP());
    this.d=new Vector3d(r.getD());
  }

  //assorted getters and setters
  public void setP(Point3d p){ this.p=p; }
  public Point3d getP(){ return p; }

  public Vector3d getD(){ return d; }
  public void setD(Vector3d d){
    this.d=d;
    //unitize();
  }

  //normalize the ray by normalizing the direction vector
  //(cs318 naming convention "unitize")
  public void unitize(){
    d.normalize();
  }

  //returns the point that is distance t away from p following d (p+t*d)
  public Point3d getPosition(double t){
    Point3d pos=new Point3d(d);
    pos.scaleAdd(t,p);
    return pos;
  }

  //dot product for rays
  public double dot(Ray r){
    return d.dot(r.getD());
  }

  //point the ray in the opposite direction
  public Ray inverse(){
    Vector3d rd=new Vector3d(d);
    rd.scale(-1.0);
    Ray r=new Ray(p,rd);
    return r;
  }

  //display the ray meaningfully
  public String toString(){
    return p.toString()+" -> "+d.toString();
  }
}