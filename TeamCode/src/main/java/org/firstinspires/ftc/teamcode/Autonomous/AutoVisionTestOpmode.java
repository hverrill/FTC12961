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
    private DcMotor leftFront, leftBack, rightFront, rightBack, winch, intakeLeft, intakeRight = null;
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
        winch = hardwareMap.get(DcMotor.class, "winch");
        leftHook = hardwareMap.get(Servo.class, "leftHook");
        rightHook = hardwareMap.get(Servo.class, "rightHook");
        grab = hardwareMap.get(Servo.class, "grab");
        turn = hardwareMap.get(Servo.class, "turn");
        leftGrab = hardwareMap.get(Servo.class, "leftGrab");
        rightGrab = hardwareMap.get(Servo.class, "rightGrab");
        revIMU = hardwareMap.get(BNO055IMU.class, "imu");


        motorInit();
        robot = new Movement(this);
        sensorSuite = new Measurement(revIMU, hardwareMap);
        portal.createVuforia(VuforiaLocalizer.CameraDirection.BACK, hardwareMap, telemetry);




        waitForStart(); /** START THE PROGRAM */
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


        portal.update(portal.stone);
//        make robot turn 90 to allign with block wall
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

//        //reverse to allign, then strafe to side to collect skystone
        robot.reverse(.2, 350);
        robot.turnClockwise(.5, 340);
        intakeLeft.setPower(.6);
        intakeRight.setPower(.6);
        robot.forward(.12, 2500);


//        sleep(200);
//        leftFront.setPower(-0.3);
//        leftBack.setPower(0.3);
//        rightFront.setPower(0.3);
//        rightBack.setPower(-0.3);
//        sleep(500);
//        leftFront.setPower(0);
//        leftBack.setPower(0);
//        rightFront.setPower(0);
//        rightBack.setPower(0);
//        sleep(200);
//        leftFront.setPower(0.3);
//        leftBack.setPower(0.3);
//        rightFront.setPower(0.3);
//        rightBack.setPower(0.3);
//        sleep(400);
//        leftFront.setPower(0);
//        leftBack.setPower(0);
//        rightFront.setPower(0);
//        rightBack.setPower(0);
//        sleep(200);
        // END


        telemetry.addData("Path", "Complete");
        telemetry.update();
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


}