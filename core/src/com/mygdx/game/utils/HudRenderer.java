package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Matrix4;

public class HudRenderer{
  public static Matrix4 HudMatrix; 
  

  public static void create(int width, int height, HudWidget[] hudWidget ){
  	HudMatrix = new Matrix4(); 
  	HudMatrix.setToOrtho2D(0, 0, width, height);
  }

  public static void render(Batch batch){

  }


  public class HudWidget{
  }
}
