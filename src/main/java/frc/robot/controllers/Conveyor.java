/*package frc.robot.controllers;

import edu.wpi.first.wpilibj.Timer;


public class Conveyor{
    private static Timer time = new Timer();
    private boolean state = false;  // true == up unless inverted
    //private boolean inverted = false;
    private boolean prevButton = false;

    public Conveyor(){
        time.start();
    }
    /*public Conveyor(boolean inverted){
        this.inverted = inverted;
        time.start();
    }

    public double update(boolean btn) {  // returns value to be fed to motor controller
        if (btn && !prevButton && time.get() > 0.5) {
            state = !state;
            time.reset();
            time.start();
        }
        prevButton = btn;

        //boolean on = inverted ? state : !state;
        return state ? 1.0 : -1.0;
    }
}
*/