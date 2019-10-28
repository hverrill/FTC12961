
package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.CRServo;

/**
 * Team 12961 Checkmate Robotics: Teleop Opmode
 * In Use: Y
 */

@TeleOp(name="The Bestest Program", group="Rover")

/*
Don't uncomment! This registers the OpMode:
@Disabled
 */


public class TeleopRover extends OpMode {

    private DcMotor left = null;
    private DcMotor right = null;
    private DcMotor winch = null;
    private DcMotor elbow = null;
    private Servo release = null;
    private CRServo spool = null;
    private CRServo yoink = null;
    private CRServo rotate = null;
    private double multiplier;
    private double leftpow;
    private double rightpow;


    private ElapsedTime runtime = new ElapsedTime();

    /**
     * One time INIT code:
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        left = hardwareMap.get(DcMotor.class,"left");
        right = hardwareMap.get(DcMotor.class,"right");
        winch = hardwareMap.get(DcMotor.class,"winch");
        release = hardwareMap.get(Servo.class,"release");
        elbow = hardwareMap.get(DcMotor.class, "elbow");
        spool = hardwareMap.get(CRServo.class, "spool");
        yoink = hardwareMap.get(CRServo.class, "yoink");
        rotate = hardwareMap.get(CRServo.class, "rotate");

        left.setDirection(DcMotor.Direction.FORWARD);
        right.setDirection(DcMotor.Direction.FORWARD);

    }

    /**
     * Looping before PLAY after INIT code:
     */
    @Override
    public void init_loop() {
    }

    //Reset System Time:
    @Override
    public void start() {runtime.reset();}

    /**
     * Looping PLAY code:
     */
    @Override
    public void loop() {

        /* Drive Code */
        // Control wheels, with the option to turn left or right
        if (gamepad1.right_bumper) {
            if (gamepad1.left_bumper) {
                rightpow = -gamepad1.left_stick_y;
                leftpow = -gamepad1.right_stick_y;
            } else {
                rightpow = -gamepad1.left_stick_y * 0.7;
                leftpow = -gamepad1.right_stick_y * 0.7;
            }
        } else {
            if (gamepad1.left_bumper) {
                rightpow = gamepad1.left_stick_y;
                leftpow = gamepad1.right_stick_y;
            } else {
                rightpow = gamepad1.left_stick_y * 0.7;
                leftpow = gamepad1.right_stick_y * 0.7;
            }
        }
        left.setPower(leftpow);
        right.setPower(rightpow);

        /* Winch Code */
        // Tighten or give slack to the rope on the winch
        if (gamepad1.y) {
            winch.setPower(-.7);
        } else if (gamepad1.a) {
            winch.setPower(.7);
        } else {
            winch.setPower(0);
        }

        /* Latch Release Code */
        // Move the screen door latch on the robot in order to release the landing arm.
        if (gamepad1.x){
            release.setPosition(.1);
        } else {
            release.setPosition(1);
        }

        /* Extend/Retract Arm Code */
        if (gamepad2.right_bumper) {
            spool.setPower(-1);
        } else if (gamepad2.left_bumper) {
            spool.setPower(1);
        } else {
            spool.setPower(0);
        }

        /* Intake Code */
        if (gamepad2.right_trigger != 0) {
            yoink.setPower(-1);
        } else if (gamepad2.left_trigger != 0) {
            yoink.setPower(1);
        } else {
            yoink.setPower(0);
        }


        /* Arm Elbow Code */
        if (gamepad2.dpad_up) {
            multiplier +=.01;
        } else {
            multiplier = .5;
        }
        if (multiplier > 1) {
            multiplier = 1;
        }
        elbow.setPower(gamepad2.right_stick_y * multiplier);

        /* Rotate Intake Code */
        rotate.setPower(gamepad2.left_stick_y);

    }
    /**
     * STOP
     */
    @Override
    public void stop() {
    }

}
