package com.mygdx.game.Games.Renaud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.WindowScreen;
import com.mygdx.game.utils.TouchAction;

public class RenaultCar implements ContactListener, TouchAction{
// DIPOSABLE 
	private OrthographicCamera camera;
	private World world; 
	private LevelVar lvl; 

	
	public Vector3 downLeft, downRight, upLeft, upRight;
	public Body body;
	public Vector2 velocity;
	


	public RenaultCar(World world, OrthographicCamera camera, String place, WindowScreen window,LevelVar lvl){
		this.world   = world; 
		this.camera  = camera; 
		this.lvl     = lvl   ;
		System.out.println("renault place" + place);
		velocity = new Vector2(0,0);
		defineBorders();
		createCar();
	}
	
	public void createCar(){
		// body definition
		BodyDef carDef = new BodyDef();
		carDef.type = BodyType.DynamicBody;
		carDef.position.set((downLeft.x+downRight.x)/2,downLeft.y + lvl.CAR_HEIGHT*1.5f); // in meter position of the center 
			
	// BodyShape 
		PolygonShape carShape = new PolygonShape();
		carShape.setAsBox(lvl.CAR_WIDTH*0.8f,lvl.CAR_HEIGHT*0.9f);
		

	// BodyFixture 
		FixtureDef carFix = new FixtureDef();
		carFix.shape = carShape;
		carFix.restitution = 1;
		carFix.friction = 0;
		
	// Create Body 
		body = world.createBody(carDef);
		body.createFixture(carFix);
		
	// Add Body Sprite 
		Sprite carSprite = new Sprite(lvl.textureRed);
		carSprite.setSize(2*lvl.CAR_WIDTH,2*lvl.CAR_HEIGHT);
		carSprite.setOrigin(carSprite.getWidth()/2, carSprite.getHeight()/2); // to resize and rotate around the origin, here center of the sprite
		body.setUserData(carSprite); 
	}

	private void defineBorders(){
		downLeft = new Vector3(0,Gdx.graphics.getHeight(),0);
		downRight = new Vector3(Gdx.graphics.getWidth(),downLeft.y,0);
		upLeft   = new Vector3(0,0,0);
		upRight  = new Vector3(downRight.x,upLeft.y,0);

        camera.unproject(downLeft);camera.unproject(downRight);
        camera.unproject(upLeft);camera.unproject(upRight);
	}
	
	
	
	
	
	/***************************
	 * **************************
	// MOVE 
	 * ***************************
	******************************/
	
	public void update() {
		velocity.y = lvl.movementForce; // go up 
		body.setLinearVelocity(velocity.x,velocity.y);
		
		
	// rotate in x 	
		if (body.getPosition().x < downLeft.x ){
			body.setTransform(downRight.x, body.getPosition().y, body.getAngle());
		}
		if (body.getPosition().x > downRight.x ){
			body.setTransform(downLeft.x, body.getPosition().y, body.getAngle());
		}
	}
	
	public void goDir(String string){


	}

	@Override
	public void wasTouched(Side side) {
	        switch(side){
		case LEFT: 
		  velocity.x = -lvl.movementForce;
		  break; 
		case RIGHT:
		  velocity.x =  lvl.movementForce;
		  break;
		case UP : 
		  velocity.y =  lvl.movementForce; 
		  break;
	        case DOWN :
		  velocity.y = -lvl.movementForce; 
		  break; 
		case A : 
		  lvl.accelerate(); 
		  break; 
	        default :
	        }

	}

	@Override
	public void wasReleased(Side side) {
	     velocity.x = 0; 
	     return; 
	}

	@Override
	public void beginContact(Contact arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endContact(Contact arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		if (contact.getFixtureA().getBody() == body || contact.getFixtureB().getBody() == body){
		   lvl.loseLife(); 
		}		
	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param body the body to set
	 */
	public Body getBody() {
		return  body;
	}

}
