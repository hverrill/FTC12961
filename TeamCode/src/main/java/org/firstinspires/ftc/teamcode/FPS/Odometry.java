package org.firstinspires.ftc.teamcode.FPS;
//import com.qualcomm.robotcore.hardware.

import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;

public class Odometry {
    private DcMotor xRight, xLeft, y;
    DcMotor lf, lb, rf, rb;
    double RATIO = 1, h, theta;
    int xGoal, yGoal;
    private Hardware robot;

    public double currentX = -69, currentY = -69, oldX = -69, oldY = -69;
    public double failsX = 0, failsY = 0;

    public long xRightRaw, xLeftRaw, yRaw;
    public double xDistance, yDistance;

    public Odometry(Hardware robot){
        this.robot = robot;
        this.lf = this.robot.leftFront;
        this.lb = this.robot.leftBack;
        this.rf = this.robot.rightFront;
        this.rb = this.robot.rightBack;
        y = lf; // CHANGE WITH ENCODER PORTS
        //xRight = rf;
        xLeft = lb;
        y.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        xLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        y.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        xLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


    }
    /**
     * UPDATE INTERNAL VALUES : */

    public double getX(){
        currentX = xLeft.getCurrentPosition();
        return currentX;
    }
    public double getY(){
        currentY = y.getCurrentPosition();
        return currentY;
    }
    public boolean checkX(){
        currentX = lb.getCurrentPosition();
        boolean encodersOK = currentX != oldX;
        if(!encodersOK) failsX++;
            else failsX = 0;
        encodersOK = failsX < 10; // if fails are greater than 10, set encodersOk to false
        oldX = currentX;
        return encodersOK;
    }
    public boolean checkY(){
        currentY = y.getCurrentPosition();
        boolean encodersOK = currentY != oldY;
        if(!encodersOK) failsY++;
            else failsY = 0;
        encodersOK = failsY < 10; // if fails are greater than 10, set encodersOk to false
        oldY = currentY;
        return encodersOK;
    }

}
