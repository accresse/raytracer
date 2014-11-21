package javaray.shader;

import java.awt.Color;

import javaray.logic.Ray;
import javaray.logic.Tracer;
import javaray.scene.Scene;
import javaray.scene.SceneObject;

import javax.vecmath.Point3d;

public abstract class ObjectShader {
  public abstract Color getColor(Ray V, Point3d p, Scene scene, Tracer tracer, SceneObject obj);
}