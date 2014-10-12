
package com.mygdx.game.Games.TryHard;


public class Row {

	public float y; //it's X coordinate
	public float leftWidth; //the current height of the left row (drawn and collided against)
	public float rightWidth; //the current height of the right row (drawn and collided against)

	public float leftTot; //the maximum width of left  row
	public float rightTot; //the maximum width of right row
	
	public boolean active; //if the row is collidable/drawn

	private LevelVar lvl; 
	
	//constructor
	public Row(float center, float tunnelWidth, float Y, boolean active, LevelVar lvl) {
		this.active = active;
		this.y = Y;
		this.lvl = lvl ; 
	
		leftTot = center - tunnelWidth/2;
		rightTot = lvl.wwidth - (center + tunnelWidth/2);
		leftWidth = 0;
		rightWidth = 0;
	}
	
	//when a row needs new information, this is called (usually when lowestRow needs to be moved)
	public void Renew(float center, float tunnelWidth, float width, boolean active) {
		this.active = active;
		y = y + lvl.wheight + width;  /// the only changing line from constructor
		
		leftTot = center - tunnelWidth/2;
		rightTot = lvl.wwidth - (center + tunnelWidth/2);
		leftWidth = 0;
		rightWidth = 0;
	}
	
	//update function (all it does is create the "entering/leaving" effect on the rows. Actual movement is handled in Level)
	public void update(float delta) {
		float dist = lvl.wheight * 1f/8f; //the distance for when a row is supposed to be fully entered in the level
		float ratio = 0.5f *  ((lvl.wheight - y) / dist) + 0.5f; //starting amount for the incoming rows
		
		if(lvl.wheight-dist < y) {
			leftWidth = ratio * leftTot;
			rightWidth = ratio * rightTot;
			if(leftWidth > leftTot) {
				leftWidth = leftTot;
			}
			if(rightWidth > rightTot) {
				rightWidth = rightTot;
			}
		} else if(dist > y) {
			leftWidth -= (lvl.wwidth*1f * delta);
			rightWidth -= (lvl.wwidth*1f * delta);
		}

		
	}
}
