package com.mygdx.game.client;

// Import Gdx
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.Global.Global;

// Import Html
import com.google.gwt.user.client.Window;

public class HtmlLauncher extends GwtApplication {

    @Override
    public GwtApplicationConfiguration getConfig () {
        // Get client size
        int height = Window.getClientHeight();
        int width = Window.getClientWidth();

        // Get desired size
        int width_desired = (int) (0.95 * Math.min(width, 0.67 * height));
        int height_desired = (int) (0.95 * Math.min(height, 1.5 * width));

        // Configure size
        GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(width_desired, height_desired);

        // Create Global platform class
        Global.platformOs = new PlatformHtml();

        // Return cfg
        return cfg;
    }

    @Override
    public ApplicationListener getApplicationListener () {
            return new MyGdxGame();
    }
}
