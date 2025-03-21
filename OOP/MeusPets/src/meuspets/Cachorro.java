/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package meuspets;

/**
 *
 * @author fabio
 */
public class Cachorro extends Animal{
    
    public Cachorro(String nome, String raca){
        super(nome, raca);
    }
    
    @Override 
    
   public String atuar(){
       return "Auau";
   }
}
