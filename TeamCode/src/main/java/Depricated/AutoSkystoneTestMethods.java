////Imports:
//package Depricated;
//import Depricated.AutoMovement;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.Servo;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//
///**
// * This program is Checkmate Robotics' Autonomous Program Template.
// */
//
//@Autonomous(name="Auto 2019/10/13", group="Sky")
//@Disabled
//public class AutoSkystoneTestMethods extends LinearOpMode {
//
//    /*
//     *  Declare OpMode Members: */
//    //Ex: private DcMotor exampleMotor = null;
//    private DcMotor leftFront, leftBack, rightFront, rightBack, liftSyst = null;
//    private Servo hook1 = null;
//    private Servo hook2 = null;
//
//    private ElapsedTime runtime = new ElapsedTime();
//
//    private AutoMovement robot = new AutoMovement();
//
//    @Override
//    public void runOpMode() {
//
//        /**
//         * Hardware Variables: */
//        //Ex: exampleMotor  = hardwareMap.get(DcMotor.class, "motor");
//        leftFront  = hardwareMap.get(DcMotor.class, "LF");
//        leftBack = hardwareMap.get(DcMotor.class, "LB");
//        rightFront  = hardwareMap.get(DcMotor.class, "RF");
//        rightBack = hardwareMap.get(DcMotor.class, "RB");
//        //hook1 = hardwareMap.get(Servo.class, "hook1");
//        //hook2 = hardwareMap.get(Servo.class, "hook2");
//        robot.initializeMotors(leftFront, leftBack, rightFront, rightBack);
//
//        leftFront.setDirection(DcMotor.Direction.FORWARD);
//        leftBack.setDirection(DcMotor.Direction.FORWARD);
//        rightFront.setDirection(DcMotor.Direction.REVERSE); // Change to forward after unswitching polarity
//        rightBack.setDirection(DcMotor.Direction.REVERSE); // Change to forward after unswitching polarity
//
//
//        /**
//         * Telemetry */
//        telemetry.addData("Status", "Ready to run");
//        telemetry.update();
//        /*
//        Wait for driver to hit the start button on the controller:
//         */ waitForStart();
//
//
//        //robot.move(50, 45, .5);
//        //robot.move(50, 225, .5);
//        robot.move(20, 30, .6);
//        robot.move(20, 60, .6);
//        robot.move(20, 90, .6);
//        robot.move(20, 120, .6);
//        robot.move(20, 150, .6);
//        robot.move(20, 180, .6);
//
//
//        //robot.move(50, 180, .5);
//        telemetry.addData("ipower", robot.leftBackPower);
//        telemetry.addData("ipower", robot.leftFrontPower);
//        telemetry.addData("ipower", robot.rightBackPower);
//        telemetry.addData("ipower", robot.rightFrontPower);
//
//        telemetry.addData("ipos", robot.leftBackTarget);
//        telemetry.addData("ipos", robot.leftFrontTarget);
//        telemetry.addData("ipos", robot.rightBackTarget);
//        telemetry.addData("ipos", robot.rightFrontTarget);
//        telemetry.update();
//        sleep(6000);
//        telemetry.clear();
//        telemetry.addData("busy", robot.lBackMotor.isBusy());
//        telemetry.addData("busy", robot.lFrontMotor.isBusy());
//        telemetry.addData("busy", robot.rBackMotor.isBusy());
//        telemetry.addData("busy", robot.rFrontMotor.isBusy());
//
//        telemetry.addData("pos", robot.lBackMotor.getCurrentPosition());
//        telemetry.addData("pos", robot.lFrontMotor.getCurrentPosition());
//        telemetry.addData("pos", robot.rBackMotor.getCurrentPosition());
//        telemetry.addData("pos", robot.rFrontMotor.getCurrentPosition());
//        telemetry.update();
//
//
//
//         sleep(9999999);
//         //robot.turn(90, .5);
////         telemetry.addData("2", "2");
////         sleep(30000);
//        /**
//         * : */
//
//
//        /**
//         * Stop OpMode */
//
//        telemetry.addData("Path", "Complete");
//        telemetry.update();
//    }
//}