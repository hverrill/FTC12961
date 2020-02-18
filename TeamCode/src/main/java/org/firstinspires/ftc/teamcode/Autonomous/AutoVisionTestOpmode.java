//Imports:
package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.FPS.Drivetrain;
import org.firstinspires.ftc.teamcode.FPS.Measurement;
import org.firstinspires.ftc.teamcode.FPS.Vision;

import java.util.ArrayList;


/**
 * This program is Checkmate Robotics' Autonomous Program Template.
 */

@Autonomous(name="Vision Test Opmode", group="Sky")
//@Disabled
public class AutoVisionTestOpmode extends LinearOpMode {

    /*
     *  Declare OpMode Members: */
    //Ex: private DcMotor exampleMotor = null;
    public TouchSensor blockToggle;
    private ArrayList<Double> coords = new ArrayList<Double>();
    private VuforiaLocalizer vuforia = null;
    private double dist;
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

        setFourbarPos(.8);
        robot.strafeLeft(5000);
        sleep(200);
        robot.forward(1000);

        portal.update(portal.stone);

        coords.add(robot.odometry.getX());
        coords.add(robot.odometry.getY());

        while (!portal.isTargetVisible(portal.stone) && !isStopRequested()) { // Run the vuforia detection (change if you need to detect later)
            portal.update(portal.stone);
            robot.setPowerAll(.12);
        }
        while ((Math.abs(portal.xTranslation) < margin) && !isStopRequested()) {
            portal.update(portal.stone);
            if (portal.xTranslation < -margin) {
                robot.setPowerAll(.12);
            }
            if (portal.xTranslation > margin) {
                robot.setPowerAll(-.12);
            }
            telemetry.addData("Angle =", robot.sensorSuite.getAngle());
        }
        stopAfter(0);
        coords.add(robot.odometry.getX());
        coords.add(robot.odometry.getY());
        dist = coords.get(2)-coords.get(0);
//        robot.reverse(1000);
        robot.rotate(55);
        succ(runtime);
        robot.rotate(-55);

//        robot.reverse(21000);

        telemetry.addData("Path", "Complete");
        telemetry.addData("dist", dist);
        telemetry.addData("X", coords.get(0));
        telemetry.addData("Y", coords.get(1));
        telemetry.update();
        sleep(9999999);
    }
//gjyhgl



    public void setFourbarPos(double pos) {
        robot.fourbarRight.setPosition(pos);
        robot.fourbarLeft.setPosition(1 - pos);
    }

    public void stopAfter(long millis) {
        sleep(millis);
        robot.leftFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.rightFront.setPower(0);
        robot.rightBack.setPower(0);
        robot.intakeLeft.setPower(0);
        robot.intakeRight.setPower(0);
    }

    public void turnClockwise(double power, long millis) {
        robot.leftFront.setPower(-power);
        robot.leftBack.setPower(-power);
        robot.rightFront.setPower(power);
        robot.rightBack.setPower(power);
        stopAfter(millis);
    }

    public void turnAntiClockwise(double power, long millis) {
        robot.leftFront.setPower(power);
        robot.leftBack.setPower(power);
        robot.rightFront.setPower(-power);
        robot.rightBack.setPower(-power);
        stopAfter(millis);
    }

    public void forward(double power, long millis) {
        robot.leftFront.setPower(power);
        robot.leftBack.setPower(power);
        robot.rightFront.setPower(power);
        robot.rightBack.setPower(power);
        stopAfter(millis);
    }

    public void reverse(double power, long millis) {
        robot.leftFront.setPower(-power);
        robot.leftBack.setPower(-power);
        robot.rightFront.setPower(-power);
        robot.rightBack.setPower(-power);
        stopAfter(millis);
    }

    public void strafe(double power, long millis) {
        robot.leftFront.setPower(-power);
        robot.leftBack.setPower(power);
        robot.rightFront.setPower(power);
        robot.rightBack.setPower(-power);
        stopAfter(millis);
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


        stopAfter(0);
    }
}