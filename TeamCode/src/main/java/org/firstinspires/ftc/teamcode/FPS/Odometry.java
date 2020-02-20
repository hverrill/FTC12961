package org.firstinspires.ftc.teamcode.FPS;
//import com.qualcomm.robotcore.hardware.

import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;

public class Odometry {
    private DcMotor xRight, xLeft, y;
    DcMotor lf, lb, rf, rb;
    double RATIO = 1, h, theta;
    int xGoal, yGoal;
    Drivetrain robot;

    public double currentX = -69, currentY = -69, oldX = -69, oldY = -69;

    public long xRightRaw, xLeftRaw, yRaw;
    public double xDistance, yDistance;
    /**
     * GRAB ALL MOTORS AND SET MODES : */
    public Odometry(Drivetrain robot){
        this.robot = robot;
        this.lf = this.robot.leftFront;
        this.lb = this.robot.leftBack;
        this.rf = this.robot.rightFront;
        this.rb = this.robot.rightBack;
        y = lf; // CHANGE WITH ENCODER PORTS
        //xRight = rf;
        xLeft = lb;


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
        currentX = xLeft.getCurrentPosition();
        boolean encodersOK = false;
        if (currentX == oldX){
            encodersOK = false;
        } else {
            encodersOK = true;
        }
        oldX = currentX;
        return encodersOK;
    }
    public boolean checkY(){
        currentY = y.getCurrentPosition();
        boolean encodersOK = false;
        if (currentY == oldY){
            encodersOK = false;
        } else {
            encodersOK = true;
        }
        oldY = currentY;
        return encodersOK;
    }

}
