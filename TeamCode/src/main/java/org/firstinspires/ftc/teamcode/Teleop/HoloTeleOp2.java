
/**
 * Team 12961 Checkmate Robotics: Holonomic Teleop Opmode
 * Version: BETA
 */

package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Holonomic 2", group="Checkmate")

/*
Don't uncomment! This registers the OpMode:
@Disabled
 */
@Disabled

public class HoloTeleOp2 extends OpMode
{
    // Call Hardware:
    private DcMotor leftFront = null;
    private DcMotor rightFront = null;
    private DcMotor leftBack = null;
    private DcMotor rightBack = null;
    private Servo leftServo = null;
    private Servo rightServo = null;
    private Servo hug = null;
    private Servo clap = null;

    double armMid = 0;
    double hugMid = 0;
    final double armSpeed = 0.005 ;
    boolean clapper = true;
    public static final double midServo =  0.5 ;

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
        /*
        Servo Initialization: */
        rightServo = hardwareMap.get(Servo.class, "right");
        leftServo = hardwareMap.get(Servo.class, "left");
        hug = hardwareMap.get(Servo.class, "hug");
        clap = hardwareMap.get(Servo.class, "clap");
        double leftPos = 0;
        double rightPos = 1;

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


        // Show Telemetry On Driver Phone:
        telemetry.addData("Status", "Run Time: " + getRuntime());

        /*
        Servo Controls: */
        // Use gamepad left & right Bumpers to open and close the claw
        if (gamepad2.b)
            armMid += armSpeed;

        else if (gamepad2.x)
            armMid -= armSpeed;

        if (gamepad2.a)
            hugMid += (armSpeed*(.7));

        else if (gamepad2.y)
            hugMid -= (armSpeed*(.7));

        // Move both servos to new position.  Assume servos are mirror image of each other.
        armMid = Range.clip(armMid, -0.5, 0.5);
        rightServo.setPosition(midServo + armMid);
        leftServo.setPosition(midServo - armMid);
        hug.setPosition(hugMid);

        if (gamepad2.right_bumper) {
            if (clapper) {
                clap.setPosition(0.45);
                clapper = false;
            } else {
                clap.setPosition(0.55);
                clapper = true;
            }
        } else {
            clap.setPosition(0.5);
        }


        /*
        Drive code: */

        // Left stick controls direction
        // Right stick X controls rotation

        float leftY = -gamepad1.left_stick_y;
        float leftX = gamepad1.left_stick_x;
        float rightX = -gamepad1.right_stick_x;

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
        rightFront.setPower(FrontRight*(.7));
        leftFront.setPower(FrontLeft*(.7));
        rightBack.setPower(BackRight*(.7));
        leftBack.setPower(BackLeft*(7));


    }

    /**
     * STOP
     */
    @Override
    public void stop() {
    }

}
