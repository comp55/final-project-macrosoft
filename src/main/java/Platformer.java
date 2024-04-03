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
import java.awt.event.KeyEvent;
import java.util.List;

import org.dyn4j.dynamics.TimeStep;
import org.dyn4j.dynamics.contact.ContactConstraint;
import org.dyn4j.geometry.AABB;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;
import org.dyn4j.samples.framework.Camera;
import org.dyn4j.samples.framework.SimulationBody;
import org.dyn4j.samples.framework.SimulationFrame;
import org.dyn4j.samples.framework.input.BooleanStateKeyboardInputHandler;
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

	private String map = "map1";

	private static final Color WHEEL_OFF_COLOR = Color.MAGENTA;
	private static final Color WHEEL_ON_COLOR = Color.GREEN;

	private static final Object CHARACTER = new Object();
	public static final Object FLOOR = new Object();
	public static final Object ONE_WAY_PLATFORM = new Object();

	private final BooleanStateKeyboardInputHandler up;
	private final BooleanStateKeyboardInputHandler down;
	private final BooleanStateKeyboardInputHandler left;
	private final BooleanStateKeyboardInputHandler right;

	private final BooleanStateKeyboardInputHandler w;
	private final BooleanStateKeyboardInputHandler s;
	private final BooleanStateKeyboardInputHandler a;
	private final BooleanStateKeyboardInputHandler d;

	private final BooleanStateKeyboardInputHandler t;
	private final BooleanStateKeyboardInputHandler g;
	private final BooleanStateKeyboardInputHandler f;
	private final BooleanStateKeyboardInputHandler h;

	private final BooleanStateKeyboardInputHandler i;
	private final BooleanStateKeyboardInputHandler k;
	private final BooleanStateKeyboardInputHandler j;
	private final BooleanStateKeyboardInputHandler l;

	private SimulationBody character;
	private SimulationBody character2;
	private SimulationBody character3;
	private SimulationBody character4;
	private boolean onGround = true;
	private boolean onGround2 = true;
	private boolean onGround3 = true;
	private boolean onGround4 = true;

	/**
	 * Default constructor for the window Map will be intigrated once we call this
	 * class from menus
	 */
	public Platformer(/* String map */) {
		super("Platformer");

		this.up = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_UP);
		this.down = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_DOWN);
		this.left = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_LEFT);
		this.right = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_RIGHT);

		this.w = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_W);
		this.s = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_S);
		this.a = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_A);
		this.d = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_D);

		this.t = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_T);
		this.g = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_G);
		this.f = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_F);
		this.h = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_H);

		this.i = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_I);
		this.k = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_K);
		this.j = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_J);
		this.l = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_L);

		this.up.install();
		this.down.install();
		this.left.install();
		this.right.install();

		this.w.install();
		this.s.install();
		this.a.install();
		this.d.install();

		this.t.install();
		this.g.install();
		this.f.install();
		this.h.install();

		this.i.install();
		this.k.install();
		this.j.install();
		this.l.install();

		// TODO uncomment once menu is intigrated
		// this.map = map;
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
		camera.scale = 32.0;
	}

	/**
	 * Creates game objects and adds them to the world.
	 */
	protected void initializeWorld() {

		// TODO this will call the map
		/*
		 * switch(map) { case value1 : // Statements case value2 : // Statements default
		 * : // default Statement }
		 */

		// proof of concept level loading
		LoadLevel loading = new LoadLevel(map);
		int length = loading.getLength();

		for (int i = 0; i < length; i++) {
			this.world.addBody(loading.loadMap(i));
		}

		// the wheel
		character = new SimulationBody(WHEEL_OFF_COLOR);
		// NOTE: lots of friction to simulate a sticky tire
		character.addFixture(Geometry.createCircle(0.5), 1.0, 20.0, 0.1);
		character.setMass(MassType.NORMAL);
		character.translate(0.0, -2.0);
		character.setUserData(CHARACTER);
		character.setAtRestDetectionEnabled(false);
		this.world.addBody(character);

		character2 = new SimulationBody(WHEEL_OFF_COLOR);
		// NOTE: lots of friction to simulate a sticky tire
		character2.addFixture(Geometry.createCircle(0.5), 1.0, 20.0, 0.1);
		character2.setMass(MassType.NORMAL);
		character2.translate(0.0, -2.0);
		character2.setUserData(CHARACTER);
		character2.setAtRestDetectionEnabled(false);
		this.world.addBody(character2);

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
			if (allowOneWayUp(b1, b2) || down.isActiveButNotHandled()) {
				down.setHasBeenHandled(true);
				contactConstraint.setEnabled(false);
			}
		}
		else if (is(b1, ONE_WAY_PLATFORM) && is(b2, CHARACTER)) {
			if (allowOneWayUp(b2, b1) || down.isActiveButNotHandled()) {
				down.setHasBeenHandled(true);
				contactConstraint.setEnabled(false);
			}
		}

		if (is(b1, CHARACTER) && is(b2, ONE_WAY_PLATFORM)) {
			if (allowOneWayUp(b1, b2) || s.isActiveButNotHandled()) {
				s.setHasBeenHandled(true);
				contactConstraint.setEnabled(false);
			}
		}
		else if (is(b1, ONE_WAY_PLATFORM) && is(b2, CHARACTER)) {
			if (allowOneWayUp(b2, b1) || s.isActiveButNotHandled()) {
				s.setHasBeenHandled(true);
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
		}
		else if (is(b1, FLOOR, ONE_WAY_PLATFORM) && is(b2, CHARACTER) && contactConstraint.isEnabled()) {
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
		if (this.left.isActive()) {
			character.applyTorque(Math.PI / 1);
		}
		if (this.right.isActive()) {
			character.applyTorque(-Math.PI / 1);
		}

		// Apply torque based on keyboard input for character 2
		if (this.a.isActive()) {
			character2.applyTorque(Math.PI / 1);
		}
		if (this.d.isActive()) {
			character2.applyTorque(-Math.PI / 1);
		}

		// Jumping mechanism for character 2 (W key)
		if (this.w.isActiveButNotHandled()) {
			this.w.setHasBeenHandled(true);
			if (this.onGround(character)) {
				// Apply an impulse in the upward direction
				character2.applyImpulse(new Vector2(0.0, 7));
				// Set onGround to false to prevent consecutive jumps until landing again
				onGround = false;
			}
		}

		// Jumping mechanism for character (Up arrow key)
		if (this.up.isActiveButNotHandled()) {
			this.up.setHasBeenHandled(true);
			if (this.onGround(character2)) {
				// Apply an impulse in the upward direction
				character.applyImpulse(new Vector2(0.0, 7));
				// Set onGround to false to prevent consecutive jumps until landing again
				onGround = false;
			}
		}

		// Update character color based on whether it's on the ground or not
		character.setColor(onGround(character) ? WHEEL_ON_COLOR : WHEEL_OFF_COLOR);
		character2.setColor(onGround(character2) ? WHEEL_ON_COLOR : WHEEL_OFF_COLOR);
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

	/**
	 * Entry point for the example application.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		Platformer simulation = new Platformer();
		simulation.run();
	}
}