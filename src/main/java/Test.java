import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Map map = new Map();

//        Skeleton skTest = new Skeleton(15, 3, map);
//        Tree trTest = new Oak(3,12,map);
//        Tree trTest2 = new Oak(3,11,map);
//        map.addEntity(skTest);
//        map.addEntity(trTest);
//        map.addEntity(trTest2);
//        map.removeEntity(0,0);
//
//        System.out.println(map);
//        System.out.println(skTest.getLine());
//        System.out.println(skTest.getColumn());
//        skTest.update();
//        System.out.println(map);
//        System.out.println(skTest.getLine());
//        System.out.println(skTest.getColumn());
//        System.out.println(map.getEntityAt(3,13));

        Shop test = new Shop(new Money(100),map, new Scanner(System.in));
        test.openShop();
    }
}
