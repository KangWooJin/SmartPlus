import sys
import serial
import RPi.GPIO as GPIO
import time

num = sys.argv[1]

#True
myBool = True
myBool2 = False


ser = serial.Serial("/dev/ttyAMA0",9600)
GPIO.setmode(GPIO.BCM)
GPIO.setup(2,GPIO.OUT)
GPIO.output(2,myBool)

print (num)
 ## no 1 yes '1'
if num == '1': 
	ser.write("!")
elif num == '2':
        ser.write("@")
elif num == '3':
        ser.write("#")
elif num == '4':
	ser.write("$")
elif num == '5':
	ser.write("%")
elif num == '6':
	GPIO.output(2,myBool2)
	print("A")
	time.sleep(1)
	GPIO.output(2,myBool)
	print("B")
	time.sleep(1)
	GPIO.cleanup()
elif num == '7':
	print("ASD")

ser.close();
