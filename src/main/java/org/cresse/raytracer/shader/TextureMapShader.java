package org.cresse.raytracer.shader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


import javax.swing.ImageIcon;
import javax.vecmath.Point2d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import org.cresse.raytracer.logic.Intersection;
import org.cresse.raytracer.logic.Ray;
import org.cresse.raytracer.logic.Tracer;
import org.cresse.raytracer.scene.Light;
import org.cresse.raytracer.scene.Scene;
import org.cresse.raytracer.scene.SceneObject;

public class TextureMapShader extends ObjectShader {

  BufferedImage img;
  Material m=Material.DEFAULT;
  double scalex=1.0;
  double scaley=1.0;
  int w;
  int h;

  public void setScale(double s){
    this.scalex=s;
    this.scaley=s;
  }
  public TextureMapShader(String filename, Material m){
    ImageIcon ii=new ImageIcon(ClassLoader.getSystemResource(filename));
    w=ii.getIconWidth();
    h=ii.getIconHeight();
    img=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
    Graphics2D g=img.createGraphics();
    g.drawImage(ii.getImage(),0,0,null);
    this.m=m;
  }

  public Color getColor(Ray ray, Point3d position, Scene scene, Tracer tracer, SceneObject obj) {
      Point2d coord=obj.get2DCoord(position);
      int x=Math.round((float)(w*coord.x*scalex));
      int y=Math.round((float)(h*coord.y*scaley));
      //System.out.println("coord.x,coord.y: "+coord.x+", "+coord.y);
      //System.out.println("x,y: "+x+", "+y);
      while(x<0) x+=w;
      while(y<0) y+=h;
      Color Kd=new Color(img.getRGB(x%w,y%h));

      //get index of this obj
      int closest_obj=-1;
      for (int i = 0; i < scene.getObjects().length; i++) {
        if(scene.getObjects()[i].equals(obj)){
          closest_obj=i;
          break;
        }
      }

      double R=0.0;
      double G=0.0;
      double B=0.0;

      SceneObject curr_obj = obj;
      Material curr_material = m;

      //No matter what, add ambient
      Color Ia=scene.getAmbient(); //ambient color
      Color Ka=curr_material.getReflAmb();  //ambient multipliers

      //add Ia*Ka
      R += (((double)Ia.getRed())/255.0)*(((double)Ka.getRed())/255.0);
      G += (((double)Ia.getGreen())/255.0)*(((double)Ka.getGreen())/255.0);
      B += (((double)Ia.getBlue())/255.0)*(((double)Ka.getBlue())/255.0);

      //create standard variables from Phong equation (Lecture 8 Slide 8)
      //use texture as diffuse color
      Color Ks=curr_material.getReflSpec(); //specular multiplier
      double n=curr_material.getShininess(); //shininess

      //set up some rays for use later
      //N- surface normal
      //v- eye vector
      Ray N=curr_obj.getUnitNormal(position); //Normal vector
      N=N.inverse();
      Vector3d v=new Vector3d(ray.getP());
      v.sub(position);
      v.normalize();

      //cycle through all light sources
      Light[] scene_lights=scene.getLights();
      for(int k=0; k<scene_lights.length; k++){
        Light curr_light = scene_lights[k];

        //calculate shadow ray from intersection to light
        Ray shadow_ray=new Ray(position,curr_light.getPosition());

        Intersection temp=tracer.checkInter(shadow_ray,closest_obj);

        //distance of first intersection on way to light
        double occluded=temp.getDist();
        //how far the light is in t
        double light_t=(curr_light.getPosition().x-shadow_ray.getP().x)/shadow_ray.getD().x;

        //More Phong variables
        Color Il=curr_light.getColor();  //light's color
        //ray from object to light
        Vector3d L=new Vector3d(curr_light.getPosition());
        L.sub(position);
        L.normalize();

        //Calculate r=2.0*N*(N*L)-L
        double NL=N.getD().dot(L);
        Vector3d r=new Vector3d(N.getD());
        r.scale(2.0*NL);
        r.sub(L);
        r.normalize();

        //if nothing in the way add other components
        if(occluded<=0 || occluded>light_t){
          //calc diffuse contribution from this light
          if(NL>0.0){
            R += (((double)Il.getRed())/255.0)*(((double)Kd.getRed())/255.0)*NL;
            G += (((double)Il.getGreen())/255.0)*(((double)Kd.getGreen())/255.0)*NL;
            B += (((double)Il.getBlue())/255.0)*(((double)Kd.getBlue())/255.0)*NL;
          }
          //calc specular contribution from this light
          double rv=r.dot(v);
          if(rv>0.0){
            R += (((double)Il.getRed())/255.0)*(((double)Ks.getRed())/255.0)*Math.pow(rv,n);
            G += (((double)Il.getGreen())/255.0)*(((double)Ks.getGreen())/255.0)*Math.pow(rv,n);
            B += (((double)Il.getBlue())/255.0)*(((double)Ks.getBlue())/255.0)*Math.pow(rv,n);
          }
        }
      }

      //time for reflection
      int recurse=scene.getRecurse();
      if(recurse>0 && !Ks.equals(Color.black)){
        //have object calculate the reflection ray at intersection
        Ray reflect=curr_obj.getReflection(ray,position);
        //get color recursively
	Color refl_color=tracer.color(reflect,closest_obj,recurse-1);
	R += (((double)Ks.getRed())/255.0)*(((double)refl_color.getRed())/255.0);
	G += (((double)Ks.getGreen())/255.0)*(((double)refl_color.getGreen())/255.0);
	B += (((double)Ks.getBlue())/255.0)*(((double)refl_color.getBlue())/255.0);
      }

      //**********added for refraction**********
      Color trans=curr_material.getReflTrans();

      //if some portion of color is transmitted
      if(! trans.equals(Color.black)){
        double defaultIr=scene.getDefaultIr();  //defaut index of refraction (normally 1.0)
        double transR=(((double)trans.getRed())/255.0);
        double transG=(((double)trans.getGreen())/255.0);
        double transB=(((double)trans.getBlue())/255.0);
        //have object calculate refract ray
        Ray refract=curr_obj.getRefraction(ray,position,defaultIr);
        //get color
	Color refract_color=tracer.color(refract,closest_obj,recurse-1);
	R = transR*(((double)refract_color.getRed())/255.0)+(1.0-transR)*R;
	G = transG*(((double)refract_color.getGreen())/255.0)+(1.0-transG)*G;
	B = transB*(((double)refract_color.getBlue())/255.0)+(1.0-transB)*B;
      }
      //**********end refraction**********

    //clamp colors [0.0,1.0]
    R=(R>1.0)?1.0:R;
    G=(G>1.0)?1.0:G;
    B=(B>1.0)?1.0:B;
    R=(R<0.0)?0.0:R;
    G=(G<0.0)?0.0:G;
    B=(B<0.0)?0.0:B;

    //write this color into pixel (x,y)

    return new Color((float)R,(float)G,(float)B);
  }
}