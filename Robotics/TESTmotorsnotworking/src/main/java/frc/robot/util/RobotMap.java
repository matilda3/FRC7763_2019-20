package frc.robot.util;

import frc.robot.controllers.DriveControl;
//import frc.robot.controllers.Conveyor;
//import frc.robot.sensors.ColourSensor;

import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.*;

/**
 * Class to contain all robot parts. Increases organization and reduces occurances of magic numbers.
 */
public final class RobotMap {

    public static final TalonSRX rampMotor = new TalonSRX(8);//ramp motor
    public static final TalonSRX beavertail = new TalonSRX(7);//beavertail

    private static final WPI_VictorSPX motorFL = new WPI_VictorSPX(0);
    private static final WPI_VictorSPX motorRL = new WPI_VictorSPX(1);
    private static final WPI_VictorSPX motorFR = new WPI_VictorSPX(2);
    private static final WPI_VictorSPX motorRR = new WPI_VictorSPX(3);

    private static SpeedControllerGroup leftDrive = new SpeedControllerGroup(motorFL, motorRL);
    private static SpeedControllerGroup rightDrive = new SpeedControllerGroup(motorFR, motorRR);

    public static final Joystick joystick = new Joystick(0);
    public static final DifferentialDrive diffDrive = new DifferentialDrive(leftDrive, rightDrive);

    
    public static final double POWER = 2;
    public static final double OFFSET = 0.28;
    public static final double DEADZONE = 0.05;
    public static final double CONST_ACCEL = 0.1;
    public static final double PROP_ACCEL = 0.2;
    public static final DriveControl tank_leftController = new DriveControl();
    public static final DriveControl tank_rightController = new DriveControl();
    public static final DriveControl arcade_forwardController = new DriveControl();
    public static final DriveControl arcade_turnController = new DriveControl();


    //servo for basile's thing
    //public static final Servo servo1 = new Servo(2);

    public static boolean arcade = true;

    //timer -- test
    public static Timer timer = new Timer();

    //colours
    public static String colorString = "";

    //ultrasonic
    public static Ultrasonic ultrasonic = new Ultrasonic(9, 8);
    

    public static void init() {

        motorFL.configFactoryDefault();
        motorRL.configFactoryDefault();
        motorFR.configFactoryDefault();
        motorRR.configFactoryDefault();

        //motorFL.setInverted(true);
        //motorRL.setInverted(true);

        //diffDrive.setRightSideInverted(false); // true by default

        //colors
        /*ColourSensor.m_colorMatcher.addColorMatch(ColourSensor.blueTarget);
        ColourSensor.m_colorMatcher.addColorMatch(ColourSensor.greenTarget);
        ColourSensor.m_colorMatcher.addColorMatch(ColourSensor.redTarget);
        ColourSensor.m_colorMatcher.addColorMatch(ColourSensor.yellowTarget);*/
    }

}