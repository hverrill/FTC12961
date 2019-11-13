
/**
 * Team 12961 Checkmate Robotics: Alex Test Drive
 * Version: BETA
 */

package Depricated;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Demo" +
        "+", group="Checkmate")

/*
Don't uncomment! This registers the OpMode:
@Disabled
 */
@Disabled


public class Demo extends OpMode {

    private DcMotor l = null;
    private DcMotor r = null;
    private ElapsedTime runtime = new ElapsedTime();

    /**
     * One time INIT code:
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        l = hardwareMap.get(DcMotor.class,"l");
        r = hardwareMap.get(DcMotor.class,"r");

        l.setDirection(DcMotor.Direction.REVERSE);
        r.setDirection(DcMotor.Direction.FORWARD);
    }

    /**
     * Looping before PLAY after INIT code:
     */
    @Override
    public void init_loop() {
    }

    //Reset System Time:
    @Override
    public void start() {runtime.reset();}

    /**
     * Looping PLAY code:
     */
    @Override
    public void loop() {

        double leftpow = gamepad1.left_stick_y * 0.7;
        double rightpow = gamepad1.right_stick_y * 0.7;
        l.setPower(leftpow);
        r.setPower(rightpow);

    }
    /**
     * STOP
     */
    @Override
    public void stop() {
    }

}
