import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.samples.framework.SimulationBody;
import org.dyn4j.samples.framework.SimulationFrame;

//store or load simulation bodies to load into a world.

public class LoadLevel extends Platformer {


	private SimulationBody LevelElement;
	
	private static final Object CHARACTER = new Object();
	private static final Object FLOOR = new Object();
	private static final Object ONE_WAY_PLATFORM = new Object();

	public SimulationBody loadMap(int val) {
		SimulationBody returnVal = null;
		
		switch(val) {
		case 1: //floor
			SimulationBody floor = new SimulationBody();
			floor.addFixture(Geometry.createRectangle(50.0, 0.2));
			floor.setMass(MassType.INFINITE);
			floor.translate(0, -3);
			floor.setUserData(FLOOR);
			return floor;
			
		case 2: //platform
			SimulationBody platform = new SimulationBody();
			platform.addFixture(Geometry.createRectangle(10.0, 0.2));
			platform.setMass(MassType.INFINITE);
			platform.translate(0, 0);
			platform.setUserData(ONE_WAY_PLATFORM);
			return platform;

		case 3: //right bound
			SimulationBody right = new SimulationBody();
			right.addFixture(Geometry.createRectangle(0.2, 20));
			right.setMass(MassType.INFINITE);
			right.translate(10, 7);
			return right;

		case 4: //left bound
			SimulationBody left = new SimulationBody();
			left.addFixture(Geometry.createRectangle(0.2, 20));
			left.setMass(MassType.INFINITE);
			left.translate(-10, 7);
			return left;
		}

		return returnVal;
	}

	@Override
	protected void initializeWorld() {
		// TODO Auto-generated method stub

	}
}
