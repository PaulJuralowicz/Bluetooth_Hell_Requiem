package main.java.bmeg257.mp4.arduino;

/**
 * Just conviently bundles accel in x y z and gyro in x y z into one nice object, in real values
 */
public class Motion6 {

    private final double aX;
    private final double aY;
    private final double aZ;
    private final double gX;
    private final double gY;
    private final double gZ;
    private final static double GRAVITY = 9.81;
    private final static double GYRO = 125.0;

    /**
     *  COnstructor that takes raw acc and gyro and converts to real world values woo
     * @param aX
     * @param aY
     * @param aZ
     * @param gX
     * @param gY
     * @param gZ
     */

    public Motion6(short aX, short aY, short aZ, short gX, short gY, short gZ){
        this.aX = convertAcc(aX);
        this.aY = convertAcc(aY);
        this.aZ = convertAcc(aZ);
        this.gX = convertGyro(gX);
        this.gY = convertGyro(gY);
        this.gZ = convertGyro(gZ);
    }

    /**
     * Converts a raw signed int to a real m/s^2 acceleration
     * @param acc raw acc
     * @return signed acceleration
     */
    private double convertAcc(short acc){
        return ((((double) acc)/ 16384.0) * GRAVITY);
    }

    /**
     * Converts a raw signed int to a real rad/s angular v
     * @param gyro raw gyro
     * @return signed gyro
     */
    private double convertGyro(short gyro){
        return ((((double) gyro)/ 16384.0) * GYRO);
    }

    /**
     * The following functions return specified acc or gyro.
     * @return specificied acceleration or gyro
     */
    public double getAX(){
        return aX;
    }
    public double getAY(){
        return aY;
    }
    public double getAZ(){
        return aZ;
    }
    public double getGX(){
        return gX;
    }
    public double getGY(){
        return gY;
    }
    public double getGZ(){
        return gZ;
    }
}
