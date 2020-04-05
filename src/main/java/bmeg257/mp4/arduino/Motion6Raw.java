package main.java.bmeg257.mp4.arduino;

/**
 * Just conviently bundles accel in x y z and gyro in x y z into one nice object, in raw values
 */
public class Motion6Raw {

    private final double aX;
    private final double aY;
    private final double aZ;
    private final double gX;
    private final double gY;
    private final double gZ;

    /**
     *  COnstructor that takes raw acc and gyro and converts to real world values woo
     * @param aX
     * @param aY
     * @param aZ
     * @param gX
     * @param gY
     * @param gZ
     */

    public Motion6Raw(short aX, short aY, short aZ, short gX, short gY, short gZ){
        this.aX = aX;
        this.aY = aY;
        this.aZ = aZ;
        this.gX = gX;
        this.gY = gY;
        this.gZ = gZ;
    }

    /**
     * Default constructor that makes it all 0.
     */
    public Motion6Raw(){
        this((short)0,(short)0,(short)0,(short)0,(short)0,(short)0);
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
