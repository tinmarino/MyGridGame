package com.mygdx.game.Games.Volley;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utils.LevelVariables;
import com.mygdx.game.utils.Tools.PixmapFactory;




public class LevelVar extends LevelVariables{


	//public  Texture tapToStartTexture; 
	public  Texture backgroundTexture ; 

	public  Texture platformTexture ; 
	public  Texture ballTexture;  


	public  Vector2 ballVelocity = new Vector2(0,4); 
	public  int platformHeight  = 30; 


	public  void init(){
             backgroundTexture = new Texture("img/game/volley/sky.png"); 
	     platformTexture =  PixmapFactory.gridPixmap(
			new Pixmap( Gdx.files.internal("img/game/volley/brick_inca.jpg") ), 7,3);
             ballTexture = new Texture("img/game/volley/volleyball.png");
	}


	public  void dispose(){
	  backgroundTexture.dispose(); 
	}

}
