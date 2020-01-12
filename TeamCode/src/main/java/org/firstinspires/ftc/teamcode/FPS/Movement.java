package org.firstinspires.ftc.teamcode.FPS;
import org.firstinspires.ftc.teamcode.Autonomous.AutoVisionTestOpmode;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Movement {
    private AutoVisionTestOpmode sadbutnessesary;
    public DcMotor leftFront;
    public DcMotor rightFront;
    public DcMotor leftBack;
    public DcMotor rightBack;
    public DcMotor intakeLeft;
    public DcMotor intakeRight;
    public Movement(AutoVisionTestOpmode blah){
        sadbutnessesary = blah;
    }




    public void turnClockwise(double power, long millis){
        leftFront.setPower(-power);
        leftBack.setPower(-power);
        rightFront.setPower(power);
        rightBack.setPower(power);
        sadbutnessesary.stopAfter(millis);
    }
    public void turnAntiClockwise(double power, long millis){
        leftFront.setPower(power);
        leftBack.setPower(power);
        rightFront.setPower(-power);
        rightBack.setPower(-power);
        sadbutnessesary.stopAfter(millis);
    }
    public void forward(double power, long millis){
        leftFront.setPower(power);
        leftBack.setPower(power);
        rightFront.setPower(power);
        rightBack.setPower(power);
        sadbutnessesary.stopAfter(millis);
    }
    public void reverse(double power, long millis){
        leftFront.setPower(-power);
        leftBack.setPower(-power);
        rightFront.setPower(-power);
        rightBack.setPower(-power);
        sadbutnessesary.stopAfter(millis);
    }
    public void succ(long millis){
        intakeRight.setPower(.6);
        intakeLeft.setPower(.6);
        leftFront.setPower(.15);
        leftBack.setPower(.15);
        rightFront.setPower(.15);
        rightBack.setPower(.15);
        sadbutnessesary.stopAfter(millis);
    }

}
