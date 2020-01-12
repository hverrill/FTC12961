//Imports:
package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import Depricated.AutoMovement;


/**
 * This program is Checkmate Robotics' Autonomous Program Template.
 */

@Autonomous(name="Auto Color Sensor Test", group="Sky")
//@Disabled
public class AutoColorTest extends LinearOpMode {

    /*
     *  Declare OpMode Members: */
    //Ex: private DcMotor exampleMotor = null;
    private DcMotor leftFront, leftBack, rightFront, rightBack, winchTop, winchBottom, intakeLeft, intakeRight = null;
    private Servo leftHook, rightHook, grab, turn, leftGrab, rightGrab;
    float hsvValues[] = {0F, 0F, 0F};
    private ColorSensor colorLeft, colorRight;
    boolean leftSeesYellow = false;
    boolean rightSeesYellow = false;
    boolean nextToWall = false;
    String side = "";
    //private DistanceSensor distance;


    private ElapsedTime runtime = new ElapsedTime();

    private AutoMovement robot = new AutoMovement();

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
        winchBottom = hardwareMap.get(DcMotor.class, "winchBottom");
        winchTop = hardwareMap.get(DcMotor.class, "winchTop");
        leftHook = hardwareMap.get(Servo.class, "leftHook");
        rightHook = hardwareMap.get(Servo.class, "rightHook");
        grab = hardwareMap.get(Servo.class, "grab");
        turn = hardwareMap.get(Servo.class, "turn");
        colorLeft = hardwareMap.get(ColorSensor.class, "colorLeft");
        colorRight = hardwareMap.get(ColorSensor.class, "colorRight");
        //distance = hardwareMap.get(DistanceSensor.class, "distance");



        leftFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE); // Change to forward after unswitching polarity
        rightBack.setDirection(DcMotor.Direction.REVERSE); // Change to forward after unswitching polarity


        /**
         * Telemetry */
        telemetry.addData("Status", "Ready to run");
        telemetry.update();
        /*
        Wait for driver to hit the start button on the controller:
         */
        waitForStart();

        //START COLOR SENSOR CODE
        while(runtime.seconds()<30){
            telemetry.addData("Red Left: ", colorLeft.red());
            telemetry.addData("Green Left: ", colorLeft.green());
            telemetry.addData("Blue Left: ", colorLeft.blue());
            telemetry.addData("Red Right: ", colorRight.red());
            telemetry.addData("Green Right: ", colorRight.green());
            telemetry.addData("Blue Right: ", colorRight.blue());
            if (((colorLeft.red() + colorLeft.green())/2) > colorLeft.blue() + 10){
                leftSeesYellow = true;
            } else {
                leftSeesYellow = false;
            }


            if ((colorRight.red() + colorRight.green())/2 > colorRight.blue() + 10){
                rightSeesYellow = true;
            } else {
                rightSeesYellow = false;
            }

            nextToWall = (leftSeesYellow | rightSeesYellow);
            if (nextToWall) {
                leftFront.setPower(0);
                leftBack.setPower(0);
                rightFront.setPower(0);
                rightBack.setPower(0);
                break;
            } else {
//                leftFront.setPower(-.3);
//                leftBack.setPower(.3);
//                rightFront.setPower(.3);
//                rightBack.setPower(-.3);
                leftFront.setPower(.15);
                leftBack.setPower(.15);
                rightFront.setPower(.15);
                rightBack.setPower(.15);
            }

            telemetry.addData("Left sees yellow:", leftSeesYellow);
            telemetry.addData("Right sees yellow:", rightSeesYellow);
            telemetry.addData("Close to wall:", nextToWall);

            telemetry.update();
        }
        leftFront.setPower(-.1);
        leftBack.setPower(-.1);
        rightFront.setPower(-.1);
        rightBack.setPower(-.1);
        sleep(200);
        leftFront.setPower(.3);
        leftBack.setPower(-.3);
        rightFront.setPower(-.3);
        rightBack.setPower(.3);
        sleep(1200);
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        while (runtime.seconds()<30){
            leftFront.setPower(-.3);
            leftBack.setPower(.3);
            rightFront.setPower(.3);
            rightBack.setPower(-.3);
            
            if (!((colorLeft.red() + colorLeft.green())/2 > colorLeft.blue() + 10)){
                side = "left";
                // SERVO CODE TO GRAB BLOCK
                break;
            }
            if (!((colorRight.red() + colorRight.green())/2 > colorRight.blue() + 10)) {
                side = "right";
                // SERVO CODE TO GRAB BLOCK
                break;
            }
        }
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        telemetry.addData("Skystone is on the ", side, " of the robot.");
        telemetry.update();
        //END COLOR SENSOR CODE

        sleep(9999999);
         //robot.turn(90, .5);
//         telemetry.addData("2", "2");
//         sleep(30000);
        /**
         * : */


        /**
         * Stop OpMode */

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }
}