/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package game;

/**
 *
 * @author fabio
 */
import javax.swing.*;

public class Combat {
    private Hero hero;
    private Zombie zombie;
    private int perception;

    public Combat(Hero hero, Zombie zombie, int perception) {
        this.hero = hero;
        this.zombie = zombie;
        this.perception = perception;
    }

    public void fight(boolean zombieFirst) {
        if (zombieFirst) {
            zombieAttack();
            if (hero.getHealth() <= 0) return;
        }

        while (zombie.isAlive() && hero.getHealth() > 0) {
            String[] options = hero.getAmmo() > 0 ? new String[]{"Mão/Taco", "Revólver", "Fugir"} : new String[]{"Mão/Taco", "Fugir"};
            int choice = JOptionPane.showOptionDialog(null, "Escolha sua ação:", "Combate",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            if (choice == 0) { // Hand/Bat
                int roll = (int) (Math.random() * 6) + 1;
                if (zombie instanceof GiantZombie) {
                    if (hero.hasBat()) {
                        zombie.takeDamage(2);
                        JOptionPane.showMessageDialog(null, "Golpe crítico! Zumbi perdeu 2 de vida.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Ataque com as mãos não afeta o Zumbi Gigante!");
                    }
                } else if (roll == 6 || hero.hasBat()) {
                    zombie.takeDamage(2);
                    JOptionPane.showMessageDialog(null, "Golpe crítico! Zumbi perdeu 2 de vida.");
                }
            } else if (choice == 1 && options.length == 3) { // Revolver
                if (!(zombie instanceof RunnerZombie)) {
                    hero.ammo--;
                    zombie.takeDamage(2);
                    JOptionPane.showMessageDialog(null, "Tiro certeiro! Zumbi perdeu 2 de vida.");
                } else {
                    JOptionPane.showMessageDialog(null, "Zumbi Corredor é muito rápido para o revólver!");
                }
            } else { // Escape
                if (canEscape()) {
                    escape();
                    JOptionPane.showMessageDialog(null, "Você conseguiu fugir do combate!");
                    return; // Sai do combate
                } else {
                    JOptionPane.showMessageDialog(null, "Não há para onde fugir! Você deve continuar lutando.");
                }
            }

            if (zombie.isAlive()) {
                zombieAttack();
            }
        }
    }

    private void zombieAttack() {
        int dodgeRoll = (int) (Math.random() * 3) + 1;
        if (dodgeRoll <= perception) {
            JOptionPane.showMessageDialog(null, "Você desviou do ataque do zumbi!");
        } else {
            hero.takeDamage();
            JOptionPane.showMessageDialog(null, "Zumbi te acertou! Saúde: " + hero.getHealth());
        }
    }

    private boolean canEscape() {
        int x = hero.getX();
        int y = hero.getY();
        char[][] mapData = getMapData(); // Supondo que Hero tenha um getter para mapData

        // Verifica as 4 posições adjacentes
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Cima, baixo, esquerda, direita
        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];
            if (newX >= 0 && newX < 10 && newY >= 0 && newY < 10 && mapData[newX][newY] == 'V') {
                return true; // Há pelo menos uma posição válida
            }
        }
        return false; // Nenhuma posição válida encontrada
    }

    private void escape() {
        int x = hero.getX();
        int y = hero.getY();
        char[][] mapData = getMapData();

        // Tenta mover para a primeira posição válida encontrada
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];
            if (newX >= 0 && newX < 10 && newY >= 0 && newY < 10 && mapData[newX][newY] == 'V') {
                hero.moveHero(dir[0], dir[1]); // Move o herói para a posição válida
                return;
            }
        }
    }

    // Método auxiliar para acessar mapData (adicionar em Hero.java)
    private char[][] getMapData() {
        try {
            java.lang.reflect.Field field = Hero.class.getDeclaredField("mapData");
            field.setAccessible(true);
            return (char[][]) field.get(hero);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}