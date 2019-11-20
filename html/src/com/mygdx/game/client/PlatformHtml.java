package com.mygdx.game.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.utils.Global.PlatformOs;

public class PlatformHtml implements PlatformOs {

@Override
public void setOrientation(Orientation orientation) {
    // Clear screeen (hopefully)
    // (Not necessary) Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    if (orientation == Orientation.PAYSAGE){
        // x, y, boolean for fullscreen yes or no 
        Gdx.graphics.setDisplayMode(480,320,false);
    }
    if (orientation == Orientation.PORTRAIT){
        Gdx.graphics.setDisplayMode(320,480,false);
    }

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
