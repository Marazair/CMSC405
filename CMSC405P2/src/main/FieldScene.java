/*
 * File Name: FieldScene.java
 * Name: Nicholas Mills
 * Date: 2/6/2019
 * Purpose: A program built off of logic included in the JoglStarter,
 * 			Unlit Cube and CartAndWindmillJogl2D examples to create
 * 			an interactive scene with various different objects and
 * 			transforms.
 */

package main;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.*;
import javax.sound.sampled.*;


public class FieldScene extends JPanel implements 
                   GLEventListener, KeyListener, ActionListener {
	
	private static final int XDIMENSION = 640;
	private static final int YDIMENSION = 480;
	private static final double ASPECT = XDIMENSION/YDIMENSION;
	private static final int FOV = 45;
	private static final int MAX_VIEW = 400;
	private static final int MIN_VIEW = 1;
	private static int STEP = 1;
	
	private static final int BOX_X = 0;
	private static final int BOX_Y = 0;
	private static final int BOX_Z = 50;
	private static final int BOX_HEIGHT = 5;
	private static final int BOX_WIDTH = 6;
	private static final int BOX_LENGTH = 6;
	private static double[] BOX_RGB = {.65, .53, .33};
	private static Box box = 
			new Box(BOX_HEIGHT, new Rectangle(BOX_LENGTH, BOX_WIDTH, BOX_RGB));
	
	private static float[] SKY_RGB = {0.53f, .81f, .98f};
	private static final float[] SPOOKY_SKY_RGB = {0.53f, 0.11f, 0.89f};
	private static final float[] RED_SKY_RGB = {.65f, 0, 0};
	
	private static final int SUN_X = -50;
	private static final int SUN_Y = 40;
	private static final int SUN_Z = 300;
	private static final int SUN_SIZE = 20;
	private static final double[] SUN_RGB = {.99, .72, .07};
	private static final double[] SPOOKY_SUN_RGB = {.29, .01, .01};
	private static final double[] EYE_COLOR = {1, 1, 1};
	private static final double[] PUPIL_COLOR = {0, 0, 0};
	private static Circle sun = new Circle(SUN_SIZE, SUN_RGB);
	private static Circle eye = new Circle(SUN_SIZE - 5, EYE_COLOR);
	private static Circle pupil = new Circle(5, PUPIL_COLOR);
	
	private static final int TREE_X = 10;
	private static final int TREE_Y = 0;
	private static final int TREE_Z = BOX_Z + 5;
	private static final double[] TRUNK_RGB = {.40, .33, .31};
	private static final double[] LEAVES_RGB = {.13, .55, .13};
	private static final double[] SPOOKY_LEAVES_RGB = {.4, 0, 0};
	private static final int TRUNK_HEIGHT = 20;
	private static final int TRUNK_WIDTH = 5;
	private static final int TRUNK_LENGTH = 5;
	private static final int LEAVES_HEIGHT = 10;
	private static final int LEAVES_WIDTH = 10;
	private static final int LEAVES_LENGTH = 10;
	private static RectangularPrism trunk = 
			new RectangularPrism(TRUNK_HEIGHT, new Rectangle(TRUNK_LENGTH, TRUNK_WIDTH, TRUNK_RGB));
	private static RectangularPrism leaves =
			new RectangularPrism(LEAVES_HEIGHT, new Rectangle(LEAVES_LENGTH, LEAVES_WIDTH, LEAVES_RGB));
	
	private static double[] GRASS_RGB = {0, 1, 0};
	private static final double[] SPOOKY_GRASS_RGB = {.6, 0, 0};
	private static final int GRASS_SIZE = 500;
	private static Rectangle ground = new Rectangle(GRASS_SIZE, GRASS_SIZE, GRASS_RGB);
	
	private static final double[] SKULL_RGB = {.85, .83, .76};
	private static final int SKULL_SIZE = 4;
	private static final double SKULL_SPEED = .3;
	private static int skullDistance = 10;
	private static double skullX = BOX_X;
	private static double skullY = BOX_Y + 1;
	private static double skullZ = BOX_Z;
	private static Skull skull = 
			new Skull(SKULL_SIZE, new Rectangle(SKULL_SIZE, SKULL_SIZE, SKULL_RGB));
	private static final int TIME = 300;
	private boolean caught = false;
	
	private static int px = 0;
	private static int py = 10;
	private static int pz = 0;
	
	private static JFrame window;
	
	
    public static void main(String[] args) {
        window = new JFrame("A Field: Walk toward the box with Up Arrow");
        FieldScene panel = new FieldScene();
        window.setContentPane(panel);
        window.pack();
        window.setLocation(50,50);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }
    
    private GLJPanel display;
    private Timer animationTimer;

    private int frameNumber = 0;  // The current frame number for an animation.
    private int boxOpenFrame = 0;

    public FieldScene() {
        GLCapabilities caps = new GLCapabilities(null);
        display = new GLJPanel(caps);
        display.setPreferredSize( new Dimension(XDIMENSION, YDIMENSION) );
        display.addGLEventListener(this);
        setLayout(new BorderLayout());
        add(display,BorderLayout.CENTER);
        // TODO:  Other components could be added to the main panel.
        
        display.requestFocusInWindow();
        display.addKeyListener(this);
        
        //TODO:  Uncomment the following line to start the animation
        startAnimation();

    }

    // ---------------  Methods of the GLEventListener interface -----------
    
    /**
     * This method is called when the OpenGL display needs to be redrawn.
     */
    public void display(GLAutoDrawable drawable) {    
            // called when the panel needs to be drawn

        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();
        
        gl.glClearColor(SKY_RGB[0], SKY_RGB[1], SKY_RGB[2], 1);
        
        gl.glClear( GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(FOV, ASPECT, MIN_VIEW, MAX_VIEW);
        gl.glMatrixMode(GL2.GL_MODELVIEW);

        gl.glLoadIdentity();             // Set up modelview transform. 
        
        if(!caught) {
	        if(!box.open)
	        	glu.gluLookAt(px,py,pz, BOX_X,BOX_Y,BOX_Z, 0,1,0);
	        else
	        	glu.gluLookAt(px,py,pz, skullX,skullY,skullZ, 0,1,0);
	        
	        
	        gl.glPushMatrix();
	        gl.glTranslated(BOX_X, BOX_Y, BOX_Z);
	        box.draw(gl);
	        gl.glPopMatrix();
	        
	        if(box.open) {
	        	gl.glPushMatrix();
	        	gl.glTranslated(skullX, skullY, skullZ);
	        	skull.draw(gl);
	        	gl.glPopMatrix();
	        }
	        
	        gl.glPushMatrix();
	        gl.glTranslated(0, -BOX_HEIGHT/2 - 1, 0);
	        gl.glRotated(90, -1, 0, 0);
	        ground.draw(gl);
	        gl.glPopMatrix();
	        
	        gl.glPushMatrix();
	        gl.glTranslated(SUN_X, SUN_Y, SUN_Z);
	        gl.glRotated(-frameNumber*0.7,0,0,1);
	        sun.draw(gl);
	        rayDraw(gl);
	        if(box.open) {
	        	gl.glTranslated(0, 0, -2);
	        	eye.draw(gl);
	        	gl.glTranslated(0, 0, -2);
	        	pupil.draw(gl);
	        }
	        
	        gl.glPopMatrix();
	        
	        gl.glPushMatrix();
	        gl.glTranslated(TREE_X, TREE_Y, TREE_Z);
	        trunk.draw(gl);
	        gl.glTranslated(0, TRUNK_HEIGHT - .5 * LEAVES_HEIGHT, 0);
	        leaves.draw(gl);
	        gl.glPopMatrix();
	    }
        
        else {
        	SKY_RGB = RED_SKY_RGB;
        }
    }
    
    private void rayDraw(GL2 gl2) {
    	for (int i = 0; i < 13; i++) { // Draw 13 rays, with different rotations.
            gl2.glRotatef( 360f / 13, 0, 0, 1 ); // Note that the rotations accumulate!
            gl2.glBegin(GL2.GL_LINES);
            gl2.glVertex2f(0, 0);
            gl2.glVertex2f(1.5f * SUN_SIZE, 0);
            gl2.glEnd();
        }
    }

    /**
     * This is called when the GLJPanel is first created.  It can be used to initialize
     * the OpenGL drawing context.
     */
    public void init(GLAutoDrawable drawable) {
            // called when the panel is created
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.3F, 0.3F, 0.3F, 1.0F);

        gl.glEnable(GL2.GL_DEPTH_TEST);  // Required for 3D drawing, not usually for 2D.
        
    }

    /**
     * Called when the size of the GLJPanel changes.  Note:  glViewport(x,y,width,height)
     * has already been called before this method is called!
     */
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        // TODO: Add any code required to respond to the size of the display area.
        //             (Not usually needed.)
    }

    /**
     * This is called before the GLJPanel is destroyed.  It can be used to release OpenGL resources.
     */
    public void dispose(GLAutoDrawable drawable) {
    }
    
    
    // ------------ Support for keyboard handling  ------------

    /**
     * Called when the user presses any key on the keyboard, including
     * special keys like the arrow keys, the function keys, and the shift key.
     * Note that the value of key will be one of the constants from
     * the KeyEvent class that identify keys such as KeyEvent.VK_LEFT,
     * KeyEvent.VK_RIGHT, KeyEvent.VK_UP, and KeyEvent.VK_DOWN for the arrow
     * keys, KeyEvent.VK_SHIFT for the shift key, and KeyEvent.VK_F1 for a
     * function key.
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();  // Tells which key was pressed.
        
        if (key == KeyEvent.VK_UP) {
        	if (pz < BOX_Z - 10)
        		pz += STEP;
        	else {
        		if(!box.open) {
        			SKY_RGB = SPOOKY_SKY_RGB;
            		ground.setColor(SPOOKY_GRASS_RGB);
            		sun.setColor(SPOOKY_SUN_RGB);
            		leaves.setColor(SPOOKY_LEAVES_RGB);
            		window.setTitle("RUN!");
            		boxOpenFrame = frameNumber;
            		box.open = true;
        		}
        	}
        }
        else if (key == KeyEvent.VK_DOWN) {
        	if (pz > TREE_Z - 80)
        		pz -= STEP;
        }
        
        display.repaint();  // Causes the display() function to be called.
    }

    /**
     * Called when the user types a character.  This function is called in
     * addition to one or more calls to keyPressed and keyTyped. Note that ch is an
     * actual character such as 'A' or '@'.
     */
    public void keyTyped(KeyEvent e) { 
    }

    /**
     * Called when the user releases any key.
     */
    public void keyReleased(KeyEvent e) { 
    }
    
    
    // --------------------------- animation support ---------------------------
    
    /* You can call startAnimation() to run an animation.  A frame will be drawn every
     * 30 milliseconds (can be changed in the call to glutTimerFunc.  The global frameNumber
     * variable will be incremented for each frame.  Call pauseAnimation() to stop animating.
     */
    
    private boolean animating;  // True if animation is running.  Do not set directly.
                                // This is set by startAnimation() and pauseAnimation().
    
    private void updateFrame() {
        frameNumber++;
        // TODO:  add any other updating required for the next frame.
        
        if(box.open) {
        	if(frameNumber < boxOpenFrame + BOX_HEIGHT)
        		skullY += SKULL_SPEED;
        	else if (frameNumber < boxOpenFrame + TIME){
        		if (skullY < py)
        			skullY += SKULL_SPEED;
        		if (skullZ > pz + SKULL_SIZE + SKULL_SPEED + skullDistance)
        			skullZ -= SKULL_SPEED;
        		else if (skullZ < pz + SKULL_SIZE + skullDistance)
        			skullZ++;
        	}
        	else {
        		if (skullZ > pz + SKULL_SIZE)
        			skullZ -= SKULL_SPEED*5;
        		else {
        			if(!caught) {
        				caught = true;
        				window.setTitle("It got you...");
        			}
        			
        		}
        	}
        }
    }
    
    public void startAnimation() {
        if ( ! animating ) {
            if (animationTimer == null) {
                animationTimer = new Timer(30, this);
            }
            animationTimer.start();
            animating = true;
        }
    }
    
    public void pauseAnimation() {
        if (animating) {
            animationTimer.stop();
            animating = false;
        }
    }
    
    public void actionPerformed(ActionEvent evt) {
        updateFrame();
        display.repaint();
    }
}
