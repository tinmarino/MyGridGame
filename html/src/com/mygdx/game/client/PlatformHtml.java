package com.mygdx.game.client;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.utils.Global.PlatformOs;

public class PlatformHtml implements PlatformOs {

	@Override
	public void setOrientation(Orientation orientation) {
		if (orientation == Orientation.PAYSAGE){
			Gdx.graphics.setDisplayMode(480,320,false); //boolean for fullscreen yes or no 
		}
		if (orientation == Orientation.PORTRAIT){
			Gdx.graphics.setDisplayMode(320,480,false); //boolean for fullscreen yes or no 
		}	

	}

	@Override
	public Orientation getOrientation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getSize() {
		// TODO Auto-generated method stub
		return null;
	}

}
