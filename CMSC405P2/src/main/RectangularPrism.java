/*
 * File Name: RectangularPrism.java
 * Name: Nicholas Mills
 * Date: 2/08/2019
 * Purpose: Contains structure for taking a rectangle and creating
 * 			a prism according to it and a corresponding height.
 */

package main;

import com.jogamp.opengl.GL2;

public class RectangularPrism extends Shape {
	protected int height;
	protected Rectangle shape;
	protected Rectangle lengthSides;
	protected Rectangle widthSides;
	
	public RectangularPrism(int height, Rectangle shape) {
		this.height = height;
		this.shape = shape;
		
		lengthSides = new Rectangle(height, shape.getLength(), shape.color);
		widthSides = new Rectangle(height, shape.getWidth(), shape.color);
	}
	
	@Override
	public void draw(GL2 gl2) {
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
        gl2.glRotated(90, 1, 0, 0);
        gl2.glTranslated(0, 0, -(double)height/2);
        shape.draw(gl2); // top face
        shape.borders(gl2);
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
	
	@Override
	public void setColor(double[] color) {
		shape.color = color;
		lengthSides.color = color;
		widthSides.color = color;
	}
}
