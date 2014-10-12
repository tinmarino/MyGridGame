package com.mygdx.game.utils.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.WindowScreen;
import com.mygdx.game.utils.Global.Global;
import com.mygdx.game.utils.Global.PlatformOs.Orientation;

public class PauseScreen extends WindowScreen{ 
// DISPOSABLE
    private Stage stage;
    private Skin skin; 
    private Texture bckTexture =  new Texture(  "img/game/pause/sinai.jpg"  ); 
	
// USED 
    // used by resize to kkep in center 
    private TextButton stickButton; 
    private TextButton bExit,bContinue,bMain,bSetting,bChangeOrientation;  
    private Label label; 
    private Table table; 

	private boolean goOut;
	private ScrollPane scrollPane;

	public void render (float delta){
	   stage.act(delta);		
	   stage.draw();
	   if (Global.debug) { Table.drawDebug(stage); }
	   super.render(delta); 
	   if (goOut) { Global.screenCaller.restoreScreen(); }
	}
	

	public void show (){
	        super.show() ; 
		stage = new Stage();
		stage.addListener(new InputListener(){
                   @Override
	           public boolean keyDown(InputEvent event, int keycode) {
	               switch (keycode){
	                 case Keys.BACK   : 
	                 case Keys.ESCAPE : 
	                 case Keys.B      : 
	                 case Keys.MENU   :
			    goOut = true; 
	                    return true;  
	                 default : 
	                     return false; 
	               }  
                   } 
                }); 
		Gdx.input.setInputProcessor(stage); 
		
		    // we restore it in SingleScreen


		skin = new Skin(Gdx.files.internal("img/game/pause/pause_skin.json"), 
		    new TextureAtlas("img/game/setting/Setting.atlas"));	   
		table = new Table();
		
	// CONTENT 
		// IMAGE 
		Image image = new Image(bckTexture);
		image.setFillParent(true);
		
	  // PAUSE LABEL 
	        label = new Label( "PAUSE"  , skin ); 
		label.setAlignment(Align.center);
		
		
      // continue BUTTON 
                bContinue = new TextButton("CONTINUE",skin);
		bContinue.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
			   super.clicked(event, x, y);
			   Global.screenCaller.restoreScreen(); 
			}
		});
		
	  	
		
      // setting Button 
		bSetting = new TextButton("Settings",skin);
		bSetting.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
		          super.clicked(event, x, y);
			  WindowScreen last = Global.screenCaller.lastWindow; 
			  Global.screenCaller.callSetting(); 
			  Global.screenCaller.lastWindow = last; 
			  last = null; // just to be sure
			}
		});
		
        // main menu
		bMain = new TextButton("Main Menu",skin);
		bMain.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Global.game.restart(); 
				PauseScreen.this.dispose(); 

			}
		});
		
        // exit
		bExit = new TextButton("Exit",skin);
		bExit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
                                Gdx.app.exit();
			}
		});
	
        // exit
		bChangeOrientation = new TextButton("Orientation",skin);
		bChangeOrientation.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
			        System.out.println("Pause: I change Orientation "); 
				super.clicked(event, x, y);
				if (Global.platformOs.getOrientation() == Orientation.PAYSAGE){
				  Global.platformOs.setOrientation(Orientation.PORTRAIT); 
				  System.out.println("Pause I change orientaiton to portrait"); 
		                }
				else {  
				  Global.platformOs.setOrientation(Orientation.PAYSAGE);
				  System.out.println("Pause I change orientaiton to paysage"); 
				}
			}
		});

	// sitck button, in the middle of road sign
	        stickButton = new TextButton("",skin,"green"); 
		stickButton.setDisabled(true); 
		
		float bWidth = stage.getWidth()/1.3f;        
		float pad    = stage.getHeight()/40f; 
		
	// PACK everything 	
		scrollPane = new ScrollPane(table);
		scrollPane.setFillParent(true); 
		scrollPane.setScrollingDisabled(true,false);
		table.add(label)    .width(bWidth).pad(4*pad).row();

		
                table.add(bContinue).width(bWidth).pad(pad).row()      ;
		table.add(bSetting) .width(bWidth).pad(pad).row()      ;
		table.add(bMain)    .width(bWidth).pad(pad).row()      ;
		table.add(bExit)    .width(bWidth).pad(pad).row()      ;
                if (Global.debug) {table.add(bChangeOrientation).width(bWidth).pad(pad).row()     ; }

		table.debug(); 

        stage.addActor(image);
        stage.addActor(scrollPane);

	}
	
        public void resize(int width, int height){
		super.resize(width, height);
		float sWidth = width/11f;
		stickButton.setBounds(stage.getWidth()*0.5f - sWidth*0.5f, -16 , sWidth, stage.getHeight()*0.65f);

		stage.getViewport().update(width, height, true);
	}

	public void dispose (){
		stage.dispose();
		skin.dispose();
		bckTexture.dispose(); 
	}


        
	
}
