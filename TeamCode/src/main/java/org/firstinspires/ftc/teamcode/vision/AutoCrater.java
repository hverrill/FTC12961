package org.firstinspires.ftc.teamcode.vision;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;


/**
 * Team 12961 Checkmate Robotics: Individual Autonomous Opmode
 * In Use: Yes
 */
@Autonomous(name = "Crater Side", group = "Normal")
//@Disabled
public class AutoCrater extends LinearOpMode {

    /** Create Variables: */
    MasterVision vision;
    SampleRandomizedPositions goldPosition;
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor left = null;
    private DcMotor right = null;
    private DcMotor winch = null;
    private CRServo yoink = null;
    private Servo release = null;

    private Servo y = null;

    @Override
    public void runOpMode() throws InterruptedException {

        /** Initialize Hardware and Software: */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.cameraDirection = CameraDirection.BACK;// recommended camera direction
        parameters.vuforiaLicenseKey = "AX6ynMb/////AAABmcK8xLR/cEMTk3Qy46GU0Po3YfoGNrpPXjGYiHOdnYKR8Lq1ccLyKdxvLsJC0AeowUIB8E8l6Gi7a2jq348Toy/p2FjR9CZ5N7J0LZaL3omzgZ3fur1L371la0RrSQeYGr7tHzkM1SelARBr4P2sl0cjZomOnRhwNvjyEzwf1RVBPnbmdjbXm7m0eRCpkYLgE2DqMYgdEJY1fTQ+W5KgVOKtpe88fWx1u764G/yCfJktjI4zUkrwmRUDtO26FUHnr0Rb2lX8O+V619d0WdHMXPbILAXOFMxVEgn+mK6ASc+/L2Qxkb9C/R+3s4ckC5pDity18Qv+Z0l9kQWeWLHbXJPYVXM863wURNZWTWn1JMRA";

        vision = new MasterVision(parameters, hardwareMap, true, MasterVision.TFLiteAlgorithm.INFER_LEFT);
        vision.init();// enables the camera overlay. this will take a couple of seconds

        left = hardwareMap.get(DcMotor.class,"left");
        right = hardwareMap.get(DcMotor.class,"right");
        winch = hardwareMap.get(DcMotor.class,"winch");
        release = hardwareMap.get(Servo.class,"release");
        yoink = hardwareMap.get(CRServo.class, "yoink");


        left.setDirection(DcMotor.Direction.FORWARD);
        right.setDirection(DcMotor.Direction.FORWARD);

        /** Wait for the game to begin: */
        telemetry.addData(">", "Press Play to start opmode");
        telemetry.update();

        waitForStart();
        /** Start Sequence: */
        telemetry.addData(">", "Opmode Running");
        int position = 3; //Variable to indicate position
        telemetry.update();

       /* Lower Robot from Lander */
        release.setPosition(0);
        sleep(3000);
        release.setPosition(1);
        sleep(700);

        /* Twist Free of Lander */
        left.setPower(-.3);
        right.setPower(.4);
        sleep(500);
        left.setPower(0);
        right.setPower(0);

        /* Retract Arm */
        winch.setPower(.7);
        sleep(2000);
        winch.setPower(0);
        sleep(300);

        /* Twist Free of Lander */
        left.setPower(.4);
        right.setPower(-.3);
        sleep(420);
        left.setPower(0);
        right.setPower(0);


        /* Move Away From Lander */
        sleep(100);
        left.setPower(.35);
        right.setPower(.35);
        sleep(650);
        left.setPower(0);
        right.setPower(0);
        sleep(500);

        /* Test Positions */
        left.setPower(-.33);
        right.setPower(0);
        sleep(550);
        left.setPower(0);
        right.setPower(0);
        vision.enable();
        sleep(2000);
        goldPosition = vision.getTfLite().getLastKnownSampleOrder();
        if (opModeIsActive()) {
            telemetry.addData("goldPosition was", goldPosition);// giving feedback
            // RIGHT: 0, CENTER: 1, LEFT: 2
            switch (goldPosition){ // using for things in the autonomous program
                case LEFT:
                    telemetry.addLine("going to the left");
                    position = 2;
                    break;
                case CENTER:
                    telemetry.addLine("going straight");
                    position = 1;
                    break;
                case RIGHT:
                    telemetry.addLine("going to the right");
                    position = 0;
                    break;
                case UNKNOWN:
                    telemetry.addLine("staying put");
                    position = 1;
                    break;
            }
            telemetry.update();
        }
        left.setPower(.33);
        right.setPower(0);
        sleep(550);
        left.setPower(0);
        right.setPower(0);
        vision.disable();


        left.setDirection(DcMotor.Direction.REVERSE);
        right.setDirection(DcMotor.Direction.REVERSE);

        left.setPower(.69);
        right.setPower(-.69);
        sleep(1550);
        left.setPower(0);
        right.setPower(0);



        if (position == 0) {
            right.setPower(-.25);
            left.setPower(0);
            sleep(1000);
            right.setPower(.7);
            left.setPower(.7);
            sleep( 1650);

            right.setPower(-.35);
            left.setPower(-.35);
            sleep( 550);

            right.setPower(-0.6);
            left.setPower(0);
            sleep( 1100);

            right.setPower(1);
            left.setPower(1);
            sleep( 1850);
            right.setPower(0);
            left.setPower(0);
            sleep( 200);

            right.setPower(-0.4);
            left.setPower(0.1);
            sleep( 820);
            right.setPower(1);
            left.setPower(1);
            sleep( 1000);

            yoink.setPower(-1);
            sleep(300);
            right.setPower(.7);
            left.setPower(.7);
            sleep(1500);
            right.setPower(0);
            left.setPower(0);
            yoink.setPower(0);
            sleep(600);


            right.setPower(-1);
            left.setPower(-1);
            sleep( 5000);
        }

        if (position == 1) {

            right.setPower(.7);
            left.setPower(.7);
            sleep( 1000);

            right.setPower(-.5);
            left.setPower(-.5);
            sleep( 400);

            right.setPower(-0.5);
            left.setPower(0);
            sleep( 1800);

            right.setPower(.7);
            left.setPower(.7);
            sleep( 2900);

            right.setPower(0);
            left.setPower(0.5);
            sleep( 1250);

            right.setPower(1);
            left.setPower(1);
            sleep( 1000);

            yoink.setPower(-1);
            sleep(300);
            right.setPower(.7);
            left.setPower(.7);
            sleep(800);
            yoink.setPower(0);
            sleep(600);

            right.setPower(0);
            left.setPower(0);
            sleep(300);

            right.setPower(-1);
            left.setPower(-0.95);
            sleep( 5000);


        }
        if (position == 2) {
            right.setPower(0);
            left.setPower(-.5);
            sleep(1000);
            right.setPower(.7);
            left.setPower(.7);
            sleep( 1700);

            right.setPower(-.49);
            left.setPower(-.49);
            sleep( 1100);

            right.setPower(-.5);
            left.setPower(.5);
            sleep( 1210);

            right.setPower(.7);
            left.setPower(.7);
            sleep( 2460);

            right.setPower(0);
            left.setPower(0.5);
            sleep( 1200);

            right.setPower(1);
            left.setPower(1);
            sleep( 1000);

            yoink.setPower(-1);
            sleep(300);
            right.setPower(.7);
            left.setPower(.7);
            sleep(800);
            yoink.setPower(0);
            sleep(600);

            right.setPower(0);
            left.setPower(0);
            sleep(300);

            right.setPower(-1);
            left.setPower(-0.95);
            sleep( 5000);
        }

        left.setPower(0);
        right.setPower(0);
        sleep(5000);

        /**
         * Stop OpMode */
        telemetry.addLine("Program Complete");
//        telemetry.addData("Left Has Moved: ", left.getCurrentPosition());
//        telemetry.addData("Right Has Moved: ", right.getCurrentPosition());
        telemetry.update();
        sleep(1000);
    }
}
