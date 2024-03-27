import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Freeplay extends GraphicsProgram {
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;
    public static final int BUTTON_WIDTH = 150;
    public static final int BUTTON_HEIGHT = 150;
    private List<GRect> buttons;
    private List<GLabel> buttonTexts;
    GRect backButton;
    private JComboBox<String> playerDropdown;
    private int highlightedButtonIndex = -1; // Index of the button being highlighted
    public int mapSelected = -1;

    public void init() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        buttons = new ArrayList<GRect>();
        buttonTexts = new ArrayList<GLabel>();
        
        
        String[] playerOptions = {"1 Player", "2 Players", "3 Players", "4 Players"};
        playerDropdown = new JComboBox<String>(playerOptions);
        playerDropdown.setFont(new Font("Arial", Font.PLAIN, 20));
        
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
        
        if(isWithinButtonBounds(backButton, e.getX(), e.getY())) {
        	resetButtons();
        	System.exit(0);
        }
    }
    
    //@Override
   public void mouseMoved(MouseEvent e) {
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
	                        System.out.println("RESET");
	                    }
	                    // Highlight the current button
	                    System.out.println("highlightedButtonIndex: " + highlightedButtonIndex);
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
        	System.out.println("highlightedButtonIndex: " + highlightedButtonIndex);
        	System.out.println("Map Selected: " + mapSelected);
        	buttons.get(highlightedButtonIndex).setFillColor(Color.black);
            buttons.get(highlightedButtonIndex).setFilled(false);
            //highlightedButtonIndex = -1; //Giving errors DO NOT UNCOMMET
            System.out.println("highlightedButtonIndex: " + highlightedButtonIndex);
        }
    }

    private void resetButtons() {
        for (GRect button : buttons) {
        	button.setFilled(false);
        	button.setFillColor(Color.black);
            System.out.println("Reset");
        }
    }


    private boolean isWithinButtonBounds(GRect button, int x, int y) {
        return x >= button.getX() && x <= button.getX() + button.getWidth() &&
               y >= button.getY() && y <= button.getY() + button.getHeight();
    }

    @Override
    public void run() {}
}
