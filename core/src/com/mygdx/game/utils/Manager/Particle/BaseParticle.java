package com.mygdx.game.utils.Manager.Particle;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/*
 * Interface for all the particles to implement
 **/
public interface BaseParticle {
	
	void update(float delta);
	
	void draw(SpriteBatch batch);
	
	boolean isAlive();

}
