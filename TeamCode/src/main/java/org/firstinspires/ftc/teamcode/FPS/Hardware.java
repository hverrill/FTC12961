package org.firstinspires.ftc.teamcode.FPS;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 *  Hello! This is the hardware class. You do all of the initalization code for hardware here,
 *  making it a lot easier to create new opmodes. Better yet, if you change a motor or server
 *  here, it will change it in every class that uses it!
 *
 * */

public class Hardware {

    /**
     * As a part of this section, please remember:
     * If you need to create a variable of a new type, please remember to create a key for each variable!
     * It's vital that you can go back and remember what each variable does.
     */

    // DOUBLES:
    public double theta, forwardSpeed, finaltheta, robotSpeed, directionSpeed, leftfront, rightfront, leftback, rightback;
    /**
     * Please list what each double variable is for:
     * example: example is a variable used as an example for this list
     * varname: text
     * varname: text
     */


    // INTEGERS:
    public int lfGoal, lbGoal, rfGoal, rbGoal;
    /**
     * Please list what each double variable is for:
     * example: example is a variable used as an example for this list
     * varname: text
     * varname: text
     */


    // MOTORS:
    public DcMotor leftFront, leftBack, rightFront, rightBack, winchLeft, winchRight, intakeLeft, intakeRight;
    /**
     * Please list what each motor does:
     * examplemotor: the example motor is used as an example, and is located on the front of the robot
     * varname: text
     * varname: text
     */


    // SERVOS:
    public Servo leftHook, rightHook, fourbarLeft, fourbarRight, blockGrab, capstone;
    public CRServo tapePark;
    /**
     * Please list what each servo does:
     * exampleservo: the example servo is used as an example, and is located on the rear of the robot
     * varname: text
     * varname: text
     */


    // SENSORS:
    public TouchSensor blockToggle;
    public BNO055IMU revIMU;
    /**
     * Please list what each sensor is for:
     * examplesesor: the example sensor is used to detect bad memes, and is located on the right side of the robot
     * blockToggle: this is to detect if a block has been obtained and is in the collection bin
     * revIMU: this is the REV Expansion Hub's internal gyro/accelerometer, and is partially used to
     *      determine the location and orientation of the robot
     * varname: text
     * varname: text
     */


    // OTHER CLASSES:
            // These are necessary if you want to use the Odometry or IMU to work!
    public Odometry odometry;
    public Measurement sensorSuite;
    public HardwareMap hardwareMap; // Especially don't delete this one

    //INTAKE LEFT AND WINCH LEFT ARE X1 AND Y RESPECTIVELY

    public void map(HardwareMap map){
        hardwareMap = map; // this sets the hardwareMap variable in this class to the hardwareMap variable from inside the opmode

        /** Motors: */
        leftFront = hardwareMap.get(DcMotor.class, "LF");
        leftBack = hardwareMap.get(DcMotor.class, "LB");
        rightFront = hardwareMap.get(DcMotor.class, "RF");
        rightBack = hardwareMap.get(DcMotor.class, "RB");
        intakeLeft = hardwareMap.get(DcMotor.class, "intakeLeft");
        intakeRight = hardwareMap.get(DcMotor.class, "intakeRight");
        winchLeft = hardwareMap.get(DcMotor.class, "winchLeft");
        winchRight = hardwareMap.get(DcMotor.class, "winchRight");

        /** Servos: */
        leftHook = hardwareMap.get(Servo.class, "leftHook");
        rightHook = hardwareMap.get(Servo.class, "rightHook");
        blockGrab = hardwareMap.get(Servo.class, "blockGrab");
        fourbarLeft = hardwareMap.get(Servo.class, "fourbarLeft");
        fourbarRight = hardwareMap.get(Servo.class, "fourbarRight");
        capstone = hardwareMap.get(Servo.class, "capstone");
        tapePark = hardwareMap.get(CRServo.class, "tapePark");

        /** Sensors: */
        blockToggle = hardwareMap.get(TouchSensor.class, "blockToggle");
        revIMU = hardwareMap.get(BNO055IMU.class, "imu");

        /** Set Motor Behavior: */
        // Drivetrain:
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.REVERSE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Reset encoders...
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        // ...then turn them off.
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        //Winch
        winchRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        winchLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        winchLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        winchRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        winchRight.setDirection(DcMotorSimple.Direction.REVERSE);



        odometry = new Odometry(this); // parses this hardware class into the Odometry class so it can use the robot's hardware
        sensorSuite = new Measurement(this); // parses this hardware class into the Measurement class so it can use the robot's hardware
    }

    public void drivePowerCalculate(double xVectorLeft, double yVectorLeft, double xVectorRight, double yVectorRight){
        robotSpeed = Math.sqrt(Math.pow(xVectorLeft, 2) + Math.pow(xVectorLeft, 2));
        theta = Math.atan2(-xVectorLeft, yVectorLeft);
        forwardSpeed = -(yVectorLeft + yVectorRight)/2;
        finaltheta = -theta + (Math.PI/4);
        forwardSpeed = -(yVectorLeft + yVectorRight)/2;
        directionSpeed = xVectorRight*.5;

        leftfront = (robotSpeed * Math.sin(finaltheta) - directionSpeed) + forwardSpeed;
        leftback = (robotSpeed * Math.cos(finaltheta) - directionSpeed) + forwardSpeed;
        rightfront = (robotSpeed * Math.cos(finaltheta) + directionSpeed) + forwardSpeed;
        rightback = (robotSpeed * Math.sin(finaltheta)+ directionSpeed) + forwardSpeed;
    }



    public void setPower( double lf, double lb, double rf, double rb){
        leftFront.setPower(lf);
        leftBack.setPower(lb);
        rightFront.setPower(rf);
        rightBack.setPower(rb);
    }


    public void setPowerAll( double p){
        leftFront.setPower(p);
        leftBack.setPower(p);
        rightFront.setPower(p);
        rightBack.setPower(p);
    }


    public void forward(int dist){
        double initial = odometry.getX();
        double percent = odometry.getX()/(initial+dist);
        boolean direction = false;
        boolean moving = true;
        if (percent > 1) direction = true;
        // else direction is default false
        while(moving && odometry.checkX()){
            percent = (odometry.getX())/(initial + dist);
            setPowerAll(.4);
            if (!direction) {
                if (percent >= 1) moving = false;
            }
            else {
                if (percent <= 1) moving = false;
            }
//            setPowerAll(.2 + .4-(percent*.4));
        }
        setPowerAll(0);


    }

    public void forward(int dist, Telemetry telemetry){
        double initial = odometry.getX();
        double percent = (odometry.getX())/(initial+dist);
        boolean direction = false;
        boolean moving = true;
        if (percent > 1) direction = true;
        // else direction is default false
        while(moving && odometry.checkX()){
            percent = (odometry.getX())/(initial+dist);
            setPowerAll(.4);
            if (!direction)
            {
                if (percent >= 1) moving = false;
            }
            else
            {
                if (percent <= 1) moving = false;
            }
//            setPowerAll(.2 + .4-(percent*.4));
            telemetry.addData("percent", percent);
            telemetry.addData("initial", initial);
            telemetry.addData("goal", initial+dist);
            telemetry.addData("current", odometry.getX());
            telemetry.update();
        }
        setPowerAll(0);
    }
    public void reverse(int dist){
        double initial = odometry.getX();
        double percent = (odometry.getX())/(initial-dist);
        boolean direction = false;
        boolean moving = true;
        if (percent > 1) direction = true;
        // else direction is default false
        while(moving && odometry.checkX()){
            percent = (odometry.getX())/(initial-dist);
            setPowerAll(-.4);
            if (!direction)
            {
                if (percent >= 1) moving = false;
            }
            else
            {
                if (percent <= 1) moving = false;
            }
//            setPowerAll(.2 + .4-(percent*.4));
        }
        setPowerAll(0);
    }
    public void reverse(int dist, Telemetry telemetry){

        double initial = odometry.getX();
        double percent = (odometry.getX())/(initial-dist);
        boolean direction = false;
        boolean moving = true;
        if (percent > 1) direction = true;
        // else direction is default false
        while(moving && odometry.checkX()){
            percent = (odometry.getX())/(initial-dist);
            setPowerAll(-.4);
            if (!direction)
            {
                if (percent >= 1) moving = false;
            }
            else
            {
                if (percent <= 1) moving = false;
            }
//            setPowerAll(.2 + .4-(percent*.4));
            telemetry.addData("percent", percent);
            telemetry.addData("initial", initial);
            telemetry.addData("current", odometry.getX());
            telemetry.update();
        }
        setPowerAll(0);
    }
    public void strafeLeft(int dist){

        double initial = odometry.getY();
        double percent = Math.abs(odometry.getY())/Math.abs(initial-dist);
        boolean direction = false;
        boolean moving = true;
        if (percent > 1) direction = true;
        // else direction is default false
        while(moving && odometry.checkY()){
            percent = Math.abs(odometry.getY())/Math.abs(initial-dist);
            setPower(-.5, .5, .5, -.5);
            if (!direction)
            {
                if (percent >= 1) moving = false;
            }
            else
            {
                if (percent <= 1) moving = false;
            }
//            setPowerAll(.2 + .4-(percent*.4));
//            telemetry.addData("percent", percent);
//            telemetry.addData("initial", initial);
//            telemetry.addData("current", odometry.getX());
//            telemetry.update();
        }
        setPowerAll(0);
    }
    public void strafeRight(int dist){
        double initial = odometry.getY();
        double percent = Math.abs(odometry.getY())/Math.abs(initial+dist);
        boolean direction = false;
        boolean moving = true;
        if (percent > 1) direction = true;
        // else direction is default false
        while(moving && odometry.checkY()){
            percent = Math.abs(odometry.getY())/Math.abs(initial+dist);
            setPower(.5, -.5, -.5, .5);
            if (!direction)
            {
                if (percent >= 1) moving = false;
            }
            else
            {
                if (percent <= 1) moving = false;
            }
//            setPowerAll(.2 + .4-(percent*.4));
//            telemetry.addData("percent", percent);
//            telemetry.addData("initial", initial);
//            telemetry.addData("current", odometry.getX());
//            telemetry.update();
        }
        setPowerAll(0);
    }
    public void strafeLeft(int dist, Telemetry telemetry){
        double initial = odometry.getY();
        double percent = Math.abs(odometry.getY())/Math.abs(initial-dist);
        boolean direction = false;
        boolean moving = true;
        if (percent > 1) direction = true;
        // else direction is default false
        while(moving && odometry.checkY()){
            percent = Math.abs(odometry.getY())/Math.abs(initial-dist);
            setPower(-.5, .5, .5, -.5);
            if (!direction)
            {
                if (percent >= 1) moving = false;
            }
            else
            {
                if (percent <= 1) moving = false;
            }
//            setPowerAll(.2 + .4-(percent*.4));
            telemetry.addData("percent", percent);
            telemetry.addData("initial", initial);
            telemetry.addData("current", odometry.getX());
            telemetry.update();
        }
        setPowerAll(0);
    }
    public void strafeRight(int dist, Telemetry telemetry){
        double initial = odometry.getY();
        double percent = Math.abs(odometry.getY())/Math.abs(initial+dist);
        boolean direction = false;
        boolean moving = true;
        if (percent > 1) direction = true;
        // else direction is default false
        while(moving && odometry.checkY()){
            percent = Math.abs(odometry.getY())/Math.abs(initial+dist);
            setPower(.5, -.5, -.5, .5);
            if (!direction)
            {
                if (percent >= 1) moving = false;
            }
            else
            {
                if (percent <= 1) moving = false;
            }
//            setPowerAll(.2 + .4-(percent*.4));
            telemetry.addData("percent", percent);
            telemetry.addData("initial", initial);
            telemetry.addData("current", odometry.getX());
            telemetry.update();
        }
        setPowerAll(0);
    }
//    public void strafeRight(int dist){
//        double initial = odometry.getY();
//        double percent = odometry.getY()/(initial+dist);
//        while(!(percent > .98 && percent < 1.02)){
//            percent = odometry.getY()/(initial+dist);
//            setPower(.4, -.4, -.4, .4);
//        }
//        setPowerAll(0);
//    }
    public void rotate(float degrees, Telemetry telemetry) {

        double powerPolarity = degrees / Math.abs(degrees);
        double power;
        double percent;
        double targetAngle = sensorSuite.getAngle().angle1 + degrees;
        boolean rotating = true;


        boolean direction = false;
        if(Math.abs(sensorSuite.getAngle().angle1) > Math.abs(targetAngle)) direction = true;
        // the else here is that direction remains false which is angle < target

        while (rotating) { // && !isStopRequested()
            percent = sensorSuite.getAngle().angle1/targetAngle;
            power =  (.3 + Range.clip(.3-(Math.abs(percent)*.3), 0, .3))*powerPolarity;
            setPower(-power, -power, power, power);


            if (!direction) {
                if( Math.abs(sensorSuite.getAngle().angle1+10) >= Math.abs(targetAngle)) rotating = false;
            }
            else {
                if (Math.abs(sensorSuite.getAngle().angle1-10) <= Math.abs(targetAngle)) rotating = false;
            }

            telemetry.addData("target", targetAngle);
            telemetry.addData("IMU", sensorSuite.getAngle().angle1);
            telemetry.addData("percent", percent);
            telemetry.addData("current", sensorSuite.getAngle().angle1);
            telemetry.addData("direction", direction);
            telemetry.addData("rotating", rotating);
            telemetry.update();

        }
        setPowerAll(0);

    }
    public void rotate(float degrees) {

        double powerPolarity = degrees / Math.abs(degrees);
        double power;
        double percent;
        double targetAngle = sensorSuite.getAngle().angle1 + degrees;
        boolean rotating = true;


        boolean direction = false;
        if(Math.abs(sensorSuite.getAngle().angle1) > Math.abs(targetAngle)) direction = true;
        // the else here is that direction remains false which is angle < target

        while (rotating) { // && !isStopRequested()
            percent = Math.abs(sensorSuite.getAngle().angle1)/Math.abs(targetAngle);
            power =  (.3 + Range.clip(.3-(percent*.3), 0, .3))*powerPolarity;
            setPower(-power, -power, power, power);


            if (!direction) {
                if( Math.abs(sensorSuite.getAngle().angle1+10) >= Math.abs(targetAngle)) rotating = false;
            }
            else {
                if (Math.abs(sensorSuite.getAngle().angle1-10) <= Math.abs(targetAngle)) rotating = false;
            }

//            telemetry.addData("target", targetAngle);
//            telemetry.addData("IMU", sensorSuite.getAngle().angle1);
//            telemetry.addData("percent", percent);
//            telemetry.addData("current", sensorSuite.getAngle().angle1);
//            telemetry.addData("direction", direction);
//            telemetry.addData("rotating", rotating);
//            telemetry.update();

        }
        setPowerAll(0);

    }





//    public void turnClockwise(double power, long millis){
//        leftFront.setPower(-power);
//        leftBack.setPower(-power);
//        rightFront.setPower(power);
//        rightBack.setPower(power);
//        ree.stopAfter(millis);
//    }
//    public void turnAntiClockwise(double power, long millis){
//        leftFront.setPower(power);
//        leftBack.setPower(power);
//        rightFront.setPower(-power);
//        rightBack.setPower(-power);
//        ree.stopAfter(millis);
//    }
//    public void forward(double power, long millis){
//        leftFront.setPower(power);
//        leftBack.setPower(power);
//        rightFront.setPower(power);
//        rightBack.setPower(power);
//        ree.stopAfter(millis);
//    }
//    public void reverse(double power, long millis){
//        leftFront.setPower(-power);
//        leftBack.setPower(-power);
//        rightFront.setPower(-power);
//        rightBack.setPower(-power);
//        ree.stopAfter(millis);
//    }
//    public void strafe(double power, long millis){
//        leftFront.setPower(-power);
//        leftBack.setPower(power);
//        rightFront.setPower(power);
//        rightBack.setPower(-power);
//        ree.stopAfter(millis);
//    }
//    public void succ(ElapsedTime time){
//        double starttime = time.milliseconds();
//        intakeRight.setPower(-.6);
//        intakeLeft.setPower(.6);
//        leftFront.setPower(.15);
//        leftBack.setPower(.15);
//        rightFront.setPower(.15);
//        rightBack.setPower(.12);
//        // loop until we detect a block or x seconds have expired
//
//        while(blockToggle.getValue() < 1) {
//
//        }
//
//        ree.stopAfter(0);
//
//    }
//
//

}
