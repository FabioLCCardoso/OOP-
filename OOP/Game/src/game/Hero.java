/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

/**
 *
 * @author fabio
 */
import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.*;

public class Hero {

    private int x, y;
    private int health;
    protected int ammo;
    private boolean hasBat;
    private boolean hasRevolver;
    private int bandages;
    private GameGrid gameGrid;
    private char[][] mapData;
    private int chestsOpened = 0;
    private List<String> chestContents = new ArrayList<>(Arrays.asList("bat", "bandage", "ammo", "ammo"));

    public Hero(GameGrid gameGrid, char[][] mapData) {
        this.gameGrid = gameGrid;
        this.mapData = mapData;
        this.x = 0;
        this.y = 0;
        this.health = 5;
        this.ammo = 0;
        this.hasBat = false;
        this.bandages = 0;
        this.hasRevolver = false;
        this.chestsOpened = 0;
        Collections.shuffle(chestContents); // Embaralha no início
        updatePosition();
    }

    public void moveHero(int deltaX, int deltaY) {
        int newX = x + deltaX;
        int newY = y + deltaY;

        if (newX >= 0 && newX < 10 && newY >= 0 && newY < 10) {
            char target = mapData[newX][newY];
            if (target != 'P') {
                gameGrid.getButton(x, y).setIcon(null);
                gameGrid.getButton(x, y).setEnabled(false);
                gameGrid.getButton(x, y).setBackground(Color.WHITE);
                mapData[x][y] = 'V';

                x = newX;
                y = newY;
                if (target == 'B') {
                    openChest();
                    mapData[x][y] = 'V';
                }
                updatePosition();
            }
        }
    }

    private void openChest() {
    String content = chestContents.get(chestsOpened++);
    switch (content) {
        case "bat": hasBat = true; JOptionPane.showMessageDialog(null, "Você encontrou um taco de beisebol!"); break;
        case "bandage": bandages++; JOptionPane.showMessageDialog(null, "Você encontrou uma atadura!"); break;
        case "ammo":
            if(hasRevolver) {
                ammo++; 
                JOptionPane.showMessageDialog(null, "Você encontrou uma munição!");
                
            }
            else {
                ammo = 1;
                JOptionPane.showMessageDialog(null, "Você encontrou um revólver com uma munição!");
                hasRevolver = true;
            }
    }
}

    public void heal() {
        if (bandages > 0 && health < 5) {
            bandages--;
            health++;
            JOptionPane.showMessageDialog(null, "Você usou uma atadura. Saúde restaurada: " + health);
        } else {
            JOptionPane.showMessageDialog(null, "Você não tem ataduras ou está com saúde máxima!");
        }
    }

    private void updatePosition() {
        JButton button = gameGrid.getButton(x, y);
        
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/game/assets/hero.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledImage));
        
        button.setEnabled(true);
        button.setBackground(Color.BLUE);
        mapData[x][y] = 'H';
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHealth() {
        return health;
    }

    public int getAmmo() {
        return ammo;
    }

    public boolean hasBat() {
        return hasBat;
    }

    public void takeDamage() {
        health--;
    }

    public char[][] getMapData() {
        return mapData;
    } // Adicionado getter para mapData
}
