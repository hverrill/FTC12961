//Imports:
package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.FPS.Measurement;
import org.firstinspires.ftc.teamcode.FPS.Movement;
import org.firstinspires.ftc.teamcode.FPS.Vision;


/**
 * This program is Checkmate Robotics' Autonomous Program Template.
 */

@Autonomous(name="IMU Test Opmode", group="Sky")
//@Disabled
public class IMUTest extends LinearOpMode {

    /*
     *  Declare OpMode Members: */
    //Ex: private DcMotor exampleMotor = null;
    public DcMotor leftFront, leftBack, rightFront, rightBack, winch, intakeLeft, intakeRight = null;
    private Servo leftHook, rightHook, grab, turn, leftGrab, rightGrab;
    float hsvValues[] = {0F, 0F, 0F};
    private ColorSensor colorLeft, colorRight;
    boolean leftSeesYellow = false;
    boolean rightSeesYellow = false;
    boolean nextToWall = false;
    private VuforiaLocalizer vuforia = null;
    String side = "";
    public BNO055IMU revIMU;
    private ElapsedTime runtime = new ElapsedTime();
    Vision portal = new Vision();
    //Movement robot = null;
    Measurement sensorSuite = null;

    double margin = .5;

    @Override
    public void runOpMode() {
        /**
         * Hardware Variables: */
        //Ex: exampleMotor  = hardwareMap.get(DcMotor.class, "motor");
        leftFront  = hardwareMap.get(DcMotor.class, "LF");
        leftBack = hardwareMap.get(DcMotor.class, "LB");
        rightFront  = hardwareMap.get(DcMotor.class, "RF");
        rightBack = hardwareMap.get(DcMotor.class, "RB");
        intakeLeft = hardwareMap.get(DcMotor.class, "intakeLeft");
        intakeRight = hardwareMap.get(DcMotor.class, "intakeRight");
        winch = hardwareMap.get(DcMotor.class, "winch");
        leftHook = hardwareMap.get(Servo.class, "leftHook");
        rightHook = hardwareMap.get(Servo.class, "rightHook");
        grab = hardwareMap.get(Servo.class, "grab");
        turn = hardwareMap.get(Servo.class, "turn");
        leftGrab = hardwareMap.get(Servo.class, "leftGrab");
        rightGrab = hardwareMap.get(Servo.class, "rightGrab");
        revIMU = hardwareMap.get(BNO055IMU.class, "imu");


        motorInit();
        //robot = new Movement(this);
        sensorSuite = new Measurement(revIMU, hardwareMap);
        portal.createVuforia(VuforiaLocalizer.CameraDirection.BACK, hardwareMap, telemetry);

        waitForStart(); /** START THE PROGRAM */
        rotate(-90);

        while (!isStopRequested()) {
            telemetry.addData("Angle 1 =", sensorSuite.getAngle().angle1);
            telemetry.addData("Angle 2 =", sensorSuite.getAngle().angle2);
            telemetry.addData("Angle 3 =", sensorSuite.getAngle().angle3);
            telemetry.update();
        }

    }

    public void motorInit(){
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE); // Change to forward after unswitching polarity
        rightBack.setDirection(DcMotor.Direction.REVERSE); // Change to forward after unswitching polarity
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void rotate(float degrees){

        boolean turning = true;

        float targetAngle = sensorSuite.getAngle().angle1 + degrees;
        double ratio;
        double powerPolarity = degrees/Math.abs(degrees);
        double powerMultiplier;

        while(turning){

            ratio = sensorSuite.getAngle().angle1/targetAngle;

            powerMultiplier = 1-ratio;

            if(Math.abs(powerMultiplier) < .15){
                powerMultiplier = .2;
            }
            if(Math.abs(powerMultiplier) > .85){
                powerMultiplier = .85;
            }

            leftBack.setPower(-.2 * powerMultiplier * powerPolarity);
            leftFront.setPower(-.2 * powerMultiplier * powerPolarity);
            rightBack.setPower(.2 * powerMultiplier * powerPolarity);
            rightFront.setPower(.2 * powerMultiplier * powerPolarity);

            if(ratio > .95) turning = false;

        }
        leftBack.setPower(0);
        leftFront.setPower(0);
        rightBack.setPower(0);
        rightFront.setPower(0);
    }


}