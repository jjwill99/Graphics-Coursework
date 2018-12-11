/* CS2150Coursework.java
 * Aston University
 * Name: Jacob Williams
 * Course: Computer Science BSc
 * Year: 2
 * Statement: This is a confirmation that everything in this piece of coursework is my own, apart from the images used for textures.
 *
 * Scene Graph:
 *  Scene origin
 *  |
 *  +-- [S(25, 1, 40) T(widthView, -1 + heigthView, -7.5 + moving)] First ground plane
 *  |
 *  +-- [S(25, 1, 40) T(widthView, -1 + heigthView, -47.5 + moving)] Second ground plane
 *  |
 *  +-- [S(25, 1, 40) Rz(180) T(widthView, 9 + heigthView, -7.5 + moving)] First ceiling plane
 *  |
 *  +-- [S(25, 1, 40) Rz(180) T(widthView, 9 + heigthView, -47.5 + moving)] Second ceiling plane
 *  |
 *  +-- [S(25, 1, 10) Rx(90) T(widthView, 4 + heigthView, -67 + moving)] Back plane
 *  |
 *  +-- [S(25, 1, 10) Rz(270) Rx(90) T(-12.5 + widthView, 4 + heigthView, -7 + moving)] First left wall plane
 *  |
 *  +-- [S(25, 1, 10) Rz(270) Rx(90) T(-12.5 + widthView, 4 + heigthView, -32 + moving)] Second left wall plane
 *  |
 *  +-- [S(25, 1, 10) Rz(270) Rx(90) T(-12.5 + widthView, 4 + heigthView, -57 + moving)] Third left wall plane
 *  |
 *  +-- [S(25, 1, 10) Rz(90) Rx(90) T(12.5 + widthView, 4 + heigthView, -7 + moving)] First right wall plane
 *  |
 *  +-- [S(25, 1, 10) Rz(270) Rx(90) T(-12.5 + widthView, 4 + heigthView, -32 + moving)] Second right wall plane
 *  |
 *  +-- [S(25, 1, 10) Rz(270) Rx(90) T(-12.5 + widthView, 4 + heigthView, -57 + moving)] Third right wall plane
 *  |
 *  +-- [S(4, 1, 4) T(widthView, 8.9 + heightView, -5 + moving)] First ceiling light
 *  |
 *  +-- [S(4, 1, 4) T(widthView, 8.9 + heightView, -20 + moving)] Second ceiling light
 *  |
 *  +-- [S(4, 1, 4) T(widthView, 8.9 + heightView, -35 + moving)] Third ceiling light
 *  |
 *  +-- [S(4, 1, 4) T(widthView, 8.9 + heightView, -50 + moving)] Fourth ceiling light
 *  |
 *  +-- [T(widthView, 3 + heightView, -67 + moving)] Dart board
 *  |
 *  +-- [Rz(ringAngle) T(widthView, 2 + heightView, -37 + moving)] First ring
 *  |   |
 *  |   +-- [S(0.9, 0.9, 0.9) T(0, 2, 0)] Second ring
 *  |   |
 *  |   +-- [S(0.9, 0.9, 0.9) T(0, -2, 0)] Third ring
 *  |   |
 *  |   +-- [S(0.9, 0.9, 0.9) T(2, 0, 0)] Fourth ring
 *  |   |
 *  |   +-- [S(0.9, 0.9, 0.9) T(-2, 0, 0)] Fifth ring
 *  |
 *  +-- [Ry(270) T(10 + widthView, 0.5 + heightView, -25 + moving)] Bar
 *  |
 *  +-- [Ry(270) T(11.5 + widthView, 5 + heightView, -25 + moving)] Shelf
 *  |
 *  +-- [T(-10 + widthView, 1.2 + heightView, -25 + moving)] Table
 *  |	|
 *  |	+-- [T(-1.9, -1.2, 3.9)] First leg
 *  |	|
 *  |	+-- [T(1.9, -1.2, 3.9)] Second leg
 *  |	|
 *  |	+-- [T(-1.9, -1.2, -3.9)] Third leg
 *  |	|
 *  |	+-- [T(1.9, -1.2, -3.9)] Fourth leg
 *  |
 *  +-- [Rz(spinRotationAngle) Ry(dartAngleX) Rx(dartAngleY) T(dartMovementX, dartMovementY, 0)] Dart grip
 *  	|
 *  	+-- [T(0, 0, -2)] Dart spike
 *  	|
 *  	+-- [T(0, 0.1, 2.75)] First fin
 *  	|
 *   	+-- [Rz(180) T(0, -0.1, 2.75)] Second fin
 *   	|
 *   	+-- [Rz(90) T(-0.1, 0, 2.75)] Third fin
 *   	|
 *   	+-- [Rz(270) T(0.1, 0, 2.75)] Fourth fin
 *   	|
 *   	+-- [T(0, 0, 3)] Back of dart
 *
 */
package coursework.willij13;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.Disk;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;
import GraphicsLab.*;

/**
 * Simulation shows a dart that is thrown through the air, and hit a dart board.
 * With the extra challenge of flying through moving rings.
 *
 * <p>
 * Controls:
 * <ul>
 * <li>Press the escape key to exit the application.
 * <li>Hold the x, y and z keys to view the scene along the x, y and z axis,
 * respectively
 * <li>While viewing the scene along the x, y or z axis, use the up and down
 * cursor keys to increase or decrease the viewpoint's distance from the scene
 * origin
 * <li>Press the space button to initiate the throw of the dart.
 * <li>Press/hold w, a, s and d keys to move the dart around the scene while in
 * flight.
 * <li>Press r to reset the animation.
 * </ul>
 *
 */
public class CS2150Coursework extends GraphicsLab {
	/**
	 * display list id of the grip
	 * 
	 * @see #initScene()
	 * @see #renderScene()
	 */
	private final int gripList = 1;
	/**
	 * display list id of a fin
	 * 
	 * @see #initScene()
	 * @see #renderScene()
	 */
	private final int finList = 2;
	/**
	 * display list id of the dart spike
	 * 
	 * @see #initScene()
	 * @see #renderScene()
	 */
	private final int spikeList = 3;
	/**
	 * display list id of the dart back end
	 * 
	 * @see #initScene()
	 * @see #renderScene()
	 */
	private final int dartBackEndList = 4;
	/**
	 * display list id of plane
	 * 
	 * @see #initScene()
	 * @see #renderScene()
	 */
	private final int planeList = 5;
	/**
	 * display list id of the dart board
	 * 
	 * @see #initScene()
	 * @see #renderScene()
	 */
	private final int boardList = 6;
	/**
	 * display list id of the ring
	 * 
	 * @see #initScene()
	 * @see #renderScene()
	 */
	private final int ringList = 7;
	/**
	 * display list id of the table top
	 * 
	 * @see #initScene()
	 * @see #renderScene()
	 */
	private final int tableTopList = 8;
	/**
	 * display list id of the table leg
	 * 
	 * @see #initScene()
	 * @see #renderScene()
	 */
	private final int tableLegList = 9;
	/**
	 * display list id of the ceiling light
	 * 
	 * @see #initScene()
	 * @see #renderScene()
	 */
	private final int lightList = 10;
	/**
	 * display list id of the bar
	 * 
	 * @see #initScene()
	 * @see #renderScene()
	 */
	private final int barList = 11;
	/**
	 * display list id of the shelf
	 * 
	 * @see #initScene()
	 * @see #renderScene()
	 */
	private final int shelfList = 12;
	/**
	 * value of the angle for the rotating rings
	 * 
	 * @see #renderScene()
	 * @see #updateScene()
	 */
	private float ringAngle = 0.0f;
	/**
	 * value of the angle for the dart spinning
	 * 
	 * @see #checkSceneInput()
	 * @see #renderScene()
	 * @see #updateScene()
	 */
	private float spinRotationAngle = 0.0f;
	/**
	 * value of the angle for the dart angling upwards/downwards
	 * 
	 * @see #checkSceneInput()
	 * @see #renderScene()
	 * @see #resetAnimations()
	 */
	private float dartAngleY = 0.0f;
	/**
	 * value of the dart movement along the y axis
	 * 
	 * @see #checkSceneInput()
	 * @see #dartYLimit()
	 * @see #renderScene()
	 * @see #resetAnimations()
	 */
	private float dartMovementY = 0.0f;
	/**
	 * value of the angle for the dart angling left/right
	 * 
	 * @see #checkSceneInput()
	 * @see #renderScene()
	 * @see #resetAnimations()
	 */
	private float dartAngleX = 0.0f;
	/**
	 * value of the dart movement along the x axis
	 * 
	 * @see #checkSceneInput()
	 * @see #dartXLimit()
	 * @see #renderScene()
	 * @see #resetAnimations()
	 */
	private float dartMovementX = 0.0f;
	/**
	 * value of the scene movement along the z axis
	 * 
	 * @see #checkSceneInput()
	 * @see #isDartOnBoard()
	 * @see #renderScene()
	 * @see #resetAnimations()
	 */
	private float moving = 0.0f;
	/**
	 * value of the scene movement along the y axis
	 * 
	 * @see #checkSceneInput()
	 * @see #initScene()
	 * @see #renderScene()
	 * @see #resetAnimations()
	 */
	private float heightView = 0.0f;
	/**
	 * value of the scene movement along the x axis
	 * 
	 * @see #checkSceneInput()
	 * @see #renderScene()
	 * @see #resetAnimations()
	 */
	private float widthView = 0.0f;
	/**
	 * holds true if the dart is in motion / has been thrown, false otherwise
	 * 
	 * @see #checkDartHit()
	 * @see #checkSceneInput()
	 * @see #resetAnimations()
	 */
	private boolean thrown = false;

	// textures
	private Texture groundTextures;
	private Texture wallTextures;
	private Texture targetTextures;
	private Texture tableTextures;
	private Texture ceilingTextures;
	private Texture ceilingLightTextures;
	private Texture barSideTextures;
	private Texture bottlesTextures;

	// TODO: Feel free to change the window title and default animation scale
	// here
	public static void main(String args[]) {
		new CS2150Coursework().run(WINDOWED, "Dartboard Game", 0.01f);
	}

	protected void initScene() throws Exception {
		// load the textures
		groundTextures = loadTexture("coursework/willij13/textures/wood_floor.jpg");
		wallTextures = loadTexture("coursework/willij13/textures/brick_wall.jpg");
		targetTextures = loadTexture("coursework/willij13/textures/target.png");
		tableTextures = loadTexture("coursework/willij13/textures/table_top.jpg");
		ceilingTextures = loadTexture("coursework/willij13/textures/ceiling.jpg");
		ceilingLightTextures = loadTexture("coursework/willij13/textures/light_panel.png");
		barSideTextures = loadTexture("coursework/willij13/textures/bar_side.jpg");
		bottlesTextures = loadTexture("coursework/willij13/textures/bottles.jpg");

		// global ambient light level
		float globalAmbient[] = { 0.2f, 0.2f, 0.2f, 1.0f };
		// set the global ambient lighting
		GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(globalAmbient));

		// the first light for the scene...
		float diffuse0[] = { 0.8f, 0.8f, 0.8f, 1.0f }; // Normal
		// ...with a dim ambient contribution...
		float ambient0[] = { 0.2f, 0.2f, 0.2f, 1.0f };
		// ...and is positioned above the viewpoint
		float position0[] = { 0.0f, 3f, -20.0f, 1.0f };

		// supply OpenGL with the properties for the first light
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, FloatBuffer.wrap(ambient0));
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, FloatBuffer.wrap(diffuse0));
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, FloatBuffer.wrap(diffuse0));
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, FloatBuffer.wrap(position0));

		// the second light for the scene...
		float diffuse1[] = { 1.0f, 0.0f, 1.0f, 1.0f }; // Purple
		// ...with a dim ambient contribution...
		float ambient1[] = { 0.2f, 0.2f, 0.2f, 1.0f };
		// ...and is positioned above the viewpoint
		float position1[] = { 0.0f, 3f, -20.0f, 1.0f };

		// supply OpenGL with the properties for the first light
		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_AMBIENT, FloatBuffer.wrap(ambient1));
		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, FloatBuffer.wrap(diffuse1));
		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_SPECULAR, FloatBuffer.wrap(diffuse1));
		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION, FloatBuffer.wrap(position1));

		// the third light for the scene...
		float diffuse2[] = { 0.2f, 1.0f, 0.2f, 1.0f }; // Green
		// ...with a dim ambient contribution...
		float ambient2[] = { 0.2f, 0.2f, 0.2f, 1.0f };
		// ...and is positioned above the viewpoint
		float position2[] = { 0.0f, 3f, -20.0f, 1.0f };

		// supply OpenGL with the properties for the first light
		GL11.glLight(GL11.GL_LIGHT2, GL11.GL_AMBIENT, FloatBuffer.wrap(ambient2));
		GL11.glLight(GL11.GL_LIGHT2, GL11.GL_DIFFUSE, FloatBuffer.wrap(diffuse2));
		GL11.glLight(GL11.GL_LIGHT2, GL11.GL_SPECULAR, FloatBuffer.wrap(diffuse2));
		GL11.glLight(GL11.GL_LIGHT2, GL11.GL_POSITION, FloatBuffer.wrap(position2));

		// the fourth light for the scene...
		float diffuse3[] = { 1.0f, 0.2f, 0.2f, 1.0f }; // Red
		// ...with a dim ambient contribution...
		float ambient3[] = { 0.2f, 0.2f, 0.2f, 1.0f };
		// ...and is positioned above the viewpoint
		float position3[] = { 0.0f, 3f, -20.0f, 1.0f };

		// supply OpenGL with the properties for the first light
		GL11.glLight(GL11.GL_LIGHT3, GL11.GL_AMBIENT, FloatBuffer.wrap(ambient3));
		GL11.glLight(GL11.GL_LIGHT3, GL11.GL_DIFFUSE, FloatBuffer.wrap(diffuse3));
		GL11.glLight(GL11.GL_LIGHT3, GL11.GL_SPECULAR, FloatBuffer.wrap(diffuse3));
		GL11.glLight(GL11.GL_LIGHT3, GL11.GL_POSITION, FloatBuffer.wrap(position3));

		// enable the first light
		GL11.glEnable(GL11.GL_LIGHT0);

		// enable lighting calculations
		GL11.glEnable(GL11.GL_LIGHTING);
		// ensure that all normals are re-normalised after transformations
		// automatically
		GL11.glEnable(GL11.GL_NORMALIZE);

		// prepare the display lists for later use
		GL11.glNewList(gripList, GL11.GL_COMPILE);
		{
			drawUnitGrip();
		}
		GL11.glEndList();
		GL11.glNewList(finList, GL11.GL_COMPILE);
		{
			drawUnitFin();
		}
		GL11.glEndList();
		GL11.glNewList(spikeList, GL11.GL_COMPILE);
		{
			drawUnitSpike();
		}
		GL11.glEndList();
		GL11.glNewList(dartBackEndList, GL11.GL_COMPILE);
		{
			drawUnitDartBackEnd();
		}
		GL11.glEndList();
		GL11.glNewList(planeList, GL11.GL_COMPILE);
		{
			drawUnitPlane();
		}
		GL11.glEndList();
		GL11.glNewList(boardList, GL11.GL_COMPILE);
		{
			drawUnitDartBoard();
		}
		GL11.glEndList();
		GL11.glEndList();
		GL11.glNewList(ringList, GL11.GL_COMPILE);
		{
			drawUnitRing();
		}
		GL11.glEndList();
		GL11.glNewList(tableTopList, GL11.GL_COMPILE);
		{
			drawUnitTableTop();
		}
		GL11.glEndList();
		GL11.glNewList(tableLegList, GL11.GL_COMPILE);
		{
			drawUnitTableLeg();
		}
		GL11.glEndList();
		GL11.glNewList(lightList, GL11.GL_COMPILE);
		{
			drawUnitLight();
		}
		GL11.glEndList();
		GL11.glNewList(barList, GL11.GL_COMPILE);
		{
			drawUnitBar();
		}
		GL11.glEndList();
		GL11.glNewList(shelfList, GL11.GL_COMPILE);
		{
			drawUnitShelf();
		}
		GL11.glEndList();
	}

	protected void checkSceneInput() {
		if (thrown == true) {
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				dartAngleY = 10.0f;
				dartMovementY += 0.01f;
				if (dartYLimit()) {
					dartMovementY = 1.8f;
					heightView -= 0.01f;
					if (heightView < -5.5) {
						dartAngleY = 0.0f;
						heightView = -5.5f;
					}
				}
			} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				dartAngleY = -10.0f;
				dartMovementY -= 0.01f;
				if (dartYLimit()) {
					dartMovementY = -0.6f;
					heightView += 0.01f;
					if (heightView > 0.0f) {
						dartAngleY = 0.0f;
						heightView = 0.0f;
					}
				}
			} else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				dartAngleX = 5.0f;
				dartMovementX -= 0.01f;
				if (dartXLimit()) {
					dartMovementX = -1.8f;
					widthView += 0.01f;
					if (widthView > 5.5) {
						dartAngleX = 0.0f;
						widthView = 5.5f;
					}
				}
			} else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				dartAngleX = -5.0f;
				dartMovementX += 0.01f;
				if (dartXLimit()) {
					dartMovementX = 1.8f;
					widthView -= 0.01f;
					if (widthView < -5.5) {
						dartAngleX = 0.0f;
						widthView = -5.5f;
					}
				}
			} else {
				dartAngleY = 0.0f;
				dartAngleX = 0.0f;
			}

			moving += 0.01f;
			checkDartHit();
		} else {
			spinRotationAngle = 45f;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			// throw dart
			if (!isDartOnBoard()) {
				thrown = true;
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
			// reset scene
			resetAnimations();
		}
	}

	protected void updateScene() {
		// TODO: Update your scene variables here - remember to use the current
		// animation scale value
		// (obtained via a call to getAnimationScale()) in your modifications so
		// that
		// your animations
		// can be made faster or slower depending on the machine you are working
		// on

		spinRotationAngle += +40.0f * getAnimationScale();

		ringAngle += 10.0f * getAnimationScale();

		if (moving < 13) {
			GL11.glDisable(GL11.GL_LIGHT1);
			GL11.glDisable(GL11.GL_LIGHT2);
			GL11.glDisable(GL11.GL_LIGHT3);
			GL11.glDisable(GL11.GL_LIGHT4);
			GL11.glEnable(GL11.GL_LIGHT0);
		} else if (moving < 26) {
			GL11.glDisable(GL11.GL_LIGHT0);
			GL11.glEnable(GL11.GL_LIGHT1);
		} else if (moving < 39) {
			GL11.glDisable(GL11.GL_LIGHT1);
			GL11.glEnable(GL11.GL_LIGHT2);
		} else if (moving < 52) {
			GL11.glDisable(GL11.GL_LIGHT2);
			GL11.glEnable(GL11.GL_LIGHT3);
		} else if (moving < 65) {
			GL11.glDisable(GL11.GL_LIGHT3);
			GL11.glEnable(GL11.GL_LIGHT0);
		}
	}

	protected void renderScene() {
		// draw ground plane #1
		GL11.glPushMatrix();
		{
			// change the geometry colour to white so that the texture
			// is bright and details can be seen clearly
			Colour.WHITE.submit();
			// enable texturing and bind an appropriate texture
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, groundTextures.getTextureID());

			// position, scale and draw the ground plane using its display list
			GL11.glTranslatef(widthView, -1.0f + heightView, -7.5f + moving);
			GL11.glScalef(25.0f, 1.0f, 40.0f);

			// how shiny is the floor (specular exponent)
			float gripFrontShininess = 100.0f;
			// specular reflection of the floor
			float gripFrontSpecular[] = { 0.1f, 0.1f, 0.8f, 1.0f };
			// diffuse reflection of the floor
			float gripFrontDiffuse[] = { 1f, 1f, 1f, 1.0f };

			// set the material properties for the dart grip using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, gripFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(gripFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(gripFrontDiffuse));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, FloatBuffer.wrap(gripFrontDiffuse));

			GL11.glCallList(planeList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// add ground plane #2
		GL11.glPushMatrix();
		{
			// change the geometry colour to white so that the texture
			// is bright and details can be seen clearly
			Colour.WHITE.submit();
			// enable texturing and bind an appropriate texture
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, groundTextures.getTextureID());

			// position, scale and draw the ground plane using its display list
			GL11.glTranslatef(widthView, -1.0f + heightView, -47.5f + moving);
			GL11.glScalef(25.0f, 1.0f, 40.0f);

			// how shiny is the floor (specular exponent)
			float gripFrontShininess = 100.0f;
			// specular reflection of the floor
			float gripFrontSpecular[] = { 0.1f, 0.1f, 0.8f, 1.0f };
			// diffuse reflection of the floor
			float gripFrontDiffuse[] = { 1f, 1f, 1f, 1.0f };

			// set the material properties for the dart grip using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, gripFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(gripFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(gripFrontDiffuse));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, FloatBuffer.wrap(gripFrontDiffuse));

			GL11.glCallList(planeList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw ceiling plane #1
		GL11.glPushMatrix();
		{
			// change the geometry colour to white so that the texture
			// is bright and details can be seen clearly
			Colour.WHITE.submit();
			// enable texturing and bind an appropriate texture
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, ceilingTextures.getTextureID());

			// position, scale and draw the ground plane using its display list
			GL11.glTranslatef(widthView, 9.0f + heightView, -7.5f + moving);
			GL11.glRotatef(180, 0, 0, 1);
			GL11.glScalef(25.0f, 1.0f, 40.0f);

			// how shiny is the floor (specular exponent)
			float gripFrontShininess = 100.0f;
			// specular reflection of the floor
			float gripFrontSpecular[] = { 0.1f, 0.1f, 0.8f, 1.0f };
			// diffuse reflection of the floor
			float gripFrontDiffuse[] = { 1f, 1f, 1f, 1.0f };

			// set the material properties for the dart grip using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, gripFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(gripFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(gripFrontDiffuse));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, FloatBuffer.wrap(gripFrontDiffuse));

			GL11.glCallList(planeList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// add ceiling plane #2
		GL11.glPushMatrix();
		{
			// change the geometry colour to white so that the texture
			// is bright and details can be seen clearly
			Colour.WHITE.submit();
			// enable texturing and bind an appropriate texture
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, ceilingTextures.getTextureID());

			// position, scale and draw the ground plane using its display list
			GL11.glTranslatef(widthView, 9.0f + heightView, -47.5f + moving);
			GL11.glRotatef(180, 0, 0, 1);
			GL11.glScalef(25.0f, 1.0f, 40.0f);

			// how shiny is the floor (specular exponent)
			float gripFrontShininess = 100.0f;
			// specular reflection of the floor
			float gripFrontSpecular[] = { 0.1f, 0.1f, 0.8f, 1.0f };
			// diffuse reflection of the floor
			float gripFrontDiffuse[] = { 1f, 1f, 1f, 1.0f };

			// set the material properties for the dart grip using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, gripFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(gripFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(gripFrontDiffuse));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, FloatBuffer.wrap(gripFrontDiffuse));

			GL11.glCallList(planeList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw the back wall plane
		GL11.glPushMatrix();
		{
			// change the geometry colour to white so that the texture
			// is bright and details can be seen clearly
			Colour.WHITE.submit();
			// enable texturing and bind an appropriate texture
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, wallTextures.getTextureID());

			// position, scale and draw the back plane using its display list
			GL11.glTranslatef(widthView, 4.0f + heightView, -67f + moving);
			GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
			GL11.glScalef(25.0f, 1.0f, 10.0f);

			// how shiny is the floor (specular exponent)
			float gripFrontShininess = 100.0f;
			// specular reflection of the floor
			float gripFrontSpecular[] = { 0.1f, 0.1f, 0.8f, 1.0f };
			// diffuse reflection of the floor
			float gripFrontDiffuse[] = { 1f, 1f, 1f, 1.0f };

			// set the material properties for the dart grip using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, gripFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(gripFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(gripFrontDiffuse));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, FloatBuffer.wrap(gripFrontDiffuse));

			GL11.glCallList(planeList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw the left wall plane #1
		GL11.glPushMatrix();
		{
			// change the geometry colour to white so that the texture
			// is bright and details can be seen clearly
			Colour.WHITE.submit();
			// enable texturing and bind an appropriate texture
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, wallTextures.getTextureID());

			// position, scale and draw the back plane using its display list
			GL11.glTranslatef(-12.5f + widthView, 4.0f + heightView, -7.0f + moving);
			GL11.glRotatef(90.0f, 1.0f, 0, 0);
			GL11.glRotatef(270.0f, 0.0f, 0.0f, 1.0f);
			GL11.glScalef(25.0f, 1.0f, 10.0f);

			// how shiny is the floor (specular exponent)
			float gripFrontShininess = 100.0f;
			// specular reflection of the floor
			float gripFrontSpecular[] = { 0.1f, 0.1f, 0.8f, 1.0f };
			// diffuse reflection of the floor
			float gripFrontDiffuse[] = { 1f, 1f, 1f, 1.0f };

			// set the material properties for the dart grip using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, gripFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(gripFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(gripFrontDiffuse));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, FloatBuffer.wrap(gripFrontDiffuse));

			GL11.glCallList(planeList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw the left wall plane #2
		GL11.glPushMatrix();
		{
			// change the geometry colour to white so that the texture
			// is bright and details can be seen clearly
			Colour.WHITE.submit();
			// enable texturing and bind an appropriate texture
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, wallTextures.getTextureID());

			// position, scale and draw the back plane using its display list
			GL11.glTranslatef(-12.5f + widthView, 4.0f + heightView, -32f + moving);
			GL11.glRotatef(90.0f, 1.0f, 0, 0);
			GL11.glRotatef(270.0f, 0.0f, 0.0f, 1.0f);
			GL11.glScalef(25.0f, 1.0f, 10.0f);

			// how shiny is the floor (specular exponent)
			float gripFrontShininess = 100.0f;
			// specular reflection of the floor
			float gripFrontSpecular[] = { 0.1f, 0.1f, 0.8f, 1.0f };
			// diffuse reflection of the floor
			float gripFrontDiffuse[] = { 1f, 1f, 1f, 1.0f };

			// set the material properties for the dart grip using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, gripFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(gripFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(gripFrontDiffuse));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, FloatBuffer.wrap(gripFrontDiffuse));

			GL11.glCallList(planeList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw the left wall plane #3
		GL11.glPushMatrix();
		{
			// change the geometry colour to white so that the texture
			// is bright and details can be seen clearly
			Colour.WHITE.submit();
			// enable texturing and bind an appropriate texture
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, wallTextures.getTextureID());

			// position, scale and draw the back plane using its display list
			GL11.glTranslatef(-12.5f + widthView, 4.0f + heightView, -57f + moving);
			GL11.glRotatef(90.0f, 1.0f, 0, 0);
			GL11.glRotatef(270.0f, 0.0f, 0.0f, 1.0f);
			GL11.glScalef(25.0f, 1.0f, 10.0f);

			// how shiny is the floor (specular exponent)
			float gripFrontShininess = 100.0f;
			// specular reflection of the floor
			float gripFrontSpecular[] = { 0.1f, 0.1f, 0.8f, 1.0f };
			// diffuse reflection of the floor
			float gripFrontDiffuse[] = { 1f, 1f, 1f, 1.0f };

			// set the material properties for the dart grip using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, gripFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(gripFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(gripFrontDiffuse));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, FloatBuffer.wrap(gripFrontDiffuse));

			GL11.glCallList(planeList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw the right wall plane #1
		GL11.glPushMatrix();
		{
			// change the geometry colour to white so that the texture
			// is bright and details can be seen clearly
			Colour.WHITE.submit();
			// enable texturing and bind an appropriate texture
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, wallTextures.getTextureID());

			// position, scale and draw the back plane using its display list
			GL11.glTranslatef(12.5f + widthView, 4.0f + heightView, -7.0f + moving);
			GL11.glRotatef(90.0f, 1.0f, 0, 0);
			GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
			GL11.glScalef(25.0f, 1.0f, 10.0f);

			// how shiny is the floor (specular exponent)
			float gripFrontShininess = 100.0f;
			// specular reflection of the floor
			float gripFrontSpecular[] = { 0.1f, 0.1f, 0.8f, 1.0f };
			// diffuse reflection of the floor
			float gripFrontDiffuse[] = { 1f, 1f, 1f, 1.0f };

			// set the material properties for the dart grip using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, gripFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(gripFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(gripFrontDiffuse));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, FloatBuffer.wrap(gripFrontDiffuse));

			GL11.glCallList(planeList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw the right wall plane #2
		GL11.glPushMatrix();
		{
			// change the geometry colour to white so that the texture
			// is bright and details can be seen clearly
			Colour.WHITE.submit();
			// enable texturing and bind an appropriate texture
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, wallTextures.getTextureID());

			// position, scale and draw the back plane using its display list
			GL11.glTranslatef(12.5f + widthView, 4.0f + heightView, -32f + moving);
			GL11.glRotatef(90.0f, 1.0f, 0, 0);
			GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
			GL11.glScalef(25.0f, 1.0f, 10.0f);

			// how shiny is the floor (specular exponent)
			float gripFrontShininess = 100.0f;
			// specular reflection of the floor
			float gripFrontSpecular[] = { 0.1f, 0.1f, 0.8f, 1.0f };
			// diffuse reflection of the floor
			float gripFrontDiffuse[] = { 1f, 1f, 1f, 1.0f };

			// set the material properties for the dart grip using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, gripFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(gripFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(gripFrontDiffuse));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, FloatBuffer.wrap(gripFrontDiffuse));

			GL11.glCallList(planeList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw the right wall plane #3
		GL11.glPushMatrix();
		{
			// change the geometry colour to white so that the texture
			// is bright and details can be seen clearly
			Colour.WHITE.submit();
			// enable texturing and bind an appropriate texture
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, wallTextures.getTextureID());

			// position, scale and draw the back plane using its display list
			GL11.glTranslatef(12.5f + widthView, 4.0f + heightView, -57f + moving);
			GL11.glRotatef(90.0f, 1.0f, 0, 0);
			GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
			GL11.glScalef(25.0f, 1.0f, 10.0f);

			// how shiny is the floor (specular exponent)
			float gripFrontShininess = 100.0f;
			// specular reflection of the floor
			float gripFrontSpecular[] = { 0.1f, 0.1f, 0.8f, 1.0f };
			// diffuse reflection of the floor
			float gripFrontDiffuse[] = { 1f, 1f, 1f, 1.0f };

			// set the material properties for the dart grip using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, gripFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(gripFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(gripFrontDiffuse));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, FloatBuffer.wrap(gripFrontDiffuse));

			GL11.glCallList(planeList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// Render ceiling light #1
		GL11.glPushMatrix();
		{
			// disable lighting calculations so that they don't affect
			// the appearance of the texture
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			GL11.glDisable(GL11.GL_LIGHTING);
			// change the geometry colour to white so that the texture
			// is bright and details can be seen clearly
			Colour.WHITE.submit();
			// enable texturing and bind an appropriate texture
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, ceilingLightTextures.getTextureID());

			GL11.glTranslatef(widthView, 8.9f + heightView, -5 + moving);
			GL11.glScalef(4, 1, 4);

			GL11.glCallList(lightList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// Render ceiling light #2
		GL11.glPushMatrix();
		{
			// disable lighting calculations so that they don't affect
			// the appearance of the texture
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			GL11.glDisable(GL11.GL_LIGHTING);
			// change the geometry colour to white so that the texture
			// is bright and details can be seen clearly
			Colour.WHITE.submit();
			// enable texturing and bind an appropriate texture
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, ceilingLightTextures.getTextureID());

			GL11.glTranslatef(widthView, 8.9f + heightView, -20 + moving);
			GL11.glScalef(4, 1, 4);

			GL11.glCallList(lightList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// Render ceiling light #3
		GL11.glPushMatrix();
		{
			// disable lighting calculations so that they don't affect
			// the appearance of the texture
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			GL11.glDisable(GL11.GL_LIGHTING);
			// change the geometry colour to white so that the texture
			// is bright and details can be seen clearly
			Colour.WHITE.submit();
			// enable texturing and bind an appropriate texture
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, ceilingLightTextures.getTextureID());

			GL11.glTranslatef(widthView, 8.9f + heightView, -35 + moving);
			GL11.glScalef(4, 1, 4);

			GL11.glCallList(lightList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// Render ceiling light #4
		GL11.glPushMatrix();
		{
			// disable lighting calculations so that they don't affect
			// the appearance of the texture
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			GL11.glDisable(GL11.GL_LIGHTING);
			// change the geometry colour to white so that the texture
			// is bright and details can be seen clearly
			Colour.WHITE.submit();
			// enable texturing and bind an appropriate texture
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, ceilingLightTextures.getTextureID());

			GL11.glTranslatef(widthView, 8.9f + heightView, -50 + moving);
			GL11.glScalef(4, 1, 4);

			GL11.glCallList(lightList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// Renders the dart board
		GL11.glPushMatrix();
		{
			// disable lighting calculations so that they don't affect
			// the appearance of the texture
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			GL11.glDisable(GL11.GL_LIGHTING);
			// change the geometry colour to white so that the texture
			// is bright and details can be seen clearly
			Colour.WHITE.submit();
			// enable texturing and bind an appropriate texture
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, targetTextures.getTextureID());

			GL11.glTranslatef(widthView, 3 + heightView, -67 + moving);

			// how shiny is the dart grip (specular exponent)
			float gripFrontShininess = 40.0f;
			// specular reflection of the dart grip
			float gripFrontSpecular[] = { 0.1f, 0.0f, 0.0f, 1.0f };
			// diffuse reflection of the dart grip
			float gripFrontDiffuse[] = { 0.6f, 0.6f, 0.6f, 1.0f };

			// set the material properties for the dart grip using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, gripFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(gripFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(gripFrontDiffuse));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, FloatBuffer.wrap(gripFrontDiffuse));

			GL11.glCallList(boardList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// Renders ring #1
		GL11.glPushMatrix();
		{
			GL11.glTranslatef(widthView, 2 + heightView, -37 + moving);
			GL11.glRotatef(ringAngle, 0, 0, 1);
			// how shiny is the dart grip (specular exponent)
			float ringFrontShininess = 40.0f;
			// specular reflection of the dart grip
			float ringFrontSpecular[] = { 0.1f, 0.0f, 0.0f, 1.0f };
			// diffuse reflection of the dart grip
			float ringFrontDiffuse[] = { 0.6f, 0.16f, 0.16f, 1.0f };

			// set the material properties for the dart grip using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, ringFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(ringFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(ringFrontDiffuse));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, FloatBuffer.wrap(ringFrontDiffuse));

			GL11.glCallList(ringList);

			// Renders ring #2
			GL11.glPushMatrix();
			{
				GL11.glTranslatef(0, 2, 0);
				GL11.glScalef(0.9f, 0.9f, 0.9f);

				GL11.glCallList(ringList);
			}
			GL11.glPopMatrix();

			// Renders ring #3
			GL11.glPushMatrix();
			{
				GL11.glTranslatef(0, -2, 0);
				GL11.glScalef(0.9f, 0.9f, 0.9f);

				GL11.glCallList(ringList);
			}
			GL11.glPopMatrix();

			// Renders ring #4
			GL11.glPushMatrix();
			{
				GL11.glTranslatef(2, 0, 0);
				GL11.glScalef(0.9f, 0.9f, 0.9f);

				GL11.glCallList(ringList);
			}
			GL11.glPopMatrix();

			// Renders ring #5
			GL11.glPushMatrix();
			{
				GL11.glTranslatef(-2, 0, 0);
				GL11.glScalef(0.9f, 0.9f, 0.9f);

				GL11.glCallList(ringList);
			}
			GL11.glPopMatrix();
		}
		GL11.glPopMatrix();

		// Renders the bar
		GL11.glPushMatrix();
		{
			// change the geometry colour to white so that the texture
			// is bright and details can be seen clearly
			Colour.WHITE.submit();
			// enable texturing and bind an appropriate texture
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, barSideTextures.getTextureID());

			GL11.glTranslatef(10 + widthView, 0.5f + heightView, -25 + moving);
			GL11.glRotatef(270, 0, 1, 0);

			// how shiny is the floor (specular exponent)
			float gripFrontShininess = 100.0f;
			// specular reflection of the floor
			float gripFrontSpecular[] = { 0.1f, 0.1f, 0.8f, 1.0f };
			// diffuse reflection of the floor
			float gripFrontDiffuse[] = { 1f, 1f, 1f, 1.0f };

			// set the material properties for the dart grip using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, gripFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(gripFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(gripFrontDiffuse));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, FloatBuffer.wrap(gripFrontDiffuse));

			GL11.glCallList(barList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// Renders the shelf
		GL11.glPushMatrix();
		{
			// change the geometry colour to white so that the texture
			// is bright and details can be seen clearly
			Colour.WHITE.submit();
			// enable texturing and bind an appropriate texture
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, bottlesTextures.getTextureID());

			GL11.glTranslatef(11.5f + widthView, 5 + heightView, -25 + moving);
			GL11.glRotatef(270, 0, 1, 0);

			// how shiny is the floor (specular exponent)
			float gripFrontShininess = 100.0f;
			// specular reflection of the floor
			float gripFrontSpecular[] = { 0.1f, 0.1f, 0.8f, 1.0f };
			// diffuse reflection of the floor
			float gripFrontDiffuse[] = { 1f, 1f, 1f, 1.0f };

			// set the material properties for the dart grip using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, gripFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(gripFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(gripFrontDiffuse));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, FloatBuffer.wrap(gripFrontDiffuse));

			GL11.glCallList(shelfList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// Renders a table
		GL11.glPushMatrix();
		{
			// how shiny is the table (specular exponent)
			float tableFrontShininess = 40.0f;
			// specular reflection of the table
			float tableFrontSpecular[] = { 0.1f, 0.0f, 0.0f, 1.0f };
			// diffuse reflection of the table
			float tableFrontDiffuse[] = { 0.9f, 0.9f, 0.9f, 1.0f };

			// set the material properties for the table using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, tableFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(tableFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(tableFrontDiffuse));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, FloatBuffer.wrap(tableFrontDiffuse));

			// change the geometry colour to white so that the texture
			// is bright and details can be seen clearly
			Colour.WHITE.submit();
			// enable texturing and bind an appropriate texture
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, tableTextures.getTextureID());

			GL11.glTranslatef(-10 + widthView, 1.2f + heightView, -25 + moving);

			// how shiny is the floor (specular exponent)
			float gripFrontShininess = 100.0f;
			// specular reflection of the floor
			float gripFrontSpecular[] = { 0.1f, 0.1f, 0.8f, 1.0f };
			// diffuse reflection of the floor
			float gripFrontDiffuse[] = { 1f, 1f, 1f, 1.0f };

			// set the material properties for the dart grip using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, gripFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(gripFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(gripFrontDiffuse));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, FloatBuffer.wrap(gripFrontDiffuse));

			GL11.glCallList(tableTopList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();

			// Renders table leg #1
			GL11.glPushMatrix();
			{
				GL11.glTranslatef(-1.9f, -1.2f, 3.9f);
				GL11.glCallList(tableLegList);
			}
			GL11.glPopMatrix();

			// Renders table leg #2
			GL11.glPushMatrix();
			{
				GL11.glTranslatef(1.9f, -1.2f, 3.9f);
				GL11.glCallList(tableLegList);
			}
			GL11.glPopMatrix();

			// Renders table leg #3
			GL11.glPushMatrix();
			{
				GL11.glTranslatef(-1.9f, -1.2f, -3.9f);
				GL11.glCallList(tableLegList);
			}
			GL11.glPopMatrix();

			// Renders table leg #4
			GL11.glPushMatrix();
			{
				GL11.glTranslatef(1.9f, -1.2f, -3.9f);
				GL11.glCallList(tableLegList);
			}
			GL11.glPopMatrix();

		}
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		{
			// Renders the dart grip

			GL11.glTranslatef(dartMovementX, dartMovementY, 0);
			GL11.glRotatef(dartAngleY, 1, 0, 0);
			GL11.glRotatef(dartAngleX, 0, 1, 0);
			GL11.glRotatef(spinRotationAngle, 0, 0, 1);

			// how shiny is the dart grip (specular exponent)
			float gripFrontShininess = 40.0f;
			// specular reflection of the dart grip
			float gripFrontSpecular[] = { 0.2f, 0.2f, 0.2f, 1.0f };
			// diffuse reflection of the dart grip
			float gripFrontDiffuse[] = { 0.2f, 0.2f, 0.2f, 1.0f };

			// set the material properties for the dart grip using OpenGL
			GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, gripFrontShininess);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(gripFrontSpecular));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(gripFrontDiffuse));
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, FloatBuffer.wrap(gripFrontDiffuse));

			GL11.glCallList(gripList);

			GL11.glPushMatrix();
			{
				// Renders the dart spike

				GL11.glTranslatef(0, 0, -2);

				// how shiny is the dart spike (specular exponent)
				float spikeFrontShininess = 40.0f;
				// specular reflection of the dart spike
				float spikeFrontSpecular[] = { 0.8f, 0.8f, 0.8f, 1.0f };
				// diffuse reflection of the dart spike
				float spikeFrontDiffuse[] = { 0.6f, 0.6f, 0.6f, 1.0f };

				// set the material properties for the dart grip using OpenGL
				GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, spikeFrontShininess);
				GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(spikeFrontSpecular));
				GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(spikeFrontDiffuse));
				GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, FloatBuffer.wrap(spikeFrontDiffuse));

				GL11.glCallList(spikeList);
			}
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			{
				// Renders dart fin #1

				GL11.glTranslatef(0, 0.1f, 2.75f);

				// how shiny are the fins (specular exponent)
				float finFrontShininess = 2.0f;
				// specular reflection of the fins
				float finFrontSpecular[] = { 0.4f, 0.0f, 0.0f, 1.0f };
				// diffuse reflection of the fins
				float finFrontDiffuse[] = { 0.6f, 0.2f, 0.2f, 1.0f };

				// set the material properties for the fins using OpenGL
				GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, finFrontShininess);
				GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(finFrontSpecular));
				GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(finFrontDiffuse));
				GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, FloatBuffer.wrap(finFrontDiffuse));

				GL11.glCallList(finList);
			}
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			{
				// Renders dart fin #2

				GL11.glTranslatef(0, -0.1f, 2.75f);
				GL11.glRotatef(180f, 0, 0, 1);

				GL11.glCallList(finList);
			}
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			{
				// Renders dart fin #3

				GL11.glTranslatef(-0.1f, 0, 2.75f);
				GL11.glRotatef(90f, 0, 0, 1);

				GL11.glCallList(finList);
			}
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			{
				// Renders dart fin #4

				GL11.glTranslatef(0.1f, 0, 2.75f);
				GL11.glRotatef(270f, 0, 0, 1);

				GL11.glCallList(finList);
			}
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			{
				// Renders the back of the dart

				// how shiny is the back (specular exponent)
				float finFrontShininess = 2.0f;
				// specular reflection of the back
				float finFrontSpecular[] = { 0.1f, 0.0f, 0.0f, 1.0f };
				// diffuse reflection of the back
				float finFrontDiffuse[] = { 0.8f, 0.6f, 0.2f, 1.0f };

				// set the material properties for the back using OpenGL
				GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, finFrontShininess);
				GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, FloatBuffer.wrap(finFrontSpecular));
				GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, FloatBuffer.wrap(finFrontDiffuse));
				GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, FloatBuffer.wrap(finFrontDiffuse));

				GL11.glTranslatef(0, 0, 3);

				GL11.glCallList(dartBackEndList);
			}
			GL11.glPopMatrix();
		}
		GL11.glPopMatrix();

	}

	protected void setSceneCamera() {
		// call the default behaviour defined in GraphicsLab. This will set a
		// default
		// perspective projection
		// and default camera settings ready for some custom camera positioning
		// below...
		super.setSceneCamera();

		// TODO: If it is appropriate for your scene, modify the camera's
		// position and
		// orientation here
		// using a call to GL11.gluLookAt(...)

		GLU.gluLookAt(0.0f, 1f, 7.0f, // viewer location
				0.0f, 0.0f, 0.0f, // view point loc.
				0.0f, 1.0f, 0.0f); // view up vector

	}

	protected void cleanupScene() {// TODO: Clean up your resources here
	}

	private void checkDartHit() {
		if (isDartOnBoard()) {
			thrown = false;
		}
	}

	private boolean isDartOnBoard() {
		if (-67.0f + moving >= -1.0f) {
			return true;
		}
		return false;
	}

	private boolean dartXLimit() {
		if (dartMovementX > 1.8f || dartMovementX < -1.8f) {
			return true;
		}
		return false;
	}

	private boolean dartYLimit() {
		if (dartMovementY > 1.8f || dartMovementY < -0.6f) {
			return true;
		}
		return false;
	}

	private void resetAnimations() {
		thrown = false;
		dartMovementY = 0.0f;
		dartMovementX = 0.0f;
		dartAngleX = 0.0f;
		dartAngleY = 0.0f;
		heightView = 0.0f;
		widthView = 0.0f;
		moving = 0.0f;
	}

	private void drawUnitGrip() {
		new Cylinder().draw(0.1f, 0.1f, 3f, 20, 20);
	}

	private void drawUnitFin() {
		// the vertices for a fin
		Vertex v1 = new Vertex(0.001f, 0f, 0.33f);
		Vertex v2 = new Vertex(0.001f, 0.15f, 0.3f);
		Vertex v3 = new Vertex(0.001f, 0.15f, -0.3f);
		Vertex v4 = new Vertex(0.001f, 0f, -0.66f);
		Vertex v5 = new Vertex(-0.001f, 0f, 0.33f);
		Vertex v6 = new Vertex(-0.001f, 0.15f, 0.3f);
		Vertex v7 = new Vertex(-0.001f, 0.15f, -0.3f);
		Vertex v8 = new Vertex(-0.001f, 0f, -0.66f);

		// draw the near face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v6.toVector(), v5.toVector(), v1.toVector(), v2.toVector()).submit();

			v6.submit();
			v5.submit();
			v1.submit();
			v2.submit();
		}
		GL11.glEnd();

		// draw the far face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v3.toVector(), v4.toVector(), v8.toVector(), v7.toVector()).submit();

			v3.submit();
			v4.submit();
			v8.submit();
			v7.submit();
		}
		GL11.glEnd();

		// draw the left face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v7.toVector(), v8.toVector(), v5.toVector(), v6.toVector()).submit();

			v7.submit();
			v8.submit();
			v5.submit();
			v6.submit();
		}
		GL11.glEnd();

		// draw the right face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v2.toVector(), v1.toVector(), v4.toVector(), v3.toVector()).submit();

			v2.submit();
			v1.submit();
			v4.submit();
			v3.submit();
		}
		GL11.glEnd();

		// draw the top face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v6.toVector(), v2.toVector(), v3.toVector(), v7.toVector()).submit();

			v6.submit();
			v2.submit();
			v3.submit();
			v7.submit();
		}
		GL11.glEnd();

		// draw the bottom face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v3.toVector(), v4.toVector(), v1.toVector(), v5.toVector()).submit();

			v3.submit();
			v4.submit();
			v1.submit();
			v5.submit();
		}
		GL11.glEnd();
	}

	private void drawUnitSpike() {
		new Cylinder().draw(0, 0.1f, 2, 20, 20);
	}

	private void drawUnitDartBackEnd() {
		new Sphere().draw(0.1f, 20, 20);
	}

	private void drawUnitPlane() {
		Vertex v1 = new Vertex(-0.5f, 0.0f, -0.5f); // left, back
		Vertex v2 = new Vertex(0.5f, 0.0f, -0.5f); // right, back
		Vertex v3 = new Vertex(0.5f, 0.0f, 0.5f); // right, front
		Vertex v4 = new Vertex(-0.5f, 0.0f, 0.5f); // left, front

		// draw the plane geometry. order the vertices so that the plane faces
		// up
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v4.toVector(), v3.toVector(), v2.toVector(), v1.toVector()).submit();

			GL11.glTexCoord2f(0.0f, 0.0f);
			v4.submit();

			GL11.glTexCoord2f(1.0f, 0.0f);
			v3.submit();

			GL11.glTexCoord2f(1.0f, 1.0f);
			v2.submit();

			GL11.glTexCoord2f(0.0f, 1.0f);
			v1.submit();
		}
		GL11.glEnd();

		// if the user is viewing an axis, then also draw this plane
		// using lines so that axis aligned planes can still be seen
		if (isViewingAxis()) {
			// also disable textures when drawing as lines
			// so that the lines can be seen more clearly
			GL11.glPushAttrib(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glBegin(GL11.GL_LINE_LOOP);
			{
				v4.submit();
				v3.submit();
				v2.submit();
				v1.submit();
			}
			GL11.glEnd();
			GL11.glPopAttrib();
		}
	}

	private void drawUnitDartBoard() {
		Vertex v1 = new Vertex(-2.5f, 2.5f, 0.1f);
		Vertex v2 = new Vertex(2.5f, 2.5f, 0.1f);
		Vertex v3 = new Vertex(2.5f, -2.5f, 0.1f);
		Vertex v4 = new Vertex(-2.5f, -2.5f, 0.1f);
		Vertex v5 = new Vertex(-2.5f, 2.5f, -0.1f);
		Vertex v6 = new Vertex(2.5f, 2.5f, -0.1f);
		Vertex v7 = new Vertex(2.5f, -2.5f, -0.1f);
		Vertex v8 = new Vertex(-2.5f, -2.5f, -0.1f);

		// draw the near face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v4.toVector(), v3.toVector(), v2.toVector(), v1.toVector()).submit();
			GL11.glTexCoord2f(0.0f, 0.0f);
			v4.submit();
			GL11.glTexCoord2f(1.0f, 0.0f);
			v3.submit();
			GL11.glTexCoord2f(1.0f, 1.0f);
			v2.submit();
			GL11.glTexCoord2f(0.0f, 1.0f);
			v1.submit();
		}
		GL11.glEnd();

		// draw the far face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v8.toVector(), v7.toVector(), v6.toVector(), v5.toVector()).submit();

			v8.submit();
			v7.submit();
			v6.submit();
			v5.submit();
		}
		GL11.glEnd();

		// draw the left face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v1.toVector(), v5.toVector(), v8.toVector(), v4.toVector()).submit();

			v1.submit();
			v5.submit();
			v8.submit();
			v4.submit();
		}
		GL11.glEnd();

		// draw the right face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v2.toVector(), v3.toVector(), v7.toVector(), v6.toVector()).submit();

			v2.submit();
			v3.submit();
			v7.submit();
			v6.submit();
		}
		GL11.glEnd();

		// draw the top face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v1.toVector(), v2.toVector(), v6.toVector(), v5.toVector()).submit();

			v1.submit();
			v2.submit();
			v6.submit();
			v5.submit();
		}
		GL11.glEnd();

		// draw the bottom face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v3.toVector(), v4.toVector(), v7.toVector(), v8.toVector()).submit();

			v3.submit();
			v4.submit();
			v7.submit();
			v8.submit();
		}
		GL11.glEnd();
	}

	private void drawUnitRing() {
		new Disk().draw(0.4f, 0.5f, 20, 20);
	}

	private void drawUnitTableLeg() {
		Vertex v1 = new Vertex(-0.1f, 1.0f, 0.1f);
		Vertex v2 = new Vertex(0.1f, 1.0f, 0.1f);
		Vertex v3 = new Vertex(-0.1f, -1.0f, 0.1f);
		Vertex v4 = new Vertex(0.1f, -1.0f, 0.1f);
		Vertex v5 = new Vertex(-0.1f, 1.0f, -0.1f);
		Vertex v6 = new Vertex(0.1f, 1.0f, -0.1f);
		Vertex v7 = new Vertex(-0.1f, -1.0f, -0.1f);
		Vertex v8 = new Vertex(0.1f, -1.0f, -0.1f);

		// draw the near face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v3.toVector(), v4.toVector(), v2.toVector(), v1.toVector()).submit();
			v3.submit();
			v4.submit();
			v2.submit();
			v1.submit();
		}
		GL11.glEnd();

		// draw the far face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v8.toVector(), v7.toVector(), v5.toVector(), v6.toVector()).submit();

			v8.submit();
			v7.submit();
			v5.submit();
			v6.submit();
		}
		GL11.glEnd();

		// draw the left face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v1.toVector(), v5.toVector(), v7.toVector(), v3.toVector()).submit();

			v1.submit();
			v5.submit();
			v7.submit();
			v3.submit();
		}
		GL11.glEnd();

		// draw the right face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v2.toVector(), v4.toVector(), v8.toVector(), v6.toVector()).submit();

			v2.submit();
			v4.submit();
			v8.submit();
			v6.submit();
		}
		GL11.glEnd();

		// draw the top face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v1.toVector(), v2.toVector(), v6.toVector(), v5.toVector()).submit();

			v1.submit();
			v2.submit();
			v6.submit();
			v5.submit();
		}
		GL11.glEnd();

		// draw the bottom face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v4.toVector(), v3.toVector(), v7.toVector(), v8.toVector()).submit();

			v4.submit();
			v3.submit();
			v7.submit();
			v8.submit();
		}
		GL11.glEnd();

	}

	private void drawUnitTableTop() {
		Vertex v1 = new Vertex(-2f, 0.2f, 4.0f);
		Vertex v2 = new Vertex(2f, 0.2f, 4.0f);
		Vertex v3 = new Vertex(-2f, -0.2f, 4.0f);
		Vertex v4 = new Vertex(2f, -0.2f, 4.0f);
		Vertex v5 = new Vertex(-2f, 0.2f, -4.0f);
		Vertex v6 = new Vertex(2f, 0.2f, -4.0f);
		Vertex v7 = new Vertex(-2f, -0.2f, -4.0f);
		Vertex v8 = new Vertex(2f, -0.2f, -4.0f);

		// draw the near face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v3.toVector(), v4.toVector(), v2.toVector(), v1.toVector()).submit();

			GL11.glTexCoord2f(0.0f, 1.0f);
			v3.submit();
			GL11.glTexCoord2f(0.0f, 0.0f);
			v4.submit();
			GL11.glTexCoord2f(1.0f, 0.0f);
			v2.submit();
			GL11.glTexCoord2f(1.0f, 1.0f);
			v1.submit();
		}
		GL11.glEnd();

		// draw the far face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v8.toVector(), v7.toVector(), v5.toVector(), v6.toVector()).submit();

			GL11.glTexCoord2f(0.0f, 0.0f);
			v8.submit();
			GL11.glTexCoord2f(1.0f, 0.0f);
			v7.submit();
			GL11.glTexCoord2f(1.0f, 1.0f);
			v5.submit();
			GL11.glTexCoord2f(0.0f, 1.0f);
			v6.submit();
		}
		GL11.glEnd();

		// draw the left face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v1.toVector(), v5.toVector(), v7.toVector(), v3.toVector()).submit();

			GL11.glTexCoord2f(0.0f, 1.0f);
			v1.submit();
			GL11.glTexCoord2f(0.0f, 0.0f);
			v5.submit();
			GL11.glTexCoord2f(1.0f, 0.0f);
			v7.submit();
			GL11.glTexCoord2f(1.0f, 1.0f);
			v3.submit();
		}
		GL11.glEnd();

		// draw the right face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v2.toVector(), v4.toVector(), v8.toVector(), v6.toVector()).submit();

			GL11.glTexCoord2f(0.0f, 0.0f);
			v2.submit();
			GL11.glTexCoord2f(1.0f, 0.0f);
			v4.submit();
			GL11.glTexCoord2f(1.0f, 1.0f);
			v8.submit();
			GL11.glTexCoord2f(0.0f, 1.0f);
			v6.submit();
		}
		GL11.glEnd();

		// draw the top face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v1.toVector(), v2.toVector(), v6.toVector(), v5.toVector()).submit();

			GL11.glTexCoord2f(0.0f, 0.0f);
			v1.submit();
			GL11.glTexCoord2f(1.0f, 0.0f);
			v2.submit();
			GL11.glTexCoord2f(1.0f, 1.0f);
			v6.submit();
			GL11.glTexCoord2f(0.0f, 1.0f);
			v5.submit();
		}
		GL11.glEnd();

		// draw the bottom face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v4.toVector(), v3.toVector(), v7.toVector(), v8.toVector()).submit();

			v4.submit();
			v3.submit();
			v7.submit();
			v8.submit();
		}
		GL11.glEnd();
	}

	private void drawUnitLight() {
		Vertex v1 = new Vertex(-0.5f, 0.1f, 0.5f);
		Vertex v2 = new Vertex(0.5f, 0.1f, 0.5f);
		Vertex v3 = new Vertex(-0.5f, -0.1f, 0.5f);
		Vertex v4 = new Vertex(0.5f, -0.1f, 0.5f);
		Vertex v5 = new Vertex(-0.5f, 0.1f, -0.5f);
		Vertex v6 = new Vertex(0.5f, 0.1f, -0.5f);
		Vertex v7 = new Vertex(-0.5f, -0.1f, -0.5f);
		Vertex v8 = new Vertex(0.5f, -0.1f, -0.5f);

		// draw the near face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v3.toVector(), v4.toVector(), v2.toVector(), v1.toVector()).submit();
			v3.submit();
			v4.submit();
			v2.submit();
			v1.submit();
		}
		GL11.glEnd();

		// draw the far face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v8.toVector(), v7.toVector(), v5.toVector(), v6.toVector()).submit();

			v8.submit();
			v7.submit();
			v5.submit();
			v6.submit();
		}
		GL11.glEnd();

		// draw the left face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v1.toVector(), v5.toVector(), v7.toVector(), v3.toVector()).submit();

			v1.submit();
			v5.submit();
			v7.submit();
			v3.submit();
		}
		GL11.glEnd();

		// draw the right face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v2.toVector(), v4.toVector(), v8.toVector(), v6.toVector()).submit();

			v2.submit();
			v4.submit();
			v8.submit();
			v6.submit();
		}
		GL11.glEnd();

		// draw the top face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v1.toVector(), v2.toVector(), v6.toVector(), v5.toVector()).submit();

			v1.submit();
			v2.submit();
			v6.submit();
			v5.submit();
		}
		GL11.glEnd();

		// draw the bottom face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v4.toVector(), v3.toVector(), v7.toVector(), v8.toVector()).submit();

			GL11.glTexCoord2f(0.0f, 0.0f);
			v4.submit();
			GL11.glTexCoord2f(1.0f, 0.0f);
			v3.submit();
			GL11.glTexCoord2f(1.0f, 1.0f);
			v7.submit();
			GL11.glTexCoord2f(0.0f, 1.0f);
			v8.submit();
		}
		GL11.glEnd();
	}

	private void drawUnitBar() {
		Vertex v1 = new Vertex(-25.0f, 1.5f, 1.0f);
		Vertex v2 = new Vertex(25.0f, 1.5f, 1.0f);
		Vertex v3 = new Vertex(-25.0f, -1.5f, 1.0f);
		Vertex v4 = new Vertex(25.0f, -1.5f, 1.0f);
		Vertex v5 = new Vertex(-25.0f, 1.5f, -1.0f);
		Vertex v6 = new Vertex(25.0f, 1.5f, -1.0f);
		Vertex v7 = new Vertex(-25.0f, -1.5f, -1.0f);
		Vertex v8 = new Vertex(25.0f, -1.5f, -1.0f);

		// draw the near face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v3.toVector(), v4.toVector(), v2.toVector(), v1.toVector()).submit();

			GL11.glTexCoord2f(0.0f, 0.0f);
			v3.submit();
			GL11.glTexCoord2f(10.0f, 0.0f);
			v4.submit();
			GL11.glTexCoord2f(10.0f, 1.0f);
			v2.submit();
			GL11.glTexCoord2f(0.0f, 1.0f);
			v1.submit();
		}
		GL11.glEnd();

		// draw the far face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v8.toVector(), v7.toVector(), v5.toVector(), v6.toVector()).submit();

			v8.submit();
			v7.submit();
			v5.submit();
			v6.submit();
		}
		GL11.glEnd();

		// draw the left face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v1.toVector(), v5.toVector(), v7.toVector(), v3.toVector()).submit();

			v1.submit();
			v5.submit();
			v7.submit();
			v3.submit();
		}
		GL11.glEnd();

		// draw the right face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v2.toVector(), v4.toVector(), v8.toVector(), v6.toVector()).submit();

			v2.submit();
			v4.submit();
			v8.submit();
			v6.submit();
		}
		GL11.glEnd();

		// draw the top face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v1.toVector(), v2.toVector(), v6.toVector(), v5.toVector()).submit();

			GL11.glTexCoord2f(0.0f, 0.0f);
			v1.submit();
			GL11.glTexCoord2f(1.0f, 0.0f);
			v2.submit();
			GL11.glTexCoord2f(1.0f, 1.0f);
			v6.submit();
			GL11.glTexCoord2f(0.0f, 1.0f);
			v5.submit();
		}
		GL11.glEnd();

		// draw the bottom face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v4.toVector(), v3.toVector(), v7.toVector(), v8.toVector()).submit();

			v4.submit();
			v3.submit();
			v7.submit();
			v8.submit();
		}
		GL11.glEnd();
	}

	private void drawUnitShelf() {
		Vertex v1 = new Vertex(-10.0f, 1.5f, 1.0f);
		Vertex v2 = new Vertex(10.0f, 1.5f, 1.0f);
		Vertex v3 = new Vertex(-10.0f, -1.5f, 1.0f);
		Vertex v4 = new Vertex(10.0f, -1.5f, 1.0f);
		Vertex v5 = new Vertex(-10.0f, 1.5f, -1.0f);
		Vertex v6 = new Vertex(10.0f, 1.5f, -1.0f);
		Vertex v7 = new Vertex(-10.0f, -1.5f, -1.0f);
		Vertex v8 = new Vertex(10.0f, -1.5f, -1.0f);

		// draw the near face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v3.toVector(), v4.toVector(), v2.toVector(), v1.toVector()).submit();

			GL11.glTexCoord2f(0.0f, 0.0f);
			v3.submit();
			GL11.glTexCoord2f(1.5f, 0.0f);
			v4.submit();
			GL11.glTexCoord2f(1.5f, 1.0f);
			v2.submit();
			GL11.glTexCoord2f(0.0f, 1.0f);
			v1.submit();
		}
		GL11.glEnd();

		// draw the far face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v8.toVector(), v7.toVector(), v5.toVector(), v6.toVector()).submit();

			v8.submit();
			v7.submit();
			v5.submit();
			v6.submit();
		}
		GL11.glEnd();

		// draw the left face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v1.toVector(), v5.toVector(), v7.toVector(), v3.toVector()).submit();

			v1.submit();
			v5.submit();
			v7.submit();
			v3.submit();
		}
		GL11.glEnd();

		// draw the right face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v2.toVector(), v4.toVector(), v8.toVector(), v6.toVector()).submit();

			v2.submit();
			v4.submit();
			v8.submit();
			v6.submit();
		}
		GL11.glEnd();

		// draw the top face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v1.toVector(), v2.toVector(), v6.toVector(), v5.toVector()).submit();

			v1.submit();
			v2.submit();
			v6.submit();
			v5.submit();
		}
		GL11.glEnd();

		// draw the bottom face:
		GL11.glBegin(GL11.GL_POLYGON);
		{
			new Normal(v4.toVector(), v3.toVector(), v7.toVector(), v8.toVector()).submit();

			v4.submit();
			v3.submit();
			v7.submit();
			v8.submit();
		}
		GL11.glEnd();
	}

}