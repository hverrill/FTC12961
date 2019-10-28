
/**
 * Team 12961 Checkmate Robotics: Holonomic Teleop Opmode
 * Version: BETA
 */

package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="Holonomic", group="Checkmate")

/*
Don't uncomment! This registers the OpMode:
@Disabled
 */
@Disabled

public class HoloTeleOp extends OpMode
{
    // Call Hardware:
    private DcMotor leftFront = null;
    private DcMotor rightFront = null;
    private DcMotor leftBack = null;
    private DcMotor rightBack = null;

    // Call Variables:
    private ElapsedTime runtime = new ElapsedTime();
    /**
     * One time INIT code:
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        /*
        Motor Initialization: */
        leftFront  = hardwareMap.get(DcMotor.class, "LF");
        rightFront = hardwareMap.get(DcMotor.class, "RF");
        leftBack = hardwareMap.get(DcMotor.class, "LB");
        rightBack = hardwareMap.get(DcMotor.class,"RB");
        // Motor Direction:
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.REVERSE);

    }

    /**
     * Looping before PLAY after INIT code:
     */
    @Override
    public void init_loop() {
    }

    //Reset System Time:
    @Override
    public void start() {}

    /**
     * Looping PLAY code:
     */
    @Override
    public void loop() {


        // Show Telemetry On Driver Phone:
        telemetry.addData("Status", "Run Time: " + getRuntime());


        /*
        Drive code: */

        // Left stick controls direction
        // Right stick X controls rotation

        float leftY = -gamepad1.left_stick_y;
        float leftX = gamepad1.left_stick_x;
        float rightX = gamepad1.right_stick_x;

        // Holonomic Formulas:

        float FrontLeft = -leftY - leftX - rightX;
        float FrontRight = leftY - leftX - rightX;
        float BackRight = leftY + leftX - rightX;
        float BackLeft = -leftY + leftX - rightX;

        // clip the right/left values so that the values never exceed +/- 1
        FrontRight = Range.clip(FrontRight, -1, 1);
        FrontLeft = Range.clip(FrontLeft, -1, 1);
        BackLeft = Range.clip(BackLeft, -1, 1);
        BackRight = Range.clip(BackRight, -1, 1);

        // write the values to the motors
        rightFront.setPower(FrontRight);
        leftFront.setPower(FrontLeft);
        rightBack.setPower(BackRight);
        leftBack.setPower(BackLeft);


    }

    /**
     * STOP
     */
    @Override
    public void stop() {
    }

}
