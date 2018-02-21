package org.usfirst.frc.team3859.robot;

import org.usfirst.frc.team3859.robot.Constants.armPos;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm {
	PIDcontrol armPID = new PIDcontrol(0, 0, 0);
	double goalAngle = 0;
	boolean init = false;
	double pointintime = 0;
	double error = 0;
	boolean done = false;
	double P = 0;
	double I = 0;

	public void setUp() {
		Constants.armRight.setInverted(true);
		Constants.armRight.follow(Constants.armLeft);
		Constants.armLeft.setSensorPhase(true);
	}
	// public void setUp() {
	// Constants.armRight.setInverted(true);
	// Constants.armRight.follow(Constants.armLeft);
	// Constants
	// }

	// SHOULDER GEAR REDUCTION IS 240:1
	double getPositionBLAH() {

		if (init == false) {
			pointintime = ((Constants.armLeft.getSelectedSensorPosition(0) / 240) * 90) / 1024;
			init = true;
		}
		double runningvalue = ((Constants.armLeft.getSelectedSensorPosition(0) / 240) * 90) / 1024;
		double position = runningvalue - pointintime;
		SmartDashboard.putNumber("Position", position);
		return position;
	}

	// double getPosition() {
	// double position = ((Constants.armLeft.getSelectedSensorPosition(0) / 240) *
	// 90) / 1024;
	// SmartDashboard.putNumber("Position", position);
	// return position;
	// }

	double getPosition() {
		setUp();
		double position = ((Constants.armSensor.getQuadraturePosition() / 240) * 90) / 1024;
		SmartDashboard.putNumber("Position", position);
		return position;
	}

	// double getPosition() {
	// double position = ((Constants.armLeft.getSelectedSensorPosition(0) / 240) *
	// 90) / 1024;
	// return position;
	// }

	public void set(double value) {
		// double P = SmartDashboard.getNumber("P", 0);
		// double I = SmartDashboard.getNumber("I", 0);
		double voltage = .14;
		double stuff = Math.cos(((Constants.armLeft.getSelectedSensorPosition(0) / 240) * Math.PI) / 2048) * voltage;
		error = value - (-getPosition());
		P = 0.015;
		I = .0000501;
		armPID.setPID(P, I, 0);

		if (error >= 0) {
			Constants.armLeft.set(ControlMode.PercentOutput, armPID.calculate(error) + stuff);
		} else if (error < 0) {
			Constants.armLeft.set(ControlMode.PercentOutput, (armPID.calculate(error) * .75) + stuff);
		}
		
		
		if (error < 4) {
			done = true;
		} else if (error >= 4) {
			done = false;
		}
		// Constants.armLeft.set(ControlMode.PercentOutput, stuff);

	}

	public void set(armPos state) {
		switch (state) {
		case INTAKE:
			SmartDashboard.putNumber("Angle", 0);
			break;
		case SWITCHSHOT:
			SmartDashboard.putNumber("Angle", 45);
			break;
		case BACKSHOT:
			SmartDashboard.putNumber("Angle", 105);
			break;
		case TEST:
			double angle = SmartDashboard.getNumber("Angle", 0);
			double stuff = Math.cos(((Constants.armLeft.getSelectedSensorPosition(0) / 240) * Math.PI) / 2048); // Constants.armLeft.set(ControlMode.PercentOutput,
																												// voltage
																												// *
																												// stuff);
			// Constants.armRight.set(ControlMode.PercentOutput, voltage * stuff);
			SmartDashboard.putNumber("Stuff", stuff);

			set(angle);

			// goalAngle = 30; // error = Math.abs(goalAngle - getPosition()); //
			armPID.setPID(0, 0, 0); // armPID.calculate(error);

			break;

		}
		// double angle = SmartDashboard.getNumber("Angle", 0);
		// set(angle);
	}

}
