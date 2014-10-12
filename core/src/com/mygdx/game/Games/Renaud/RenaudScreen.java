package com.mygdx.game.Games.Renaud;


import com.mygdx.game.utils.InputMakeEasy;
import com.mygdx.game.utils.ScrolableBackground;
import com.mygdx.game.utils.Classes.GameScreen;
import com.mygdx.game.utils.Global.Global;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class RenaudScreen extends GameScreen {
//DIPOSABLE
  private World world;
  private LevelVar lvl; 

//CLASSES 
  private RenaultCar player; 
  private ScrolableBackground scrolableBackground; 
  private RenaultLevelGenerator levelGenerator; 

  // USE 
  private OrthographicCamera camera; 
  private float lastCameraY;
  private Array<Body> tmpBodies = new Array<Body>(); 
  // to delete bodies not every delta
  private int destroyCounter =0;



  public void step(float delta){
    world.step(lvl.TIMESTEP, 
	       lvl.VELOCITYITERATIONS, 
	       lvl.POSITIONITERATIONS);

    player.update();


  // CAMERA
    lastCameraY = camera.position.y; 
    camera.position.y = player.upRight.y + player.getBody().getPosition().y - lvl.CAR_HEIGHT * 1.5f;
    camera.update();
  	
    levelGenerator.generate(camera.position.y + player.upRight.y);
  //SCORE 
  }
  
  
  
  
  public void draw(float delta){
  // update backdround
      float offset = - (camera.position.y -lastCameraY) / (player.upLeft.y*2);
  
      batch.setProjectionMatrix(HUDMatrix);
      batch.begin();
         scrolableBackground.render(batch,offset,ScrolableBackground.Dir.S);
      batch.end();
  
  // batch renderer 
  	batch.setProjectionMatrix(camera.combined); // be in world coordinates but one pixel is one meter hight 
  	batch.begin();		
  	// world body sprites 
  		world.getBodies(tmpBodies);
  		for (Body body : tmpBodies)
  			if (body.getUserData() != null 
  			         && body.getUserData() instanceof Sprite 
  			         //&& body != levelGenerator.getBody()// otherwise, print a sprite at 0 0
  			         ) { 
  			        Sprite sprite = (Sprite) body.getUserData();
  				sprite.setPosition(body.getPosition().x - sprite.getWidth()/2, 
  						body.getPosition().y  - sprite.getHeight()/2);
  				sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
  				sprite.draw(batch);
  			}

  	batch.end();
  	
	// RECYCLE , destroy the shit 
        destroyCounter++; 
	if (destroyCounter == 20){
	   for (Body body : tmpBodies){
	      if (body ==player.body ) {break; }
	      Vector2 tmp = body.getPosition(); 
	      if (tmp.y < player.downLeft.y + camera.position.y  - lvl.CAR_HEIGHT||
	          tmp.x < player.downLeft.x        - lvl.CAR_WIDTH || // camera.position.x = 0
	          tmp.x > player.downRight.x        + lvl.CAR_WIDTH ){

	          world.destroyBody( body ) ; 
	      }
	   }
	}
  	
  }
  
  
  
  @Override
  public void render(float delta) {
  	step(delta); 
  	draw(delta); 
  	super.render(delta);
  }
	





























  public void show() {
  	super.show();
  // LOAD 
        lvl = new LevelVar(); 
  	lvl.loadTexture(); 
	lvl.life=3; 
  // WORLD
  	world = new World(new Vector2(0,0),true);
  	camera = new OrthographicCamera(15,15*wheight/wwidth); 
  
  	
  	
  	
  // PLAYER + input processor
  	player = new RenaultCar(world, camera,"left",this,lvl); // for the inp.listeener
  	//this.wscreen.inputMultiplexer.addProcessor(player);
	InputMakeEasy inputMakeEasy = new InputMakeEasy(this);
	inputMakeEasy.setCamera(camera); 
	inputMakeEasy.setTouchAction(player); 
	Global.inputMultiplexer.addProcessor(0,inputMakeEasy) ; 
  	world.setContactListener(player);
  	
  // HUDMAtrix
  	scrolableBackground = new ScrolableBackground(lvl.textureRoad,(int) (wwidth*1.28),wheight);
  	
  // LEVEL GENERATOR
  	levelGenerator = new RenaultLevelGenerator(world,player,lvl);
  }
	

	
	



	public void dispose() {
	  world.dispose(); 
	  batch.dispose(); 
	  lvl.disposeTexture(); 
	  levelGenerator.dispose(); 
	}

	public LevelVar getLevelVar(){return lvl;}

}
