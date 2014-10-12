package com.mygdx.game.Games.Renaud;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;


public class RenaultLevelGenerator {
	private World world; 
	private RenaultCar player;
	private LevelVar lvl; 
	private float y; // The y of the last created car. 
	final float width = 1 , height = 2;
	private List<Body> bodyList = new ArrayList<Body>();
	
   // USED 	
        private BodyDef carDef; 

	private PolygonShape shape;

	private FixtureDef fixture;

	public RenaultLevelGenerator(World world, RenaultCar player, LevelVar lvl) {
	        this.lvl    = lvl ; 
		this.world  = world; 
		this.player = player;
		this.y = player.upLeft.y;
		initiate(); 
	}
	
	

	public void generate(float topEdge) {
		if(y + MathUtils.random(lvl.minGap, lvl.maxGap)  > topEdge ) return;
		
		
		y = topEdge ;
		float x = MathUtils.random(player.downLeft.x, player.downRight.x -width);
		
		createOneCar(x, y);	
	}
	
        private void initiate(){
	   // they all share the same bodyDef because the same car  
	   // body definition
           carDef     = new BodyDef();
	   carDef.type        = BodyType.DynamicBody;

	   shape = new PolygonShape();
	   shape.setAsBox(width*0.8f, height*0.9f, new Vector2(0,0),0);	

	   // fixture 	
	   fixture = new FixtureDef();
	   fixture.shape = shape;
	   fixture.restitution = 0.75f; 
	   fixture.friction = 0.75f;
	   fixture.density = 1;

	}


	private void createOneCar(float x,float y){
		Body body = world.createBody(carDef);
				
		Sprite opSprite = new Sprite(lvl.textureBlue);
		opSprite.setSize(2*width, 2*height);
		opSprite.setOrigin(width, height);

		body.setUserData(opSprite);

		body.createFixture(fixture);
		
	      	body.setTransform(	new Vector2(x,y) , 0 );
	      	body.setLinearVelocity(0, 8 + MathUtils.random(2)); //m/s

		bodyList.add(body); 
	}


 

	

	
	
	/********************
	 * **** GETTER SETTER 
	 ************************
	 */
	
	public float getHeight() {return height;}
	
	
	public void dispose(){
	    shape.dispose();
	}

}


