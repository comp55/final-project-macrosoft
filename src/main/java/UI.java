import java.util.ArrayList;

import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;


//NOT FINISHED
//Planned to manage a graphical representation of player lives
public class UI extends Platformer {
	
	private int playerNum;
	private int numLives;
	private String img;
	private ImageBody uiMain;
	private ImageBody l1;
	private ImageBody l2;
	private ImageBody l3;
	
	private final int originX = -18;
	private final int originY = -10;
	private final int playerOffset = 7;
	private final int Xoffset = 2;
	private final int Yoffset = -1;
	
	private static final String TOMATO = "tomatoPlayer.png";
	private static final String ORANGE = "orangePlayer.png";
	private static final String APPLE = "applePlayer.png";
	private static final String WATERMELON = "watermelonPlayer.png";
	
	private ArrayList<ImageBody> uiElements = new ArrayList<ImageBody>();
	
	public UI(int pNum, int lives, String i) {
		playerNum = pNum;
		numLives = lives;
		img = identifyPlayerChar(i);
		initUIarray(numLives);
		
		uiMain = new ImageBody(img);
		l1 = new ImageBody(img);
		l2 = new ImageBody(img);
		l3 = new ImageBody(img);
		
	}
	
	private void initUIarray(int numL) {
		for (int i = 0; i <= numL; i++) {
			uiElements.add(createUI(i));
		}
	}
	
	public ArrayList<ImageBody> getUIarray(){
		return uiElements;
	}
	
	
	

	public ImageBody createUI(int index) {
		ImageBody temp = new ImageBody(img);
		
		int homeX = (playerNum - 1) * playerOffset;
		
	
		int xstart = originX + homeX;
		int ystart = originY;
		int x = xstart;
		int y = ystart;
		int scale = 1;;
		
		switch (index){
		case 0:
			x = xstart;
			y = ystart;
			scale = 2;
			break;
		case 1:
			x = xstart + Xoffset;
			y = ystart + Yoffset;
			break;
		case 2:
			x = xstart + Xoffset *2;
			y = ystart + Yoffset;
			break;
		case 3:
			x = xstart + Xoffset *3;
			y = ystart + Yoffset;
			break;
		default:
			break;
		
		}
		
		temp.addFixture(Geometry.createCircle(0.00001), 0, 0, 0);
		temp.setMass(MassType.INFINITE);
		temp.translate(x, y);
		temp.setCustomImageStatus(true);
		temp.setImageOptions(-15 * scale, 15 * scale, 50 * scale, -40 * scale);
		return temp;

	}
	
	private String identifyPlayerChar(String selectedChar) {
		switch (selectedChar) {
		case ("a"):
			return APPLE;
		case ("t"):
			return TOMATO;
		case ("o"):
			return ORANGE;
		case ("w"):
			return WATERMELON;
		default:
			return APPLE;
		}
	}
	
}

