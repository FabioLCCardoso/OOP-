/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author fabio
 */
public class GameGrid {
    private JPanel panel;
    private JButton[][] grid;
    private int rows, cols;
    public boolean debugMode;

    public GameGrid(int rows, int cols, char[][] mapData, boolean debugMode) {
        this.rows = rows;
        this.cols = cols;
        this.debugMode = debugMode;
        panel = new JPanel(new GridLayout(rows, cols));
        grid = new JButton[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new JButton();
                grid[i][j].setEnabled(false);
                panel.add(grid[i][j]);
            }
        }
        updateGrid(mapData, 0, 0); // Inicializa com uma posição padrão (será atualizada depois)
    }

    public void updateGrid(char[][] mapData, int heroX, int heroY) {
        boolean[][] visible = calculateLineOfSight(mapData, heroX, heroY);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JButton button = grid[i][j];
                char cell = mapData[i][j];

                if (cell == 'H') {
                    // Sempre atualiza o herói, independentemente da visibilidade
                    button.setBackground(Color.BLUE);
                    ImageIcon originalIcon = new ImageIcon(getClass().getResource("/game/assets/hero.png"));
                    Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    button.setIcon(new ImageIcon(scaledImage));
                    button.setEnabled(true);
                    button.setToolTipText("Hero");
                } else if (debugMode || visible[i][j]) {
                    // Modo debug ou célula visível: exibe normalmente
                    button.setIcon(null); // Reseta o ícone por padrão
                    switch (cell) {
                        case 'V': 
                            button.setBackground(Color.WHITE); 
                            button.setToolTipText("Empty Space"); 
                            break;
                        case 'P': 
                            button.setBackground(Color.WHITE); 
                            ImageIcon wallIcon = new ImageIcon(getClass().getResource("/game/assets/wall.png"));
                            Image scaledWall = wallIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                            button.setIcon(new ImageIcon(scaledWall));
                             button.setEnabled(true);
                            button.setToolTipText("Wall"); 
                            break;
                        case 'B': 
                            button.setBackground(Color.YELLOW); 
                            ImageIcon chestIcon = new ImageIcon(getClass().getResource("/game/assets/chest.png"));
                            Image scaledChest = chestIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                            button.setIcon(new ImageIcon(scaledChest));
                             button.setEnabled(true);

                            button.setToolTipText("Chest"); 
                            break;
                        // Zumbis são atualizados por suas próprias classes
                    }
                } else {
                    // Fora da linha de visão e sem debug: esconde tudo exceto o herói
                    button.setIcon(null);
                    button.setBackground(Color.GRAY); // Cor para áreas não visíveis
                    button.setToolTipText("Unknown");
                }
            }
        }
    }

    // Calcula a linha de visão do herói (horizontal e vertical até o primeiro obstáculo)
    private boolean[][] calculateLineOfSight(char[][] mapData, int heroX, int heroY) {
        boolean[][] visible = new boolean[rows][cols];
        visible[heroX][heroY] = true; // O herói sempre é visível

        // Obstáculos que bloqueiam a visão: parede ('P'), baú ('B'), zumbis exceto 'R'
        char[] blockers = {'P', 'B', 'C', 'Z', 'G'};

        // Linha horizontal à esquerda
        for (int j = heroY - 1; j >= 0; j--) {
            visible[heroX][j] = true;
            if (isBlocker(mapData[heroX][j], blockers)) break;
        }
        // Linha horizontal à direita
        for (int j = heroY + 1; j < cols; j++) {
            visible[heroX][j] = true;
            if (isBlocker(mapData[heroX][j], blockers)) break;
        }
        // Linha vertical acima
        for (int i = heroX - 1; i >= 0; i--) {
            visible[i][heroY] = true;
            if (isBlocker(mapData[i][heroY], blockers)) break;
        }
        // Linha vertical abaixo
        for (int i = heroX + 1; i < rows; i++) {
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

    public JPanel getPanel() { return panel; }
    public JButton getButton(int x, int y) { return grid[x][y]; }
    public int getRows() { return rows; }
    public int getCols() { return cols; }
}