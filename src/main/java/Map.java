public class Map {
    private Entity[][] map;

    public Map() {
        map = new Entity[5][15];
    }

    // faire en sorte que addEntity prenne des coordonnées en paramètre
    public void addEntity(Entity e) {
        this.map[e.getLine()][e.getColumn()] = e;
    }

    // à appeler dans une update de
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

    public void removeEntity(int line, int column) {
        this.map[line][column] = null;
    }

    public Entity getEntityAt(int line, int column){
        return map[line][column];
    }

    public String toString() {
        String s = "   ";
        for (int i = 0; i < this.map[0].length; i++) {
            if (i < 10)
                s += i + "  ";
            else
                s += (char) (i + 55) + "  ";
        }
        s += "\n";
        for (int i = 0; i < this.map.length; i++) {
            s += i + "  ";
            for (int j = 0; j < this.map[i].length; j++) {
                if (this.map[i][j] == null)
                    s += "   ";
                else
                    s += this.map[i][j] + "  ";
            }
            s += "\n";
        }
        return s;
    }

}
