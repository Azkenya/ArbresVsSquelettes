public class Skeleton extends Entity {
    private int range;
    private int speed;

    public Skeleton(int hp, int lane, int damage, int range, int speed, Map map) {
        super(hp, lane, 15, damage, map);
        this.range = range;
        this.speed = speed;
    }

    // la lane correspond à la ligne sur laquelle le squelette va apparaitre,
    // il apparaitra toujours sur la colonne 15
    public Skeleton(int hp, int lane, Map map) {
        super(hp, lane, 14, 1, map);
        this.range = 2;
        this.speed = 1;
    }

    @Override
    public void update() {
        int currentLine = this.getLine();
        int currentColumn = this.getColumn();
        // Range of the first tree we can attack
        int treeAt = this.treeInOurRange();
        // If there is an entity in our range is a Tree, attack it
        if (treeAt != -1) {
            this.attack(this.getMap().getEntityAt(currentLine, currentColumn - treeAt - 1));
        }

        // Then, move as far as we can regarding our speed and room before us

        int currentForwardMove = speed;
        // While we have no room before us AND we want to stay here, try moving 1 unit
        // less
        while (this.getMap().getEntityAt(currentLine, currentColumn - currentForwardMove) != null
                && currentForwardMove > 0) {
            currentForwardMove--;
        }
        // If we move
        if (currentForwardMove > 0) {
            // Moving
            int newColumn = currentColumn - currentForwardMove;
            if (newColumn < 0) {
                newColumn = 0;
            }
            this.setColumn(newColumn);
            this.getMap().addEntity(this);
            this.getMap().removeEntity(currentLine, currentColumn);
        }
    }

    public boolean attack(Entity e) {
        if (this.range >= Math.abs(this.getLine() - e.getLine())) {
            e.kill(this.getDamage());
            return true;
        }
        return false;
    }

    // Returns -1 if there is no tree in our range
    // Else returns the range between us and the first tree
    public int treeInOurRange() {
        int currentRange = 0;
        while (currentRange <= this.range) {
            if (this.getColumn() - currentRange - 1 < 0) {
                return -1;
            }
            if (this.getMap().getEntityAt(this.getLine(), this.getColumn() - currentRange - 1) instanceof Tree) {
                return currentRange;
            }
            currentRange++;
        }
        return -1;
    }

    public int getRange() {
        return this.range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String toString() {
        return "S";
    }

    @Override
    public Map getMap() {
        return super.getMap();
    }
}
