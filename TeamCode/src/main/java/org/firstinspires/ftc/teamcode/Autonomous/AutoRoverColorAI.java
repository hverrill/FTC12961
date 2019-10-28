//Imports:
package org.firstinspires.ftc.teamcode.Autonomous;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import java.util.List;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;


/**
 * This program is Checkmate Robotics' Autonomous Program Template.
 * The OpMode currently being edited is: Blue 1
 */

@Autonomous(name="Auto 2018/11/16", group="Rover")
@Disabled
public class AutoRoverColorAI extends LinearOpMode {

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
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    private static final String VUFORIA_KEY = "AX6ynMb/////AAABmcK8xLR/cEMTk3Qy46GU0Po3YfoGNrpPXjGYiHOdnYKR8Lq1ccLyKdxvLsJC0AeowUIB8E8l6Gi7a2jq348Toy/p2FjR9CZ5N7J0LZaL3omzgZ3fur1L371la0RrSQeYGr7tHzkM1SelARBr4P2sl0cjZomOnRhwNvjyEzwf1RVBPnbmdjbXm7m0eRCpkYLgE2DqMYgdEJY1fTQ+W5KgVOKtpe88fWx1u764G/yCfJktjI4zUkrwmRUDtO26FUHnr0Rb2lX8O+V619d0WdHMXPbILAXOFMxVEgn+mK6ASc+/L2Qxkb9C/R+3s4ckC5pDity18Qv+Z0l9kQWeWLHbXJPYVXM863wURNZWTWn1JMRA";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    @Override
    public void runOpMode() {

//        initVuforia();
//
//        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
//            initTfod();
//        } else {
//            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
//        }




        /**
         * Hardware Variables: */
        //Ex: exampleMotor  = hardwareMap.get(DcMotor.class, "motor");
        left = hardwareMap.get(DcMotor.class,"left");
        right = hardwareMap.get(DcMotor.class,"right");
        winch = hardwareMap.get(DcMotor.class,"winch");
        release = hardwareMap.get(Servo.class,"release");
        x = hardwareMap.get(Servo.class, "x");
        y = hardwareMap.get(Servo.class,"y");
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

        /**
         * Call Telemetry Update */
        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        /*
        Wait for driver to hit the start button on the controller:
         */ waitForStart();

        /**
         * Instructions: */

        int position = 0; //Variable to indicate position


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

        /* Retract Arm */
        winch.setPower(-.7);
        sleep(2000);
        winch.setPower(0);
        sleep(300);

        /* Twist Free of Lander */
        left.setPower(1);
        sleep(500);
        left.setPower(0);
        right.setPower(0);


        /* Use Camera to detect minerals */
        runtime.reset();
        if (opModeIsActive()) {
            /** Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }

            while (opModeIsActive() && (runtime.seconds() < 2)) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        if (updatedRecognitions.size() == 3) {
                            int goldMineralX = -1;
                            int silverMineral1X = -1;
                            int silverMineral2X = -1;
                            for (Recognition recognition : updatedRecognitions) {
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    goldMineralX = (int) recognition.getLeft();
                                } else if (silverMineral1X == -1) {
                                    silverMineral1X = (int) recognition.getLeft();
                                } else {
                                    silverMineral2X = (int) recognition.getLeft();
                                }
                            }
                            if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                                if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                                    telemetry.addData("Gold Mineral Position", "Left");
                                    position = 0;
                                } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                    telemetry.addData("Gold Mineral Position", "Right");
                                    position = 2;
                                } else {
                                    telemetry.addData("Gold Mineral Position", "Center");
                                    position = 1;
                                }
                            }
                        }
                        telemetry.update();
                    }
                }
            }
        }

        if (tfod != null) {
            tfod.shutdown();
        }
        sleep(1500);

        /* Move Away From Lander */
        sleep(500);
        left.setPower(-.35);
        right.setPower(.40);
        sleep(1800);
        left.setPower(0);
        right.setPower(0);
        sleep(1000);

        /* Move To knock over gold mineral */
        if (position == 0){
            left.setPower(-.40);
            right.setPower(.40);
            sleep(900);
            left.setPower(0);
            right.setPower(0);
        } else if (position == 1){
            left.setPower(.40);
            right.setPower(.40);
            sleep(900);
            left.setPower(0);
            right.setPower(0);
        } else if (position == 2){
            left.setPower(-.40);
            right.setPower(-.40);
            sleep(900);
            left.setPower(0);
            right.setPower(0);
        } else {
            left.setPower(.40);
            right.setPower(-.40);
            sleep(900);
            left.setPower(0);
            right.setPower(0);
        }


        /**
         * Stop OpMode */

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
    }
}