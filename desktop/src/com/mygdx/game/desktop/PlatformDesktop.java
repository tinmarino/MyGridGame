package com.mygdx.game.desktop;


import com.badlogic.gdx.Gdx;
import com.mygdx.game.utils.Global.PlatformOs;

public class PlatformDesktop implements PlatformOs {
        public Orientation orientation; 
	@Override
	public void setOrientation(Orientation orientation) {
	        this.orientation = orientation; 
		if (orientation == Orientation.PAYSAGE){
			Gdx.app.log("PlatformDesktop","I change to desktop paysage");
			Gdx.graphics.setDisplayMode(480,320,false); //boolean for fullscreen yes or no 
		}
		if (orientation == Orientation.PORTRAIT){
			Gdx.app.log("PlatformDesktop","I change to desktop portrait");
			Gdx.graphics.setDisplayMode(320,480,false); //boolean for fullscreen yes or no 
		}	

	}

	@Override
	public Orientation getOrientation() {
		return orientation;
	}

	@Override
	public int[] getSize() {
	     return new int[]{ Gdx.graphics.getWidth(),Gdx.graphics.getHeight()};
	}
	


}
