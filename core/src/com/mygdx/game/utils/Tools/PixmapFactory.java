package com.mygdx.game.utils.Tools;

import java.util.Random;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;

public class PixmapFactory{
	
	public static Pixmap pixmapRandom(int x, int y, Color color, int random){
		Random ranGen = new Random(); 
		Pixmap pixmap = new Pixmap(x, y, Pixmap.Format.RGBA8888); 
		for (int i = 0; i<pixmap.getWidth(); i++){
			for (int j = 0; j<pixmap.getHeight(); j++){
					pixmap.setColor( new Color(
							color.r + (-random + ranGen.nextInt(2*random) )/255f,
							color.g + (0   -random + ranGen.nextInt(2*random) )/255f,
							color.b + (0   -random + ranGen.nextInt(2*random) )/255f,
							1));
					pixmap.drawPixel(i, j );
			}
			System.out.println("Texture Generation lin :" + i + "," +(-random +ranGen.nextInt(2*random)) ); 
		}
		return pixmap; 
	}
	
	public static Texture texturePixel(Color color){
		Pixmap pixmap = monocromaticPixmap(1, 1, color); 
		Texture texture = new Texture(pixmap); 
		pixmap.dispose();
		return texture; 
	}


	
	public static Texture gridPixmap(Pixmap pixmap, int xRepeat,int yRepeat){ // size is the number of bricks
		/* we repeat a texture in a grid to imitate the textute of a wall:
		 */
		Format format = Format.RGBA8888;
		if (xRepeat<0){pixmap = flipPixmapX(pixmap) ; xRepeat *= -1;}
		if (yRepeat<0){pixmap = flipPixmapY(pixmap) ; yRepeat *= -1;}
		
		
		Pixmap out = new Pixmap(xRepeat*pixmap.getWidth(), yRepeat*pixmap.getHeight(), format);
		
	// join the images	
		for (int i= 0 ; i < xRepeat; i++){
			for (int j=0 ; j < yRepeat; j++){
				out.drawPixmap(pixmap, i*pixmap.getWidth(), j*pixmap.getHeight(), 0, 0 , pixmap.getWidth() , pixmap.getHeight());
			}
		}

				
		Texture res = new Texture(out);
		out.dispose();
		return res;

	}
	
	
	public static Texture alphaTexture(FileHandle file , float alpha){
		Pixmap fg= new Pixmap(file); 
		
		// Initially, the mask should have an alpha of 1
		Pixmap mask = new Pixmap(fg.getWidth(), fg.getHeight(), Pixmap.Format.Alpha);

		// Cut a rectangle of alpha value 0
		Pixmap.setBlending(Pixmap.Blending.None);
		mask.setColor(new Color(0f, 0f, 0f, 0.2f));
		mask.fillRectangle(0, 0,fg.getWidth(), fg.getHeight());

		fg.drawPixmap(mask, fg.getWidth(), fg.getHeight());
		Pixmap.setBlending(Pixmap.Blending.SourceOver);
		
		Texture res = new Texture(fg); 
		fg.dispose();
		mask.dispose();
		
		return res; 
	}
	public static Pixmap monocromaticPixmap(int width, int height, Color color){
		Pixmap res = new Pixmap(width, height, Format.RGBA8888);
		
		res.setColor(color);
		res.drawRectangle(0, 0, width, height);
		
		return res; 
	}
	
	public static Pixmap polycromaticPixmap(Color[][] colorArray){ // gives lines and column 
		Pixmap res = new Pixmap(colorArray[0].length, colorArray.length, Format.RGBA8888);
		
		for (int x = 0 ; x < colorArray[0].length ; x ++){
			for (int y=0 ; y < colorArray.length ; y++){

				res.setColor(colorArray[y][x]) ; 
				res.drawPixel(x, y);
			}
		}
		return res; 
	}
	
	
	public static Pixmap circle(int radius,Color color) {
		Pixmap res = new Pixmap( radius , radius , Format.RGBA8888);
		
		res.setColor(color);
		res.fillCircle(radius/2, radius/2, radius/2);
		
		return res; 
	}
	
	
	public static Pixmap flipPixmapY( Pixmap src ) {
	    final int width = src.getWidth();
	    final int height = src.getHeight();
	    Pixmap flipped = new Pixmap(width, height, src.getFormat());

	    for (int x = 0; x < width; x++) {
	        for (int y = 0; y < height; y++) {
	            flipped.drawPixel(x, y, src.getPixel(x, height -y-1));
	        }
	    }
	    return flipped;
	}
	
	
	/** Cut this pixmap to a sub array .
	 * @param src, the pixmap to be cutted
	 * @param x0 the top left starting point
	 * @param y0 the top left starting point
	 * @param width the width in pixel 
	 * @param height the height in pixel  */
	public static Pixmap cutPixmap(Pixmap src, int x0, int y0, int width, int height){
		Pixmap res = new Pixmap(width, height,src.getFormat());
	    for (int x = 0; x < width; x++) {
	        for (int y = 0; y < height; y++) {
	            res.drawPixel(x, y, src.getPixel(x0+x,y0+y));
	        }
	    }
	    return res;
	}
	
	public static Pixmap flipPixmapX(Pixmap src) {
	    final int width = src.getWidth();
	    final int height = src.getHeight();
	    Pixmap flipped = new Pixmap(width, height, src.getFormat());

	    for (int x = 0; x < width; x++) {
	        for (int y = 0; y < height; y++) {
	            flipped.drawPixel(x, y, src.getPixel(width-x-1,y));
	        }
	    }
	    return flipped;
	}

}

