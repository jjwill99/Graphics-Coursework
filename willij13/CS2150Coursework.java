/* CS2150Coursework.java
 * TODO: Aston University. Vivek Bhukhan, Devin Shingadia, Jacob Williams
 *
 * Scene Graph:
 *  Scene origin
 *  |
 *
 *  TODO: Provide a scene graph for your submission
 */
package coursework.willij13;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;
import GraphicsLab.*;

/**
 * Simulation shows a dart that is thrown through the air, and hit an [INSERT
 * OBJECT HERE]. With the extra challenge of flying through moving rings.
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
 * <li>Press/hold w, a, s and d keys to move the dart around the scene while in
 * flight.
 * </ul>
 * TODO: Add any additional controls for your sample to the list above
 *
 */
public class CS2150Coursework extends GraphicsLab {
	/**
	 * display list id of the grip
	 */
	private final int gripList = 1;
	/**
	 * display list id of a fin
	 */
	private final int finList = 2;
	/**
	 * display list id of the dart spike
	 */
	private final int spikeList = 3;
	/**
	 * display list id of the dart back end
	 */
	private final int dartBackEndList = 4;
	/**
	 * display list id of plane
	 */
	private final int planeList = 5;

	private final int boardList = 6;

	private float spinRotationAngle = 0.0f;

	private float dartAngleY = 0.0f;

	private float dartMovementY = 0.0f;

	private float dartAngleX = 0.0f;

	private float dartMovementX = 0.0f;

	private float moving = 0.0f;

	private float heightView = 0.0f;

	private float widthView = 0.0f;

	private boolean thrown = false;

	/** ids for nearest, linear and mipmapped textures for the ground plane */
	private Texture groundTextures;

	private Texture wallTextures;

	// TODO: Feel free to change the window title and default animation scale
	// here
	public static void main(String args[]) {
		new CS2150Coursework().run(WINDOWED, "CS2150 Coursework Submission", 0.01f);
	}

	protected void initScene() throws Exception {
		// load the textures

		// carpet.jpg not in correct size
		groundTextures = loadTexture("coursework/willij13/textures/metal_floor.jpg");
		wallTextures = loadTexture("coursework/willij13/textures/bricks.jpg");

		// global ambient light level
		float globalAmbient[] = { 0.8f, 0.8f, 0.8f, 1.0f };
		// set the global ambient lighting
		GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, FloatBuffer.wrap(globalAmbient));

		// the first light for the scene is soft blue...
		float diffuse0[] = { 0.2f, 0.2f, 0.4f, 1.0f };
		// ...with a very dim ambient contribution...
		float ambient0[] = { 0.05f, 0.05f, 0.05f, 1.0f };
		// ...and is positioned above the viewpoint
		float position0[] = { 0.0f, 10.0f, 5.0f, 1.0f };

		// supply OpenGL with the properties for the first light
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, FloatBuffer.wrap(ambient0));
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, FloatBuffer.wrap(diffuse0));
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, FloatBuffer.wrap(diffuse0));
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, FloatBuffer.wrap(position0));
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
	}

	protected void checkSceneInput() {
		if (thrown == true) {
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				dartAngleY = 10.0f;
				dartMovementY += 0.001f;
				if (dartYLimit()) {
					dartMovementY = 1.8f;
					heightView -= 0.001f;
				}
			} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				dartAngleY = -10.0f;
				dartMovementY -= 0.001f;
				if (dartYLimit()) {
					dartMovementY = -0.6f;
					heightView += 0.001f;
					if (heightView > 0.0f) {
						dartAngleY = 0.0f;
						heightView = 0.0f;
					}
				}
			} else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				dartAngleX = 5.0f;
				dartMovementX -= 0.001f;
				if (dartXLimit()) {
					dartMovementX = -1.8f;
					widthView += 0.001f;
				}
			} else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				dartAngleX = -5.0f;
				dartMovementX += 0.001f;
				if (dartXLimit()) {
					dartMovementX = 1.8f;
					widthView -= 0.001f;
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
			// TODO: when space is help at end, movement continues
			thrown = true;
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

		spinRotationAngle += +8.0f * getAnimationScale();
	}

	protected void renderScene() {
		// draw ground plane #1
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
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, groundTextures.getTextureID());

			// position, scale and draw the ground plane using its display list
			GL11.glTranslatef(0.0f + widthView, -1.0f + heightView, -7.5f + moving);
			GL11.glScalef(25.0f, 1.0f, 40.0f);
			GL11.glCallList(planeList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// add ground plane #2
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
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, groundTextures.getTextureID());

			// position, scale and draw the ground plane using its display list
			GL11.glTranslatef(0.0f + widthView, -1.0f + heightView, -47.5f + moving);
			GL11.glScalef(25.0f, 1.0f, 40.0f);
			GL11.glCallList(planeList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw the back wall plane
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
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, wallTextures.getTextureID());

			// position, scale and draw the back plane using its display list
			GL11.glTranslatef(0.0f + widthView, 4.0f + heightView, -67f + moving);
			GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
			GL11.glScalef(25.0f, 1.0f, 10.0f);
			GL11.glCallList(planeList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw the left wall plane #1
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
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, wallTextures.getTextureID());

			// position, scale and draw the back plane using its display list
			GL11.glTranslatef(-12.5f + widthView, 4.0f + heightView, -7.5f + moving);
			GL11.glRotatef(90.0f, 1.0f, 0, 0);
			GL11.glRotatef(270.0f, 0.0f, 0.0f, 1.0f);
			GL11.glScalef(25.0f, 1.0f, 10.0f);
			GL11.glCallList(planeList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw the left wall plane #2
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
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, wallTextures.getTextureID());

			// position, scale and draw the back plane using its display list
			GL11.glTranslatef(-12.5f + widthView, 4.0f + heightView, -32f + moving);
			GL11.glRotatef(90.0f, 1.0f, 0, 0);
			GL11.glRotatef(270.0f, 0.0f, 0.0f, 1.0f);
			GL11.glScalef(25.0f, 1.0f, 10.0f);
			GL11.glCallList(planeList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw the left wall plane #3
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
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, wallTextures.getTextureID());

			// position, scale and draw the back plane using its display list
			GL11.glTranslatef(-12.5f + widthView, 4.0f + heightView, -57f + moving);
			GL11.glRotatef(90.0f, 1.0f, 0, 0);
			GL11.glRotatef(270.0f, 0.0f, 0.0f, 1.0f);
			GL11.glScalef(25.0f, 1.0f, 10.0f);
			GL11.glCallList(planeList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw the right wall plane #1
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
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, wallTextures.getTextureID());

			// position, scale and draw the back plane using its display list
			GL11.glTranslatef(12.5f + widthView, 4.0f + heightView, -7.5f + moving);
			GL11.glRotatef(90.0f, 1.0f, 0, 0);
			GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
			GL11.glScalef(25.0f, 1.0f, 10.0f);
			GL11.glCallList(planeList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw the right wall plane #2
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
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, wallTextures.getTextureID());

			// position, scale and draw the back plane using its display list
			GL11.glTranslatef(12.5f + widthView, 4.0f + heightView, -32f + moving);
			GL11.glRotatef(90.0f, 1.0f, 0, 0);
			GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
			GL11.glScalef(25.0f, 1.0f, 10.0f);
			GL11.glCallList(planeList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// draw the right wall plane #3
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
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, wallTextures.getTextureID());

			// position, scale and draw the back plane using its display list
			GL11.glTranslatef(12.5f + widthView, 4.0f + heightView, -57f + moving);
			GL11.glRotatef(90.0f, 1.0f, 0, 0);
			GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
			GL11.glScalef(25.0f, 1.0f, 10.0f);
			GL11.glCallList(planeList);

			// disable textures and reset any local lighting changes
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glPopAttrib();
		}
		GL11.glPopMatrix();

		// Renders the dart board
		//TODO: add face to the dart board...or create the dart board from scratch
		GL11.glPushMatrix();
		{
			GL11.glTranslatef(0, 3, -67 + moving);
			
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
		}
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		{
			// Renders the dart grip

			GL11.glTranslatef(0, dartMovementY, 0);
			GL11.glTranslatef(dartMovementX, 0, 0);
			GL11.glRotatef(dartAngleY, 1, 0, 0);
			GL11.glRotatef(dartAngleX, 0, 1, 0);
			GL11.glRotatef(spinRotationAngle, 0, 0, 1);

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

			GL11.glCallList(gripList);

			GL11.glPushMatrix();
			{
				// Renders the dart spike

				GL11.glTranslatef(0, 0, -2);

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
				float finFrontSpecular[] = { 0.1f, 0.0f, 0.0f, 1.0f };
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
		}

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
		if (-67.0f + moving >= -1.0f) {
			thrown = false;
		}
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
		new Cylinder().draw(1, 1, 0.5f, 20, 20);
	}

}