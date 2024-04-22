// example for setting up a level
public class GameStartTest {
	public static void main(String[] args) {
		Platformer simulation = new Platformer();
		simulation.setPlayerIMG("t", "a", "o", "w");
		simulation.setMap("map2");
		simulation.setNumPlayers(4);
		simulation.setStartingScore(2);
		simulation.run();
	}
}