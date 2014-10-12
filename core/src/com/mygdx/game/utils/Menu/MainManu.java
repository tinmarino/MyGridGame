package com.mygdx.game.utils.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.WindowScreen;
import com.mygdx.game.utils.Global.Global;
import com.mygdx.game.utils.Global.PlatformOs.Orientation;


public class MainManu extends WindowScreen{
// DISPOSABLE 
	private Stage stage;
	private Texture bckTexture    = new Texture("img/game/main_menu/wolfRayet.jpg");
	private Texture stickTexture  = new Texture( "img/game/main_menu/stick.png"   ); 
	private Skin skin             = new Skin(Gdx.files.internal("img/game/main_menu/mainMenuSkin.json")
				, new TextureAtlas("img/game/main_menu/woodButtons.pack"));

	private Color htmlColor = new Color(0.75f,0f,0f,1); 
	
        // USed in resize 	
	private Image stickImage      = new Image( stickTexture ); // the vertical  stick between the


	public void render (float delta){
		stage.act(delta);		
		stage.draw();
		
		//if (Global.debug) {Table.drawDebug(stage);}
		super.render(delta); // for debug 
	}
	
	
	public void show (){
		super.show(); 

	// create Stage and set input listener to it
		stage = new Stage();
		Global.inputMultiplexer.addProcessor(stage); 
		Table table = new Table() ; 
		table.setFillParent(true);  // otherwise don't render well the buttons bounds
		
		

		
		
	// BUTTONS			
		// SINGLE GAME
		TextButton bSingle = new TextButton("Single Game", skin , "wood1");
		bSingle.getStyle().fontColor = htmlColor; 
		
		bSingle.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
			    super.clicked(event,x,y); 
			    Global.screenCaller.changeWindow(new GridMenu(),
			       MainManu.this);
			}
		});
		
		// DUAL GAME
		TextButton bDouble = new TextButton("Dual Game", skin , "wood2");
		bDouble.getStyle().fontColor = htmlColor; 
		bDouble.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
			    super.clicked(event,x,y); 
			    Global.platformOs.setOrientation(Orientation.PAYSAGE);
			    Global.game.setScreen( 
			      new DoubleScreen( new GridMenu(),new GridMenu() )); 
			}
		});
		
		// SETTING
		TextButton bSetting = new TextButton("Settings", skin, "wood3");
		bSetting.getStyle().fontColor = htmlColor; 
		bSetting.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
			        super.clicked(event,x,y); 
			        Global.screenCaller.callSetting();
			}
		});
		
		// EXIT TODO DOWNRIGHT
		TextButton bExit = new TextButton("EXIT", skin , "wood4");
		bExit.getStyle().fontColor = htmlColor; 
		bExit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
			        super.clicked(event,x,y); 
                                Gdx.app.exit();
			}
		});
		
	// Image background 
		Image image = new Image(bckTexture);
		image.setFillParent(true);
		
	// GEOMETRY 
		float bWidth = wwidth/1.1f;
		float bPad   = 4;
		
		
		table.add()        .width(bWidth).pad(bPad).row(); // nothing 
		table.add()        .width(bWidth).pad(bPad).row(); // nothing 
		
		table.add(bSingle) .width(bWidth).pad(bPad).row(); //width(wwidth/1.3f);
		
		table.add(bDouble) .width(bWidth).pad(bPad).row();
		
		table.add(bSetting).width(bWidth).pad(bPad).row();
		
		table.add(bExit)   .width(bWidth).pad(bPad).row(); 
		
		table.add()        .width(bWidth).pad(bPad).row(); // nothing 
		table.add()        .width(bWidth).pad(bPad).row(); // nothing 
		table.add()        .width(bWidth).pad(bPad).row(); // nothing 
		table.add()        .width(bWidth).pad(bPad).row(); // nothing 

	

		

		stage.addActor(image);
		stage.addActor(stickImage);
		stage.addActor(table);

	};
	
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		float sWidth = width/11f;
		stickImage.setBounds(stage.getWidth()*0.5f - sWidth*0.5f, 0 , sWidth, stage.getHeight()*0.83f);
		stage.getViewport().update(width, height, true);
	}
	

	
	public void dispose (){
		super.dispose();
		stage.dispose();
		skin.dispose();
		bckTexture.dispose();
		stickTexture.dispose(); 
	}

	

}
