import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Map map = new Map();

        Skeleton skTest = new Skeleton(15, 0, map);
        Tree trTest = new Oak(0,1,map);
//        Tree trTest2 = new Oak(0,1,map);
        skTest.setColumn(3);
        map.addEntity(skTest);
        map.addEntity(trTest);


//        map.getChainsaws()[0] = false;

        System.out.println(map);
        System.out.println(trTest.getHp());
        for(int i = 0; i < 17; i++){
            skTest.update();
            System.out.println(map);
            System.out.println(trTest.getHp());
        }
    }
}
