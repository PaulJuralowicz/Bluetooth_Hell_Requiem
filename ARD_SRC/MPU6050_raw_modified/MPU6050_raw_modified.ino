// I2Cdev and MPU6050 must be installed as libraries, or else the .cpp/.h files
// for both classes must be in the include path of your project
#include "I2Cdev.h"
#include "MPU6050.h"
#include <SoftwareSerial.h>
SoftwareSerial BT(10, 11); 

// Arduino Wire library is required if I2Cdev I2CDEV_ARDUINO_WIRE implementation
// is used in I2Cdev.h
#if I2CDEV_IMPLEMENTATION == I2CDEV_ARDUINO_WIRE
    #include "Wire.h"
#endif

// class default I2C address is 0x68
// specific I2C addresses may be passed as a parameter here
// AD0 low = 0x68 (default for InvenSense evaluation board)
// AD0 high = 0x69
MPU6050 accelgyro;
//MPU6050 accelgyro(0x69); // <-- use for AD0 high

int16_t ax, ay, az;
int16_t gx, gy, gz;
// These are the 16 bit signed integers that the MPU spits out

#define LED_PIN 13
bool blinkState = false;
//Debugging LED blinking, tells us if it works

/*
 * Serial stuff is just bug testing in the serial monitor of arduino IDE. Useful
 */
void setup() {
    // join I2C bus (I2Cdev library doesn't do this automatically)
    #if I2CDEV_IMPLEMENTATION == I2CDEV_ARDUINO_WIRE
        Wire.begin();
    #elif I2CDEV_IMPLEMENTATION == I2CDEV_BUILTIN_FASTWIRE
        Fastwire::setup(400, true);
    #endif

    //Initialize the bluetooth connection. Basicly, it acts as a serial port
    BT.begin(9600);

    // initialize serial communication
    // (38400 chosen because it works as well at 8MHz as it does at 16MHz, but
    // it's really up to you depending on your project)
    Serial.begin(38400);

    // initialize device
    Serial.println("Initializing I2C devices...");
    accelgyro.initialize();
    accelgyro.setSleepEnabled(false);

    // verify connection
    Serial.println("Testing device connections...");
    Serial.println(accelgyro.testConnection() ? "MPU6050 connection successful" : "MPU6050 connection failed");

    // use the code below to change accel/gyro offset values
    
    Serial.println("Updating internal sensor offsets...");
    // -76  -2359 1688  0 0 0
    
    /*accelgyro.CalibrateAccel(6);
    accelgyro.CalibrateGyro(6);*/
    accelgyro.PrintActiveOffsets();
    

    // configure Arduino LED pin for output
    // set digital pin to control as an output
    pinMode(13, OUTPUT);
    // set the data rate for the SoftwareSerial port
    // Send test message to other device
}

/*
 * Waits for computer to tell it it wants data, and then sends it
 */
void loop() {
  
 /* accelgyro.getMotion6(&ax, &ay, &az, &gx, &gy, &gz);
  Serial.print("a/g:\t");
  Serial.print(ax); Serial.print("\t");
  Serial.print(ay); Serial.print("\t");
  Serial.print(az); Serial.print("\t");
  Serial.print(gx); Serial.print("\t");
  Serial.print(gy); Serial.print("\t");
  Serial.println(gz); */
  if(BT.available() > 0){
    if ((char)BT.read() == 'l'){ 
      accelgyro.getMotion6(&ax, &ay, &az, &gx, &gy, &gz);
      BT.write(ax >> 8);
      BT.write(ax & 0xFF);
      BT.write(ay >> 8);
      BT.write(ay & 0xFF);
      BT.write(az >> 8);
      BT.write(az & 0xFF);
      BT.write(gx >> 8);
      BT.write(gx & 0xFF);
      BT.write(gy >> 8);
      BT.write(gy & 0xFF);
      BT.write(gz >> 8);
      BT.write(gz & 0xFF); 
    } 
    blinkState = !blinkState;
    digitalWrite(LED_PIN, blinkState);
  }
}
