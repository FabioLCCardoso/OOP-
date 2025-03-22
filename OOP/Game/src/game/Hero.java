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
    
    public Hero(GameGrid gameGrid){
        this.gameGrid = gameGrid;
        //start hero on left upper side
        this.x = 0;
        this.y = 0;
        
        
        //Define hero in initial position
        updatePosition();
    }
    
    public void moveHero(int deltaX, int deltaY){
        int newX = x + deltaX;
        int newY = y + deltaY;
        
        //verify if it's inside the limits
        if(newX >= 0 && newX < 10 && newY >=0 && newY < 10 ){
            //Remove icon from last position
            gameGrid.getButton(x, y).setIcon(null);
            gameGrid.getButton(x, y).setEnabled(false);
            
            //update position
            x = newX;
            y = newY;
            
            //update hero's position in the grid
            updatePosition();
            
        }
    }
    
    private void updatePosition(){
        gameGrid.getButton(x, y).setIcon(new ImageIcon(getClass().getResource("/game/assets/hero.png")));
        gameGrid.getButton(x, y).setEnabled(true);
    }
}

