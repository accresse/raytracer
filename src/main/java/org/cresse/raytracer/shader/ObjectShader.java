package org.cresse.raytracer.shader;

import java.awt.Color;


import javax.vecmath.Point3d;

import org.cresse.raytracer.logic.Ray;
import org.cresse.raytracer.logic.Tracer;
import org.cresse.raytracer.scene.Scene;
import org.cresse.raytracer.scene.SceneObject;

public abstract class ObjectShader {
  public abstract Color getColor(Ray V, Point3d p, Scene scene, Tracer tracer, SceneObject obj);
}