import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainMenu {

    private static final int WINDOW_X = 1024;
    private static final int WINDOW_Y = 768;
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 50;
    private JFrame frame;

    public MainMenu() {
        window();
        addButton("play", WINDOW_Y / 2 - BUTTON_HEIGHT / 2, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playAction(e);
            }
        });
        addButton("settings", WINDOW_Y / 2 + BUTTON_HEIGHT - 20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsAction(e);
            }
        });
        addButton("quit", WINDOW_Y / 2 + BUTTON_HEIGHT * 2 - 15, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quitAction(e);
            }
        });
    }

    private void window() {
        ImageIcon backgroundImage = new ImageIcon("images/mainbg.png");
        JLabel background = new JLabel(backgroundImage);
        background.setBounds(0, 0, WINDOW_X, WINDOW_Y);

        JLabel title = new JLabel();
        title.setText("fruitless conflict");
        title.setFont(new Font("Arial", Font.PLAIN, 50));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.CENTER);
        title.setBounds(0, 0, WINDOW_X, WINDOW_Y / 2);

        JLabel versionLabel = new JLabel("ver 0.00025");
        versionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        versionLabel.setForeground(Color.GRAY);
        versionLabel.setHorizontalAlignment(JLabel.RIGHT);
        versionLabel.setBounds(WINDOW_X - 120, 0, 100, 20);

        JLabel copyrightLabel = new JLabel("\u00a9 2024 Macrosoft");
        copyrightLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        copyrightLabel.setForeground(Color.GRAY);
        copyrightLabel.setBounds(10, WINDOW_Y - 60, 200, 20);

        frame = new JFrame();
        frame.setTitle("Fruitless Conflict");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(WINDOW_X, WINDOW_Y);
        frame.setLayout(null);

        ImageIcon logo = new ImageIcon("images/logo.png");
        frame.setIconImage(logo.getImage());

        frame.setContentPane(background);
        frame.add(title);
        frame.add(versionLabel);
        frame.add(copyrightLabel);
        frame.setVisible(true);
    }


    private void addButton(String text, int y, ActionListener actionListener) {
        final JButton button = new JButton();
        button.setText(text);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setBackground(new Color(0, 0, 0, 0));
        button.setBorder(null);
        button.setFocusable(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
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
        frame.add(button);
    }


    private void playAction(ActionEvent e) {
        // Add functionality for play button
    }

    private void settingsAction(ActionEvent e) {
        // Add functionality for settings button
    }

    private void quitAction(ActionEvent e) {
        System.exit(0);
    }

    public static void main(String[] args) {
        new MainMenu();
    }
}
