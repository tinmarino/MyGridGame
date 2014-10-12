package com.mygdx.game.Games.Renaud;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.utils.LevelVariables;


public class LevelVar extends LevelVariables{ 

        public float level; 
	
	public final String textureRedString = "img/game/car/red.png"    ; 
	public final String textureBlueString = "img/game/car/blue.png"  ; 
	public final String textureRoadString = "img/game/car/road512.jpg" ; 
	
	public Texture textureRed ;
	public Texture textureBlue; 
	public Texture textureRoad; 


	public final int   CAR_WIDTH     = 1;
	public final int   CAR_HEIGHT    = 2;
	public final float movementForce = 20;
        public final float minGap        = CAR_HEIGHT *8;
	public final float maxGap        = CAR_HEIGHT *15;
	public final int   carNumber     = 40; // we have 40 body  ennemy cars


	public LevelVar(){
            life = 0; 
	}

	public  void loadTexture(){
		textureRed  = new Texture(textureRedString); 
		textureBlue = new Texture(textureBlueString); 
		textureRoad = new Texture(textureRoadString); 
	}

	public  void disposeTexture(){
	   	if (textureRed != null) {textureRed.dispose() ;} 
	   	if (textureBlue != null){textureBlue.dispose();} 
	   	if (textureRoad != null){textureRoad.dispose();} 
        }


}
