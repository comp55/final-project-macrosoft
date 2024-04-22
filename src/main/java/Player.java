import java.awt.Color;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;



//Current testing for images
//-Importing over simulate frame and simulate body similar to the image class does not work in tests
//-Trying to make a new image class for the player to grab does not work because the body won't move with the image
//-Current plan is to try and change either platformer or this class so that i can follow the framework set by the image test

public class Player extends Platformer{
	// declarations
	private ImageBody character;
	private String playerControls;
	private String playerIcon;
	private double startX;
	private double startY;
	private Color playerColor;
	private int lives;
	private Boolean isOut = true;
	
	private String TOMATO = "tomatoPlayer2.png";
	



	public Player(int x, int y, int startingScore, Color color) {
		startX = x;
		startY = y;
		playerColor = color;
		lives = startingScore;
	}

	// this should take in the players icon, and whatever control pattern they want.
	// once thats done create the actual body but don't place it.
	// characters need to be placed in the play class
	public ImageBody createPlayer() {

		ImageBody character = new ImageBody(TOMATO);
		
		// needs too be
		//character = new SimulationBody(playerColor);
		this.isOut = false;
		// TODO keep this the same but make sure you can edit these settings
		//character.addFixture(Geometry.createCircle(0.5), 1.0, 200.0, 0.1);
		character.addFixture(Geometry.createCircle(0.7), 0.5, 2000.0, 0.3);
		character.setMass(MassType.NORMAL);
		character.translate(startX, startY);
		character.setUserData(CHARACTER);
		character.setAtRestDetectionEnabled(false);
		
		//ImageBody character = new ImageBody(TOMATO);
		//character.image = TOMATO;
		//character.addFixture(Geometry.createCircle(0.5), 1, 0.2, 0.5);
		//character.setMass(MassType.NORMAL);
		//character.translate(2.0, 2.0);
		// test adding some force
		//character.applyForce(new Vector2(-100.0, 0.0));
		// set some linear damping to simulate rolling friction
		//character.setLinearDamping(0.05);
		
		return character;

	}
	
	
	// These can also be use in case I find its easier to store player movements on
	// the map page, which might need to happen
	// current testing has concluded that I can't have everything a player would
	// need stored in the player class
	// map class needs to be used
	public String getPlayerIcon() {
		return playerIcon;
	}

	public void setPlayerIcon(String playerIcon) {
		this.playerIcon = playerIcon;
	}

	public String getPlayerControls() {
		return playerControls;
	}
	
	public Color getplayerColor() {
		return playerColor;
	}
	
	public void setPlayerControls(String playerControls) {
		this.playerControls = playerControls;
	}
	
	//TODO maybe delete?
	public void resetOnDeath() {
		character.translate(100, 100);
	}
	
	public int getLives() {
		return lives;
	}

	public void subtractLives(int s) {
		lives = lives - s;
	}

	// This is much more complicated than i thought, see dyn4j samples images for
	// more details
	public void SetPlayerCharacterImage(String playerImage) {

	}

	// needs to be here for listeners testing
	@Override
	protected void initializeWorld() {
		// TODO Auto-generated method stub

	}

	public Boolean isOut() {
		return isOut;
	}

	public void setOut(Boolean isOut) {
		this.isOut = isOut;
	}

}
