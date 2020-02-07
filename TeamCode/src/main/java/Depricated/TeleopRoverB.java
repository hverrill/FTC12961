
package Depricated;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Team 12961 Checkmate Robotics: Teleop Opmode
 * In Use: Y
 */

@TeleOp(name="The Bestest Program B", group="Rover")

/*
Don't uncomment! This registers the OpMode:
@Disabled
 */
@Disabled

public class TeleopRoverB extends OpMode {

    private DcMotor left = null;
    private DcMotor right = null;
    private DcMotor winch = null;
    private DcMotor elbow = null;
    private Servo release = null;
    private Servo claim = null;
    private double pos = 0;


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
        elbow = hardwareMap.get(DcMotor.class, "elbow");

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

        /* Claim Servo */
        // Rotate the claim servo arm to release the marker.
        if (gamepad1.b){
            claim.setPosition(.1);
        } else {
            claim.setPosition(1);
        }

        /* Arm Elbow Code */
        // Rotate the manipulation arm.
        if (gamepad1.left_trigger > 0){
            elbow.setPower(-gamepad1.left_trigger);
        } else if (gamepad1.right_trigger > 0) {
            elbow.setPower(gamepad1.right_trigger);
        } else {
            elbow.setPower(0);
        }

    }
    /**
     * STOP
     */
    @Override
    public void stop() {
    }

}
