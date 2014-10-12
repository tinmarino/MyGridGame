package com.mygdx.game.utils;

public interface TouchAction {
   public void wasTouched(Side side);   
   public void wasReleased(Side side);   

   public enum Side{
     UP, DOWN,
     LEFT, RIGHT,
     ZOOM, A // a for accelerate 
   }
}

