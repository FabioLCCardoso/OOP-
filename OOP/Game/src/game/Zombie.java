/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;
import javax.swing.*;
import java.awt.Color;
/**
 *
 * @author fabio
 */
public abstract class Zombie {
    protected int x, y;
    protected GameGrid gameGrid;
    protected Attributes atributos;
    protected ImageIcon imagem;
    
    public Zombie(GameGrid gameGrid, int x, int y, int saude){
        this.gameGrid = gameGrid;
        this.x = x;
        this.y = y;
        this.atributos = new Attributes(saude, 0, Difficulty.DIFICIL);
        updatePosition();
    }
    protected void updatePosition(){
        gameGrid.getButton(x , y).setIcon(imagem);
        gameGrid.getButton(x , y).setEnabled(true);
    }
    protected boolean isAlive(){
        return atributos.getSaude() > 0;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public Attributes getAtributos(){
        return atributos;
    }
    protected void zombieMove(int dx, int dy){
        int newX = x + dx;
        int newY = y + dy;
        if(isValidMove(newX, newY)){
            gameGrid.getButton(x,y).setIcon(null);
            gameGrid.getButton(x, y).setEnabled(false);
            x = newX;
            y = newY;
            updatePosition();
        }
    }
    protected boolean isValidMove(int newX, int newY){
        //check if its inside the limits and if there is a wall
        if((newX >= 0 && newX < gameGrid.getRows() && newY >= 0 && newY < gameGrid.getCols())) {
            return gameGrid.getCell(newX, newY) != 'P';
        }
        return false;
    }
}
