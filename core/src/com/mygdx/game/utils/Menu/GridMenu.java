package com.mygdx.game.utils.Menu;
// with a stage  the one I use 

import java.util.ArrayList;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.WindowScreen;
import com.mygdx.game.utils.Classes.CustomViewport;
import com.mygdx.game.utils.Classes.GameScreen;
import com.mygdx.game.utils.Global.GameList;
import com.mygdx.game.utils.Global.GameList.GameClass;
import com.mygdx.game.utils.Global.Global;

public class GridMenu extends WindowScreen{
// DISPOSABLE
private Stage stage; 
private Texture bckTexture = new Texture("img/game/grid_menu/Herca.jpg"); 
private BitmapFont font = new BitmapFont(Gdx.files.internal("font/White32.fnt")); 
private ArrayList<GameClass> gameClassList = GameList.getGameList(); 

// USED 
private boolean goOut = false; // do we pushed back 

///////////////////////////////////////////////////////////////////////////////////////////////
public void render(float delta){
	stage.act();
	stage.draw();

	super.render(delta);
	if (goOut){
	  goOut = false; 
	  Global.game.restart(); 
	  this.dispose(); 
	}
}

public void show(){
	super.show();
    //STAGE	
	stage = new Stage();
	stage.getCamera().viewportWidth = wwidth ; 
	stage.getCamera().viewportHeight= wheight; 
	CustomViewport viewport = 	new CustomViewport(stage.getViewport()); 
	viewport.setBounds(wx,wy,wwidth,wheight); 
	stage.setViewport(viewport);		
	Global.inputMultiplexer.addProcessor(stage);
	stage.addListener(new InputListener(){
                   @Override
           public boolean keyDown(InputEvent event, int keycode) {
	       System.out.println("GridMenu touched" + wx + wy + wwidth); 
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
	Table table = new Table();
	table.setFillParent(true);
	
	

    // ACTORS
    // BACKGROUND IMAGE 
	Image bckImage = new Image( bckTexture ); 
	bckImage.setFillParent(true);
	
    // TITLE 
	LabelStyle labelStyle = new LabelStyle();
	labelStyle.font = font; 
	Label title = new Label("Choose Game", labelStyle);
	title.setAlignment(Align.center);
	
    // IMAGE GAME TABLE
	Table table2 = new Table();
	
	float iPad    = wwidth/24f;
	float iWidth  = wwidth/4f -iPad;
	float iHeight = iWidth;
	if (Gdx.app.getType() == ApplicationType.Android ) {iHeight*=wheight/wwidth;}
	System.out.println("GRID MENU _______________________");

    // Must be a multiple of 3
	for (int j=0; j<30; j++){
	for (int i=1 ; i < gameClassList.size()+1 ; i++ ) {
		final GameClass game = gameClassList.get(i-1);
		Image image = new Image(game.texture);
		image.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				startGame(game);
			}
		});
		
		table2.add(image).width(iWidth).height(iHeight).fill().pad(iPad);
		if ((i+gameClassList.size()*j)%3==0){table2.row();}
	}}


    // Label stupid
	Label endLabel = new Label("Congrats: you are a good nitpicker", labelStyle);
	endLabel.setAlignment(Align.left);
    endLabel.setWidth(wwidth);
    endLabel.setWrap(true);
    table2.row();
    table2.add(endLabel).colspan(3).width(wwidth).left().row();
	
	
    //PACK 
	ScrollPane scrollPane =  new ScrollPane(table2);

	table.add(title).height(stage.getHeight()/6f).expand().fill().center(); 
	table.row();
	table.add(scrollPane).height(stage.getHeight()*5f/6f).expand().fill().center();
	table.row();
	stage.addActor(bckImage);
	stage.addActor(table);
}




public void startGame(GameClass gameClass){
   WindowScreen newGame = GameList.GameRefractor(gameClass.className); 
   Global.screenCaller.changeWindow(newGame,this);  
}


@Override
public void resize(int width,int height){
    super.resize(width,height); 
    ((CustomViewport) stage.getViewport()).setBounds(wx,wy,wwidth,wheight)  ; 
}

public void dispose(){
	super.dispose();

	stage.dispose();
	bckTexture.dispose();
	font.dispose(); 
	GameList.disposeList(gameClassList); 
}

}
