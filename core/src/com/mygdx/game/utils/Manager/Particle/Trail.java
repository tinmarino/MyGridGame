
package com.mygdx.game.utils.Manager.Particle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Games.TryHard.LevelVar;
import com.mygdx.game.Games.TryHard.Parameter;

public class Trail implements BaseParticle {
	
	private float positionX;
	private float positionY;

	private int HEIGHT;
	private int WIDTH;
	
	private Color color;
	private float alpha;
	private final float alpha_reduction = 16;
	
	private Sprite sprite;
	private LevelVar lvl; 
	
	
	public Trail(float pos,LevelVar lvl) {
	        this.lvl = lvl;
		this.positionX = pos;
		Init();
	}
	
	/*
	 * Initializes the main components of the trail.
	 * Loads in the correct texture to the sprite.
	 */
	private void Init() {		
		//Load texture
		sprite = lvl.Textures.getSprite(Parameter.trail);
		
		//Sets x position, height and width.
		positionY = lvl.wheight/6f;
		WIDTH =  lvl.wwidth/16;	
		HEIGHT = lvl.wheight/64;
		positionX+=lvl.playerSize/2-WIDTH/2f ; 
		
		//Sets starting alpha value
		alpha = 100;		
			
	}
	
	@Override
	public void update(float delta) {
		//Moves the trail DOWN  by it's height amount
		positionY -= HEIGHT+2;
		
		//Reduces the alpha value
		alpha -= alpha_reduction ;
		
		//Gets the current color off the background and sets it to the color of the trail
		Color t = lvl.currentColor;
		
		if(lvl.currentLevel == 8)
			t = Color.GRAY;
		if(lvl.currentLevel == 9)
			t = Color.WHITE;
		color = new Color(t.r, t.g, t.b, alpha/255f);
	}

	@Override
	public void draw(SpriteBatch batch) {
		//Sets the position, size and color of the trail
		sprite.setBounds(positionX, positionY,WIDTH,HEIGHT);
		sprite.setSize(WIDTH, HEIGHT);
		sprite.setColor(color);
		
		//Draws the trail
		sprite.draw(batch);
	}

	@Override
	public boolean isAlive() {
		return (alpha > 0);
	}

}
