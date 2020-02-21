//Imports:
package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.FPS.Drivetrain;
import org.firstinspires.ftc.teamcode.FPS.Measurement;

import org.firstinspires.ftc.teamcode.FPS.Vision;


/**
 * This program is Checkmate Robotics' Autonomous Program Template.
 */

@Autonomous(name="Odometery test", group="Sky")
//@Disabled
public class IMUTest extends LinearOpMode {

    /*
     *  Declare OpMode Members: */
    Drivetrain robot = new Drivetrain();
//    private VuforiaLocalizer vuforia = null;

    public BNO055IMU revIMU;
    private ElapsedTime runtime = new ElapsedTime();
//    Vision portal = new Vision();
//    Measurement sensorSuite = null;

    double margin = .5;

    @Override
    public void runOpMode() {
        robot.map(hardwareMap);
        //robot = new Movement(this);
        //sensorSuite = new Measurement(revIMU, hardwareMap);
//        portal.createVuforia(VuforiaLocalizer.CameraDirection.BACK, hardwareMap, telemetry);

        waitForStart(); /** START THE PROGRAM */

        robot.rotate(90, telemetry);
        sleep(5000);
        robot.rotate(-90, telemetry);
        sleep(99999999);
//        while (!isStopRequested()) {
//            telemetry.addData("Angle 1 =", sensorSuite.getAngle().angle1);
//            telemetry.addData("Angle 2 =", sensorSuite.getAngle().angle2);
//            telemetry.addData("Angle 3 =", sensorSuite.getAngle().angle3);
//            telemetry.update();
//        }

    }


    public void rotate(float degrees){

        boolean turning = true;

        float targetAngle = robot.sensorSuite.getAngle().angle1 + degrees;
        double ratio;
        double powerPolarity = degrees/Math.abs(degrees);
        double powerMultiplier;

        while(turning && !isStopRequested()){

            ratio = robot.sensorSuite.getAngle().angle1/targetAngle;

            powerMultiplier = 1-ratio;

            if(Math.abs(powerMultiplier) < .15){
                powerMultiplier = .2;
            }
            if(Math.abs(powerMultiplier) > .85){
                powerMultiplier = .85;
            }

            robot.leftBack.setPower(-.2 * powerMultiplier * powerPolarity);
            robot.leftFront.setPower(-.2 * powerMultiplier * powerPolarity);
            robot.rightBack.setPower(.2 * powerMultiplier * powerPolarity);
            robot.rightFront.setPower(.2 * powerMultiplier * powerPolarity);

            if(ratio > .95) turning = false;

        }
        robot.leftBack.setPower(0);
        robot.leftFront.setPower(0);
        robot.rightBack.setPower(0);
        robot.rightFront.setPower(0);
    }
    public void setFourbarPos(double pos){
        robot.fourbarRight.setPosition(pos);
        robot.fourbarLeft.setPosition(1-pos);
    }
    public void grabbersDown(){
        robot.leftHook.setPosition(.27);
        robot.rightHook.setPosition(.73);
    }
    public void stopAfter(long millis){
        sleep(millis);
        robot.leftFront.setPower(0);
        robot.leftBack.setPower(0);
        robot.rightFront.setPower(0);
        robot.rightBack.setPower(0);
        robot.intakeLeft.setPower(0);
        robot.intakeRight.setPower(0);
    }
    public void turnClockwise(double power){// , long millis
        robot.leftFront.setPower(-power);
        robot.leftBack.setPower(-power);
        robot.rightFront.setPower(power);
        robot.rightBack.setPower(power);
        //stopAfter(millis);
    }
    public void turnAntiClockwise(double power, long millis){
        robot.leftFront.setPower(power);
        robot.leftBack.setPower(power);
        robot.rightFront.setPower(-power);
        robot.rightBack.setPower(-power);
        stopAfter(millis);
    }
    public void forward(double power){ //, long millis
        robot.leftFront.setPower(power);
        robot.leftBack.setPower(power);
        robot.rightFront.setPower(power);
        robot.rightBack.setPower(power);
        //stopAfter(millis);
    }
    public void reverse(double power){ //, long millis
        robot.leftFront.setPower(-power);
        robot.leftBack.setPower(-power);
        robot.rightFront.setPower(-power);
        robot.rightBack.setPower(-power);
        //stopAfter(millis);
    }
    public void strafe(double power){//, long millis
        robot.leftFront.setPower(-power);
        robot.leftBack.setPower(power);
        robot.rightFront.setPower(power);
        robot.rightBack.setPower(-power);
        //stopAfter(millis);
    }
    public void succ(ElapsedTime time){
        double starttime = time.milliseconds();
        robot.intakeRight.setPower(-.6);
        robot.intakeLeft.setPower(.6);
        robot.leftFront.setPower(.15);
        robot.leftBack.setPower(.15);
        robot.rightFront.setPower(.15);
        robot.rightBack.setPower(.12);
        // loop until we detect a block or x seconds have expired

        while(robot.blockToggle.getValue() < 1) {

        }

        stopAfter(0);

    }
}
