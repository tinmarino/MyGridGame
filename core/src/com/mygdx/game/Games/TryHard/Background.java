package com.mygdx.game.Games.TryHard;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/*
 * Used in Level.java for changing the background color
 * when the Player gets to a new level.
 **/
public class Background {
	
	
	
	private Color targetColor;
	private int colorIndex = 0;
	
	private boolean doLerp;
	private float lerpTimer;
	
	// Interval in i secs between color change.
	private final float LERP_TIME = 3.0f;
	
	// The background being rendered.
	private Sprite background;
	// Used on top of background for nice effect.
	private Sprite tint;
	// Current position of the background.
	private float positionY;
	
	// How fast the background should move.
	// Gives the illusion of moving in space.
	private final float SPEED = 50.0f;
	private LevelVar lvl ; 
	
	public Background(LevelVar lvl) {
	        this.lvl = lvl ; 
		background = lvl.Textures.getSprite(Parameter.background);
		tint = lvl.Textures.getSprite(Parameter.backgroundTint);
		
		background.setSize(lvl.wwidth, lvl.wheight);
		tint.setSize(lvl.wwidth, lvl.wheight);
		tint.flip(false,true); 
		
		lvl.currentColor = new Color(lvl.colors[colorIndex]);

		this.targetColor = new Color();
	}
	
	public void update(float time) {
		if(doLerp) {
			float lerpValue = lerpTimer / LERP_TIME;

			lvl.currentColor.lerp(targetColor, lerpValue);

			lerpTimer += time;
			if(lerpTimer >= LERP_TIME) {
				lerpTimer -= LERP_TIME;
				doLerp = false;
			}
		}

		positionY -= SPEED * time;
		if(positionY < -background.getHeight()) {
			positionY = 0;
		}
	}
	
	public void draw(SpriteBatch batch) {
		background.setColor(lvl.currentColor);
		background.setPosition(0, positionY + background.getHeight() - 1);
		background.draw(batch);
		background.setPosition(0,positionY);
		background.draw(batch);
		tint.draw(batch);
	}
	
	/*
	 * Sets the background color to specified color.
	 **/
	public void setColor(Color color) {
		doLerp = true;
		targetColor.set(color);
	}
	
	/*
	 * Sets the background color to the next one in the colors array.
	 **/
	public void setColorNext() {
		lerpTimer = 0;
		doLerp = true;
		colorIndex++; if (colorIndex >= lvl.colors.length) {colorIndex=0;}
		targetColor.set(lvl.colors[colorIndex]);
	}
	
}
