package org.cresse.raytracer.logic;

import java.awt.Color;


import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Point4d;
import javax.vecmath.Vector3d;

import org.cresse.raytracer.display.ImageDisplay;
import org.cresse.raytracer.scene.Camera;
import org.cresse.raytracer.scene.Scene;
import org.cresse.raytracer.scene.SceneObject;
import org.cresse.raytracer.scene.ScenePlane;

/**
 * Description:
 *    The "brain" of the ray tracer.  This thread runs through all pixels and
 *    determines the final color.  It does this by casting rays from the eye to
 *    each pixel and calculating intersections.
 */

public class Tracer2 extends Tracer{

  private ImageDisplay display; //where to draw the results
  private Scene scene;          //the object describing the scene

  //set display and scene at startup
  public Tracer2(ImageDisplay display, Scene scene){
	  super(display,scene);
    this.display=display;
    this.scene=scene;
  }

  //thread's body
  public void run(){

    long start=System.currentTimeMillis();
    
    Camera camera=scene.getCamera();

    //if in debug mode, only run at a certain pixel
    int w=camera.getWidth();
    int h=camera.getHeight();

    //set up for depth of field
    double fs=camera.getFStop();
    double fl=camera.getFocalLength();
    boolean pinhole=camera.isPinhole();
    double aperature=fl/fs;
    int jitterNum=scene.getJitterNum();
    //this will set up a plane at the focal length perp. to the eye
    ScenePlane focalPlane=new ScenePlane();
    focalPlane.setCropped(false);
    Ray lookat=new Ray(vec2point(camera.getEye()),vec2point(camera.getAt()));
    focalPlane.setCenter(lookat.getPosition(fl));
    focalPlane.setNormal(lookat.inverse().getD());

    Matrix4d msw=scene.getMsw(); //Matrix from scene to world coordinates
    //get eye in world coords
    Point4d eye=new Point4d(0.0,0.0,-1.0,0.0);
    msw.transform(eye);// hit eye with matrix
    eye.project(eye); //4D -> 3D divide by w
    Point3d eyeW=new Point3d(eye.x,eye.y,eye.z);//eye in world coords

    //Get an x-axis and y-axis perpendicular to the way we are looking
    Ray yAxis=new Ray(eyeW,camera.getUp()); //from eye in up dir
    Vector3d at=camera.getAt();
    Ray zAxis=new Ray(eyeW,new Point3d(at.x,at.y,at.z)); //from eye to At
    Vector3d xD=new Vector3d();
    xD.cross(yAxis.getD(),zAxis.getD());
    Ray xAxis=new Ray(eyeW,xD);                        //y cross z
    
    double aperature2=2.0*aperature;

    // iterate over all scanlines
    for(int j=0; j<h; j++){
      // iterate over the pixels
      for(int i=0; i<w; i++){

        //total color components per ray
        double[] colTotal={0.0,0.0,0.0};

        //do once for center - so we can get pinhole camera when jitterNum=1

        //transform pixel
        Point4d pixel=new Point4d(i+0.5, j+0.5, 0.0, 1.0);
        msw.transform(pixel);  //hit pixel with matrix
        pixel.project(pixel);  //4D -> 3D divide by w
        Point3d pixelW=new Point3d(pixel.x,pixel.y,pixel.z);//pixel in world coords

        Ray eyeRay=new Ray(eyeW, pixelW); //make Ray from eye (+variance) to pixel
        Color rgb=color(eyeRay,-1,scene.getRecurse()); //calculate color of this pixel
        colTotal[0]+=((double)rgb.getRed())/255.0;
        colTotal[1]+=((double)rgb.getGreen())/255.0;
        colTotal[2]+=((double)rgb.getBlue())/255.0;

        double fst=focalPlane.getIntersection(eyeRay);
        Point3d focalSpot=eyeRay.getPosition(fst);

        //and then again for jitters
        for (int k = 0; k < jitterNum; k++) {
          //calc random variation
          double dx1=aperature2*Math.random()-aperature;
          double dy1=aperature2*Math.random()-aperature;
          double dx2=Math.random();
          double dy2=Math.random();

          //jitter eye
          pixel=new Point4d(i+dx2, j+dy2, 0.0, 1.0);
          msw.transform(pixel);  //hit pixel with matrix
          pixel.project(pixel);  //4D -> 3D divide by w
          pixelW=new Point3d(pixel.x,pixel.y,pixel.z);//pixel in world coords

          Point3d dxW1=xAxis.getPosition(dx1);
          Point3d dyW1=yAxis.getPosition(dy1);
          Point3d dofStart=new Point3d();
          dofStart.add(dxW1,dyW1);
          dofStart.sub(eyeW);

          //check if within circle
          double dist=dofStart.distance(eyeW);

          //if not pick a new point
          if(dist>aperature){
            k--;
            continue;
          }

          if(pinhole) dofStart=eyeW;
          eyeRay=new Ray(dofStart, focalSpot); //make Ray from eye (+variance) to pixel
          rgb=color(eyeRay,-1,scene.getRecurse()); //calculate color of this pixel
          colTotal[0]+=((double)rgb.getRed())/255.0;
          colTotal[1]+=((double)rgb.getGreen())/255.0;
          colTotal[2]+=((double)rgb.getBlue())/255.0;
        }
        colTotal[0]/=(jitterNum+1);
        colTotal[1]/=(jitterNum+1);
        colTotal[2]/=(jitterNum+1);
        rgb=new Color((float)colTotal[0],(float)colTotal[1],(float)colTotal[2]);
        display.setPixel(i,j,rgb);  //update display
      }
    }
    //if scene says to save image
    if(scene.getSave()){
      ImageSaver imgs=new ImageSaver();
      imgs.setImage(display.getImg());
      imgs.saveAs(scene.getSaveAs());
    }
    long end=System.currentTimeMillis();
    long ms=end-start;
    long hours=ms/3600000;
    ms=ms%3600000;
    long mins=ms/60000;
    ms=ms%60000;
    long secs=ms/1000;
    System.out.println("Done in "+hours+":"+mins+":"+secs);
  }

  //Checks for intersection with all objects
  //Adds slight offset if checking self intersection
  public Intersection checkInter(Ray r, int obj, boolean isRefract){
    double closest_t=-1.0;
    int closest_obj=-1;
    SceneObject[] objects=scene.getObjects();  //get a list of all objects in scene

    //loop through all objects and  find closest
    for(int k=0; k<objects.length; k++){
      if(k==obj) continue; //don't self intersect
      SceneObject curr_obj = objects[k];

      double t=curr_obj.getIntersection(r);  //have object calculate its intersection

      //find surface with closest intersection
      //adds offset if we are checking intersection with self
      if(!(k==obj && Math.abs(t)<0.000000000001) && ((t>0.0 && closest_t>0.0 && t<closest_t) || (t>=0.0 && closest_t<0.0))){
        closest_t=t;
        closest_obj=k;
      }
    }
    Intersection retVal=new Intersection();
    retVal.setDist(closest_t);
    retVal.setObj(closest_obj);
    //return list that has (t value of closest object), (closest object index)
    return retVal;
  }

  //If fewer params passed
  public Intersection checkInter(Ray r){ return checkInter(r,-1,false); }
  public Intersection checkInter(Ray r, int obj){ return checkInter(r,obj,false); }
  public Color color(Ray ray, int obj, int recurse){
    return color(ray,obj,recurse,false);
  }

  //Uses Phong equations to calculate color of object at ray intersection
  public Color color(Ray ray, int obj, int recurse, boolean isRefract){
    //compute intersections with all surfaces
	Intersection inter=checkInter(ray, obj, isRefract);
    double closest_t = inter.getDist();
    int closest_obj = inter.getObj();
    Point3d position = ray.getPosition(closest_t);

    //declare color components
    double R=0.0;
    double G=0.0;
    double B=0.0;

    //if we don't intersect anything, set bgcolor
    if(closest_t<0.0){
      Color bg=scene.getBgcolor();
      R=((double)bg.getRed())/255.0;
      G=((double)bg.getGreen())/255.0;
      B=((double)bg.getBlue())/255.0;
    }

    //otherwise
    else{
      SceneObject closest=(SceneObject)scene.getObjects()[closest_obj];
      Color col=closest.getColor(ray,position,scene,this);
      R=((double)col.getRed())/255.0;
      G=((double)col.getGreen())/255.0;
      B=((double)col.getBlue())/255.0;
    }

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

  private Point3d vec2point(Vector3d v){
    return new Point3d(v.x,v.y,v.z);
  }
}