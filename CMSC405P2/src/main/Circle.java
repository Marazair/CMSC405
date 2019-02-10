/*
 * File Name: Circle.java
 * Name: Nicholas Mills
 * Date: 2/8/2019
 * Purpose: Contains structure for creating a circle based on a given radius.
 * 			Primarily taken from CartAndWindmillJogl2D.java.
 */

package main;

import com.jogamp.opengl.GL2;

public class Circle extends Shape {
	int radius;
	
	public Circle(int radius, double[] color) {
		this.radius = radius;
		this.color = color;
	}
	
	@Override
	public void draw(GL2 gl2) {
		gl2.glColor3d(color[0], color[1], color[2]);
		gl2.glBegin(GL2.GL_POLYGON);
        for (int d = 0; d < 32; d++) {
            double angle = 2*Math.PI/32 * d;
            gl2.glVertex2d( radius*Math.cos(angle), radius*Math.sin(angle));
        }
        gl2.glEnd();
	}
	
	public int getRadius() {
		return radius;
	}

}
