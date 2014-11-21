package javaray.scene;

import javaray.logic.Ray;
import javaray.shader.Material;
import javaray.shader.ObjectShader;
import javaray.shader.PhongShader;

import javax.vecmath.Point2d;
import javax.vecmath.Point3d;

/**
 * Description:
 *    Special SceneObject.  Just renders Spheres.
 */

public class SceneSphere extends SceneObject{
  private Point3d center=new Point3d(0.0,0.0,0.0);
  private double radius=1.0;

  public SceneSphere(){}

  //deprecated - use transforms
  public SceneSphere(Point3d center, double radius, ObjectShader shader){
    this.center=center;
    this.radius=radius;
    this.shader=shader;
  }

  //deprecated - use transforms
  public SceneSphere(Point3d center, double radius, Material material){
    this.center=center;
    this.radius=radius;
    this.shader=new PhongShader(material);
  }

  //returns intersection -- specialized quad. formula
  protected double doIntersection(Ray ray){

    //quad form: t=(-b+/-Sqrt(b^2-4ac))/2a
    double t=-1.0;
    double t1=-1.0; //two possible intersections
    double t2=-1.0;
    double x0=ray.getP().x;
    double y0=ray.getP().y;
    double z0=ray.getP().z;
    double dx=ray.getD().x;
    double dy=ray.getD().y;
    double dz=ray.getD().z;
    double a=center.x;
    double b=center.y;
    double c=center.z;
    double r=radius;
    double A=dx*dx+dy*dy+dz*dz;
    double B=2.0*(dx*(x0-a)+dy*(y0-b)+dz*(z0-c));
    double C=(x0-a)*(x0-a)+(y0-b)*(y0-b)+(z0-c)*(z0-c)-r*r;
    //check for imaginaries
    if((B*B-4.0*A*C)<0.0){
      t1=-1.0;
      t2=-1.0;
    }
    //calc t param for vector
    else{
      t1=(-1.0*B-Math.sqrt(B*B-4.0*A*C))/(2.0*A);
      t2=(-1.0*B+Math.sqrt(B*B-4.0*A*C))/(2.0*A);
    }

    //find closest intersection
    t=t1;
    t=((t2>0 && t>0 && t2<t) || (t2>0 && t<0))?t2:t;

    return t;
  }

  //easy for sphere
  protected Ray doUnitNormal(Point3d p){
    Ray N = new Ray(p,center);
    N.inverse();
    return N;
  }

  public boolean isOn(Point3d p){
    double x=p.x-center.x;
    double y=p.y-center.y;
    double z=p.z=center.z;
    double dist=Math.sqrt(x*x+y*y+z*z);
    return (Math.abs(dist-radius)<0.0000001);
  }

  public Point2d get2DCoord(Point3d p){
    return new Point2d();
  }
}