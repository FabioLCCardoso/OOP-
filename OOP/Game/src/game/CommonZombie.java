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
class CommonZombie extends Zombie {
    public CommonZombie(int x, int y, GameGrid gameGrid) {
        super(x, y, gameGrid, 'Z');
        this.health = 2;
        updateIcon(true);
    }

    @Override
    public void updateIcon(boolean visible) {
        if (alive && (gameGrid.debugMode || visible)) {
            JButton button = gameGrid.getButton(x, y);
            button.setBackground(Color.GREEN);
            button.setToolTipText("Common Zombie");
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/game/assets/commonZombie.png"));
            Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
            button.setEnabled(true);
        }
    }
}
