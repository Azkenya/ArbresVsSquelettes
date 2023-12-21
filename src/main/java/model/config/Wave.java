package model.config;

import java.util.ArrayList;
import java.util.Random;
import controller.Updatable;
import model.Entity;
import model.entities.Skeleton;

public class Wave implements Updatable {
    private Skeleton[][][]enemies;
    private ArrayList<Skeleton> enemiesOnMap;
    private boolean isFinished;
    private static Map map;
    private int currentRound;
    private int currentSubWave;

    public Wave(Skeleton[][][] enemies, ArrayList<Skeleton> enemiesOnMap, Map map) {
        this.enemies = enemies;
        this.isFinished = false;
        this.enemiesOnMap = enemiesOnMap;
        Wave.map = map;
        this.currentRound = -5;
        this.currentSubWave = 0;

    }

    /**
     * Constructor of the wave
     * 
     * @param n the level of the wave, 1, 2 or 3
     */
    public Wave(int n, Map map) {
        this(makeWave(n, map), new ArrayList<Skeleton>(), map);
    }

    @Override
    public void update() {
        this.updateEnemies();
        if (this.currentSubWave < this.enemies.length) {
            if(this.currentRound < this.enemies[this.currentSubWave].length){
                if(this.currentRound>=0)
                    this.spawnEnemies();
                this.currentRound++;
            }
            else{
                this.currentRound = -5;
                if(this.noEnemiesOnMap()){
                    this.currentSubWave++;
                    System.out.println("Subwave Finished");
                }
                
            }
        }
        else{
            this.isFinished = true;
        }
    }
    public boolean isFinished() {
        return this.isFinished;
    }
    public boolean noEnemiesOnMap() {
        return this.enemiesOnMap.isEmpty();
    }

    public void updateEnemies() {//FIXME PROBLEME DE JEU
        for (int i = 0; i < this.enemiesOnMap.size(); i++) {
            this.enemiesOnMap.get(i).update();
        }
        enemiesOnMap.removeIf(skeleton -> skeleton.getHp() <= 0);
    }

    public void spawnEnemies() {
        for (int i = 0; i < 5; i++) {
            if (this.enemies[this.currentSubWave][this.currentRound][i] != null) {
                this.enemiesOnMap.add(this.enemies[this.currentSubWave][this.currentRound][i]);
                this.enemies[this.currentSubWave][this.currentRound][i] = null;
            }
        }
    }

    /**
     * Return a random number between 0 and n
     * 
     * @param n the maximum number
     * @return the random number between 0 and n inclusive
     */
    public static int random(int n) {
        Random rand = new Random();
        int Numb = rand.nextInt(n + 1);
        return Numb;
    }

    /**
     * Make a wave of enemies depending on the level
     * (easy=1, medium=2 or hard=3)
     * 
     * @param lvl the level of the wave, 1, 2 or 3
     * @return the wave of enemies depending on the level
     */
    public static Skeleton[][][] makeWave(int lvl, Map map) {
        if (lvl == 1) {
            return makeEasy(map);
        } else if (lvl == 2) {
            return makeMedium(map);
        } else {
            return makeHard(map);
        }
    }

    /**
     * Make an easy mode wave of enemies
     * @param map the map
     * 
     * This wave is made of 7 waves of enemies
     * The first wave is made of 20 enemies
     * The other waves are made of 30 enemies
     * We use the makeBeginningWave, makeMidGameWaveEasy and makeFinalWaveEasy
     * methods to make the waves
     * 
     * A wave represents the whole game.
     * For the easy mode, it is made of a 7 box long array, which represents 7 sub-waves of enemies.
     * Each sub-wave is made of 20 or 30 box long arrays, which represent the rounds of the wave
     * Each round is made of an array of 5 Skeletons, which represent the 5 lanes where enemies spawn
     * 
     * For example, enemies[2][3] represents the 4th round of the 3rd sub-wave of the wave
     * And enemies[2][3][4] represents the enemy on the 5th lane of the 4th round 
     * of the 3rd sub-wave of the wave
     *
     * @return the wave of enemies
     */
    public static Skeleton[][][] makeEasy(Map map) {
        Skeleton [][][] enemies=new Skeleton[7][][];
        enemies[0]=makeBeginningWave(map);
        enemies[1]=makeMidGameWaveEasy(map);
        enemies[2]=makeMidGameWaveEasy(map);
        enemies[3]=makeFinalWaveEasy(map);
        enemies[4]=makeMidGameWaveEasy(map);
        enemies[5]=makeMidGameWaveEasy(map);
        enemies[6]=makeFinalWaveEasy(map);
        return enemies;
    }
    /** 
     *Make the first wave for any difficulty
     * @param map the map, to place the enemies
     * 
     * Make a wave of 20 rounds
     * The first 5 rounds have no enemies, for a smooth start
     * Starting from the 6th round, there is:
     * - A 55% chance to have 1 enemy
     * - A 15% chance to have 2 enemies
     * - A 30% chance to have no enemies
     * 
     * The canPlace boolean is used to make sure that there aren't two 
     * consecutive rounds with enemies, so as not to make the game too hard
     * or unplayable
     * @return a beginning wave of enemies
    */
    public static Skeleton[][] makeBeginningWave(Map map){
        Skeleton[][] enemies = new Skeleton[20][5];
        boolean canPlace=true;
        for(int i=0;i<20;i++){    
            int prob = random(100);
            if(prob <= 55 && canPlace){
                place(enemies, i, 1, map);
                canPlace=false;
            }else if(prob <= 70 && canPlace){
                place(enemies, i, 2, map);
                canPlace=false;
            }else if(!canPlace){
                canPlace=true;
            } 
        }
        return enemies; 
    }
    /**
     * Make a mid-game wave of enemies for the easy mode
     * @param map the map, to place the enemies
     * 
     * Make a wave of 30 rounds
     * Make each round randomized with a probability of:
     * - A 55% chance to have 1 enemy
     * - A 25% chance to have 2 enemies
     * - A 10% chance to have 3 enemies
     * - A 10% chance to have no enemies
     * 
     * The canPlace int is used to make sure that there aren't two
     * consecutive rounds with enemies, so as not to make the game too hard
     * How it works:
     * - If canPlace is 0, then we can place enemies
     * - If we place 1 or 2 enemies, then we can't place enemies for the next round
     * - If we place 3 enemies, then we can't place enemies for the next two rounds
     * 
     * @return the wave of enemies
     */
    public static Skeleton[][] makeMidGameWaveEasy(Map map){
        Skeleton[][] enemies=new Skeleton[30][5];
        int canPlace=0;
        for(int i=0;i<30;i++){
            int prob =random(100);
            if(canPlace!=0)
                canPlace--;
            else if(canPlace==0&&prob<=55){
                canPlace++;
                place(enemies,i,1,map);
            }else if(canPlace==0&&prob<=80){
                canPlace++;
                place(enemies,i,2,map);
            }else if(canPlace==0&&prob<=90){
                canPlace+=2;
                place(enemies,i,3,map);
            }
        }
        return enemies;
    }
    /**
     * Make the final wave for the easy mode
     * @param map the map, to place the enemies
     * 
     * Make a wave of 30 rounds
     * Make each round randomized with a probability of:
     * - A 30% chance to have 1 enemy
     * - A 30% chance to have 2 enemies
     * - A 15% chance to have 3 enemies
     * - A 10% chance to have 4 enemies
     * - A 7% chance to have 5 enemies
     * - A 8% chance to have no enemies
     * 
     * The canPlace int is used to make sure that there aren't two
     * consecutive rounds with enemies, so as not to make the game too hard
     * How it works:
     * - If canPlace is 0, then we can place enemies
     * - If we place 1, 2 or 3 enemies, then we can't place enemies for the next round
     * - If we place 4 or 5 enemies, then we can't place enemies for the next two rounds
     * 
     * @return the wave of enemies
     */
    public static Skeleton[][] makeFinalWaveEasy(Map map){
        Skeleton[][]enemies=new Skeleton[30][5];
        int canPlace=0;
        place(enemies,0,1,map);//make this a Wave Leader Skeleton
        for(int i=1;i<30;i++){
            int prob=random(100);
            if(canPlace!=0){
                canPlace--;
            }else if (canPlace==0&&prob<=30) {
                canPlace++;
                place(enemies,i,1,map);                
            }else if(canPlace==0&&prob<=60){
                canPlace++;
                place(enemies,i,2,map);
            }else if(canPlace==0&&prob<=75){
                canPlace++;
                place(enemies,i,3,map);
            }else if(canPlace==0&&prob<=85){
                canPlace+=2;
                place(enemies,i,4,map);
            }else if(canPlace==0&&prob<=92){
                canPlace+=2;
                place(enemies,i,5,map);
            }
        }
        return enemies;
    }

    /**
     * Make a medium mode wave of enemies
     * @param map the map, to place the enemies
     * 
     * This wave is made of 7 waves of enemies
     * The first wave is made of 20 rounds
     * Second, third, fifth and sixth waves are made of 40 rounds
     * Fourth and seventh waves are made of 50 rounds
     * 
     * We use the makeBeginningWave, makeMidGameWaveMedium and makeFinalWaveMedium
     * methods to make the waves
     * 
     * @return the wave of enemies
     */
    public static Skeleton[][][] makeMedium(Map map){
        Skeleton [][][] enemies=new Skeleton[7][][];
        enemies[0]=makeBeginningWave(map);
        enemies[1]=makeMidGameWaveMedium(map);
        enemies[2]=makeMidGameWaveMedium(map);
        enemies[3]=makeFinalWaveMedium(map);
        enemies[4]=makeMidGameWaveMedium(map);
        enemies[5]=makeMidGameWaveMedium(map);
        enemies[6]=makeFinalWaveMedium(map);
        return enemies;
    }

    /**
     * Make the mid-game wave for the medium mode
     * @param map the map, to place the enemies
     * 
     * Make a wave of 40 rounds
     * Make each round randomized with a probability of:
     * - A 40% chance to have 1 enemy
     * - A 20% chance to have 2 enemies
     * - A 13% chance to have 3 enemies
     * - A 7% chance to have 4 enemies
     * - A 5% chance to have 5 enemies
     * - A 15 % chance to have no enemies
     * 
     * The canPlace int is used to make sure that there aren't two
     * consecutive rounds with enemies, so as not to make the game too hard
     * How it works:
     * - If canPlace is 0, then we can place enemies
     * - If we place 2 or 3 enemies, then we can't place enemies for the next round
     * - If we place 4 enemies, then we can't place enemies for the next two rounds
     * - If we place 5 enemies, then we can't place enemies for the next three rounds
     * 
     * @return the wave of enemies
     */
    public static Skeleton[][]makeMidGameWaveMedium(Map map){
        Skeleton[][] enemies=new Skeleton[40][5];
        int canPlace=0;
        for(int i=0;i<40;i++){
            int prob =random(100);
            if(canPlace!=0)
                canPlace--;
            else if(canPlace==0&&prob<=40){
                place(enemies,i,1,map);
            }else if(canPlace==0&&prob<=60){
                canPlace++;
                place(enemies,i,2,map);
            }else if(canPlace==0&&prob<=73){
                canPlace++;
                place(enemies,i,3,map);
            }else if(canPlace==0&&prob<=80){
                canPlace+=2;
                place(enemies,i,4,map);
            }else if(canPlace==0&&prob<=85){
                canPlace+=3;
                place(enemies,i,5,map);
            }
        }
        return enemies;
    }

    /**
     * Make the final wave for the medium mode
     * @param map the map, to place the enemies
     * 
     * Make a wave of 50 rounds
     * Make each round randomized with a probability of:
     * - A 30% chance to have 1 enemy
     * - A 20% chance to have 2 enemies
     * - A 20% chance to have 3 enemies
     * - A 15% chance to have 4 enemies
     * - A 10% chance to have 5 enemies
     * - A 5% chance to have no enemies
     * 
     * The canPlace int is used to make sure that there aren't two
     * consecutive rounds with enemies, so as not to make the game too hard
     * How it works:
     * - If canPlace is 0, then we can place enemies
     * - If we place 2 or 3 enemies, then we can't place enemies for the next round
     * - If we place 4 enemies, then we can't place enemies for the next two rounds
     * - If we place 5 enemies, then we can't place enemies for the next three rounds
     * 
     * @return the wave of enemies
     */
    public static Skeleton[][]makeFinalWaveMedium(Map map){
        Skeleton[][]enemies=new Skeleton[50][5];
        int canPlace=0;
        place(enemies,0,1,map);//make this a Wave Leader Skeleton
        for(int i=1;i<50;i++){
            int prob=random(100);
            if(canPlace!=0){
                canPlace--;
            }else if (canPlace==0&&prob<=30) {
                place(enemies,i,1,map);                
            }else if(canPlace==0&&prob<=50){
                canPlace++;
                place(enemies,i,2,map);
            }else if(canPlace==0&&prob<=70){
                canPlace++;
                place(enemies,i,3,map);
            }else if(canPlace==0&&prob<=85){
                canPlace+=2;
                place(enemies,i,4,map);
            }else if(canPlace==0&&prob<=95){
                canPlace+=3;
                place(enemies,i,5,map);
            }
        }
        return enemies;
    }   

    /**
     * Make a hard mode wave of enemies
     * @param map the map, to place the enemies
     * 
     * This wave is made of 9 waves of enemies
     * The first wave is made of 20 rounds
     * Second, third, fifth and sixth waves are made of 40 rounds
     * Fourth, seventh and Eigth waves are made of 50 rounds
     * Ninth wave is made of 60 rounds
     * 
     * We use the makeBeginningWave, makeMidGameWaveHard, makeFinalGameMedium and makeFinalWaveHard
     * methods to make the waves
     * 
     * @return the wave of enemies
     */
    public static Skeleton[][][] makeHard(Map map){
        Skeleton [][][] enemies=new Skeleton[9][][];
        enemies[0]=makeBeginningWave(map);
        enemies[1]=makeMidGameWaveHard(map);
        enemies[2]=makeMidGameWaveHard(map);
        enemies[3]=makeFinalWaveMedium(map);
        enemies[4]=makeMidGameWaveHard(map);
        enemies[5]=makeMidGameWaveHard(map);
        enemies[6]=makeMidGameWaveHard(map);
        enemies[7]=makeFinalWaveMedium(map);
        enemies[8]=makeFinalWaveHard(map);
        return enemies;
    }

    /**
     * Make the mid-game wave for the hard mode
     * @param map the map, to place the enemies
     * 
     * Make a wave of 40 rounds
     * Make each round randomized with a probability of:
     * - A 35% chance to have 1 enemy
     * - A 25% chance to have 2 enemies
     * - A 15% chance to have 3 enemies
     * - A 15% chance to have 4 enemies
     * - A 7% chance to have 5 enemies
     * - A 3% chance to have no enemies
     * 
     * The canPlace int is used to make sure that there aren't two
     * consecutive rounds with enemies, so as not to make the game too hard
     * How it works:
     * - If canPlace is 0, then we can place enemies
     * - If we place 2 or 3 enemies, then we can't place enemies for the next round
     * - If we place 4 enemies, then we can't place enemies for the next two rounds
     * - If we place 5 enemies, then we can't place enemies for the next three rounds
     * 
     * @return the wave of enemies
     */
    public static Skeleton[][]makeMidGameWaveHard(Map map){
        Skeleton[][] enemies=new Skeleton[40][5];
        int canPlace=0;
        for(int i=0;i<40;i++){
            int prob =random(100);
            if(canPlace!=0)
                canPlace--;
            else if(canPlace==0&&prob<=35){
                place(enemies,i,1,map);
            }else if(canPlace==0&&prob<=60){
                canPlace++;
                place(enemies,i,2,map);
            }else if(canPlace==0&&prob<=75){
                canPlace++;
                place(enemies,i,3,map);
            }else if(canPlace==0&&prob<=90){
                canPlace+=2;
                place(enemies,i,4,map);
            }else if(canPlace==0&&prob<=97){
                canPlace+=3;
                place(enemies,i,5,map);
            }
        }
        return enemies;
    }

    /**
     * Make the final wave for the hard mode
     * @param map the map, to place the enemies
     * 
     * Make a wave of 60 rounds
     * Make each round randomized with a probability of:
     * - A 25% chance to have 1 enemy
     * - A 25% chance to have 2 enemies
     * - A 20% chance to have 3 enemies
     * - A 20% chance to have 4 enemies
     * - A 10% chance to have 5 enemies
     * - A 0% chance to have no enemies
     * 
     * The canPlace int is used to make sure that there aren't two
     * consecutive rounds with enemies, so as not to make the game too hard
     * How it works:
     * - If canPlace is 0, then we can place enemies
     * - If we place 3 or 4 enemies, then we can't place enemies for the next round
     * - If we place 5 enemies, then we can't place enemies for the next two rounds
     * 
     * @return the wave of enemies
     */
    public static Skeleton[][]makeFinalWaveHard(Map map){
        Skeleton[][]enemies=new Skeleton[60][5];
        int canPlace=0;
        place(enemies,0,1,map);//make this a Wave Leader Skeleton
        for(int i=1;i<60;i++){
            int prob=random(100);
            if(canPlace!=0){
                canPlace--;
            }else if (canPlace==0&&prob<=25) {
                place(enemies,i,1,map);                
            }else if(canPlace==0&&prob<=50){
                place(enemies,i,2,map);
            }else if(canPlace==0&&prob<=70){
                canPlace++;
                place(enemies,i,3,map);
            }else if(canPlace==0&&prob<=90){
                canPlace++;
                place(enemies,i,4,map);
            }else if(canPlace==0&&prob<=100){
                canPlace+=2;
                place(enemies,i,5,map);
            }
        }
        return enemies;
    }
    
    /**
     * place n enemies in the wave at the i-th position
     * 
     * @param enemies the wave
     * 
     * @param i       the position in the wave
     * 
     * @param n       the number of enemies to place at the i-th position
     */
    public static void place(Skeleton[][] enemies, int i, int n, Map map) {
        for (int j = 0; j < n; j++) {
            boolean placeBool = false;
            while (!placeBool) {
                int place = random(4);
                if (enemies[i][place] == null) {
                    enemies[i][place] = new Skeleton(10, place, map);
                    placeBool = true;
                }
            }
        }
    }

    public String toString() {
        // print the enemies array
        var s = "0 1 2 3 4\n";
        for(int i=0;i<enemies.length;i++){
            for(int j=0;j<enemies[i].length;j++){
                
                for(int k=0;k<enemies[i][j].length;k++){
                    s+=" ";
                    if(enemies[i][j][k]!=null){
                        s+=enemies[i][j][k].toString();
                    }else{
                        s+=" ";
                    }
                }
                s+="\n";
            }
            s+="\nFin de la sous-vague "+i+"\n\n";
        }
        return s;
    }

    public ArrayList<Skeleton> getEnemiesOnMap() {
        return this.enemiesOnMap;
    }

    public void setEnemiesOnMap(ArrayList<Skeleton> enemiesOnMap) {
        this.enemiesOnMap = enemiesOnMap;
    }

    public Skeleton[][][] getEnemies() {
        return this.enemies;
    }

    public void setEnemies(Skeleton[][][] enemies) {
        this.enemies = enemies;
    }

}
