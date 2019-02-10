/*
 * File Name: Shape.java
 * Name: Nicholas Mills
 * Date: 2/06/2019
 * Purpose: Contains the very basic elements of what makes a shape.
 * 			Inherited by other shape-based classes.
 */

package main;

import com.jogamp.opengl.GL2;

public abstract class Shape {
	protected double[] color;
	
	public abstract void draw(GL2 gl2);
	
	public void setColor(double[] color) {
		this.color = color;
	}
}
