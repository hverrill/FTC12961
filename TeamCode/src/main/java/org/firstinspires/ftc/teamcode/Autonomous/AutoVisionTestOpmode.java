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
import org.firstinspires.ftc.teamcode.FPS.Movement;
import org.firstinspires.ftc.teamcode.FPS.Vision;


/**
 * This program is Checkmate Robotics' Autonomous Program Template.
 */

@Autonomous(name="Vision Test Opmode", group="Sky")
//@Disabled
public class AutoVisionTestOpmode extends LinearOpMode {

    /*
     *  Declare OpMode Members: */
    //Ex: private DcMotor exampleMotor = null;
    public DcMotor leftFront, leftBack, rightFront, rightBack, intakeLeft, intakeRight = null; // winch,
    private Servo leftHook, rightHook, grab, turn, leftGrab, rightGrab;
    float hsvValues[] = {0F, 0F, 0F};
    private ColorSensor colorLeft, colorRight;
    public TouchSensor blockToggle;
    boolean leftSeesYellow = false;
    boolean rightSeesYellow = false;
    boolean nextToWall = false;
    private VuforiaLocalizer vuforia = null;
    String side = "";
    public BNO055IMU revIMU;
    int visionTick = 0;
    private ElapsedTime runtime = new ElapsedTime();
    Vision portal = new Vision();
    Drivetrain robot = new Drivetrain();
    Measurement sensorSuite = null;

    double margin = .5;


    @Override
    public void runOpMode() {

        /**
         * Hardware Variables: */
        //Ex: exampleMotor  = hardwareMap.get(DcMotor.class, "motor");
        robot.map(hardwareMap);

        sensorSuite = new Measurement(revIMU, hardwareMap);
        portal.createVuforia(VuforiaLocalizer.CameraDirection.BACK, hardwareMap, telemetry);


        waitForStart(); /** START THE PROGRAM */
        runtime.reset();
        //START
        setFourbarPos(.8);
        strafe(.3, 900);
        sleep(200);
        forward(.22, 400);

        runtime.seconds();
        portal.update(portal.stone);

//        robot.leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        robot.leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        robot.rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        robot.rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        while (!portal.isTargetVisible(portal.stone) && !isStopRequested()) { // Run the vuforia detection (change if you need to detect later)
            portal.update(portal.stone);
            robot.leftFront.setPower(.02);
            robot.leftBack.setPower(.02);
            robot.rightFront.setPower(.02);
            robot.rightBack.setPower(.02);
            //visionTick = (robot.leftBack.getCurrentPosition() + robot.leftFront.getCurrentPosition() + robot.rightBack.getCurrentPosition() + robot.rightFront.getCurrentPosition()) / 4;
        }
        while ((Math.abs(portal.xTranslation) < margin) && !isStopRequested()) {
            portal.update(portal.stone);
            if (portal.xTranslation < -margin) {
                robot.leftFront.setPower(.02);
                robot.leftBack.setPower(.02);
                robot.rightFront.setPower(.02);
                robot.rightBack.setPower(.02);
            }
            if (portal.xTranslation > margin) {
                robot.leftFront.setPower(-.02);
                robot.leftBack.setPower(-.02);
                robot.rightFront.setPower(-.02);
                robot.rightBack.setPower(-.02);
            }
            telemetry.addData("Angle =", sensorSuite.getAngle());
        }
        stopAfter(0);
        //reverse to allign, then strafe to side to collect skystone
        reverse(.2, 350);
        rotate(55);
        succ(runtime);
        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(9999999);
    }


    public void rotate(float degrees) {

        boolean turning = true;

        float targetAngle = sensorSuite.getAngle().angle1 + degrees;
        double ratio;
        double powerPolarity = degrees / Math.abs(degrees);
        double powerMultiplier;

        while (turning && !isStopRequested()) {

            ratio = sensorSuite.getAngle().angle1 / targetAngle;

            powerMultiplier = 1 - ratio;

            if (Math.abs(powerMultiplier) < .15) {
                powerMultiplier = .2;
            }
            if (Math.abs(powerMultiplier) > .85) {
                powerMultiplier = .85;
            }

            robot.leftBack.setPower(-.2 * powerMultiplier * powerPolarity);
            robot.leftFront.setPower(-.2 * powerMultiplier * powerPolarity);
            robot.rightBack.setPower(.2 * powerMultiplier * powerPolarity);
            robot.rightFront.setPower(.2 * powerMultiplier * powerPolarity);

            if (ratio > .95) turning = false;

        }
        robot.leftBack.setPower(0);
        robot.leftFront.setPower(0);
        robot.rightBack.setPower(0);
        robot.rightFront.setPower(0);
    }

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
        while (!robot.blockToggle.isPressed()) {
            finishtime = runtime.milliseconds();
            if(finishtime - starttime >= 4000){
                break;
            }
        }
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