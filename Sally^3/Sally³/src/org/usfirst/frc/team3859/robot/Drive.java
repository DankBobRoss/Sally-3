package org.usfirst.frc.team3859.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive {
	boolean init = false;
	boolean leftEncInit = false;
	boolean rightEncInit = false;
	boolean init1 = false;
	double stuff = 0;
	double rotations = 0;
	double pointintimeright = 0;
	double pointintimeleft = 0;
	double pre;
	double valueW = Constants.driveWheel.getX();
	double valueJ = Constants.joystick1.getY();

	public void setUp(boolean auto) {

		Constants.leftMiddle.follow(Constants.leftFront);
		Constants.leftBack.follow(Constants.leftFront);

		Constants.rightMiddle.follow(Constants.rightFront);
		Constants.rightBack.follow(Constants.rightFront);

		Constants.leftFront.setInverted(true);
		Constants.leftMiddle.setInverted(true);
		Constants.leftBack.setInverted(true);
		Constants.rightFront.setInverted(true);
		Constants.rightMiddle.setInverted(true);
		Constants.rightBack.setInverted(true);

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
		if (rightEncInit == false) {
			Constants.rightEncSensor.setQuadraturePosition(0, 200);
			setEnc("right");
			rightEncInit = true;
		}
		double distance = Constants.rightFront.getSelectedSensorPosition(0) * (.01885);
		return distance;
	}

	public double getLeftEncDistance() {
		if (leftEncInit == false) {
			Constants.rightEncSensor.setQuadraturePosition(0, 200);
			setEnc("left");
			leftEncInit = true;
		}
		double distance = Constants.rightFront.getSelectedSensorPosition(0) * (.01885);
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
		Constants.leftFront.set(ControlMode.PercentOutput, Constants.xbox2.getY(Hand.kLeft));
		Constants.rightFront.set(ControlMode.PercentOutput, Constants.xbox2.getY(Hand.kRight));

	}

	// Function to set left and right drive speeds
	public void drive(double valueJ) {
		rightDrive(valueJ);
		leftDrive(valueJ);
	}

	////////////////////////////////////////////////////////////////////
	// public void drive(double valueJ) {
	// Constants.leftFront.set(ControlMode.PercentOutput, valueJ);
	// Constants.rightFront.set(ControlMode.PercentOutput, valueJ);
	// }
	// public void leftdrive(double valueJ) {
	// Constants.leftFront.set(ControlMode.PercentOutput, valueJ);
	// }
	// public void rightderive(double valueJ) {
	// Constants.rightFront.set(ControlMode.PercentOutput, valueJ);
	// }
	// public double leftdrive (double valueJ, double multiplier) {
	// double leftdrive = valueJ*multiplier;
	// return leftdrive;
	// }
	// Function to set right drive speed
	public void rightDrive(double speed) {
		rightDrive(speed);
	}

	// Function to set left drive speed
	public void leftDrive(double speed) {
		leftSet(speed);
	}

	// Function to set left drive speed and apply a multiplier
	public void leftDrive(double speed, double multiplier) {
		double newSpeed = speed * multiplier;
		leftSet(newSpeed);
	}

	// Function to set right drive speed and apply a multiplier
	public void rightDrive(double speed, double multiplier) {
		double newSpeed = speed * multiplier;
		leftSet(newSpeed);
	}

	// Functions to assign speed to CAN talon associated with drive motors

	////////////////////////////////////////////////////////////////////
	public void leftSet(double valueJ) {
		Constants.leftFront.set(ControlMode.PercentOutput, valueJ);
	}

	public void rightSet(double valueJ) {
		Constants.rightFront.set(ControlMode.PercentOutput, valueJ);
	}
	////////////////////////////////////////////////////////////////////

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
		double speedd;

		if (speed >= 0.5) {
			speedd = .5;
		} else if (speed <= -0.5) {
			speedd = -.5;
		} else {
			speedd = speed;
		}

		// double rightSide = -(inverse(speed) - (inverse(speed) * turnSense(turn));
		double rightSide = -(inverse(speedd) - (inverse(speedd) * turnSense(turn)));
		double leftSide = inverse(speedd) + (inverse(speedd) * turnSense(turn));

		Constants.rightFront.set(ControlMode.PercentOutput, rightSide);
		Constants.leftFront.set(ControlMode.PercentOutput, leftSide);

	}

	public void spinspot(double speed) {

		Constants.rightFront.set(ControlMode.PercentOutput, speed);
		Constants.leftFront.set(ControlMode.PercentOutput, speed);

		// Constants.rightFront.getSelectedSensorVelocity(0);

	}
}
