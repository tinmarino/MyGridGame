package com.mygdx.game.Games.StarSwitch;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.utils.LevelVariables;

public class LevelVar extends LevelVariables{
// What we get 
	public  enum Mission{
		PASS_GAL, // each galaxy passsed give one point 
		TIME,     // each 2s give one point
	}
	
	public  Mission mission = Mission.TIME;
	
// Game play 
	/*
	public float ZOOM = 25; 
	public int VELOCITYITERATIONS = 8 , POSITIONITERATIONS = 3;
	public float TIMESTEP = 1/60f;
	*/
        public  float worldWidth =15, worldHeight; 	
	public  float minGap=5, maxGap=10; // 5 10 was the first staff 
	public  int columnNumber = 2;
	public  boolean baunceBol = true ; // baunce on edgde and not rotate;
	public  int moveDirection = 1; // from left to rigth;
	public  List<Vector2> positionList = new ArrayList<Vector2>() ; 
	    // created in player because he goes there and caller before levelGen


	
// level 
	// the first is the name and the second the frecuency 
	public  List<Texture> textureList =  new ArrayList<Texture>(); 
	
// Estetic 
	public  float starNumber =300;
	public  Texture playerTexture = new Texture("img/game/star_switch/spaceshift.png"); 
	
// Player	
	public  float playerSpeed = 3;
	public  Vector3 playerSize = new Vector3(1,1.5f,0);

	
	
	
	
	
	public  void create(){
	  createTextureList(); 
	}

	public   void createTextureList(){
	      textureList.add(  new Texture("img/game/star_switch/galaxies/Andromeda.png"  )   ); 
	      textureList.add(  new Texture("img/game/star_switch/galaxies/Cena.png"       )   ); 
	      textureList.add(  new Texture("img/game/star_switch/galaxies/Hoag.png"       )   ); 
	      textureList.add(  new Texture("img/game/star_switch/galaxies/M64.png"        )   ); 
	      textureList.add(  new Texture("img/game/star_switch/galaxies/M81.png"        )   ); 
	      textureList.add(  new Texture("img/game/star_switch/galaxies/NGC1300.png"    )   ); 
	      textureList.add(  new Texture("img/game/star_switch/galaxies/NGC2442.png"    )   ); 
	      textureList.add(  new Texture("img/game/star_switch/galaxies/NGC2685.png"    )   ); 
	      textureList.add(  new Texture("img/game/star_switch/galaxies/NGC4565.png"    )   ); 
	      textureList.add(  new Texture("img/game/star_switch/galaxies/NGC4725.png"    )   ); 
	      textureList.add(  new Texture("img/game/star_switch/galaxies/NGC4945.png"    )   ); 
	      textureList.add(  new Texture("img/game/star_switch/galaxies/NGC5033.png"    )   ); 
	      textureList.add(  new Texture("img/game/star_switch/galaxies/NGC55.png"      )   ); 
	      textureList.add(  new Texture("img/game/star_switch/galaxies/Sombrero.png"   )   ); 
	      textureList.add(  new Texture("img/game/star_switch/galaxies/Whale.png"      )   ); 

	}


	public  void dispose(){
             for (Texture i : textureList){
	       i.dispose(); 
	     }
	     playerTexture.dispose(); 
	}
	
	
	
}
