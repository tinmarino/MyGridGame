package com.mygdx.game.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.Global.Global;

public class AndroidLauncher extends AndroidApplication {

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		PlatformAndroid platformAndroid = new PlatformAndroid();
		platformAndroid.activity = this; 
		Global.platformOs = platformAndroid; 

		initialize(new MyGdxGame(), config);
	}



	@Override
	public void onBackPressed() {
	}
        
}
