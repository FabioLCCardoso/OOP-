/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fabio
 */
public class gameWindow implements KeyListener {

    private JFrame frame;
    private GameGrid gameGrid;
    private Hero hero;
    private List<Zombie> zumbis;
    private Difficulty dificuldade;

    public gameWindow() throws IOException {

        welcomeScreen();

    }

    private void welcomeScreen() {
        JFrame welcomeFrame = new JFrame("Bem-vindo");
        welcomeFrame.setSize(300, 200);
        welcomeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeFrame.setLayout(new BorderLayout());
        //centraliza a tela
        welcomeFrame.setLocationRelativeTo(null);

        JPanel difficultyPanel = new JPanel();
        difficultyPanel.setLayout(new GridLayout(4, 1));
        JLabel label = new JLabel("Escolha a dificuldade ");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        difficultyPanel.add(label);

        JButton easyButton = new JButton("Facil : Percepcao 3");
        JButton mediumButton = new JButton("Medio : Percepcao 2");
        JButton hardButton = new JButton("Dificil : Percepcao 1");

        difficultyPanel.add(easyButton);
        difficultyPanel.add(mediumButton);
        difficultyPanel.add(hardButton);

        welcomeFrame.add(difficultyPanel, BorderLayout.CENTER);

        ActionListener difficultyListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton source = (JButton) e.getSource();
                String buttonText = source.getText();

                switch (buttonText) {
                    case "Fácil (Percepção: 3)":
                        dificuldade = Difficulty.FACIL;
                        break;
                    case "Médio (Percepção: 2)":
                        dificuldade = Difficulty.MEDIO;
                        break;
                    case "Difícil (Percepção: 1)":
                        dificuldade = Difficulty.DIFICIL;
                        break;
                    default:
                        // Caso inesperado (não deve ocorrer com botões fixos)
                        dificuldade = Difficulty.FACIL; // Valor padrão
                        break;
                }
                welcomeFrame.dispose(); // Fecha a tela de dificuldade
                showMenuScreen(); // Mostra o menu principal
            }
        };

        easyButton.addActionListener(difficultyListener);
        mediumButton.addActionListener(difficultyListener);
        hardButton.addActionListener(difficultyListener);

        welcomeFrame.setVisible(true);
    }

    private void showMenuScreen() {
    JFrame menuFrame = new JFrame("Menu Principal");
        menuFrame.setSize(300, 200);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setLayout(new BorderLayout());
        menuFrame.setLocationRelativeTo(null);
        
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(4, 1));
        JLabel label = new JLabel("Dificuldade: " + dificuldade + " (Percepção: " 
                + dificuldade.getPercepcao() + ")");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        menuPanel.add(label);
        
        JButton playButton = new JButton("1. Jogar");
        JButton debugButton = new JButton("2. DEBUG");
        JButton exitButton = new JButton("3. Sair");
        
        menuPanel.add(playButton);
        menuPanel.add(debugButton);
        menuPanel.add(exitButton);
        
        menuFrame.add(menuPanel, BorderLayout.CENTER);
        
        playButton.addActionListener(e -> {
            menuFrame.dispose();
            try {
                initializeGame();
            } catch (IOException ex) {
                ex.printStackTrace();
                System.exit(1);
            }
        });

        debugButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(menuFrame, "Modo DEBUG: Dificuldade = " + dificuldade + ", Percepção = " + dificuldade.getPercepcao());
        });

        exitButton.addActionListener(e -> System.exit(0));

        menuFrame.setVisible(true);
        
    }

    private void initializeGame() throws IOException {
//Load random map
        char[][] mapData = MapLoader.loadRandomMap();
        gameGrid = new GameGrid(10, 10, mapData);
        hero = new Hero(gameGrid, dificuldade);
        zumbis = new ArrayList<>();

        try {
            mapData = MapLoader.loadRandomMap();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar mapa. Criando um mapa vazio...");
            System.exit(1);
        }
//window config
        frame = new JFrame("Mapa Grid");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setFocusable(true);
        frame.addKeyListener(this);

//grid Panel
        gameGrid = new GameGrid(10, 10, mapData);
        //TODO (DEIXAR O USUARIO ESCOLHER A DIFICULDADE)
        hero = new Hero(gameGrid, Difficulty.FACIL);

//Control panel (movement buttons)
        JPanel controlPanel = new JPanel();
        JButton upButton = new JButton("Up");
        JButton downButton = new JButton("Down");
        JButton leftButton = new JButton("Left");
        JButton rightButton = new JButton("Right");

        controlPanel.add(upButton);
        controlPanel.add(downButton);
        controlPanel.add(leftButton);
        controlPanel.add(rightButton);

        //Add actions to the buttons
        upButton.addActionListener(e -> hero.moveHero(-1, 0));
        downButton.addActionListener(e -> hero.moveHero(1, 0));
        leftButton.addActionListener(e -> hero.moveHero(0, -1));
        rightButton.addActionListener(e -> hero.moveHero(0, 1));

        //Add Panels to the window
        frame.add(gameGrid.getPanel(), BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            //move UP
            case KeyEvent.VK_W:
                hero.moveHero(-1, 0);
                moveZombiesToHero();
                break;
            //move LEFT
            case KeyEvent.VK_A:
                hero.moveHero(0, -1);
                moveZombiesToHero();
                break;
            //move DOWN
            case KeyEvent.VK_S:
                hero.moveHero(1, 0);
                moveZombiesToHero();
                break;
            //move RIGHT
            case KeyEvent.VK_D:
                hero.moveHero(0, 1);
                moveZombiesToHero();
                break;
        }
        

        for (int i = 0; i < zumbis.size(); i++) {
            Zombie zumbi = zumbis.get(i);
            if (zumbi.getX() == hero.getX() && zumbi.getY() == hero.getY() && zumbi.isAlive()) {
                zumbi.getAtributos().perdeSaude(hero.getAtributos().getDano());
                System.out.println("Herói atacou zumbi em [" + zumbi.getX() + "," + zumbi.getY() + "]. Saúde do zumbi: " + zumbi.getAtributos().getSaude());
                if (!zumbi.isAlive()) {
                    gameGrid.getButton(zumbi.getX(), zumbi.getY()).setIcon(null);
                    zumbis.remove(i--);
                }
            }
        }
    }

    
       private void moveZombiesToHero(){
           for(Zombie zumbi : zumbis){
               if(zumbi.isAlive()){
                   int heroX = hero.getX();
                   int heroY = hero.getY();
                   int zombieX = zumbi.getX();
                   int zombieY = zumbi.getY();
                   //calcula direcao em x e y
                   int dx = Integer.compare(heroX, zombieX);
                   int dy = Integer.compare(heroY, zombieY);
                   
                   if(dx != 0){
                       zumbi.zombieMove(dx, 0);
                   }else if(dy != 0){
                       zumbi.zombieMove(0, dy);
                   }
               }
           }
           frame.repaint();
       }
    @Override
    public void keyReleased(KeyEvent e) {
        // not used
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //not used
    }

}
