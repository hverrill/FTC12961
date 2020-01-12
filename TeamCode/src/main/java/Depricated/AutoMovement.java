package Depricated;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;


public class AutoMovement {
    public double x, y, thetaFinal, sine, cosine, tempPower, degreesTheta, dist, leftBackPower, leftFrontPower, rightBackPower, rightFrontPower;
    public Long lB, lF, rB, rF;
    public int leftBackTarget, leftFrontTarget, rightBackTarget, rightFrontTarget;
    public DcMotor lBackMotor, lFrontMotor, rBackMotor, rFrontMotor;
    public double ratio;
    private ElapsedTime runtime = new ElapsedTime();
    public void initializeMotors(DcMotor leftFront, DcMotor leftBack, DcMotor rightFront, DcMotor rightBack){
        // GRAB ALL MOTOR VARIABLES :
        lFrontMotor = leftFront;
        lBackMotor = leftBack;
        rFrontMotor = rightFront;
        rBackMotor = rightBack;

        lFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        lFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //END INITIALIZE METHOD
    }
    public void untilNotBusy(){


        int sum = 0;
        // LOOP UNTIL AT VALUES :
        while(sum < 4){

            sum = 0;
            double percent1 = (double)(lBackMotor.getCurrentPosition()+1)/(lBackMotor.getTargetPosition()+1);
            double percent2 = (double)(lFrontMotor.getCurrentPosition()+1)/(lFrontMotor.getTargetPosition()+1);
            double percent3 = (double)(rBackMotor.getCurrentPosition()+1)/(rBackMotor.getTargetPosition()+1);
            double percent4 = (double)(rFrontMotor.getCurrentPosition()+1)/(rFrontMotor.getTargetPosition()+1);
            if(percent1 >= .9){
                sum++;
            }
            if(percent2 >= .9){
                sum++;
            }
            if(percent3 >= .9){
                sum++;
            }
            if(percent4 >= .9){
                sum++;
            }
        }
        // STOP MOTOR MOVEMENT :
        lBackMotor.setPower(0);
        lFrontMotor.setPower(0);
        rBackMotor.setPower(0);
        rFrontMotor.setPower(0);

        // SWITCH BACK TO RUN_USING_ENCODERS : TURNS OFF BUSY
        lFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
    public void move(double distance, double angle, double power){
        tempPower = power;
        degreesTheta = angle;
        thetaFinal = Math.toRadians(degreesTheta) + (Math.PI/2); // !!! finalTheta var to reduce function calls, (degreesTheta/360)*PI -
        sine = Math.sin(thetaFinal); // sine var to reduce function calls -
        cosine = Math.cos(thetaFinal); // cosine var to reduce function calls -
        ratio = 383.6/(Math.cos(thetaFinal)*10*Math.PI); // !!!
        dist = distance*ratio;
        lF = Math.round(sine * dist);
        lB = Math.round(cosine * dist);
        rF = Math.round(cosine * dist);
        rB = Math.round(sine * dist);


        // SET MOTOR TARGET POSITION VALUES :
        leftBackTarget = lB.intValue() + lBackMotor.getCurrentPosition();
        leftFrontTarget = lF.intValue() + lFrontMotor.getCurrentPosition();
        rightBackTarget = rB.intValue() + rBackMotor.getCurrentPosition();
        rightFrontTarget = rF.intValue() + rFrontMotor.getCurrentPosition();

        lBackMotor.setTargetPosition(leftBackTarget);
        lFrontMotor.setTargetPosition(leftFrontTarget);
        rBackMotor.setTargetPosition(rightBackTarget);
        rFrontMotor.setTargetPosition(rightFrontTarget);

        // SET MOTOR MODE TO RUN_TO_POSITION :
        lBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // SET POWER OF THE MOTORS :
        leftBackPower = power*cosine;
        leftFrontPower = power*sine;
        rightBackPower = power*sine;
        rightFrontPower = power*cosine;
        lBackMotor.setPower(leftBackPower);
        lFrontMotor.setPower(leftFrontPower);
        rBackMotor.setPower(rightBackPower);
        rFrontMotor.setPower(rightFrontPower);

        untilNotBusy(); // WAIT UNTIL ALL MOTORS ARE NOT BUSY -

        // STOP MOTOR MOVEMENT :
        lBackMotor.setPower(0);
        lFrontMotor.setPower(0);
        rBackMotor.setPower(0);
        rFrontMotor.setPower(0);

        // SWITCH BACK TO RUN_USING_ENCODERS : TURNS OFF BUSY
        lFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //END MOVE METHOD
    }
    public void turn(double angle, double power){
        tempPower = power;
        degreesTheta = angle;
        thetaFinal = -Math.toRadians(degreesTheta) + (Math.PI/4); // finalTheta var to reduce function calls, (degreesTheta/360)*PI -
        sine = Math.sin(thetaFinal); // sine var to reduce function calls -
        cosine = Math.cos(thetaFinal); // cosine var to reduce function calls -
        ratio = 383.6/(Math.cos(thetaFinal)*10*Math.PI);
        dist = (angle/360) * 203.1286079 * ratio;
        int distFinal = (int)Math.round(dist);


        // SET MOTOR TARGET POSITION VALUES :
        lBackMotor.setTargetPosition(-distFinal + lBackMotor.getCurrentPosition());
        lFrontMotor.setTargetPosition(-distFinal + lFrontMotor.getCurrentPosition());
        rBackMotor.setTargetPosition(distFinal + rBackMotor.getCurrentPosition());
        rFrontMotor.setTargetPosition(distFinal + rFrontMotor.getCurrentPosition());

        // SET MOTOR MODE TO RUN_TO_POSITION :
        lBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // SET POWER OF THE MOTORS :
        lBackMotor.setPower(-power);
        lFrontMotor.setPower(-power);
        rBackMotor.setPower(power);
        rFrontMotor.setPower(power);

        untilNotBusy(); // WAIT UNTIL ALL MOTORS ARE NOT BUSY -

        // STOP MOTOR MOVEMENT :
        lBackMotor.setPower(0);
        lFrontMotor.setPower(0);
        rBackMotor.setPower(0);
        rFrontMotor.setPower(0);

        // SWITCH BACK TO RUN_USING_ENCODERS : TURNS OFF BUSY
        lFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // END TURN METHOD
    }
}
