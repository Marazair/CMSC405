package main;

import com.jogamp.opengl.GL2;

public class Rectangle extends Shape2D {
	private double length;
	private double width;
	
	public Rectangle(int length, int width, double[] color) {
		this.length = length;
		this.width = width;
		this.color = color;
	}

	@Override
	public void draw(GL2 gl2) {
		gl2.glColor3d(color[0], color[1], color[2]);
	    gl2.glBegin(GL2.GL_TRIANGLE_FAN);
	    gl2.glVertex3d(-length/2, -width/2, 0);
	    gl2.glVertex3d(length/2, -width/2, 0);
	    gl2.glVertex3d(length/2, width/2, 0);
        gl2.glVertex3d(-length/2, width/2, 0);
        gl2.glEnd();
	}
	
	public double getLength() {
		return length;
	}
	
	public double getWidth() {
		return width;
	}
	
	public void setColor(double[] color) {
		this.color = color;
	}
}
