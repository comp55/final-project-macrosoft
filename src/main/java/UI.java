import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;

public class UI extends Platformer {
	
	private int playerNum;
	private String img;
	private ImageBody uiMain;
	private ImageBody l1;
	private ImageBody l2;
	private ImageBody l3;
	
	private final int originX = -18;
	private final int originY = -10;
	private final int playerOffset = 5;
	private final int Xoffset = 2;
	private final int Yoffset = -1;
	
	private static final String TOMATO = "tomatoPlayer.png";
	private static final String ORANGE = "orangePlayer.png";
	private static final String APPLE = "applePlayer.png";
	private static final String WATERMELON = "watermelonPlayer.png";
	
	public UI(int p, String i) {
		playerNum = p;
		img = identifyPlayerChar(i);
		
		uiMain = new ImageBody(img);
		l1 = new ImageBody(img);
		l2 = new ImageBody(img);
		l3 = new ImageBody(img);
		
	}

	public ImageBody createUI(int index) {
		ImageBody temp = null;
		
		int x = originX;
		int y = originY;
		int scale;
		
		switch (index){
		case 0:
			temp = uiMain;
			x = originX;
			y = originY;
			break;
		case 1:
			temp = l1;
			x = originX + Xoffset;
			y = originY + Yoffset;
			break;
		case 2:
			temp = l2;
			x = originX + Xoffset *2;
			y = originY + Yoffset;
			break;
		case 3:
			temp = l3;
			x = originX + Xoffset *3;
			y = originY + Yoffset;
			break;
		default:
			break;
		
		}
		
		temp.addFixture(Geometry.createCircle(0.00001), 0, 0, 0);
		temp.setMass(MassType.INFINITE);
		temp.translate(x, y);
		temp.setCustomImageStatus(true);
		temp.setImageOptions(0, 0, 40, 40);
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

