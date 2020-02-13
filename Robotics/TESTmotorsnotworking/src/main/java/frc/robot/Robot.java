/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

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
    RobotMap.timer.reset();
    RobotMap.timer.start();
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    while(RobotMap.joystick.getRawButton(3)){
      System.out.println("Button is being pressed");
    }
    //RobotMap.diffDrive.arcadeDrive(0.4, 0.0);
    
    /*RobotMap.diffDrive.arcadeDrive(
    RobotMap.arcade_forwardController.drive(RobotMap.joystick.getRawAxis(1) * -1),
    RobotMap.arcade_turnController.drive(RobotMap.joystick.getRawAxis(0)));*/
  }

}
