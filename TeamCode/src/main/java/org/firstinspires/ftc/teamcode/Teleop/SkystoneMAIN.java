/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.FPS.Drivetrain;
import org.firstinspires.ftc.teamcode.FPS.Odometry;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="CURRENT_DUALCONTROLLER", group="MAIN")
public class SkystoneMAIN extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFront, leftBack, rightFront, rightBack, winch, intakeLeft, intakeRight = null;
    private Servo leftHook, rightHook, grab, turn, push, leftGrab, rightGrab, capstone;
    double gearSpeed = .7;
    double lB, lF, rB, rF;
    int goal;
    boolean winchToggle, capToggle, capDeployed = false;

    Drivetrain mecanum = new Drivetrain();
    //Odometry encoders = new Odometry();
    boolean toggle = false;

    public void processUpdate() {
        mecanum.calculate(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.right_stick_y);
        //encoders.update(mecanum.finaltheta);
        // GEAR SPEED CALCULATIONS :
        if (!(gamepad1.dpad_down | gamepad1.dpad_up) && toggle) {
            toggle = false;
        }
        if (gamepad1.dpad_up && !toggle) {
            gearSpeed += .1;
            toggle = true;
        }
        if (gamepad1.dpad_down && !toggle) {
            gearSpeed -= .1;
            toggle = true;
        }
        gearSpeed = Range.clip(gearSpeed, .2, .9);

        lF = gearSpeed * mecanum.leftfront;
        lB = gearSpeed * mecanum.leftback;
        rF = gearSpeed * mecanum.rightfront;
        rB = gearSpeed * mecanum.rightback;
    }


    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftFront = hardwareMap.get(DcMotor.class, "LF");
        leftBack = hardwareMap.get(DcMotor.class, "LB");
        rightFront = hardwareMap.get(DcMotor.class, "RF");
        rightBack = hardwareMap.get(DcMotor.class, "RB");
        intakeLeft = hardwareMap.get(DcMotor.class, "intakeLeft");
        intakeRight = hardwareMap.get(DcMotor.class, "intakeRight");
        winch = hardwareMap.get(DcMotor.class, "winch");
        leftHook = hardwareMap.get(Servo.class, "leftHook");
        rightHook = hardwareMap.get(Servo.class, "rightHook");
        grab = hardwareMap.get(Servo.class, "grab");
        turn = hardwareMap.get(Servo.class, "turn");
        push = hardwareMap.get(Servo.class, "push");
        leftGrab = hardwareMap.get(Servo.class, "leftGrab");
        rightGrab = hardwareMap.get(Servo.class, "rightGrab");
        capstone = hardwareMap.get(Servo.class, "capstone");


        leftFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.REVERSE);
        winch.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        winch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        winch.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //winchTop.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intakeLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        //encoders.initialize(intakeRight /* Right X */, intakeLeft /* Left X */, winchBottom /* Y */); //BECAUSE THEY STILL DON'T HAVE ENCODERS AS THEIR OWN SENSORS YET FOR SOME REASON
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            processUpdate();
            // Send calculated power to wheels
            if  (gamepad2.right_trigger != 0){
                intakeLeft.setPower(.69);
                intakeRight.setPower(-.69);
            } else if (gamepad2.left_trigger != 0){
                intakeLeft.setPower(-.18);
                intakeRight.setPower(.18);
            } else {
                intakeLeft.setPower(0);
                intakeRight.setPower(0);
            }

            if (gamepad2.y) {
                grab.setPosition(.0);
            } else if (gamepad2.b) {
                grab.setPosition(1);
            }

            if (gamepad2.x) {
                winch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                winch.setPower(.1);
                goal = winch.getCurrentPosition();
                toggle = true;

            } else if (gamepad2.a) {
                winch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                winch.setPower(-.1);
                goal = winch.getCurrentPosition();
                toggle = true;

            } else {
                //winch.setPower(-.0005);
                if (toggle) {
                    winch.setTargetPosition(goal);
                    winch.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    toggle = false;
                }

                winch.setPower(-.0005);
            }

            if (gamepad2.dpad_down) {
                turn.setPosition(.13);
            } else if (gamepad2.dpad_up) {
                turn.setPosition(.79);
            }

            if (gamepad2.left_bumper) {
                push.setPosition(1);
            } else {
                push.setPosition(0);
            }

//            if (gamepad1.y){
//                capToggle = !capToggle;
//            } else {
//                if (capToggle){
//                    capToggle = false;
//                    capDeployed = !capDeployed;
//                }
//            }
//            if (capDeployed){
//                capstone.setPosition(1);
//            } else {
//                capstone.setPosition(0);
//            }
            if (gamepad1.a){
                capstone.setPosition(.35);
            } else if (gamepad1.y){
                capstone.setPosition(0);
            }


            if (gamepad1.left_bumper) {
                leftHook.setPosition(.3);//deployed
                rightHook.setPosition(.7);
            } else {
                leftHook.setPosition(.9); //retracted
                rightHook.setPosition(.1);
            }
            if (gamepad1.left_trigger != 0) {
                leftGrab.setPosition(1); // deployed
                rightGrab.setPosition(0);
            } else {
                leftGrab.setPosition(0.6);
                rightGrab.setPosition(0.25);  //retracted
            }
                leftFront.setPower(Range.clip(lF, -1, 1));
                leftBack.setPower(Range.clip(lB, -1, 1));
                rightFront.setPower(Range.clip(rF, -1, 1));
                rightBack.setPower(Range.clip(rB, -1, 1));

                // Show the elapsed game time and wheel power.
                telemetry.addData("Status", "Run Time: " + runtime.toString());
                //telemetry.addData("X Pos: ", encoders.xDistance);
                //telemetry.addData("Y Pos: ", encoders.yDistance);
//            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftFront.getPower(), );
                telemetry.update();

        }
    }
}
