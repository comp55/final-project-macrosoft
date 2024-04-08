import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PlayerSelection {
    private static final int WINDOW_X = 1024;
    private static final int WINDOW_Y = 768;
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 50;

    private JLabel backgroundImageLabel;

    public void selectionLoad(JFrame frame) {
        // Clear the contents of the frame
        frame.getContentPane().removeAll();

        // Repaint the frame to reflect the changes
        frame.revalidate();
        frame.repaint();

        // Load the background image
        ImageIcon backgroundImageIcon = new ImageIcon("images/intermissionbg.png");
        backgroundImageLabel = new JLabel(backgroundImageIcon);
        backgroundImageLabel.setBounds(0, 0, WINDOW_X, WINDOW_Y);
        frame.add(backgroundImageLabel);

        // Create a panel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(0, 0, WINDOW_X, WINDOW_Y);
        // Make the panel transparent so the background image is still visible
        buttonPanel.setOpaque(false);

        // Create other components as needed
        createButton(frame, buttonPanel, "Testing Purposes", WINDOW_Y / 2 + BUTTON_HEIGHT - 20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement functionality
            }
        });

        // Add the button panel to the frame's content pane
        frame.add(buttonPanel);
    }

    private JButton createButton(JFrame frame, JPanel buttonPanel, String text, int y, ActionListener actionListener) {
        JButton button = new JButton();
        button.setText(text);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setBounds(WINDOW_X / 2 - BUTTON_WIDTH / 2, y, BUTTON_WIDTH, BUTTON_HEIGHT);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(Color.BLACK);
            }
        });

        button.addActionListener(actionListener);
        buttonPanel.add(button);
        return button;
    }
}
