/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

import javax.swing.ImageIcon;

/**
 *
 * @author fabio
 */
public class GiantZombie extends Zombie {

    public GiantZombie(GameGrid gameGrid, int x, int y){
        super(gameGrid, x, y, 2);
        this.imagem = new ImageIcon(getClass().getResource("/game/assets/giantZombie.png"));
        updatePosition();
    }
    
}

