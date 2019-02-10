package main;

import com.jogamp.opengl.GL2;

public class Rectangle extends Shape {
	private int length;
	private int width;
	
	public Rectangle(int length, int width, double[] color) {
		this.length = length;
		this.width = width;
		this.color = color;
	}

	@Override
	public void draw(GL2 gl2) {
		gl2.glColor3d(color[0], color[1], color[2]);
	    gl2.glBegin(GL2.GL_TRIANGLE_FAN);
	    gl2.glVertex3d(-(double)width/2, -(double)length/2, 0);
	    gl2.glVertex3d((double)width/2, -(double)length/2, 0);
	    gl2.glVertex3d((double)width/2, (double)length/2, 0);
        gl2.glVertex3d(-(double)width/2, (double)length/2, 0);
        gl2.glEnd();
	}
	
	public void borders(GL2 gl2) {
		gl2.glColor3d(0,0,0);
		gl2.glLineWidth(2f);
	    gl2.glBegin(GL2.GL_LINES);
	    gl2.glVertex3d(-(double)width/2, -(double)length/2, 0);
	    gl2.glVertex3d((double)width/2, -(double)length/2, 0);
	    gl2.glVertex3d((double)width/2, (double)length/2, 0);
        gl2.glVertex3d(-(double)width/2, (double)length/2, 0);
        gl2.glEnd();
	}
	
	public int getLength() {
		return length;
	}
	
	public int getWidth() {
		return width;
	}
}
