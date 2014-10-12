package com.mygdx.game.Games.StarSwitch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Games.StarSwitch.LevelGen.StaticBody;
import com.mygdx.game.utils.InputMakeEasy;
import com.mygdx.game.utils.Classes.GameScreen;
import com.mygdx.game.utils.Global.Global;

public class StarSwitchScreen extends GameScreen{
// DIPOSABL 	
	public  World world; 
	private SpriteBatch batch;
	private Box2DDebugRenderer debugRenderer;
	private  LevelVar lvl ; 

	public  OrthographicCamera camera; 

	private StarPlayer player;


	private LevelGen levelGen;
	
	private ShapeRenderer shapeRenderer ; 
	private List<Star> starList = new ArrayList<Star>();
	
	
	
	public void step(float delta){
		world.step(lvl.TIMESTEP, lvl.VELOCITYITERATIONS, lvl.POSITIONITERATIONS);
		
		player.update();
		camera.position.set(camera.position.x,
				player.body.getPosition().y  -lvl.worldHeight/2f + lvl.playerSize.y,0);
		
		
		camera.update();
		levelGen.generate(delta, camera.position.y + -lvl.worldHeight/2f );
		
		recycleStar();
	}
	
	
	public void draw(float delta){

		

	  // GALXIES 
	    batch.setProjectionMatrix(camera.combined);
	    batch.begin();
			for ( StaticBody i : levelGen.getFixtureList() ){
				if (i.fixture != null){
					i.sprite.draw(batch);
				}
			}
		
	    batch.end();

	// SHAPE RENDERER = stars	
	    shapeRenderer.setProjectionMatrix(camera.combined);
	    shapeRenderer.begin(ShapeType.Line);
			for (Star star : starList){
				shapeRenderer.setColor(star.color);
				shapeRenderer.line( 
					star.position.x -star.size*5  ,  star.position.y  , 
					star.position.x + star.size*5 , star.position.y)  ;				
				shapeRenderer.line( 
						star.position.x  ,  star.position.y-star.size*5   , 
						star.position.x  ,  star.position.y+ star.size*5)  ;	
			}
	    shapeRenderer.end();


	    // DRAW PLAYER
	    batch.setProjectionMatrix(camera.combined);
	    batch.begin();
			player.sprite.draw(batch);
	    batch.end(); 
	}
	
	
	@Override
	public void render(float delta){
		step(delta); 
		draw(delta);
		if (Global.debug) {debugRenderer.render(world, camera.combined);}
		super.render(delta);
	}
	
	
	
	
	
	
	
	
	
	
	@Override
	public void show(){
		super.show();
		lvl = new LevelVar(); 
		lvl.create(); 
		lvl.life =3;
		world = new World( new Vector2(0,0) , true );
		lvl.worldHeight = lvl.worldWidth * wheight/wwidth; 
		camera = new OrthographicCamera(lvl.worldWidth,lvl.worldHeight); 
		batch = new SpriteBatch();
		debugRenderer = new Box2DDebugRenderer();
		
		player = new StarPlayer(this,lvl);
		InputMakeEasy input = new InputMakeEasy(this);
		input.setTouchAction(player); 
		input.setCamera(camera); 
		world.setContactFilter(player);
		Global.inputMultiplexer.addProcessor(input);
	

		levelGen = new LevelGen(this,lvl);
		shapeRenderer = new ShapeRenderer();
		createAllStars();
		
	}
	
	
	public void createAllStars(){
		for (int i = 0 ; i< lvl.starNumber ; i++){
			Star star = new Star();
			star.position = new Vector2 ( 
				(float) MathUtils.random(-lvl.worldWidth/2f, lvl.worldWidth/2f ),
				(float) MathUtils.random(-lvl.worldHeight/2f ,lvl.worldHeight/2f )	
			);
	          // SIZE 
			Random ran = new Random();
		        int tmp2 = ran.nextInt(10); 
			switch(tmp2){
			   case 1: star.size   = 0.02f  ; break ; 
			   case 2: star.size   = 0.01f  ; break ; 
			   case 3: star.size   = 0.01f  ; break ; 
		           default : star.size = 0.005f ; break ; 
		        }
			star.angle = MathUtils.random(MathUtils.PI/2);
	          // COLOR 
			int tmp = ran.nextInt(10);
			switch (tmp) {
				case 0: 
				case 1:
				  star.color = Color.YELLOW; 
				  break ; 
				case 2: 
				  star.color = Color.WHITE ; 
				  break ; 
				case 3:  //REd
			          star.color = new Color(1f,0.7f,0f,1); 
				  break;
				case 4:  // BLUE
			          star.color = new Color(0.7f,0.7f,0.2f,1); 
				  break;
				default: 
				  star.color = Color.YELLOW; 
			}
			star.color = new Color( star.color.r +MathUtils.random(-0.5f,0.5f) , 
			                        star.color.g +MathUtils.random(-0.5f,0.5f) ,  
						star.color.b +MathUtils.random(-0.5f,0.5f) ,  
						1 ); 
			
			starList.add(star);
		}
	}
	public void recycleStar(){
		for (Star star : starList){
			if (star.position.y > camera.position.y + lvl.worldHeight/2f ){
				star.position.y -= lvl.worldHeight+ MathUtils.random(2);
				star.position.x = MathUtils.random(-lvl.worldWidth/2f, lvl.worldWidth/2f);
			}
		}
	}
	
	
	public static class Star{
		public Vector2 position; 
		public float size; // circle , 0.5f spike
		public float angle; // of the spike
		public Color color ; 
	}
	
	public LevelVar getLevelVar(){return lvl;}

	@Override
	public void dispose(){
		super.dispose();
		world.dispose();
		batch.dispose();
		if (debugRenderer!= null) {debugRenderer.dispose();}
		shapeRenderer.dispose();
		lvl.dispose(); 
	}




}

