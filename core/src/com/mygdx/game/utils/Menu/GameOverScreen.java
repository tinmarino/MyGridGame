package com.mygdx.game.utils.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.mygdx.game.WindowScreen;
import com.mygdx.game.utils.Classes.GameScreen;
import com.mygdx.game.utils.Global.GameList;
import com.mygdx.game.utils.Global.Global;


public class GameOverScreen extends WindowScreen{
// DISPOABEL 
	private Stage stage;
	private Skin skin= new Skin(Gdx.files.internal("img/game/game_over_screen/game_over_skin.json"), 
				new TextureAtlas("img/game/game_over_screen/fire_button.pack"));
	private Texture bckTexture = new Texture("img/game/game_over_screen/my_thor.jpg"); 
// what we get 
	private GameScreen window;

// USED 
        private boolean goOut = false; 
	private ScrollPane scrollPane; 

	

	
	
	public GameOverScreen(GameScreen window){
		this.window  = window;
	}
	
	@Override
	public void render (float delta){
		//System.out.println("Game Over render");
		stage.act(delta);
		stage.draw();
		if (Global.debug) {Table.drawDebug(stage);}
		System.out.println("GAMEOVER " + scrollPane.getScrollY() +","+ scrollPane.getScrollHeight());
		super.render(delta);
		if (goOut) {restart();}
	
	}
	
	@Override
	public void show(){
	super.show(); 
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);

      // CONTENT 
        // Background Image 
        Image image = new Image( bckTexture  );
	image.setFillParent(true);
		
        // restart BUTTON
        TextButton bRestart = new TextButton("Restart",skin);
	bRestart.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				goOut = true ;
			}
	});
		
		
        // main menu BUTTON
        TextButton bMain = new TextButton("Main Menu",skin);
        bMain.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
          	  Global.game.restart(); 
                }
        });	
        // exit BUTTON
        TextButton bExit = new TextButton("Exit",skin);
        bExit.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
          	      super.clicked(event, x, y);
          	      Gdx.app.exit();

                }
        });		

        // game over LABEL
	Label lOver = new Label("GAME OVER", skin);
	lOver.setFontScale(1.5f);
	lOver.getStyle().fontColor = Color.RED; 

        // Score label
	Label lScore = new Label("Score: "+ Integer.toString((int) window.score)  , skin);

	// GEOMETRY 
	float bWidth = Gdx.graphics.getWidth() /1.15f;
	float bHeight = 0.4f * bWidth ; 
		
	
      // PACK everything 
        Table table2 = new Table(); 
	table2.setFillParent(true); 
	scrollPane = new ScrollPane(table2); 
	scrollPane.setScrollingDisabled(true,false); 
			Timer.schedule(new Task(){
			    @Override
			    public void run() {
			      scrollPane.setScrollY(scrollPane.getScrollHeight()-100); 
			    }
			}, 0.1f);


	table.add(lOver).expand().row();
	table.add(lScore).expand().row();	
	table.add(scrollPane).expand().row(); 

	table2.add(bRestart).width(bWidth).row();
	table2.add()     .height(wheight/6).width(bWidth).row(); // add empty space

	table2.add(bMain).height(bHeight).width(bWidth).row(); 
        table2.add(bExit).height(bHeight).width(bWidth).row();

        table2.debug(); 
        table.debug();
        stage.addActor(image);
        stage.addActor(table);
	}


	

	
	public void restart(){
	        if (window != null ) {window.dispose(); }
		GameScreen newGame = GameList.GameRefractor(window.gameClass.className);
		if (Global.screenCaller.instance == SingleScreen.Instance.SINGLE) {
		      Global.screenCaller.lastWindow = newGame;
	              Global.screenCaller.restoreScreen(); 
		      Global.screenCaller.show(); 
		      return; 
	        }
		else if (Global.screenCaller.instance == SingleScreen.Instance.DOUBLE) {
		  if      (window.place == WindowScreen.Place.LEFT){
		      ((DoubleScreen)Global.screenCaller).window_left  =  newGame; 
		      ((DoubleScreen)Global.screenCaller).showLeft(); 
		     
		  }
		  else if (window.place == WindowScreen.Place.RIGHT){
		      ((DoubleScreen)Global.screenCaller).window_right = newGame ;  
		      ((DoubleScreen)Global.screenCaller).showRight(); 
		  }
		}
		
	        Global.screenCaller.restoreScreen(); 
	}
	
	@Override
	public void dispose (){
		stage.dispose();
		skin.dispose();
		bckTexture.dispose();
	}

}
