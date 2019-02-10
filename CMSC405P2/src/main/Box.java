/*
 * File Name: Box.java
 * Name: Nicholas Mills
 * Date: 2/10/2019
 * Purpose: Expands upon RectangularPrism, making the top
 * 			face removable.
 */

package main;

import com.jogamp.opengl.GL2;

public class Box extends RectangularPrism {
	
	public boolean open;
	
	public Box(int height, Rectangle shape) {
		super(height, shape);
		open = false;
	}
	
	@Override
	public void draw(GL2 gl2) {
		if (open == false) {
			super.draw(gl2);
		}
		else {
			int length = shape.getLength();
			int width = shape.getWidth();
			
			gl2.glPushMatrix();
	        
			gl2.glPushMatrix();
			gl2.glTranslated(0, 0, -(double)length/2);
	        widthSides.draw(gl2); //front face
	        widthSides.borders(gl2);
	        gl2.glPopMatrix();
	        
	        gl2.glPushMatrix();
	        gl2.glRotated(90, 0, 1, 0);
	        gl2.glTranslated(0, 0, -(double)width/2);
	        lengthSides.draw(gl2); // right face
	        lengthSides.borders(gl2);
	        gl2.glPopMatrix();
	        
	       	gl2.glPushMatrix();
			gl2.glTranslated(0, 0, (double)length/2);
	        widthSides.draw(gl2); //rear face
	        widthSides.borders(gl2);
	        gl2.glPopMatrix();
	        
	        gl2.glPushMatrix();
	        gl2.glRotated(90, 0, 1, 0);
	        gl2.glTranslated(0, 0, (double)width/2);
	        lengthSides.draw(gl2); // right face
	        lengthSides.borders(gl2);
	        gl2.glPopMatrix();
	        
	        gl2.glPushMatrix();
	        gl2.glRotated(90, 1, 0, 0);
	        gl2.glTranslated(0, 0, (double)height/2);
	        shape.draw(gl2); // bottom face
	        shape.borders(gl2);
	        gl2.glPopMatrix();
	        
	        
	        gl2.glPopMatrix(); // Restore matrix to its state before cube() was called.
		}
	}

}
