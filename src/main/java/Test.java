import javax.swing.*;
public class Test {
    public static void main(String[] args){
        Timer test = new Timer(1000, e -> System.out.println("caca"));
        test.setRepeats(true);
        test.start();
        JOptionPane.showMessageDialog(null, "Timer Running - Click OK to end");
    }
}
