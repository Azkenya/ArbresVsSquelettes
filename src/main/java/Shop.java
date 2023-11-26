import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Scanner;

public class Shop {
    private Money playerMoney;
    private ArrayList<Tree> availableTrees;
    private Map map;
    private Scanner userInput;
    private final Game game;

    public Shop(Money playerMoney, Map map, Scanner userInput, Game game) {
        this.playerMoney = playerMoney;
        this.availableTrees = new ArrayList<>();
        this.game = game;

        //TODO Moyen d'automatiser l'ajout ?
        availableTrees.add(new Oak(-1,-1,null));
        availableTrees.add(new Acacia(-1,-1,null));
        availableTrees.add(new PineTree(-1,-1,null));
        availableTrees.add(new IceTree(-1,-1,null));
        availableTrees.add(new Baobab(-1,-1,null));

        this.map = map;
        this.userInput = userInput;
    }

    // this method takes in a tree and checks
    // if the tree is available to buy
    // if it is, it calls buyTree
    public Tree selectTree(int i){
        Money selectTreeCost = new Money(availableTrees.get(i-1).getCost());
        if(!playerMoney.remove(selectTreeCost)){
            System.out.println("\nYou don't have enough money to buy this tree\n");
            return null;
        }
        int[] coos = askForCoordinates();

        //Si coos est null, cela veut dire que l'on a souhaité annuler notre achat lors du choix des coordonnées
        if(coos == null){
            playerMoney.add(selectTreeCost); //On refund l'achat
            return null;
        }
        try{
            return availableTrees.get(i - 1).getClass().getConstructor(int.class,int.class,Map.class).newInstance(coos[0],coos[1],map); //Crée dynamiquement l'arbre souhaité
        }catch (Exception e){
            return null;
        }
    }

    // this method takes in a tree and adds the tree
    // to the map if the player has enough money through the addTree method
    // from the map class
    public void buyTree(int i) {
        Tree t = selectTree(i);
        if(t == null){
            return;
        }
        if (this.map.addEntity(t)) {
            System.out.println("\nSuccesfully placed the tree at line " + t.getLine() + " and at column " + t.getColumn() + "\n");
            game.addTree(t);
        }
        else{
            playerMoney.add(new Money(t.getCost()));//Si on ne peut pas placer l'arbre, on recrédite le compte du joueur du prix de l'arbre
        }
    }

    public void openShop() {
        boolean firstTimeEntering = true;
        while(true){

            if (firstTimeEntering){
                System.out.println("Welcome to the tree shop !");
                firstTimeEntering = false;
            }
            System.out.println("You currently have " + playerMoney);
            System.out.println("Please select the tree you want : ");

            displayTreesAvailable(); //Affiche la liste des arbres que l'on peut acheter
            String answer = userInput.nextLine();

            if(answer.equals("Q") || answer.equals("q")){
                System.out.println("See you next time !\n");
                break;
            }
            if(answer.equals("M") || answer.equals("m")){
                System.out.println(map);
                continue;
            }

            if(!answerIsANumber(answer)){
                continue;//Le mot clé continue permet de revenir au début de la boucle
            }

            int chosenTree = Integer.parseInt(answer);

            //Si le nombre choisi n'est pas dans la liste proposée, on renvoie l'utilisateur à son choix
            if(chosenTree > availableTrees.size() || chosenTree <= 0){
                System.out.println("\nError : the number you inputed is not among buyable trees\n");
                continue;
            }

            buyTree(chosenTree);
        }
    }

    public void displayTreesAvailable(){
        int k = 1;
        for (Tree t : availableTrees){
            System.out.printf("%d / %s tree : Cost - %d, Damages - %d ",k,t.getClass().toString().substring(6), t.getCost(), t.getDamage());
            if(t instanceof IceTree){
                System.out.print(" - This tree can freeze enemies");
            }else if(t instanceof Acacia){
                System.out.print(" - This tree randomly makes you earn money");
            }
            System.out.println();
            k++;
        }
        System.out.println("Press M to display the map or Q to exit the shop...");
    }

    public int[] askForCoordinates(){
        int[] coos = new int[2];
        boolean stillAsking = true;
        while(stillAsking) {

            System.out.println();
            System.out.println(map);

            System.out.println("Please input the line you want to place your tree / Press Q to exit :");
            String answer = userInput.nextLine();

            if(answer.equals("Q") || answer.equals("q")){
                stillAsking = false;
                coos = null;
                System.out.println();
                break;
            }

            if (!answerIsANumber(answer)) {
                continue;
            }

            int line = Integer.parseInt(answer);

            if (line >= map.numberOfLines() || line < 0) {
                System.out.println("Error : line number is not valid");
                continue;
            }
            coos[0] = line;
            break;
        }

        while(stillAsking){
            System.out.println("Please input the column you want to place your tree / Press Q to exit :");
            String answer = userInput.nextLine();

            if(answer.equals("Q") || answer.equals("q")){
                coos = null;
                System.out.println();
                break;
            }
           if(!answerIsANumberOrAChar(answer)){
                continue;
            }
            int column = -1;
            try{
                column= Integer.parseInt(answer);
            }catch(NumberFormatException e){
                column = answer.toUpperCase().charAt(0) - 'A'+10;
            }
            System.out.println("Column : "+column);
            if(column >= map.numberOfColumns() || column < 0){
                System.out.println("Error : column is not valid");
                continue;
            }
            coos[1] = column;
            break;
        }
        return coos;
    }


    //On teste si la réponse est bien un entier
    public static boolean answerIsANumberOrAChar(String answer){
        if(answer.length() > 2){
            System.out.println("Error : please input a valid column");
            return false;
        }else{
            try{
                Integer.parseInt(answer);
            }
            catch(NumberFormatException e){
                try{
                    if(answer.toUpperCase().charAt(0) < 'A' || answer.toUpperCase().charAt(0) > 'Z'){
                        System.out.println("Error : please input a valid column");
                        return false;
                    }
                }catch(Exception ex){
                        System.out.println("Error : please input a valid column");
                        return false;
                    }
            }  
        }     
        return true;   
    }
    public static boolean answerIsANumber(String answer){
        try{
            Integer.parseInt(answer);
        }
        catch(NumberFormatException e){
            System.out.println("Error : please input a valid number");
            return false;
        }
        return true;
    }
}
