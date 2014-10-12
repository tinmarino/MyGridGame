package com.mygdx.game.utils.Classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.WindowScreen;
import com.mygdx.game.utils.LevelVariables;
import com.mygdx.game.utils.Global.GameList;
import com.mygdx.game.utils.Global.Global;

public class GameScreen extends WindowScreen{
// DIPOSABLE 
  public SpriteBatch batch; 
  
  public Sprite heartSprite = new Sprite( new Texture("img/game/game_screen/heart_zelda.png")); 
  // the class of the game to refractor 
  public GameList.GameClass gameClass; 
  
  // The number of life in the game 
  public int   life      =3; 

  // The current score of the player; the color of the score display  
  public float score     = 0; 
  public Color scoreColor= Color.WHITE; 
  public BitmapFont white32; 

  // The time passed since the begining, cumul delta
  public float ellapsedTime =0 ; 

  // To leave the game at the end of render loop; 
  public boolean isGameOver = false; 



  public GameScreen(){
    super(); 
    instance = Instance.GAME; 
  }


  @Override 
  public void render( float delta ){
   // TIME COUNT 
     ellapsedTime+=delta;
     if (ellapsedTime > score+1) {
        score= (int) ellapsedTime ;
        if (getLevelVar()!= null) {
          getLevelVar().accelerate();
        } 
     }

  // HUD DISPLAY 
    batch.setProjectionMatrix(HUDMatrix); 
    batch.begin();  
          for (int xm = 0 ; xm < getLevelVar().life; xm++){
                  heartSprite.setPosition( (0.925f-0.05f*xm) * wwidth, 0.95f*wheight); 
		  heartSprite.draw(batch);
          }
          white32.draw(batch, "SCORE: " + Integer.toString((int) score), 0.05f*wwidth, wheight-5 );
    batch.end(); 


    super.render(delta); 
    if (getLevelVar().isGameOver) {Global.screenCaller.callGameOver(this);}
  }


  @Override
  public void show(){
        super.show(); 
        batch   = new SpriteBatch(); 

	white32 = new BitmapFont(Gdx.files.internal("font/White32.fnt")) ;
	white32.setColor(scoreColor); 
	white32.setScale(0.5f);

        heartSprite.setSize(0.05f*wwidth, 0.05f*wwidth);
  }


  

  public LevelVariables getLevelVar(){
    return new LevelVariables(); 
  }

  public void dispose(){
    heartSprite.getTexture().dispose(); 
    super.dispose(); 
  }

}
