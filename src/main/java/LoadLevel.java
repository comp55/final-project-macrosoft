import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.samples.framework.SimulationBody;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

//store or load simulation bodies to load into a world.

public class LoadLevel extends Platformer {

	// current map format: SHAPE; SIZE X; SIZE Y; TRANSLATE X; TRANSLATE Y;
	// ROTATION; MASS; USER DATA
	// any LINE starting with a # is treated as a comment

	private int length;
	private ArrayList<String> levelLoadText = new ArrayList<String>();
	private String mapVersion;
	private String bgMapOptions;
	private String miscMapOptions;
	private String tempLine;
	private double rotation;

	public LoadLevel(String levelName) {
		try {
			File levelFile = new File("maps/" + levelName + ".txt");
			Scanner myReader = new Scanner(levelFile);
			while (myReader.hasNextLine()) {
				tempLine = myReader.nextLine();

				switch (tempLine.charAt(0)) {
				case '#':
					break;
				case '$':
					bgMapOptions = tempLine;
					break;
				case '@':
					miscMapOptions = tempLine;
					break;
				case '!':
					mapVersion = tempLine;
					break;
				default:
					levelLoadText.add(tempLine);
				}

			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		length = levelLoadText.size();

	}

	public ImageBody loadMapIMG(String imgS, int bodyX, int bodyY, int imgX, int imgY, int imgW, int imgH) {

		ImageBody img = new ImageBody(imgS);
		img.addFixture(Geometry.createCircle(0.00001), 0, 0, 0);
		img.setMass(MassType.INFINITE);
		img.translate(bodyX, bodyY);
		img.setCustomImageStatus(true);
		img.setImageOptions(imgX, imgY, imgW, imgH);
		return img;

	}

	public SimulationBody loadMap(int val) {

		String currentLoad = levelLoadText.get(val);
		return stringtoSimBody(currentLoad);
	}
	
	public ImageBody loadBG() {
		ImageBody mapBG = stringBGSetup(bgMapOptions);
		return mapBG;
	}
	
	private ImageBody stringBGSetup(String inputStr) {
		//$"plateMap.png";0;3;400;400;800;800
		inputStr = inputStr.replace("$", "");
		String[] bgArr = inputStr.split(";", 0);
		
		ImageBody mapBG = loadMapIMG(bgArr[0], Integer.parseInt(bgArr[1]), Integer.parseInt(bgArr[2]), Integer.parseInt(bgArr[3]), Integer.parseInt(bgArr[4]), Integer.parseInt(bgArr[5]), Integer.parseInt(bgArr[6]));
		return mapBG;
		
		
	}

	// takes a set of parameters in a string and create a simulation body from it
	private SimulationBody stringtoSimBody(String inputStr) {
		String[] elementsArr = inputStr.split(";", 0);
		int arraySize = elementsArr.length;

		SimulationBody tempBody = new SimulationBody();

		// to do allow multiple shapes
		switch (elementsArr[0]) {
		case "RECTANGLE":
			tempBody.addFixture(
					Geometry.createRectangle(Double.parseDouble(elementsArr[1]), Double.parseDouble(elementsArr[2])));
			break;
		case "ELLIPSE":
			tempBody.addFixture(
					Geometry.createEllipse(Double.parseDouble(elementsArr[1]), Double.parseDouble(elementsArr[2])));
			break;
		case "HALF-ELLIPSE":
			tempBody.addFixture(
					Geometry.createHalfEllipse(Double.parseDouble(elementsArr[1]), Double.parseDouble(elementsArr[2])));
			break;
		case "TRIANGLE":
			tempBody.addFixture(Geometry.createIsoscelesTriangle(Double.parseDouble(elementsArr[1]),
					Double.parseDouble(elementsArr[2])));
			break;
		default:
			break;
		}

		// to do allow multiple mass types
		tempBody.setMass(MassType.INFINITE);

		tempBody.translate(Double.parseDouble(elementsArr[3]), Double.parseDouble(elementsArr[4]));

		tempBody.rotateAboutCenter(Double.parseDouble(elementsArr[5]));

		switch (elementsArr[arraySize - 1]) {
		case "FLOOR":
			tempBody.setUserData(FLOOR);
			tempBody.setColor(Color.DARK_GRAY);
			break;
		case "ONE_WAY_PLATFORM":
			tempBody.setUserData(ONE_WAY_PLATFORM);
			tempBody.setColor(Color.blue);
			break;
		case "SCORE_ZONE":
			tempBody.setUserData(SCORE_ZONE);
			tempBody.setColor(Color.orange);
			break;
		default:
		}
		return tempBody;

	}

	public int getLength() {
		return length;
	}

}
