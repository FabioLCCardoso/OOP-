/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Arrays;
/**
 *
 * @author fabio
 */
public class gameWindow implements KeyListener {
    private JFrame frame; 
    private GameGrid gameGrid;
    private Hero hero;
    
 
public gameWindow() throws IOException{
    
//Load random map
    char[][] mapData = MapLoader.loadRandomMap();
    
        try {
        mapData = MapLoader.loadRandomMap();
    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Erro ao carregar mapa. Criando um mapa vazio...");
        System.exit(1);
    }
//window config
    frame = new JFrame("Mapa Grid");
    frame.setSize(400, 400);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    frame.setFocusable(true);
    frame.addKeyListener(this);
    
    
//grid Panel
    gameGrid = new GameGrid(10, 10, mapData);
    hero = new Hero(gameGrid);
    
    
//Control panel (movement buttons)
    JPanel controlPanel = new JPanel();
    JButton upButton = new JButton("Up");
    JButton downButton = new JButton("Down");
    JButton leftButton = new JButton("Left");
    JButton rightButton = new JButton("Right");
    
    controlPanel.add(upButton);
    controlPanel.add(downButton);
    controlPanel.add(leftButton);
    controlPanel.add(rightButton);
    
    //Add actions to the buttons
    upButton.addActionListener(e -> hero.moveHero(-1, 0));
    downButton.addActionListener(e -> hero.moveHero(1, 0));
    leftButton.addActionListener(e -> hero.moveHero(0, -1));
    rightButton.addActionListener(e -> hero.moveHero(0, 1));
    
    //Add Panels to the window
    frame.add(gameGrid.getPanel(), BorderLayout.CENTER);
    frame.add(controlPanel, BorderLayout.SOUTH);
    
    frame.setVisible(true);
    
    }

@Override
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        switch(key){
            //move UP
            case KeyEvent.VK_W:
                hero.moveHero(-1,0);
                break;
            //move LEFT
            case KeyEvent.VK_A:
                hero.moveHero(0,-1);
                break;
            //move DOWN
            case KeyEvent.VK_S:
                hero.moveHero(1,0);
                break;
            //move RIGHT
            case KeyEvent.VK_D:
                hero.moveHero(0,1);
                break;
        }
        
    }
 @Override
    public void keyReleased(KeyEvent e) {
        // not used
    }
        
    @Override
    public void keyTyped(KeyEvent e) {
       //not used
    }
    
}