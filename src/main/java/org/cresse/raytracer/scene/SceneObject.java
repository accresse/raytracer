package org.cresse.raytracer.scene;

import java.awt.Color;


import javax.vecmath.Matrix4d;
import javax.vecmath.Point2d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import org.cresse.raytracer.logic.Ray;
import org.cresse.raytracer.logic.Tracer;
import org.cresse.raytracer.shader.Material;
import org.cresse.raytracer.shader.ObjectShader;
import org.cresse.raytracer.shader.PhongShader;

/**
 * Description:
 *    Parent to all renderable objects sets mandatory operations and provides
 *    default reflection/refraction implementation
 */

 public abstract class SceneObject {

  //protected Material material=Material.DEFAULT;

  //public void setMaterial(Material material){ this.material=material; }
  //public Material getMaterial(){ return material; }

  protected ObjectShader shader=new PhongShader();
  protected double ir=1.0;
  protected Matrix4d trans=new Matrix4d(1.0,0.0,0.0,0.0,
                                           0.0,1.0,0.0,0.0,
                                           0.0,0.0,1.0,0.0,
                                           0.0,0.0,0.0,1.0);

  protected Matrix4d invTrans=new Matrix4d(1.0,0.0,0.0,0.0,
                                           0.0,1.0,0.0,0.0,
                                           0.0,0.0,1.0,0.0,
                                           0.0,0.0,0.0,1.0);

  protected Matrix4d invTransT=new Matrix4d(1.0,0.0,0.0,0.0,
                                           0.0,1.0,0.0,0.0,
                                           0.0,0.0,1.0,0.0,
                                           0.0,0.0,0.0,1.0);

  public void setShader(ObjectShader shader){this.shader=shader;}
  public ObjectShader getShader(){return shader;}

  public void setIr(double ir){this.ir=ir;}
  public double getIr(){return ir;}

  public void setTransform(Matrix4d transform){
    this.trans=new Matrix4d(transform);
    invTrans.invert(trans);
    invTransT.transpose(invTrans);
  }

  //do transformation of ray
  protected Ray invTransRay(Ray r){
    Ray ray=new Ray(r);
    invTrans.transform(ray.getD());
    invTrans.transform(ray.getP());
    return ray;
  }
  protected Ray transRay(Ray r){
    Ray ray=new Ray(r);
    trans.transform(ray.getD());
    trans.transform(ray.getP());
    return ray;
  }
  protected Ray invTransTRay(Ray r){
    Ray ray=new Ray(r);
    invTransT.transform(ray.getD());
    invTransT.transform(ray.getP());
    return ray;
  }

  //do transformation of point
  protected Point3d invTransPoint(Point3d p){
    Point3d ret=new Point3d(p);
    invTrans.transform(ret);
    return ret;
  }
  protected Point3d transPoint(Point3d p){
    Point3d ret=new Point3d(p);
    trans.transform(ret);
    return ret;
  }

  //transform ray and then get intersection
  public double getIntersection(Ray v){
    Ray ray=invTransRay(v);
    return doIntersection(ray);
  }

  //all SceneObjects must define how to get intersection
  protected abstract double doIntersection(Ray r);

  //transform ray and then get intersection
  public Ray getUnitNormal(Point3d p){
    Point3d transP=invTransPoint(p);
    Ray r=doUnitNormal(transP);
    r=invTransTRay(r);
    r.setP(p);
    return r;
  }

  //all SceneObjects must define how to get unit normal
  protected abstract Ray doUnitNormal(Point3d p);

  //default reflection implementation
  public Ray getReflection(Ray r, Point3d pos){
    //Ray i=invTransRay(r);
    Ray i=new Ray(r);
    Ray N=getUnitNormal(pos); //normal
    double ni=N.dot(i);
    Vector3d Nd=new Vector3d(N.getD());
    Nd.scale(2.0*ni);
    i.getD().sub(Nd);//I.d-(2.0*N.dot(I))*N.d
    Ray reflect=new Ray(pos,i.getD());
    //return transRay(reflect);
    return reflect;
  }

  //if fewer params (other one is optimization)
  public Ray getReflection(Ray r){
    return getReflection(r, r.getPosition(getIntersection(r)));
  }

  //default refraction implementation
  //defaultIr incase we want to render underwater or something
  public Ray getRefraction(Ray r, Point3d pos, double defaultIr){
    Ray refract=new Ray();
    Ray i=new Ray(r);
    Ray N=getUnitNormal(pos);
    Vector3d nd=N.getD();
    nd.negate();
    N.setD(nd);
    double ni=N.dot(i.inverse());
    double eta=defaultIr/ir;
    refract.setP(pos);
    Vector3d b=r.getD();
    double scale=eta*ni-Math.sqrt(1.0-eta*eta*(1.0-ni*ni));
    Vector3d a=N.getD();
    a.scale(scale);
    a.add(b);
    refract.setD(a);
    return refract;
  }

  //fewer params (other is optimization)
  public Ray getRefraction(Ray r, double defaultIr){
    return getRefraction(r, r.getPosition(getIntersection(r)), defaultIr);
  }

  //objects should tell whether a certain point is on the surface
  public abstract boolean isOn(Point3d p);

  //returns color of surface according to shader
  public Color getColor(Ray V, Point3d P, Scene scene, Tracer tracer){
    return shader.getColor(V, P, scene, tracer, this);
  }

  //deprecated methods for phong shading
  public void setMaterial(Material m){
    this.shader=new PhongShader(m);
  }

  //get 2D coords for use in tex. maps, etc.
  public abstract Point2d get2DCoord(Point3d p);
}