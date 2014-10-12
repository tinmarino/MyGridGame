package com.mygdx.game.Games.TryHard;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.utils.LevelVariables;
import com.mygdx.game.utils.Global.GamePrefs;
import com.mygdx.game.utils.Manager.TextureManager;
import com.mygdx.game.utils.Manager.Particle.ParticleManager;

public class LevelVar extends LevelVariables{
        public  boolean isBetweenLevels=true; 
        public   ParticleManager Particles; 
	public   TextureManager Textures; 
	public  int wx,wy; 
	public  int wwidth,wheight; 
        public   float 	playerSize = 30 ;
	public   int currentLevel; 

	public  Row rows[]; 
	
        // PARAMETERS 
	public  float distanceSinceLastPoint;
	public  float distanceBetweenPoints;
	public  float currentPoint; //the Y coordinate for the current point (0.0f is top, 1.0 is bottom)
	public  float nextPoint; //the Y coordinate for the next point (0.0f is top, 1.0 is bottom)
	public  float tunnelWidth; //the width of the tunnel (measured in pixel amounts)

	public  int lowestRow; //the position of the leftmost row
	public  float levelSpeed; //the speed the level travels, measured in pixels per second
	public  float rowWidth; //the width of an individual row, measured in pixels
	public  int nbOfRows; //the total amount of rows for easy reference
	
	public  float timeUntilLevelStarts; //the amount of time left for the pauses
	public  float totalPauseTime; //the total amount of time for pauses
	public  float timeUntilLevelEnds; //the amount of time left for the actual level
	public  float timeBetweenLevels; //the total amount of time for the level
	public  float maximumPointDifference; //determines the maximum difference a between points when randomly determining the next point (measured in percentages)

	public  float TURN_SPEED=3; 

	public  Background background; //the moving background

	private  Random random = new Random(); //for randomisation

	public  float     playerTurnSpeed = wheight;

	private  Sprite sprite,pauseSprite; 



	public   Color[] colors = {
			new Color(142/255.0f, 56/255.0f, 255/255.0f, 1.0f),	// Purple
			new Color(255/255.0f, 124/255.0f, 31/255.0f, 1.0f),	// Orange
			new Color(31/255.0f, 255/255.0f, 218/255.0f, 1.0f),	// Teal
			new Color(255/255.0f, 31/255.0f, 68/255.0f, 1.0f),	// Red
			new Color(42/255.0f, 31/255.0f, 255/255.0f, 1.0f),	// Blue
			new Color(31/255.0f, 255/255.0f, 46/255.0f, 1.0f),	// Green
			new Color(255/255.0f, 0/255.0f, 204/255.0f, 1.0f),	// Pink
			new Color(0/255.0f, 0/255.0f, 0/255.0f, 1.0f),		// Black
	};
	
	public   Color currentColor;
 
	public  void create(){
		Textures = new TextureManager();
		Particles = new ParticleManager();
		background = new Background(this);



		tunnelWidth = wwidth*0.5f; //default tunnelwidth is half a screen wide.
		nbOfRows = 1+(int)wheight/15; //on a 480x320 resolution, this results in 36 rows. The higher resolution, the more rows. The extra +1 is for smoothness
		rowWidth = 1f*wheight / (nbOfRows-1);
		distanceBetweenPoints = wheight/4; //nextpoint is determined after a fourth of a screen
		levelSpeed = wheight / 3f * GamePrefs.getTimeStep(GamePrefs.preferences.getInteger(GamePrefs.TIMESTEP) ) / LevelVariables.DEFAULT_TIMESTEP;  // the second number is the freq in Hz
		currentPoint = 0.5f; //percentage of the games height where the current point is
		nextPoint = 0.5f; //to avoid problems, the next point has the same percentage
		currentLevel = 0;
		timeBetweenLevels = 20f; //the first level is 20 seconds long, higher levels are longer
		timeUntilLevelEnds = timeBetweenLevels;
		totalPauseTime = 3f; //the intermission time is always three seconds
		timeUntilLevelStarts = totalPauseTime;
		distanceSinceLastPoint = 0.0f;
		maximumPointDifference = 0.4f; //in order to not have too much of a difficulty in the game, maximum change interval between points is 40%. This is never changed
	}

	public  void dispose() {
		Textures.dispose();
		Particles.dispose();



	}



	
	//resets all values to their default. Is called when the player starts/restarts the game
	public  void reset() {
		isBetweenLevels = true; //the game starts in intermission

		sprite = Textures.getSprite(Parameter.row); //all the rows look the same, and thus share the same sprite
		sprite.flip(true,false); 
		pauseSprite = Textures.getSprite(Parameter.newLevel); //the pausetext needs it's own sprite
		pauseSprite.setColor(Color.WHITE); 
		pauseSprite.flip(false,true); 
		
		rows = new Row[nbOfRows]; //the array that holds the rows
		for(int i = 0; i < rows.length; i++)
		{
			 //all the rows are set as a straight line as default. Not that it matters since it'll be part of the intermission
			rows[i] = new Row(wwidth/2, tunnelWidth, (rows.length - (i-1))*rowWidth, !isBetweenLevels,this);
		}
		lowestRow = nbOfRows-1; //the lowest row is last place in the array
		
	}
	
	//collision-detection, that checks only against the rows that "contain" the same Y coordinates as the player
	//rect is (usually) the player rectangle
	public  boolean hasCollided(Rectangle rect) {
		//this part determines which part of the row-array "contains" the player
		int start = ( 2 * nbOfRows + lowestRow - (int)(rect.y/rowWidth)-1)%nbOfRows;
		int nrOfExtraRowsToCheck = 0;
		
		//while this checks for additional rows to check due to the players size
		while(rows[(2*nbOfRows+start-nrOfExtraRowsToCheck)%nbOfRows].y+rowWidth < rect.y + rect.height)
		{
			nrOfExtraRowsToCheck++;
		}
		
		//no point in checking if the rows aren't visible or collidable
		if(rows[start].active)
		{
			if(rect.x < rows[start].leftWidth)
				return true;
			if(rect.x+rect.width > (wwidth- rows[start].rightWidth))
				return true;
		}
		
		//uses modulus to determine which part of the array to check
		for(int i = start-1; i >= start-nrOfExtraRowsToCheck; i--)
		{
			if(rows[(nbOfRows+i)%nbOfRows].active)
			{
				if(rect.x + rect.width/2f < rows[(nbOfRows+i)%nbOfRows].leftWidth)
					return true;
				if((rect.x+rect.width/2f) > wwidth - rows[(nbOfRows+i)%nbOfRows].rightWidth)
					return true;
			}
		}
		
		//if nothing returned true, then obviously there was no collision
		return false;
	}

	
	//triggers once per intermission, when it ends
	private  void endPauseTrigger() {
		//increase in difficulty
		if(currentLevel < 9){
			currentLevel++; //a higher level
			tunnelWidth *= 0.95f; //tighter tunnel
			levelSpeed *= 1.05f; //faster level
			timeBetweenLevels *= 1.05f; //longer level
			TURN_SPEED *= 1.05f; //the player can move faster
		}
		
		timeUntilLevelStarts+= totalPauseTime; //reset pause timer for the next intermission
		isBetweenLevels = false; //end of intermission
	}
	
	//triggers once per intermission, when it starts
	private  void startPauseTrigger() {
		timeUntilLevelEnds += timeBetweenLevels; //reset the level timer for the next level
		isBetweenLevels = true; //start of intermission
		background.setColorNext();
	}
	
	//the main update function
	//delta is the amount of time since the last update
	public  void update(float delta) {		
		background.update(delta); //background update (movement and such)
		
		//TIMERS
		if(isBetweenLevels) //if there's an intermission...
		{			
			timeUntilLevelStarts -= delta;
			if(timeUntilLevelStarts <= 0)
			{
				endPauseTrigger(); //intermission end trigger
			}
		}
		else {
			timeUntilLevelEnds -= delta;
			if(timeUntilLevelEnds <= 0 && currentLevel < 9)
			{
				startPauseTrigger(); //intermission start trigger (there are no intermissions on level 9
			}
		}
		
		//LEVEL CALCULATIONS
		distanceSinceLastPoint+= levelSpeed*delta;
		
		//determines what Y-percentage the next point will have
		if(distanceSinceLastPoint >= distanceBetweenPoints)
		{
			distanceSinceLastPoint -= distanceBetweenPoints; //reset distance

			float convertedHalfTW = (tunnelWidth/wheight)/2; //conversion for half the current tunnelwidth in a percentage for easy reference and less calculations
			
			currentPoint = nextPoint;
			float deltaValue = -maximumPointDifference + random.nextFloat()*(maximumPointDifference*2); //how much the points will differ
			
			nextPoint = currentPoint +deltaValue;

			//correctional calculations (0.05f is a buffer from the edges of the game screen). This prevents the next point from ending up outside the screen
			if(nextPoint < convertedHalfTW+0.05f || nextPoint > (0.95f - convertedHalfTW))
				nextPoint = currentPoint - deltaValue;
			
			//if the last one doesn't do it, this one will
			if(nextPoint < convertedHalfTW+0.05f)
				nextPoint = convertedHalfTW+0.05f;
			if(nextPoint > (0.95f - convertedHalfTW))
				nextPoint = 0.95f - convertedHalfTW;
		}

		//INDIVIDUAL ROW CALCULATIONS
		
		//update for every row
		for(int i = 0; i < rows.length;i++)
		{
			rows[i].y -= levelSpeed*delta;
			rows[i].update(delta);
		}
		
		//moves rows that have fully gone from top to bottom back to the start. LowestRow determines which row is the more at the bottom of the screen in the array 
		while(rows[lowestRow].y <= -rowWidth)
		{
			float ratio = distanceSinceLastPoint / distanceBetweenPoints;
			float activePoint = (currentPoint + (ratio * (nextPoint - currentPoint)))*wwidth;
			
			rows[lowestRow].Renew(activePoint, tunnelWidth, rowWidth, !isBetweenLevels);
			lowestRow--;
			if(lowestRow < 0)
			lowestRow = nbOfRows-1;
		}
	}
	
	//draw-function
	//batch allows the drawing of sprites
	public   void draw(SpriteBatch batch) {
		background.draw(batch); //draws the background
		
		Particles.render(batch, Gdx.graphics.getDeltaTime()); //draws particles
		
		//determines the position of and draws the pausesprite
		if(isBetweenLevels && currentLevel >= 1)
		{
			float xPos = (float)Math.pow(totalPauseTime/2 - timeUntilLevelStarts, 4f)*wwidth/2; //results in a form of swooshing effect
			
			if(timeUntilLevelStarts > totalPauseTime/2)
				xPos = wwidth/2 + xPos;
			else
				xPos = wwidth/2 - xPos;
			pauseSprite.setBounds(xPos, wheight/4, wwidth/4, wheight/8);
			pauseSprite.draw(batch);
		}
		
		//drawing the rows
		for(int i = 0; i < nbOfRows; i++)
		{
			if(rows[i].active) //doesn't draw the rows if they aren't collidable (during an intermission, for example)
			{
				//flips the sprite twice, once for each side of the tunnel
			        sprite.flip(true,false); 
				sprite.setBounds(wwidth-rows[i].rightWidth,rows[i].y,rows[i].rightWidth, rowWidth);
				sprite.draw(batch);
			        sprite.flip(true,false); 
				sprite.setBounds(0,rows[i].y,rows[i].leftWidth, rowWidth);
				sprite.draw(batch);
			}
		}
	}
}
