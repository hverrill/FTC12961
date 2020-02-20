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
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
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

@TeleOp(name="DUALCONTROLLER", group="MAIN")
public class SkystoneMAIN extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private Drivetrain robot = new Drivetrain();

    // Declare OpMode members.
    double gearSpeed = .7, fourbarPos = .85, grabberPos;
    double lB, lF, rB, rF;
    int goal, liftGoal = 300;
    double RATIO = (4.8*Math.PI)/1650;
    boolean winchToggle, capToggle, capDeployed = false, foundationToggle, toggle = false, liftToggle, blockGrabToggle = false;

    //Odometry encoders = new Odometry();

    public void processUpdate() {
        robot.calculate(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.right_stick_y);
        // GEAR SPEED CALCULATIONS :
        if ((gamepad1.right_trigger !=0 | gamepad1.left_trigger !=0) && toggle) {
            toggle = false;
        }
        if (gamepad1.right_trigger !=0 && !toggle) {
            gearSpeed += .1;
            toggle = true;
        }
        if (gamepad1.left_trigger !=0 && !toggle) {
            gearSpeed -= .1;
            toggle = true;
        }

        gearSpeed = Range.clip(gearSpeed, .2, .9);
        liftGoal = Range.clip(liftGoal, -900, 0);
        lF = gearSpeed * robot.leftfront;
        lB = gearSpeed * robot.leftback;
        rF = gearSpeed * robot.rightfront;
        rB = gearSpeed * robot.rightback;
    }


    @Override
    public void runOpMode() {
        robot.map(hardwareMap);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
//        robot.resetEncoders();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            processUpdate();

            // Send calculated power to wheels
            if  (gamepad2.right_trigger != 0){
                robot.intakeLeft.setPower(.55);
                robot.intakeRight.setPower(-.55);
            } else if (gamepad2.left_trigger != 0){
                robot.intakeLeft.setPower(-.18);
                robot.intakeRight.setPower(.18);
            } else {
                robot.intakeLeft.setPower(0);
                robot.intakeRight.setPower(0);
            }

            // Lift Code
            if (gamepad2.dpad_up) {
                robot.winchRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.winchLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                robot.winchLeft.setPower(.65);
                robot.winchRight.setPower(.65);
                goal = (int)(robot.winchRight.getCurrentPosition()+robot.winchLeft.getCurrentPosition())/2;


            } else if (gamepad2.dpad_down) {
                robot.winchRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.winchLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.winchLeft.setPower(-.35);
                robot.winchRight.setPower(-.35);
                goal = (int)(robot.winchRight.getCurrentPosition()+robot.winchLeft.getCurrentPosition())/2;


            } else {
                robot.winchRight.setTargetPosition(goal);
                robot.winchLeft.setTargetPosition(goal);
                robot.winchRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.winchLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                if(gamepad2.a){


                    robot.winchRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.winchLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    robot.winchRight.setTargetPosition(0);
                    robot.winchLeft.setTargetPosition(0);


                    robot.winchRight.setPower(-.4);
                    robot.winchLeft.setPower(-.4);

                } else {
                    robot.winchLeft.setPower(0);
                    robot.winchRight.setPower(0);
                }
            }
            telemetry.addData("Right current", robot.winchRight.getCurrentPosition());
            telemetry.addData("Left current", robot.winchLeft.getCurrentPosition());
            telemetry.addData("Right goal", robot.winchRight.getTargetPosition());
            telemetry.addData("Left goal", robot.winchLeft.getTargetPosition());


            //virtual fourbar code
            if(gamepad2.y){
                fourbarPos = .85;
            } else if(gamepad2.b){
                fourbarPos = .97;
            } else if (gamepad2.x){
                fourbarPos = .1;
            }
            robot.fourbarRight.setPosition(fourbarPos);
            robot.fourbarLeft.setPosition(1-fourbarPos);
            if(gamepad1.a){
                robot.capstone.setPosition(1);
            } else {
                robot.capstone.setPosition(0);
            }

            if(gamepad2.left_bumper){
                blockGrabToggle = false;
                robot.blockGrab.setPosition(.25);
            } else if(gamepad2.right_bumper){
                blockGrabToggle = true;
                robot.blockGrab.setPosition(0.05);
            }
            //Foundation Grabbers
            if (gamepad1.right_bumper) {
                robot.leftHook.setPosition(.4);//deployed
                robot.rightHook.setPosition(.6);
            } else if (gamepad1.left_bumper) {
                robot.leftHook.setPosition(1); //retracted
                robot.rightHook.setPosition(0);
            }
            robot.setPower(Range.clip(lF, -1, 1), Range.clip(lB, -1, 1), Range.clip(rF, -1, 1), Range.clip(rB, -1, 1));

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Ru n Time: " + runtime.toString());
            telemetry.addData("Touch", robot.blockToggle.isPressed());
            telemetry.addData("X", robot.odometry.getX());
            telemetry.addData("Y", robot.odometry.getY());


            //telemetry.addData("X Pos: ", encoders.xDistance);
            //telemetry.addData("Y Pos: ", encoders.yDistance);
//          telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftFront.getPower(), );
            telemetry.update();

        }
    }
}
