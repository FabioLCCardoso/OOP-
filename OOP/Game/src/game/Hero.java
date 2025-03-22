/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;
import javax.swing.*;
import javax.swing.ImageIcon;

/**
 *
 * @author fabio
 */
public class Hero {
    private int x, y;
    private GameGrid gameGrid;
    private Attributes atributos;
    
    public Hero(GameGrid gameGrid, Difficulty dificuldade){
        this.gameGrid = gameGrid;
        //start hero on left upper side
        this.x = 0;
        this.y = 0;
        
        //TODO DANO NORMAL = 1, CRIT = 2, REVOLVER = CRIT
        this.atributos = new Attributes(5,1, dificuldade );
     
        //Define hero in initial position
        updatePosition();
    }
    
    public void moveHero(int deltaX, int deltaY){
        int newX = x + deltaX;
        int newY = y + deltaY;
        
        //verify if it's inside the limits
 if (isValidMove(newX, newY)) {
            // Remove icon from last position
            gameGrid.getButton(x, y).setIcon(null);
            gameGrid.getButton(x, y).setEnabled(false);
            
            // Update position
            x = newX;
            y = newY;
            
            // Update hero's position in the grid
            updatePosition();
        }
    }
    
    private void updatePosition(){
        gameGrid.getButton(x, y).setIcon(new ImageIcon(getClass().getResource("/game/assets/hero.png")));
        gameGrid.getButton(x, y).setEnabled(true);
    }
    protected boolean isValidMove(int newX, int newY){
        //check if its inside the limits and if there is a wall
        if((newX >= 0 && newX < gameGrid.getRows() && newY >= 0 && newY < gameGrid.getCols())) {
            return gameGrid.getCell(newX, newY) != 'P';
        }
        return false;
    }
    public Attributes getAtributos(){
        return atributos;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
}

