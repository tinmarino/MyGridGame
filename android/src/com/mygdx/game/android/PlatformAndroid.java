package com.mygdx.game.android;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.utils.Global.PlatformOs;




import android.app.Activity;
import android.content.pm.ActivityInfo;


public class PlatformAndroid implements PlatformOs {
	public Activity activity;
	private Orientation orientation;

	@Override
	public void setOrientation(Orientation orientation) {
	   this.orientation = orientation; 
	   if (orientation == Orientation.PAYSAGE){
		   activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	           Gdx.graphics.setDisplayMode(480,320,false); //boolean for fullscreen yes or no 
	   }
	   if (orientation == Orientation.PORTRAIT){
		   activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	           Gdx.graphics.setDisplayMode(320,480,false); //boolean for fullscreen yes or no 
	   }
	}

	@Override
	public Orientation getOrientation() {
		return orientation;
	}

	@Override
	public int[] getSize() {
	        if (orientation == Orientation.PAYSAGE){
		  return new int[]{Gdx.graphics.getHeight(),Gdx.graphics.getWidth()}; 
		}
		else if (orientation == Orientation.PORTRAIT){
		  return new int[]{Gdx.graphics.getWidth(),Gdx.graphics.getHeight()}; 
		}
		else return null; 
	}

}

	   /*
           try {
               Thread.sleep(1000);                 //1000 milliseconds is one second.
           } catch(InterruptedException ex) {
           }	
	   */
