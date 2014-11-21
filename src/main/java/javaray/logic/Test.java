package javaray.logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import javaray.scene.CSGObject;
import javaray.scene.SceneObject;
import javaray.scene.SceneSphere;
import javaray.shader.Material;

import javax.vecmath.Point3d;

/**
 * Description:
 *    Used to test various new features as they are added.
 *    NOT NEEDED.
 */

public class Test {
  public static void main(String[] args) throws Exception{
    SceneObject A=new SceneSphere(new Point3d(-0.25,0.0,0.0),0.5,Material.DEFAULT);
    SceneObject B=new SceneSphere(new Point3d(0.25,0.0,0.0),0.5,Material.DEFAULT);
    CSGObject csg=new CSGObject();
    csg.setOperation(CSGObject.INTERSECTION);
    csg.setA(A);
    csg.setB(B);
    BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
    while(true){
      System.out.print("> ");
      String point=in.readLine();
      StringTokenizer st=new StringTokenizer(point,", ");
      Point3d p=new Point3d();
      p.x=Double.parseDouble(st.nextToken());
      p.y=Double.parseDouble(st.nextToken());
      p.z=Double.parseDouble(st.nextToken());
      System.out.println("A: "+A.isOn(p));
      System.out.println("B: "+B.isOn(p));
      //System.out.println("CSG: "+csg.isOn(p));
    }
  }
}