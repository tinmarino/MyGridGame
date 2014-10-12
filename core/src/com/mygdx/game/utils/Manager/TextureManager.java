package com.mygdx.game.utils.Manager;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class TextureManager {
	
	private Map<String, Texture> textures = new HashMap<String, Texture>();
	
	public void dispose() {
		for(Texture tex: textures.values()) {
			tex.dispose();
		}
	}
	
	public Texture getTexture(String path) {
		Texture tex = null;
		if(textures.containsKey(path)) {
			tex = textures.get(path);
		} else {
			tex = new Texture(Gdx.files.internal(path));
			textures.put(path, tex);
		}
		return tex;
	}
	
	// Returns a sprite with flipY == true
	public Sprite getSprite(String path) {
		Texture tex = getTexture(path);
		Sprite sprite = new Sprite(tex);
		sprite.flip(false, true);
		return sprite;
	}
	
}
