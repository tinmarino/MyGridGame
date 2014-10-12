package com.mygdx.game.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.Global.Global;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(320, 480);
		Global.platformOs = new PlatformHtml(); 
		return cfg; 
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new MyGdxGame();
        }
}
