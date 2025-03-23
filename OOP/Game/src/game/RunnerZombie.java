/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author joao
 */
class RunnerZombie extends Zombie {
    public RunnerZombie(int x, int y, GameGrid gameGrid) {
        super(x, y, gameGrid, 'C');
        this.health = 2;
        updateIcon(true);
    }

    @Override
    public void moveTowards(int targetX, int targetY, char[][] mapData) {
        for (int i = 0; i < 2; i++) {
            int dx = Integer.compare(targetX, x);
            int dy = Integer.compare(targetY, y);
            int newX = x + dx;
            int newY = y + dy;
            if (isValidMove(newX, newY, mapData)) {
                mapData[x][y] = 'V';
                x = newX;
                y = newY;
                mapData[x][y] = 'C';
                updateIcon(true);
            } else {
                break;
            }
        }
    }

    @Override
    public void updateIcon(boolean visible) {
        if (alive && (gameGrid.debugMode || visible)) {
            JButton button = gameGrid.getButton(x, y);
            button.setBackground(Color.RED);
            button.setToolTipText("Runner Zombie");
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/game/assets/runnerZombie.png"));
            Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
            button.setEnabled(true);
        }
    }
}
