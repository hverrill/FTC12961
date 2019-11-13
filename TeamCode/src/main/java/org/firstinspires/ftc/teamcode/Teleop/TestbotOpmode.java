package org.firstinspires.ftc.teamcode.Teleop;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="Testbot Opmode 1", group="Test")
public class TestbotOpmode extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFront, leftBack, rightFront, rightBack, liftSyst = null;
    private Servo hook = null;
    private Servo grabber = null;
    private CRServo sans = null;

    public double rX, rY, lX, lY, robotSpeed, theta, directionSpeed, forwardSpeed, grabPos, hookPos, liftSystPower, sansPower, thetaFinal, sine, cosine;
    public double lB, lF, rB, rF;

    public void processUpdate(){
        rY = gamepad1.right_stick_y;
        lX = gamepad1.left_stick_x;
        lY = gamepad1.left_stick_y;
        rX = gamepad1.right_stick_x;


        // RUN CALCULATIONS :
        robotSpeed = Math.sqrt(Math.pow(lX, 2) + Math.pow(lX, 2));
        theta = Math.atan2(-lX, lY);
        directionSpeed = rX;
        forwardSpeed = -(rY + lY)/2;
        thetaFinal = -theta + (Math.PI/4); // finalTheta var to reduce function calls
        sine = Math.sin(thetaFinal); // sine var to reduce function calls
        cosine = Math.cos(thetaFinal); // cosine var to reduce function calls
        lF = robotSpeed * sine + directionSpeed + forwardSpeed;
        lB = robotSpeed * cosine + directionSpeed + forwardSpeed;
        rF = robotSpeed * cosine - directionSpeed + forwardSpeed;
        rB = robotSpeed * sine - directionSpeed + forwardSpeed;
        //robotSpeed * sine/cosine to reduce more?
        if (gamepad1.dpad_up){
            liftSystPower = -0.5;
        } else if (gamepad1.dpad_down) {
            liftSystPower = 0.5;
        } else {
            liftSystPower = 0;
        }

        // SERVOS:
        if (gamepad1.right_bumper){
            grabPos = 1;
        } else {
            grabPos = 0;
        }
        if (gamepad1.left_bumper){
            hookPos = .9;
        } else {
            hookPos = .2;
        }

        if (gamepad1.dpad_left){
            sansPower = 0.5;
        } else if (gamepad1.dpad_right) {
            sansPower = -0.5;

        }else {
            sansPower= 0;
        }



    }

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftFront  = hardwareMap.get(DcMotor.class, "LF");
        leftBack = hardwareMap.get(DcMotor.class, "LB");
        rightFront  = hardwareMap.get(DcMotor.class, "RF");
        rightBack = hardwareMap.get(DcMotor.class, "RB");
        liftSyst = hardwareMap.get(DcMotor.class, "lift");
        grabber = hardwareMap.get(Servo.class, "grab");
        hook = hardwareMap.get(Servo.class, "hook");
        sans = hardwareMap.get(CRServo.class, "sans");

        leftFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.REVERSE);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            processUpdate();
            // Send calculated power to wheels

            leftFront.setPower(Range.clip(lF*1, -1, 1));
            leftBack.setPower(Range.clip(lB*1, -1, 1));
            rightFront.setPower(Range.clip(rF*1, -1, 1));
            rightBack.setPower(Range.clip(rB*1, -1, 1));
            liftSyst.setPower(liftSystPower);
            sans.setPower(sansPower);
            hook.setPosition(hookPos);
            grabber.setPosition(grabPos);


            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
//            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftFront.getPower(), );
            telemetry.update();
        }
    }
}
