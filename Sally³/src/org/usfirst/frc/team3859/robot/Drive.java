package org.usfirst.frc.team3859.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive {
	boolean init = false;
	boolean init1 = false;
	double stuff = 0;
	double rotations = 0;
	double pointintimeright = 0;
	double pointintimeleft = 0;
	double pre;

	public void setUp(boolean auto) {

		Constants.leftMiddle.follow(Constants.leftFront);
		Constants.leftBack.follow(Constants.leftFront);

		Constants.rightMiddle.follow(Constants.rightFront);
		Constants.rightBack.follow(Constants.rightFront);

		Constants.leftFront.setInverted(true);
		Constants.leftMiddle.setInverted(true);
		Constants.leftBack.setInverted(true);

		if (auto) {
			Constants.leftFront.setNeutralMode(NeutralMode.Brake);
			Constants.leftMiddle.setNeutralMode(NeutralMode.Brake);
			Constants.leftBack.setNeutralMode(NeutralMode.Brake);
			Constants.rightFront.setNeutralMode(NeutralMode.Brake);
			Constants.rightMiddle.setNeutralMode(NeutralMode.Brake);
			Constants.rightBack.setNeutralMode(NeutralMode.Brake);
		} else {
			Constants.leftFront.setNeutralMode(NeutralMode.Coast);
			Constants.leftMiddle.setNeutralMode(NeutralMode.Coast);
			Constants.leftBack.setNeutralMode(NeutralMode.Coast);
			Constants.rightFront.setNeutralMode(NeutralMode.Coast);
			Constants.rightMiddle.setNeutralMode(NeutralMode.Coast);
			Constants.rightBack.setNeutralMode(NeutralMode.Coast);
			// Constants.leftFront.setNeutralMode(NeutralMode.Brake);
			// Constants.leftMiddle.setNeutralMode(NeutralMode.Brake);
			// Constants.leftBack.setNeutralMode(NeutralMode.Brake);
			// Constants.rightFront.setNeutralMode(NeutralMode.Brake);
			// Constants.rightMiddle.setNeutralMode(NeutralMode.Brake);
			// Constants.rightBack.setNeutralMode(NeutralMode.Brake);

		}

	}

	public void setEnc(String direction) {
		if (direction == "both") {
			Constants.leftFront.setSensorPhase(true);
			Constants.rightFront.setSensorPhase(true);
		} else if (direction == "left") {
			Constants.leftFront.setSensorPhase(true);
		} else if (direction == "right") {
			Constants.rightFront.setSensorPhase(true);
		}
	}

	public double getRightEncDistance() {
		if (init == false) {
			// pointintimeright = (Constants.rightFront.getSelectedSensorPosition(0)/1000) *
			// Constants.wheelCircumference ;
			// pointintimeright = Constants.rightFront.getSelectedSensorPosition(0) *
			// .02827433;
			pointintimeright = Constants.rightFront.getSelectedSensorPosition(0) * ((.02761165) / 1.5);
			init = true;
		}
		// double runningvalue =
		// (Constants.rightFront.getSelectedSensorPosition(0)/1000) *
		// Constants.wheelCircumference ;
		// double runningvalue = Constants.rightFront.getSelectedSensorPosition(0) *
		// .02827433;
		double runningvalue = Constants.rightFront.getSelectedSensorPosition(0) * ((.02761165) / 1.5);
		double distance = runningvalue - pointintimeright;
		return distance;
	}

	public double getLeftEncDistance() {
		if (init1 == false) {

			// pointintimeleft = (Constants.leftFront.getSelectedSensorPosition(0)/1000) *
			// Constants.wheelCircumference ;
			// pointintimeleft = Constants.leftFront.getSelectedSensorPosition(0)*
			// .02827433;
			pointintimeleft = Constants.leftFront.getSelectedSensorPosition(0) * ((.02761165) / 1.5);
			init1 = true;
		}
		// double runningvalue = (Constants.leftFront.getSelectedSensorPosition(0)/1000)
		// * Constants.wheelCircumference ;
		// double runningvalue = Constants.leftFront.getSelectedSensorPosition(0) *
		// .02827433;
		double runningvalue = Constants.leftFront.getSelectedSensorPosition(0) * ((.02761165) / 1.5);
		double distance = runningvalue - pointintimeleft;
		return distance;
	}

	public double getEncRotations(boolean first) {
		if (first == true) {
			if (init == false) {
				stuff = Constants.rightFront.getSelectedSensorPosition(0) / 1000;
				init = true;
			}
			double runningvalue = Constants.rightFront.getSelectedSensorPosition(0) / 1000;
			rotations = runningvalue - stuff;
		} else {
			rotations = (Constants.rightFront.getSelectedSensorPosition(0) / 1000);
		}
		return rotations;
	}

	public void set() {
		setUp(false);
		Constants.leftFront.set(ControlMode.PercentOutput, Constants.Xbox2.getY(Hand.kLeft));
		Constants.rightFront.set(ControlMode.PercentOutput, Constants.Xbox2.getY(Hand.kRight));

	}

	// WHEEL DRIVE
	double valueW;
	double valueJ;

	public void drive(double valueJ) {
		Constants.leftFront.set(ControlMode.PercentOutput, valueJ);
		Constants.rightFront.set(ControlMode.PercentOutput, valueJ);
	}

	public double leftdrive(double valueJ, double multiplier) {
		double leftdrive = valueJ * multiplier;
		return leftdrive;
	}

	public void Stick() {
		valueW = Constants.DriveWheel.getX();
		valueJ = Constants.Joystick1.getY();

		if (valueJ > 0) {
			Constants.rightFront.set(ControlMode.PercentOutput, valueJ);
			Constants.leftFront.set(ControlMode.PercentOutput, leftdrive(valueJ, 0.9));
		} else if (valueJ < 0) {
			Constants.rightFront.set(ControlMode.PercentOutput, valueJ);
			Constants.leftFront.set(ControlMode.PercentOutput, leftdrive(valueJ, 0.9));
		} else if (valueJ >= 0.5) {
			Constants.rightFront.set(ControlMode.PercentOutput, 0.5);
			Constants.leftFront.set(ControlMode.PercentOutput, leftdrive(0.5, 0.9));
		} else if (valueJ <= -0.5) {
			Constants.rightFront.set(ControlMode.PercentOutput, -0.5);
			Constants.leftFront.set(ControlMode.PercentOutput, leftdrive(-0.5, 0.9));

		}
	}

	public double turnSense(double notPopTart) {
		double sense = .035; // steering sensitivity
		return sense * notPopTart * notPopTart * notPopTart + notPopTart * (1 - sense);
	}

	public double inverse(double start) {
		double inverse = .1;
		return (start) * inverse + start;
	}

	public void move(double speed, double turn) {
		setUp(false);
		// double rightSide = -(inverse(speed) - (inverse(speed) * turnSense(turn));
		double rightSide = -(inverse(speed) - (inverse(speed) * turnSense(turn)));
		double leftSide = inverse(speed) + (inverse(speed) * turnSense(turn));

		Constants.rightFront.set(ControlMode.PercentOutput, rightSide);
		Constants.leftFront.set(ControlMode.PercentOutput, leftSide);

	}

	public void spinspot(double speed) {

		Constants.rightFront.set(ControlMode.PercentOutput, speed);
		Constants.leftFront.set(ControlMode.PercentOutput, speed);

		// Constants.rightFront.getSelectedSensorVelocity(0);

	}

	public void turn(double speed) {
		Constants.rightFront.set(ControlMode.PercentOutput, speed);
		Constants.leftFront.set(ControlMode.PercentOutput, speed);
		Constants.leftFront.set(ControlMode.PercentOutput, speed);
		Constants.rightFront.set(ControlMode.PercentOutput, speed);
	}

	public double TurnSense(double Ptart) {
		double carp;
		carp = .1 * Ptart * Ptart * Ptart + Ptart * (1 - .1);
		return carp;
	}

	public double Inverse(double Sstart) {
		double inert;
		inert = (Sstart - pre) * SmartDashboard.getNumber("Inverse", .25) + Sstart;
		pre = Sstart;
		return inert;
	}

	public void point(double turn) {
		Constants.rightFront.set(ControlMode.PercentOutput, -turn);
		Constants.leftFront.set(ControlMode.PercentOutput, -turn);
		Constants.rightFront.set(ControlMode.PercentOutput, -turn);
		Constants.leftFront.set(ControlMode.PercentOutput, -turn);
	}

	public void movewheel(double speed, double turn) {
		Constants.rightFront.set(ControlMode.PercentOutput, -(Inverse(speed) - (Inverse(speed) * TurnSense(turn))));
		Constants.leftFront.set(ControlMode.PercentOutput, Inverse(speed) + (Inverse(speed) * TurnSense(turn)));
		Constants.rightFront.set(ControlMode.PercentOutput, -(Inverse(speed) - (Inverse(speed) * TurnSense(turn))));
		Constants.leftFront.set(ControlMode.PercentOutput, Inverse(speed) + (Inverse(speed) * TurnSense(turn)));
	}
}
