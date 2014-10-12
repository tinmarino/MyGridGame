package com.mygdx.game.utils;
/*
 *    RENDER 
 *         in HUD draw 
 * 		   float offset = -lvl.scrollingSpeed*delta / (player.upLeft.y*2);
	       scrolableBackground.render(batch,offset);
	}
	
	public ScrolableBackground(WindowScreen window , int xRepeat, int yRepeat, FileHandle file) {
 * 
 */




import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScrolableBackground {
// what we GET, in constructor 
// what we CREATE
	public Sprite sprite; 
	public float shift=0;
	
	

	public ScrolableBackground(Texture bckTexture,int width,int height){
           bckTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
	   sprite =new Sprite(bckTexture);
           sprite.setPosition(0,0);
           sprite.setSize(width, height);
	}
	
	
	
	public void render(SpriteBatch batch, float offset , Dir dir){
	// Scrolling Y down 
		 this.shift+=offset;
		 if   (shift>1.0f) {shift = 0.0f;}
		 if (shift< -1.0f) {shift = 0.0f;}
		 switch (dir){
		 case N:
		     sprite.setV(shift);
		     sprite.setV2((shift-1)); // -1 to go forward, +1 back, V y , U x
		     break; 
		 case S:
		     sprite.setV(shift);
		     sprite.setV2((shift+1)); // -1 to go forward, +1 back, V y , U x
		         break;
		 }
		 
	     sprite.draw(batch);
	}
	
	
  public enum Dir{N,S}

	
	

}
	
	
	
	
	
	
