package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.mygdx.game.utils.Global.GamePrefs;
import com.mygdx.game.utils.Global.Global;
import com.mygdx.game.utils.Global.PlatformOs.Orientation;
import com.mygdx.game.utils.Menu.MainManu;
import com.mygdx.game.utils.Menu.SingleScreen;


public class MyGdxGame extends Game{
        protected Screen screen; 	

	@Override
	public void create () {
		Gdx.app.log("MyGdxGame starting", "________________________");
	        Global.game = this; 

		GamePrefs.loadPreferences();

		//screen = new SingleScreen( new VolleyScreen() ); 
		this.screen = new SingleScreen( new MainManu() ); 
		restart(); 
	}


	public void restart(){
		  Global.platformOs.setOrientation(Orientation.PORTRAIT);
		  setScreen( new SingleScreen( new MainManu()) ); 
	}
}
