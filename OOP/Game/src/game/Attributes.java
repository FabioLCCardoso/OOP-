/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;

/**
 *
 * @author fabio
 */
public class Attributes {
    private int saude;
    private int dano;
    private int percepcao;
    
    public Attributes(int saude, int dano, Difficulty dificuldade){
        this.saude = saude;
        this.dano = dano;
        this.percepcao = dificuldade.getPercepcao();
    }
    
    public int getSaude(){
        return saude;
    }
    public void setSaude(int saude){
        //se saude = 0, saude = 0. se nao saude = saude
        this.saude = saude < 0 ? 0 : saude;
    }
    public void perdeSaude(int valor ){
        this.saude -= valor;
        //vida maxima: 5
        if(this.saude > 5)
            this.saude = 5;
    }
    public void ganhaSaude(int valor){
        this.saude += valor;
        if(this.saude > 5)
            this.saude = 5;
    }
    
    public int getDano(){
        return dano;
    }
    public void setDano(int Dano){
        this.dano = dano;
    }
    public int getPercepcao(){
        return percepcao;
    }
    public void setPercepcao(int percepcao){
        this.percepcao = percepcao;
    }
}
