package org.usfirst.frc.team3859.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm {
	PIDcontrol armPID = new PIDcontrol(0, 0, 0);
	double goalAngle = 0;
	boolean init = false;
	double pointintime = 0;

	enum armPos {
		INTAKE, SWITCHSHOT, SCALESHOT, BACKWARDS, TEST
	}

	public void setUp() {
		Constants.armRight.setInverted(true);
		Constants.armRight.follow(Constants.armLeft);
		Constants.armLeft.setSensorPhase(true);
	}

	// SHOULDER GEAR REDUCTION IS 240:1
	double getPosition() {

		if (init == false) {
			pointintime = ((Constants.armLeft.getSelectedSensorPosition(0) / 240) * 90) / 1024;
			init = true;
		}
		double runningvalue = ((Constants.armLeft.getSelectedSensorPosition(0) / 240) * 90) / 1024;
		double position = runningvalue - pointintime;
		return position;
	}

	// double getPosition() {
	// double position = ((Constants.armLeft.getSelectedSensorPosition(0) / 240) *
	// 90) / 1024;
	// return position;
	// }

	public void set(double value) {
		setUp();
		double P = SmartDashboard.getNumber("P", 0);
		double I = SmartDashboard.getNumber("I", 0);
		// double P = 0.015;
		// double I = .00005;
		double voltage = .18;
		armPID.setPID(P, I, 0);
		double stuff = Math.cos(((Constants.armLeft.getSelectedSensorPosition(0) / 240) * Math.PI) / 2048) * voltage;
		SmartDashboard.putNumber("Stuff", stuff);
		double error = value - getPosition();

		Constants.armLeft.set(ControlMode.PercentOutput, armPID.calculate(error) + stuff);
//		Constants.armLeft.set(ControlMode.PercentOutput, stuff);
//		Constants.armRight.set(ControlMode.PercentOutput, stuff);

	}

	/*
	 * public void set(armPos state) { switch (state) { case INTAKE: set(0); break;
	 * case SWITCHSHOT: set(50); break; case SCALESHOT: set(80); break; case
	 * BACKWARDS: set(0); break; case TEST: double angle =
	 * SmartDashboard.getNumber("Angle", 0); double stuff =
	 * Math.cos(((Constants.armLeft.getSelectedSensorPosition(0) / 240) * Math.PI) /
	 * 2048); // Constants.armLeft.set(ControlMode.PercentOutput, voltage * stuff);
	 * // Constants.armRight.set(ControlMode.PercentOutput, voltage * stuff);
	 * SmartDashboard.putNumber("Stuff", stuff);
	 * 
	 * set(angle);
	 * 
	 * // goalAngle = 30; // error = Math.abs(goalAngle - getPosition()); //
	 * armPID.setPID(0, 0, 0); // armPID.calculate(error);
	 * 
	 * break;
	 * 
	 * } }
	 */
}
