/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util;

//import java.sql.Time;

import edu.wpi.first.wpilibj.Timer;

/**
 * Add your docs here.
 */
public class PIDLoop {
    private double sum, lastTime, lastError, kp, ki, kd, limit;
    private Timer t = new Timer();

    public PIDLoop(double kp, double ki, double kd, double limit) {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.limit = limit;

        sum = 0;
        lastTime = 0;
        t.start();
    }

    public double update(double error) {
        if (Math.abs(sum) < limit) sum += error * (t.get() - lastTime);
        double d = (error - lastError) / (t.get() - lastTime);
        lastTime = t.get();
        lastError = error;
        return kp*error + ki*sum + kd*d;
    }
}
