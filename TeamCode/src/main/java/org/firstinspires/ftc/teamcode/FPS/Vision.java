package org.firstinspires.ftc.teamcode.FPS;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;

//import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
//import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
//import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
//import org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
//import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;
//import org.firstinspires.ftc.teamcode.library.functions.Point3D;
//import org.firstinspires.ftc.teamcode.library.vision.skystone.opencv.OpenCvContainer;
//import org.openftc.easyopencv.OpenCvCamera;
//import org.openftc.easyopencv.OpenCvCameraFactory;
//import org.openftc.easyopencv.OpenCvCameraRotation;
//import org.openftc.easyopencv.OpenCvInternalCamera;
//import org.openftc.easyopencv.OpenCvPipeline;

public class Vision {
    private float mmPerInch = 25.4f;
    private double target = 8 * mmPerInch;
    private boolean targetVisible = false;
    private OpenGLMatrix lastLocation = null;
    private VuforiaLocalizer vuforia = null;
    public VuforiaLocalizer.Parameters parameters = null;
    VuforiaTrackables targetsSkyStone = this.vuforia.loadTrackablesFromAsset("Skystone");
    private float stoneZ = 2.00f * 6;
    private float portalXrotate = 0f;
    private float portalYrotate = 0f;
    private float portalZrotate = 0f;
    VuforiaTrackable stone = targetsSkyStone.get(0);
    public String vuforiaKey;

    public VuforiaLocalizer createVuforia(VuforiaLocalizer.CameraDirection cameraDirection, HardwareMap hardwareMap) {
        parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = vuforiaKey;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        stone.setLocation(OpenGLMatrix
                .translation(0, 0, stoneZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

    return vuforia;
    }
    
    public boolean isTargetVisible(VuforiaTrackable target){
        return ((VuforiaTrackableDefaultListener)target.getListener()).isVisible();
    }
}
