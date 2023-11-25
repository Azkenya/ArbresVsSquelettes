import java.util.Arrays;

public class Map {
    private Entity[][] map;
    private boolean[] chainsaws;

    public Map() {
        map = new Entity[5][15];
        chainsaws =  new boolean[map.length];
        Arrays.fill(chainsaws, true);
    }

    // faire en sorte que addEntity prenne des coordonnées en paramètre
    public boolean addEntity(Entity e) {
        if (this.getEntityAt(e.getLine(),e.getColumn()) == null){
            this.map[e.getLine()][e.getColumn()] = e;
            return true;
        }
        else{
            System.out.println("Error : there is already an entity here\n");
            return false;
        }
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
        String s = "    ";
        for (int i = 0; i < this.map[0].length; i++) {
            if (i < 10)
                s += i + "  ";
            else
                s += (char) (i + 55) + "  ";
        }
        s += "\n";
        for (int i = 0; i < this.map.length; i++) {
            s += i + " ";

            //Ajoute les chainsaw
            if(this.chainsaws[i]){
                s += "c ";
            }
            else{
                s += "  ";
            }
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

    public int numberOfLines(){
        return map.length;
    }

    public int numberOfColumns(){
        return map[0].length;
    }

    public boolean[] getChainsaws() {
        return chainsaws;
    }

    public void killEverythingOnLine(int line){
        for(int column = 0; column < map[line].length; column++){
            Entity currentEntity = this.getEntityAt(line, column);
            if(currentEntity != null){
                currentEntity.setHp(0); //Permet de tuer effectivement l'entité
                removeEntity(line, column); //Elle est retirée de la map
            }
        }
    }
}
