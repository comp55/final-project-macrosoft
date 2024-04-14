import java.awt.Color;

import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.samples.framework.SimulationBody;

//Remember always check to see if one of these is missing

public class Player extends Platformer {
	// declarations
	private SimulationBody character;
	private String playerControls;
	private String playerIcon;
	private double startX;
	private double startY;
	private Color playerColor;
	private int lives;
	private Boolean isOut = true;


	public Player(int x, int y, int startingScore, Color color) {
		startX = x;
		startY = y;
		playerColor = color;
		lives = startingScore;
	}

	// this should take in the players icon, and whatever control pattern they want.
	// once thats done create the actual body but don't place it.
	// characters need to be placed in the play class
	public SimulationBody createPlayer() {

		// TODO change this so that instead of pushing a color it pushes what image it
		// needs too be
		character = new SimulationBody(playerColor);
		this.isOut = false;
		// TODO keep this the same but make sure you can edit these settings
		character.addFixture(Geometry.createCircle(0.5), 1.0, 200.0, 0.1);
		character.setMass(MassType.NORMAL);
		character.translate(startX, startY);
		character.setUserData(CHARACTER);
		character.setAtRestDetectionEnabled(false);
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
	
	public void resetOnDeath() {
		character.translate(100, 100);
		//character.
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

	

	// start working on powerups changing abilites
	// players speed needs to be determined in the map

}
