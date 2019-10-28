//Imports:
package org.firstinspires.ftc.teamcode.Autonomous;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * This program is Checkmate Robotics' Autonomous Program Template.
 * The OpMode currently being edited is: Blue 1
 */

@Autonomous(name="Auto 2018/11/2", group="Rover")
@Disabled
public class AutoRoverColor extends LinearOpMode {

    /*
     *  Declare OpMode Members: */
    //Ex: private DcMotor exampleMotor = null;
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor left = null;
    private DcMotor right = null;
    private DcMotor winch = null;
    private Servo release = null;
    private Servo x = null;
    private Servo y = null;
    float hsvValues[] = {0F, 0F, 0F};
    private ColorSensor sensorColor1;
    private ColorSensor sensorColor2;
    @Override
    public void runOpMode() {

        /**
         * Hardware Variables: */
        //Ex: exampleMotor  = hardwareMap.get(DcMotor.class, "motor");
        left = hardwareMap.get(DcMotor.class,"left");
        right = hardwareMap.get(DcMotor.class,"right");
        winch = hardwareMap.get(DcMotor.class,"winch");
        release = hardwareMap.get(Servo.class,"release");
        x = hardwareMap.get(Servo.class, "x");
        y = hardwareMap.get(Servo.class,"y");
        sensorColor1 = hardwareMap.get(ColorSensor.class, "color1");
        sensorColor2 = hardwareMap.get(ColorSensor.class, "color2");
        telemetry.addData("Status", "Ready to run");
        telemetry.update();
        float hsvValues[] = {0F, 0F, 0F};
        final float values[] = hsvValues;
        final double SCALE_FACTOR = 255;
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
        /**
         * Telemetry */
        telemetry.addData("Status", "Ready to run");
        telemetry.update();
        /*
        Wait for driver to hit the start button on the controller:
         */ waitForStart();


        /**
         * Instructions: */
        int position = 0;
        /* Lower Robot from Lander */
        release.setPosition(0);
        sleep(3000);
        release.setPosition(1);
        sleep(700);
        /* Twist Free of Lander */
        left.setPower(-0.28);
        right.setPower(-0.1);
        sleep(500);
        left.setPower(0);
        right.setPower(0);
        sleep(500);
        left.setPower(-.28);
        right.setPower(.40);
        sleep(900);
        left.setPower(0);
        right.setPower(0);
        sleep(1000);

        // 3: Test Color:
        y.setPosition(0);
        sleep(1300);

        x.setPosition(0.74);
        sleep(400);
        if (((sensorColor1.red() + sensorColor1.green())/2.3) > sensorColor1.blue()){
            position = 1;
        }
        sleep(500);
        runtime.reset();
//        while (opModeIsActive() && (runtime.seconds() < 5)) {
//            Color.RGBToHSV((int) (sensorColor1.red() * SCALE_FACTOR),
//                    (int) (sensorColor1.green() * SCALE_FACTOR),
//                    (int) (sensorColor1.blue() * SCALE_FACTOR),
//                    hsvValues);
//            telemetry.addData("Alpha", sensorColor1.alpha());
//            telemetry.addData("Red  ", sensorColor1.red());
//            telemetry.addData("Green", sensorColor1.green());
//            telemetry.addData("Blue ", sensorColor1.blue());
//            telemetry.addData("Average ", (sensorColor1.red() + sensorColor1.green())/2);
//            telemetry.addData("Hue", hsvValues[0]);
//            telemetry.update();
//        }
//        sleep(1500);
        x.setPosition(.93);
        sleep(400);
        if (((sensorColor2.red() + sensorColor2.green())/2.3) > sensorColor2.blue()){
            position = 2;
        }

        if (position == 0){
            x.setPosition(0.76);
            sleep(100);
            y.setPosition(1);
            sleep(300);
            left.setPower(0.4);
            right.setPower(0.4);
            sleep(600);
            left.setPower(0);
            right.setPower(0);
            left.setPower(-0.4);
            right.setPower(0.4);
            sleep(1600);
            left.setPower(0);
            right.setPower(0);


        } else if (position == 1){
            x.setPosition(0.6);
            left.setPower(-0.4);
            right.setPower(-0.4);
            sleep(400);
            left.setPower(0);
            right.setPower(0);
        } else if (position == 2){
            x.setPosition(1);
            left.setPower(0.4);
            right.setPower(0.4);
            sleep(400);
            left.setPower(0);
            right.setPower(0);


        }
        x.setPosition(0.76);
        y.setPosition(1);

        sleep(1000);

        /* Retract Arm */
        winch.setPower(-.7);
        sleep(2000);
        winch.setPower(0);



        sleep(300);


        /**
         * Stop OpMode */

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
    }
}