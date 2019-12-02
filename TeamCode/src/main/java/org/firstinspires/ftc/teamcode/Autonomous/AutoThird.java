//Imports:
package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.FPS.AutoMovement;


/**
 * This program is Checkmate Robotics' Beta Blue Side Autonomous for the 2019-2020 game Skystone.
 */

@Autonomous(name="Auto 2019/11/2 - Skystone Blu", group="Sky")
//@Disabled
public class AutoThird extends LinearOpMode {

    /*
     *  Declare OpMode Members: */
    //Ex: private DcMotor exampleMotor = null;
    private DcMotor leftFront, leftBack, rightFront, rightBack, winchTop, winchBottom, intakeLeft, intakeRight = null;
    private Servo leftHook, rightHook, grab, turn;

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



        leftFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE); // Change to forward after unswitching polarity
        rightBack.setDirection(DcMotor.Direction.REVERSE); // Change to forward after unswitching polarity
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        /**
         * Telemetry */
        telemetry.addData("Status", "Ready to run");
        telemetry.update();
        /*
        Wait for driver to hit the start button on the controller:
         */
        waitForStart();
        //strafe left
        leftFront.setPower(0.25);
        leftBack.setPower(-0.25);
        rightFront.setPower(-0.25);
        rightBack.setPower(0.25);
        sleep(1690);
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        sleep(300);
        // go forward
        leftFront.setPower(-0.20);
        leftBack.setPower(-0.20);
        rightFront.setPower(-0.20);
        rightBack.setPower(-0.20);
        sleep(1500);
        //Grab the foundation
        leftHook.setPosition(.3);
        rightHook.setPosition(.7);
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        sleep(500);
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        sleep(100);
        //back up to wall
        leftFront.setPower(0.25);
        leftBack.setPower(0.25);
        rightFront.setPower(0.25);
        rightBack.setPower(0.25);
        sleep(2500);
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        sleep(100);
        // let go of foundation
        leftHook.setPosition(.9);
        rightHook.setPosition(.1);
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        sleep(100);
        //strafe left
        leftFront.setPower(-0.7);
        leftBack.setPower(0.7);
        rightFront.setPower(0.7);
        rightBack.setPower(-0.7);
        sleep(850);
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        sleep(100);
        //go forward
        leftFront.setPower(-0.20);
        leftBack.setPower(-0.20);
        rightFront.setPower(-0.20);
        rightBack.setPower(-0.20);
        sleep(700);
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        sleep(100);
        //strafe left (push foundation into depot)
        leftFront.setPower(0.35);
        leftBack.setPower(-0.35);
        rightFront.setPower(-0.35);
        rightBack.setPower(0.35);
        sleep(1000);
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        sleep(100);
        //strafe left under bridge
        leftFront.setPower(-0.5);
        leftBack.setPower(0.5);
        rightFront.setPower(0.5);
        rightBack.setPower(-0.5);
        sleep(750);
        leftFront.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        rightBack.setPower(0);
        sleep(100);





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