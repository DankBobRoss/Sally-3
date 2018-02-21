package org.usfirst.frc.team3859.robot;

import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Joystick;

public class Constants {

	static Compressor c = new Compressor(0);

	// INTAKE
	enum position {
		INTAKE, SCORE_HARD, SCORE_MEDIUM, DISABLE, DEJAM;
	}

	enum pnuematic {
		kForward, kReverse;
	}

	// ARM

	enum armPos {
		INTAKE, SWITCHSHOT, BACKSHOT, TEST, STARTINGCONFIG;
	}

	// right drive
	static TalonSRX rightFront = new TalonSRX(6);
	static VictorSPX rightMiddle = new VictorSPX(7);
	static VictorSPX rightBack = new VictorSPX(8);

	// left drive
	static TalonSRX leftFront = new TalonSRX(1);
	static VictorSPX leftMiddle = new VictorSPX(2);
	static VictorSPX leftBack = new VictorSPX(3);

	// cool stuff
	static VictorSPX cubeLeft = new VictorSPX(5);
	static VictorSPX cubeRight = new VictorSPX(10);
	//
	static TalonSRX armLeft = new TalonSRX(9);
	static VictorSPX armRight = new VictorSPX(4);
	//
	static VictorSPX roller = new VictorSPX(11);
	//
	static DoubleSolenoid cubePneumatic = new DoubleSolenoid(2, 1);
	// static DoubleSolenoid shootPneumatic0 = new DoubleSolenoid(3,5);
	// static DoubleSolenoid shootPneumatic1 = new DoubleSolenoid(7,6);
	static Solenoid superPunch0 = new Solenoid(3);
	static Solenoid superPunch1 = new Solenoid(5);
	static Solenoid superPunch2 = new Solenoid(6);
	static Solenoid superPunch3 = new Solenoid(7);

	/**
	 * ENGAGED = REVERSE; DISENGAGED = FORWARD;
	 */
	static DoubleSolenoid PTO = new DoubleSolenoid(0, 4);

	// eh

	static AHRS navx = new AHRS(SPI.Port.kMXP);
	static AnalogInput sharp = new AnalogInput(0);
	static DigitalInput limitSwitch = new DigitalInput(9);

	static SensorCollection armSensor = new SensorCollection(Constants.armLeft);
	
	static SensorCollection rightEncSensor = new SensorCollection(Constants.rightFront);
	static SensorCollection leftEncSensor = new SensorCollection(Constants.leftFront);

	static XboxController xbox1 = new XboxController(2);
	static XboxController xbox2 = new XboxController(3);

	static Joystick joystick1 = new Joystick(0);
	static Joystick driveWheel = new Joystick(1);

	// VARIABLES

	static double wheelDiameter = 6;
	static double wheelCircumference = (wheelDiameter) * Math.PI;

	static double intake = 0;
	static double switchShot = 45;
	static double backShot = 90;

}