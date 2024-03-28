import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

public class MainMenu {

	private static final int WINDOW_X = 1024;
	private static final int WINDOW_Y = 768;
	private static final int BUTTON_WIDTH = 200;
	private static final int BUTTON_HEIGHT = 50;
	private JFrame frame;
	
	public MainMenu() {
		window();
		playButton();
		settingsButton();
		quitButton();
	}
	
	private void window() {
		
		JLabel title = new JLabel();
		title.setText("fruitless conflict");
		title.setFont(new Font("Arial" ,Font.PLAIN, 50));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);
		title.setBounds(0, 0, WINDOW_X, WINDOW_Y/2);
		
		frame = new JFrame();
		frame.setTitle("Fruitless Conflict");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(WINDOW_X, WINDOW_Y);
		frame.setLayout(null);
		
		ImageIcon logo = new ImageIcon("images/logo.png");
		frame.setIconImage(logo.getImage());
		
		frame.add(title);	
		frame.setVisible(true);
		
	}
	
	private void playButton() {
		JButton play = new JButton();
		play.setText("play");
		play.setFont(new Font("Arial", Font.PLAIN, 20));
		play.setBackground(Color.white);
		play.setBorder(BorderFactory.createLineBorder(Color.red));
		play.setFocusable(false);
		play.setBounds(WINDOW_X/2 - BUTTON_WIDTH/2, WINDOW_Y/2 - BUTTON_HEIGHT/2, BUTTON_WIDTH, BUTTON_HEIGHT);
		frame.add(play);
	}
	
	private void settingsButton() {
		JButton settings = new JButton();
		settings.setText("settings");
		settings.setFont(new Font("Arial", Font.PLAIN, 20));
		settings.setBackground(Color.white);
		settings.setBorder(BorderFactory.createLineBorder(Color.red));
		settings.setFocusable(false);
		settings.setBounds(WINDOW_X/2 - BUTTON_WIDTH/2, WINDOW_Y/2 + BUTTON_HEIGHT - 20, BUTTON_WIDTH, BUTTON_HEIGHT);
		frame.add(settings);
	}
	
	private void quitButton() {
		JButton exit = new JButton();
		exit.setText("quit");
		exit.setFont(new Font("Arial", Font.PLAIN, 20));
		exit.setBackground(Color.white);
		exit.setBorder(BorderFactory.createLineBorder(Color.red));
		exit.setFocusable(false);
		exit.setBounds(WINDOW_X/2 - BUTTON_WIDTH/2, WINDOW_Y/2 + BUTTON_HEIGHT*2 - 15, BUTTON_WIDTH, BUTTON_HEIGHT);
		frame.add(exit);	
	}
	
	public static void main(String[] args) {
		new MainMenu();
	}
}
