import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import java.awt.event.ActionListener;
import java.awt.Window;

import javax.swing.JComboBox;

import acm.graphics.*;
import acm.program.*;

public class Freeplay extends GraphicsProgram {
    //Static
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;
    public static final int BUTTON_WIDTH = 150;
    public static final int BUTTON_HEIGHT = 150;
	private static final ActionEvent ActionEvent = null;
    
    //Lists
    private List<GRect> buttons;
    private List<GLabel> buttonTexts;
    private List<GRect> playerButtons;
    private List<GRect> playerSelection;
    private List<GRect> numOfPlayers;
    
    //Other
    GRect backButton;
    private JComboBox<String> playerDropdown;
    private JComboBox<String> playerControles;
    private int highlightedButtonIndex = -1; // Index of the button being highlighted
    public int mapSelected = -1;
    MainMenu mainMenu;
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    private Map<GRect, String> selectedCharacters;
    private Map<GRect, String> selectedKeybinds;
    private String[] characters = {"orange", "red", "sqaureMelon", "tomato", "watermelon"};
    private String[] keybinds = {"WASD", "YGHJ", "OKL;", "Arrow Keys"};
    private String[] playerOptions = {"1 Player", "2 Players", "3 Players", "4 Players"};

    //Init the program
    public void init() {
    	//setting the size of the window
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        
        //initializing arrays
        buttons = new ArrayList<>();
        buttonTexts = new ArrayList<>();
        playerButtons = new ArrayList<>();
        playerSelection = new ArrayList<>();
        numOfPlayers = new ArrayList<>();
        selectedCharacters = new HashMap<>();
        selectedKeybinds = new HashMap<>();
        UpdateNumPlayers(1);
        
        //Creating number of players
        playerDropdown = new JComboBox<>(playerOptions);
        playerDropdown.setFont(new Font("Arial", Font.PLAIN, 20));
        playerDropdown.addActionListener(e -> {
            JComboBox<String> cb = (JComboBox<String>) e.getSource();
            String selectedPlayerOption = (String) cb.getSelectedItem();
            // Extracting the number of players from the selected option
            int selectedNumPlayers = Integer.parseInt(selectedPlayerOption.split(" ")[0]);
            UpdateNumPlayers(selectedNumPlayers);
        });
        
        // Add the JComboBox to a GCanvas
        playerDropdown.setPreferredSize(new Dimension(150, 30));

        // Add the JComboBox to the canvas
        GCanvas canvas = getGCanvas();
        canvas.add((Component) playerDropdown, WINDOW_WIDTH / 10 - 40, WINDOW_HEIGHT / 2);
        
        //Create Back Button
        backButton = new GRect(WINDOW_WIDTH / 1.2, WINDOW_HEIGHT / 50, 200, 75);
        backButton.setFilled(false);
        add(backButton);
        GLabel backButtonText = new GLabel("Back");
        backButtonText.setFont("Arial-20");
        add(backButtonText, WINDOW_WIDTH / 1.12, WINDOW_HEIGHT / 13);
        
        
        // Create buttons
        for (int i = 0; i < 5; i++) { 
            GRect button = new GRect(100 + i * 200, (WINDOW_HEIGHT - BUTTON_HEIGHT) / 5, BUTTON_WIDTH, BUTTON_HEIGHT);
            button.setFilled(false);
            add(button);
            buttons.add(button);
            
            GLabel buttonText = new GLabel("Map " + (i + 1));
            buttonText.setFont("Arial-20");
            double y = (WINDOW_HEIGHT - buttonText.getAscent()) / 5;
            double x = 100 + i * 200 + (BUTTON_WIDTH + buttonText.getWidth()) / 5;
            add(buttonText, x, y);
            buttonTexts.add(buttonText);
        }

        addMouseListeners();
        addKeyListeners();
    }
    
    //updates the screen to show new number of players
    private void UpdateNumPlayers(int numOfPlayers) {
    	System.out.println("Called");
    	
    	
        for (GRect playButton : playerButtons) {
            remove(playButton);
        }
        
        playerButtons.clear();
        // Create buttons for each player
        for (int i = 0; i < numOfPlayers; i++) {
        	GRect playerButton = new GRect(100 + i * 200, (WINDOW_HEIGHT - BUTTON_HEIGHT) / 1.3, BUTTON_WIDTH, BUTTON_HEIGHT);
        	playerButton.setFilled(false);
        	add(playerButton);
        	playerButtons.add(playerButton);
        }
    }
    
    // Character Selection Menu
    private void CharacterSelectMenu(/*GRect playerButton*/) {
        // If the character is already selected for this player, do not open character selection menu
        /*if (selectedCharacters.containsKey(playerButton)) {
            return;
        }*/
        
        //Making the background
        GRect backGround = new GRect(250, 100, 800, 500);
        backGround.setFilled(true);
        backGround.setFillColor(Color.cyan);
        add(backGround);
        
        //Making the Character Select Images and Buttons
        double x = 300;
        for (int i = 0; i < characters.length; i++) {
        	System.out.println("images/" + characters[i] + "Player.PNG");
            GImage characterImage = new GImage("images/" + characters[i] + "Player.PNG", x, 130);
            characterImage.setSize(160, 120);
            add(characterImage);
            
            GRect charButton = new GRect(x + 15, 130, BUTTON_WIDTH / 1.2, BUTTON_HEIGHT / 1.2);
            charButton.setFilled(false);
            //charButton.addMouseListener(new CharacterSelectListener(playerButton, characters[i]));
            add(charButton);
            
            x += 130;
        }
        
        //Making the character control Buttons
       

        playerControles = new JComboBox<>(keybinds);
        playerControles.setFont(new Font("Arial", Font.PLAIN, 20));
        playerControles.addActionListener(e -> {
            JComboBox<String> cb = (JComboBox<String>) e.getSource();
            String selectedPlayerKeybind = (String) cb.getSelectedItem();
            //Extracting the number of players from the selected option
            int selectedKeybind = Integer.parseInt(selectedPlayerKeybind.split(" ")[0]);
        });
       
       playerControles.setPreferredSize(new Dimension(150, 30));
        
        GCanvas canvas = getGCanvas();
        canvas.add((Component) playerControles, WINDOW_WIDTH / 10 - 70, WINDOW_HEIGHT / 3);        
    }
    
	public static void main(String[] args) {
        new Freeplay().start();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (int i = 0; i < buttons.size(); i++) {
            GRect button = buttons.get(i);
            if (isWithinButtonBounds(button, e.getX(), e.getY())) {
                // Reset all buttons
            	mapSelected = i;
                resetButtons();
                // Set the clicked button to pressed state
                button.setFilled(true);
                button.setFillColor(Color.GREEN);
                // Exit the loop since we found the clicked button
                break;
            }
        }
        
        for (int i = 0; i < playerButtons.size(); i++) {
            GRect playButton = playerButtons.get(i);
            if (isWithinButtonBounds(playButton, e.getX(), e.getY())) {
                CharacterSelectMenu();
                // Exit the loop since we found the clicked button
                break;
            }
        }
        
        if(isWithinButtonBounds(backButton, e.getX(), e.getY())) {
            // Close the current window
           //System.exit(0);
           mainMenu = new MainMenu();
           mainMenu.playAction(ActionEvent);
        }
        
        System.out.println("Map Selected: " + mapSelected);
        System.out.println("Number of Players: " + numOfPlayers);
    }
    
    //@Override
   public void mouseMoved(MouseEvent e) {
	   //System.out.println("numplayers: " + numOfPlayers);
    	int temp = -1;
        for (int i = 0; i < buttons.size(); i++) {
        	temp = i;
        	GRect button = buttons.get(i);
            if (isWithinButtonBounds(button, e.getX(), e.getY())) {
            	if(temp != mapSelected) {
            		//System.out.println("mapSelected: " + mapSelected);
	                if (highlightedButtonIndex != i) {
	                	highlightedButtonIndex = i;
	                    // Reset previously highlighted button
	                    if (highlightedButtonIndex != -1 && temp != mapSelected) {
	                        buttons.get(highlightedButtonIndex).setFilled(false);
	                        buttons.get(highlightedButtonIndex).setFillColor(Color.black);
	                        //System.out.println("RESET");
	                    }
	                    // Highlight the current button
	                    //System.out.println("highlightedButtonIndex: " + highlightedButtonIndex);
	                    button.setFilled(true);
	                    button.setFillColor(Color.yellow);
	                    //highlightedButtonIndex = i;
	                }
	                return;
            	}
            }
        }
        
        for (int i = 0; i < playerButtons.size(); i++) {
            GRect playButton = playerButtons.get(i);
            if (isWithinButtonBounds(playButton, e.getX(), e.getY())) {
                playButton.setFilled(true);
                playButton.setFillColor(Color.magenta);
                //CharacterSelectMenu();
                // Exit the loop since we found the clicked button
                break;
            }else {
            	playButton.setFilled(false);
                playButton.setFillColor(Color.black);
            }
        }
        
        // If no button is being hovered over, reset the highlighting
        if (highlightedButtonIndex != mapSelected) {
        	//System.out.println("highlightedButtonIndex: " + highlightedButtonIndex);
        	//System.out.println("Map Selected: " + mapSelected);
        	buttons.get(highlightedButtonIndex).setFillColor(Color.black);
            buttons.get(highlightedButtonIndex).setFilled(false);
            //highlightedButtonIndex = -1; //Giving errors DO NOT UNCOMMET
            //System.out.println("highlightedButtonIndex: " + highlightedButtonIndex);
        }
    }

    private void resetButtons() {
        for (GRect button : buttons) {
        	button.setFilled(false);
        	button.setFillColor(Color.black);
            //System.out.println("Reset");
        }
    }


    private boolean isWithinButtonBounds(GRect button, int x, int y) {
        return x >= button.getX() && x <= button.getX() + button.getWidth() &&
               y >= button.getY() && y <= button.getY() + button.getHeight();
    }

    @Override
    public void run() {}
}
