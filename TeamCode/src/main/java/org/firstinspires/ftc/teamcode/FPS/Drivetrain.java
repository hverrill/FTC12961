package org.firstinspires.ftc.teamcode.FPS;

public class Drivetrain {
    public double theta, forwardSpeed, finaltheta, robotSpeed, directionSpeed, leftfront, rightfront, leftback, rightback;
    public void calculate(double xVectorLeft, double yVectorLeft, double xVectorRight, double yVectorRight){
        robotSpeed = Math.sqrt(Math.pow(xVectorLeft, 2) + Math.pow(xVectorLeft, 2));
        theta = Math.atan2(-xVectorLeft, yVectorLeft);
        forwardSpeed = -(yVectorLeft + yVectorRight)/2;
        finaltheta = -theta + (Math.PI/4);
        forwardSpeed = -(yVectorLeft + yVectorRight)/2;
        directionSpeed = xVectorRight*.5;

        leftfront = (robotSpeed * Math.sin(finaltheta) - directionSpeed) + forwardSpeed;
        leftback = (robotSpeed * Math.cos(finaltheta) - directionSpeed) + forwardSpeed;
        rightfront = (robotSpeed * Math.cos(finaltheta) + directionSpeed) + forwardSpeed;
        rightback = (robotSpeed * Math.sin(finaltheta)+ directionSpeed) + forwardSpeed;
    }
}
