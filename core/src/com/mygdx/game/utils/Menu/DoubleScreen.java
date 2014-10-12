package com.mygdx.game.utils.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.WindowScreen;
import com.mygdx.game.WindowScreen.Place;
import com.mygdx.game.utils.Classes.GameScreen;
import com.mygdx.game.utils.Global.Global;
import com.mygdx.game.utils.Global.PlatformOs.Orientation;


public class DoubleScreen extends SingleScreen  {
	public WindowScreen window_left; // public if we want to change speed 
	public WindowScreen window_right;



	 // we extends SingleScreen to be stored as SingleScreen in WindowScreen
	
	/********************************
	 *  CONSTRUCTORS*******************
	 * ****************************
	 */
	public DoubleScreen(){
	}
	
	public DoubleScreen(WindowScreen window_left, WindowScreen window_right){
		this.window_left     = window_left ;
		this.window_right    = window_right;
	}
	

	public void changeWindow(WindowScreen window,WindowScreen caller){
		if (caller == null || caller.place == Place.LEFT){
			this.window_left = window;
			showLeft();
		}
		else if (caller != null || caller.place == Place.RIGHT){
			this.window_right = window;
			showRight();
		}
		if (caller != null ) {caller.dispose(); }
		return ;
	}
        // ***********************************
	//           PAUSE 
	// *********************************

	@Override
	public void callPause(){
	   window                = new PauseScreen(); 
	   window.show(); 
	   window.resize(window.wwidth,window.wheight); 
	}
	@Override
	public void callSetting(){
	   window       = new SettingScreen(); 
	   window.show(); 
	   window.resize(window.wwidth,window.wheight); 
	}
	@Override
	public void callGameOver(GameScreen gameScreen){
	  window       = new GameOverScreen(gameScreen); 
	  System.out.println("DOUBLE"+ window_left + "," + window_right);
	  System.out.println("PLace " + gameScreen.place);
	  window.show(); 
	  window.resize(window.wwidth,window.wheight); 
	}

	@Override
	public void restoreScreen(){
	   window.dispose();
	   window = null; 
	   Gdx.input.setInputProcessor( Global.inputMultiplexer) ; 
	  System.out.println("DOUBLE2"+ window_left + "," + window_right);
	}



	@Override
	public void render(float delta) {
	    /*Wipe Screen to black*/
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (window != null) {
		  render_pause(delta);}
		else {render_left(delta); render_right(delta);}
	}

	public void render_pause(float delta){
	    Gdx.gl.glViewport( 0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight() );
	    window.render( delta );
	}
	public void render_left(float delta){
	    Gdx.gl.glViewport( 0,0,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight() );
	    window_left.render( delta );
	}
	public void render_right(float delta){
	    Gdx.gl.glViewport( Gdx.graphics.getWidth()/2,0,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight() );
	    window_right.render( delta );
	}
	
	
	
	@Override
	public void resize(int width, int height) { 
		
		window_left.wwidth   = width/2 ;
		window_left.wheight  = height  ;
		window_left.wx       = 0 ; 
		window_left.wy       = 0 ;

		window_right.wwidth  = window_left.wwidth  ;
		window_right.wheight = window_left.wheight ;
		window_right.wx      = window_left.wwidth  ; 
		window_right.wy      = 0 ;

		window_left.resize (window_left.wwidth , window_left.wheight );
		window_right.resize(window_right.wwidth, window_right.wheight);
	}

	@Override
	public void show() {
	// resize the staff
	        instance = Instance.DOUBLE; 
	        Global.screenCaller = this; 
		Global.platformOs.setOrientation(Orientation.PAYSAGE);
	// get input 
		Global.inputMultiplexer = new InputMultiplexer(); 
		Gdx.input.setInputProcessor(Global.inputMultiplexer); 
		
	// create an empty INput Adapter to prevent from nullpointer exception	
		showLeft();
		showRight();
	}
	public void showLeft(){
		// local width or height;
	// PLACE CALLER 
		window_left.place   = Place.LEFT;
        // Geometry 
	        window_left.wx = 0; 
	        window_left.wy = 0; 
	        window_left.wwidth  = Gdx.graphics.getWidth()/2; 
	        window_left.wheight = Gdx.graphics.getHeight();  
		
		window_left.show();
	}
	public void showRight(){
	// PLACE CALLER 
		window_right.place   = Place.RIGHT;
		
        // Geometry 
	        window_right.wx = window_left.wwidth; 
	        window_right.wy = 0; 
	        window_right.wwidth  = window_left.wwidth ; 
	        window_right.wheight = window_left.wheight;  

		window_right.show();
	}
	
	

	
	
	

	

	/**** GETTER SETTER
	 */

	
	public void setWindow(WindowScreen wl,WindowScreen wr){
		this.window_left = wl;
		this.window_right = wr;
	}
	
	
	
	// just in case, to prevent heritage
	@Override
	public void hide() {
			window_left.hide();
			window_right.hide();
	}

	@Override
	public void pause() {
			window_left.pause();
			window_right.pause();
	}

	@Override
	public void resume() {
			window_left.resume();
			window_right.resume();
	}

	@Override
	public void dispose() {
			window_left.dispose();
			window_right.dispose();
	}
	
}

		
/*
 * }
        public void resizeSetting(){
	  int[] tmp = Global.platformOs.getSize(); 
	  int width =  tmp[0]; 
	  int height =  tmp[1]; 

	   Vector2 size;
	   size = Scaling.fit.apply(Global.VIEWPORT_GUI_HEIGHT,
	   				Global.VIEWPORT_GUI_WIDTH, height, width);
           int viewportX = (int)(width - size.x) / 2;
           int viewportY = (int)(height - size.y) / 2;
           int viewportWidth = (int)size.x;
           int viewportHeight = (int)size.y;
           Gdx.gl.glViewport(0, 0, width, height);
           window.wwidth = viewportWidth; 
           window.wheight = viewportHeight; 
           window.wx = viewportX; window.wy = viewportY; 
	   System.out.println("Pause1 " + window.wwidth + "," + window.wx); 
	   window.wx = 0; window.wy = 0; window.wwidth = width ; window.wheight = height; 
	   System.out.println("Pause2 " + window.wwidth + "," + window.wx); 
	   window.wx = 0; window.wy = 0; window.wwidth = Gdx.graphics.getWidth();  window.wheight = height; 
	   System.out.println("Pause3 " + window.wwidth + "," + window.wx); 
           window.wwidth = viewportWidth; 
           window.wheight = viewportHeight; 
           window.wx = viewportX; window.wy = viewportY; 
	   window.wx =0; window.wy =0; 
	   System.out.println("Pause4 " + window.wwidth + "," + window.wx); 
        }
	*/
