package org.usfirst.frc.team3859.robot;

import com.ctre.CANTalon;
import com.ctre.phoenix.*;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.XboxController;

public class Constants {

	// right drive
	static TalonSRX rightfront = new TalonSRX(5);
	static TalonSRX rightmiddle = new TalonSRX(6);
	static TalonSRX rightback = new TalonSRX(7);

	// left drive
	static TalonSRX leftfront = new TalonSRX(1);
	static TalonSRX leftmiddle = new TalonSRX(2);
	static TalonSRX leftback = new TalonSRX(3);
	
	//cool stuff
	static TalonSRX cube = new TalonSRX(8);
	
	//bad stuff
	static TalonSRX climb = new TalonSRX(4);
	
	//eh

	static AHRS navx = new AHRS(SPI.Port.kMXP);
	
	static XboxController Xbox1 = new XboxController(0);
}