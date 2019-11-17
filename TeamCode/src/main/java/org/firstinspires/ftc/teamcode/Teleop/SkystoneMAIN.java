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
    private DcMotor leftFront, leftBack, rightFront, rightBack, winchTop, winchBottom, intakeLeft, intakeRight = null;
    private Servo leftHook, rightHook, grab, turn;
    public double rX, rY, lX, lY, throttle, robotSpeed, theta, finaltheta, directionSpeed, forwardSpeed, gearSpeed = .7;
    public double lB, lF, rB, rF;
    boolean toggle = false;

    public void processUpdate(){
        rY = gamepad1.right_stick_y;
        lX = gamepad1.left_stick_x;
        lY = gamepad1.left_stick_y;
        rX = gamepad1.right_stick_x;
        throttle = gamepad1.right_trigger;
        // RUN CALCULATIONS :
        robotSpeed = Math.sqrt(Math.pow(lX, 2) + Math.pow(lX, 2));
        theta = Math.atan2(-lX, lY);
        directionSpeed = rX*.5;
        forwardSpeed = -(rY + lY)/2;
        finaltheta = -theta + (Math.PI/4);

        // GEAR SPEED CALCULATIONS :
        if(gamepad1.dpad_up && toggle){
            gearSpeed += .05;
            toggle = false;
        } else if (gamepad1.dpad_down && toggle){
            gearSpeed -= .05;
            toggle = false;

        } else if (!(gamepad1.dpad_down || gamepad1.dpad_up) && !toggle) {
            toggle = true;
        }
        Range.clip(gearSpeed, .2, .9);

        lF = gearSpeed * robotSpeed * Math.sin(finaltheta) - directionSpeed + forwardSpeed;
        lB = gearSpeed * robotSpeed * Math.cos(finaltheta) - directionSpeed + forwardSpeed;
        rF = gearSpeed * robotSpeed * Math.cos(finaltheta) + directionSpeed + forwardSpeed;
        rB = gearSpeed * robotSpeed * Math.sin(finaltheta) + directionSpeed + forwardSpeed;
    }


    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftFront  = hardwareMap.get(DcMotor.class, "LF");
        leftBack = hardwareMap.get(DcMotor.class, "LB");
        rightFront  = hardwareMap.get(DcMotor.class, "RF");
        rightBack = hardwareMap.get(DcMotor.class, "RB");
        intakeLeft = hardwareMap.get(DcMotor.class, "intakeLeft");
        intakeRight = hardwareMap.get(DcMotor.class, "intakeRight");
        winchBottom = hardwareMap.get(DcMotor.class, "winchBottom");
        winchTop = hardwareMap.get(DcMotor.class, "winchTop");
        leftHook = hardwareMap.get(Servo.class, "leftHook");
        rightHook = hardwareMap.get(Servo.class, "rightHook");
        grab = hardwareMap.get(Servo.class, "grab");
        turn = hardwareMap.get(Servo.class, "turn");


        leftFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.REVERSE);

        winchBottom.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        winchTop.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            processUpdate();
            // Send calculated power to wheels

            intakeLeft.setPower(gamepad2.left_trigger);
            intakeRight.setPower(-gamepad2.left_trigger);

            intakeLeft.setPower(-gamepad2.right_trigger*.3);
            intakeRight.setPower(gamepad2.right_trigger*.3);

            if(gamepad2.y){
                grab.setPosition(0);
            } else if (gamepad2.b){
                grab.setPosition(1);
            }

            if(gamepad2.x){
                winchBottom.setPower(.5);
                winchTop.setPower(.5);
            } else if(gamepad2.a){
                winchBottom.setPower(-.3);
                winchTop.setPower(-.3);
            } else {
                winchBottom.setPower(0);
                winchTop.setPower(0);
            }

            if(gamepad2.dpad_down){
                turn.setPosition(0);
            } else if (gamepad2.dpad_up){
                turn.setPosition(.8);
            }





            if(gamepad1.left_bumper){
                leftHook.setPosition(.3);//deployed
                rightHook.setPosition(.7);
            } else {
                leftHook.setPosition(.9); //retracted
                rightHook.setPosition(.1);
            }



            leftFront.setPower(Range.clip(lF, -1, 1));
            leftBack.setPower(Range.clip(lB, -1, 1));
            rightFront.setPower(Range.clip(rF, -1, 1));
            rightBack.setPower(Range.clip(rB, -1, 1));

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
//            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftFront.getPower(), );
            telemetry.update();
        }
    }
}
