/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package meuspets;

/**
 *
 * @author fabio
 */
public abstract class Animal {
    String nome;
    String raca;

    //metodo contrutor
    public Animal(String nome, String raca){
        this.nome = nome;
        this.raca = raca;
    }
    
    //getNome
    public String getNome(){
        return nome;
    }
    
    
    public abstract String atuar(); 
}
