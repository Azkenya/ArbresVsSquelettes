import java.util.ArrayList;
import java.util.Random;

public class Wave implements Updatable {
    private Skeleton[][] enemies;
    private ArrayList<Skeleton> enemiesOnMap;
    private boolean isFinished;
    private static Map map;
    private int currentTurn = 0;

    public Wave(Skeleton[][] enemies, ArrayList<Skeleton> enemiesOnMap, Map map) {
        this.enemies = enemies;
        this.isFinished = false;
        this.enemiesOnMap = enemiesOnMap;
        Wave.map = map;
        this.currentTurn = 0;
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
        if (this.currentTurn <= this.enemies.length) {
            this.updateEnemies();
            this.spawnEnemies();
            this.currentTurn++;
        }
    }

    public boolean noEnemiesOnMap() {
        return this.enemiesOnMap.size() == 0;
    }

    public void updateEnemies() {
        for (int i = 0; i < this.enemiesOnMap.size(); i++) {
            this.enemiesOnMap.get(i).update();
        }
    }

    public void spawnEnemies() {
        System.out.println("Spawn enemies: current turn : " + this.currentTurn);
        for (int i = 0; i < this.enemies[this.currentTurn].length; i++) {
            if (this.enemies[this.currentTurn][i] != null) {
                this.enemiesOnMap.add(this.enemies[this.currentTurn][i]);
                this.enemies[this.currentTurn][i] = null;
                Wave.map.addEntity(this.enemiesOnMap.get(this.enemiesOnMap.size() - 1));
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
    public static Skeleton[][] makeWave(int lvl, Map map) {
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
     * Make it randomized with a probability of 25% to have one enemy at a position
     * 15% to have 2 enemies at a position
     * 7% to have 3 enemies at a position
     * 3% to have 4 enemies at a position
     * And 50% to have no enemy at a position
     *
     * From the 20th position, there is a 75% chance to have at least one enemy at a
     * position
     * and 25% to have no enemy at a position
     * 
     * @return the wave of enemies
     */
    public static Skeleton[][] makeEasy(Map map) {
        Skeleton[][] enemies = new Skeleton[40][5];
        for (int i = 0; i < 40; i++) {
            int prob = random(100);
            if (prob <= 3 && i >= 22) {
                place(enemies, i, 4, map);
            } else if (prob <= 10 && i >= 15) {
                place(enemies, i, 3, map);
            } else if (prob <= 25 && i >= 6) {
                place(enemies, i, 2, map);
            } else if (prob <= 50) {
                place(enemies, i, 1, map);
            } else if (prob <= 75 && i >= 20) {
                place(enemies, i, 1, map);
            }
        }
        return enemies;
    }

    /**
     * Make a medium mode wave of enemies
     * Make it randomized with a probability of:
     * 5% to have 5 enemies at a position starting from the 35th position
     * 8% to have 4 enemies at a position starting from the 22nd position
     * 17% to have 3 enemies at a position starting from the 15th position
     * 25% to have 2 enemies at a position starting from the 6th position
     * 20% to have 1 enemy at a position
     * And 25% to have no enemy at a position
     * Starting from the 30th position, there is a 100% chance to have at least one
     * enemy at a position
     * 
     * @return the wave of enemies
     */
    public static Skeleton[][] makeMedium(Map map) {
        Skeleton[][] enemies = new Skeleton[60][5];
        for (int i = 0; i < 60; i++) {
            int prob = random(100);
            if (prob <= 5 && i >= 35) {
                place(enemies, i, 5, map);
            } else if (prob <= 13 && i >= 22) {
                place(enemies, i, 4, map);
            } else if (prob <= 30 && i >= 15) {
                place(enemies, i, 3, map);
            } else if (prob <= 55 && i >= 6) {
                place(enemies, i, 2, map);
            } else if (prob <= 75) {
                place(enemies, i, 1, map);
            } else if (prob <= 100 && i >= 30) {
                place(enemies, i, 1, map);
            }
        }
        return enemies;
    }

    /**
     * Make a hard mode wave of enemies
     * Make it randomized with a probability of:
     * 8% to have 5 enemies at a position starting from the 35th position
     * 12% to have 4 enemies at a position starting from the 22nd position
     * 20% to have 3 enemies at a position starting from the 15th position
     * 25% to have 2 enemies at a position starting from the 6th position
     * 20% to have 1 enemy at a position.
     * And 15% to have no enemy at a position
     * Starting from the 30th position, there is a 100% chance to have at least one
     * enemy at a position.
     * Starting from the 50th position, there is a 100% chance to have at least two
     * enemies at a position.
     * 
     * @return the wave of enemies
     * 
     */
    public static Skeleton[][] makeHard(Map map) {
        Skeleton[][] enemies = new Skeleton[80][5];
        for (int i = 0; i < 80; i++) {
            int prob = random(100);
            if (prob <= 8 && i >= 35) {
                place(enemies, i, 5, map);
            } else if (prob <= 20 && i >= 22) {
                place(enemies, i, 4, map);
            } else if (prob <= 40 && i >= 15) {
                place(enemies, i, 3, map);
            } else if (prob <= 65 && i >= 6) {
                place(enemies, i, 2, map);
            } else if (prob <= 85 && i <= 50) {
                place(enemies, i, 1, map);
            } else if (prob <= 100 && i >= 50) {
                place(enemies, i, 2, map);
            } else if (prob <= 100 && i >= 30) {
                place(enemies, i, 1, map);
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
        for (int i = 0; i < this.enemies.length; i++) {
            for (int j = 0; j < this.enemies[i].length; j++) {
                if (this.enemies[i][j] == null)
                    s += "  ";
                else
                    s += (this.enemies[i][j] + " ");
            }
            s += "\n";
        }
        return s + "fin";
    }

    public boolean isFinished() {
        return this.isFinished;
    }

    public ArrayList<Skeleton> getEnemiesOnMap() {
        return this.enemiesOnMap;
    }

    public void setEnemiesOnMap(ArrayList<Skeleton> enemiesOnMap) {
        this.enemiesOnMap = enemiesOnMap;
    }

    public Skeleton[][] getEnemies() {
        return this.enemies;
    }

    public void setEnemies(Skeleton[][] enemies) {
        this.enemies = enemies;
    }

}
