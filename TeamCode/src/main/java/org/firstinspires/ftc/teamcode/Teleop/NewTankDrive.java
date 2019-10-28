
/**
 * Team 12961 Checkmate Robotics: Alex Test Drive
 * Version: BETA
 */

package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TeleOp 10/23/18", group="Checkmate")

/*
Don't uncomment! This registers the OpMode:
@Disabled
 */


public class NewTankDrive extends OpMode {

    private DcMotor left = null;
    private DcMotor right = null;
    private DcMotor winch = null;
    private Servo release = null;
    private Servo claim = null;
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
        claim = hardwareMap.get(Servo.class, "claim");

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

        if (gamepad1.right_trigger > 0) {
            double rightpow = -gamepad1.left_stick_y * 0.7;
            double leftpow = -gamepad1.right_stick_y * 0.7;
            left.setPower(leftpow);
            right.setPower(rightpow);
        } else {
            double rightpow = gamepad1.left_stick_y * 0.7;
            double leftpow = gamepad1.right_stick_y * 0.7;
            left.setPower(leftpow);
            right.setPower(rightpow);
        }

        if (gamepad1.y){
            winch.setPower(-0.5);
        } else if (gamepad1.a){
            winch.setPower(0.5);
        } else {
            winch.setPower(0);
        }

        if (gamepad1.x){
            release.setPosition(.1);
        } else {
            release.setPosition(1);
        }

//        if (gamepad1.b){
//            claim.setPosition(.5);
//        } else {
//            claim.setPosition(0);
//        }





    }
    /**
     * STOP
     */
    @Override
    public void stop() {
    }

}
