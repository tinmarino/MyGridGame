package com.mygdx.game.utils.Global;

import com.badlogic.gdx.InputMultiplexer;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.Menu.SingleScreen;




public class Global{
    public static InputMultiplexer inputMultiplexer; 
    public static PlatformOs platformOs; 
    public static boolean debug = true; 
    public static MyGdxGame game; 
    public static SingleScreen screenCaller; 

    public static int VIEWPORT_GUI_HEIGHT =480; 
    public static int VIEWPORT_GUI_WIDTH  =320; 
}
