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

    public void init() {
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        buttons = new ArrayList<GRect>();
        buttonTexts = new ArrayList<GLabel>();
        
        // Create buttons
        for (int i = 0; i < 5; i++) { 
            GRect button = new GRect(100 + i * 200, (WINDOW_HEIGHT - BUTTON_HEIGHT) / 10, BUTTON_WIDTH, BUTTON_HEIGHT);
            button.setFilled(false);
            add(button);
            buttons.add(button);
            
            GLabel buttonText = new GLabel("Button " + (i + 1));
            buttonText.setFont("Arial-20");
            double y = (WINDOW_HEIGHT - buttonText.getAscent()) / 5;
            double x = 100 + i * 200 + (BUTTON_WIDTH + buttonText.getWidth()) / 6;
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
