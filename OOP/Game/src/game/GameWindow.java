/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fabio
 */
public class GameWindow {
    private JFrame frame;
    private GameGrid gameGrid;
    private Hero hero;
    private List<Zombie> zombies;
    private JLabel statsLabel;
    private int perception;
    private boolean debugMode;
    private char[][] mapData;

    public GameWindow(int perception, boolean debugMode) throws IOException {
        this.perception = perception;
        this.debugMode = debugMode;

        mapData = MapLoader.loadRandomMap();
        zombies = new ArrayList<>();

        frame = new JFrame("Zumbicídio");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setFocusable(true);

        gameGrid = new GameGrid(10, 10, mapData, debugMode);
        hero = new Hero(gameGrid, mapData);
        initializeZombies();

        statsLabel = new JLabel("Saúde: " + hero.getHealth() + " | Percepção: " + perception + " | Munição: " + hero.getAmmo());
        JPanel statsPanel = new JPanel();
        statsPanel.add(statsLabel);
        frame.add(statsPanel, BorderLayout.NORTH);

        JPanel controlPanel = new JPanel();
        JButton upButton = new JButton("Up");
        JButton downButton = new JButton("Down");
        JButton leftButton = new JButton("Left");
        JButton rightButton = new JButton("Right");
        JButton healButton = new JButton("Curar");
        JButton exitButton = new JButton("Sair");

        controlPanel.add(upButton);
        controlPanel.add(downButton);
        controlPanel.add(leftButton);
        controlPanel.add(rightButton);
        controlPanel.add(healButton);
        controlPanel.add(exitButton);

        upButton.addActionListener(e -> moveHero(-1, 0));
        downButton.addActionListener(e -> moveHero(1, 0));
        leftButton.addActionListener(e -> moveHero(0, -1));
        rightButton.addActionListener(e -> moveHero(0, 1));
        healButton.addActionListener(e -> { hero.heal(); updateStats(); });
        exitButton.addActionListener(e -> endGame());

        frame.add(gameGrid.getPanel(), BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
        updateGameView(); // Atualiza a visão inicial
        updateStats();
    }

    private void initializeZombies() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                switch (mapData[i][j]) {
                    case 'R': zombies.add(new CrawlingZombie(i, j, gameGrid)); break;
                    case 'C': zombies.add(new RunnerZombie(i, j, gameGrid)); break;
                    case 'Z': zombies.add(new CommonZombie(i, j, gameGrid)); break;
                    case 'G': zombies.add(new GiantZombie(i, j, gameGrid)); break;
                }
            }
        }
    }

    private void moveHero(int deltaX, int deltaY) {
        hero.moveHero(deltaX, deltaY);
        updateGameView();
        Zombie encounteredZombie = checkForZombie(hero.getX(), hero.getY());
        if (encounteredZombie != null) {
            initiateCombat(encounteredZombie, false);
        } else {
            moveZombies();
        }
        updateGameView();
        updateStats();
        checkGameEnd();
    }

    private void moveZombies() {
        for (Zombie zombie : new ArrayList<>(zombies)) {
            if (zombie.isAlive() && !(zombie instanceof GiantZombie)) {
                zombie.moveTowards(hero.getX(), hero.getY(), mapData);
                if (zombie.getX() == hero.getX() && zombie.getY() == hero.getY()) {
                    initiateCombat(zombie, true);
                }
            }
        }
        updateGameView();
    }

    private Zombie checkForZombie(int x, int y) {
        for (Zombie zombie : zombies) {
            if (zombie.getX() == x && zombie.getY() == y && zombie.isAlive()) {
                return zombie;
            }
        }
        return null;
    }

    private void initiateCombat(Zombie zombie, boolean zombieFirst) {
        Combat combat = new Combat(hero, zombie, perception);
        combat.fight(zombieFirst);
        if (!zombie.isAlive()) {
            zombies.remove(zombie);
            zombie.clearPosition(mapData);
        }
        updateGameView();
        updateStats();
    }

    private void updateGameView() {
        // Calcula a matriz de visibilidade uma vez
        boolean[][] visible = calculateLineOfSight(mapData, hero.getX(), hero.getY());
        gameGrid.updateGrid(mapData, hero.getX(), hero.getY());
        for (Zombie zombie : zombies) {
            // Passa a visibilidade da posição do zumbi
            boolean isVisible = visible[zombie.getX()][zombie.getY()];
            zombie.updateIcon(isVisible);
        }
    }

    // Método auxiliar para calcular a linha de visão (duplicado do GameGrid para simplicidade)
    private boolean[][] calculateLineOfSight(char[][] mapData, int heroX, int heroY) {
        boolean[][] visible = new boolean[10][10];
        visible[heroX][heroY] = true;

        char[] blockers = {'P', 'B', 'C', 'Z', 'G'};

        for (int j = heroY - 1; j >= 0; j--) {
            visible[heroX][j] = true;
            if (isBlocker(mapData[heroX][j], blockers)) break;
        }
        for (int j = heroY + 1; j < 10; j++) {
            visible[heroX][j] = true;
            if (isBlocker(mapData[heroX][j], blockers)) break;
        }
        for (int i = heroX - 1; i >= 0; i--) {
            visible[i][heroY] = true;
            if (isBlocker(mapData[i][heroY], blockers)) break;
        }
        for (int i = heroX + 1; i < 10; i++) {
            visible[i][heroY] = true;
            if (isBlocker(mapData[i][heroY], blockers)) break;
        }

        return visible;
    }

    private boolean isBlocker(char cell, char[] blockers) {
        for (char blocker : blockers) {
            if (cell == blocker) return true;
        }
        return false;
    }

    private void updateStats() {
        statsLabel.setText("Saúde: " + hero.getHealth() + " | Percepção: " + perception + " | Munição: " + hero.getAmmo());
    }

    private void checkGameEnd() {
        if (hero.getHealth() <= 0) {
            endGame("Você perdeu! Todos os seus pontos de saúde foram esgotados.");
        } else if (zombies.isEmpty()) {
            endGame("Você ganhou! Todos os zumbis foram derrotados.");
        }
    }

    private void endGame(String message) {
        int choice = JOptionPane.showOptionDialog(frame, message, "Fim de Jogo",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                new String[]{"Reiniciar Jogo", "Novo Jogo"}, "Reiniciar Jogo");
        frame.dispose();
        if (choice == 0) {
            try {
                new GameWindow(perception, debugMode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            new WelcomeScreen();
        }
    }

    private void endGame() {
        endGame("Jogo encerrado manualmente.");
    }
}