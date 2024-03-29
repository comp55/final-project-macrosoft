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
//Remember always check to see if one of these is missing

//charlie wuz here
public class Player {
	//declarations
	private SimulationBody character;
	
	public Player() {
		//the wheel
		
		//TODO change this so that instead of pushing a color it pushes what image it needs too be
		//character = new SimulationBody(WHEEL_OFF_COLOR);
		
		//TODO keep this the same but make sure you can edit these settings
		character.addFixture(Geometry.createCircle(0.5), 1.0, 20.0, 0.1);
		character.setMass(MassType.NORMAL);
		character.translate(0.0, -2.0);
		//character.setUserData(CHARACTER);
		character.setAtRestDetectionEnabled(false);
		
	}
	
}
