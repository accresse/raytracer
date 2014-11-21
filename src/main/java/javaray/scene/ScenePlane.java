package javaray.scene;

import javaray.logic.Ray;

import javax.vecmath.Point2d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

public class ScenePlane extends SceneObject {
  protected Vector3d normal=new Vector3d(0.0,0.0,-1.0);
  protected Point3d center=new Point3d(0.0,0.0,0.0);
  protected double D=0.0;
  protected double width=1.0;
  protected double height=1.0;
  protected boolean cropped=true;

  public void setCenter(Point3d center){this.center=center;}

  public void setNormal(Vector3d normal){
    this.normal.normalize(normal);
  }
  public void setD(double D){ this.D=D; }
  public void setCropped(boolean cropped){
    this.cropped=cropped;
  }

  public boolean isOn(Point3d p) {return false;}

  protected double doIntersection(Ray r) {
    //Ax+By+Cz+D=0
    //A(a+td)+B(b+te)+C(c+tf)+D==0
    //(Ad+Be+Cf)t=-(Aa+Bb+Cc+D)
    //t=-(Aa+Bb+Cc+D)/(Ad+Be+Cf)
    double A=normal.x;
    double B=normal.y;
    double C=normal.z;
    double a=r.getP().x-center.x;
    double b=r.getP().y-center.y;
    double c=r.getP().z-center.z;
    double d=r.getD().x;
    double e=r.getD().y;
    double f=r.getD().z;
    double t = -(A*a+B*b+C*c+D)/(A*d+B*e+C*f);
    Point3d pos=r.getPosition(t);
    if(cropped && (pos.x<0.0 || pos.x>width || pos.y<0.0 || pos.y >height)) return -1.0;
    return t;
  }

  protected Ray doUnitNormal(Point3d p) {
    return new Ray(p,normal);
  }

  public Point2d get2DCoord(Point3d p){
    Point3d itp=this.invTransPoint(p);
    return new Point2d(itp.x,itp.z);
  }
}