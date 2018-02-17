package org.usfirst.frc.team3859.robot;

import org.usfirst.frc.team3859.robot.Cube.position;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {
	Drive drive = new Drive();
	Cube cube = new Cube();
	Arm arm = new Arm();
	boolean ptoInit = false;
	boolean cubeInit = false;

	boolean toggle = true;
	boolean pnueToggle = true;
	boolean armToggle = true;

	boolean pnue = false;
	boolean belt = false;
	boolean armm = false;

	double angle = 0;

	public void enable() {

		// TOGGLE BUTTONS
		// if (toggle && Button) { // Only execute once per Button push
		// toggle = false; // Prevents this section of code from being called again
		// until the Button is released and re-pressed
		// if (belt) { // Decide which way to set the motor this time through (or use
		// this as a motor value instead)
		// belt= false;
		// conveyorMotor.set(1);
		// } else {
		// belt= true;
		// conveyorMotor.set(0);
		// }
		// } else if(Button == FALSE) {
		// toggle = true; // Button has been released, so this allows a re-press to
		// activate the code above.
		// }

		// CUBE MOTORS
		if (Constants.Xbox1.getBumper(Hand.kLeft)) {
			cube.set(position.INTAKE);
		} else if (Constants.Xbox1.getBumper(Hand.kRight)) {
			cube.set(position.SCORE);
		} else {
			cube.set(position.DISABLE);
		}

		// CUBE PNEUMATIC
		if (pnueToggle && Constants.Xbox1.getTriggerAxis(Hand.kRight) >= .5) {
			pnueToggle = false;
			if (pnue) {
				pnue = false;
				Constants.cubePneumatic.set(Value.kForward);
			} else {
				pnue = true;
				Constants.cubePneumatic.set(Value.kReverse);
			}
		} else if (!(Constants.Xbox1.getTriggerAxis(Hand.kRight) >= .5)) {
			if (cubeInit == false) {
				Constants.cubePneumatic.set(Value.kForward);
				cubeInit = true;
			}
			pnueToggle = true;

		}

		// ARM
		if (Constants.Xbox1.getTriggerAxis(Hand.kLeft) > .6) {
			double stuff = SmartDashboard.getNumber("Stuff", 0);
			Constants.armLeft.set(ControlMode.PercentOutput, (-(Constants.Xbox1.getY(Hand.kLeft)) * .5) + stuff);
			SmartDashboard.putNumber("Position", arm.getPosition());
		} else {
			if (Constants.Xbox1.getBButton()) { // Joystick is to the right
				SmartDashboard.putNumber("Angle", 0);
			} else if (Constants.Xbox1.getYButton()) { // Joystick is up
				SmartDashboard.putNumber("Angle", 45);
			} else if (Constants.Xbox1.getXButton()) { // Joystick is to the left
				SmartDashboard.putNumber("Angle", 80);
			} else if (Constants.Xbox1.getAButton()) { // Joystick is down
				SmartDashboard.putNumber("Angle", 100);
			}
			angle = SmartDashboard.getNumber("Angle", 0);
			arm.set(angle);
		}

		// PTO
		if (DriverStation.getInstance().getMatchTime() >= 2) {
			if (toggle && Constants.Xbox1.getBButton()) {
				toggle = false;
				if (belt) {
					belt = false;
					Constants.PTO.set(Value.kForward);
				} else {
					belt = true;
					Constants.PTO.set(Value.kReverse);
				}
			} else if (!Constants.Xbox1.getBButton()) {
				if (ptoInit == false) {
					Constants.PTO.set(Value.kReverse);
					ptoInit = false;
				}
				toggle = true;
			}
		} else {
			Constants.PTO.set(Value.kForward);
		}

		// DEJAMMING
		if (Constants.Xbox1.getPOV() == 90) {
			Constants.cubeLeft.set(ControlMode.PercentOutput, .45);
			Constants.cubeRight.set(ControlMode.PercentOutput, .6);
			Constants.roller.set(ControlMode.PercentOutput, .4);
		} else if (Constants.Xbox1.getPOV() == 270) {
			Constants.cubeLeft.set(ControlMode.PercentOutput, -.6);
			Constants.cubeRight.set(ControlMode.PercentOutput, -.45);
			Constants.roller.set(ControlMode.PercentOutput, .4);
		}

		// angle = SmartDashboard.getNumber("Angle", 0);
		// arm.set(angle);

		// XBOX DRIVE
		Constants.leftFront.set(ControlMode.PercentOutput, Constants.Xbox2.getY(Hand.kLeft) * .5);
		Constants.rightFront.set(ControlMode.PercentOutput, Constants.Xbox2.getY(Hand.kRight) * .5);

		// WHEEL DRIVE

		if (Constants.Joystick1.getRawButton(2)) {// turn in place button X

			Constants.rightFront.set(ControlMode.PercentOutput, drive.valueJ);
			Constants.leftFront.set(ControlMode.PercentOutput, -drive.valueJ);

			drive.spinspot(.5);

		}

		else if (Constants.Joystick1.getRawButton(4)) {// adjust button
			Constants.rightFront.set(ControlMode.PercentOutput, drive.valueJ);
			Constants.leftFront.set(ControlMode.PercentOutput, -drive.valueJ);

			drive.spinspot(.25);

		} else if (Constants.Joystick1.getRawButton(5)) {// adjust button
			Constants.rightFront.set(ControlMode.PercentOutput, drive.valueJ);
			Constants.leftFront.set(ControlMode.PercentOutput, -drive.valueJ);

			drive.spinspot(-.25);

		} else if (Constants.Joystick1.getRawButton(1)) {// TURBO NGHHHHHHH
			Constants.rightFront.set(ControlMode.PercentOutput, drive.valueJ);
			Constants.leftFront.set(ControlMode.PercentOutput, -drive.valueJ);
			// drive.inverse(0.5);
			drive.move(1, Constants.DriveWheel.getX());
		} else if (Constants.Joystick1.getRawButton(3)) {// slowwwwwwwwwwww
			drive.move(0.3, Constants.DriveWheel.getX());
		}

		else {
			// drive.move(-Constants.Joystick1.getY(), Constants.DriveWheel.getX());

		}

	}
}
