package tools;

import view.GameScreen;

import java.awt.*;

public class MathTools {
    public static Point nearestUnitPoint(int x, int y){

        int returnX, returnY;

        double xRatio = ((double) x / GameScreen.widthPerUnit);
        double yRatio = ((double) y / GameScreen.heightPerUnit);

        double decimalPartXRatio = decimalPartOfDouble(xRatio);
        double decimalPartYRatio = decimalPartOfDouble(yRatio);
        if(decimalPartXRatio < 0){
            returnX = 0;
        }
        else if(decimalPartXRatio < 0.5){
            returnX = (int) (Math.floor(xRatio)*GameScreen.widthPerUnit);
        }
        else{
            returnX = (int) ((Math.floor(xRatio)+1)*GameScreen.widthPerUnit);
        }

        if(decimalPartYRatio < 0){
            returnY = 0;
        }
        else if(decimalPartYRatio < 0.5){
            returnY = (int) (Math.floor(yRatio)*GameScreen.heightPerUnit);
        }
        else{
            returnY = (int) ((Math.floor(yRatio)+1)*GameScreen.heightPerUnit);
        }

        return new Point(returnX,returnY);
    }

    public static double decimalPartOfDouble(double x){
        int rounded = (int) x;
        return x - rounded;
    }
}
