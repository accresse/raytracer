package javaray.scene;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import javaray.shader.Material;
import javaray.shader.TextureMapShader;

import javax.vecmath.Point3d;
import javax.vecmath.Tuple3d;
import javax.vecmath.Vector3d;

/**
 * Description:
 *    Creates a scene from some data.  For now scene is hard coded.  Later could
 *    read from a file.  Want to try XML.
 */

public class SceneParser {

  public Scene parse(){
    return mp2scene();
  }

  private double dist(Tuple3d p1, Tuple3d p2){
    double dx=p1.x-p2.x;
    double dy=p1.y-p2.y;
    double dz=p1.z-p2.z;
    return Math.sqrt(dx*dx+dy*dy*dz*dz);

  }

  public Scene cs318scene(){
    Scene scene=new Scene();
    scene.setBgcolor(new Color((float)0.078, (float)0.361, (float)0.753));
    
    Light[] lights={
    		new Light(new Point3d(4.0,3.0,2.0), Color.white),
    		new Light(new Point3d(1.0,-4.0,4.0), Color.white),
    		new Light(new Point3d(-3.0,1.0,5.0), Color.white)
    };
    
    scene.setLights(lights);
    
    Camera camera=scene.getCamera();
    camera.setWidth(320);
    camera.setHeight(240);
    camera.setEye(new Vector3d(2.1,1.3,1.7));
    camera.setFocalLength(dist(camera.getEye(),new Point3d(1.05,0.95,0.85)));
    camera.setFStop(50.0);
    scene.setJitterNum(16);
    camera.setPinhole(false);
    scene.setCamera(camera);

    Material y=new Material();
    y.setReflDiff(new Color((float)0.8,(float)0.6,(float)0.264));
    y.setShininess(100000);

    Material c=new Material();
    c.setReflDiff(new Color((float)0.4,(float)0.45,(float)0.35));
    c.setReflSpec(new Color((float)0.4,(float)0.45,(float)0.35));
    c.setShininess(45.2776);

    Material g=new Material();
    g.setReflDiff(new Color((float)0.4,(float)0.45,(float)0.35));
    g.setReflSpec(new Color((float)0.4,(float)0.45,(float)0.35));
    g.setReflTrans(new Color((float)0.75,(float)0.75,(float)0.75));
    g.setIr(1.5);
    g.setShininess(45.2776);

    Material r=new Material();
    r.setReflDiff(new Color((float)0.6,(float)0.2,(float)0.2));
    r.setShininess(45.2776);
    
    List<SceneObject> objects=new LinkedList<SceneObject>();
    
    SceneObject sph=new SceneSphere(new Point3d(0.0,0.0,-30.0),29.5,y);
    objects.add(sph);

    sph=new SceneSphere(new Point3d(0.0,0.0,0.0),0.5,c);
    objects.add(sph);

    sph=new SceneSphere(new Point3d(0.272166, 0.272166, 0.544331), 0.166667, c);
    objects.add(sph);
    sph=new SceneSphere(new Point3d(0.643951, 0.172546, 5.23308e-17), 0.166667, c);
    objects.add(sph);
    sph=new SceneSphere(new Point3d(0.172546, 0.643951, 5.23308e-17), 0.166667, c);
    objects.add(sph);
    sph=new SceneSphere(new Point3d(-0.371785, 0.0996195, 0.544331), 0.166667, c);
    objects.add(sph);
    sph=new SceneSphere(new Point3d(-0.471405, 0.471405, 5.23308e-17), 0.166667, c);
    objects.add(sph);
    sph=new SceneSphere(new Point3d(-0.643951, -0.172546, 5.23308e-17), 0.166667, c);
    objects.add(sph);
    sph=new SceneSphere(new Point3d(0.0996195, -0.371785, 0.544331), 0.166667, c);
    objects.add(sph);
    sph=new SceneSphere(new Point3d(-0.172546, -0.643951, 5.23308e-17), 0.166667, c);
    objects.add(sph);
    sph=new SceneSphere(new Point3d(0.471405, -0.471405, 5.23308e-17), 0.166667, c);
    objects.add(sph);

    //double theta=55.0*Math.PI/180.0;

    //sph=new SceneSphere(new Point3d(1.5*Math.sin(theta),1.5*Math.cos(theta),1.2), 0.1, g);
    sph=new SceneSphere(new Point3d(1.05,0.95,0.85), 0.1, r);
    objects.add(sph);
    
    Object[] oa=objects.toArray();
    SceneObject[] soa=new SceneObject[oa.length];
    for (int i = 0; i < soa.length; i++) {
		soa[i]=(SceneObject)oa[i];
	}
    scene.setObjects(soa);
    
    scene.setSaveAs("cresse1.jpg");

    return scene;
  }


  public Scene mp1scene(){
    Scene scene=new Scene();
    /*scene.setBgcolor(new Color((float)0.078, (float)0.361, (float)0.753));
    scene.addLight(new Light(new Point3d(-10.0,10.0,2.0), Color.red));
    scene.addLight(new Light(new Point3d(0.0,10.0,2.0), Color.green));
    scene.addLight(new Light(new Point3d(10.0,10.0,2.0), Color.blue));
    scene.addLight(new Light(new Point3d(-10.0,10.0,-2.0), Color.blue));
    scene.addLight(new Light(new Point3d(0.0,10.0,-2.0), Color.green));
    scene.addLight(new Light(new Point3d(10.0,10.0,-2.0), Color.red));*/
    Light[] lights={
    	new Light(new Point3d(-0.25,1.0,2.0), Color.red),
    	new Light(new Point3d(0.0,1.0,2.0), Color.green),
    	new Light(new Point3d(0.25,1.0,2.0), Color.blue),
    	new Light(new Point3d(0.0,10.0,-1.0), Color.white)
    };
    scene.setLights(lights);
    
    Camera camera=scene.getCamera();
    camera.setWidth(640);
    camera.setHeight(480);
    camera.setEye(new Vector3d(0.0,-1.0,2.0));
    camera.setAt(new Vector3d(0.0,0.0,0.0));
    camera.setUp(new Vector3d(0.0,1.0,0.0));
    scene.setCamera(camera);

    Material red=new Material();
    red.setReflDiff(new Color((float)0.8,(float)0.0,(float)0.0));
    red.setShininess(100000);

    Material green=new Material();
    green.setReflDiff(new Color((float)0.0,(float)0.8,(float)0.0));
    green.setShininess(100000);

    Material purple=new Material();
    purple.setReflDiff(new Color((float)0.7,(float)0.0,(float)0.8));
    purple.setShininess(100000);

    Material yellow=new Material();
    yellow.setReflDiff(new Color((float)1.0,(float)1.0,(float)0.0));
    yellow.setShininess(100000);

    Material white=new Material();
    white.setReflDiff(new Color((float)1.0,(float)1.0,(float)1.0));
    white.setShininess(100000);

    Material mirror=new Material();
    mirror.setReflDiff(new Color((float)0.4,(float)0.45,(float)0.35));
    mirror.setReflSpec(new Color((float)0.4,(float)0.45,(float)0.35));
    mirror.setShininess(45.2776);

    Material glass=new Material();
    glass.setReflDiff(new Color((float)0.4,(float)0.45,(float)0.35));
    glass.setReflSpec(new Color((float)0.4,(float)0.45,(float)0.35));
    //glass.setReflTrans(new Color((float)0.75,(float)0.75,(float)0.75));
    glass.setReflTrans(new Color((float)0.8,(float)0.8,(float)0.8));
    glass.setIr(1.5);
    glass.setShininess(45.2776);

    List<SceneObject> objects=new LinkedList<SceneObject>();
    
    SceneQuadric so=new SceneQuadric();
    so.x2=1.0;
    so.z2=5.0;
    so.y=-.2;
    so.setMaterial(glass);
    objects.add(so);

    so=new SceneQuadric();
    so.x2=1.0;
    so.z2=5.0;
    so.y=.2;
    so.setMaterial(mirror);
    objects.add(so);

    SceneSphere sph=new SceneSphere(new Point3d(-0.7,0.0,0.5),.25,red);
    objects.add(sph);

    sph=new SceneSphere(new Point3d(0.0,0.0,-0.5),.25,purple);
    //scene.addObject(sph);

    sph=new SceneSphere(new Point3d(0.7,0.0,0.5),.25,green);
    objects.add(sph);

    sph=new SceneSphere(new Point3d(0.0,0.5,-0.5),.25,yellow);
    objects.add(sph);

    Object[] oa=objects.toArray();
    SceneObject[] soa=new SceneObject[oa.length];
    for (int i = 0; i < soa.length; i++) {
		soa[i]=(SceneObject)oa[i];
	}
    scene.setObjects(soa);

    //sph=new SceneSphere(new Point3d(0.0,0.0,0.0),.6,white);
    //scene.addObject(sph);
    scene.setSaveAs("cresse3.jpg");

    return scene;
  }

  public Scene mp2scene(){
    Scene scene=new Scene();
    scene.setBgcolor(new Color((float)0.078, (float)0.361, (float)0.753));
    Light[] lights={
    	new Light(new Point3d(2.0,5.0,2.0), new Color((float)0.333,(float)0.333,(float)0.333)),
    	new Light(new Point3d(1.0,5.0,2.0), new Color((float)0.333,(float)0.333,(float)0.333)),
    	new Light(new Point3d(2.0,5.0,1.0), new Color((float)0.333,(float)0.333,(float)0.333))
    };
    scene.setLights(lights);
    
    Camera camera=scene.getCamera();
    camera.setWidth(320);
    camera.setHeight(240);
    camera.setEye(new Vector3d(2.0,0.25,2.0));
    camera.setAt(new Vector3d(0.0,0.0,0.0));
    camera.setUp(new Vector3d(0.0,1.0,0.0));
    camera.setFocalLength(dist(camera.getEye(),new Point3d(0.5,-0.5,0.5)));
    camera.setFStop(50.0);
    //camera.setPinhole(true);
    //scene.setJitterNum(1);
    camera.setPinhole(false);
    scene.setJitterNum(16);
    scene.setCamera(camera);

    Material red=new Material();
    red.setReflDiff(new Color((float)0.8,(float)0.0,(float)0.0));
    red.setReflSpec(new Color((float)0.5,(float)0.5,(float)0.5));
    red.setShininess(45);

    Material blue=new Material();
    blue.setReflDiff(new Color((float)0.0,(float)0.0,(float)0.8));
    blue.setReflSpec(new Color((float)0.5,(float)0.5,(float)0.5));
    blue.setShininess(45);

    Material green=new Material();
    green.setReflDiff(new Color((float)0.0,(float)0.8,(float)0.0));
    green.setReflSpec(new Color((float)0.5,(float)0.5,(float)0.5));
    green.setShininess(45);

    Material white=new Material();
    white.setReflDiff(new Color((float)1.0,(float)1.0,(float)1.0));
    white.setShininess(100000);

    Material mirror=new Material();
    mirror.setReflDiff(new Color((float)0.4,(float)0.45,(float)0.35));
    mirror.setReflSpec(new Color((float)0.4,(float)0.45,(float)0.35));
    mirror.setShininess(45.2776);

    Material glass=new Material();
    glass.setReflDiff(new Color((float)0.4,(float)0.45,(float)0.35));
    glass.setReflSpec(new Color((float)0.4,(float)0.45,(float)0.35));
    //glass.setReflTrans(new Color((float)0.75,(float)0.75,(float)0.75));
    glass.setReflTrans(new Color((float)0.8,(float)0.8,(float)0.8));
    glass.setIr(1.5);
    glass.setShininess(45.2776);

    SceneSphere sph;
    List<SceneObject> objects=new LinkedList<SceneObject>();

    sph=new SceneSphere(new Point3d(-.5,0.5,-0.5),.25,mirror);
    objects.add(sph);

    sph=new SceneSphere(new Point3d(0.0,0.0,0.0),.25,mirror);
    objects.add(sph);

    sph=new SceneSphere(new Point3d(0.5,-0.5,0.5),.25,mirror);
    objects.add(sph);

    ScenePlane pl;

    pl=new ScenePlane();
    pl.setNormal(new Vector3d(0.0,0.0,-1.0));
    pl.setCenter(new Point3d(0.0,0.0,-1.0));
    pl.setMaterial(red);
    pl.setCropped(false);
    objects.add(pl);

    //bottom
    pl=new ScenePlane();
    pl.setNormal(new Vector3d(0.0,-1.0,0.0));
    pl.setCenter(new Point3d(0.0,-1.0,0.0));
    pl.setShader(new TextureMapShader("check.gif",mirror));
    //pl.setMaterial(white);
    pl.setCropped(false);
    objects.add(pl);

    pl=new ScenePlane();
    pl.setNormal(new Vector3d(-1.0,0.0,0.0));
    pl.setCenter(new Point3d(-1.0,0.0,0.0));
    pl.setMaterial(blue);
    pl.setCropped(false);
    objects.add(pl);

    Object[] oa=objects.toArray();
    SceneObject[] soa=new SceneObject[oa.length];
    for (int i = 0; i < soa.length; i++) {
		soa[i]=(SceneObject)oa[i];
	}
    scene.setObjects(soa);

    scene.setSave(false);
    scene.setSaveAs("dof_640x480x16.bmp");

    return scene;
  }

  public Scene dna(){
    Scene scene=new Scene();
    scene.setBgcolor(new Color((float)0.2, (float)0.2, (float)0.2));
    boolean colored=false;
    Light[] lights=new Light[3];
    if(colored){
    	lights[0]=(new Light(new Point3d(-1.0,2.0,2.0), Color.red));
    	lights[1]=(new Light(new Point3d(0.0,2.0,2.0), Color.green));
    	lights[2]=(new Light(new Point3d(1.0,2.0,2.0), Color.blue));
    } else{
    	lights[0]=(new Light(new Point3d(-10.0,10.0,10.0), Color.white));
    	lights[1]=(new Light(new Point3d(0.0,10.0,10.0), Color.white));
    	lights[2]=(new Light(new Point3d(10.0,10.0,10.0), Color.white));
    }
    scene.setLights(lights);
    
    Camera camera=scene.getCamera();
    camera.setWidth(320);
    camera.setHeight(240);
    //camera.setEye(new Vector3d(-3.0,0.0,5.0));
    camera.setEye(new Vector3d(-4.0,0.0,0.0));
    //camera.setEye(new Vector3d(0.0,0.0,4.0));
    camera.setAt(new Vector3d(0.0,0.0,0.0));
    camera.setUp(new Vector3d(0.0,1.0,0.0));
    //camera.setFStop(300.0);
    //camera.setFocalLength(6.0);
    camera.setFStop(40.0);
    //x=-.7
    //double dist=5.494666711269765;
    double dist=1.0;
    camera.setFocalLength(dist);
    camera.setPinhole(false);
    scene.setJitterNum(16);
    scene.setCamera(camera);

    Material red=new Material();
    red.setReflDiff(new Color((float)0.6,(float)0.2,(float)0.2));
    red.setReflSpec(new Color((float)0.6,(float)0.6,(float)0.6));
    red.setShininess(45);

    Material blue=new Material();
    blue.setReflDiff(new Color((float)0.2,(float)0.2,(float)0.6));
    blue.setReflSpec(new Color((float)0.6,(float)0.6,(float)0.6));
    blue.setShininess(45);

    Material yellow=new Material();
    yellow.setReflDiff(new Color((float)0.6,(float)0.6,(float)0.2));
    yellow.setReflSpec(new Color((float)0.6,(float)0.6,(float)0.6));
    yellow.setShininess(45);

    Material mirror=new Material();
    mirror.setReflDiff(new Color((float)0.0,(float)0.5,(float)0.7));
    mirror.setReflSpec(new Color((float)0.8,(float)0.8,(float)0.8));
    mirror.setShininess(100);

    Material glass=new Material();
    glass.setReflDiff(new Color((float)0.4,(float)0.45,(float)0.35));
    glass.setReflSpec(new Color((float)0.4,(float)0.45,(float)0.35));
    glass.setReflTrans(new Color((float)0.8,(float)0.8,(float)0.8));
    glass.setIr(1.5);
    glass.setShininess(45.2776);

    SceneSphere sph;
    double range=1.5;
    int i=0;
    
    List<SceneObject> objects=new LinkedList<SceneObject>();
    
    for(double x=-range; x<range; x+=.05){
      sph=new SceneSphere(new Point3d(3.0*x,Math.cos(2.0*Math.PI*x),Math.sin(2.0*Math.PI*x)),.15,red);
      objects.add(sph);

      sph=new SceneSphere(new Point3d(3.0*(x+0.5),Math.cos(2.0*Math.PI*x),Math.sin(2.0*Math.PI*x)),.15,blue);
      objects.add(sph);
      i++;
    }

    SceneQuadric cyl=new SceneQuadric();
    cyl.setMaterial(glass);
    cyl.y2=1.0;
    cyl.z2=1.0;
    cyl.k=-0.5;
    //scene.addObject(cyl);

    ScenePlane pl=new ScenePlane();
    pl.setCenter(new Point3d(-1.0,0.0,0.0));
    pl.setNormal(new Vector3d(-1.0,0.0,0.0));
    pl.setMaterial(mirror);
    //scene.addObject(pl);

    Object[] oa=objects.toArray();
    SceneObject[] soa=new SceneObject[oa.length];
    for (int j = 0; j < soa.length; j++) {
		soa[i]=(SceneObject)oa[i];
	}
    scene.setObjects(soa);
    
    scene.setSave(false);
    scene.setSaveAs("c:\\Documents and Settings\\Adam Cresse\\Desktop\\workspace\\dna_640x480x16_dof.bmp");

    return scene;
  }

//  private Scene testScene(){
//    Scene scene=new Scene();
//    scene.setBgcolor(new Color((float)0.078, (float)0.361, (float)0.753));
//    Light[] lights={
//    	new Light(new Point3d(2.0,5.0,2.0), new Color((float)0.333,(float)0.333,(float)0.333)),
//    	new Light(new Point3d(1.0,5.0,2.0), new Color((float)0.333,(float)0.333,(float)0.333)),
//    	new Light(new Point3d(2.0,5.0,1.0), new Color((float)0.333,(float)0.333,(float)0.333))
//    };
//    scene.setLights(lights);
//    
//    Camera camera=scene.getCamera();
//    camera.setWidth(320);
//    camera.setHeight(240);
//    camera.setEye(new Vector3d(2.0,0.25,2.0));
//    //camera.setEye(new Vector3d(0.0,0.0,2.0));
//    camera.setAt(new Vector3d(0.0,0.0,0.0));
//    camera.setUp(new Vector3d(0.0,1.0,0.0));
//    camera.setFocalLength(dist(camera.getEye(),new Point3d(0.5,-0.5,0.5)));
//    camera.setFStop(40.0);
//    //camera.setPinhole(false);
//    //scene.setJitterNum(16);
//    camera.setPinhole(true);
//    scene.setJitterNum(1);
//    scene.setCamera(camera);
//
//    Material red=new Material();
//    red.setReflDiff(new Color((float)0.8,(float)0.0,(float)0.0));
//    red.setReflSpec(new Color((float)0.5,(float)0.5,(float)0.5));
//    red.setShininess(45);
//
//    Material blue=new Material();
//    blue.setReflDiff(new Color((float)0.0,(float)0.0,(float)0.8));
//    blue.setReflSpec(new Color((float)0.5,(float)0.5,(float)0.5));
//    blue.setShininess(45);
//
//    Material green=new Material();
//    green.setReflDiff(new Color((float)0.0,(float)0.8,(float)0.0));
//    green.setReflSpec(new Color((float)0.5,(float)0.5,(float)0.5));
//    green.setShininess(45);
//
//    Material white=new Material();
//    white.setReflDiff(new Color((float)1.0,(float)1.0,(float)1.0));
//    white.setShininess(100000);
//
//    Material mirror=new Material();
//    mirror.setReflDiff(new Color((float)0.4,(float)0.45,(float)0.35));
//    mirror.setReflSpec(new Color((float)0.4,(float)0.45,(float)0.35));
//    mirror.setShininess(45.2776);
//
//    Material glass=new Material();
//    glass.setReflDiff(new Color((float)0.4,(float)0.45,(float)0.35));
//    glass.setReflSpec(new Color((float)0.4,(float)0.45,(float)0.35));
//    //glass.setReflTrans(new Color((float)0.75,(float)0.75,(float)0.75));
//    glass.setReflTrans(new Color((float)0.8,(float)0.8,(float)0.8));
//    glass.setIr(1.5);
//    glass.setShininess(45.2776);
//
//    SceneSphere sph;
//
//    List<SceneObject> objects=new LinkedList<SceneObject>();
//    
//    sph=new SceneSphere(new Point3d(-.5,0.5,-0.5),.25,mirror);
//    objects.add(sph);
//
//    sph=new SceneSphere(new Point3d(0.0,0.0,0.0),.25,mirror);
//    objects.add(sph);
//
//    sph=new SceneSphere(new Point3d(0.5,-0.5,0.5),.25,mirror);
//    objects.add(sph);
//
//    ScenePlane pl;
//
//    //left
//    pl=new ScenePlane();
//    pl.setNormal(new Vector3d(-1.0,0.0,0.0));
//    pl.setCenter(new Point3d(-1.0,0.0,0.0));
//    pl.setMaterial(blue);
//    pl.setCropped(false);
//    objects.add(pl);
//
//    //right
//    pl=new ScenePlane();
//    pl.setNormal(new Vector3d(0.0,0.0,-1.0));
//    pl.setCenter(new Point3d(0.0,0.0,-1.0));
//    pl.setMaterial(red);
//    pl.setCropped(false);
//    objects.add(pl);
//
//
//    Matrix4d r = new Matrix4d();
//    r.setIdentity();
//    r.rotX(-90.0);
//    Matrix4d t = new Matrix4d();
//    t.setIdentity();
//    t.setTranslation(new Vector3d(0.0,0.0,-1.0));
//    Matrix4d m = new Matrix4d();
//    m.mul(r,t);
//    r = new Matrix4d();
//    r.setIdentity();
//    r.rotZ(45.0);
//    m.mul(m,r);
//
//    //bottom
//    pl=new ScenePlane();
//    //pl.setNormal(new Vector3d(0.0,-1.0,0.0));
//    //pl.setCenter(new Point3d(0.0,-1.0,0.0));
//    pl.setTransform(m);
//      ObjectShader sh=new TextureMapShader("C:\\Documents and Settings\\Adam Cresse\\jbproject\\javaray\\classes\\check.gif",mirror);
//      pl.setShader(sh);
//    //pl.setMaterial(white);
//    pl.setCropped(false);
//    objects.add(pl);
//    
//    Object[] oa=objects.toArray();
//    SceneObject[] soa=new SceneObject[oa.length];
//    for (int i = 0; i < soa.length; i++) {
//		soa[i]=(SceneObject)oa[i];
//	}
//    scene.setObjects(soa);
//    
//    scene.setSave(!true);
//    scene.setSaveAs("dof_1024x768x16.bmp");
//
//    return scene;
//  }
//
//  private Scene me(){
//    Scene scene=new Scene();
//    scene.setBgcolor(new Color((float)0.078, (float)0.361, (float)0.753));
//    Light[] lights={
//    	new Light(new Point3d(-1.0,5.0,2.0), Color.white),
//    	new Light(new Point3d(0.0,5.0,2.0), Color.white),
//    	new Light(new Point3d(1.0,5.0,2.0), Color.white)
//    };
//    scene.setLights(lights);
//    
//    Camera camera=scene.getCamera();
//    camera.setWidth(320);
//    camera.setHeight(320);
//    camera.setEye(new Vector3d(0.0,0.0,1.0));
//    camera.setAt(new Vector3d(0.0,0.0,0.0));
//    camera.setUp(new Vector3d(0.0,1.0,0.0));
//    camera.setFocalLength(dist(camera.getEye(),new Point3d(0.5,-0.5,0.5)));
//    camera.setFStop(50.0);
//    camera.setPinhole(true);
//    scene.setJitterNum(1);
//    //camera.setPinhole(false);
//    //scene.setJitterNum(16);
//    scene.setCamera(camera);
//
//    Material red=new Material();
//    red.setReflDiff(new Color((float)0.8,(float)0.0,(float)0.0));
//    red.setReflSpec(new Color((float)0.5,(float)0.5,(float)0.5));
//    red.setShininess(45);
//
//    Material blue=new Material();
//    blue.setReflDiff(new Color((float)0.0,(float)0.0,(float)0.8));
//    blue.setReflSpec(new Color((float)0.5,(float)0.5,(float)0.5));
//    blue.setShininess(45);
//
//    Material green=new Material();
//    green.setReflDiff(new Color((float)0.0,(float)0.8,(float)0.0));
//    green.setReflSpec(new Color((float)0.5,(float)0.5,(float)0.5));
//    green.setShininess(45);
//
//    Material white=new Material();
//    white.setReflDiff(new Color((float)1.0,(float)1.0,(float)1.0));
//    white.setShininess(100000);
//
//    Material mirror=new Material();
//    mirror.setReflDiff(new Color((float)0.4,(float)0.45,(float)0.35));
//    mirror.setReflSpec(new Color((float)0.4,(float)0.45,(float)0.35));
//    mirror.setShininess(45.2776);
//
//    Material glass=new Material();
//    glass.setReflDiff(new Color((float)0.4,(float)0.45,(float)0.35));
//    glass.setReflSpec(new Color((float)0.4,(float)0.45,(float)0.35));
//    //glass.setReflTrans(new Color((float)0.75,(float)0.75,(float)0.75));
//    glass.setReflTrans(new Color((float)0.8,(float)0.8,(float)0.8));
//    glass.setIr(1.5);
//    glass.setShininess(45.2776);
//
//    SceneSphere sph;
//    List<SceneObject> objects=new LinkedList<SceneObject>();
//
//    sph=new SceneSphere(new Point3d(-.5,0.5,-0.5),.25,mirror);
//    //scene.addObject(sph);
//
//    sph=new SceneSphere(new Point3d(0.0,0.0,0.0),.25,mirror);
//    //scene.addObject(sph);
//
//    sph=new SceneSphere(new Point3d(0.5,-0.5,0.5),.25,mirror);
//    //scene.addObject(sph);
//
//    ScenePlane pl;
//
//    //bottom
//    pl=new ScenePlane();
//    pl.setNormal(new Vector3d(0.0,0.0,-1.0));
//    pl.setCenter(new Point3d(0.5,0.0,0.0));
//    pl.setShader(new TextureMapShader("me.jpg",white));
//    //pl.setMaterial(white);
//    pl.setCropped(false);
//    objects.add(pl);
//
//    Object[] oa=objects.toArray();
//    SceneObject[] soa=new SceneObject[oa.length];
//    for (int i = 0; i < soa.length; i++) {
//		soa[i]=(SceneObject)oa[i];
//	}
//    scene.setObjects(soa);
//
//    scene.setSave(!true);
//    scene.setSaveAs("dof_640x480x16.bmp");
//
//    return scene;
//
//  }
}