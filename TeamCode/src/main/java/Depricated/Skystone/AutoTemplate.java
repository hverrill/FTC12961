////Imports:
//package Depricated.Skystone;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
///**
// * This program is Checkmate Robotics' Autonomous Program Template.
// */
//
//@Autonomous(name="Auto 2018/10/2", group="Rover")
//@Disabled
//public class AutoTemplate extends LinearOpMode {
//
//    /*
//     *  Declare OpMode Members: */
//    //Ex: private DcMotor exampleMotor = null;
//    private ElapsedTime runtime = new ElapsedTime();
//
//
//    @Override
//    public void runOpMode() {
//
//        /**
//         * Hardware Variables: */
//        //Ex: exampleMotor  = hardwareMap.get(DcMotor.class, "motor");
//
//        /**
//         * Telemetry */
//        telemetry.addData("Status", "Ready to run");
//        telemetry.update();
//        /*
//        Wait for driver to hit the start button on the controller:
//         */ waitForStart();
//
//
//        /**
//         * : */
//
//        // 1: Do Action 2:
//        /* Action 1 */
//        runtime.reset();
//        while (opModeIsActive() && (runtime.seconds() < 1.0)) telemetry.update();
//
//
//        // 2: Do Action 2:
//        /* Action 2 */
//        sleep(1000);
//
//
//        /**
//         * Stop OpMode */
//
//        telemetry.addData("Path", "Complete");
//        telemetry.update();
//        sleep(1000);
//    }
//}