import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;


public class MainMenu {

	private static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 20);
	private static final Font BOLD_DEFAULT_FONT = new Font("Arial", Font.BOLD, 20);
    private static final int WINDOW_X = 1024;
    private static final int WINDOW_Y = 768;
    private static final int BIG_BUTTON_WIDTH = 200;
    private static final int BIG_BUTTON_HEIGHT = 50;
    private static final int SMALL_BUTTON_WIDTH = 100;
    private static final int SMALL_BUTTON_HEIGHT = 30;
    private static final int SQUARE_BUTTON_SIZE = 100;
    
    private JFrame frame;
    private JLabel title;
    private JLabel mainBackground;
    private JLabel secondaryBackground;
	private JButton highlightedButton;
    private JButton playButton;
    private JButton settingsButton;
    private JButton quitButton;
    private JButton startButton;
    private JButton howToPlayButton;
    private JButton backToMainMenuButton;
    private JPanel quitConfirmationPanel;
    private JPanel playPanel;
    private JPanel settingsPanel;
    private JPanel gameSetupPanel;
    private JPanel howToPlayPanel;
    
    Sound backgroundMusic;
    Sound gameSetupMusic;
    Sound buttonClicked;
    
	private int panelWidth;
	private int panelHeight;
	private int panelX;
	private int panelY;
	
	private Platformer platformer;
	private int numPlayers = 2;
	private int startingScore = 0;
	private int currentPlayer = 1;

    
    public MainMenu() {
    	
    	buttonClicked = new Sound("audio/ClickSound.mp3", false);
    	backgroundMusic = new Sound("audio/MainMenuTheme.mp3", true);
    	gameSetupMusic = new Sound("audio/GetReady.mp3", true);
    	
        window();
        addButton(frame, "play", WINDOW_X / 2 - BIG_BUTTON_WIDTH / 2, WINDOW_Y / 2 - BIG_BUTTON_HEIGHT / 2, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonClicked.play();
                showIntermediary(e);
            }
        }, false);

        addButton(frame, "settings", WINDOW_X / 2 - BIG_BUTTON_WIDTH / 2, WINDOW_Y / 2 + BIG_BUTTON_HEIGHT - 20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonClicked.play();
                showSettings(e);
            }
        }, false);

        addButton(frame, "quit", WINDOW_X / 2 - BIG_BUTTON_WIDTH / 2, WINDOW_Y / 2 + BIG_BUTTON_HEIGHT * 2 - 15, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonClicked.play();
                showQuitConfirmation();
            }
        }, false);
        
        backgroundMusic.play();
        
    }

    private void window() {
        ImageIcon backgroundImage = new ImageIcon("images/mainbg.png");
        mainBackground = new JLabel(backgroundImage);
        mainBackground.setBounds(0, 0, WINDOW_X, WINDOW_Y);

        title = new JLabel();
        title.setText("fruitless conflict");
        title.setFont(new Font("Arial", Font.PLAIN, 50));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.CENTER);
        title.setBounds(0, 0, WINDOW_X, WINDOW_Y / 2);

        JLabel versionLabel = new JLabel("ver 0.00128");
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
    
    private JButton createBigButton(Container container, String text, int x, int y, ActionListener actionListener) {
        final JButton button = new JButton();
        button.setText(text);
        button.setFont(DEFAULT_FONT);
        button.setBackground(new Color(0, 0, 0, 0));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setFocusable(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBounds(x, y, BIG_BUTTON_WIDTH, BIG_BUTTON_HEIGHT);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(new Color(255, 165, 0));
                button.setFont(new Font("Arial", Font.BOLD, 20));
                button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(Color.BLACK);
                button.setFont(DEFAULT_FONT);
                button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            }
        });

        button.addActionListener(actionListener);
        container.add(button);
        return button;
    }

    private JButton createSmallButton(Container container, String text, int x, int y, ActionListener actionListener) {
        final JButton button = new JButton();
        button.setText(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(new Color(0, 0, 0, 0));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setFocusable(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBounds(x, y, SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(new Color(255, 165, 0));
                button.setFont(new Font("Arial", Font.BOLD, 16));
                button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(Color.BLACK);
                button.setFont(new Font("Arial", Font.PLAIN, 16));
                button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            }
        });

        button.addActionListener(actionListener);
        container.add(button);
        return button;
    }
    
    private JButton createMapButton(Container container, ImageIcon icon, int x, int y, ActionListener actionListener) {
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        final JButton button = new JButton(scaledIcon);
        button.setBackground(new Color(0, 0, 0, 0));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setFocusable(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBounds(x, y, SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBorder(BorderFactory.createLineBorder(new Color(255, 165, 0), 4));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (button != highlightedButton) {
                    button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (highlightedButton != null && highlightedButton != button) {
                    highlightedButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                }
                highlightedButton = button;
            }
        });

        button.addActionListener(actionListener);
        return button;
        
    }
    
    private JButton createCharacterButton(Container container, ImageIcon icon, int x, int y, ActionListener actionListener) {
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        final JButton button = new JButton(scaledIcon);
        button.setBackground(new Color(0, 0, 0, 0));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setFocusable(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBounds(x, y, SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBorder(BorderFactory.createLineBorder(new Color(255, 0, 0), 4));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (button != highlightedButton) {
                    button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (highlightedButton != null && highlightedButton != button) {
                    highlightedButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                }
                highlightedButton = button;
            }
        });

        button.addActionListener(actionListener);
        return button;
        
    }

    private void addButton(Container container, String text, int x, int y, ActionListener actionListener, boolean isSmall) {
        JButton button;
        if (isSmall) {
            button = createSmallButton(container, text, x, y, actionListener);
        } else {
            button = createBigButton(container, text, x, y, actionListener);
        }
        
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
            case "start game":
                startButton = button;
                break;
            case "how to play":
                howToPlayButton = button;
                break;
            case "main menu":
                backToMainMenuButton = button;
                break;
        }
    }
    
    private void addMapButton(Container container, ImageIcon icon, int x, int y, ActionListener actionListener) {
    	JButton button = createMapButton(container, icon, x, y, actionListener);
        container.add(button);
    }
    
    private void addCharacterButton(Container container, ImageIcon icon, int x, int y, ActionListener actionListener) {
    	JButton button = createCharacterButton(container, icon, x, y, actionListener);
    	container.add(button);
    }
    
    public void showIntermediary(ActionEvent e) {
        
    	playPanel = new JPanel();
    	playPanel.setLayout(null);
    	playPanel.setBounds(0, 0, WINDOW_X, WINDOW_Y);
    	
    	ImageIcon playBackgroundImage = new ImageIcon("images/intermissionbg.png");
    	secondaryBackground = new JLabel(playBackgroundImage);
    	secondaryBackground.setBounds(0, 0, WINDOW_X, WINDOW_Y);
    	
    	addButton(playPanel, "start game", WINDOW_X / 2 - BIG_BUTTON_WIDTH / 2, WINDOW_Y/2 - BIG_BUTTON_HEIGHT/2, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	buttonClicked.play();
            	backgroundMusic.stop();
            	gameSetupMusic.play();
            	showGameSetup();
            }
        }, false);

        addButton(playPanel, "how to play", WINDOW_X / 2 - BIG_BUTTON_WIDTH / 2, WINDOW_Y/2 + BIG_BUTTON_HEIGHT - 20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	buttonClicked.play();
            	showHowToPlay();
            }
        }, false);

        addButton(playPanel, "main menu", WINDOW_X / 2 - BIG_BUTTON_WIDTH / 2, WINDOW_Y/2 + BIG_BUTTON_HEIGHT*2 - 15, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	buttonClicked.play();
                returnToMainMenu();
            }
        }, false);
    	
    	playPanel.add(secondaryBackground);
    	
    	frame.setContentPane(playPanel);
    	frame.revalidate();
    	frame.repaint();
    	
    }
    


	private void returnToMainMenu() {
    	frame.setContentPane(mainBackground);
    	frame.revalidate();
    	frame.repaint();
    }

    private void showSettings(ActionEvent e) {
    	
    	playButton.setVisible(false);
        settingsButton.setVisible(false);
        quitButton.setVisible(false);
        title.setVisible(false);
        
        if (settingsPanel == null) {
    		
        	settingsPanel = new JPanel();
        	settingsPanel.setLayout(null);
        	
            panelWidth = 900;
            panelHeight = 600;
            panelX = (WINDOW_X - panelWidth) / 2;
            panelY = (WINDOW_Y - panelHeight) / 2;
            
            settingsPanel.setBounds(panelX, panelY, panelWidth, panelHeight);
            settingsPanel.setBackground(Color.WHITE);
            settingsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            
            JLabel label = new JLabel("Settings WIP");
            label.setFont(DEFAULT_FONT);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setBounds(0, 20, panelWidth, 30);
            settingsPanel.add(label);
            
            addButton(settingsPanel, "back", 50, 80, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    buttonClicked.play();

                    playButton.setVisible(true);
                    settingsButton.setVisible(true);
                    quitButton.setVisible(true);
                    title.setVisible(true);

                    frame.getContentPane().remove(settingsPanel);
                    frame.setContentPane(mainBackground);
                    frame.revalidate();
                    frame.repaint();

                    settingsPanel = null;
                }
            }, true);

            frame.setContentPane(mainBackground);
            frame.getContentPane().add(settingsPanel);
            frame.revalidate();
            frame.repaint();
            
    	} else {
    		settingsPanel.setVisible(true);
    	}
    }
    
    private void showGameSetup() {
    	
    	startButton.setVisible(false);
    	howToPlayButton.setVisible(false);
    	backToMainMenuButton.setVisible(false);
    	
    	if (gameSetupPanel == null) {
    		
    		gameSetupPanel = new JPanel();
    		gameSetupPanel.setLayout(null);
    		
    		panelWidth = 900;
            panelHeight = 600;
            panelX = (WINDOW_X - panelWidth) / 2;
            panelY = (WINDOW_Y - panelHeight) / 2;
            
            gameSetupPanel.setBounds(panelX, panelY, panelWidth, panelHeight);
            gameSetupPanel.setBackground(Color.WHITE);
            gameSetupPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            
            JLabel label = new JLabel("game setup");
            label.setFont(BOLD_DEFAULT_FONT);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setBounds(0, 20, panelWidth, 30);
            gameSetupPanel.add(label);
            
            JLabel mapLabel = new JLabel("select a map:");
            mapLabel.setFont(DEFAULT_FONT);
            mapLabel.setHorizontalAlignment(JLabel.LEFT);
            mapLabel.setBounds(200, 50, panelWidth, 30);
            gameSetupPanel.add(mapLabel);
            
            JLabel playerLabel = new JLabel("select number of players:");
            playerLabel.setFont(DEFAULT_FONT);
            playerLabel.setHorizontalAlignment(JLabel.LEFT);
            playerLabel.setBounds(200, 190, panelWidth, 30);
            gameSetupPanel.add(playerLabel);
            
            JLabel scoreLabel = new JLabel("select starting lives:");
            scoreLabel.setFont(DEFAULT_FONT);
            scoreLabel.setHorizontalAlignment(JLabel.LEFT);
            scoreLabel.setBounds(200, 230, panelWidth, 30);
            gameSetupPanel.add(scoreLabel);
            
            JLabel characterLabel = new JLabel("select a fruit for player:");
            characterLabel.setFont(DEFAULT_FONT);
            characterLabel.setHorizontalAlignment(JLabel.LEFT);
            characterLabel.setBounds(200, 270, panelWidth, 30);
            gameSetupPanel.add(characterLabel);
            
            BasicComboBoxUI comboBoxUI = new BasicComboBoxUI() {
                
                @Override
                protected JButton createArrowButton() { 
                    JButton arrowButton = new JButton("");
                    arrowButton.setContentAreaFilled(false);
                    arrowButton.setFocusable(false);
                    arrowButton.setBorder(BorderFactory.createEmptyBorder());
                    return arrowButton;
                }
                
                @Override
                protected ComboPopup createPopup() {
                    BasicComboPopup popup = (BasicComboPopup) super.createPopup();
                    popup.getList().addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            popup.getList().setSelectionBackground(new Color(255, 165, 0)); 
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            popup.getList().setSelectionBackground(UIManager.getColor("List.selectionBackground"));
                        }
                    });
                    return popup;
                }

            };
            
            BasicComboBoxUI comboBoxUI2 = new BasicComboBoxUI() {
                
                @Override
                protected JButton createArrowButton() { 
                    JButton arrowButton = new JButton("");
                    arrowButton.setContentAreaFilled(false);
                    arrowButton.setFocusable(false);
                    arrowButton.setBorder(BorderFactory.createEmptyBorder());
                    return arrowButton;
                }
                
                @Override
                protected ComboPopup createPopup() {
                    BasicComboPopup popup = (BasicComboPopup) super.createPopup();
                    popup.getList().addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            popup.getList().setSelectionBackground(new Color(255, 165, 0)); 
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            popup.getList().setSelectionBackground(UIManager.getColor("List.selectionBackground"));
                        }
                    });
                    return popup;
                }

            };
            
            BasicComboBoxUI comboBoxUI3 = new BasicComboBoxUI() {
                
                @Override
                protected JButton createArrowButton() { 
                    JButton arrowButton = new JButton("");
                    arrowButton.setContentAreaFilled(false);
                    arrowButton.setFocusable(false);
                    arrowButton.setBorder(BorderFactory.createEmptyBorder());
                    return arrowButton;
                }
                
                @Override
                protected ComboPopup createPopup() {
                    BasicComboPopup popup = (BasicComboPopup) super.createPopup();
                    popup.getList().addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            popup.getList().setSelectionBackground(new Color(255, 165, 0)); 
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            popup.getList().setSelectionBackground(UIManager.getColor("List.selectionBackground"));
                        }
                    });
                    return popup;
                }

            };
            
            String[] playerOptions = {"2", "3", "4"};
            JComboBox<String> playerComboBox = new JComboBox<>(playerOptions);
            playerComboBox.setFont(DEFAULT_FONT);
            playerComboBox.setBounds(430, 190, 50, 30);
            playerComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            playerComboBox.setUI(comboBoxUI);
            gameSetupPanel.add(playerComboBox);
            
            String[] scoreOptions = {"1", "2", "3"};
            JComboBox<String> scoreOptionsBox = new JComboBox<>(scoreOptions);
            scoreOptionsBox.setFont(DEFAULT_FONT);
            scoreOptionsBox.setBounds(430, 230, 50, 30);
            scoreOptionsBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            scoreOptionsBox.setUI(comboBoxUI2);
            gameSetupPanel.add(scoreOptionsBox);
            
            String[] playerSelect = {"1", "2", "3", "4"};
            JComboBox<String> playerSelectBox = new JComboBox<>(playerSelect);
            playerSelectBox.setFont(DEFAULT_FONT);
            playerSelectBox.setBounds(430, 270, 50, 30);
            playerSelectBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            playerSelectBox.setUI(comboBoxUI3);
            gameSetupPanel.add(playerSelectBox);
            
            playerComboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedOption = (String) playerComboBox.getSelectedItem();
                    numPlayers = Integer.parseInt(selectedOption);
                }
            });
            
            scoreOptionsBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedOption2 = (String) scoreOptionsBox.getSelectedItem();
                    startingScore = Integer.parseInt(selectedOption2)-1;
                }
            });
            
            playerSelectBox.addActionListener(new ActionListener() {
            	@Override
            	public void actionPerformed(ActionEvent e) {
            		String selectedOption3 = (String) playerSelectBox.getSelectedItem();
            		currentPlayer  = Integer.parseInt(selectedOption3);
            	}
            });
            
            addMapButton(gameSetupPanel, new ImageIcon("images/PlateMap.png"), 200, 80, new ActionListener() {
            	@Override
            	public void actionPerformed(ActionEvent e) {
            		buttonClicked.play();         		
            	}
            });
            
            addMapButton(gameSetupPanel, new ImageIcon("images/SinkMap.png"), 310, 80, new ActionListener() {
            	@Override
            	public void actionPerformed(ActionEvent e) {
            		buttonClicked.play();
            	}
            });
            
            addCharacterButton(gameSetupPanel, new ImageIcon("images/applePlayer.PNG"), 200, 310, new ActionListener() {
            	@Override
            	public void actionPerformed(ActionEvent e) {
            		buttonClicked.play();
            	}
            });
            
            addCharacterButton(gameSetupPanel, new ImageIcon("images/orangePlayer.PNG"), 310, 310, new ActionListener() {
            	@Override
            	public void actionPerformed(ActionEvent e) {
            		buttonClicked.play();
            	}
            });
            
            addCharacterButton(gameSetupPanel, new ImageIcon("images/tomatoPlayer.PNG"), 420, 310, new ActionListener() {
            	@Override
            	public void actionPerformed(ActionEvent e) {
            		buttonClicked.play();
            	}
            });
            
            addCharacterButton(gameSetupPanel, new ImageIcon("images/watermelonPlayer.PNG"), 530, 310, new ActionListener() {
            	@Override
            	public void actionPerformed(ActionEvent e) {
            		buttonClicked.play();
            	}
            });
            
            addButton(gameSetupPanel, "back", 50, 80, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    buttonClicked.play();
                    gameSetupMusic.stop();
                    backgroundMusic.play();

                    startButton.setVisible(true);
                    howToPlayButton.setVisible(true);
                    backToMainMenuButton.setVisible(true);

                    frame.getContentPane().remove(gameSetupPanel);
                    frame.setContentPane(playPanel);
                    frame.getContentPane().add(secondaryBackground);
                    frame.revalidate();
                    frame.repaint();

                    gameSetupPanel = null;
                }
            }, true);
            
            addButton(gameSetupPanel, "ready",  450 - BIG_BUTTON_WIDTH / 2, 500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    buttonClicked.play();
                    gameSetupMusic.stop();
                    
                    platformer = new Platformer();
                    gameSetup();
                    
                }

				private void gameSetup() {
					
					platformer.setMap("map2");
			        platformer.setNumPlayers(numPlayers);
			        platformer.setStartingScore(startingScore);
					platformer.setPlayerIMG("t", "a", "o", "w");
			        platformer.run();
			        
			        frame.dispose();
					
				}
            }, false);

            frame.setContentPane(secondaryBackground);
            frame.getContentPane().add(gameSetupPanel);
            frame.revalidate();
            frame.repaint();
            
    	} else {
    		gameSetupPanel.setVisible(true);
    	}
            
    }
    
    private void showHowToPlay() {
    	
    	startButton.setVisible(false);
    	howToPlayButton.setVisible(false);
    	backToMainMenuButton.setVisible(false);
    	
    	if (howToPlayPanel == null) {
    		
        	howToPlayPanel = new JPanel();
        	howToPlayPanel.setLayout(null);
        	
            panelWidth = 900;
            panelHeight = 600;
            panelX = (WINDOW_X - panelWidth) / 2;
            panelY = (WINDOW_Y - panelHeight) / 2;
            
            howToPlayPanel.setBounds(panelX, panelY, panelWidth, panelHeight);
            howToPlayPanel.setBackground(Color.WHITE);
            howToPlayPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            
            JLabel label = new JLabel("How to Play WIP");
            label.setFont(DEFAULT_FONT);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setBounds(0, 20, panelWidth, 30);
            howToPlayPanel.add(label);
            
            addButton(howToPlayPanel, "back", 50, 80, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    buttonClicked.play();

                    startButton.setVisible(true);
                    howToPlayButton.setVisible(true);
                    backToMainMenuButton.setVisible(true);

                    frame.getContentPane().remove(howToPlayPanel);
                    frame.setContentPane(playPanel);
                    frame.getContentPane().add(secondaryBackground);
                    frame.revalidate();
                    frame.repaint();

                    howToPlayPanel = null;
                }
            }, true);

            frame.setContentPane(secondaryBackground);
            frame.getContentPane().add(howToPlayPanel);
            frame.revalidate();
            frame.repaint();
            
    	} else {
    		howToPlayPanel.setVisible(true);
    	}
    	
    }
    
    private void showQuitConfirmation() {

        playButton.setVisible(false);
        settingsButton.setVisible(false);
        quitButton.setVisible(false);

        if (quitConfirmationPanel == null) {
            quitConfirmationPanel = new JPanel();
            quitConfirmationPanel.setLayout(null);

            panelWidth = 300;
            panelHeight = 150;
            panelX = (WINDOW_X - panelWidth) / 2;
            panelY = (WINDOW_Y - panelHeight) / 2;

            quitConfirmationPanel.setBounds(panelX, panelY, panelWidth, panelHeight);
            quitConfirmationPanel.setBackground(Color.WHITE);
            quitConfirmationPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

            JLabel label = new JLabel("quit the game?");
            label.setFont(DEFAULT_FONT);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setBounds(0, 20, panelWidth, 30);
            quitConfirmationPanel.add(label);

            addButton(quitConfirmationPanel, "yes", 50, 80, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    buttonClicked.play();
                    System.exit(0);
                }
            }, true);

            addButton(quitConfirmationPanel, "no", 170, 80, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    buttonClicked.play();
                    playButton.setVisible(true);
                    settingsButton.setVisible(true);
                    quitButton.setVisible(true);
                    frame.getContentPane().remove(quitConfirmationPanel);
                    frame.revalidate();
                    frame.repaint();
                    quitConfirmationPanel = null;
                }
            }, true);

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
