//Imports:
package org.firstinspires.ftc.teamcode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.FPS.Drivetrain;

/**
 * This program is Checkmate Robotics' Autonomous Program Template.
 */

@Autonomous(name="BLUE_Foundation", group="State")
//@Disabled
public class BLUE_Foundation extends LinearOpMode {

    /*
     *  Declare OpMode Members: */
    Drivetrain robot = new Drivetrain();

    private ElapsedTime runtime = new ElapsedTime();


    @Override
    public void runOpMode() {
        robot.map(hardwareMap);

        waitForStart(); /** START THE PROGRAM */
        runtime.reset();


        robot.leftHook.setPosition(.4);
        robot.rightHook.setPosition(.6); // up
        sleep(1200);

        robot.reverse(5000, telemetry);
        sleep(300);

        robot.strafeRight(9500, telemetry);//, telemetry
        sleep(300);

        robot.reverse(4700);
        sleep(300);

        robot.leftHook.setPosition(1);
        robot.rightHook.setPosition(0); //grab
        sleep(1200);

        robot.forward(5100); // pull foundation
        sleep(300);

        robot.rotate(90, telemetry);
        sleep(300);

        robot.reverse(2000);
        sleep(1000);

        robot.leftHook.setPosition(.4);
        robot.rightHook.setPosition(.6); // let go
        sleep(1200);



        robot.forward(11000, telemetry);
        sleep(300);
        robot.strafeLeft(1000, telemetry);
//        sleep(3000);

    }

}
