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
        this.range = 1;
        this.speed = 1;
    }

    @Override
    public void update() {
        boolean stillHere = true; //Used if the skelton gets removed while we're updating it
        int actualLine = this.getLine();
        int actualColumn = this.getColumn();

        //Range of the first tree we can attack
        int treeAt = this.treeInOurRange();
        //If there is an entity in our range is a Tree, attack it
        if(treeAt != -1){
            this.attack(this.getMap().getEntityAt(actualLine,actualColumn - treeAt - 1));
        }

        //Moves as far as possible
        this.moveOne(this.speed);
    }

    //Advances a skeleton by one step
    public void moveOne(int leftToMove){
        //If we have no moving to do, dont move
        if(leftToMove == 0){
            return;
        }
        //If we are at the end of the map
        if(this.getColumn() == 0){
            //If there is no chainsaw, lose
            if(!this.getMap().getChainsaws()[this.getLine()]){
                this.skeletonsWin();
            }
            //Else activate chainsaw
            else{
                this.getMap().killEverythingOnLine(this.getLine());
                this.getMap().getChainsaws()[this.getLine()] = false;
                return;
            }
        }

        //Else check for entity next to us
        //If there is none, move
        if(this.getMap().getEntityAt(this.getLine(), this.getColumn() - 1) == null){
            this.getMap().removeEntity(this.getLine(), this.getColumn());
            this.setColumn(this.getColumn() - 1);
            this.getMap().addEntity(this);
            this.moveOne(leftToMove - 1);
        }
    }


    public boolean attack(Entity e) {
        if (this.range >= Math.abs(this.getLine() - e.getLine())) {
            e.kill(this.getDamage());
            return true;
        }
        return false;
    }

    //Returns -1 if there is no tree in our range
    //Else returns the range between us and the first tree
    public int treeInOurRange(){
        int actualRange = 0;
        while(actualRange <= this.range && this.getColumn() - actualRange - 1 >= 0){
            if(this.getMap().getEntityAt(this.getLine(),this.getColumn() - actualRange - 1) instanceof Tree){
                return actualRange;
            }
            actualRange++;
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

    public void skeletonsWin(){
        System.out.println("Les squelettes ont gagné, game over");
        System.exit(0);
    }
}
