package game;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 *
 * @author fabio
 */
public class WelcomeScreen extends JFrame {
    private int perception; // Player's perception level based on difficulty

    public WelcomeScreen() {
        setTitle("Zumbicídio - Bem-vindo");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));

        JLabel titleLabel = new JLabel("Escolha a dificuldade:", SwingConstants.CENTER);
        JButton easyButton = new JButton("Fácil (Percepção: 3)");
        JButton mediumButton = new JButton("Médio (Percepção: 2)");
        JButton hardButton = new JButton("Difícil (Percepção: 1)");

        add(titleLabel);
        add(easyButton);
        add(mediumButton);
        add(hardButton);

        easyButton.addActionListener(e -> showOptions(3));
        mediumButton.addActionListener(e -> showOptions(2));
        hardButton.addActionListener(e -> showOptions(1));

        setVisible(true);
    }

    private void showOptions(int perceptionLevel) {
        this.perception = perceptionLevel;
        getContentPane().removeAll();
        setLayout(new GridLayout(4, 1, 10, 10));

        JLabel modeLabel = new JLabel("Escolha uma opção:", SwingConstants.CENTER);
        JButton playButton = new JButton("Jogar");
        JButton debugButton = new JButton("DEBUG");
        JButton exitButton = new JButton("Sair");

        add(modeLabel);
        add(playButton);
        add(debugButton);
        add(exitButton);

        playButton.addActionListener(e -> startGame(false));
        debugButton.addActionListener(e -> startGame(true));
        exitButton.addActionListener(e -> System.exit(0));

        revalidate();
        repaint();
    }

    private void startGame(boolean debugMode) {
        dispose(); // Close welcome screen
        try {
            new GameWindow(perception, debugMode);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}