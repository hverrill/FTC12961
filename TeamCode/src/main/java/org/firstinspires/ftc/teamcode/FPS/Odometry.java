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
    ArrayList<Integer> coords = new ArrayList<Integer>();
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
    public void update(){
        /**
         * GET RAW VALUES : */
        coords.clear();
        coords.add(xLeft.getCurrentPosition()); //(xLeft.getCurrentPosition()+ ) / 2
        coords.add(y.getCurrentPosition());

    }
    public double getX(){
        update();
        return coords.get(0);
    }
    public double getY(){
        update();
        return coords.get(1);
    }

}
