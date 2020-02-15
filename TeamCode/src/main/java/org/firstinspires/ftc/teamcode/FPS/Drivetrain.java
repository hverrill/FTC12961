package org.firstinspires.ftc.teamcode.FPS;

import android.os.SystemClock;
import android.sax.TextElementListener;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Autonomous.AutoVisionTestOpmode;
import org.firstinspires.ftc.teamcode.Autonomous.IMUTest;

public class Drivetrain {
    public double theta, forwardSpeed, finaltheta, robotSpeed, directionSpeed, leftfront, rightfront, leftback, rightback;
    public HardwareMap hardwareMap;
    public DcMotor leftFront, leftBack, rightFront, rightBack, winchLeft, winchRight, intakeLeft, intakeRight;
    public Servo leftHook, rightHook, fourbarLeft, fourbarRight, blockGrab, capstone;
    public TouchSensor blockToggle;
    public int lfGoal, lbGoal, rfGoal, rbGoal;
    public BNO055IMU revIMU;
    public Odometry odometry;
    public Measurement sensorSuite;

    //INTAKE LEFT AND WINCH LEFT ARE X1 AND Y RESPECTIVELY
    ElapsedTime timer = new ElapsedTime();
//    IMUTest ree = new IMUTest();
    public void declare(){

    }
    public void map(HardwareMap map){
        hardwareMap = map;

        leftFront = hardwareMap.get(DcMotor.class, "LF");
        leftBack = hardwareMap.get(DcMotor.class, "LB");
        rightFront = hardwareMap.get(DcMotor.class, "RF");
        rightBack = hardwareMap.get(DcMotor.class, "RB");
        intakeLeft = hardwareMap.get(DcMotor.class, "intakeLeft");
        intakeRight = hardwareMap.get(DcMotor.class, "intakeRight");
        winchLeft = hardwareMap.get(DcMotor.class, "winchLeft");
        winchRight = hardwareMap.get(DcMotor.class, "winchRight");
        leftHook = hardwareMap.get(Servo.class, "leftHook");
        rightHook = hardwareMap.get(Servo.class, "rightHook");
        blockToggle = hardwareMap.get(TouchSensor.class, "blockToggle");
        blockGrab = hardwareMap.get(Servo.class, "blockGrab");
        fourbarLeft = hardwareMap.get(Servo.class, "fourbarLeft");
        fourbarRight = hardwareMap.get(Servo.class, "fourbarRight");
        capstone = hardwareMap.get(Servo.class, "capstone");
        revIMU = hardwareMap.get(BNO055IMU.class, "imu");

        //Drivetrain
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.REVERSE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
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
        odometry = new Odometry(this);
        sensorSuite = new Measurement(revIMU, hardwareMap);
    }
    public void calculate(double xVectorLeft, double yVectorLeft, double xVectorRight, double yVectorRight){
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
        while(odometry.getX()/(initial+dist)<.95){
            setPowerAll(.1 + .4-(odometry.getX()/(initial+dist))*.4);
        }
        setPowerAll(0);
    }
    public void reverse(int dist){
        double initial = odometry.getX();
        while(odometry.getX()/(initial-dist)<.95){
            setPowerAll(-(.1 + .4-(odometry.getX()/(initial-dist))*.4));
        }
        setPowerAll(0);
    }
    public void strafeLeft(int dist){
        double initial = odometry.getY();
        double pow;
        while(odometry.getY()/(initial-dist)<.99){
            pow = .25 + .5-(odometry.getY()/(initial-dist))*.5;
            setPower(-pow, pow, pow, -pow);
        }
        setPowerAll(0);
    }
    public void strafeRight(int dist){
        double initial = odometry.getY();
        double pow;
        while(odometry.getY()/(initial+dist)<.95){
            pow = .1 + .4-(odometry.getY()/(initial+dist))*.4;
            setPower(pow, -pow, -pow, pow);
        }
        setPowerAll(0);
    }
    public void rotate(float degrees) {

        boolean turning = true;

        float targetAngle = sensorSuite.getAngle().angle1 + degrees;
        double ratio;
        double powerPolarity = degrees / Math.abs(degrees);
        double powerMultiplier;

        while (turning) { // && !isStopRequested()

            ratio = (sensorSuite.getAngle().angle1 / targetAngle);

            powerMultiplier = 1 - ratio;

            if (Math.abs(powerMultiplier) < .4) {
                powerMultiplier = .4;
            }
            if (Math.abs(powerMultiplier) > .9) {
                powerMultiplier = .9;
            }
            setPower(-.4 * powerMultiplier * powerPolarity, -.4 * powerMultiplier * powerPolarity, .4 * powerMultiplier * powerPolarity, .4 * powerMultiplier * powerPolarity);

            if (ratio > .99) turning = false;

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
