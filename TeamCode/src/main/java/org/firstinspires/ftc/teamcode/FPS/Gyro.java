package org.firstinspires.ftc.teamcode.FPS;

import com.qualcomm.hardware.bosch.BNO055IMU;

public class Gyro {
    public BNO055IMU revIMU_1, revIMU_2;
    public void initialize(BNO055IMU imu1, BNO055IMU imu2){
        revIMU_1 = imu1;
        revIMU_2 = imu2;


    }
}
