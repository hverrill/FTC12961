//Imports:
package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
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
    private ElapsedTime runtime = new ElapsedTime();
    public Vision portal = new Vision();





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
        colorLeft = hardwareMap.get(ColorSensor.class, "colorLeft");
        colorRight = hardwareMap.get(ColorSensor.class, "colorRight");



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

        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        portal.createVuforia(VuforiaLocalizer.CameraDirection.BACK, hardwareMap, telemetry);

        waitForStart(); /** START THE PROGRAM */
            //START

        leftFront.setPower(-0.3);
        leftBack.setPower(0.3);
        rightFront.setPower(0.3);
        rightBack.setPower(-0.3);
        sleep(700);
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        sleep(600);

                sleep(600);
        //make robot turn 90 to allign with block wall
        while(!portal.isTargetVisible(portal.stone) && !isStopRequested()) { // Run the vuforia detection (change if you need to detect later)

            leftFront.setPower(.1);
            leftBack.setPower(.1);
            rightFront.setPower(.1);
            rightBack.setPower(.1);


            portal.update(portal.stone);

        }
        //reverse to allign, then strafe to side to collect skystone


        leftFront.setPower(0.3);
        leftBack.setPower(0.3);
        rightFront.setPower(0.3);
        rightBack.setPower(0.3);
        sleep(1200);
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        sleep(100);





        telemetry.addData("Path", "Complete");
        telemetry.update();
    }
}