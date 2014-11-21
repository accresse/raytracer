package javaray.scene;

import java.awt.Color;

import javax.vecmath.Matrix4d;
import javax.vecmath.Vector3d;

/**
 * Description:
 *    Stores scene information
 */

public class Scene {

  private SceneObject[] objects;  				//all objects in scene
  private Light[] lights;                       //all lights in scene
  private Camera camera=new Camera();           //camera for scene
  private Color bgcolor=Color.blue;             //background color
  private Color ambient=Color.black;            //ambient light
  private double defaultIr=1.0;                 //default index of refraction
  private Matrix4d msw;                         //matrix from screen -> world
  private Matrix4d mws;                         //matrix from world -> screen
  private String saveAs="default.jpg";          //where to save the image
  private boolean save=true;                    //whether to save the image
  private int recurse=3;                        //how far to recurse on reflect/refract
  private int jitterNum=1;                      //how many rays to draw for anti-alias and depth-of-field

  // getters and setters
  public int getJitterNum(){ return jitterNum; }
  public void setJitterNum(int jitterNum){ this.jitterNum=jitterNum; }

  public int getRecurse(){ return recurse; }
  public void setRecurse(int recurse){ this.recurse=recurse; }

  public String getSaveAs(){ return saveAs; }
  public void setSaveAs(String filename){ this.saveAs=filename; }

  public boolean getSave(){ return save; }
  public void setSave(boolean save){ this.save=save; }

  public void setObjects(SceneObject[] objects){
    this.objects=objects;
  }

  public SceneObject[] getObjects(){ return objects; }

  public void setLights(Light[] lights){
    this.lights=lights;
  }

  public Light[] getLights(){ return lights; }

  public void setCamera(Camera camera){
    this.camera=camera;
    calcProjection();
  }

  public Camera getCamera(){ return camera; }

  public void setBgcolor(Color bgcolor){ this.bgcolor=bgcolor; }
  public Color getBgcolor(){ return bgcolor; }

  public void setAmbient(Color ambient){ this.ambient=ambient; }
  public Color getAmbient(){ return ambient; }

  public void setDefaultIr(double ir){ this.defaultIr=ir; }
  public double getDefaultIr(){ return defaultIr; }

  public Matrix4d getMws(){ return mws; }
  public Matrix4d getMsw(){ return msw; }

  public Scene(){
    //calcProjection();
  }

  //calculate the view from the camera
  //I ported OpenGL code to Java to get the different matrices
  private void calcProjection(){
    mws=new Matrix4d();
    mws.setIdentity();
    mws.mul(getViewportMatrix());
    mws.mul(getPerspectiveMatrix());
    mws.mul(getLookAtMatrix());
    msw=new Matrix4d(mws);
    msw.invert();
  }

  //port of glViewport()
  private Matrix4d getViewportMatrix(){
    int w=camera.getWidth();
    int h=camera.getHeight();
    Matrix4d m=
      new Matrix4d(w/2.0,    0.0, 0.0, w/2.0,
                     0.0, h/-2.0, 0.0, h/2.0,
                     0.0,    0.0, 1.0,   0.0,
                     0.0,    0.0, 0.0,   1.0);
    return m;
  }

  //port of gluPerspective()
  private Matrix4d getPerspectiveMatrix(){
    double radians=camera.getFovy()/2.0*Math.PI/180.0;
    double sine=Math.sin(radians);
    double cosine=Math.cos(radians);
    double cotangent=cosine/sine;
    double a=cotangent/camera.getAspect();
    double b=cotangent;
    Matrix4d m=
      new Matrix4d(  a, 0.0,  0.0, 0.0,
                   0.0,   b,  0.0, 0.0,
                   0.0, 0.0,  1.0, 1.0,
                   0.0, 0.0, -1.0, 0.0);
    return m;
  }

  //port of gluLookAt()
  private Matrix4d getLookAtMatrix(){
    Vector3d forward=new Vector3d(camera.getAt().x - camera.getEye().x,
                                  camera.getAt().y - camera.getEye().y,
                                  camera.getAt().z - camera.getEye().z);

    Vector3d up=new Vector3d(camera.getUp());

    forward.normalize();
    up.normalize();

    Vector3d side=new Vector3d();
    side.cross(forward,up);
    side.normalize();

    /* Recompute up as: up = side x forward */
    up.cross(side,forward);

    //distance of eye from look at
    double dist=-1.0*camera.getEye().length();

    Matrix4d m=
      new Matrix4d(        side.x,         side.y,          side.z,  0.0,
                             up.x,           up.y,            up.z,  0.0,
                   -1.0*forward.x, -1.0*forward.y,  -1.0*forward.z, dist,
                              0.0,            0.0,             0.0,  1.0);
    return m;


    //glTranslated(-eyex, -eyey, -eyez);
  }

}