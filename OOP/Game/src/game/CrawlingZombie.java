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
public class CrawlingZombie extends Zombie{
    public CrawlingZombie(GameGrid gameGrid, int x, int y){
        super(gameGrid, x, y, 1);
        this.imagem = new ImageIcon(getClass().getResource("/game/assets/crawlingZombie.png"));
        updatePosition();
    }
    
}
