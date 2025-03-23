/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package game;
import java.io.*;
import java.util.*;

/**
 *
 * @author fabio
 */
public class MapLoader {
    private static final String MAP_FOLDER = System.getProperty("user.dir") + File.separator +"src" + File.separator + "game" + File.separator + "maps";
    private static final int MAP_SIZE = 10;
    public static char[][] loadRandomMap() throws IOException {
        File folder = new File(MAP_FOLDER);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));
        
        if(files == null){
           throw new IOException("nenhum mapa foi encontrado: "+ MAP_FOLDER);
           
        }
        
        File selectedFile = files[new Random().nextInt(files.length)];
        
        System.out.println("Loading map: " + selectedFile.getName());
        
        return loadMap(selectedFile);
        
    }
        private static char[][] loadMap(File file){
            char[][] map = new char[MAP_SIZE][MAP_SIZE];
            
            try(BufferedReader br = new BufferedReader(new FileReader(file))){
                for( int i = 0; i < 10; i++){
                String line = br.readLine();
                if(line == null){
                    throw new IOException("Formato de mapa invalido, tem que ser 10x10");
                }
                
                line = line.replace(" ", "");
                
                for(int j = 0; j < 10; j++){
                    map[i][j] = line.charAt(j);
                }
            }
            }
            catch(IOException e){
                e.printStackTrace();
                System.out.println("Erro ao carregar mapa: "+file.getName());
                return generateEmptyMap();
            }
            return map;
    }
        private static char[][] generateEmptyMap(){
            char[][] emptyMap = new char[10][10];
            for(char[] row: emptyMap){
                //fill with empty spaces 
                Arrays.fill(row,'V');
            }
            return emptyMap;
        }
}
