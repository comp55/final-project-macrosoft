import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainMenu {

    private static final int WINDOW_X = 1024;
    private static final int WINDOW_Y = 768;
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 50;
    
    private JFrame frame;
    private JLabel mainBackground;
    private JButton playButton;
    private JButton settingsButton;
    private JButton quitButton;
    private JPanel quitConfirmationPanel;
    
    BackgroundMusic backgroundMusic;
    
    PlayerSelection playerSelec;
    
    public MainMenu() {
    	
    	backgroundMusic = new BackgroundMusic("audio/MainMenuTheme.mp3", true);
    	
        window();
        addButton(frame, "play", WINDOW_Y / 2 - BUTTON_HEIGHT / 2, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playAction(e);
            }
        });
        addButton(frame, "settings", WINDOW_Y / 2 + BUTTON_HEIGHT - 20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsAction(e);
            }
        });
        addButton(frame, "quit", WINDOW_Y / 2 + BUTTON_HEIGHT * 2 - 15, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showQuitConfirmation();
            }
        });
        
        backgroundMusic.play();
        
    }

    private void window() {
        ImageIcon backgroundImage = new ImageIcon("images/mainbg.png");
        mainBackground = new JLabel(backgroundImage);
        mainBackground.setBounds(0, 0, WINDOW_X, WINDOW_Y);

        JLabel title = new JLabel();
        title.setText("fruitless conflict");
        title.setFont(new Font("Arial", Font.PLAIN, 50));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.CENTER);
        title.setBounds(0, 0, WINDOW_X, WINDOW_Y / 2);

        JLabel versionLabel = new JLabel("ver 0.00053");
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

        frame.setContentPane(mainBackground);
        frame.add(title);
        frame.add(versionLabel);
        frame.add(copyrightLabel);
        frame.setVisible(true);
    }
    
    private JButton createButton(Container container, String text, int y, ActionListener actionListener) {
        final JButton button = new JButton();
        button.setText(text);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setBackground(new Color(0, 0, 0, 0));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setFocusable(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBounds(WINDOW_X / 2 - BUTTON_WIDTH / 2, y, BUTTON_WIDTH, BUTTON_HEIGHT);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	button.setForeground(Color.RED);
            	button.setFont(new Font("Arial", Font.BOLD, 20));
                button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	button.setForeground(Color.BLACK);
            	button.setFont(new Font("Arial", Font.PLAIN, 20));
                button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            }
        });

        button.addActionListener(actionListener);
        container.add(button);
        return button;
        
    }

    private void addButton(Container container, String text, int y, ActionListener actionListener) {
    	JButton button = createButton(container, text, y, actionListener);
    	switch (text) {
    	case "play":
    		playButton = button;
    		break;
    	case "settings":
    		settingsButton = button;
    		break;
    	case "quit":
    		quitButton = button;
    		break;
    	}
    }

    public void playAction(ActionEvent e) {
        
    	JPanel playPanel = new JPanel();
    	playPanel.setLayout(null);
    	playPanel.setBounds(0, 0, WINDOW_X, WINDOW_Y);
    	
    	ImageIcon playBackgroundImage = new ImageIcon("images/intermissionbg.png");
    	JLabel playBackground = new JLabel(playBackgroundImage);
    	playBackground.setBounds(0, 0, WINDOW_X, WINDOW_Y);
    	
    	createButton(playPanel, "start game", WINDOW_Y/2 - BUTTON_HEIGHT/2, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement functionality
            	System.out.println("Trying to get playerSelec");
            	playerSelec = new PlayerSelection();
            	playerSelec.selectionLoad(frame);
            }
        });

        createButton(playPanel, "how to play", WINDOW_Y/2 + BUTTON_HEIGHT - 20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement functionality
            }
        });

        createButton(playPanel, "main menu", WINDOW_Y/2 + BUTTON_HEIGHT*2 - 15, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnToMainMenu();
            }
        });
    	
    	playPanel.add(playBackground);
    	
    	frame.setContentPane(playPanel);
    	frame.revalidate();
    	frame.repaint();
    	
    }
    
    private void returnToMainMenu() {
    	frame.setContentPane(mainBackground);
    	frame.revalidate();
    	frame.repaint();
    }

    private void settingsAction(ActionEvent e) {
        // Implement functionality
    }

    private void quitAction(ActionEvent e) {
        System.exit(0);
    }
    
    private void showQuitConfirmation() {
        // Hide main menu buttons
        playButton.setVisible(false);
        settingsButton.setVisible(false);
        quitButton.setVisible(false);

        if (quitConfirmationPanel == null) {
            quitConfirmationPanel = new JPanel();
            quitConfirmationPanel.setLayout(null);

            int panelWidth = 300;
            int panelHeight = 150;
            int panelX = (WINDOW_X - panelWidth) / 2;
            int panelY = (WINDOW_Y - panelHeight) / 2;

            quitConfirmationPanel.setBounds(panelX, panelY, panelWidth, panelHeight);
            quitConfirmationPanel.setBackground(Color.WHITE);
            quitConfirmationPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

            JLabel label = new JLabel("quit the game?");
            label.setFont(new Font("Arial", Font.PLAIN, 20));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setBounds(0, 20, panelWidth, 30);
            quitConfirmationPanel.add(label);

            final JButton yesButton = new JButton("yes");
            yesButton.setBounds(50, 80, 80, 30);
            yesButton.setFont(new Font("Arial", Font.PLAIN, 20));
            yesButton.setBackground(new Color(0, 0, 0, 0));
            yesButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            yesButton.setFocusable(false);
            yesButton.setFocusPainted(false);
            yesButton.setContentAreaFilled(false);
            yesButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    yesButton.setForeground(Color.RED);
                    yesButton.setFont(new Font("Arial", Font.BOLD, 20));
                    yesButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    yesButton.setForeground(Color.BLACK);
                    yesButton.setFont(new Font("Arial", Font.PLAIN, 20));
                    yesButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                }
            });
            yesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    quitAction(e);
                }
            });
            quitConfirmationPanel.add(yesButton);

            final JButton noButton = new JButton("no");
            noButton.setBounds(170, 80, 80, 30);
            noButton.setFont(new Font("Arial", Font.PLAIN, 20));
            noButton.setBackground(new Color(0, 0, 0, 0));
            noButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            noButton.setFocusable(false);
            noButton.setFocusPainted(false);
            noButton.setContentAreaFilled(false);
            noButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    noButton.setForeground(Color.RED);
                    noButton.setFont(new Font("Arial", Font.BOLD, 20));
                    noButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    noButton.setForeground(Color.BLACK);
                    noButton.setFont(new Font("Arial", Font.PLAIN, 20));
                    noButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                }
            });
            noButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Show main menu buttons again
                    playButton.setVisible(true);
                    settingsButton.setVisible(true);
                    quitButton.setVisible(true);

                    frame.getContentPane().remove(quitConfirmationPanel);
                    frame.revalidate();
                    frame.repaint();
                    
                    // Reset quitConfirmationPanel to null
                    quitConfirmationPanel = null;
                }
            });
            quitConfirmationPanel.add(noButton);

            frame.setContentPane(mainBackground);
            frame.getContentPane().add(quitConfirmationPanel);
            frame.revalidate();
            frame.repaint();
        } else {
            quitConfirmationPanel.setVisible(true);
        }
    }

    public static void main(String[] args) {
        new MainMenu();
    }
}
