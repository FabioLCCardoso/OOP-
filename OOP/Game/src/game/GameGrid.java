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
  
 public GameGrid(int rows, int cols, char[][]mapData){
     this.rows = rows;
     this.cols = cols;
     panel = new JPanel(new GridLayout(rows, cols));
     grid = new JButton[rows][cols];
     
     //Creating grid buttons
     
     for(int i = 0; i<rows; i++){
         for(int j = 0; j < cols; j++){
             grid[i][j] = new JButton();
             grid[i][j].setEnabled(false);
             char cell = mapData[i][j];
             
             switch(cell){
                    case 'V': // Empty
                        grid[i][j].setBackground(Color.WHITE);
                        grid[i][j].setToolTipText("Empty Space");
                        break;
                    case 'P': // Wall
                        grid[i][j].setBackground(Color.BLACK);
                        grid[i][j].setToolTipText("Wall");
                        break;
                    case 'R': // Crawling Zombie
                        grid[i][j].setBackground(Color.ORANGE);
                        grid[i][j].setToolTipText("Crawling Zombie");
                        break;
                    case 'C': // Runner Zombie
                        grid[i][j].setBackground(Color.RED);
                        grid[i][j].setToolTipText("Runner Zombie");
                        break;
                    case 'Z': // Common Zombie
                        grid[i][j].setBackground(Color.GREEN);
                        grid[i][j].setToolTipText("Common Zombie");
                        break;
                    case 'G': // Giant Zombie
                        grid[i][j].setBackground(Color.DARK_GRAY);
                        grid[i][j].setToolTipText("Giant Zombie");
                        break;
                    case 'B': // Chest
                        grid[i][j].setBackground(Color.YELLOW);
                        grid[i][j].setToolTipText("Chest");
                        break;
                    default:
                        grid[i][j].setBackground(Color.GRAY);
                        grid[i][j].setToolTipText("Unknown");
                        break;
                }
             
             
             panel.add(grid[i][j]);
             
         }
     }
 }
    
    public JPanel getPanel(){
        return panel;
    }
    public JButton getButton(int x, int y){
        return grid[x][y];
        
    }
    public int getRows(){
        return rows;
    }
    public int getCols(){
        return cols;
    }
    
    
}
