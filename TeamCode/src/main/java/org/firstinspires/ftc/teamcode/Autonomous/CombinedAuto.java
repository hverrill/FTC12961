////Imports:
//package org.firstinspires.ftc.teamcode.Autonomous;
//
//import com.qualcomm.hardware.bosch.BNO055IMU;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.TouchSensor;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
//import org.firstinspires.ftc.teamcode.FPS.Drivetrain;
//import org.firstinspires.ftc.teamcode.FPS.Vision;
//
//import java.util.ArrayList;
//
//
///**
// * This program is Checkmate Robotics' Autonomous Program Template.
// */
//
//@Autonomous(name="Combined Auto", group="Beta")
////@Disabled
//public class CombinedAuto extends LinearOpMode {
//
//    /*
//     *  Declare OpMode Members: */
//    //Ex: private DcMotor exampleMotor = null;
//    public TouchSensor blockToggle;
//    private ArrayList<Integer> coords = new ArrayList<Integer>();
//    private VuforiaLocalizer vuforia = null;
//    private int dist;
//    public BNO055IMU revIMU;
//    private ElapsedTime runtime = new ElapsedTime();
//
//    Drivetrain robot = new Drivetrain();
//
//    @Override
//    public void runOpMode() {
//
//        /**
//         * Hardware Variables: */
//        //Ex: exampleMotor  = hardwareMap.get(DcMotor.class, "motor");
//        robot.map(hardwareMap);
//
//
//
//        robot.initVuforia(telemetry);
//        telemetry.addData("ready to", "run");
//        telemetry.update();
//        waitForStart(); /** START THE PROGRAM */
//        runtime.reset();
//        //START
//
//        robot.leftHook.setPosition(.4);
//        robot.rightHook.setPosition(.6);
//        setFourbarPos(.8);
//        sleep(1200);
//        robot.strafeLeft(4500, telemetry);
//        sleep(200);
////        robot.forward(1000);
//
//
//        coords.add((int)robot.odometry.getX());
////        coords.add((int)robot.odometry.getY());
//
//        while (!robot.portal.isTargetVisible(robot.portal.stone) && !isStopRequested()) { // Run the vuforia detection (change if you need to detect later)
//            robot.portal.update(robot.portal.stone);
//            robot.setPowerAll(.115);
//        }
////        while ((Math.abs(portal.xTranslation) < margin) && !isStopRequested()) {
////            portal.update(portal.stone);
////            if (portal.xTranslation < -margin) {
////                robot.setPowerAll(.12);
////            }
////            if (portal.xTranslation > margin) {
////                robot.setPowerAll(-.12);
////            }
////            telemetry.addData("Angle =", robot.sensorSuite.getAngle().angle1);
////        }
//        robot.setPowerAll(0);
//        //robot.reverse(1000);
//        coords.add((int)robot.odometry.getX());
////        coords.add((int)robot.odometry.getY());
//        dist = coords.get(1)-coords.get(0);
////        robot.reverse(1000);
//        robot.rotate(40, telemetry);
//        succ();
//        robot.rotate(-70, telemetry);
//        sleep(4000);
//        robot.reverse(dist+12000);
//
//        //end of old vision opmode
//
////
////        sleep(1200);
////        robot.reverse(5000); //actually forward
////        sleep(300);
////        robot.strafeRight(9200);
////        sleep(300);
////        robot.reverse(6000);
////        sleep(300);
////        robot.rotate(-90, telemetry);
////        robot.reverse(4000);
////        robot.leftHook.setPosition(1);
////        robot.rightHook.setPosition(0);
////        sleep(1200);
////
////        robot.forward(5500);
////        sleep(1000);
////        robot.rotate(90, telemetry);
////        sleep(300);
////        robot.reverse(4000);
//
//        while(opModeIsActive()){
//            telemetry.addData("blocktoggle", robot.blockToggle.isPressed());
//            telemetry.update();
//        }
//
//        sleep(9999999);
//    }
////gjyhgl
//
//
//
//    public void setFourbarPos(double pos) {
//        robot.fourbarRight.setPosition(pos);
//        robot.fourbarLeft.setPosition(1 - pos);
//    }
//
//
//    public void succ() {
//
//        double starttime = robot.timer.milliseconds();
//        double finishtime;
//        coords.add((int)robot.odometry.getX());
//        robot.intakeRight.setPower(-.9);
//        robot.intakeLeft.setPower(.9);
//        robot.setPowerAll(.2);
//        // loop until we detect a block or x seconds have expired
//
////        while(!(finishtime - starttime >= 2000)){
////            finishtime = runtime.milliseconds();
////        }
//        finishtime = robot.timer.milliseconds();
//        while (!robot.blockToggle.isPressed() && opModeIsActive()) {
//            telemetry.addData("blocktoggle", robot.blockToggle.isPressed());
//            telemetry.addData("timer", robot.timer.milliseconds());
//            telemetry.addData("goaltime", finishtime - starttime);
//            telemetry.update();
//            finishtime = robot.timer.milliseconds();
//            if(finishtime - starttime >= 4000) break;
//        }
//        robot.setPowerAll(0);
//        robot.intakeLeft.setPower(0);
//        robot.intakeRight.setPower(0);
//        coords.add((int)robot.odometry.getX());
//        sleep(200);
//        robot.reverse(coords.get(3)-coords.get(2));
//
//
////        robot.setPowerAll(0);
//
//    }
//}