package com.mygdx.game.Games.StarSwitch;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.utils.TouchAction;

public class StarPlayer implements TouchAction,ContactFilter{
// WHAT we get 
	private World world;
	private StarSwitchScreen caller ; 
	private Camera camera; 
	private LevelVar lvl; 

// WHAT We crete 	
	Body body;
	public Sprite sprite;

// WHAE we use 	
	public float floatScore = 0; // the last score stored to know when It changes.
	public int positionIndex = 0 ;
	public int moveDirection; // 1 move to right ; -1 to left 
	
	
	public StarPlayer(StarSwitchScreen starSwitchScreen,LevelVar lvl) {
		caller = starSwitchScreen;
		world = caller.world ; 
		camera = caller.camera ; 
		this.lvl = lvl; 
		moveDirection = lvl.moveDirection; 
		
		
		createBody();
		createPositionList();// need sprite size , after createBody

	}

	
	public void createPositionList(){
		System.out.println("column number : " + Integer.toString(lvl.columnNumber)  );
		for (int i=0; i< lvl.columnNumber ; i++  ){
			float step = lvl.worldWidth / lvl.columnNumber ; 
			float x =   -lvl.worldWidth/2f + step/2 + i*step ; 
			float y =    lvl.worldHeight/2f - lvl.playerSize.y ;
			lvl.positionList.add( new Vector2( x , y ) );
			
			body.setTransform(  lvl.positionList.get( positionIndex) , 0 )  ;

		}

		
	}
	public void createBody(){ // and define some var
	//DEfine LEFT AND RIGHT POSITION
		
		
	//BODY 	
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		body = world.createBody(bodyDef);
		body.setFixedRotation(true);
		
		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(lvl.playerSize.x*.5f,  lvl.playerSize.y*.5f);
		fixtureDef.shape = shape;
		
		body.createFixture(fixtureDef);
		shape.dispose();
		
	//SPRITE 
		sprite = new Sprite(lvl.playerTexture);
		sprite.setSize(lvl.playerSize.x,  lvl.playerSize.y);
		sprite.setPosition(body.getPosition().x-sprite.getWidth()/2, 
				body.getPosition().y-sprite.getHeight()/2);
		
	// give velocity 
		body.setLinearVelocity(0, -lvl.playerSpeed);
		
		
	}
	
	public void update(){
	// SPRITE
		sprite.setPosition(body.getPosition().x-sprite.getWidth()/2, 
				body.getPosition().y-sprite.getHeight()/2);
	}
	

	
	
	

	@Override
	public void wasTouched(Side side) {

		positionIndex+= lvl.moveDirection ;
		
	// BORDER CONTROL 
		if (positionIndex >= lvl.positionList.size()  ){// Im right  
			if (lvl.baunceBol) { 
				positionIndex = lvl.positionList.size() -2 ; 
				moveDirection = -1 ;// go LEFT 
			}
			else {positionIndex = 0 ;} 
		}
		if (positionIndex <= -1  ){// I'm left 
			if (lvl.baunceBol) { 
				positionIndex = 1  ; 
				moveDirection = 1 ;// go Right 
			}
			else {positionIndex = lvl.positionList.size() -1 ;} // rotate
		}
		
	// MOVE BODY AND SPRITE 	
		body.setTransform( camera.position.x +   lvl.positionList.get(positionIndex).x  ,
				camera.position.y +   lvl.positionList.get(positionIndex).y, 
				0);
		
		sprite.setPosition(body.getPosition().x-sprite.getWidth()/2, 
				body.getPosition().y-sprite.getHeight()/2);

	}

	@Override
	public void wasReleased(Side side) {
	}

	@Override
	public boolean shouldCollide(Fixture arg0, Fixture arg1) {
	        lvl.loseLife(); 
		return false;
	}
	
}
