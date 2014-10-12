package com.mygdx.game.Games.TryHard;

import java.util.ArrayList;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.WindowScreen;
import com.mygdx.game.utils.Global.Global;
import com.mygdx.game.utils.Manager.Particle.BaseParticle;
import com.mygdx.game.utils.Manager.Particle.Trail;

/*
 * The Player!
 **/
public class Player extends InputAdapter {

	
	// How fast the player can move up and down
	// Size of the player in pixels
	
	// Used for collision
	private Rectangle bounds;
	
	// Position of the player
	private Vector2 position;
	
	// Image of the player (triangle)
	private Sprite sprite;
	
	// Where on the screen we have touched (Y-coord only)
	private float mouseTochedY=0;
	private float mouseTochedX=0; // we reduce the half payerWidth
	
	// The current rotation of the player (triangle)
	private float rotation;
	
	private ArrayList<BaseParticle> trail;

	private WindowScreen window;
	private LevelVar lvl; 

	
	public Player(TryHardScreen caller) {
	        this.window = caller; 
		this.lvl = caller.lvl; 
		this.init();
	}

	void init() {
		
		bounds = new Rectangle();
		bounds.setSize(lvl.playerSize, lvl.playerSize);
		
		sprite = lvl.Textures.getSprite(Parameter.player);
		sprite.flip(false,true);
		sprite.setSize(lvl.playerSize,lvl.playerSize);
		sprite.setOrigin(0, sprite.getHeight() / 2);
		
		trail = new ArrayList<BaseParticle>();
		
		this.reset();
	}
	
	public void reset() {
		position = new Vector2(lvl.wwidth/2, lvl.wheight/6f);
	}

	public void update(float delta) {
		// This is to prevent flickering when the player moves
		float fm = lvl.playerTurnSpeed * delta;
		if(position.x < (mouseTochedX - fm) || position.x > (mouseTochedX + fm)) {
			if(position.x < mouseTochedX) {
				position.x += fm;
			} else if(position.x > mouseTochedY) {
				position.y -= fm;
			}
		}
		position.x = mouseTochedX; 
		
		float tempRot = ((mouseTochedX - position.x) / (lvl.wwidth / 4)) * 45;
		rotation = MathUtils.clamp(tempRot, -45, 45);
		
		if(position.x < 0) {
			position.x = 0;
		} else if(position.x + lvl.playerSize > lvl.wwidth) {
			position.x = lvl.wwidth - lvl.playerSize;
		}
		
		UpdateTrail(delta);
		
		// Update collision box
		bounds.setPosition(position);
	}

	public void draw(SpriteBatch batch) {
		DrawTrail(batch);
		
		sprite.setRotation(rotation);
		sprite.setPosition(position.x, position.y);
		sprite.draw(batch);

	}
	
	public Rectangle getBounds() {
		return this.bounds;
	}

	public Vector2 getPosition() {
		return this.position;
	}
	
	/*
	 * Updates the trail behind the player.
	 * Removes the trail if alive is false
	 * Adds a new trail every update
	 */
	private void UpdateTrail(float time) {
		Trail t = new Trail(getPosition().x,lvl);
		trail.add(t);
		
		for(int i = 0; i < trail.size(); i++){
			t = (Trail) trail.get(i);
			t.update(time);
			if(!t.isAlive())
				trail.remove(i);
		}	
	}
	/*
	 * Draws the trail behind the player
	 */
	private void DrawTrail(SpriteBatch batch) {
		for(int i = 0; i < trail.size(); i++) {
			Trail t = (Trail) trail.get(i);
			t.draw(batch);
		}
	}
	
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (screenX < window.wx || screenX > window.wx + window.wwidth ){return false;}
		mouseTochedX = screenX-lvl.wx-lvl.playerSize/2f; 
		return true;
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return touchDown(screenX, screenY, pointer, 0);
	}


	@Override
	public boolean keyDown(int keycode) {
                switch(keycode) {
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

	
}
