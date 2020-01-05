package org.firstinspires.ftc.teamcode.FPS;

import com.qualcomm.robotcore.hardware.HardwareMap;
//import com.qualcomm.robotcore.hardware;
import com.qualcomm.robotcore.util.Hardware;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
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
    public VuforiaLocalizer.Parameters parameters = null;
    private VuforiaLocalizer vuforia = null;
    private float stoneZ = 2.00f * 6;
    private float portalXrotate = 0f;
    private float portalYrotate = -90;
    private float portalZrotate = 0f;
    public float xTranslation;
    public float yTranslation;
    public float zTranslation;
    private Telemetry stoneTelemetry = null;
    final float CAMERA_FORWARD_DISPLACEMENT  = 4.0f * mmPerInch;   // eg: Camera is 4 Inches in front of robot center
    final float CAMERA_VERTICAL_DISPLACEMENT = 8.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
    final float CAMERA_LEFT_DISPLACEMENT     = 0;  // eg: Camera is ON the robot's center line
    public HardwareMap hardwareMap = null;
    public VuforiaTrackables targets = null;
    public VuforiaTrackable stone = null;



    public VuforiaLocalizer createVuforia(VuforiaLocalizer.CameraDirection cameraDirection, HardwareMap map, Telemetry telemetry) {
        hardwareMap = map; // Parse HardwareMap instance from opmode to internal public variable
        stoneTelemetry = telemetry; // Parse Telemetry instance from opmode to internal public variable

        int cameraMonitorViewId = /** What the heck does this line do */ hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId); // Parse data to internal public instance of VuforiaLocalizer.Parameters
        parameters.vuforiaLicenseKey = "AZIn0o3/////AAABmfxYL6aC+UR0kbocCpW0hFEZ3iRVuKE172GjTlK08gQs52Z0HqTYqgUdJgqKjbpP2QCDbqa8DH5FimG7ZyvJk6g4yt0Rlg8EhwZwipv7qJA3e/QvyGFB/C3sDNeFV4WMZksf3cwTsxTVPhw2JUtUGQxrHB/zMgYqJetR9s5xmDN77xAetQY1qvAK5DX6aYr4hKtAaMqQurl28oLjANyZTKRDUQ+vOxCJPrbp+qFEDAiUPdUtS3VJxQxkfIl7rCEpxp/FPgsv+RsZXCgWzJx6oHPL5BN1ZVz5JUa53YgxqKGl0I0bn0Cl8ESenX6qSY5p8d04dc7EwFEJEXGbPngmVfEG8ZqHI0Cg1kkk8LpQ3Pgr";
        vuforia = ClassFactory.getInstance().createVuforia(parameters); // Parse data to internal public instance of VuforiaLocalizer
        parameters.cameraDirection = cameraDirection;
        // Parse given VuforiaLocalizer.CameraDirection variable to parameters object
        lastLocation = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, portalXrotate, portalYrotate, portalZrotate));
        targets = this.vuforia.loadTrackablesFromAsset("Skystone");
        targets.activate();
        stone = targets.get(0);
        com.vuforia.CameraDevice.getInstance().setFlashTorchMode(true);

    return vuforia; // Return final version of VuforiaLocalizer for use in opomode
    }

    public void update(VuforiaTrackable target){
        if (isTargetVisible(target)) {
            stoneTelemetry.addData("Visible Target", stone.getName()); // Reports which navigation target is visible, if any
            OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)stone.getListener()).getUpdatedRobotLocation();
            if (robotLocationTransform != null) {
                lastLocation = robotLocationTransform;
            }

//            ((VuforiaTrackableDefaultListener) stone.getListener()).setPhoneInformation(lastLocation, parameters.cameraDirection);

            /** Report position of robot (camera) in inches */
            VectorF translation = lastLocation.getTranslation();
            stoneTelemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
                    translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);


            /** Report position of robot (camera) in degrees */
            Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
            stoneTelemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f",
                    rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
            xTranslation = translation.get(0);
            yTranslation = translation.get(1);
            zTranslation = translation.get(2);
        }
        else {
            stoneTelemetry.addData("Visible Target", "none"); // Reports that no navigation target is visible
        }
        stoneTelemetry.update(); // Updates telemetry (duh)

    }

    
    public boolean isTargetVisible(VuforiaTrackable target){
        return ((VuforiaTrackableDefaultListener)target.getListener()).isVisible();
    }
}
