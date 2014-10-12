package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.Global.Global;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();


		cfg.title = "GRID GAMES 0.1";
		cfg.vSyncEnabled = true;
		cfg.height = (int) (480 *1);
		cfg.width =  (int) (320 *1);

		Global.platformOs = new PlatformDesktop();


		new LwjglApplication(new MyGdxGame(), cfg);
	}
}
