package org.usfirst.frc.team3859.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Drive {
	boolean init = false;
	double pointintime = 0;
	double stuff = 0;
	double rotations = 0;
	// public void SetUp() {
	//
	// // Constants.leftfront.setInverted(true);
	// Constants.rightmiddle.set(ControlMode.Follower,
	// Constants.rightfront.getDeviceID());
	// Constants.rightback.set(ControlMode.Follower,
	// Constants.rightfront.getDeviceID());
	// Constants.leftmiddle.set(ControlMode.Follower,
	// Constants.leftfront.getDeviceID());
	// Constants.leftback.set(ControlMode.Follower,
	// Constants.leftfront.getDeviceID());
	//
	// }
	//
	// public void set() {
	// SetUp();
	// Constants.leftfront.set(ControlMode.PercentOutput,
	// Constants.Xbox1.getY(Hand.kLeft));
	// Constants.rightfront.set(ControlMode.PercentOutput,
	// Constants.Xbox1.getY(Hand.kRight));

	// }
	public void SetUp(boolean auto) {

//		Constants.leftmiddle.set(ControlMode.Follower, Constants.leftfront.getDeviceID());
//		Constants.rightmiddle.set(ControlMode.Follower, Constants.rightfront.getDeviceID());
//
//		Constants.leftback.set(ControlMode.Follower, Constants.leftfront.getDeviceID());
//		Constants.rightback.set(ControlMode.Follower, Constants.rightfront.getDeviceID());
		
		Constants.leftmiddle.follow(Constants.leftfront);
		Constants.leftback.follow(Constants.leftfront);

		Constants.rightmiddle.follow(Constants.rightfront);
		Constants.rightback.follow(Constants.rightfront);

		Constants.leftfront.setInverted(true);
		Constants.leftmiddle.setInverted(true);
		Constants.leftback.setInverted(true);

//		Constants.leftfront.setNeutralMode(NeutralMode.Brake);
//		Constants.leftmiddle.setNeutralMode(NeutralMode.Brake);
		if (auto) {
			Constants.leftfront.setNeutralMode(NeutralMode.Brake);
			Constants.leftmiddle.setNeutralMode(NeutralMode.Brake);
			Constants.leftback.setNeutralMode(NeutralMode.Brake);
			Constants.rightfront.setNeutralMode(NeutralMode.Brake);
			Constants.rightmiddle.setNeutralMode(NeutralMode.Brake);
			Constants.rightback.setNeutralMode(NeutralMode.Brake);
		} else {
//			Constants.leftfront.setNeutralMode(NeutralMode.Coast);
//			Constants.leftmiddle.setNeutralMode(NeutralMode.Coast);
//			Constants.leftback.setNeutralMode(NeutralMode.Coast);
//			Constants.rightfront.setNeutralMode(NeutralMode.Coast);
//			Constants.rightmiddle.setNeutralMode(NeutralMode.Coast);
//			Constants.rightback.setNeutralMode(NeutralMode.Coast);
			Constants.leftfront.setNeutralMode(NeutralMode.Brake);
			Constants.leftmiddle.setNeutralMode(NeutralMode.Brake);
			Constants.leftback.setNeutralMode(NeutralMode.Brake);
			Constants.rightfront.setNeutralMode(NeutralMode.Brake);
			Constants.rightmiddle.setNeutralMode(NeutralMode.Brake);
			Constants.rightback.setNeutralMode(NeutralMode.Brake);

		}

	}

	public void setEnc() {
		Constants.rightfront.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
	}

	public double getEncDistance() {
		if (init == false) {
			pointintime = Constants.rightfront.getSelectedSensorPosition(0) * .01256;
			init = true;
		}
		double runningvalue = Constants.rightfront.getSelectedSensorPosition(0) * .01256;
		double distance = runningvalue - pointintime;
		return distance;
	}

	public double getEncRotations(boolean first) {
		if (first == true) {
			if (init == false) {
				stuff = Constants.rightfront.getSelectedSensorPosition(0) / 1000;
				init = true;
			}
			double runningvalue = Constants.rightfront.getSelectedSensorPosition(0) / 1000;
			rotations = runningvalue - stuff;
		} else {
			rotations = (Constants.rightfront.getSelectedSensorPosition(0) / 1000);
		}
		return rotations;
	}

	public void set() {
		SetUp(false);
		Constants.leftfront.set(ControlMode.PercentOutput, Constants.Xbox1.getY(Hand.kLeft));
		Constants.rightfront.set(ControlMode.PercentOutput, Constants.Xbox1.getY(Hand.kRight));

	}
}
