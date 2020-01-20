package org.firstinspires.ftc.teamcode.FPS;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class Drivetrain {
    public double theta, forwardSpeed, finaltheta, robotSpeed, directionSpeed, leftfront, rightfront, leftback, rightback;
    public HardwareMap hardwareMap;
    public DcMotor leftFront, leftBack, rightFront, rightBack, winchLeft, winchRight, intakeLeft, intakeRight;
    public Servo leftHook, rightHook, fourbarLeft, fourbarRight, blockGrab;
    public TouchSensor blockToggle;
    public Drivetrain(HardwareMap map){
        hardwareMap = map;
    }

    public void declare(){

    }
    public void map(){
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

        //Drivetrain
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.REVERSE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        //Winch
        winchRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        winchLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        winchRight.setDirection(DcMotorSimple.Direction.REVERSE);
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


}
