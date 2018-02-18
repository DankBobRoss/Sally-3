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
		Constants.armRight.follow(Constants.armLeft);
		Constants.armLeft.setSensorPhase(true);
	}

	public double getEncAngle(boolean gear) {

		double distance = 0;
		if (gear) {
			if (init == false) {
				pointintime = (Constants.armLeft.getSelectedSensorPosition(0) * 2.25);

				// pointintime = (arm.getSelectedSensorPosition(0) / 1000);
				// pointintime = (pointintime * 360) / 2.75;
				init = true;
			}
			double runningvalue = (Constants.armLeft.getSelectedSensorPosition(0) * 2.25);

			// double runningvalue = (arm.getSelectedSensorPosition(0) / 1000);
			// runningvalue = (runningvalue * 360) / 2.75;
			distance = runningvalue - pointintime;
		} else {
			if (init == false) {
				pointintime = (Constants.armLeft.getSelectedSensorPosition(0) * .36);

				// pointintime = (arm.getSelectedSensorPosition(0) / 1000);
				// pointintime = (pointintime * 360) / 2.75;
				init = true;
			}
			double runningvalue = (Constants.armLeft.getSelectedSensorPosition(0) * .36);

			// double runningvalue = (arm.getSelectedSensorPosition(0) / 1000);
			// runningvalue = (runningvalue * 360) / 2.75;
			distance = runningvalue - pointintime;

		}
		return distance;
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
		double P = 0.015;
		double I = .00005;
		double voltage = .16;
		armPID.setPID(P, I, 0);
		double stuff = Math.cos(((Constants.armLeft.getSelectedSensorPosition(0) / 240) * Math.PI) / 2048) * voltage;
		double error = value - getPosition();

		Constants.armLeft.set(ControlMode.PercentOutput, armPID.calculate(error) + stuff);
		Constants.armRight.set(ControlMode.PercentOutput, -armPID.calculate(error) - stuff);
		// Constants.armLeft.set(ControlMode.PercentOutput, stuff);
		// Constants.armRight.set(ControlMode.PercentOutput, stuff);
		// Constants.armLeft.set(ControlMode.PercentOutput, armPID.calculate(error));
		// Constants.armRight.set(ControlMode.PercentOutput, armPID.calculate(error));
		SmartDashboard.putNumber("Stuff", stuff);
		SmartDashboard.putNumber("Position", getPosition());
		SmartDashboard.putNumber("Error", error);

	}

	public void set(armPos state) {
		double error = 0;
		switch (state) {
		case INTAKE:
			set(0);
			break;
		case SWITCHSHOT:
			set(50);
			break;
		case SCALESHOT:
			set(80);
			break;
		case BACKWARDS:
			set(0);
			break;
		case TEST:
			// if (Constants.Xbox1.getBumper(Hand.kLeft)) {
			// Constants.armLeft.set(ControlMode.PercentOutput, .2);
			// Constants.armRight.set(ControlMode.PercentOutput, .2);
			// } else if (Constants.Xbox1.getBumper(Hand.kRight)) {
			// Constants.armLeft.set(ControlMode.PercentOutput, -.2);
			// Constants.armRight.set(ControlMode.PercentOutput, -.2);
			// } else {
			// Constants.armLeft.set(ControlMode.PercentOutput, 0);
			// Constants.armRight.set(ControlMode.PercentOutput, 0);
			//
			// }
			double angle = SmartDashboard.getNumber("Angle", 0);
			double stuff = Math.cos(((Constants.armLeft.getSelectedSensorPosition(0) / 240) * Math.PI) / 2048);
			// Constants.armLeft.set(ControlMode.PercentOutput, voltage * stuff);
			// Constants.armRight.set(ControlMode.PercentOutput, voltage * stuff);
			SmartDashboard.putNumber("Stuff", stuff);

			set(angle);

			// goalAngle = 30;
			// error = Math.abs(goalAngle - getPosition());
			// armPID.setPID(0, 0, 0);
			// armPID.calculate(error);

			break;

		}
	}
}
