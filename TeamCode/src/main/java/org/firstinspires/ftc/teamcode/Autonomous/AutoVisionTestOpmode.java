//Imports:
package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.FPS.Measurement;
import org.firstinspires.ftc.teamcode.FPS.Movement;
import org.firstinspires.ftc.teamcode.FPS.Vision;


/**
 * This program is Checkmate Robotics' Autonomous Program Template.
 */

@Autonomous(name="Vision Test Opmode", group="Sky")
//@Disabled
public class AutoVisionTestOpmode extends LinearOpMode {

    /*
     *  Declare OpMode Members: */
    //Ex: private DcMotor exampleMotor = null;
    public DcMotor leftFront, leftBack, rightFront, rightBack, intakeLeft, intakeRight = null; // winch,
    private Servo leftHook, rightHook, grab, turn, leftGrab, rightGrab;
    float hsvValues[] = {0F, 0F, 0F};
    private ColorSensor colorLeft, colorRight;
    public TouchSensor blockToggle;
    boolean leftSeesYellow = false;
    boolean rightSeesYellow = false;
    boolean nextToWall = false;
    private VuforiaLocalizer vuforia = null;
    String side = "";
    public BNO055IMU revIMU;
    private ElapsedTime runtime = new ElapsedTime();
    Vision portal = new Vision();
    Movement robot = null;
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
        //winch = hardwareMap.get(DcMotor.class, "winch");
        leftHook = hardwareMap.get(Servo.class, "leftHook");
        rightHook = hardwareMap.get(Servo.class, "rightHook");

        revIMU = hardwareMap.get(BNO055IMU.class, "imu");
        blockToggle = hardwareMap.get(TouchSensor.class, "blockToggle");

        robot = new Movement(this);
        sensorSuite = new Measurement(revIMU, hardwareMap);
        portal.createVuforia(VuforiaLocalizer.CameraDirection.BACK, hardwareMap, telemetry);


        waitForStart(); /** START THE PROGRAM */
        runtime.reset();
        //START
        robot.forward(.2, 400);
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        sleep(200);
        leftFront.setPower(-0.3);
        leftBack.setPower(0.3);
        rightFront.setPower(0.3);
        rightBack.setPower(-0.3);
        stopAfter(850);
        runtime.seconds();
        portal.update(portal.stone);

        while(!portal.isTargetVisible(portal.stone) && !isStopRequested()) { // Run the vuforia detection (change if you need to detect later)
            portal.update(portal.stone);
            leftFront.setPower(.02);
            leftBack.setPower(.02);
            rightFront.setPower(.02);
            rightBack.setPower(.02);
        }
        while((Math.abs(portal.xTranslation) < margin) && !isStopRequested()){
            portal.update(portal.stone);
            if(portal.xTranslation < -margin){
                leftFront.setPower(.02);
                leftBack.setPower(.02);
                rightFront.setPower(.02);
                rightBack.setPower(.02);
            }
            if(portal.xTranslation > margin){
                leftFront.setPower(-.02);
                leftBack.setPower(-.02);
                rightFront.setPower(-.02);
                rightBack.setPower(-.02);
            }
            telemetry.addData("Angle =", sensorSuite.getAngle());
        }
        stopAfter(0);
        //reverse to allign, then strafe to side to collect skystone
        robot.reverse(.2, 350);
        rotate(55);
        robot.succ(runtime);
        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(9999999);
    }

    public void stopAfter(long millis){
        sleep(millis);
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        intakeLeft.setPower(0);
        intakeRight.setPower(0);
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