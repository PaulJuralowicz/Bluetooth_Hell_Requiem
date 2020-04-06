package main.java.bmeg257.mp4.arduino;

import java.util.ArrayList;

/**
 * This beefy boy will get ya a displacement.
 */

public class Calculator {

    private Bluetooth sensor; //the bluetooth connection to the arduino
    private Motion6Raw current;
    private static final String btURL = "btspp://001403055AA3:1;authenticate=false;encrypt=false;master=false."; //default BT URL
    public static final double MIN_SCALE_FOR_SIMILARITY = 0.0000001; //a minimal scalar for sim test

    /**
     * Constructor, starts the bluetooth and everything
     */

    /**
     * Determine the similarity between two motions
     * The similarity metric, gamma, is the sum of squares of
     * instantaneous differences.
     * If either of the motion is full of zeroes (like frefall or somethng) returns 0
     *
     * @param base is the base thing to be checked
     * @param other is not null.
     * @return the similarity between this wave and other.
     */
    public double similarity(ArrayList<Motion6Raw> base, ArrayList<Motion6Raw> other) {
        ArrayList<Motion6Raw> sw1 = (ArrayList<Motion6Raw>)other.clone();
        ArrayList<Motion6Raw> sw2 = (ArrayList<Motion6Raw>)base.clone();

        //make the datasets the same size
        while(sw1.size() != sw2.size()){
            if (sw1.size() > sw2.size()) {
                sw2.add(new Motion6Raw());
            } else if (sw1.size() < sw2.size()) {
                sw1.add(new Motion6Raw());
            }
        }

        double similarityTotal = (simCalc(sw1, sw2) + simCalc(sw2, sw1)) / 2.0;

        return similarityTotal;
    }

    /**
     * Computes the similarity between 2 datasets. Since the overall similarity
     * involves compute similarity between sw1, sw2 and sw2, sw1, i broke it up
     * so I wouldn't repeat code.
     *
     * @param sw1 first dataset
     * @param sw2 second dataset
     * @return similarity the similiarty between the 2 datasets
     */
    private double simCalc(ArrayList<Motion6Raw> sw1, ArrayList<Motion6Raw> sw2) {
        double compA = computeAOrC(sw2);
        double compB = computeB(sw1, sw2);
        double compC = computeAOrC(sw1);
        double scaleFactor;

        if (Double.compare(compA, 0.0) == 0 || Double.compare(compC, 0.0) == 0.0) {
            return 0.0;
        }
        if (Double.compare(compB, 0.0) == 0) {
            scaleFactor = MIN_SCALE_FOR_SIMILARITY;
        } else {
            scaleFactor = compB / compA;
        }
        if (scaleFactor < 0) {
            scaleFactor = MIN_SCALE_FOR_SIMILARITY;
        }

        double similarity = (1.0 / (1.0 + computePolynomial(scaleFactor, compA, compB, compC)));
        return similarity;
    }

    private double computePolynomial(double x, double a, double b, double c) {
        return ((x * x * a) - 2.0 * (x * b) + c);
    }

    /**
     * Computes the A or C component of the similarity function
     *
     * @param sw1 is the dataset. You only need one of the two datasets for A or C
     * @return the A or C component
     */
    private double computeAOrC(ArrayList<Motion6Raw> sw1) {
        double componentAOrC = 0;
        for (int i = 0; i < sw1.size(); i++) {
            componentAOrC += ((sw1.get(i).getAX()/32768.0) * (sw1.get(i).getAX()/32768.0))
                    + ((sw1.get(i).getAY()/32768.0) * (sw1.get(i).getAY()/32768.0))
                    + ((sw1.get(i).getAZ()/32768.0) * (sw1.get(i).getAZ()/32768.0))
                    + ((sw1.get(i).getGX()/32768.0) * (sw1.get(i).getGX()/32768.0))
                    + ((sw1.get(i).getGY()/32768.0) * (sw1.get(i).getGY()/32768.0))
                    + ((sw1.get(i).getGZ()/32768.0) * (sw1.get(i).getGZ()/32768.0));
        }
        return componentAOrC;
    }

    /**
     * Computes the B component of the similarity function
     *
     * @param sw1 the first dataset, which must have the same length as sw1 on both channels
     * @param sw2 the second dataset, which must have the same length as sw2 on both channels.
     * @return the B component
     */
    private double computeB(ArrayList<Motion6Raw> sw1, ArrayList<Motion6Raw> sw2) {
        double componentB = 0;
        for (int i = 0; i < sw2.size(); i++) {
            componentB += ((sw1.get(i).getAX()/32768.0) * (sw2.get(i).getAX()/32768.0))
                    + ((sw1.get(i).getAY()/32768.0) * (sw2.get(i).getAY()/32768.0))
                    + ((sw1.get(i).getAZ()/32768.0) * (sw2.get(i).getAZ()/32768.0))
                    + ((sw1.get(i).getGX()/32768.0) * (sw2.get(i).getGX()/32768.0))
                    + ((sw1.get(i).getGY()/32768.0) * (sw2.get(i).getGY()/32768.0))
                    + ((sw1.get(i).getGZ()/32768.0) * (sw2.get(i).getGZ()/32768.0));
        }
        return componentB;
    }
}
