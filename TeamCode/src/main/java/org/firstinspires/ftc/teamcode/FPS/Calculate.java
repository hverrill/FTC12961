package org.firstinspires.ftc.teamcode.FPS;

public class Calculate {

    public Tick tick = new Tick();
    public Accelerometer acc = new Accelerometer();
    public Odometry odo = new Odometry();

    public double accXInput, accYInput, odoXinput, odoYInput, theta, finalX, finalY, rawX, rawY;

    public void sort(){
        odoXinput = odo.getX()*.5;
        odoYInput = odo.getY()*.5;
        accXInput = acc.getDeltaX()*5;
        accYInput = acc.getDeltaY()*.5;
        finalX = odoXinput + accXInput;
        finalY = odoYInput + accYInput;

    }

    public void run(){
        sort();
        tick.updateStart();
        finalX = Math.sin(theta)*rawX;
        finalY = Math.cos(theta)*rawY;
        tick.updateEnd();
    }
    public int getTPS(){ //Gets the last number of ticks per second
        return tick.getTicksPerSecond();
    }
    public double distance(double x, double y){
        return 2;
    }

}
