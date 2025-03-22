/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package game;

/**
 *
 * @author fabio
 */
public enum Difficulty {
    FACIL(3), MEDIO(2), DIFICIL(1);
    
    private final int percepcao;
    
    Difficulty(int percepcao){
        this.percepcao = percepcao;
    }
    public int getPercepcao(){
        return percepcao;
    }
}
