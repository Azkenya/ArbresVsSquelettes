public class Test {
    public static void main(String[] args) {
        Map map = new Map();

        Skeleton skTest = new Skeleton(15, 3, map);
        Tree trTest = new Oak(3,12,map);
        map.addEntity(skTest);
        map.addEntity(trTest);
//        map.removeEntity(0,0);

        System.out.println(trTest.getHp());
        System.out.println(map);

        skTest.update();

        System.out.println(trTest.getHp());
        System.out.println(map);

        skTest.update();

        System.out.println(trTest.getHp());
        System.out.println(map);


    }
}
