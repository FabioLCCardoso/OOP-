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
 * @author fabio
 */
public abstract class Zombie {
    protected int x, y;
    protected int health;
    protected GameGrid gameGrid;
    protected boolean alive;
    protected char type;

    public Zombie(int x, int y, GameGrid gameGrid, char type) {
        this.x = x;
        this.y = y;
        this.gameGrid = gameGrid;
        this.alive = true;
        this.type = type;
        updateIcon(true); // Inicializa como visível por padrão (será ajustado depois)
    }

    public void moveTowards(int targetX, int targetY, char[][] mapData) {
        int dx = Integer.compare(targetX, x);
        int dy = Integer.compare(targetY, y);
        int newX = x + dx;
        int newY = y + dy;

        if (isValidMove(newX, newY, mapData)) {
            mapData[x][y] = 'V'; // Limpa a posição anterior
            JButton button = gameGrid.getButton(x, y);
            button.setEnabled(false);
            x = newX;
            y = newY;
            mapData[x][y] = type; // Atualiza nova posição
            updateIcon(true); // Será ajustado pelo GameWindow
        }
    }

    protected boolean isValidMove(int newX, int newY, char[][] mapData) {
        return newX >= 0 && newX < 10 && newY >= 0 && newY < 10 && 
               (mapData[newX][newY] == 'V' || mapData[newX][newY] == 'H');
    }

    public void clearPosition(char[][] mapData) {
        JButton button = gameGrid.getButton(x, y);
        button.setToolTipText("Empty Space");
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/game/assets/hero.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledImage));
        button.setBackground(Color.BLUE);
        button.setEnabled(true);
        mapData[x][y] = 'H';
    }

    public abstract void updateIcon(boolean visible);

    public int getX() { return x; }
    public int getY() { return y; }
    public boolean isAlive() { return alive; }
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) alive = false;
    }
}