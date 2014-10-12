
package com.mygdx.game.utils.Manager.Particle;


import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Games.TryHard.LevelVar;
import com.mygdx.game.Games.TryHard.Parameter;

public class Star implements BaseParticle {
	private Rectangle bounds;
	private Vector2 position;
	private Vector2 direction;
	private Sprite sprite;
	
	private float speed = 10;

	private LevelVar lvl; 

	
	public Star(LevelVar lvl ) {
	        this.lvl = lvl; 
		init();
	}
	
	private void init() {
		// Random start position;
		Random rand = new Random();
		float x =  rand.nextFloat() *lvl.wwidth;
		float y =  lvl.wheight;
		float scale = 3 + rand.nextInt(4);
		float alpha = rand.nextFloat();
		speed *= rand.nextFloat();
		
		// Give the particle random direction
		direction = new Vector2(0, -rand.nextFloat() - 0.2f);
		
		position = new Vector2(x, y);
		
		sprite = lvl.Textures.getSprite(Parameter.starParticle);
		sprite.setSize(scale, scale);
		sprite.setColor(
				sprite.getColor().r,
				sprite.getColor().g,
				sprite.getColor().b,
				alpha
		);
		
		if(lvl.currentLevel == 8)
			sprite.setColor(0, 0, 0, alpha);
		
		bounds = new Rectangle();
		bounds.setSize(scale);
	}

	@Override
	public void update(float delta) {
		position.x += direction.x * speed;
		position.y += direction.y * speed;
		
		bounds.setPosition(position);
	}

	@Override
	public void draw(SpriteBatch batch) {
		sprite.setPosition(position.x, position.y);
		sprite.draw(batch);
	}

	@Override
	public boolean isAlive() {
		return !(position.y > lvl.wheight ||
				position.x < 0 ||
				position.x > lvl.wwidth||
				position.y < 0);
	}

}
