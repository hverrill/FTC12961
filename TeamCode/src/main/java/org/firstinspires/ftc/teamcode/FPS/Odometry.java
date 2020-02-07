package org.firstinspires.ftc.teamcode.FPS;
//import com.qualcomm.robotcore.hardware.

import com.qualcomm.robotcore.hardware.DcMotor;

public class Odometry {
    DcMotor xRight, xLeft, y;
    DcMotor lf, lb, rf, rb;
    double ratio, h, theta;
    int xGoal, yGoal;
    Drivetrain robot;
    public long xRightRaw, xLeftRaw, yRaw;
    public double xDistance, yDistance;
    /**
     * GRAB ALL MOTORS AND SET MODES : */
    public Odometry(Drivetrain robot){
        this.lf = robot.leftFront;
        this.lb = robot.leftBack;
        this.rf = robot.rightFront;
        this.rb = robot.rightBack;
        this.robot = robot;
        y = lf;
        xRight = lb;
        xLeft = rf;

        y.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        xLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        xRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        y.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        xLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        xRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


    }
    /**
     * UPDATE INTERNAL VALUES : */
    public void update(){
        /**
         * GET RAW VALUES : */
        xLeftRaw = xLeft.getCurrentPosition();
        xRightRaw = xRight.getCurrentPosition();
        yRaw = y.getCurrentPosition();

        /**
         * GET DISTANCE VALUES : */
        ratio = 1; //ticks per cm (Not really yet)
        xDistance = ((xRightRaw+xLeftRaw)/2)*ratio;
        yDistance = yRaw*ratio;
    }
    public double getX(){ return xDistance; }
    public double getY(){ return yDistance; }
//    public void goTo(double x, double y){
//        double l, r;
//        update();
//        h = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
//        theta = -Math.atan2(-x, y) + (Math.PI/4);
//
//        l = Math.sin(theta)*.5;
//        r = Math.cos(theta)*.5;
//
//
//        while (getY()/(y*ratio) > .95 && getX()/(x*ratio) > .95){
//            this.lf.setPower();
//        }
//
//    }
//    public boolean check(int x, int y){
//        int lf, lb, rf, rb;
//        lf = this.lf.getCurrentPosition();
//        lb = this.rb.getCurrentPosition();
//        rf = this.rf.getCurrentPosition();
//        rb = this.rb.getCurrentPosition();
//
//        return lf
//
//    }

}
