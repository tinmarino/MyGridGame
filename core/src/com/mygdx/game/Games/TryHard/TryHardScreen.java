package com.mygdx.game.Games.TryHard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.mygdx.game.utils.Classes.GameScreen;
import com.mygdx.game.utils.Global.Global;
import com.mygdx.game.utils.Manager.Particle.Star;

public class TryHardScreen extends GameScreen {
// DISPOSABLE 
	private Player player;
	private ParticleEffect explosion;
	private SpriteBatch batch; 
	public LevelVar lvl; 

	
	private boolean isExplosionCalled = false;
	
	public TryHardScreen() {}
	
	
	public void step(float delta){
		if(!lvl.isBetweenLevels)
		lvl.Particles.add(new Star(lvl));
		player.update(delta);
		lvl.update(delta);
	}
	
	public void draw(float delta){
	        //camera.update(); 
		batch.setProjectionMatrix(HUDMatrix); 
		batch.begin();
		
			lvl.draw(batch);
			player.draw(batch);
			
			if(lvl.hasCollided(player.getBounds())) {
				explosion();
				explosion.draw(batch, delta);
				//willDie = true; 
			}
			
		batch.end();
		
	}
	

	
	@Override
	public void render(float delta) {
		step(delta); 
		draw(delta); 
		super.render(delta); 

	}
	
	
	public void explosion(){
		if (isExplosionCalled  == true ){ return; }
		explosion.setPosition(player.getPosition().x, player.getPosition().y);
		explosion.reset();
		Timer.schedule(new Task(){
		    @Override
		    public void run() {isExplosionCalled = false;}
		}, 3);
		
		isExplosionCalled = true;
		lvl.loseLife();
	}
	
	public void show() {
		super.show();
		lvl = new LevelVar(); 
		lvl.wx = wx         ; lvl.wy= wy ; 
		lvl.wwidth = wwidth ; lvl.wheight = wheight;  
		lvl.life =3; 
		lvl.create(); 
		lvl.reset(); 
		

		batch = new SpriteBatch();
		lvl.Particles.clear();  //
		player = new Player(this);
		Global.inputMultiplexer.addProcessor(0,player);
		
		
	// EXPLOSION 
		explosion = new ParticleEffect();
		explosion.load(Gdx.files.internal(Parameter.explosionP),
				Gdx.files.internal(Parameter.explosion));


	}
	
	
	public LevelVar getLevelVar(){return lvl;}

	@Override
	public void dispose() {
	        super.dispose(); 
		explosion.dispose();
		lvl.dispose();  
	}
	
}
