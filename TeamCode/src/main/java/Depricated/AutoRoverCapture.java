/**
 * Imports: */
package Depricated;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * This program is Checkmate Robotics' Autonomous Program Template.
 * The OpMode currently being edited is: Blue 1
 */

@Autonomous(name="Auto 2018/10/16 (No Color)", group="Rover")
@Disabled
public class AutoRoverCapture extends LinearOpMode {

    /*
     *  Declare OpMode Members: */
    //Ex: private DcMotor exampleMotor = null;
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor left = null;
    private DcMotor right = null;
    private DcMotor winch = null;
    private Servo release = null;
    private Servo claim = null;
    @Override
    public void runOpMode() {

        /**
         * Hardware Variables: */
        //Ex: exampleMotor  = hardwareMap.get(DcMotor.class, "motor");
        left = hardwareMap.get(DcMotor.class,"left");
        right = hardwareMap.get(DcMotor.class,"right");
        winch = hardwareMap.get(DcMotor.class,"winch");
        release = hardwareMap.get(Servo.class,"release");
        claim = hardwareMap.get(Servo.class, "claim");
        telemetry.addData("Status", "Ready to run");
        telemetry.update();


        /**
         * Telemetry */
        telemetry.addData("Status", "Ready to run");
        telemetry.update();
        /*
        Wait for driver to hit the start button on the controller:
         */ waitForStart();


        /**
         * Instructions: */

        /* Lower Robot from Lander */
        release.setPosition(0);
        sleep(3000);
        release.setPosition(1);

        /* Twist Free of Lander */
        left.setPower(-0.3);
        right.setPower(0);
        sleep(700);
        left.setPower(0);
        right.setPower(0);
        sleep(700);
        left.setPower(-.5);
        right.setPower(.5);
        sleep(1000);
        left.setPower(0);
        right.setPower(0);
        sleep(1000);
        /* Retract Arm */
        winch.setPower(.7);
        sleep(2000);
        winch.setPower(0);


        /**
         * Stop OpMode */

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
    }
}