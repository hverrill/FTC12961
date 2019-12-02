package org.firstinspires.ftc.teamcode.FPS;
//import com.qualcomm.robotcore.hardware.

import com.qualcomm.robotcore.hardware.DcMotor;

public class Odometry {
    DcMotor xRight, xLeft, y;
    double ratio;
    public long xRightRaw, xLeftRaw, yRaw;
    public double xDistance, yDistance;
    /**
     * GRAB ALL MOTORS AND SET MODES : */
    public void initialize(DcMotor xrInput, DcMotor xlInput, DcMotor yInput){
        y = yInput;
        xRight = xrInput;
        xLeft = xlInput;
        y.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        xLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        xRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        y.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        xLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        xRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


    }
    /**
     * UPDATE INTERNAL VALUES : */
    public void update(double theta){
        /**
         * GET RAW VALUES : */
        xLeftRaw = xLeft.getCurrentPosition();
        xRightRaw = xRight.getCurrentPosition();
        yRaw = y.getCurrentPosition();

        /**
         * GET DISTANCE VALUES : */
        ratio = 383.6/(Math.cos(theta)*10*Math.PI); // Calculate ticks per cm
        xDistance = ((xRightRaw+xLeftRaw)/2)*ratio;
        yDistance = yRaw*ratio;
    }

}
