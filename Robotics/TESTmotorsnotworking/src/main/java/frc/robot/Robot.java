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
  }

  @Override
  public void autonomousPeriodic() {
    System.out.println(RobotMap.ultrasonic.getRangeMM());
    if(RobotMap.ultrasonic.getRangeMM() > 500){
      RobotMap.beavertail.set(ControlMode.PercentOutput, 0.1);
    }else{
      RobotMap.beavertail.set(ControlMode.PercentOutput, 0.0);
    }
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    while(RobotMap.joystick.getRawButton(5)){
      RobotMap.timer.reset();
      RobotMap.timer.start();
      System.out.println("Button 5");
      while(RobotMap.timer.get() < 0.763){
        RobotMap.beavertail.set(ControlMode.PercentOutput, 0.1);
        System.out.println("motor should move for 1 sec");
      }
    }
    RobotMap.beavertail.set(ControlMode.PercentOutput, 0.0);
    if(RobotMap.joystick.getRawButton(3)){
      System.out.println("Button 3");
      //RobotMap.rampMotor.set(ControlMode.PercentOutput, 0.2);
    }//else{
      //RobotMap.rampMotor.set(ControlMode.PercentOutput, 0.0);
    //}
    //RobotMap.diffDrive.arcadeDrive(0.4, 0.0);
    
    /*RobotMap.diffDrive.arcadeDrive(
    RobotMap.arcade_forwardController.drive(RobotMap.joystick.getRawAxis(1) * -1),
    RobotMap.arcade_turnController.drive(RobotMap.joystick.getRawAxis(0)));*/
  }

}
