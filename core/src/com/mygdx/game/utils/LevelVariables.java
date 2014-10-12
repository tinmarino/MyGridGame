package com.mygdx.game.utils;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.mygdx.game.utils.Global.GamePrefs;

public class LevelVariables {


// And their default value  that won't change in Setting
  public static float DEFAULT_ZOOM = 25;  
  public static int DEFAULT_VELOCITYITERATIONS = 8;
  public static int DEFAULT_POSITIONITERATIONS = 3;
  public static float DEFAULT_TIMESTEP = 1/60f; 

  public float ZOOM = 25; 
  public int   VELOCITYITERATIONS = 8;
  public int   POSITIONITERATIONS = 3;
  public float TIMESTEP = GamePrefs.getTimeStep(GamePrefs.preferences.getInteger(GamePrefs.TIMESTEP));

  public int     life; 
  public boolean canLoseLife = true;
  public boolean isGameOver  = false; 




  public void accelerate(){
      TIMESTEP += 0.000004/TIMESTEP; // for a log acceleration 
      System.out.println("TimeStep " + TIMESTEP); 
  }


  public void loseLife(){
    if (canLoseLife){
       canLoseLife=false; 
       life--; 
       if (life<0){isGameOver=true;return;}
       Timer.schedule(new Task(){
           @Override
           public void run() {canLoseLife=true;}
       }, 1);
    }
  }

}
