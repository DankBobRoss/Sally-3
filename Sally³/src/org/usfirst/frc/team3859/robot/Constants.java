package org.usfirst.frc.team3859.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.XboxController;

public class Constants {

	// right drive
	static TalonSRX rightfront = new TalonSRX(6);
	static VictorSPX rightmiddle = new VictorSPX(7);
	static VictorSPX rightback = new VictorSPX(8);

	// left drive
	static TalonSRX leftfront = new TalonSRX(1);
	static VictorSPX leftmiddle = new VictorSPX(2);
	static VictorSPX leftback = new VictorSPX(3);

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
	static DoubleSolenoid shootPneumatic = new DoubleSolenoid(5, 3);
	

	static DoubleSolenoid PTO = new DoubleSolenoid(0, 4);

	// eh

	static AHRS navx = new AHRS(SPI.Port.kMXP);

	static XboxController Xbox1 = new XboxController(0);

}