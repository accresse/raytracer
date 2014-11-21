package org.cresse.raytracer.shader;

import java.awt.Color;

/**
 * Description:
 *    Stores all info on a Material
 */

public class Material {

  private Color emit=Color.black;        //Light being emitted
  private Color reflAmb=Color.black;     //Ambient reflectance
  private Color reflDiff=Color.black;    //Diffuse reflectance
  private Color reflSpec=Color.black;    //Specular reflectance (for reflection)
  private Color reflTrans=Color.black;   //Specular transmittance (for transmission)
  private double shininess=0.0;          //The Phong specular exponent
  private double ir=1.0;                 //Index of refraction

  public static final Material DEFAULT=new Material();

  public Material(){}

  public Material(Color emit, Color reflAmb, Color reflDiff, Color reflSpec, Color reflTrans, double shininess, double ir){
    this.emit=emit;
    this.reflAmb=reflAmb;
    this.reflDiff=reflDiff;
    this.reflSpec=reflSpec;
    this.reflTrans=reflTrans;
    this.shininess=shininess;
    this.ir=ir;
  }

  //getters and setters
  public void setEmit(Color emit){ this.emit=emit; }
  public Color getEmit(){ return emit; }

  public void setReflAmb(Color reflAmb){ this.reflAmb=reflAmb; }
  public Color getReflAmb(){ return reflAmb; }

  public void setReflDiff(Color reflDiff){ this.reflDiff=reflDiff; }
  public Color getReflDiff(){ return reflDiff; }

  public void setReflSpec(Color reflSpec){ this.reflSpec=reflSpec; }
  public Color getReflSpec(){ return reflSpec; }

  public void setReflTrans(Color reflTrans){ this.reflTrans=reflTrans; }
  public Color getReflTrans(){ return reflTrans; }

  public void setShininess(double shininess){ this.shininess=shininess; }
  public double getShininess(){ return shininess; }

  public void setIr(double ir){ this.ir=ir; }
  public double getIr(){ return ir; }
}