package model.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import controller.Updatable;
import model.entities.skeletons.*;
import model.entities.Skeleton;
import controller.Game;

public class Wave implements Updatable {
    private Skeleton[][][] enemies;
    private static ArrayList<Skeleton> enemiesOnMap;
    private boolean isFinished;
    private static Map map;
    private int currentRound;
    private int currentSubWave;
    private boolean isEndless;
    private int endlessLevel = 0;
    private int cd = 0;
    public int waveDifficulty = 0;

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
        this.waveDifficulty = n;
        if (n == 4) {
            this.isEndless = true;
        }
    }

    @Override
    public void update() {
        this.updateEnemies();
        if (this.currentSubWave < this.enemies.length) {
            if (this.currentRound < this.enemies[this.currentSubWave].length) {
                if (!map.isEnemyOnLastColumn()) {
                    if (!Game.graphicMode) {
                        if (this.currentRound >= 0)
                            this.spawnEnemies();
                        this.currentRound++;
                    } else {
                        if (cd == 100) {
                            this.spawnEnemies();
                            this.currentRound++;
                            cd = 0;
                        } else {
                            cd++;
                        }
                    }
                }

            } else {
                if (noEnemiesOnMap()) {
                    this.currentRound = 0;
                    this.currentSubWave++;
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


    public boolean isFinished() {
        return this.isFinished;
    }

    public static boolean noEnemiesOnMap() {
        return enemiesOnMap.isEmpty();
    }

    public void updateEnemies() {
        ArrayList<Skeleton> toRemove = new ArrayList<Skeleton>();
        for (int i = 0; i < Wave.enemiesOnMap.size(); i++) {
            Skeleton skeleton = Wave.enemiesOnMap.get(i);
            skeleton.update();
            if (skeleton.getHp() <= 0) {
                toRemove.add(skeleton);
            }
        }
        Wave.enemiesOnMap.removeAll(toRemove);
    }

    public void spawnEnemies() {
        for (int i = 0; i < 5; i++) {
            if (this.enemies[this.currentSubWave][this.currentRound][i] != null) {
                Wave.enemiesOnMap.add(this.enemies[this.currentSubWave][this.currentRound][i]);
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
        return rand.nextInt(n + 1);
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
                if (i >= 15) {
                    place(enemies, i, 1, map, 3);
                } else
                    place(enemies, i, 1, map, 1);
                canPlace = false;
            } else if (prob <= 70 && canPlace) {
                place(enemies, i, 2, map, 1);
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
     * @return the wave of enemies
     */
    public static Skeleton[][] makeMidGameWaveEasy(Map map) {
        Skeleton[][] enemies = new Skeleton[30][5];
        for (int i = 0; i < 30; i++) {
            int prob = random(100);
            if (prob <= 55) {
                place(enemies, i, 1, map, 3);
            } else if (prob <= 80) {
                place(enemies, i, 2, map, 2);
            } else if (prob <= 90) {
                place(enemies, i, 3, map, 2);
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
     * @return the wave of enemies
     */
    public static Skeleton[][] makeFinalWaveEasy(Map map) {
        Skeleton[][] enemies = new Skeleton[30][5];
        place(enemies, 0, 1, map, 0);// make this a Wave Leader Skeleton
        for (int i = 1; i < 30; i++) {
            int prob = random(100);
            if (prob <= 30) {
                place(enemies, i, 1, map, 4);
            } else if (prob <= 60) {
                place(enemies, i, 2, map, 3);
            } else if (prob <= 75) {
                place(enemies, i, 3, map, 3);
            } else if (prob <= 85) {
                place(enemies, i, 4, map, 2);
            } else if (prob <= 92) {
                place(enemies, i, 5, map, 2);
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
     * @return the wave of enemies
     */
    public static Skeleton[][] makeMidGameWaveMedium(Map map) {
        Skeleton[][] enemies = new Skeleton[40][5];
        for (int i = 0; i < 40; i++) {
            int prob = random(100);
            if (prob <= 40) {
                place(enemies, i, 1, map, 4);
            } else if (prob <= 60) {
                place(enemies, i, 2, map, 4);
            } else if (prob <= 73) {
                place(enemies, i, 3, map, 3);
            } else if (prob <= 80) {
                place(enemies, i, 4, map, 3);
            } else if (prob <= 85) {
                place(enemies, i, 5, map, 2);
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
     * @return the wave of enemies
     */
    public static Skeleton[][] makeFinalWaveMedium(Map map) {
        Skeleton[][] enemies = new Skeleton[50][5];
        place(enemies, 0, 1, map, 0);// make this a Wave Leader Skeleton
        for (int i = 1; i < 50; i++) {
            int prob = random(100);
            if (prob <= 30) {
                place(enemies, i, 1, map, 4);
            } else if (prob <= 50) {
                place(enemies, i, 2, map, 4);
            } else if (prob <= 70) {
                place(enemies, i, 3, map, 3);
            } else if (prob <= 85) {
                place(enemies, i, 4, map, 3);
            } else if (prob <= 95) {
                place(enemies, i, 5, map, 3);
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
     * @return the wave of enemies
     */
    public static Skeleton[][] makeMidGameWaveHard(Map map) {
        Skeleton[][] enemies = new Skeleton[40][5];
        for (int i = 0; i < 40; i++) {
            int prob = random(100);
            if (prob <= 35) {
                place(enemies, i, 1, map, 6);
            } else if (prob <= 60) {
                place(enemies, i, 2, map, 6);
            } else if (prob <= 75) {
                place(enemies, i, 3, map, 5);
            } else if (prob <= 90) {
                place(enemies, i, 4, map, 4);
            } else if (prob <= 97) {
                place(enemies, i, 5, map, 4);
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
     * @return the wave of enemies
     */
    public static Skeleton[][] makeFinalWaveHard(Map map) {
        Skeleton[][] enemies = new Skeleton[60][5];
        place(enemies, 0, 1, map, 0);// make this a Wave Leader Skeleton
        for (int i = 1; i < 60; i++) {
            int prob = random(100);
            if (prob <= 25) {
                place(enemies, i, 1, map, 7);
            } else if (prob <= 50) {
                place(enemies, i, 2, map, 7);
            } else if (prob <= 70) {
                place(enemies, i, 3, map, 6);
            } else if (prob <= 90) {
                place(enemies, i, 4, map, 5);
            } else if (prob <= 100) {
                place(enemies, i, 5, map, 5);
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
    public static void place(Skeleton[][] enemies, int i, int n, Map map, int roundLevel) {
        for (int j = 0; j < n; j++) {
            boolean placeBool = false;
            while (!placeBool) {
                int place = random(4);
                if (enemies[i][place] == null) {
                    enemies[i][place] = createSkeleton(roundLevel, place, map);
                    placeBool = true;
                }
            }
        }
    }

    /**
     * create a skeleton depending on the round level and the place
     * 
     * @param roundLevel
     *                   the round level decides which type of skeleton will be
     *                   created
     *                   it goes from 1 to 7
     *                   level 1 can only be default skeleton
     *                   level 2 can be default skeleton or fast skeleton
     *                   level 3 can be default skeleton, fast skeleton or glasses
     *                   skeleton
     *                   level 4 can be default skeleton, fast skeleton, hard
     *                   skeleton or glasses skeleton
     *                   level 5 can be fast skeleton, hard skeleton or glasses
     *                   skeleton
     *                   level 6 can be fast skeleton or glasses skeleton
     *                   level 7 can only be hard skeleton or fast skeleton
     * @param place
     * @param map
     * @return
     */
    public static Skeleton createSkeleton(int roundLevel, int place, Map map) {
        switch (roundLevel) {
            case 0:
                return new WaveLeaderSkeleton(place, map);
            case 1:
                return new DefaultSkeleton(place, map);
            case 2:
                int type = random(10);
                if (type <= 8) {
                    return new DefaultSkeleton(place, map);
                } else {
                    return new FastSkeleton(place, map);
                }
            case 3:
                type = random(10);
                if (type <= 6) {
                    return new DefaultSkeleton(place, map);
                } else if (type <= 8) {
                    return new FastSkeleton(place, map);
                } else {
                    return new GlassesSkeleton(place, map);
                }
            case 4:
                type = random(10);
                if (type <= 4) {
                    return new DefaultSkeleton(place, map);
                } else if (type <= 6) {
                    return new FastSkeleton(place, map);
                } else if (type <= 8) {
                    return new GlassesSkeleton(place, map);
                } else {
                    return new HardSkeleton(place, map);
                }
            case 5:
                type = random(10);
                if (type <= 4) {
                    return new FastSkeleton(place, map);
                } else if (type <= 8) {
                    return new GlassesSkeleton(place, map);
                } else {
                    return new HardSkeleton(place, map);
                }
            case 6:
                type = random(10);
                if (type <= 5) {
                    return new GlassesSkeleton(place, map);
                } else {
                    return new FastSkeleton(place, map);
                }
            case 7:
                type = random(10);
                if (type <= 1) {
                    return new DefaultSkeleton(place, map);
                } else if (type <= 5) {
                    return new HardSkeleton(place, map);
                } else {
                    return new GlassesSkeleton(place, map);
                }
            default:
                return new DefaultSkeleton(place, map);
        }
    }

    public String toString() {
        // print the enemies array
        StringBuilder s = new StringBuilder("0 1 2 3 4\n");
        for (int i = 0; i < enemies.length; i++) {
            for (int j = 0; j < enemies[i].length; j++) {

                for (int k = 0; k < enemies[i][j].length; k++) {
                    s.append(" ");
                    if (enemies[i][j][k] != null) {
                        s.append(enemies[i][j][k].toString());
                    } else {
                        s.append(" ");
                    }
                }
                s.append("\n");
            }
            s.append("\nFin de la sous-vague ").append(i).append("\n\n");
        }
        return s.toString();
    }

    public String enemiesToString() {
        StringBuilder retStr = new StringBuilder();
        for (Skeleton[][] enemy : enemies) {
            retStr.append("[ ").append(enemies.length).append("\n");
            for (Skeleton[] skeletons : enemy) {
                retStr.append("    [ ").append(enemy.length).append("\n       ");
                for (Skeleton skeleton : skeletons) {
                    retStr.append(skeleton).append(" ");
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
