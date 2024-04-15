// example for setting up a level
public class GameStartTest {
	public static void main(String[] args) {
		Platformer simulation = new Platformer();
		simulation.setMap("map2");
		simulation.setNumPlayers(2);
		simulation.setStartingScore(0);
		simulation.run();
	}
}