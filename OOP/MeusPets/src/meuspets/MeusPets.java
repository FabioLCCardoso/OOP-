/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package meuspets;

/**
 *
 * @author fabio
 */
public class MeusPets {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Animal[] animais = new Animal[4];
        
        animais[0] = new Cachorro("Gohan", "Shiba Inu");
        animais[1] = new Cachorro("Shiro", "Akita Inu");
        animais[2] = new Gato("Spike", "Vira-lata");
        animais[3] = new Gato("Pepita", "Vira-lata");
        
        for(int i = 0; i < animais.length; i++ ){
            
            System.out.println( animais[i].getNome() + " faz: " + animais[i].atuar());
            
        }
    }
    
}
