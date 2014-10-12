package com.mygdx.game.utils.Global;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.mygdx.game.WindowScreen;
import com.mygdx.game.utils.LevelVariables;
import com.mygdx.game.utils.Classes.GameScreen;
import com.mygdx.game.utils.Global.GameList.ClassName;
import com.mygdx.game.utils.Menu.DoubleScreen;
import com.mygdx.game.utils.Menu.SingleScreen;

public class GamePrefs {
  public static Preferences preferences;
  
  public static String VOLUME   =  "volume"; 
  public static String IS_MUTE  =  "isMute"; 
  public static String DEBUG    =  "debug" ; 
  public static String TIMESTEP =  "timestep" ; 


  public static void loadPreferences(){
     preferences = Gdx.app.getPreferences("setting"); 
     if (!preferences.get().containsKey(VOLUME))     {preferences.putFloat  (VOLUME  , 0.5f);}
     if (!preferences.get().containsKey(IS_MUTE))    {preferences.putBoolean(IS_MUTE ,false);}
     if (!preferences.get().containsKey(DEBUG))      {preferences.putBoolean(DEBUG   ,false);}
     if (!preferences.get().containsKey(TIMESTEP))   {preferences.putInteger(TIMESTEP,5    );}
     Global.debug = preferences.getBoolean(DEBUG); 
  }



  public static float getTimeStep(float slider){
    return (float) (LevelVariables.DEFAULT_TIMESTEP * Math.pow(1.25,(slider-5))); 
  }

  public static void putTimeStep(WindowScreen windowScreen,float slider){
       if (windowScreen.instance != WindowScreen.Instance.GAME) {return ;}
       GameScreen gameScreen = (GameScreen) (windowScreen); 

       if (gameScreen.getLevelVar() != null){
	    System.out.println("GameClassName"  +gameScreen.gameClass.className); 
            if (gameScreen.gameClass.className != null && gameScreen.gameClass.className == ClassName.TRY_HARD){
	        ((com.mygdx.game.Games.TryHard.LevelVar) gameScreen.getLevelVar()).levelSpeed *= getTimeStep(slider)/LevelVariables.DEFAULT_TIMESTEP; 
		System.out.println("GamePrefs I go there ") ;
		return ; 
                          
            }
            gameScreen.getLevelVar().TIMESTEP = getTimeStep(slider) ; 
       }

  }

  public static void calculateTimeStep(float slider){
      preferences.putInteger(GamePrefs.TIMESTEP,(int) slider);
      preferences.flush();

      if ( Global.screenCaller != null ){
	  if (Global.screenCaller.instance == SingleScreen.Instance.SINGLE){
		if (Global.screenCaller.lastWindow  != null && Global.screenCaller.lastWindow.instance == WindowScreen.Instance.GAME ){
		  putTimeStep(Global.screenCaller.lastWindow,slider); 
	        }
          }

	  else if (Global.screenCaller.instance == SingleScreen.Instance.DOUBLE){
		  putTimeStep(((DoubleScreen)Global.screenCaller).window_left,slider); 
		  putTimeStep(((DoubleScreen)Global.screenCaller).window_right,slider); 
          }
      }
  }

}
