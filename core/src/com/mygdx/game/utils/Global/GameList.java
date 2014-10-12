package com.mygdx.game.utils.Global;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.WindowScreen;
import com.mygdx.game.Games.FourColor.FourColorScreen;
import com.mygdx.game.Games.StarSwitch.StarSwitchScreen;
import com.mygdx.game.Games.TryHard.TryHardScreen;
import com.mygdx.game.Games.Volley.VolleyScreen;
import com.mygdx.game.Games.Renaud.RenaudScreen;
import com.mygdx.game.utils.Classes.GameScreen;

public class GameList{

  public static ArrayList<GameClass> getGameList(){
    ArrayList<GameClass> res = new ArrayList<GameClass>(); 

    GameClass A1   =   new GameClass() ;
    A1.title       =   "Renault"  ; 
    A1.className   =   ClassName.RENAULT ; 
    A1.textureName =   "img/game/car/car_icon.png";
    res.add(A1); 

    GameClass A2   =   new GameClass() ;
    A2.title       =   "Four Color" ; 
    A2.className   =   ClassName.FOUR_COLOR;
    A2.textureName =   "img/game/four_color/four_color_icon.jpg";
    res.add(A2); 



    GameClass A3   =   new GameClass() ;
    A3.title       =   "Try Hard" ; 
    A3.className   =   ClassName.TRY_HARD;
    A3.textureName =   "img/game/try_hard/try_hard_icon.png";
    res.add(A3); 

    GameClass A4   =   new GameClass() ;
    A4.title       =   "Volley" ; 
    A4.className   =   ClassName.VOLLEY;
    A4.textureName =   "img/game/volley/volley_icon.png";
    res.add(A4); 


    GameClass A5   =   new GameClass() ;
    A5.title       =   "Star Switch" ; 
    A5.className   =   ClassName.STAR_SWITCH;
    A5.textureName =   "img/game/star_switch/spaceshift.png";
    res.add(A5); 

    for ( GameClass i : res ){i.loadTexture();}
    return res;  
  }





  public static enum ClassName{
      RENAULT,
      FOUR_COLOR, 
      TRY_HARD,
      VOLLEY,
      STAR_SWITCH,
  }

  // Because html doesn't support well refraction, 
  public static GameScreen GameRefractor(ClassName className){
    GameScreen res=null; 
    switch (className) {
      case RENAULT : 
	res = new RenaudScreen()    ; break;
      case FOUR_COLOR : 
	res = new FourColorScreen() ; break; 
      case TRY_HARD : 
	res = new TryHardScreen()   ; break; 
      case VOLLEY : 
	res = new VolleyScreen()    ; break; 
      case STAR_SWITCH : 
	res = new StarSwitchScreen(); break; 
    }
    if (res != null){ 
      res.gameClass = new GameClass(); 
      res.gameClass.className = className ;
    }
    return res; 
  }

  // the string is the className
  public static WindowScreen getScreen(String className ){
        WindowScreen res = null; 
        if ( className == "RenaudScreen" )   { res = new RenaudScreen(); }
        return res; 
  }







  public static void disposeList(ArrayList<GameClass> list){
    for ( GameClass i : list ){
      if (i.texture != null ){
	  i.texture.dispose(); 
      }
    }
  }






  public static class GameClass{
      public String title; 
      public ClassName className; 
             // please put the full class name, because sometimes no import 
      public String textureName = null; // in assets 
      public Texture texture; // to know where to dispose 

      public GameClass(){}  
      public void loadTexture(){
          if (textureName != null){
            texture = new Texture(textureName) ;
          }
      }
  }



}
