package javaray.scene;

import javaray.logic.Ray;

import javax.vecmath.Point2d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

/**
 * Description:
 *    Special SceneObject.  Handles all Quadrics. i.e. surfaces in the form:
 *      Ax^2+By*2+Cz^2+Dxy+Exz+Fyz+Gx+Hy+Jz+K==0
 */

public class SceneQuadric extends SceneObject {

//coefficients
  public double x2=0.0;
  public double y2=0.0;
  public double z2=0.0;
  public double xy=0.0;
  public double xz=0.0;
  public double yz=0.0;
  public double x=0.0;
  public double y=0.0;
  public double z=0.0;
  public double k=0.0;

  //is a point on the surface?
  //NOT IMPLEMENTED YET
  public boolean isOn(Point3d p){
    return false;
  }

  //return intersection of ray with surface -- quadratic formula
  protected double doIntersection(Ray r) {
    double a=r.getP().x;
    double b=r.getP().y;
    double c=r.getP().z;
    double d=r.getD().x;
    double e=r.getD().y;
    double f=r.getD().z;
    double A=x2;
    double B=y2;
    double C=z2;
    double D=xy;
    double E=yz;
    double F=xz;
    double G=x;
    double H=y;
    double J=z;
    double K=k;


    double Aterm=A*d*d+B*e*e+C*f*f+2.0*(D*d*e+E*d*f+F*e*f);
    double Bterm=2.0*(a*A*d+b*d*D+b*B*e+a*D*e+c*d*E+c*C*f+a*E*f+c*e*F+b*f*F+d*G+e*H+f*J);
    double Cterm=a*a*A+b*b*B+c*c*C+2.0*(a*b*D+a*c*E+b*d*F+a*G+b*H+c*J)+K;

    double Dterm=Bterm*Bterm-4.0*Aterm*Cterm;
    if(Dterm<0.0) return -1; //imaginary
    Dterm=Math.sqrt(Dterm);
    double t1=(-Bterm+Dterm)/(2.0*Aterm);
    double t2=(-Bterm-Dterm)/(2.0*Aterm);
    double t=Math.min(t1,t2);
    if(t<0.0) t=Math.max(t1,t2);
    return t;
  }

  //return unit normal at point p -- uses partial derivative
  protected Ray doUnitNormal(Point3d p) {
    double A=x2;
    double B=y2;
    double C=z2;
    double D=xy;
    double E=yz;
    double F=xz;
    double G=x;
    double H=y;
    double J=z;
    //double K=k;
    double dx=2.0*A*p.x+D*p.y+F*p.z+G;
    double dy=2.0*B*p.y+D*p.x+E*p.z+H;
    double dz=2.0*C*p.z+E*p.y+F*p.x+J;
    //double dx=2.0*p.x;
    //double dy=2.0*p.y;
    //double dz=2.0*p.z;
    Ray N=new Ray(p,new Vector3d(dx,dy,dz));
    return N.inverse();
  }

  public Point2d get2DCoord(Point3d p){
    return new Point2d();
  }
}