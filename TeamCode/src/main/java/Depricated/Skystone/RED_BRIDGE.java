////Imports:
//package Depricated.Skystone;
//
//import com.qualcomm.hardware.bosch.BNO055IMU;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//import org.firstinspires.ftc.teamcode.FPS.Hardware;
//import org.firstinspires.ftc.teamcode.FPS.Measurement;
//
//
///**
// * This program is Checkmate Robotics' Autonomous Program Template.
// */
//
//@Autonomous(name="BRIDGE_Red", group="State")
////@Disabled
//public class RED_BRIDGE extends LinearOpMode {
//
//    /*
//     *  Declare OpMode Members: */
//    public BNO055IMU revIMU;
//    Hardware robot = new Hardware();
//
//
//    private ElapsedTime runtime = new ElapsedTime();
//
//    //private AutoMovement robot = new AutoMovement();
//
//    @Override
//    public void runOpMode() {
//
//        /**
//         * Hardware Variables: */
//        robot.map(hardwareMap);
//
//
//        /**
//         * Telemetry */
//        telemetry.addData("Status", "Ready to run");
//        telemetry.update();
//        /*
//        Wait for driver to hit the start button on the controller:
//         */
//        waitForStart();
////        // go forward
////        leftFront.setPower(-0.20);
////        leftBack.setPower(-0.20);
////        rightFront.setPower(-0.20);
////        rightBack.setPower(-0.20);
////        sleep(1100);
////        leftFront.setPower(0);
////        leftBack.setPower(0);
////        rightFront.setPower(0);
////        rightBack.setPower(0);
////        sleep(100);
//
//        //strafe right
//        sleep(15000);
//        robot.forward(6000);
//        robot.strafeLeft(5000);
//
//
//
//
//
//
//
//        sleep(9999999);
//        //robot.turn(90, .5);
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
//    public void rotate(float degrees){
//
//        boolean turning = true;
//
//        float targetAngle = robot.sensorSuite.getAngle().angle1 + degrees;
//        double ratio;
//        double powerPolarity = degrees/Math.abs(degrees);
//        double powerMultiplier;
//
//        while(turning && !isStopRequested()){
//
//            ratio = robot.sensorSuite.getAngle().angle1/targetAngle;
//
//            powerMultiplier = 1-ratio;
//
//            if(Math.abs(powerMultiplier) < .15){
//                powerMultiplier = .2;
//            }
//            if(Math.abs(powerMultiplier) > .85){
//                powerMultiplier = .85;
//            }
//
//            robot.leftBack.setPower(-.2 * powerMultiplier * powerPolarity);
//            robot.leftFront.setPower(-.2 * powerMultiplier * powerPolarity);
//            robot.rightBack.setPower(.2 * powerMultiplier * powerPolarity);
//            robot.rightFront.setPower(.2 * powerMultiplier * powerPolarity);
//
//            if(ratio > .95) turning = false;
//
//        }
//        robot.leftBack.setPower(0);
//        robot.leftFront.setPower(0);
//        robot.rightBack.setPower(0);
//        robot.rightFront.setPower(0);
//    }
//    public void setFourbarPos(double pos){
//        robot.fourbarRight.setPosition(pos);
//        robot.fourbarLeft.setPosition(1-pos);
//    }
//    public void grabbersDown(){
//        robot.leftHook.setPosition(.27);
//        robot.rightHook.setPosition(.73);
//    }
//    public void stopAfter(long millis){
//        sleep(millis);
//        robot.leftFront.setPower(0);
//        robot.leftBack.setPower(0);
//        robot.rightFront.setPower(0);
//        robot.rightBack.setPower(0);
//        robot.intakeLeft.setPower(0);
//        robot.intakeRight.setPower(0);
//    }
//    public void turnClockwise(double power){// , long millis
//        robot.leftFront.setPower(-power);
//        robot.leftBack.setPower(-power);
//        robot.rightFront.setPower(power);
//        robot.rightBack.setPower(power);
//        //stopAfter(millis);
//    }
//    public void turnAntiClockwise(double power, long millis){
//        robot.leftFront.setPower(power);
//        robot.leftBack.setPower(power);
//        robot.rightFront.setPower(-power);
//        robot.rightBack.setPower(-power);
//        stopAfter(millis);
//    }
//    public void forward(double power){ //, long millis
//        robot.leftFront.setPower(power);
//        robot.leftBack.setPower(power);
//        robot.rightFront.setPower(power);
//        robot.rightBack.setPower(power);
//        //stopAfter(millis);
//    }
//    public void reverse(double power){ //, long millis
//        robot.leftFront.setPower(-power);
//        robot.leftBack.setPower(-power);
//        robot.rightFront.setPower(-power);
//        robot.rightBack.setPower(-power);
//        //stopAfter(millis);
//    }
//    public void strafe(double power){//, long millis
//        robot.leftFront.setPower(-power);
//        robot.leftBack.setPower(power);
//        robot.rightFront.setPower(power);
//        robot.rightBack.setPower(-power);
//        //stopAfter(millis);
//    }
//    public void succ(ElapsedTime time){
//        double starttime = time.milliseconds();
//        robot.intakeRight.setPower(-.6);
//        robot.intakeLeft.setPower(.6);
//        robot.leftFront.setPower(.15);
//        robot.leftBack.setPower(.15);
//        robot.rightFront.setPower(.15);
//        robot.rightBack.setPower(.12);
//        // loop until we detect a block or x seconds have expired
//
//        while(robot.blockToggle.getValue() < 1) {
//
//        }
//
//        stopAfter(0);
//
//    }
//}