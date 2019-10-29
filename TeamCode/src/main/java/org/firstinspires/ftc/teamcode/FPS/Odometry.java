package org.firstinspires.ftc.teamcode.FPS;
//import com.qualcomm.robotcore.hardware.

import com.qualcomm.robotcore.hardware.DcMotor;

public class Odometry {
    DcMotor vertical_left, vertical_right, horizontal_init;
    public void initialize(DcMotor verticalL, DcMotor verticalR, DcMotor horizontal){
        vertical_left = verticalL;
        vertical_right = verticalR;
        horizontal_init = horizontal;
    }
    public int getX(){
        int x1 = 0;
        int x2 = 0;
        return (x1+x2)/2;
    }
    public int getY(){
        long y = 9;
        return 3;
    }
}
