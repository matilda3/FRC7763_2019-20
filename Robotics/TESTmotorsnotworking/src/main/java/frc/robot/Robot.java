/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

//import com.revrobotics.ColorMatchResult;

import edu.wpi.first.wpilibj.TimedRobot;
//import edu.wpi.first.wpilibj.util.Color;
//import frc.robot.sensors.ColourSensor;
import frc.robot.util.RobotMap;
import frc.robot.util.Telemetry;



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
    new Thread(() -> {
      UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
      camera.setResolution(640, 480);

      CvSink cvSink = CameraServer.getInstance().getVideo();
      CvSource outputStream = CameraServer.getInstance().putVideo("Blur", 640, 480);

      Mat source = new Mat();
      Mat output = new Mat();

      while(!Thread.interrupted()) {
          cvSink.grabFrame(source);
          Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
          outputStream.putFrame(output);
      }
  }).start();
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
      //RobotMap.diffDrive.arcadeDrive(-0.7, 0.0);//Drive forward for 2s
    }

    //TEST
    else if(RobotMap.timer.get() > 2.0 && RobotMap.timer.get() < 3.44){
      RobotMap.diffDrive.arcadeDrive(-0.5, -0.5);//turn ccw, 90 deg
    }

    if(RobotMap.ultrasonic.getRangeMM() > 2500){
      RobotMap.diffDrive.arcadeDrive(1.0, 0.0);//drive forwards until 250cm from wall
    }else{
      RobotMap.diffDrive.arcadeDrive(0.0, 0.0);
      RobotMap.timer.reset();
      RobotMap.timer.start();
    }

    //TEST
    if(RobotMap.timer.get() < 1.44){
      RobotMap.diffDrive.arcadeDrive(-0.5, -0.5);//turn ccw, 90 deg
    }else{
      if(RobotMap.ultrasonic.getRangeMM() > 300){
        RobotMap.diffDrive.arcadeDrive(1.0, 0.0);//drive until 30cm from wall
      }else{
        //NEED to drive 30 cm
        RobotMap.timer.reset();
        RobotMap.timer.start();
        if(RobotMap.timer.get() < 0.5){
      RobotMap.diffDrive.arcadeDrive(0.5, 0.0);
        }else{
          RobotMap.diffDrive.arcadeDrive(0.0, 0.0);
        }
        //dump balls
        //don't actually need timer for rampM, can just GO
        if(RobotMap.timer.get() > 3.0){
          RobotMap.rampMotor.set(ControlMode.PercentOutput, 0.9);
        }else{
          RobotMap.rampMotor.set(ControlMode.PercentOutput, 0.0);
        }
      }
    }
  }

  @Override
  public void teleopInit() {
    RobotMap.intakeSpinning = false;
    RobotMap.intakeUp = true;
    Telemetry.init();
  }

  @Override
  public void teleopPeriodic() {
    Telemetry.update();
    /*U CAN ONLY PUT ONE BUTTON PER MOTOR AND IT'S SHIT
     = NO REVERSE BUTTONS*/

    //button 10 - servo up
    if(RobotMap.joystick.getRawButtonPressed(10)){
      RobotMap.servo1.setAngle(90);
    }else{
      RobotMap.servo1.setAngle(0);
    }

    //button 2 - rhys's toggle intake
    if (RobotMap.joystick.getRawButtonPressed(2)) {
      if (RobotMap.intakeSpinning == false){
        System.out.println("turning on");
        RobotMap.intake.set(ControlMode.PercentOutput, -0.5);
        RobotMap.intakeSpinning = true;
      } else {
        System.out.println("turning off");
        RobotMap.intake.set(ControlMode.PercentOutput, 0.0);
        RobotMap.intakeSpinning = false;
      }
    }



    //button 1 - ramp
    if(RobotMap.joystick.getRawButton(1)){
      RobotMap.rampMotor.set(ControlMode.PercentOutput, 0.5);
    }else{
      RobotMap.rampMotor.set(ControlMode.PercentOutput, 0.0);
    }

    //button 4 - intake up
    if(RobotMap.joystick.getRawButtonPressed(4)) {
      if (RobotMap.intakeUp == true) {
        //System.out.println("intake going down");
        RobotMap.timer.reset();
        RobotMap.timer.start();
        while(RobotMap.timer.get() < 2.0){
          RobotMap.spool.set(ControlMode.PercentOutput, 0.6);
        }
        RobotMap.spool.set(ControlMode.PercentOutput, 0.0);
        RobotMap.intakeUp = false;
      } else{
        //System.out.println("intake going up");
        RobotMap.timer.reset();
        RobotMap.timer.start();
        while(RobotMap.timer.get() < 0.7){
          RobotMap.spool.set(ControlMode.PercentOutput, -0.1);
        }
        RobotMap.spool.set(ControlMode.PercentOutput, 0.0);
        RobotMap.intakeUp = true;
      }
     }

    //regular driving
    RobotMap.diffDrive.arcadeDrive(
    RobotMap.arcade_forwardController.drive(RobotMap.joystick.getRawAxis(1) * -1),
    RobotMap.arcade_turnController.drive(RobotMap.joystick.getRawAxis(0)));
  }

}
