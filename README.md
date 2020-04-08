# Bluetooth_Hell_Requiem
BMEG 257 pain

All the source code for the BMEG 257 project!

src has everything!

main has all the code!

test has the coded tests!

If you got any questions, email me at pauljuralowicz@gmail.com

PROOF OF VARIOUS FUNCTIONS:

Ideally, you would run the test yourself to see it in action, but if you would like examples of it working, look at the test code, 
then reffer to the files in the local folder. The data.txt contains a log of what the server has recieved 
(it should contain an example of a survery response). The various txt with excercise names contain sample raw data from the arduino.

Lets quickly go over the structure of arduino raw data

Each instance of time has 6 values attached to it, accerlation in the x, accerlation in the y, acceleration in the z, gyroscope x,
gyroscope y, gyroscope z. These are also the order written in the file.
The raw data (what is in the file) stores theses accelerations and rad/s measures as a 16 bit signed integer. 
They range from -32,768 to 32,767.
To convert to real values, the range represents accerlation from -2.00 to 2.00 g and -250 to 250 rad/s respectively.
So, if you want to conver to real values, take your acc, divide by 32,768, multiply by 2 to get to g, or instead of multiplying by 2
multiply by 250 to get rad/s.

The orientation of the device made it so the x-axis pointed downwards. This is why the x value is often large, as gravity is not removed.
This is somewhat convient for reading the raw data, and desipite the fact that it is not really separated, you can tell when the
next chunk of data begins as it starts with a really large and negative x value!

Want to watch the videos? They area all in the local folder
