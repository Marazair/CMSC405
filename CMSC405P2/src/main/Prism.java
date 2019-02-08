package main;

import com.jogamp.opengl.GL2;

public class Prism extends Shape {
	private int height;
	private Shape2D shape;
	
	public Prism(int height, Shape2D shape) {
		this.height = height;
		this.shape = shape;
	}
	
	@Override
	public void draw(GL2 gl2) {
		gl2.glPushMatrix();
		
		for(int i = 0; i < height; i++) {
			gl2.glPushMatrix();
			shape.draw(gl2);
			gl2.glPopMatrix();
			gl2.glTranslated(0, 1, 0);
		}
		
		gl2.glPopMatrix();
	}
}
