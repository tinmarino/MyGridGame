package com.mygdx.game.utils.Manager.Particle;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/*
 * ParticleManager.java
 **/
public class ParticleManager {
	
	// Particle limit during game play
	private final int LIMIT = 500;
	
	private ArrayList<BaseParticle> particles;
	
	public ParticleManager() {
		particles = new ArrayList<BaseParticle>();
	}
	
	public void dispose() {
	}
	
	public void clear() {
		particles.clear();
	}
	
	/*
	 * Adds a new particle to the PM and it gets
	 * updated, rendered and removed during game play
	 **/
	public void add(BaseParticle particle) {
		if(particles.size() < LIMIT) {
			particles.add(particle);
		}
	}
	
	public void render(SpriteBatch batch, float time) {
		for(int i = 0; i < particles.size(); i++) {
			BaseParticle part = particles.get(i);
			part.update(time);
			if(part.isAlive() == false) {
				particles.remove(i);
				continue;
			}
			part.draw(batch);
		}
	}
	
}
