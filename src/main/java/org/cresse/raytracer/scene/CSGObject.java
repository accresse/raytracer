package org.cresse.raytracer.scene;


import javax.vecmath.Point2d;
import javax.vecmath.Point3d;

import org.cresse.raytracer.logic.Ray;

/**
 * Description:
 *    Special composite SceneObject that can do set operations on other SceneObjects
 */

public class CSGObject extends SceneObject {

  //operations
  public static final int INTERSECTION=0;
  public static final int UNION=1;
  public static final int SUBTRACT=2;

  //instance vars
  private int operation;
  private SceneObject A;
  private SceneObject B;

  //getters and setters
  public void setA(SceneObject obj){ A=obj; }
  public SceneObject getA(){ return A; }
  public void setB(SceneObject obj){ B=obj; }
  public SceneObject getB(){ return B; }

  public void setOperation(int operation){ this.operation=operation; }
  public int getOperation(){ return operation; }

  public Ray getRefraction(Ray r, Point3d pos, double defaultIr) {
    return null;
    /**@todo: implement this javaray.scene.SceneObject abstract method*/
  }
  public Ray getReflection(Ray r, Point3d pos) {
    return null;
    /**@todo: implement this javaray.scene.SceneObject abstract method*/
  }

  //returns where a ray intersects this composite object--WORKS!
  protected double doIntersection(Ray r) {
    double At=A.getIntersection(r);
    double Bt=B.getIntersection(r);

    //definitions of what to return
    switch(operation){
      case INTERSECTION:
        if(At==-1 || Bt==-1) return -1;
        else return At;
      case UNION:
        if(At!=-1) return At;
        else if(Bt!=-1) return Bt;
        else return -1;
      case SUBTRACT:
        if(At!=-1 && Bt==-1) return At;
        else return -1;
      default:
        return -1;
    }
  }

  //returns normal ray at intersection point -- DOES NOT WORK! :(
  protected Ray doUnitNormal(Point3d p) {
    boolean isOnA=A.isOn(p);

    //definition of what to return
    switch(operation){
      case INTERSECTION:
        return isOnA?A.getUnitNormal(p):B.getUnitNormal(p);
      case UNION:
        return null;
      case SUBTRACT:
        return null;
      default:
        return null;
    }
  }

  public Point2d get2DCoord(Point3d p){
    return new Point2d();
  }

  //write later-- not really needed
  public boolean isOn(Point3d p){
    return false;
  }
}