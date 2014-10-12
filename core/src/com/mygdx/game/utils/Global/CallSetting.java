package com.mygdx.game.utils.Global;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.WindowScreen;
import com.mygdx.game.utils.Menu.SettingScreen;




public class CallSetting{
  public static WindowScreen lastWindow; 

  

  public static void callSetting(){
      
  }


  public static void restoreScreen(){
  }

  public static InputListener backListener(WindowScreen caller){
        lastWindow = caller; 
        InputListener res = new InputListener(){
	   @Override
	   public boolean keyDown(InputEvent event, int keycode) {
               return settingKeyTyped(keycode);  
	}}; 
	
	return res; 
  }

  public static boolean  settingKeyTyped(int keycode){
	    switch (keycode){
	      case Keys.BACK   : 
	      case Keys.ESCAPE : 
	      case Keys.B      : 
	      case Keys.MENU   :
                  Global.game.setScreen( new SettingScreen() ) ;
	      default : 
	          return false; 
	    }
  }


  public static InputListener getListener(){
      InputListener res = new InputListener(){
          @Override
	  public boolean keyDown(InputEvent event, int keycode) {
	      switch (keycode){
	        case Keys.BACK   : 
	        case Keys.ESCAPE : 
	        case Keys.B      : 
	        case Keys.MENU   :
	           Global.game.setScreen( lastWindow );
	           return true;  
	        default : 
	            return false; 
	      }  
          } 
      }; 
      return res; 
  }








}
