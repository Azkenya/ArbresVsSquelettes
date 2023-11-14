public class Map {
    private Entity[][] map;

    // constructeur claqué mais faudra le modifier en fonction de l'aléatoire etc
    // etc
    public Map(Entity[][] map) {
        this.map = map;
    }

    // faire en sorte que addEntity prenne des coordonnées en paramètre
    public void addEntity(Entity e) {
        this.map[e.getPosition()[0]][e.getPosition()[1]] = e;
    }

    // à appeler dans une update
    public void removeDeadEntities() {
        for (int i = 0; i < this.map.length; i++) {
            for (int j = 0; j < this.map[i].length; j++) {
                if (this.map[i][j] != null) {
                    if (this.map[i][j].getHp() <= 0) {
                        this.removeEntity(i, j);
                    }
                }
            }
        }
    }

    public void removeEntity(int x, int y) {
        this.map[x][y] = null;
    }
}
