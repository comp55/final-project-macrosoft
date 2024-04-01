import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import acm.graphics.*;
import acm.program.*;

public class Freeplay extends GraphicsProgram {
	//Static
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;
    public static final int BUTTON_WIDTH = 150;
    public static final int BUTTON_HEIGHT = 150;
    
    //Lists
    private List<GRect> buttons;
    private List<GLabel> buttonTexts;
    private List<GRect> playerButtons;
    private List<GRect> playerSelection;
    
    //Other
    GRect backButton;
    private JComboBox<String> playerDropdown;
    private int highlightedButtonIndex = -1; // Index of the button being highlighted
    public int mapSelected = -1;
    private int numOfPlayers = 1; // Default number of players

    //Init the program
    public void init() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        buttons = new ArrayList<GRect>();
        buttonTexts = new ArrayList<GLabel>();
        playerButtons = new ArrayList<GRect>();
        playerSelection = new ArrayList<GRect>();
        UpdateNumPlayers();
        
        //Creating number of players
        String[] playerOptions = {"1 Player", "2 Players", "3 Players", "4 Players"};
        playerDropdown = new JComboBox<String>(playerOptions);
        playerDropdown.setFont(new Font("Arial", Font.PLAIN, 20));
        playerDropdown.addActionListener(e -> {
            JComboBox<String> cb = (JComboBox<String>) e.getSource();
            String selectedPlayerOption = (String) cb.getSelectedItem();
            // Extracting the number of players from the selected option
            numOfPlayers = Integer.parseInt(selectedPlayerOption.split(" ")[0]);
            UpdateNumPlayers();
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
    }
    
    //updates the screen to show new number of players
    private void UpdateNumPlayers() {
    	System.out.println("I was called");
    	for(GRect playButton : playerButtons){
    		remove(playButton);
    	}
    	
    	playerButtons.clear();
    	//TODO: Write a For loop that creates a button for every number of player that will be in the game
    	for(int i = 0; i < numOfPlayers; i++){
    		GRect playerButton = new GRect(100 + i * 200, (WINDOW_HEIGHT - BUTTON_HEIGHT) / 1.3, BUTTON_WIDTH, BUTTON_HEIGHT);
            playerButton.setFilled(false);
            add(playerButton);
            playerButtons.add(playerButton);
    	}
    }
    
    private void CharacterSelectMenu() {
    	//Making the background
    	GRect backGround = new GRect(250, 100, 800, 500);
        backGround.setFilled(true);
        backGround.setFillColor(Color.cyan);
        //debug
        System.out.println("AHHHHHHH");
        //debug
        add(backGround);
        
        //Making the Character Select Buttons
        GImage charOrange = new GImage("images/orangePlayer.PNG", 300, 130);
        charOrange.setSize(160, 120);
        GImage charRed = new GImage("images/redPlayer.PNG", 430, 130);
        charRed.setSize(160, 120);
        GImage charSquare = new GImage("images/sqaureMelonPlayer.PNG", 560, 130);
        charSquare.setSize(160, 120);
        GImage charTomato = new GImage("images/tomatoPlayer.PNG", 690, 130);
        charTomato.setSize(160, 120);
        GImage charWater = new GImage("images/watermelonPlayer.PNG", 820, 130);
        charWater.setSize(160, 120);
        add(charOrange);
        add(charRed);
        add(charSquare);
        add(charTomato);
        add(charWater);
        
        //Making the confirm Button
        
    	return;
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
                playButton.setFilled(true);
                playButton.setFillColor(Color.magenta);
                CharacterSelectMenu();
                // Exit the loop since we found the clicked button
                break;
            }
        }
        
        if(isWithinButtonBounds(backButton, e.getX(), e.getY())) {
        	resetButtons();
			System.exit(0);
        }
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
