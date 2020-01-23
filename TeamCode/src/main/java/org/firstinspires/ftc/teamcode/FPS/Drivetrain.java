package org.firstinspires.ftc.teamcode.FPS;

import android.os.SystemClock;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Drivetrain {
    public double theta, forwardSpeed, finaltheta, robotSpeed, directionSpeed, leftfront, rightfront, leftback, rightback;
    public HardwareMap hardwareMap;
    public DcMotor leftFront, leftBack, rightFront, rightBack, winchLeft, winchRight, intakeLeft, intakeRight;
    public Servo leftHook, rightHook, fourbarLeft, fourbarRight, blockGrab;
    public TouchSensor blockToggle;
    public BNO055IMU revIMU;

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
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        //Winch
        winchRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        winchLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        winchRight.setDirection(DcMotorSimple.Direction.REVERSE);

        intakeLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intakeRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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


    public void stopAfter(long millis){
        long starttime = SystemClock.elapsedRealtime();
        while(starttime + millis > SystemClock.elapsedRealtime()){ //This is the code that is not waiting  - Henry
        }
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        intakeLeft.setPower(0);
        intakeRight.setPower(0);
    }



    public void turnClockwise(double power, long millis){
        leftFront.setPower(-power);
        leftBack.setPower(-power);
        rightFront.setPower(power);
        rightBack.setPower(power);
        stopAfter(millis);
    }
    public void turnAntiClockwise(double power, long millis){
        leftFront.setPower(power);
        leftBack.setPower(power);
        rightFront.setPower(-power);
        rightBack.setPower(-power);
        stopAfter(millis);
    }
    public void forward(double power, long millis){
        leftFront.setPower(power);
        leftBack.setPower(power);
        rightFront.setPower(power);
        rightBack.setPower(power);
        stopAfter(millis);
    }
    public void reverse(double power, long millis){
        leftFront.setPower(-power);
        leftBack.setPower(-power);
        rightFront.setPower(-power);
        rightBack.setPower(-power);
        stopAfter(millis);
    }
    public void strafe(double power, long millis){
        leftFront.setPower(-power);
        leftBack.setPower(power);
        rightFront.setPower(power);
        rightBack.setPower(-power);
        stopAfter(millis);
    }
    public void succ(ElapsedTime time){
        double starttime = time.milliseconds();
        intakeRight.setPower(-.6);
        intakeLeft.setPower(.6);
        leftFront.setPower(.15);
        leftBack.setPower(.15);
        rightFront.setPower(.15);
        rightBack.setPower(.12);
        // loop until we detect a block or x seconds have expired

        while(blockToggle.getValue() < 1) {

        }

        stopAfter(0);

    }



}
