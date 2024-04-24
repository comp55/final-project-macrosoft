import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Vector2;
import org.dyn4j.samples.framework.SimulationBody;

public final class ImageBody extends SimulationBody {
	/** The image to use, if required */

	private double scaleMult;
	private int imageX;
	private int imageY;
	private int imageW;
	private int imageH;
	private Boolean customImg;

	private BufferedImage image;

	public ImageBody(String imagePath) {
		this.image = getImageSuppressExceptions("/imagesG/" + imagePath);
		customImg = false;
	}

	public static final BufferedImage getImageSuppressExceptions(String pathOnClasspath) {
		try {
			return ImageIO.read(ImageBody.class.getResource(pathOnClasspath));
		} catch (IOException e) {
			return null;
		}
	}

	public void setImageOptions(int x, int y, int w, int h) {
		imageX = x;
		imageY = y;
		imageW = w;
		imageH = h;

	}
	
	public void setCustomImageStatus(Boolean b) {
		customImg = b;
	}
	
	public Boolean getCustomImageStatus() {
		return customImg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dyn4j.samples.SimulationBody#renderFixture(java.awt.Graphics2D,
	 * double, org.dyn4j.dynamics.BodyFixture, java.awt.Color)
	 */
	@Override
	protected void renderFixture(Graphics2D g, double scale, BodyFixture fixture, Color color) {
		// do we need to render an image?
		if (this.image != null) {
			// get the shape on the fixture

			if (this.customImg) {
				g.drawImage(this.image, imageX, imageY, imageW, imageH, null);

			}
			else {
				Convex convex = fixture.getShape();
				// check the shape type
				if (convex instanceof Circle) {
					// cast the shape to get the radius
					Circle c = (Circle) convex;
					double r = c.getRadius();
					Vector2 cc = c.getCenter();
					// TODO make the numbers a constant
					int x = (int) Math.ceil((cc.x - r) * 20);
					int y = (int) Math.ceil((cc.y - r) * 20);
					int w = (int) Math.ceil(r * 2 * (22));
					// lets us an image instead
					g.drawImage(this.image, x, y, w + 25, w + 10, null);
				}
			}
		}

		else {
			// default rendering
			super.renderFixture(g, scale, fixture, color);
		}
	}
}