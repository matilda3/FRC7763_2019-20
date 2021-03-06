/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.*;

/**
 * Class to contain all robot parts. Increases organization and reduces occurances of magic numbers.
 */
public final class RobotMap {
    private static WPI_TalonSRX motorFL = new WPI_TalonSRX(1);
    private static WPI_TalonSRX motorRL = new WPI_TalonSRX(2);
    private static WPI_TalonSRX motorFR = new WPI_TalonSRX(3);
    private static WPI_TalonSRX motorRR = new WPI_TalonSRX(4);

    private static SpeedControllerGroup leftDrive = new SpeedControllerGroup(motorFL, motorRL);
    private static SpeedControllerGroup rightDrive = new SpeedControllerGroup(motorFR, motorRR);

    private static UsbCamera camera;

    public static final Joystick joystick = new Joystick(0);
    public static final DifferentialDrive diffDrive = new DifferentialDrive(leftDrive, rightDrive);

    //Adding timer -- test
    public static Timer timer =  new Timer();

    /**
     * TO DO:
     * better defaults
     * shuffleboard adjustability
     */
    public static final double POWER = 2;
    public static final double OFFSET = 0.28;
    public static final double DEADZONE = 0.05;
    public static final double CONST_ACCEL = 0.1;
    public static final double PROP_ACCEL = 0.2;
    public static final double SPEED = 0.5;
    public static final DriveControl tank_leftController = new DriveControl();
    public static final DriveControl tank_rightController = new DriveControl();
    public static final DriveControl arcade_forwardController = new DriveControl();
    public static final DriveControl arcade_turnController = new DriveControl();
    
    public static boolean arcade = true;

    public static void init() {
        camera = CameraServer.getInstance().startAutomaticCapture();
        camera.setResolution(320, 240);
        camera.setFPS(30);

        motorFL.configFactoryDefault();
        motorRL.configFactoryDefault();
        motorFR.configFactoryDefault();
        motorRR.configFactoryDefault();

        //motorFL.setInverted(true);
        //motorRL.setInverted(true);

        //diffDrive.setRightSideInverted(false); // true by default
    }

    public static UsbCamera getCamera() {
        return camera;
    }
}
