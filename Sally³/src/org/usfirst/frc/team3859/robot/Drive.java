package org.usfirst.frc.team3859.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class Drive {
	public void SetUp() {

		Constants.leftfront.setInverted(true);
		Constants.rightmiddle.set(ControlMode.Follower, Constants.rightfront.getDeviceID());
		Constants.rightback.set(ControlMode.Follower, Constants.rightfront.getDeviceID());
		Constants.leftmiddle.set(ControlMode.Follower, Constants.leftfront.getDeviceID());
		Constants.leftback.set(ControlMode.Follower, Constants.leftfront.getDeviceID());

	}
}
