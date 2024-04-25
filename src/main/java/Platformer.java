/*
 * Copyright (c) 2010-2022 William Bittle  http://www.dyn4j.org/
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted 
 * provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice, this list of conditions 
 *     and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice, this list of conditions 
 *     and the following disclaimer in the documentation and/or other materials provided with the 
 *     distribution.
 *   * Neither the name of dyn4j nor the names of its contributors may be used to endorse or 
 *     promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR 
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL 
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER 
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
//package org.dyn4j.samples;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;

import java.util.List;

import javax.swing.JFrame;

import org.dyn4j.dynamics.TimeStep;
import org.dyn4j.dynamics.contact.ContactConstraint;
import org.dyn4j.geometry.AABB;
import org.dyn4j.geometry.Vector2;
import org.dyn4j.samples.framework.Camera;
import org.dyn4j.samples.framework.SimulationBody;
import org.dyn4j.samples.framework.SimulationFrame;
import org.dyn4j.samples.framework.input.BooleanStateKeyboardInputHandler;
import org.dyn4j.samples.framework.input.ToggleStateKeyboardInputHandler;
import org.dyn4j.world.ContactCollisionData;
import org.dyn4j.world.PhysicsWorld;
import org.dyn4j.world.listener.ContactListenerAdapter;
import org.dyn4j.world.listener.StepListenerAdapter;

/**
 * A simple scene of a circle that is controlled by the left and right arrow
 * keys that is moved by applying torques and forces.
 * <p>
 * Also illustrated here is how to track whether the body is in contact with the
 * "ground."
 * <p>
 * Always keep in mind that this is just an example, production code should be
 * more robust and better organized.
 * 
 * @author William Bittle
 * @since 5.0.1
 * @version 3.2.0
 */
public class Platformer extends SimulationFrame {
	/** The serial version id */
	private static final long serialVersionUID = -313391186714427055L;

	private String map;
	private int numPlayers;
	private int startingScore;
	private Boolean gameOver = false;
	private String gamemode;
	private String p1_img;
	private String p2_img;
	private String p3_img;
	private String p4_img;

	private JFrame frame;

	public static final Object CHARACTER = new Object();
	public static final Object FLOOR = new Object();
	public static final Object ONE_WAY_PLATFORM = new Object();
	public static final Object SCORE_ZONE = new Object();

	private final BooleanStateKeyboardInputHandler p1_up;
	private final BooleanStateKeyboardInputHandler p1_down;
	private final BooleanStateKeyboardInputHandler p1_left;
	private final BooleanStateKeyboardInputHandler p1_right;

	private final BooleanStateKeyboardInputHandler p2_up;
	private final BooleanStateKeyboardInputHandler p2_down;
	private final BooleanStateKeyboardInputHandler p2_left;
	private final BooleanStateKeyboardInputHandler p2_right;

	private final BooleanStateKeyboardInputHandler p3_up;
	private final BooleanStateKeyboardInputHandler p3_down;
	private final BooleanStateKeyboardInputHandler p3_left;
	private final BooleanStateKeyboardInputHandler p3_right;

	private final BooleanStateKeyboardInputHandler p4_up;
	private final BooleanStateKeyboardInputHandler p4_down;
	private final BooleanStateKeyboardInputHandler p4_left;
	private final BooleanStateKeyboardInputHandler p4_right;
	private final BooleanStateKeyboardInputHandler quit;

	private Player player1;
	private Player player2;
	private Player player3;
	private Player player4;
	private SimulationBody character;
	private SimulationBody character2;
	private SimulationBody character3;
	private SimulationBody character4;
	private boolean onGround = true;
	private boolean onGround2 = true;
	private boolean onGround3 = true;
	private boolean onGround4 = true;

	// creates the tomato icon
	/*
	 * private static final BufferedImage tomato =
	 * getImageSuppressExceptions("images/tomatoPlayer.PNG");
	 * 
	 * Helper function to read the images from the class path
	 */
	/*
	 * private static final BufferedImage getImageSuppressExceptions(String
	 * pathOnClasspath) { try { return
	 * ImageIO.read(Images.class.getResource(pathOnClasspath)); } catch (IOException
	 * e) { return null; } }
	 */

	/**
	 * Default constructor for the window Map will be intigrated once we call this
	 * class from menus
	 */
	public Platformer() {
		super("Platformer");

		this.p1_up = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_UP);
		this.p1_down = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_DOWN);
		this.p1_left = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_LEFT);
		this.p1_right = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_RIGHT);

		this.p2_up = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_W);
		this.p2_down = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_S);
		this.p2_left = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_A);
		this.p2_right = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_D);

		this.p3_up = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_T);
		this.p3_down = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_G);
		this.p3_left = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_F);
		this.p3_right = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_H);

		this.p4_up = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_I);
		this.p4_down = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_K);
		this.p4_left = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_J);
		this.p4_right = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_L);

		this.quit = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_BACK_SPACE);
		
		this.p1_up.install();
		this.p1_down.install();
		this.p1_left.install();
		this.p1_right.install();

		this.p2_up.install();
		this.p2_down.install();
		this.p2_left.install();
		this.p2_right.install();

		this.p3_up.install();
		this.p3_down.install();
		this.p3_left.install();
		this.p3_right.install();

		this.p4_up.install();
		this.p4_down.install();
		this.p4_left.install();
		this.p4_right.install();
		
		this.quit.install();

	}

	public void setMap(String m) {
		map = m;
	}

	public void setNumPlayers(int n) {
		numPlayers = n;
	}

	public void setStartingScore(int s) {
		startingScore = s;
	}

	public void setGamemode(String g) {
		gamemode = g;
	}

	public void setPlayerIMG(String p1, String p2, String p3, String p4) {
		p1_img = p1;
		p2_img = p2;
		p3_img = p3;
		p4_img = p4;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dyn4j.samples.framework.SimulationFrame#initializeCamera(org.dyn4j.
	 * samples.framework.Camera)
	 */
	@Override
	protected void initializeCamera(Camera camera) {
		super.initializeCamera(camera);
		camera.scale = 20.0;
	}

	// Creates game objects and adds them to the world.
	protected void initializeWorld() {

		// TODO this will set the map variable
		/*
		 * switch(mapNum) { case value1 : // Statements case value2 : // Statements
		 * default : // default Statement }
		 */

		// loads level from a text file
		LoadLevel loading = new LoadLevel(map);
		int length = loading.getLength();
		this.world.addBody(loading.loadBG());
		
		
		for (int i = 0; i < length; i++) {
			this.world.addBody(loading.loadMap(i));
		}

		initPlayers(numPlayers);

		// Use a number of concepts here to support movement, jumping, and one-way
		// platforms - this is by no means THE solution to these problems, but just
		// and example to provide some ideas on how you might

		// One consideration might be to use a sensor body to get less accurate
		// on-ground detection so that it's not frustrating to the user. dyn4j
		// will detect them in collision, but small bouncing or other things could
		// cause it to look/feel wrong

		// SETP 1:
		// at the beginning of each world step, check if the body is in
		// contact with any of the floor bodies
		this.world.addStepListener(new StepListenerAdapter<SimulationBody>() {
			@Override
			public void begin(TimeStep step, PhysicsWorld<SimulationBody, ?> world) {
				super.begin(step, world);

				boolean isGround = false;
				List<ContactConstraint<SimulationBody>> contacts = world.getContacts(character);
				for (ContactConstraint<SimulationBody> cc : contacts) {
					if (is(cc.getOtherBody(character), FLOOR, ONE_WAY_PLATFORM) && cc.isEnabled()) {
						isGround = true;
					}
				}
				List<ContactConstraint<SimulationBody>> contacts2 = world.getContacts(character2);
				for (ContactConstraint<SimulationBody> cc : contacts2) {
					if (is(cc.getOtherBody(character2), FLOOR, ONE_WAY_PLATFORM) && cc.isEnabled()) {
						isGround = true;
					}
				}

				// only clear it
				if (!isGround) {
					onGround = false;
				}
			}
		});

		// STEP 2:
		// when contacts are processed, we need to check if we're colliding with either
		// the one-way platform or the ground
		this.world.addContactListener(new ContactListenerAdapter<SimulationBody>() {
			@Override
			public void collision(ContactCollisionData<SimulationBody> collision) {
				ContactConstraint<SimulationBody> cc = collision.getContactConstraint();

				// set the other body to one-way if necessary
				disableContactForOneWay(cc);

				// track on the on-ground status
				trackIsOnGround(cc);

				super.collision(collision);
			}
		});

		Sound music = new Sound("audio/FightSong.mp3", true);
		// music.play();

	}

	protected void initPlayers(int p) {
		player1 = new Player(4, 2, startingScore, Color.orange);
		player2 = new Player(2, 2, startingScore, Color.red);
		player3 = new Player(-2, 2, startingScore, Color.black);
		player4 = new Player(-4, 2, startingScore, Color.cyan);

		if (p >= 1) {
			character = player1.createPlayer(p1_img);
			this.world.addBody(character);
		}
		if (p >= 2) {
			character2 = player2.createPlayer(p2_img);
			this.world.addBody(character2);
		}
		if (p >= 3) {
			character3 = player3.createPlayer(p3_img);
			this.world.addBody(character3);
		}
		if (p >= 4) {
			character4 = player4.createPlayer(p4_img);
			this.world.addBody(character4);
		}
	}

	/**
	 * Helper method to determine if a body is one of the given types assuming the
	 * type is stored in the user data.
	 * 
	 * @param body  the body
	 * @param types the set of types
	 * @return boolean
	 */
	private boolean is(SimulationBody body, Object... types) {
		for (Object type : types) {
			if (body.getUserData() == type) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true if the given platform should be toggled as one-way given the
	 * position of the character body.
	 * 
	 * @param character the character body
	 * @param platform  the platform body
	 * @return boolean
	 */
	private boolean allowOneWayUp(SimulationBody character, SimulationBody platform) {
		AABB wAABB = character.createAABB();
		AABB pAABB = platform.createAABB();

		// NOTE: this would need to change based on the shape of the platform and it's
		// orientation
		//
		// one thought might be to store the allowed normal of the platform on the
		// platform body
		// and check that against the ContactConstraint normal to see if they are
		// pointing in the
		// same direction
		//
		// another option might be to project both onto the platform normal to see where
		// they are overlapping
		if (wAABB.getMinY() < pAABB.getMinY()) {
			return true;
		}
		return false;
	}

	/**
	 * Disables the constraint if it's between the character and platform and it the
	 * scenario meets the condition for one-way.
	 * 
	 * @param contactConstraint the constraint
	 */
	private void disableContactForOneWay(ContactConstraint<SimulationBody> contactConstraint) {
		SimulationBody b1 = contactConstraint.getBody1();
		SimulationBody b2 = contactConstraint.getBody2();

		if (is(b1, CHARACTER) && is(b2, ONE_WAY_PLATFORM)) {
			if (allowOneWayUp(b1, b2) || p1_down.isActiveButNotHandled()) {
				p1_down.setHasBeenHandled(true);
				contactConstraint.setEnabled(false);
			}
		} else if (is(b1, ONE_WAY_PLATFORM) && is(b2, CHARACTER)) {
			if (allowOneWayUp(b2, b1) || p1_down.isActiveButNotHandled()) {
				p1_down.setHasBeenHandled(true);
				contactConstraint.setEnabled(false);
			}
		}

		if (is(b1, CHARACTER) && is(b2, ONE_WAY_PLATFORM)) {
			if (allowOneWayUp(b1, b2) || p2_down.isActiveButNotHandled()) {
				p2_down.setHasBeenHandled(true);
				contactConstraint.setEnabled(false);
			}
		} else if (is(b1, ONE_WAY_PLATFORM) && is(b2, CHARACTER)) {
			if (allowOneWayUp(b2, b1) || p2_down.isActiveButNotHandled()) {
				p2_down.setHasBeenHandled(true);
				contactConstraint.setEnabled(false);
			}
		}
	}

	/**
	 * Sets the isOnGround flag if the given contact constraint is between the
	 * character body and a floor or one-way platform.
	 * 
	 * @param contactConstraint
	 */
	private void trackIsOnGround(ContactConstraint<SimulationBody> contactConstraint) {
		SimulationBody b1 = contactConstraint.getBody1();
		SimulationBody b2 = contactConstraint.getBody2();

		if (is(b1, CHARACTER) && is(b2, FLOOR, ONE_WAY_PLATFORM) && contactConstraint.isEnabled()) {
			onGround = true;
		} else if (is(b1, FLOOR, ONE_WAY_PLATFORM) && is(b2, CHARACTER) && contactConstraint.isEnabled()) {
			onGround = true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dyn4j.samples.framework.SimulationFrame#reset()
	 */
	@Override
	public void reset() {
		super.reset();
		this.onGround = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dyn4j.samples.framework.SimulationFrame#handleEvents()
	 */
	protected void handleEvents() {
		super.handleEvents();

		// Apply torque based on keyboard input for character 1
		if (this.p1_left.isActive()) {
			character.applyTorque(Math.PI / 1);
		}
		if (this.p1_right.isActive()) {
			character.applyTorque(-Math.PI / 1);
		}

		// Apply torque based on keyboard input for character 2
		if (this.p2_left.isActive()) {
			character2.applyTorque(Math.PI / 1);
		}
		if (this.p2_right.isActive()) {
			character2.applyTorque(-Math.PI / 1);
		}

		if (this.p3_left.isActive()) {
			character3.applyTorque(Math.PI / 1);
		}
		if (this.p3_right.isActive()) {
			character3.applyTorque(-Math.PI / 1);
		}

		if (this.p4_left.isActive()) {
			character4.applyTorque(Math.PI / 1);
		}
		if (this.p4_right.isActive()) {
			character4.applyTorque(-Math.PI / 1);
		}

		// Jumping mechanism for character (Up arrow key)
		if (this.p1_up.isActiveButNotHandled()) {
			this.p1_up.setHasBeenHandled(true);
			if (this.onGround(character)) {
				// Apply an impulse in the upward direction
				character.applyImpulse(new Vector2(0.0, 7));
				// Set onGround to false to prevent consecutive jumps until landing again
				onGround = false;
			}
		}

		// Jumping mechanism for character 2 (W key)
		if (this.p2_up.isActiveButNotHandled()) {
			this.p2_up.setHasBeenHandled(true);
			if (this.onGround(character2)) {
				// Apply an impulse in the upward direction
				character2.applyImpulse(new Vector2(0.0, 7));
				// Set onGround to false to prevent consecutive jumps until landing again
				onGround = false;
			}
		}

		if (this.p3_up.isActiveButNotHandled()) {
			this.p3_up.setHasBeenHandled(true);
			if (this.onGround(character3)) {
				// Apply an impulse in the upward direction
				character3.applyImpulse(new Vector2(0.0, 7));
				// Set onGround to false to prevent consecutive jumps until landing again
				onGround = false;
			}
		}

		if (this.p4_up.isActiveButNotHandled()) {
			this.p4_up.setHasBeenHandled(true);
			if (this.onGround(character4)) {
				// Apply an impulse in the upward direction
				character4.applyImpulse(new Vector2(0.0, 7));
				// Set onGround to false to prevent consecutive jumps until landing again
				onGround = false;
			}
		}

		gameruleStock();

		if (gameOver) {
			this.pause();
		}
		
		if(this.isPaused()) {
			if (quit.isActive()) {
				closeToMenu();
			}
		}

		// Update character color based on whether it's on the ground or not
		// TODO use for testing, delete later
		// character.setColor(onGround(character) ? WHEEL_ON_COLOR :
		// player1.getplayerColor());
		// character2.setColor(onGround(character2) ? WHEEL_ON_COLOR :
		// player2.getplayerColor());
	}

	private boolean onGround(SimulationBody character) {
		List<ContactConstraint<SimulationBody>> contacts = world.getContacts(character);
		for (ContactConstraint<SimulationBody> cc : contacts) {
			if (is(cc.getOtherBody(character), FLOOR, ONE_WAY_PLATFORM) && cc.isEnabled()) {
				return true;
			}
		}
		return false;
	}

	private boolean outOfBounds(SimulationBody character) {
		List<ContactConstraint<SimulationBody>> contacts = world.getContacts(character);
		for (ContactConstraint<SimulationBody> cc : contacts) {
			if (is(cc.getOtherBody(character), SCORE_ZONE) && cc.isEnabled()) {
				return true;
			}
		}
		return false;
	}

	protected void render(Graphics2D g, double elapsedTime) {
		super.render(g, elapsedTime);
		AffineTransform tx = g.getTransform();
		g.scale(1, -1);
		g.translate(-this.getWidth() * 0.5 - this.getCameraOffsetX(),
				-this.getHeight() * 0.5 + this.getCameraOffsetY());

		int winWidth = (int) (this.getWidth() * 0.5);
		int winHeight = (int) (this.getHeight() * 0.5);

		if (numPlayers >= 1) {
			g.setColor(Color.black);
			g.setFont(new Font("SansSerif", Font.PLAIN, 20));
			if (player1.isOut()) {
				g.drawString("P1 OUT ", 20, 45);
			} else {
				g.drawString("P1 Lives: " + (player1.getLives() + 1), 20, 45);
			}
		}

		if (numPlayers >= 2) {
			g.setColor(Color.black);
			g.setFont(new Font("SansSerif", Font.PLAIN, 20));
			if (player2.isOut()) {
				g.drawString("P2 OUT ", 20, 70);
			} else {
				g.drawString("P2 Lives: " + (player2.getLives() + 1), 20, 70);
			}
		}

		if (numPlayers >= 3) {
			g.setColor(Color.black);
			g.setFont(new Font("SansSerif", Font.PLAIN, 20));
			if (player3.isOut()) {
				g.drawString("P3 OUT ", 20, 95);
			} else {
				g.drawString("P3 Lives: " + (player3.getLives() + 1), 20, 95);
			}
		}

		if (numPlayers >= 4) {
			g.setColor(Color.black);
			g.setFont(new Font("SansSerif", Font.PLAIN, 20));
			if (player4.isOut()) {
				g.drawString("P4 OUT ", 20, 120);
			} else {
				g.drawString("P4 Lives: " + (player4.getLives() + 1), 20, 120);
			}
		}

		switch (winPlayer()) {
		case 1:
			showMenuBox(g, winWidth, winHeight);
			g.setColor(Color.red);
			g.setFont(new Font("SansSerif", Font.PLAIN, 50));
			g.drawString("P1 WIN ", winWidth - 100, winHeight);
			gameOver = true;
			break;
		case 2:
			showMenuBox(g, winWidth, winHeight);
			g.setColor(Color.red);
			g.setFont(new Font("SansSerif", Font.PLAIN, 50));
			g.drawString("P2 WIN ", winWidth - 100, winHeight);
			gameOver = true;
			break;
		case 3:
			showMenuBox(g, winWidth, winHeight);
			g.setColor(Color.red);
			g.setFont(new Font("SansSerif", Font.PLAIN, 50));
			g.drawString("P3 WIN ", winWidth - 100, winHeight);
			gameOver = true;
			break;
		case 4:
			showMenuBox(g, winWidth, winHeight);
			g.setColor(Color.red);
			g.setFont(new Font("SansSerif", Font.PLAIN, 50));
			g.drawString("P4 WIN ", winWidth - 100, winHeight);
			gameOver = true;
			break;
		}

		if (this.isPaused() & !gameOver) {
			showMenuBox(g, winWidth, winHeight);
			g.setColor(Color.red);
			g.setFont(new Font("SansSerif", Font.PLAIN, 50));
			g.drawString("PAUSED", winWidth - 100, winHeight - 150);
		}
		// todo add clickable pause button and pause menu

	}
	
	private void showMenuBox(Graphics2D g, int x, int y) {
		final int w = 600;
		final int h = 400;
		int xPos = (int) (x - (w * 0.5));
		int yPos = (int) (y - (h * 0.5));
		
		g.setColor(Color.white);
		g.fillRect(xPos, yPos, w, h);
		g.setColor(Color.black);
		g.drawRect(xPos, yPos, w, h);
		g.drawString("Press Backspace to quit and return to the menu", x - 200, y + 150);
	}

	// returns the number of the winning player
	private int winPlayer() {
		if (activePlayers(player1.isOut(), player2.isOut(), player3.isOut(), player4.isOut()) == 1) {
			if (!player1.isOut()) {
				return 1;
			} else if (!player2.isOut()) {
				return 2;
			} else if (!player3.isOut()) {
				return 3;
			} else if (!player4.isOut()) {
				return 4;
			}
		}
		return 0;
	}

	// returns number of players still 'alive'
	private int activePlayers(Boolean a, Boolean b, Boolean c, Boolean d) {
		int count = 0;

		if (!a) {
			count++;
		}
		if (!b) {
			count++;
		}
		if (!c) {
			count++;
		}
		if (!d) {
			count++;
		}
		return count;
	}

	/*
	 * Win rules for the 'Stock' game mode. Detects if a player touches out of
	 * bounds zone. When player goes out of bounds, it destroys that instance and
	 * spawns a new character instance.
	 */
	protected void gameruleStock() {
		if (outOfBounds(character)) {
			if (player1.getLives() > 0) {
				this.world.removeBody(character);
				player1.subtractLives(1);
				character = player1.createPlayer(p1_img);
				this.world.addBody(character);
			} else {
				this.world.removeBody(character);
				player1.setOut(true);
			}
		}

		if (outOfBounds(character2)) {
			if (player2.getLives() > 0) {
				this.world.removeBody(character2);
				player2.subtractLives(1);
				character2 = player2.createPlayer(p2_img);
				this.world.addBody(character2);
			} else {
				this.world.removeBody(character2);
				player2.setOut(true);
			}
		}

		if (outOfBounds(character3)) {
			if (player3.getLives() > 0) {
				this.world.removeBody(character3);
				player3.subtractLives(1);
				character3 = player3.createPlayer(p3_img);
				this.world.addBody(character3);
			} else {
				this.world.removeBody(character3);
				player3.setOut(true);
			}
		}

		if (outOfBounds(character4)) {
			if (player4.getLives() > 0) {
				this.world.removeBody(character4);
				player4.subtractLives(1);
				character4 = player4.createPlayer(p4_img);
				this.world.addBody(character4);
			} else {
				this.world.removeBody(character4);
				player4.setOut(true);
			}
		}
	}
	
	private void close() {
		this.stop();
		this.dispose();
	}

	private void closeToMenu() {
		MainMenu m = new MainMenu();
		this.stop();
		this.dispose();
	}

	/**
	 * Entry point for the example application.
	 */
	public static void main(String[] args) {
		Platformer simulation = new Platformer();
		simulation.setPlayerIMG("t", "a", "o", "w");
		simulation.setMap("map1");
		simulation.setNumPlayers(2);
		simulation.setStartingScore(0);
		simulation.run();
	}
}