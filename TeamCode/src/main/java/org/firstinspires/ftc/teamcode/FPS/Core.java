package org.firstinspires.ftc.teamcode.FPS;

public class Core {
    Tick tick = new Tick();
    public double accXInput, accYInput, odoXinput, odoYInput, theta, finalX, finalY, rawX, rawY;
    public void sort(){
        accXInput*=.6;
        accYInput*=.6;
        odoXinput*=.4;
        odoYInput*=.4;
    }
    

    public void run(){
        sort();
        tick.updateStart();
        finalX = Math.sin(theta)*rawX;
        finalY = Math.cos(theta)*rawY;



    }

}
