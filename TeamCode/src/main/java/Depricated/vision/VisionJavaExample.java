package Depricated.vision;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

@TeleOp

public class VisionJavaExample extends LinearOpMode{
    MasterVision vision;
    SampleRandomizedPositions goldPosition;

    @Override
    public void runOpMode() throws InterruptedException {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;// recommended camera direction
        parameters.vuforiaLicenseKey = "AX6ynMb/////AAABmcK8xLR/cEMTk3Qy46GU0Po3YfoGNrpPXjGYiHOdnYKR8Lq1ccLyKdxvLsJC0AeowUIB8E8l6Gi7a2jq348Toy/p2FjR9CZ5N7J0LZaL3omzgZ3fur1L371la0RrSQeYGr7tHzkM1SelARBr4P2sl0cjZomOnRhwNvjyEzwf1RVBPnbmdjbXm7m0eRCpkYLgE2DqMYgdEJY1fTQ+W5KgVOKtpe88fWx1u764G/yCfJktjI4zUkrwmRUDtO26FUHnr0Rb2lX8O+V619d0WdHMXPbILAXOFMxVEgn+mK6ASc+/L2Qxkb9C/R+3s4ckC5pDity18Qv+Z0l9kQWeWLHbXJPYVXM863wURNZWTWn1JMRA";

        vision = new MasterVision(parameters, hardwareMap, true, MasterVision.TFLiteAlgorithm.INFER_LEFT);
        vision.init();// enables the camera overlay. this will take a couple of seconds
        vision.enable();// enables the tracking algorithms. this might also take a little time

        waitForStart();

        vision.disable();// disables tracking algorithms. this will free up your phone's processing power for other jobs.

        goldPosition = vision.getTfLite().getLastKnownSampleOrder();

        while(opModeIsActive()){
            telemetry.addData("goldPosition was", goldPosition);// giving feedback

            switch (goldPosition){ // using for things in the autonomous program
                case LEFT:
                    telemetry.addLine("going to the left");
                    break;
                case CENTER:
                    telemetry.addLine("going straight");
                    break;
                case RIGHT:
                    telemetry.addLine("going to the right");
                    break;
                case UNKNOWN:
                    telemetry.addLine("staying put");
                    break;
            }

            telemetry.update();
        }

        vision.shutdown();
    }
}
