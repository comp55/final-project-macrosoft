import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.samples.framework.SimulationBody;

//store or load simulation bodies to load into a world.

public class LoadLevel extends Platformer {

	private SimulationBody LevelElement;

	// test to create an object from a string
	// format: SHAPE; SIZE X; SIZE Y; MASS; TRANSLATE X; TRANSLATE Y; USER DATA
	private static String testLevelString = "RECTANGLE;50.0;0.2;INFINITE;0;-3;FLOOR";
	private static String testLevelString2 = "RECTANGLE;10.0;0.2;INFINITE;0;0;ONE_WAY_PLATFORM";

	private static final Object FLOOR = new Object();
	private static final Object ONE_WAY_PLATFORM = new Object();

	public SimulationBody loadMap(int val) {
		stringtoSimBody(testLevelString);

		SimulationBody returnVal = null;

		switch (val) {
		case 1: // floor
			// SimulationBody floor = new SimulationBody();
			// floor.addFixture(Geometry.createRectangle(50.0, 0.2));
			// floor.setMass(MassType.INFINITE);
			// floor.translate(0, -3);
			// floor.setUserData(FLOOR);
			// return floor;

			return stringtoSimBody(testLevelString);

		case 2: // platform
			return stringtoSimBody(testLevelString2);

		case 3: // right bound
			SimulationBody right = new SimulationBody();
			right.addFixture(Geometry.createRectangle(0.2, 20));
			right.setMass(MassType.INFINITE);
			right.translate(10, 7);
			return right;

		case 4: // left bound
			SimulationBody left = new SimulationBody();
			left.addFixture(Geometry.createRectangle(0.2, 20));
			left.setMass(MassType.INFINITE);
			left.translate(-10, 7);
			return left;
		}
		return returnVal;
	}

	// takes a set of parameters in a string and create a simulation body from it
	private SimulationBody stringtoSimBody(String inputStr) {
		String[] elementsArr = inputStr.split(";", 0);
		int arraySize = elementsArr.length;
		
		SimulationBody tempBody = new SimulationBody();
		tempBody.addFixture(Geometry.createRectangle(Double.parseDouble(elementsArr[1]), Double.parseDouble(elementsArr[2])));
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

}
