/*
    Handle platform specific action for HTML

    Screen horizontal or vertical, all is calculated for a 480 / 320 = 1.5 ration
        Same equation as in HtmlLauncher
*/

package com.mygdx.game.client;

// Import Gdx
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.utils.Global.PlatformOs;

// Import Html
import com.google.gwt.user.client.Window;
import java.lang.Math;

public class PlatformHtml implements PlatformOs {

@Override
public void setOrientation(Orientation orientation) {
    int width_desired = 320;
    int height_desired = 320;

    // Clear screeen (hopefully)
    // (Not necessary) Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    // Get client size
    int height = Window.getClientHeight();
    int width = Window.getClientWidth();


    if (orientation == Orientation.PAYSAGE){
        // Get desired size
        width_desired = (int) (0.95 * Math.min(width, 1.5 * height));
        height_desired = (int) (0.95 * Math.min(height, 0.67 * width));

    }
    if (orientation == Orientation.PORTRAIT){
        // Get desired size
        width_desired = (int) (0.95 * Math.min(width, 0.67 * height));
        height_desired = (int) (0.95 * Math.min(height, 1.5 * width));
    }

    // Change size
    // x, y, boolean for fullscreen yes or no
    Gdx.graphics.setDisplayMode(width_desired, height_desired, false);

}

@Override
public Orientation getOrientation() {
    return null;
}

@Override
public int[] getSize() {
    return null;
}

}
