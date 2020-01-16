/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/**
 * @author FRC Team 7763 Carrborobotics
 */

package frc.robot;

import frc.robot.util.RobotMap;
import frc.robot.util.Telemetry;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {

  @Override
  public void robotInit() {
    RobotMap.init();
    Telemetry.init();
  }
  @Override
  public void autonomousInit()  {//ADD more timers? or use while?
    RobotMap.timer.reset();
    RobotMap.timer.start();
    //int range = RobotMap.distSensor.
  }

  @Override
  public void autonomousPeriodic() {
    //competition autonomous
    if(RobotMap.timer.get() < 15.0){
      //go forward for 1 second - may change
      while(RobotMap.timer.get() < 1.0){
        RobotMap.diffDrive.arcadeDrive(-0.5, 0.0);//ADD numbers for speed/rot
      }
      //turns it 90 degrees. CHECK direction -/+
      while(RobotMap.timer.get() < 2.0 && RobotMap.timer.get() > 1.0){
        RobotMap.diffDrive.arcadeDrive(-0.7, -0.5);
      }
      //middle of hole is ca 250cm away from the wall (est.)
      while(RobotMap.distSensor.read() > 2.5){
        //also PLACEHOLDER we can't test speed / distance yet
        //drive forward until 250 cm from wall
        RobotMap.diffDrive.arcadeDrive(-0.5, 0.0);
      }
      //RobotMap.DiffDrive.arcadeDrive()
    }

    RobotMap.diffDrive.arcadeDrive(0.0, 0.0);
  }

  @Override
  public void teleopInit() {

  }

  @Override
  public void teleopPeriodic() {
    Telemetry.update();
    if (!RobotMap.arcade) {
      RobotMap.diffDrive.tankDrive(
        RobotMap.tank_leftController.drive(RobotMap.joystick.getRawAxis(1)), 
        RobotMap.tank_rightController.drive(RobotMap.joystick.getRawAxis(5))
      );
    } else {
      RobotMap.diffDrive.arcadeDrive(
        RobotMap.arcade_forwardController.drive(RobotMap.joystick.getRawAxis(1) * -1),
        RobotMap.arcade_turnController.drive(RobotMap.joystick.getRawAxis(0)));
    }
    RobotMap.liftMotor.set(RobotMap.liftController.update(RobotMap.joystick.getRawButton(0)));
    //RobotMap.conveyorMotor.set(RobotMap.conveyorController.update(RobotMap.joystick.getRawButton(2)));//button 2 conveyor
  }

  @Override
  public void disabledInit() {
    //RobotMap.getCamera().close();
  }
}
