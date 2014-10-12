package com.mygdx.game.utils;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.WindowScreen;
import com.mygdx.game.utils.Global.Global;

public class InputMakeEasy extends InputAdapter{
     private TouchAction touchAction; 
     private WindowScreen window; 
     private OrthographicCamera camera; 


     public InputMakeEasy(WindowScreen window){
       this.window = window; 
     }



	@Override
	public boolean keyDown(int keycode) {

		if (window.place == WindowScreen.Place.RIGHT){ 
		     switch(keycode) {
		     case Keys.J:
		     	touchAction.wasTouched(TouchAction.Side.LEFT);
		     	break;
		     case Keys.L:
		     	touchAction.wasTouched(TouchAction.Side.RIGHT);
		     	break;
		     case Keys.I:
		     	touchAction.wasTouched(TouchAction.Side.UP);
		     	break;
		     case Keys.K:
		     	touchAction.wasTouched(TouchAction.Side.DOWN);
		     	break;
		     default:
		     }
		}else{
		     switch(keycode) {
		     case Keys.S:
		     	touchAction.wasTouched(TouchAction.Side.LEFT);
		     	break;
		     case Keys.F:
		     	touchAction.wasTouched(TouchAction.Side.RIGHT);
		     	break;
		     case Keys.E:
		     	touchAction.wasTouched(TouchAction.Side.UP);
		     	break;
		     case Keys.D:
		     	touchAction.wasTouched(TouchAction.Side.DOWN);
		     	break;
		     default:
		     }
		}
                switch(keycode) {
	           case Keys.BACK   : 
	           case Keys.ESCAPE : 
		     Global.screenCaller.callPause(); 
		     return true; 
	           case Keys.B      : 
	           case Keys.MENU   :
		     Global.screenCaller.callSetting(); 
		     return true; 
	           default : 
	        }
		if (keycode == Keys.A){touchAction.wasTouched(TouchAction.Side.A) ;return true;  }
	        return false; 
	}

	@Override
	public boolean keyUp(int keycode) {
		if (window.place == WindowScreen.Place.RIGHT){ 
		     switch(keycode) {
		     case Keys.J:
		     	touchAction.wasReleased(TouchAction.Side.LEFT);
		     	break;
		     case Keys.L:
		     	touchAction.wasReleased(TouchAction.Side.RIGHT);
		     	break;
		     case Keys.I:
		     	touchAction.wasReleased(TouchAction.Side.UP);
		     	break;
		     case Keys.K:
		     	touchAction.wasReleased(TouchAction.Side.DOWN);
		     	break;
		     default:
		     	return false;
		     }
		     return true; // an input is given ownLeftWindow(keycode); 
		}else{
		     switch(keycode) {
		     case Keys.S:
		     	touchAction.wasReleased(TouchAction.Side.LEFT);
		     	break;
		     case Keys.F:
		     	touchAction.wasReleased(TouchAction.Side.RIGHT);
		     	break;
		     case Keys.E:
		     	touchAction.wasReleased(TouchAction.Side.UP);
		     	break;
		     case Keys.D:
		     	touchAction.wasReleased(TouchAction.Side.DOWN);
		     	break;
		     default:
		     	return false;
		     }
		     return true; // an input is given ownLeftWindow(keycode); 
		}

	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (screenX < window.wx || screenX > window.wx + window.wwidth ){
		  return false;
		}
		else if (screenX-window.wx< window.wwidth/2 ) {
		  touchAction.wasTouched(TouchAction.Side.LEFT);
		  return true;
		}
		else {
		  touchAction.wasTouched(TouchAction.Side.RIGHT);
		  return true;
		}
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (screenX < window.wx || screenX > window.wx + window.wwidth ){
		  return false;
		}
		else if (screenX-window.wx< window.wwidth/2 ) {
		  touchAction.wasReleased(TouchAction.Side.LEFT);
		  return true;
		}
		else {
		  touchAction.wasReleased(TouchAction.Side.RIGHT);
		  return true;
		}
	}
	
	@Override
	public boolean scrolled(int amount) {
	    if (camera != null) {camera.zoom+=amount/25f;}
	    return false;  
	}



	public void setTouchAction(TouchAction touchAction) {
		this.touchAction = touchAction;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}



  
}
