//package org.firstinspires.ftc.teamcode.FPS;
//import org.firstinspires.ftc.teamcode.Autonomous.AutoVisionTestOpmode;
//
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.TouchSensor;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//public class Movement {
//    private AutoVisionTestOpmode sadbutnessesary;
//    public DcMotor leftFront;
//    public DcMotor rightFront;
//    public DcMotor leftBack;
//    public DcMotor rightBack;
//    public DcMotor intakeLeft;
//    public DcMotor intakeRight;
//    public TouchSensor blockToggle;
////    public Movement(AutoVisionTestOpmode blah){
////        sadbutnessesary = blah;
////        leftBack = sadbutnessesary.leftBack;
////        leftFront = sadbutnessesary.leftFront;
////        rightBack = sadbutnessesary.rightBack;
////        rightFront = sadbutnessesary.rightFront;
////        intakeLeft = sadbutnessesary.intakeLeft;
////        intakeRight = sadbutnessesary.intakeRight;
////        blockToggle = sadbutnessesary.blockToggle;
////    }
//
//
//
//
//    public void turnClockwise(double power, long millis){
//        leftFront.setPower(-power);
//        leftBack.setPower(-power);
//        rightFront.setPower(power);
//        rightBack.setPower(power);
//        sadbutnessesary.stopAfter(millis);
//    }
//    public void turnAntiClockwise(double power, long millis){
//        leftFront.setPower(power);
//        leftBack.setPower(power);
//        rightFront.setPower(-power);
//        rightBack.setPower(-power);
//        sadbutnessesary.stopAfter(millis);
//    }
//    public void forward(double power, long millis){
//        leftFront.setPower(power);
//        leftBack.setPower(power);
//        rightFront.setPower(power);
//        rightBack.setPower(power);
//        sadbutnessesary.stopAfter(millis);
//    }
//    public void reverse(double power, long millis){
//        leftFront.setPower(-power);
//        leftBack.setPower(-power);
//        rightFront.setPower(-power);
//        rightBack.setPower(-power);
//        sadbutnessesary.stopAfter(millis);
//    }
//    public void succ(ElapsedTime time){
//        double starttime = time.milliseconds();
//        intakeRight.setPower(-.6);
//        intakeLeft.setPower(.6);
//        leftFront.setPower(.15);
//        leftBack.setPower(.15);
//        rightFront.setPower(.15);
//        rightBack.setPower(.12);
//        // loop until we detect a block or x seconds have expired
//
//        while(blockToggle.getValue() < 1) {
//
//        }
//
//        sadbutnessesary.stopAfter(0);
//
//    }
//
//}
