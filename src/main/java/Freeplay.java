import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Freeplay extends GraphicsProgram {
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;
    public static final int BUTTON_WIDTH = 150;
    public static final int BUTTON_HEIGHT = 150;
    private List<GRect> buttons;
    private List<GLabel> buttonTexts;
    GRect backButton;

    public void init() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        buttons = new ArrayList<GRect>();
        buttonTexts = new ArrayList<GLabel>();
        
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
    
    public void mouseEnter(MouseEvent e) {
    	//TODO: Make it where when you hover over a button,it highlights
    }

    private void resetButtons() {
        for (GRect button : buttons) {
            button.setFilled(false);
        }
    }


    private boolean isWithinButtonBounds(GRect button, int x, int y) {
        return x >= button.getX() && x <= button.getX() + button.getWidth() &&
               y >= button.getY() && y <= button.getY() + button.getHeight();
    }

    @Override
    public void run() {}
}
