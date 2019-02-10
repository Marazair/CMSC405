package main;

import com.jogamp.opengl.GL2;

public class RectangularPrism extends Shape {
	private int height;
	private Rectangle shape;
	private Rectangle lengthSides;
	private Rectangle widthSides;
	
	public RectangularPrism(int height, Rectangle shape) {
		this.height = height;
		this.shape = shape;
		
		lengthSides = new Rectangle(shape.getLength(), height, shape.color);
		widthSides = new Rectangle(shape.getWidth(), height, shape.color);
	}
	
	@Override
	public void draw(GL2 gl2) {
		int length = shape.getLength();
		int width = shape.getWidth();
		
		gl2.glPushMatrix();
        
		gl2.glPushMatrix();
		gl2.glTranslated(0, 0, -length/2);
        widthSides.draw(gl2); //front face
        gl2.glPopMatrix();
        
        gl2.glPushMatrix();
        gl2.glRotated(90, 0, 1, 0);
        gl2.glTranslated(0, 0, -width/2);
        lengthSides.draw(gl2); // right face
        gl2.glPopMatrix();
        
        gl2.glPushMatrix();
        gl2.glRotated(90, 1, 0, 0);
        gl2.glTranslated(0, 0, -height/2);
        shape.draw(gl2); // top face
        gl2.glPopMatrix();
        
       	gl2.glPushMatrix();
		gl2.glTranslated(0, 0, length/2);
        widthSides.draw(gl2); //front face
        gl2.glPopMatrix();
        
        gl2.glPushMatrix();
        gl2.glRotated(90, 0, 1, 0);
        gl2.glTranslated(0, 0, width/2);
        lengthSides.draw(gl2); // right face
        gl2.glPopMatrix();
        
        gl2.glPushMatrix();
        gl2.glRotated(90, 1, 0, 0);
        gl2.glTranslated(0, 0, height/2);
        shape.draw(gl2); // bottom face
        gl2.glPopMatrix();
        
        
        gl2.glPopMatrix(); // Restore matrix to its state before cube() was called.
	}
	
	
}
