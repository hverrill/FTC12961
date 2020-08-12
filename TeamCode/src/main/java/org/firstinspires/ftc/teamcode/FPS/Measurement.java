package org.firstinspires.ftc.teamcode.FPS;
import org.firstinspires.ftc.teamcode.FPS.Inumeration.*;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.FPS.Inumeration.AngleResult;

public class Measurement {
    private Hardware robot = new Hardware();
    public BNO055IMU revIMU;
    public HardwareMap hardwareMap;
    AngleResult results = new AngleResult();
    public double currentAngle1 = -181, oldAngle1 = -181;

    public Measurement(Hardware robot){
        this.robot = robot;
        hardwareMap = this.robot.hardwareMap;
        revIMU = this.robot.revIMU;
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        revIMU = hardwareMap.get(BNO055IMU.class, "imu");
        revIMU.initialize(parameters);
    }
    public AngleResult getAngle(){
        revIMU.getPosition();
        results.angle1 = revIMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
        results.angle2 = revIMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).secondAngle;
        results.angle3 = revIMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).thirdAngle;
        currentAngle1 = results.angle1;
        return results;
    }
    public boolean checkAngle1(){
        currentAngle1 = results.angle1;
        boolean imuOK = currentAngle1 != oldAngle1;
        oldAngle1 = currentAngle1;
        return imuOK;
    }
}
