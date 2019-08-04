/*************************************
 *
 * Kevin Mitchell
 * CSCI 4100 DA Fall 2018
 * Problem 4.22: The Monte Carlo
 *
 **************************************/

import java.util.concurrent.ThreadLocalRandom;

public class MonteCarlo implements Runnable{

    /*Declare Global Variables*/
    //Implement Lock as a bool
    private static boolean LOCK = false;
    private static int COUNT_INSIDE = 0;
    private static int sampleSize = 0;



    public static boolean isInside(double x, double y) {
        if (x * x + y * y <= 1.0) {
            return true;
        }
        return false;
    }
    public static double[][] fillArray(int ArraySize){
        double[][] rtn = new double[ArraySize][2];

        for(int i=0;i<ArraySize;i++){
            double x = Math.random();
            double y = Math.random();
            rtn[i][0] = x;
            rtn[i][1] = y;
        }

        return rtn;
    }

    public static void main (String args[]){

        Thread foo = new Thread(new MonteCarlo());
        foo.start();
        try {
            foo.join();

            if(!LOCK) {
                System.out.print(COUNT_INSIDE + " of the ");
                System.out.println(sampleSize + " sampled numbers fell within the circle");
                double piGuess = 4.0 * ((double) COUNT_INSIDE / (double) sampleSize);

                System.out.println("Pi is around: " + piGuess);
            }
        }
        catch(Exception e){
            System.out.println("Oof");
        }
    }

    public void run() {
        //Aquire Lock
        if (!LOCK) {
            LOCK = true;
            //Generate a Sample size that is large enough to get consistent results
            sampleSize = ThreadLocalRandom.current().nextInt(1000000);
            double[][] samples = fillArray(sampleSize);

            int count = 0;
            for (double[] val :
                    samples) {
                if (isInside(val[0], val[1])) {
                    count++;
                }
            }

            //Set global and release lock
            COUNT_INSIDE = count;
            LOCK = false;
        }
    }
}



