package com.mygdx.game.Games.StarSwitch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class LevelGen {
	private World world; 
	
	private LevelVar lvl; 
	private float y; 
	private float width , height;
	private Body body; // static
	
	private List<StaticBody> fixtureList = new ArrayList<StaticBody>();
	
	
	private float randomGap = 0;

	
	
	public LevelGen(StarSwitchScreen caller,LevelVar lvl) {
	        this.lvl   = lvl; 
		this.world = caller.world; 
		this.y = -lvl.worldHeight/2f;
		create(); // create the body of the ennemy cars
	}
	
	

	public void generate(float delta, float bottomEdge) {
		if(y - randomGap < bottomEdge - body.getPosition().y)
			return;
		randomGap = MathUtils.random(lvl.minGap, lvl.maxGap);
		y = bottomEdge - body.getPosition().y;
		


		
		Random ran = new Random();
		int index = ran.nextInt(lvl.columnNumber);
		float x=lvl.positionList.get(index).x;

		createOneGal(x, y);
	}
	
	
	private void createOneGal(float x,float y){

	// Get from the list 
		StaticBody staticBody = fixtureList.get(fixtureList.size()-1);
		//System.out.println("I remove:"+ staticBody.center.toString());
		fixtureList.remove(fixtureList.size()-1);	
		
	// fixture 	
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width*.5f, height*.25f , new Vector2(x, y),0);
             //*  Integer.parseInt(lvl.galNames.get(index)[1])
		FixtureDef fixture = new FixtureDef();
		fixture.shape = shape;		
		

	// CREATE and Store in the list 
		staticBody.fixture = body.createFixture(fixture);
		staticBody.center = new Vector2(x,y);
		staticBody.sprite.setTexture(getRandomTexture());
		staticBody.sprite.setPosition(x-staticBody.sprite.getWidth()*.5f,
				y- staticBody.sprite.getHeight()*0.5f);
		fixtureList.add(0,staticBody);
		
		
		shape.dispose();	
	}
	
	private void create(){
	// WIDHT HEIGHT 
		width = lvl.worldWidth / lvl.columnNumber;
		height = width/3;
	// body definition
		BodyDef carDef = new BodyDef();
		carDef.type = BodyType.StaticBody;
		body = world.createBody(carDef);
				

		
		for (int i = 0 ; i<20 ; i++){
			StaticBody staticBody = new StaticBody();
			staticBody.sprite  = new Sprite(getRandomTexture());
			staticBody.sprite.setSize(width,height);
			fixtureList.add(staticBody);}

	}
	
	
	public Texture getRandomTexture(){
		return lvl.textureList.get(MathUtils.random(lvl.textureList.size()-1)); 
	}

	
	public List<StaticBody> getFixtureList() {
		return fixtureList;
	}
	



    public class StaticBody {
    	public Fixture fixture ; 
    	public Sprite sprite;
    
    	
    	public Vector2 center ; 
    	public Vector2 size ; 
    }

}

