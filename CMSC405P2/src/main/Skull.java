package main;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class Skull extends RectangularPrism {
	private static final double[] HOLE_COLOR = {0,0,0};
	private Rectangle eye;
	private int length;
	private int width;

	public Skull(int height, Rectangle shape) {
		super(height, shape);
		
		eye = new Rectangle(1, 1, HOLE_COLOR);
		length = shape.getLength();
		width = shape.getWidth();
	}
	
	@Override
	public void draw(GL2 gl2) {
		super.draw(gl2);
		
		gl2.glPushMatrix();
		
		gl2.glPushMatrix();
		gl2.glTranslated((double)width/3, (double)height/3, -(double)length/2 - .1);
		eye.draw(gl2);
		gl2.glPopMatrix();
		
		gl2.glPushMatrix();
		gl2.glTranslated(-(double)width/3, (double)height/3, -(double)length/2 - .1);
		eye.draw(gl2);
		gl2.glPopMatrix();
        
        gl2.glPopMatrix();
	}
}
