package com.mygdx.game.Games.FourColor;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.mygdx.game.utils.LevelVariables;
import com.mygdx.game.utils.Classes.CustomViewport;
import com.mygdx.game.utils.Classes.GameScreen;
import com.mygdx.game.utils.Global.Global;
import com.mygdx.game.utils.Tools.PixmapFactory;


public class FourColorScreen extends GameScreen{
// DISPOSABLE
	private Stage stage; 
	private SpriteBatch batch; 
	private World world; 
	private LevelVariables lvl; 
	private List<Texture> textureList = new ArrayList<Texture>(); 
	
	private Table table;
// CONSTANTS 	
	private float appearPeriod  = 1f ; // seconds, each .. seconds appear a new sprite 
	private float linearVelocity = -2000; // second to cross the screen 
	private float blockedTime = 1; // Second you cannot play if you miss
	private Color[] colorArray = new Color[]{Color.BLUE,Color.RED ,Color.GREEN, Color.YELLOW };
	private float centerHeight = 0.1f ; // *wheigt

// USED 	
	private List<Image> imageList = new ArrayList<Image>(); 
	private List<Body> bodyList = new ArrayList<Body>();
	private List<Color> colorList = new ArrayList<Color>();
	private Vector3 topRight, bottomLeft; // in camera coordinates
	private float[][] bounds;  // Bounds of the 4 images buttons
	private boolean isBlocked = false ; // when the player fail and cannot push buttons
	private int radius; 

	
	
	public void draw(float delta){
		stage.draw();
		batch.setProjectionMatrix(stage.getCamera().combined);
		batch.begin();
			for (Body i : bodyList){
				Sprite sprite = (Sprite) i.getUserData(); 
				sprite.setPosition( i.getPosition().x - sprite.getWidth()/2 ,
						stage.getCamera().position.y - sprite.getHeight()/2);
				sprite.draw(batch);
			}
		batch.end(); 
	}
	
	public void step(float delta){
		stage.act(delta);
		world.step(lvl.TIMESTEP, lvl.VELOCITYITERATIONS, lvl.POSITIONITERATIONS);
		
		
		float ran = MathUtils.random(appearPeriod/lvl.TIMESTEP*LevelVariables.DEFAULT_TIMESTEP); 
		if ( ran < delta ){
			createSprite();
		}
		for (Body body : bodyList){
			if (body.getLinearVelocity().x >0) { 
				body.setLinearVelocity(0, 0);
			}
		}
		if ((bodyList.size()-3) * radius > stage.getWidth() ){lvl.loseLife();}
		
	}
	
	
	@Override
	public void render(float delta){
		step(delta); 
		draw(delta); 
		super.render(delta);
	}

	
	
	public void show(){
		super.show();
		lvl = new LevelVariables(); 
		System.out.println("FOURScree" + wx + "," + wy + "," + wwidth + "," + wheight  ); 


		stage = new Stage();
		stage.getCamera().viewportWidth = wwidth ; 
		stage.getCamera().viewportHeight= wheight; 
		stage.setViewport(new CustomViewport(stage.getViewport()));		
		((CustomViewport) stage.getViewport()).setBounds(wx, wy, wwidth, wheight);
		stage.getViewport().setWorldWidth(wwidth); 
		stage.getViewport().setWorldHeight(wheight); 
		stage.getCamera().position.set(wwidth/2,wheight/2,0);
		stage.addListener(new InputListener(){
                   @Override
	           public boolean keyDown(InputEvent event, int keycode) {
		       System.out.println( "I received a key down " ); 
	               switch (keycode){
	                 case Keys.BACK   : 
	                 case Keys.ESCAPE : 
			    Global.screenCaller.callPause(); 
			    return true; 
	                 case Keys.B      : 
	                 case Keys.MENU   :
			    Global.screenCaller.callSetting();
	                    return true;  
	                 default : 
	                     return false; 
	               }  
                   } 
                }); 
		Global.inputMultiplexer.addProcessor(0,stage);

		
		world = new World(new Vector2(0,0) , true ); 
		
		batch = new SpriteBatch();
		
		
		

		
		table = new Table(); 
		table.setFillParent(true);
		
	// CONTENT 
		Pixmap pPlay = PixmapFactory.monocromaticPixmap(1, 1, Color.WHITE);
		Texture tPlay = new Texture(pPlay);
		pPlay.dispose();
		
		Image i0=createImage(0)  ;  imageList.add(i0); 
		Image i1=createImage(1)  ;  imageList.add(i1); 
		Image i2=createImage(2)  ;  imageList.add(i2); 
		Image i3=createImage(3)  ;  imageList.add(i3); 
		

				
				
	// PACK 	
		table.add(    i0   ).fill().expand(); 
		table.add(    i1   ).fill().expand();
		
		table.row(); 
		table.add(  new Image(tPlay)  ).fill().colspan(2).expandX().height(wheight* centerHeight); 
		table.row();
		
		table.add(    i2   ).fill().expand(); 
		table.add(    i3   ).fill().expand(); 
		
		
		
		stage.addActor(table);

		defineBorders(); 
		
		createSprite();
		
		// for the images to get smaller on click 
		stage.draw();  // otherwise I lose the buttons
		bounds = new float[][]{
				{i0.getX(),i0.getY(),  i0.getWidth(),i0.getHeight() }, 
				{i1.getX(),i1.getY(),  i1.getWidth(),i1.getHeight() }, 
				{i2.getX(),i2.getY(),  i2.getWidth(),i2.getHeight() }, 
				{i3.getX(),i3.getY(),  i3.getWidth(),i3.getHeight() }, 
		};
		
				
	}
	



        // create the balls 	
	public void createSprite(){
		int ran = MathUtils.random(3);
		Color color = colorArray[ran]; 
		radius = (int) ( wheight * centerHeight/1.5f ) ; 
				
	    	
	// BODY 	
		BodyDef bodyDef = new BodyDef();  
		bodyDef.type   = BodyType.DynamicBody ; 
		
		CircleShape shape = new CircleShape(); 
		shape.setRadius(radius/2);
		FixtureDef fixtureDef = new FixtureDef(); 
		fixtureDef.shape = shape ; 
		fixtureDef.restitution = 0; 
		fixtureDef.friction = 0;
		fixtureDef.density = 1;
		
		Body body = world.createBody(bodyDef); 
		body.createFixture(fixtureDef); 
		

		
	//SPRITE 	
		
		Sprite sprite = new Sprite(new Texture( 
				PixmapFactory.circle(  radius , color)  ));
		body.setTransform(new Vector2( topRight.x + sprite.getWidth()/2 , 
				(topRight.y + bottomLeft.y  ) /2) ,  
				0);
		body.setLinearVelocity(linearVelocity , 0);
	
		body.setUserData(sprite);
		
		colorList.add(color);
		bodyList.add(body); 
		shape.dispose();
	}
	
	
	public Image createImage( final int index ){
		Pixmap p1 =  PixmapFactory.monocromaticPixmap(1, 1, colorArray[index]);
		Texture t1 = new Texture(p1); 
		p1.dispose();
		
		final Image i1 = new Image(t1); 
		i1.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				click(i1, index);
				return false;
			}
		});
		i1.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				if ( event.getType() == Type.touchUp ) 
				   release(i1, index);
			}
		});
		
		textureList.add(t1);
		return i1 ; 
	}
	
	
	public void click(final Image image, final int index) {
	// Move Button *

		image.setSize(bounds[index][2]*0.9f, bounds[index][3]*0.9f);
		image.setX (  bounds[index][0] + image.getWidth() * 0.05f ) ; 
		image.setY (  bounds[index][1] + image.getHeight() * 0.05f ) ; 
		

		
	// do the JOB 
		// FAIL 
		if ( colorList.size() == 0 || colorList.get(0) != colorArray[index]  ) {
			isBlocked = true ; // Cannot play other buttons
			Pixmap p1 =  PixmapFactory.monocromaticPixmap(1, 1, new Color(1f,0.5f,0.5f,0.15f));
			Texture t1 = new Texture(p1); 
			p1.dispose();
			
			final Image i1 = new Image(t1); 
			i1.setFillParent(true);
			stage.addActor(i1);
			Timer.schedule(new Task(){
			    @Override
			    public void run() {
			    	isBlocked = false; 
				i1.remove();
			    }
			}, blockedTime);
		}
		// Good buttton
		else if (!isBlocked){
			world.destroyBody(bodyList.get(0));
			bodyList.remove(0); 
			colorList.remove(0);
			//wscore ++; 
			for (Body body : bodyList){
					body.setLinearVelocity(linearVelocity, 0);
			}
		}
	}
	
	public void release(Image image, int index){
		image.setSize(bounds[index][2], bounds[index][3]);
		image.setX (  bounds[index][0] ) ; 
		image.setY (  bounds[index][1]) ; 
	}
	
	public void defineBorders(){
		Vector2 topRight2 = new Vector2(wx+wwidth,wy+wheight);
		stage.screenToStageCoordinates(topRight2); 
		topRight = new Vector3(topRight2.x,topRight2.y,0); 
		stage.getCamera().unproject(topRight);
		
		Vector2 bottomLeft2 = new Vector2(wx,wy); 
		stage.screenToStageCoordinates(bottomLeft2); 
		bottomLeft = new Vector3(bottomLeft2.x,bottomLeft2.y,0); 
		stage.getCamera().unproject(bottomLeft);
		
	// Static BODY to stop the ball at the end 
		BodyDef bodyDef = new BodyDef() ; 
		bodyDef.type = BodyType.StaticBody; 
		
		PolygonShape shape = new PolygonShape(); 
		shape.setAsBox(centerHeight*wheight/2, centerHeight*wheight/2, 
				new Vector2( bottomLeft.x -centerHeight* wheight/2, stage.getCamera().position.y ) 
		        , 0);
		FixtureDef fixtureDef = new FixtureDef(); 
		fixtureDef.shape = shape; 
		
		world.createBody(  bodyDef ).createFixture(fixtureDef);
		shape.dispose();
	}
	
	public void dispose(){
		stage.dispose();
		batch.dispose();
		world.dispose();
		for (Texture t : textureList) {t.dispose();}
	}


	

	
	
	public LevelVariables getLevelVar(){return lvl;}
	
	


}
