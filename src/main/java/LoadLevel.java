import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.samples.framework.SimulationBody;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

//store or load simulation bodies to load into a world.

public class LoadLevel extends Platformer {

	// test to create an object from a string
	// format: SHAPE; SIZE X; SIZE Y; MASS; TRANSLATE X; TRANSLATE Y; USER DATA
	private static String testLevelString = "RECTANGLE;50.0;0.2;INFINITE;0;-3;FLOOR";
	private static String testLevelString2 = "RECTANGLE;10.0;0.2;INFINITE;0;0;ONE_WAY_PLATFORM";

	//private static final Object FLOOR = new Object();
	//private static final Object ONE_WAY_PLATFORM = new Object();

	private int length;
	private ArrayList<String> levelLoadText = new ArrayList<String>();

	public LoadLevel(String levelName) {
		try {
	        File levelFile = new File("maps/"+levelName + ".txt");
	        Scanner myReader = new Scanner(levelFile);
	        while (myReader.hasNextLine()) {
	         levelLoadText.add(myReader.nextLine());
	        }
	        myReader.close();
	      } 
	    catch (FileNotFoundException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	    }
		
		length = levelLoadText.size();
	}

	public SimulationBody loadMap(int val) {
		
		String currentLoad = levelLoadText.get(val); 
		return stringtoSimBody(currentLoad);
	}

	// takes a set of parameters in a string and create a simulation body from it
	private SimulationBody stringtoSimBody(String inputStr) {
		String[] elementsArr = inputStr.split(";", 0);
		int arraySize = elementsArr.length;

		SimulationBody tempBody = new SimulationBody();
		tempBody.addFixture(
				Geometry.createRectangle(Double.parseDouble(elementsArr[1]), Double.parseDouble(elementsArr[2])));
		tempBody.setMass(MassType.INFINITE);
		tempBody.translate(Double.parseDouble(elementsArr[4]), Double.parseDouble(elementsArr[5]));

		switch (elementsArr[arraySize - 1]) {
		case "FLOOR":
			tempBody.setUserData(FLOOR);
		case "ONE_WAY_PLATFORM":
			tempBody.setUserData(ONE_WAY_PLATFORM);
		default:
		}
		return tempBody;

	}

	@Override
	protected void initializeWorld() {
		// TODO Auto-generated method stub

	}

	public int getLength() {
		return length;
	}

}
