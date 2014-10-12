package com.mygdx.game.utils.Classes;

import com.badlogic.gdx.utils.viewport.Viewport;
/* the Only difference isin the setX and setY */

public class CustomViewport extends Viewport{
	

	
	public CustomViewport(int x, int y, int width, int height){
		setBounds(x, y, width, height);
	}
	
	public  CustomViewport(Viewport viewport){
		this.viewportX      = viewport.getViewportX();
		this.viewportY      = viewport.getViewportY();
		this.viewportWidth  = viewport.getViewportWidth();
		this.viewportHeight = viewport.getViewportHeight();

		this.worldWidth     = viewport.getWorldWidth();
	        this.worldHeight    = viewport.getWorldHeight();
		this.camera         = viewport.getCamera();
	}
	
	public void setBounds(int x, int y, int width, int height){
		this.viewportX = x; 
		this.viewportY = y;
		this.viewportWidth = width;
		this.viewportHeight = height;
	}
	public void setX(int x){
		this.viewportX = x; 
	}
	public void setY(int y){
		this.viewportX = y; 
	}

}
