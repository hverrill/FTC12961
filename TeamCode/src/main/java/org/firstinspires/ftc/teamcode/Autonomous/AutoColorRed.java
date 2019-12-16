//Imports:
package org.firstinspires.ftc.teamcode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * This program is Checkmate Robotics' Autonomous Program Template.
 */

@Autonomous(name="SKYSTONE_RED", group="Sky")
//@Disabled
public class AutoColorRed extends LinearOpMode {

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
    String side = "";
    private ElapsedTime runtime = new ElapsedTime();


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


        /**
         * Telemetry */
        telemetry.addData("Status", "Ready to run");
        telemetry.update();
        /*
        Wait for driver to hit the start button on the controller:
         */
        waitForStart();
        //START

        leftFront.setPower(0.3);
        leftBack.setPower(-0.3);
        rightFront.setPower(-0.3);
        rightBack.setPower(0.3);
        sleep(1550);
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        sleep(600);
        leftFront.setPower(-0.15);
        leftBack.setPower(-0.15);
        rightFront.setPower(-0.15);
        rightBack.setPower(-0.15);
        sleep(100);
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        sleep(600);
        leftFront.setPower(0.3);
        leftBack.setPower(0.3);
        rightFront.setPower(0.3);
        rightBack.setPower(0.3);
        sleep(320);


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
        sleep(20);
        leftFront.setPower(.3);
        leftBack.setPower(-.3);
        rightFront.setPower(-.3);
        rightBack.setPower(.3);
        sleep(100);
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        while (runtime.seconds()<30){
            leftFront.setPower(-.15);
            leftBack.setPower(.15);
            rightFront.setPower(.15);
            rightBack.setPower(-.15);

            if (!((colorLeft.red() + colorLeft.green())/2 > colorLeft.blue() + 7)){
                side = "right";
                // SERVO CODE TO GRAB BLOCK
                break;
            }
            if (!((colorRight.red() + colorRight.green())/2 > colorRight.blue() + 7)) {
                side = "left";
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
        //AFTER COLOR SENSOR CODE
        sleep(4000);
        if (side == "left"){
            leftGrab.setPosition(1);
        } else {
            leftGrab.setPosition(.6);
        }
        if (side == "right"){
            rightGrab.setPosition(0);
        } else {
            rightGrab.setPosition(.25);
        }
        leftFront.setPower(-0.1);
        leftBack.setPower(0.1);
        rightFront.setPower(0.1);
        rightBack.setPower(-0.1);
        sleep(1500);
        leftFront.setPower(-0.5);
        leftBack.setPower(-0.5);
        rightFront.setPower(-0.5);
        rightBack.setPower(-0.5);
        sleep(600);
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        sleep(500);
        leftFront.setPower(-0.5);
        leftBack.setPower(0.5);
        rightFront.setPower(0.5);
        rightBack.setPower(-0.5);
        sleep(1500);
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        sleep(500);




        //END



        /**
         * : */


        /**
         * Stop OpMode */

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }
}