package com.mygdx.game.utils.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.WindowScreen;
import com.mygdx.game.utils.Classes.GameScreen;
import com.mygdx.game.utils.Global.Global;


public class SingleScreen implements Screen {
        public WindowScreen window; 
	public WindowScreen lastWindow; 
	public Instance     instance = Instance.SINGLE; 


	public enum Instance{SINGLE,DOUBLE}
  
	public SingleScreen(){} // defautl constructor for inheritance

	public SingleScreen(WindowScreen window){
	   this.window            = window; 
	}

	public void changeWindow(WindowScreen window, WindowScreen caller){
	   if (caller != null ) {caller.dispose() ;}
	   this.window = window; 
	   this.show() ; 
	}
	 
	public void callPause(){
	   // save last things 
	   lastWindow   = window; 
	   // open window
	   window       = new PauseScreen(); 
	   window.show(); 
	   resize(window.wwidth, window.wheight); 
	}
	public void callSetting(){
	   lastWindow   = window; 
	   window       = new SettingScreen(); 
	   window.show(); 
	   resize(window.wwidth, window.wheight); 
	}

	public void callGameOver(GameScreen gameScreen){
	   window = new GameOverScreen(gameScreen); 
	   window.show(); 
	   resize(window.wwidth,window.wheight); 
	}

	public void restoreScreen(){
	   window.dispose();
	   window = lastWindow; // and I will render  
	   Gdx.input.setInputProcessor( Global.inputMultiplexer ) ; 
	}



	@Override
	public void show() {
	  System.out.println("singlescreenSHoW");
	  Global.screenCaller    = this  ; 
	  Global.inputMultiplexer = new InputMultiplexer(); 
	  Gdx.input.setInputProcessor( Global.inputMultiplexer );
	  window.show();
	}
	@Override
	public void render(float delta) {
	  Gdx.gl.glClearColor(0, 0, 0, 1);
	  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	  window.render(delta);
	}
	@Override
	public void dispose() {window.dispose();}



	@Override
	public void hide() {window.hide();}
	@Override
	public void pause() {window.pause();}
	@Override
	public void resume() {window.resume();}
	@Override
	public void resize(int width, int height) {
           window.wwidth   = width ; 
           window.wheight  = height; 
	   window.resize(width, height);
	}


}
