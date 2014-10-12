package com.mygdx.game.Games.Volley;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.utils.InputMakeEasy;
import com.mygdx.game.utils.TouchAction;
import com.mygdx.game.utils.Classes.GameScreen;
import com.mygdx.game.utils.Global.Global;

public class VolleyScreen extends GameScreen implements ContactFilter,TouchAction{
// DIPOSABLE 
	private World world;
	private SpriteBatch batch;

//PARAMETERS 
	private LevelVar lvl ; 
        private Sprite backgroundSprite; 
	private InputMakeEasy input; 
	
	
//USED 
	private Matrix4 HUDMatrix;
	private Body ballBody;
	private BodyDef groundDef;
	private Body groundBody;
	private Place crPlace = Place.UP;
	private Box2DDebugRenderer debugRender;
	private Array<Body> tmpBodies = new Array<Body>();
      
// level dependant	

	/********************************
	// FUCTIONS 
	********************************/
	


	public void step(float delta){
		world.step(lvl.TIMESTEP, lvl.VELOCITYITERATIONS, lvl.POSITIONITERATIONS);
		if ( ballBody.getPosition().y >  wheight +20 || ballBody.getPosition().y < -20 ){
		  lvl.loseLife(); 
		  ballBody.setTransform(wwidth/2,wheight/2,0);
		}
	}
	
	
	public void draw(float delta){
		// batch renderer 
		batch.setProjectionMatrix(HUDMatrix); // be in world coordinates but one pixel is one meter hight 
		batch.begin();
		
		// bckgd img
			backgroundSprite.draw(batch);
			
			world.getBodies(tmpBodies);
			for (Body body : tmpBodies)
				if (body.getUserData() != null && body.getUserData() instanceof Sprite) {
					Sprite sprite = (Sprite) body.getUserData();
					sprite.setPosition(body.getPosition().x - sprite.getWidth()/2, body.getPosition().y - sprite.getHeight()/2);
					sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
					sprite.draw(batch);
				}	


		batch.end();
		debugRender.render(world,HUDMatrix); 
		
	}
	
	
	@Override
	public void render(float delta) {
		step(delta); 
		draw(delta); 

		super.render(delta);
		//if (life==-1) {gameOver();} 
	}

	
	
	
	
	/******************************************/
	

	@Override
	public void show() {	
		super.show();
	// WORLD
		lvl = new LevelVar() ; 
	        lvl.init(); 
		lvl.life = 3;  
		
		batch = new SpriteBatch();
		world = new World(new Vector2(0,0),true);


    // add ball and platform bodies to the world   
	// and get the corner coord
	        input = new InputMakeEasy(this); 
		input.setTouchAction(this);
		Global.inputMultiplexer.addProcessor(0,input);
		world.setContactFilter(this);
		
	// create Background image	
                backgroundSprite =new Sprite(lvl.backgroundTexture);
                backgroundSprite.setBounds(0,0,wwidth,wheight) ;  
		
	// hud 
		HUDMatrix = new Matrix4();
		HUDMatrix.setToOrtho2D(0, 0, wwidth, wheight);	
		debugRender = new Box2DDebugRenderer(); 
		defineBall(); 
		defineGround(); 
	}



	public void defineBall(){
		// body definition
		BodyDef ballDef = new BodyDef();
		ballDef.type = BodyType.DynamicBody;
        //ballDef.position.set((downLeft.x+downRight.x)/2,(downLeft.y+upLeft.y)/2); // middle
                ballDef.position.set(wwidth/2,wheight/2); // middle
		
	// BodyShape 
		CircleShape ballShape = new CircleShape();
		ballShape.setRadius(wwidth/10f);

	// BodyFixture 
		FixtureDef ballFix = new FixtureDef();
		ballFix.shape = ballShape;
		ballFix.restitution = 1;
		ballFix.friction = 0;
		
	// Create Body 
		ballBody = world.createBody(ballDef);
		ballBody.createFixture(ballFix);
		
	// Add Body Sprite 
		Sprite ballSprite = new Sprite(lvl.ballTexture);
		ballSprite.setSize( (int) (2*ballShape.getRadius())  , 
				            (int) (2*ballShape.getRadius())  ); // metros el radio
		ballSprite.setOrigin(ballSprite.getWidth()/2, ballSprite.getHeight()/2); // to resize and rotate around the origin, here center of the sprite
		ballBody.setUserData(ballSprite); 
		
	// give velocity 	
		ballBody.setLinearVelocity(new Vector2(0,wheight/2));
	}
		
	
	public void defineGround(){
	// body definition
			groundDef = new BodyDef();
			groundDef.type = BodyType.StaticBody;
			groundDef.position.set(wwidth/2,0); // in meter 
			groundBody = world.createBody(groundDef);
	
		// FixtureShape 
			PolygonShape groundShape = new PolygonShape();
			groundShape.setAsBox(wheight/2,wheight/20f);		
		// BodyFixture 
			FixtureDef groundFix = new FixtureDef();
			groundFix.shape = groundShape;
			groundFix.restitution = 1;
			groundFix.friction = 0;
			groundBody.createFixture(groundFix);
			
		// Add Body Sprite 
			Sprite groundSprite = new Sprite(lvl.platformTexture);
			groundSprite.setSize(wwidth, wheight/10f);
			groundSprite.setOrigin(groundSprite.getWidth()/2, groundSprite.getHeight()/2); // to resize and rotate around the origin, here center of the sprite
			//groundSprite.rotate90(true);
			groundBody.setUserData(groundSprite); 
			
		// change place to be in a valid position	
			//changePlace();
	}	




	public void dispose() {
	// background image 
		world.dispose();
		batch.dispose();
		lvl.dispose(); 
	}




	public void changePlace(){ // change the place of the platform 
		switch (crPlace){
		case DOWN:
			groundBody.setTransform(wwidth/2,wheight, 0);
			crPlace = Place.UP; 
			break;
		case UP:
			groundBody.setTransform(wwidth/2,0, 0);
			crPlace = Place.DOWN; 
			break;
		default : return ; 
		}
	}

	@Override
	public boolean shouldCollide(Fixture arg0, Fixture arg1) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void wasTouched(Side side) {
	  changePlace(); 
	    

	}

	@Override
	public void wasReleased(Side side) {
		// TODO Auto-generated method stub

	}

	public LevelVar getLevelVar(){return lvl;}

	public enum Place{
	  UP, DOWN , LEFT, RIGHT
	}

}



