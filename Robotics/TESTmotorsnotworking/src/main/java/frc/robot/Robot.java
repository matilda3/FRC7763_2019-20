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

import com.revrobotics.ColorMatchResult;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.sensors.ColourSensor;
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
    int count = 1;
    System.out.println(RobotMap.ultrasonic.getRangeMM());
    if(RobotMap.timer.get() < 2.0 && count == 1){
      RobotMap.diffDrive.arcadeDrive(-0.4, 0.0);//Drive forward for 2s
      System.out.println("forward 1sr");
      count++;
    }
    else if(RobotMap.timer.get() > 2.0 && RobotMap.timer.get() < 3.44 && count == 2){
      RobotMap.diffDrive.arcadeDrive(-0.5, -0.5);//turn ccw, 90 deg
      System.out.println("Turn towards wall");
      count++;
    }
    else if(RobotMap.timer.get() > 3.44 && RobotMap.ultrasonic.getRangeMM() > 2500 && count == 3){
      RobotMap.diffDrive.arcadeDrive(0.5, 0.0);//drive forwards until 250cm from wall
      System.out.println("Drive to wall");
      count++;
    }
    else if(RobotMap.ultrasonic.getRangeMM() < 2400 && count == 4){
      RobotMap.timer.reset();
      RobotMap.timer.start();
      if(RobotMap.timer.get() < 1.44){
        RobotMap.diffDrive.arcadeDrive(-0.5, -0.5);//90 deg turn ccw
        System.out.println("Turn to goal");
      }
      else if(RobotMap.timer.get() > 1.44 && RobotMap.ultrasonic.getRangeMM() > 300){
        RobotMap.diffDrive.arcadeDrive(0.5, 0.0);
        System.out.println("Drive to goal");
      }
      else{
        RobotMap.diffDrive.arcadeDrive(0.0, 0.0);
        System.out.println("Stop and balls");
        RobotMap.rampMotor.set(ControlMode.PercentOutput, 0.5);
      }
    }
    else{
      RobotMap.diffDrive.arcadeDrive(0.0, 0.0);
      RobotMap.rampMotor.set(ControlMode.PercentOutput, 0.5);
      System.out.println("END");
    }
  }

  @Override
  public void teleopInit() {
    RobotMap.intakeSpinning = false;
    RobotMap.intakeUp = true;
    RobotMap.servo0 = true;
    Telemetry.init();
  }

  @Override
  public void teleopPeriodic() {
    Telemetry.update();
    System.out.println(RobotMap.colorString);
    RobotMap.servo1.setAngle(90);
    /*U CAN ONLY PUT ONE BUTTON PER MOTOR AND IT'S SHIT
     = NO REVERSE BUTTONS*/

    //button 10 - servo up
    /*if(RobotMap.joystick.getRawButtonPressed(10)){
      if(RobotMap.servo0){
        RobotMap.servo1.setAngle(90);
        RobotMap.servo0 = false;
      }else{
        RobotMap.servo1.setAngle(0);
        RobotMap.servo0 = true;
      }
    }*/
    if(RobotMap.joystick.getRawButton(10)){
      RobotMap.servo1.setAngle(0);
    }
    if(RobotMap.joystick.getRawButton(7)){
      System.out.println("angle" + RobotMap.servo1.getAngle());
    }

    //button 12 - color spinner motor
    if(RobotMap.joystick.getRawButton(12)){
      //System.out.println("press 9");
      RobotMap.spinner.set(ControlMode.PercentOutput, 0.2);
    }else{
      RobotMap.spinner.set(ControlMode.PercentOutput, 0.0);
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
