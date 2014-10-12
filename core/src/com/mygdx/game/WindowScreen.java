package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.mygdx.game.utils.Global.Global;




public class WindowScreen implements Screen {
        public int wwidth  = Gdx.graphics.getWidth(); 
	public int wheight = Gdx.graphics.getHeight(); 
	public int wx      = 0;
	public int wy      = 0; 

	public Place place       = Place.RIGHT;
	public Instance instance = Instance.WINDOW; 
	public enum Place   {LEFT,RIGHT}
        public enum Instance{WINDOW,GAME}

// DEBUG 
        public Matrix4 HUDMatrix; 
	public BitmapFont red32; 
	public SpriteBatch debugBatch; 
	public float numberOfRender=0, averageDelta=0, OneSec=0; 


/////////////////////////////////////////////////////////////////////////////////////////




	@Override
	public void render(float delta) {if (Global.debug){debugRender(delta);}}
	@Override
	public void show() { 
	  HUDMatrix = new Matrix4(); 
  	  HUDMatrix.setToOrtho2D(0, 0, wwidth, wheight);
	  if (Global.debug){debugShow();} 
	}
	@Override
	public void dispose() {}


	@Override
	public void hide() {}

	@Override
	public void pause() {}


	@Override
	public void resize(int width, int height) {}

	@Override
	public void resume() {}

	



        //***********************MISCELANOUS 
	//****************************************
	//
	

	public void debugShow(){
	    red32 = new BitmapFont(Gdx.files.internal("font/White32.fnt")) ;
	    red32.setColor(1,0,0,1);
	    red32.setScale(0.5f);

	    debugBatch = new SpriteBatch(); 
	}


 	public void debugRender(float delta){
		numberOfRender +=1; 
		OneSec +=delta;
		if (OneSec>1){
			OneSec = 0; 
			averageDelta = numberOfRender;
			numberOfRender = 0;
		}
		debugBatch.begin(); 
	// FPS 
		red32.draw(debugBatch, "FPS: " + Integer.toString((int) averageDelta), 0.05f*wwidth, 0.05f*wheight );
		
	// MEMORY 
		red32.draw(debugBatch, "MEM: " + Float.toString(Gdx.app.getJavaHeap()/1000), 0.05f*wwidth, 0.15f*wheight );
		debugBatch.end(); 


 	}

	public void debugdispose(){
	  debugBatch.dispose(); 
	  red32.dispose(); 
	}
}
