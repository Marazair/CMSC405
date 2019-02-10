package main;

import com.jogamp.opengl.GL2;

public class RectangularPrism extends Shape {
	private int height;
	private Rectangle shape;
	
	public RectangularPrism(int height, Rectangle shape) {
		this.height = height;
		this.shape = shape;
	}
	
	@Override
	public void draw(GL2 gl2) {
		
	}
}
