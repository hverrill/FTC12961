//Imports:
package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.FPS.Drivetrain;
import org.firstinspires.ftc.teamcode.FPS.Vision;

import java.util.ArrayList;


/**
 * This program is Checkmate Robotics' Autonomous Program Template.
 */

@Autonomous(name="Combined Auto", group="Beta")
//@Disabled
public class CombinedAuto extends LinearOpMode {

    /*
     *  Declare OpMode Members: */
    //Ex: private DcMotor exampleMotor = null;
    public TouchSensor blockToggle;
    private ArrayList<Integer> coords = new ArrayList<Integer>();
    private VuforiaLocalizer vuforia = null;
    private int dist;
    public BNO055IMU revIMU;
    private ElapsedTime runtime = new ElapsedTime();
    Vision portal = new Vision();
    Drivetrain robot = new Drivetrain();


    double margin = .5;


    @Override
    public void runOpMode() {

        /**
         * Hardware Variables: */
        //Ex: exampleMotor  = hardwareMap.get(DcMotor.class, "motor");
        robot.map(hardwareMap);


        portal.createVuforia(VuforiaLocalizer.CameraDirection.BACK, hardwareMap, telemetry);


        waitForStart(); /** START THE PROGRAM */
        runtime.reset();
        //START
        robot.leftHook.setPosition(.4);
        robot.rightHook.setPosition(.6);
        setFourbarPos(.8);
        sleep(1200);
        robot.strafeLeft(5000);
        sleep(200);
//        robot.forward(1000);

        portal.update(portal.stone);

        coords.add((int)robot.odometry.getX());
        coords.add((int)robot.odometry.getY());

        while (!portal.isTargetVisible(portal.stone) && !isStopRequested()) { // Run the vuforia detection (change if you need to detect later)
            portal.update(portal.stone);
            robot.setPowerAll(.1);
        }
        while ((Math.abs(portal.xTranslation) < margin) && !isStopRequested()) {
            portal.update(portal.stone);
            if (portal.xTranslation < -margin) {
                robot.setPowerAll(.1);
            }
            if (portal.xTranslation > margin) {
                robot.setPowerAll(-.1);
            }
            telemetry.addData("Angle =", robot.sensorSuite.getAngle().angle1);
        }
        robot.setPowerAll(0);
        coords.add((int)robot.odometry.getX());
        coords.add((int)robot.odometry.getY());
        dist = coords.get(2)-coords.get(0);
//        robot.reverse(1000);
        robot.rotate(55, telemetry);
        succ(runtime);
        robot.rotate(-55, telemetry);
        robot.reverse(dist);

        //end of old vision opmode

//
//        sleep(1200);
//        robot.reverse(5000); //actually forward
//        sleep(300);
//        robot.strafeRight(9200);
//        sleep(300);
//        robot.reverse(6000);
//        sleep(300);
        robot.rotate(-90, telemetry);
        robot.reverse(4000);
//        robot.leftHook.setPosition(1);
//        robot.rightHook.setPosition(0);
//        sleep(1200);
//
//        robot.forward(5500);
//        sleep(1000);
//        robot.rotate(90, telemetry);
//        sleep(300);
//        robot.reverse(4000);



        sleep(9999999);
    }
//gjyhgl



    public void setFourbarPos(double pos) {
        robot.fourbarRight.setPosition(pos);
        robot.fourbarLeft.setPosition(1 - pos);
    }


    public void succ(ElapsedTime time) {
        double starttime = runtime.milliseconds();
        double finishtime;
        double goal;
        robot.intakeRight.setPower(-.6);
        robot.intakeLeft.setPower(.6);
        robot.leftFront.setPower(.15);
        robot.leftBack.setPower(.15);
        robot.rightFront.setPower(.15);
        robot.rightBack.setPower(.12);
        // loop until we detect a block or x seconds have expired
        finishtime = runtime.milliseconds();
        while(!(finishtime - starttime >= 2000)){
            finishtime = runtime.milliseconds();
        }

//        while (!robot.blockToggle.isPressed()) {
//            finishtime = runtime.milliseconds();
//            if(finishtime - starttime >= 4000){
//                break;
//            }
//        }
        goal = (finishtime - starttime) + runtime.milliseconds();
        while (runtime.milliseconds() < goal) {
            robot.leftFront.setPower(-.15);
            robot.leftBack.setPower(-.15);
            robot.rightFront.setPower(-.15);
            robot.rightBack.setPower(-.12);
        }


        robot.setPowerAll(0);
        robot.intakeLeft.setPower(0);
        robot.intakeRight.setPower(0);
    }
}