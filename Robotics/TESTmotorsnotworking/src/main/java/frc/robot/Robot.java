/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

//import com.revrobotics.ColorMatchResult;

import edu.wpi.first.wpilibj.TimedRobot;
//import edu.wpi.first.wpilibj.util.Color;
//import frc.robot.sensors.ColourSensor;
import frc.robot.util.RobotMap;



/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
  }

  @Override
  public void autonomousInit() {
    RobotMap.ultrasonic.setAutomaticMode(true);
    RobotMap.timer.reset();
    RobotMap.timer.start();
  }

  @Override
  public void autonomousPeriodic() {
    System.out.println(RobotMap.ultrasonic.getRangeMM());
    if(RobotMap.timer.get() < 2.0){
      RobotMap.diffDrive.arcadeDrive(0.5, 0.0);//Drive forward for 2s
    }

    //TEST
    else if(RobotMap.timer.get() > 2.0 && RobotMap.timer.get() < 4.2){
      RobotMap.diffDrive.arcadeDrive(0.5, -0.5);//turn ccw, 90 deg
    }

    if(RobotMap.ultrasonic.getRangeMM() > 2500){
      RobotMap.diffDrive.arcadeDrive(1.0, 0.0);//drive forwards until 250cm from wall
    }else{
      RobotMap.diffDrive.arcadeDrive(0.0, 0.0);
      RobotMap.timer.reset();
      RobotMap.timer.start();
    }

    if(RobotMap.timer.get() < 2.5){
      RobotMap.diffDrive.arcadeDrive(0.5, -0.5);//turn ccw, 90 deg
    }else{
      if(RobotMap.ultrasonic.getRangeMM() > 300){
        RobotMap.diffDrive.arcadeDrive(1.0, 0.0);//drive until 30cm from wall
      }else{
        //NEED to drive 30 cm
        RobotMap.timer.reset();
        RobotMap.timer.start();
        //dump balls
        if(RobotMap.timer.get() > 3.0){
          RobotMap.rampMotor.set(ControlMode.PercentOutput, 0.2);
        }else{
          RobotMap.rampMotor.set(ControlMode.PercentOutput, 0.0);
        }
      }
    }
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    //5
    if(RobotMap.joystick.getRawButtonPressed(5)){
      RobotMap.onOffBeav = !RobotMap.onOffBeav;
      System.out.println("Button 5");
      RobotMap.beavertail.set(ControlMode.PercentOutput, 0.2);
    }else{
      RobotMap.beavertail.set(ControlMode.PercentOutput, 0.0);
    }
    RobotMap.beavertail.set(ControlMode.PercentOutput, 0.0);

    //3
    if(RobotMap.joystick.getRawButtonPressed(3)){
      RobotMap.onOffRamp = !RobotMap.onOffRamp;
      System.out.println("Button 3");
      RobotMap.rampMotor.set(ControlMode.PercentOutput, 0.2);
    }else{
      RobotMap.rampMotor.set(ControlMode.PercentOutput, 0.0);
    }

    //2
    if(RobotMap.joystick.getRawButtonPressed(2)){
      RobotMap.onOffSpoolU = !RobotMap.onOffSpoolU;
      RobotMap.timer.reset();
      RobotMap.timer.start();
      while(RobotMap.timer.get() < 1.0){
        RobotMap.spool.set(ControlMode.PercentOutput, 0.2);
      }
      RobotMap.spool.set(ControlMode.PercentOutput, 0.0);
    }else{
      RobotMap.spool.set(ControlMode.PercentOutput, 0.0);
    }

    //1
    if(RobotMap.joystick.getRawButtonPressed(1)){
      RobotMap.onOffSpoolD = !RobotMap.onOffSpoolD;
      RobotMap.timer.reset();
      RobotMap.timer.start();
      while(RobotMap.timer.get() < 1.0){
        RobotMap.spool.set(ControlMode.PercentOutput, -0.2);
      }
      RobotMap.spool.set(ControlMode.PercentOutput, 0.0);
    }else{
      RobotMap.spool.set(ControlMode.PercentOutput, 0.0);
    }
    
    RobotMap.diffDrive.arcadeDrive(
    RobotMap.arcade_forwardController.drive(RobotMap.joystick.getRawAxis(1) * -1),
    RobotMap.arcade_turnController.drive(RobotMap.joystick.getRawAxis(0)));
  }

}
