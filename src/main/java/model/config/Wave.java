package model.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import controller.Updatable;
import model.entities.skeletons.*;
import model.entities.Skeleton;
import controller.Game;
import view.GameScreen;

import javax.imageio.IIOException;

public class Wave implements Updatable {
    private Skeleton[][][] enemies;
    private static ArrayList<Skeleton> enemiesOnMap;
    private boolean isFinished;
    private static Map map;
    private int currentRound;
    private int currentSubWave;
    private boolean isEndless;
    private int endlessLevel = 0;

    public Wave(Skeleton[][][] enemies, ArrayList<Skeleton> enemiesOnMap, Map map) {
        this.enemies = enemies;
        this.isFinished = false;
        Wave.enemiesOnMap = enemiesOnMap;
        Wave.map = map;
        this.currentRound = 0;
        this.currentSubWave = 0;

        try {
            tools.IOTools.writeToFile(this.enemiesToString(), "/cacaDeWave.txt");
        } catch (IOException e) {
            System.out.println("oh non error io");
        }
    }

    /**
     * Constructor of the wave
     * 
     * @param n the level of the wave, 1, 2 or 3
     */
    public Wave(int n, Map map) {
        this(makeWave(n, map), new ArrayList<Skeleton>(), map);
        if (n == 4) {
            this.isEndless = true;
        }
    }

    @Override
    public void update() {
        this.updateEnemies();
        if (this.currentSubWave < this.enemies.length) {
            if (this.currentRound < this.enemies[this.currentSubWave].length) {
                if (!map.isEnemyOnLastColumn() || Game.graphicMode) {
                    if (!Game.graphicMode) {
                        if (this.currentRound >= 0)
                            this.spawnEnemies();
                        this.currentRound++;
                    } else {
                        System.out.println(
                                "CurrentRound: " + this.currentRound + "\nNextRoundIsEmpty : " + nextRoundIsEmpty());
                        this.spawnEnemies();
                        System.out.println("Spawned enemies");
                        this.currentRound++;
                    }
                }

            } else {
                if (noEnemiesOnMap()) {
                    this.currentRound = 0;
                    this.currentSubWave++;
                    System.out.println("Subwave Finished");
                }
            }
        } else {
            if (this.isEndless) {
                this.endlessLevel++;
                this.enemies = makeWave(this.endlessLevel, map);
                this.currentSubWave = 0;
                this.currentRound = 0;
            } else {
                this.isFinished = true;
            }
        }
    }

    public boolean nextRoundIsEmpty() {
        if (this.currentRound < 0) {
            return true;
        }
        if (this.currentRound + 1 >= this.enemies[this.currentSubWave].length) {
            return true;
        }
        for (int i = 0; i < 5; i++) {
            if (this.enemies[this.currentSubWave][this.currentRound + 1][i] != null) {
                return false;
            }
        }
        return true;
    }

    public boolean isFinished() {
        return this.isFinished;
    }

    public static boolean noEnemiesOnMap() {
        return enemiesOnMap.isEmpty();
    }

    public void updateEnemies() {
        for (int i = 0; i < Wave.enemiesOnMap.size(); i++) {
            Skeleton skeleton = Wave.enemiesOnMap.get(i);
            skeleton.update();
            if (skeleton.getHp() <= 0) {
                enemiesOnMap.remove(skeleton);
            }
        }
    }

    public void spawnEnemies() {
        System.out.println("Spawning enemies");
        for (int i = 0; i < 5; i++) {
            if (this.enemies[this.currentSubWave][this.currentRound][i] != null) {
                Wave.enemiesOnMap.add(this.enemies[this.currentSubWave][this.currentRound][i]);
                System.out.println("Enemy added to arraylist");
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
        if (lvl == 1 || lvl == 4) {
            return makeEasy(map);
        } else if (lvl == 2 || lvl == 5) {
            return makeMedium(map);
        } else if (lvl == 3 || lvl == 6) {
            return makeHard(map);
        } else {
            return makeEndless(map);
        }
    }

    /**
     * Make an easy mode wave of enemies
     * 
     * @param map the map
     *            This wave is made of 7 waves of enemies
     *            The first wave is made of 20 enemies
     *            The other waves are made of 30 enemies
     *            We use the makeBeginningWave, makeMidGameWaveEasy and
     *            makeFinalWaveEasy
     *            methods to make the waves
     *            A wave represents the whole game.
     *            For the easy mode, it is made of a 7 box long array, which
     *            represents 7 sub-waves of enemies.
     *            Each sub-wave is made of 20 or 30 box long arrays, which represent
     *            the rounds of the wave
     *            Each round is made of an array of 5 Skeletons, which represent the
     *            5 lanes where enemies spawn
     *            For example, enemies[2][3] represents the 4th round of the 3rd
     *            sub-wave of the wave
     *            And enemies[2][3][4] represents the enemy on the 5th lane of the
     *            4th round
     *            of the 3rd sub-wave of the wave
     * @return the wave of enemies
     */
    public static Skeleton[][][] makeEasy(Map map) {
        Skeleton[][][] enemies = new Skeleton[7][][];
        enemies[0] = makeBeginningWave(map);
        enemies[1] = makeMidGameWaveEasy(map);
        enemies[2] = makeMidGameWaveEasy(map);
        enemies[3] = makeFinalWaveEasy(map);
        enemies[4] = makeMidGameWaveEasy(map);
        enemies[5] = makeMidGameWaveEasy(map);
        enemies[6] = makeFinalWaveEasy(map);
        return enemies;
    }

    /**
     * Make the first wave for any difficulty
     * 
     * @param map the map, to place the enemies
     * 
     *            Make a wave of 20 rounds
     *            The first 5 rounds have no enemies, for a smooth start
     *            Starting from the 6th round, there is:
     *            - A 55% chance to have 1 enemy
     *            - A 15% chance to have 2 enemies
     *            - A 30% chance to have no enemies
     * 
     *            The canPlace boolean is used to make sure that there aren't two
     *            consecutive rounds with enemies, so as not to make the game too
     *            hard
     *            or unplayable
     * @return a beginning wave of enemies
     */
    public static Skeleton[][] makeBeginningWave(Map map) {
        Skeleton[][] enemies = new Skeleton[20][5];
        boolean canPlace = true;
        for (int i = 0; i < 20; i++) {
            int prob = random(100);
            if (prob <= 55 && canPlace) {
                place(enemies, i, 1, map);
                canPlace = false;
            } else if (prob <= 70 && canPlace) {
                place(enemies, i, 2, map);
                canPlace = false;
            } else if (!canPlace) {
                canPlace = true;
            }
        }
        return enemies;
    }

    /**
     * Make a mid-game wave of enemies for the easy mode
     * 
     * @param map the map, to place the enemies
     * 
     *            Make a wave of 30 rounds
     *            Make each round randomized with a probability of:
     *            - A 55% chance to have 1 enemy
     *            - A 25% chance to have 2 enemies
     *            - A 10% chance to have 3 enemies
     *            - A 10% chance to have no enemies
     * 
     *            The canPlace int is used to make sure that there aren't two
     *            consecutive rounds with enemies, so as not to make the game too
     *            hard
     *            How it works:
     *            - If canPlace is 0, then we can place enemies
     *            - If we place 1 or 2 enemies, then we can't place enemies for the
     *            next round
     *            - If we place 3 enemies, then we can't place enemies for the next
     *            two rounds
     * 
     * @return the wave of enemies
     */
    public static Skeleton[][] makeMidGameWaveEasy(Map map) {
        Skeleton[][] enemies = new Skeleton[30][5];
        int canPlace = 0;
        for (int i = 0; i < 30; i++) {
            int prob = random(100);
            if (canPlace != 0)
                canPlace--;
            else if (canPlace == 0 && prob <= 55) {
                canPlace++;
                place(enemies, i, 1, map);
            } else if (canPlace == 0 && prob <= 80) {
                canPlace++;
                place(enemies, i, 2, map);
            } else if (canPlace == 0 && prob <= 90) {
                canPlace += 2;
                place(enemies, i, 3, map);
            }
        }
        return enemies;
    }

    /**
     * Make the final wave for the easy mode
     * 
     * @param map the map, to place the enemies
     * 
     *            Make a wave of 30 rounds
     *            Make each round randomized with a probability of:
     *            - A 30% chance to have 1 enemy
     *            - A 30% chance to have 2 enemies
     *            - A 15% chance to have 3 enemies
     *            - A 10% chance to have 4 enemies
     *            - A 7% chance to have 5 enemies
     *            - A 8% chance to have no enemies
     * 
     *            The canPlace int is used to make sure that there aren't two
     *            consecutive rounds with enemies, so as not to make the game too
     *            hard
     *            How it works:
     *            - If canPlace is 0, then we can place enemies
     *            - If we place 1, 2 or 3 enemies, then we can't place enemies for
     *            the next round
     *            - If we place 4 or 5 enemies, then we can't place enemies for the
     *            next two rounds
     * 
     * @return the wave of enemies
     */
    public static Skeleton[][] makeFinalWaveEasy(Map map) {
        Skeleton[][] enemies = new Skeleton[30][5];
        int canPlace = 0;
        place(enemies, 0, 1, map, 0);// make this a Wave Leader Skeleton
        for (int i = 1; i < 30; i++) {
            int prob = random(100);
            if (canPlace != 0) {
                canPlace--;
            } else if (canPlace == 0 && prob <= 30) {
                canPlace++;
                place(enemies, i, 1, map);
            } else if (canPlace == 0 && prob <= 60) {
                canPlace++;
                place(enemies, i, 2, map);
            } else if (canPlace == 0 && prob <= 75) {
                canPlace++;
                place(enemies, i, 3, map);
            } else if (canPlace == 0 && prob <= 85) {
                canPlace += 2;
                place(enemies, i, 4, map);
            } else if (canPlace == 0 && prob <= 92) {
                canPlace += 2;
                place(enemies, i, 5, map);
            }
        }
        return enemies;
    }

    /**
     * Make a medium mode wave of enemies
     * 
     * @param map the map, to place the enemies
     * 
     *            This wave is made of 7 waves of enemies
     *            The first wave is made of 20 rounds
     *            Second, third, fifth and sixth waves are made of 40 rounds
     *            Fourth and seventh waves are made of 50 rounds
     * 
     *            We use the makeBeginningWave, makeMidGameWaveMedium and
     *            makeFinalWaveMedium
     *            methods to make the waves
     * 
     * @return the wave of enemies
     */
    public static Skeleton[][][] makeMedium(Map map) {
        Skeleton[][][] enemies = new Skeleton[7][][];
        enemies[0] = makeBeginningWave(map);
        enemies[1] = makeMidGameWaveMedium(map);
        enemies[2] = makeMidGameWaveMedium(map);
        enemies[3] = makeFinalWaveMedium(map);
        enemies[4] = makeMidGameWaveMedium(map);
        enemies[5] = makeMidGameWaveMedium(map);
        enemies[6] = makeFinalWaveMedium(map);
        return enemies;
    }

    /**
     * Make the mid-game wave for the medium mode
     * 
     * @param map the map, to place the enemies
     * 
     *            Make a wave of 40 rounds
     *            Make each round randomized with a probability of:
     *            - A 40% chance to have 1 enemy
     *            - A 20% chance to have 2 enemies
     *            - A 13% chance to have 3 enemies
     *            - A 7% chance to have 4 enemies
     *            - A 5% chance to have 5 enemies
     *            - A 15 % chance to have no enemies
     * 
     *            The canPlace int is used to make sure that there aren't two
     *            consecutive rounds with enemies, so as not to make the game too
     *            hard
     *            How it works:
     *            - If canPlace is 0, then we can place enemies
     *            - If we place 2 or 3 enemies, then we can't place enemies for the
     *            next round
     *            - If we place 4 enemies, then we can't place enemies for the next
     *            two rounds
     *            - If we place 5 enemies, then we can't place enemies for the next
     *            three rounds
     * 
     * @return the wave of enemies
     */
    public static Skeleton[][] makeMidGameWaveMedium(Map map) {
        Skeleton[][] enemies = new Skeleton[40][5];
        int canPlace = 0;
        for (int i = 0; i < 40; i++) {
            int prob = random(100);
            if (canPlace != 0)
                canPlace--;
            else if (canPlace == 0 && prob <= 40) {
                place(enemies, i, 1, map);
            } else if (canPlace == 0 && prob <= 60) {
                canPlace++;
                place(enemies, i, 2, map);
            } else if (canPlace == 0 && prob <= 73) {
                canPlace++;
                place(enemies, i, 3, map);
            } else if (canPlace == 0 && prob <= 80) {
                canPlace += 2;
                place(enemies, i, 4, map);
            } else if (canPlace == 0 && prob <= 85) {
                canPlace += 3;
                place(enemies, i, 5, map);
            }
        }
        return enemies;
    }

    /**
     * Make the final wave for the medium mode
     * 
     * @param map the map, to place the enemies
     * 
     *            Make a wave of 50 rounds
     *            Make each round randomized with a probability of:
     *            - A 30% chance to have 1 enemy
     *            - A 20% chance to have 2 enemies
     *            - A 20% chance to have 3 enemies
     *            - A 15% chance to have 4 enemies
     *            - A 10% chance to have 5 enemies
     *            - A 5% chance to have no enemies
     * 
     *            The canPlace int is used to make sure that there aren't two
     *            consecutive rounds with enemies, so as not to make the game too
     *            hard
     *            How it works:
     *            - If canPlace is 0, then we can place enemies
     *            - If we place 2 or 3 enemies, then we can't place enemies for the
     *            next round
     *            - If we place 4 enemies, then we can't place enemies for the next
     *            two rounds
     *            - If we place 5 enemies, then we can't place enemies for the next
     *            three rounds
     * 
     * @return the wave of enemies
     */
    public static Skeleton[][] makeFinalWaveMedium(Map map) {
        Skeleton[][] enemies = new Skeleton[50][5];
        int canPlace = 0;
        place(enemies, 0, 1, map, 0);// make this a Wave Leader Skeleton
        for (int i = 1; i < 50; i++) {
            int prob = random(100);
            if (canPlace != 0) {
                canPlace--;
            } else if (canPlace == 0 && prob <= 30) {
                place(enemies, i, 1, map);
            } else if (canPlace == 0 && prob <= 50) {
                canPlace++;
                place(enemies, i, 2, map);
            } else if (canPlace == 0 && prob <= 70) {
                canPlace++;
                place(enemies, i, 3, map);
            } else if (canPlace == 0 && prob <= 85) {
                canPlace += 2;
                place(enemies, i, 4, map);
            } else if (canPlace == 0 && prob <= 95) {
                canPlace += 3;
                place(enemies, i, 5, map);
            }
        }
        return enemies;
    }

    /**
     * Make a hard mode wave of enemies
     * 
     * @param map the map, to place the enemies
     * 
     *            This wave is made of 9 waves of enemies
     *            The first wave is made of 20 rounds
     *            Second, third, fifth and sixth waves are made of 40 rounds
     *            Fourth, seventh and Eigth waves are made of 50 rounds
     *            Ninth wave is made of 60 rounds
     * 
     *            We use the makeBeginningWave, makeMidGameWaveHard,
     *            makeFinalGameMedium and makeFinalWaveHard
     *            methods to make the waves
     * 
     * @return the wave of enemies
     */
    public static Skeleton[][][] makeHard(Map map) {
        Skeleton[][][] enemies = new Skeleton[9][][];
        enemies[0] = makeBeginningWave(map);
        enemies[1] = makeMidGameWaveHard(map);
        enemies[2] = makeMidGameWaveHard(map);
        enemies[3] = makeFinalWaveMedium(map);
        enemies[4] = makeMidGameWaveHard(map);
        enemies[5] = makeMidGameWaveHard(map);
        enemies[6] = makeMidGameWaveHard(map);
        enemies[7] = makeFinalWaveMedium(map);
        enemies[8] = makeFinalWaveHard(map);
        return enemies;
    }

    /**
     * Make the mid-game wave for the hard mode
     * 
     * @param map the map, to place the enemies
     * 
     *            Make a wave of 40 rounds
     *            Make each round randomized with a probability of:
     *            - A 35% chance to have 1 enemy
     *            - A 25% chance to have 2 enemies
     *            - A 15% chance to have 3 enemies
     *            - A 15% chance to have 4 enemies
     *            - A 7% chance to have 5 enemies
     *            - A 3% chance to have no enemies
     * 
     *            The canPlace int is used to make sure that there aren't two
     *            consecutive rounds with enemies, so as not to make the game too
     *            hard
     *            How it works:
     *            - If canPlace is 0, then we can place enemies
     *            - If we place 2 or 3 enemies, then we can't place enemies for the
     *            next round
     *            - If we place 4 enemies, then we can't place enemies for the next
     *            two rounds
     *            - If we place 5 enemies, then we can't place enemies for the next
     *            three rounds
     * 
     * @return the wave of enemies
     */
    public static Skeleton[][] makeMidGameWaveHard(Map map) {
        Skeleton[][] enemies = new Skeleton[40][5];
        int canPlace = 0;
        for (int i = 0; i < 40; i++) {
            int prob = random(100);
            if (canPlace != 0)
                canPlace--;
            else if (canPlace == 0 && prob <= 35) {
                int type = random(70);
                if (type <= 30) {
                    place(enemies, i, 1, map, 1);
                } else if (type <= 60) {
                    place(enemies, i, 1, map, 2);
                } else {
                    place(enemies, i, 1, map, 3);
                }
            } else if (canPlace == 0 && prob <= 60) {
                canPlace++;
                int type = random(100);

            } else if (canPlace == 0 && prob <= 75) {
                canPlace++;
                place(enemies, i, 3, map);
            } else if (canPlace == 0 && prob <= 90) {
                canPlace += 2;
                place(enemies, i, 4, map);
            } else if (canPlace == 0 && prob <= 97) {
                canPlace += 3;
                place(enemies, i, 5, map);
            }
        }
        return enemies;
    }

    /**
     * Make the final wave for the hard mode
     * 
     * @param map the map, to place the enemies
     * 
     *            Make a wave of 60 rounds
     *            Make each round randomized with a probability of:
     *            - A 25% chance to have 1 enemy
     *            - A 25% chance to have 2 enemies
     *            - A 20% chance to have 3 enemies
     *            - A 20% chance to have 4 enemies
     *            - A 10% chance to have 5 enemies
     *            - A 0% chance to have no enemies
     * 
     *            The canPlace int is used to make sure that there aren't two
     *            consecutive rounds with enemies, so as not to make the game too
     *            hard
     *            How it works:
     *            - If canPlace is 0, then we can place enemies
     *            - If we place 3 or 4 enemies, then we can't place enemies for the
     *            next round
     *            - If we place 5 enemies, then we can't place enemies for the next
     *            two rounds
     * 
     * @return the wave of enemies
     */
    public static Skeleton[][] makeFinalWaveHard(Map map) {
        Skeleton[][] enemies = new Skeleton[60][5];
        int canPlace = 0;
        place(enemies, 0, 1, map, 0);// make this a Wave Leader Skeleton
        for (int i = 1; i < 60; i++) {
            int prob = random(100);
            if (canPlace != 0) {
                canPlace--;
            } else if (canPlace == 0 && prob <= 25) {
                int type = random(70);
                if (type <= 10) {
                    place(enemies, i, 1, map, 1);
                } else if (type <= 40) {
                    place(enemies, i, 1, map, 2);
                } else if (type <= 65) {
                    place(enemies, i, 1, map, 3);
                } else {
                    place(enemies, i, 1, map, 4);
                }
            } else if (canPlace == 0 && prob <= 50) {
                int type = random(70);
                if (type <= 10) {
                    place(enemies, i, 1, map, 1);
                } else if (type <= 40) {
                    place(enemies, i, 1, map, 2);
                } else if (type <= 65) {
                    place(enemies, i, 1, map, 3);
                } else {
                    place(enemies, i, 1, map, 4);
                }
            } else if (canPlace == 0 && prob <= 70) {
                canPlace++;
                int type = random(70);
                if (type <= 10) {
                    place(enemies, i, 1, map, 1);
                } else if (type <= 40) {
                    place(enemies, i, 1, map, 2);
                } else if (type <= 65) {
                    place(enemies, i, 1, map, 3);
                } else {
                    place(enemies, i, 1, map, 4);
                }
            } else if (canPlace == 0 && prob <= 90) {
                canPlace++;
                int type = random(70);
                if (type <= 10) {
                    place(enemies, i, 1, map, 1);
                } else if (type <= 40) {
                    place(enemies, i, 1, map, 2);
                } else if (type <= 65) {
                    place(enemies, i, 1, map, 3);
                } else {
                    place(enemies, i, 1, map, 4);
                }
            } else if (canPlace == 0 && prob <= 100) {
                canPlace += 2;
                int type = random(70);
                if (type <= 10) {
                    place(enemies, i, 1, map, 1);
                } else if (type <= 40) {
                    place(enemies, i, 1, map, 2);
                } else if (type <= 65) {
                    place(enemies, i, 1, map, 3);
                } else {
                    place(enemies, i, 1, map, 4);
                }
            }
        }
        return enemies;
    }

    /**
     * Make the last waves for the endless mode
     * They will be made of only FinalWaveHard
     * 
     * @param map the map, to place the enemies
     * 
     * @return the wave of enemies
     */

    public static Skeleton[][][] makeEndless(Map map) {
        Skeleton[][][] enemies = new Skeleton[1][][];
        enemies[0] = makeFinalWaveHard(map);
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
    public static void place(Skeleton[][] enemies, int i, int n, Map map, int type) {
        for (int j = 0; j < n; j++) {
            boolean placeBool = false;
            while (!placeBool) {
                int place = random(4);
                if (enemies[i][place] == null) {
                    switch (type) {
                        case 0:
                            enemies[i][place] = new WaveLeaderSkeleton(place, map);
                            break;
                        case 1:
                            enemies[i][place] = new FastSkeleton(place, map);
                            break;
                        case 2:
                            enemies[i][place] = new GlassesSkeleton(place, map);
                            break;
                        case 3:
                            enemies[i][place] = new HardSkeleton(place, map);
                            break;
                        default:
                            enemies[i][place] = new DefaultSkeleton(place, map);
                            break;
                    }
                    placeBool = true;
                }
            }
        }
    }

    public String toString() {
        // print the enemies array
        var s = "0 1 2 3 4\n";
        for (int i = 0; i < enemies.length; i++) {
            for (int j = 0; j < enemies[i].length; j++) {

                for (int k = 0; k < enemies[i][j].length; k++) {
                    s += " ";
                    if (enemies[i][j][k] != null) {
                        s += enemies[i][j][k].toString();
                    } else {
                        s += " ";
                    }
                }
                s += "\n";
            }
            s += "\nFin de la sous-vague " + i + "\n\n";
        }
        return s;
    }

    public String enemiesToString() {
        StringBuilder retStr = new StringBuilder();
        for (int i = 0; i < enemies.length; i++) {
            retStr.append("[ " + enemies.length + "\n");
            for (int j = 0; j < enemies[i].length; j++) {
                retStr.append("    [ " + enemies[i].length + "\n       ");
                for (int k = 0; k < enemies[i][j].length; k++) {
                    retStr.append("" + enemies[i][j][k] + " ");
                }
                retStr.append("\n    ]\n\n");
            }
            retStr.append("]\n\n");
        }
        return retStr.toString();
    }

    public static ArrayList<Skeleton> getEnemiesOnMap() {
        return Wave.enemiesOnMap;
    }

    public static void setEnemiesOnMap(ArrayList<Skeleton> enemiesOnMap) {
        Wave.enemiesOnMap = enemiesOnMap;
    }

    public Skeleton[][][] getEnemies() {
        return this.enemies;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public int getCurrentSubWave() {
        return currentSubWave;
    }
}
