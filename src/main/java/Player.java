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
public class Player extends SimulationFrame {
	//declarations
	private static final Object CHARACTER = new Object();
	private SimulationBody character;
	private String playerControls;
	private String playerIcon;
	
	private final BooleanStateKeyboardInputHandler up;
	private final BooleanStateKeyboardInputHandler down;
	private final BooleanStateKeyboardInputHandler left;
	private final BooleanStateKeyboardInputHandler right;
	
	private final BooleanStateKeyboardInputHandler w;
	private final BooleanStateKeyboardInputHandler s;
	private final BooleanStateKeyboardInputHandler a;
	private final BooleanStateKeyboardInputHandler d;
	
	//this should take in the players icon, and whatever control pattern they want. once thats done create the actual body but don't place it. 
	//characters need to be placed in the play class
	public Player(String control, String icon) {
		super("Player");
		
		setPlayerControls(control);
		setPlayerIcon(icon);
		
		
		this.up = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_UP);
		this.down = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_DOWN);
		this.left = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_LEFT);
		this.right = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_RIGHT);
		
		this.w = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_W);
		this.s = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_S);
		this.a = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_A);
		this.d = new BooleanStateKeyboardInputHandler(this.canvas, KeyEvent.VK_D);
		
		this.up.install();
		this.down.install();
		this.left.install();
		this.right.install();
		
		this.w.install();
		this.s.install();
		this.a.install();
		this.d.install();
		
		//TODO change this so that instead of pushing a color it pushes what image it needs too be
		character = new SimulationBody();
		
		//TODO keep this the same but make sure you can edit these settings
		character.addFixture(Geometry.createCircle(0.5), 1.0, 20.0, 0.1);
		character.setMass(MassType.NORMAL);
		character.translate(0.0, -2.0);
		character.setUserData(CHARACTER);
		character.setAtRestDetectionEnabled(false);
		
	}
	
	//These can also be use in case I find its easier to store player movements on the map page, which might need to happen
	//current testing has concluded that I can't have everything a player would need stored in the player class
	//map class needs to be used 
	public String getPlayerIcon() {
		return playerIcon;
	}

	public void setPlayerIcon(String playerIcon) {
		this.playerIcon = playerIcon;
	}

	public String getPlayerControls() {
		return playerControls;
	}

	public void setPlayerControls(String playerControls) {
		this.playerControls = playerControls;
	}
	
	//This is much more complicated than i thought, see  dyn4j samples images for more details
	public void SetPlayerCharacterImage() {
		
	}

	//needs to be here for listeners testing
	@Override
	protected void initializeWorld() {
		// TODO Auto-generated method stub
		
	}
	
	//start working on powerups changing abilites
	//players speed needs to be determined in the map
	
}
