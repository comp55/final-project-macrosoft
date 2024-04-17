import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;
import org.dyn4j.samples.framework.SimulationBody;
import org.dyn4j.samples.Images;

//Current testing for images
//-Importing over simulate frame and simulate body similar to the image class does not work in tests
//-Trying to make a new image class for the player to grab does not work because the body won't move with the image
//-Current plan is to try and change either platformer or this class so that i can follow the framework set by the image test

public class Player extends Platformer{
	// declarations
	private SimulationBody character;
	private String playerControls;
	private String playerIcon;
	private double startX;
	private double startY;
	private Color playerColor;
	private int lives;
	private Boolean isOut = true;
	
	//new
	/*
	private static final BufferedImage TOMATO = getImageSuppressExceptions("images/tomatoPlayer.PNG");
	private static final BufferedImage getImageSuppressExceptions(String pathOnClasspath) {
		try {
			return ImageIO.read(Images.class.getResource(pathOnClasspath));
		} catch (IOException e) {
			return null;
		}
	}*/


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
		
		//character.render(TOMATO, lives);
		
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
	
	//possible solution? need to edit and troll through code, chat gbt suggestion
	/*
	    protected void render(Graphics g) {
	        super.paintComponent(g);
	        Graphics2D g2d = (Graphics2D) g;

	        if (playerImage != null && character != null) {
	            // Draw player image at the position of the character body
	            double x = character.getTransform().getTranslationX();
	            double y = character.getTransform().getTranslationY();
	            g2d.drawImage(playerImage, (int) x, (int) y, null);
	        }
	    }
	
	*/
	
	protected void renderFixture(Graphics2D g, double scale, BodyFixture fixture) {
		Convex convex = fixture.getShape();
		Circle c = (Circle) convex;
		double r = c.getRadius();
		Vector2 cc = c.getCenter();
		int x = (int)Math.ceil((cc.x - r) * scale);
		int y = (int)Math.ceil((cc.y - r) * scale);
		int w = (int)Math.ceil(r * 2 * scale);
			// lets us an image instead
			g.drawImage(TOMATO, x, y, w, w, null);
	}
	
	private final class ImageBody extends SimulationBody {
		/** The image to use, if required */
		private final BufferedImage image;
		
		public ImageBody(BufferedImage image) {
			this.image = image;
		}
		
		/* (non-Javadoc)
		 * @see org.dyn4j.samples.SimulationBody#renderFixture(java.awt.Graphics2D, double, org.dyn4j.dynamics.BodyFixture, java.awt.Color)
		 */
		@Override
		protected void renderFixture(Graphics2D g, double scale, BodyFixture fixture, Color color) {
			// do we need to render an image?
			if (this.image != null) {
				// get the shape on the fixture
				Convex convex = fixture.getShape();
				// check the shape type
				if (convex instanceof Circle) {
					// cast the shape to get the radius
					Circle c = (Circle) convex;
					double r = c.getRadius();
					Vector2 cc = c.getCenter();
					int x = (int)Math.ceil((cc.x - r) * scale);
					int y = (int)Math.ceil((cc.y - r) * scale);
					int w = (int)Math.ceil(r * 2 * scale);
						// lets us an image instead
						g.drawImage(this.image, x, y, w, w, null);
				}
			} else {
				// default rendering
				super.renderFixture(g, scale, fixture, color);
			}
		}
	}
	
	

	

	// start working on powerups changing abilites
	// players speed needs to be determined in the map

}
