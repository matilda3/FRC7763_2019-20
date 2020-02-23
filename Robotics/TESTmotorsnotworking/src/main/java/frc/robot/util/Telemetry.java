package frc.robot.util;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public final class Telemetry {
    //private static ShuffleboardTab DriveControl = Shuffleboard.getTab("Drive Control");
    private static NetworkTableEntry offset, pow, dzn, accCon, accPro, turn_offset, turn_pow, turn_dzn, turn_accCon, turn_accPro, arcade, spd;

    public static void init() {
        offset = Shuffleboard.getTab("Drive Control")
        .add("Offset", RobotMap.OFFSET)
        .withWidget(BuiltInWidgets.kTextView)
        .getEntry();
        pow = Shuffleboard.getTab("Drive Control")
        .add("Power", RobotMap.POWER)
        .withWidget(BuiltInWidgets.kTextView)
        .getEntry();
        dzn = Shuffleboard.getTab("Drive Control")
        .add("Dead Zone", RobotMap.DEADZONE)
        .withWidget(BuiltInWidgets.kTextView)
        .getEntry();
        accCon = Shuffleboard.getTab("Drive Control")
        .add("Constant Accelecation", RobotMap.CONST_ACCEL)
        .withWidget(BuiltInWidgets.kTextView)
        .getEntry();
        accPro = Shuffleboard.getTab("Drive Control")
        .add("Proportional Accelecation", RobotMap.PROP_ACCEL)
        .withWidget(BuiltInWidgets.kTextView)
        .getEntry();
        arcade = Shuffleboard.getTab("Drive Control")
        .add("Tank Drive", RobotMap.arcade)
        .withWidget(BuiltInWidgets.kToggleSwitch)
        .getEntry();
        turn_offset = Shuffleboard.getTab("Drive Control")
        .add("Turn Offset", RobotMap.OFFSET)
        .withWidget(BuiltInWidgets.kTextView)
        .getEntry();
        turn_pow = Shuffleboard.getTab("Drive Control")
        .add("Turn Power", RobotMap.POWER)
        .withWidget(BuiltInWidgets.kTextView)
        .getEntry();
        turn_dzn = Shuffleboard.getTab("Drive Control")
        .add("Turn Dead Zone", RobotMap.DEADZONE)
        .withWidget(BuiltInWidgets.kTextView)
        .getEntry();
        turn_accCon = Shuffleboard.getTab("Drive Control")
        .add("Turn Constant Accelecation", RobotMap.CONST_ACCEL)
        .withWidget(BuiltInWidgets.kTextView)
        .getEntry();
        turn_accPro = Shuffleboard.getTab("Drive Control")
        .add("Turn Proportional Accelecation", RobotMap.PROP_ACCEL)
        .withWidget(BuiltInWidgets.kTextView)
        .getEntry();
        spd = Shuffleboard.getTab("Drive Control")
        .add("Speed Multiplier", RobotMap.SPEED)
        .withWidget(BuiltInWidgets.kTextView)
        .getEntry();


        Shuffleboard.getTab("Information")
        .add("Drive Base", RobotMap.diffDrive)
        .withWidget(BuiltInWidgets.kDifferentialDrive);

        //distance sensor
        Shuffleboard.getTab("Information").add("distance", RobotMap.ultrasonic.getRangeMM()).getEntry();

        
    }

    public static void update() {
        RobotMap.tank_leftController.setParams(
            getPower(), 
            getOffset(), 
            getDeadZone(), 
            getAccConstant(), 
            getAccProportion(),
            spd.getDouble(RobotMap.SPEED)
            );

        RobotMap.tank_rightController.setParams(
            getPower(), 
            getOffset(), 
            getDeadZone(), 
            getAccConstant(), 
            getAccProportion(),
            spd.getDouble(RobotMap.SPEED)
            );

        RobotMap.arcade_forwardController.setParams(
            getPower(), 
            getOffset(), 
            getDeadZone(), 
            getAccConstant(), 
            getAccProportion(),
            spd.getDouble(RobotMap.SPEED)
            );

        RobotMap.arcade_turnController.setParams(
            turn_getPower(), 
            turn_getOffset(), 
            turn_getDeadZone(), 
            turn_getAccConstant(), 
            turn_getAccProportion(),
            spd.getDouble(RobotMap.SPEED)
            );

        RobotMap.arcade = getArcade();        

        accPro.setDouble(Math.pow((RobotMap.joystick.getRawAxis(3) * -1 + 1.1) / 2.33333333333, 2));
        turn_accPro.setDouble(Math.pow((RobotMap.joystick.getRawAxis(3) * -1 + 1.1) / 2.33333333333, 2));
    }
    

    public static double getOffset() {
        return offset.getDouble(RobotMap.OFFSET);
    }

    public static double getPower() {
        return pow.getDouble(RobotMap.POWER);
    }

    public static double getAccConstant() {
        return accCon.getDouble(RobotMap.CONST_ACCEL);
    }

    public static double getAccProportion() {
        return accPro.getDouble(RobotMap.PROP_ACCEL);
    }

    public static double getDeadZone() {
        return dzn.getDouble(RobotMap.DEADZONE);
    }

    public static double turn_getOffset() {
        return turn_offset.getDouble(RobotMap.OFFSET);
    }

    public static double turn_getPower() {
        return turn_pow.getDouble(RobotMap.POWER);
    }

    public static double turn_getAccConstant() {
        return turn_accCon.getDouble(RobotMap.CONST_ACCEL);
    }

    public static double turn_getAccProportion() {
        return turn_accPro.getDouble(RobotMap.PROP_ACCEL);
    }

    public static double turn_getDeadZone() {
        return turn_dzn.getDouble(RobotMap.DEADZONE);
    }

    public static boolean getArcade() {
        return arcade.getBoolean(RobotMap.arcade);
    }
 }