//Imports:
package Depricated;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.FPS.Drivetrain;

import Depricated.AutoMovement;


/**
 * This program is Checkmate Robotics' Autonomous Program Template.
 */

@Autonomous(name="BRIDGE_BLUE", group="Tourny")
@Disabled
public class AutoFifth extends LinearOpMode {

    /*
     *  Declare OpMode Members: */
    Drivetrain robot = new Drivetrain();

    private ElapsedTime runtime = new ElapsedTime();

    //private AutoMovement robot = new AutoMovement();

    @Override
    public void runOpMode() {

        /**
         * Hardware Variables: */
        robot.map(hardwareMap);

        /**
         * Telemetry */
        telemetry.addData("Status", "Ready to run");
        telemetry.update();
        /*
        Wait for driver to hit the start button on the controller:
         */
        waitForStart();
//        // go forward
//        leftFront.setPower(-0.20);
//        leftBack.setPower(-0.20);
//        rightFront.setPower(-0.20);
//        rightBack.setPower(-0.20);
//        sleep(1100);
//        leftFront.setPower(0);
//        leftBack.setPower(0);
//        rightFront.setPower(0);
//        rightBack.setPower(0);
//        sleep(100);
        //strafe right
        robot.leftFront.setPower(0.5);
        robot.leftBack.setPower(-0.5);
        robot.rightFront.setPower(-0.5);
        robot.rightBack.setPower(0.5);
        sleep(300);
        robot.leftFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.rightFront.setPower(0);
        robot.rightBack.setPower(0);
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