package org.usfirst.frc.team3859.robot;

import org.usfirst.frc.team3859.robot.Constants.armPos;
import org.usfirst.frc.team3859.robot.Constants.position;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {
	Drive drive = new Drive();
	Intake in = new Intake();
	Arm arm = new Arm();
	Climb climb = new Climb();
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
		if (Constants.xbox1.getBumper(Hand.kLeft) && Constants.xbox1.getPOV() != 90) {
			in.set(position.INTAKE);
		} else if (Constants.xbox1.getBumper(Hand.kLeft) && Constants.xbox1.getPOV() == 90) {
			in.set(position.DEJAM);
		} else if (Constants.xbox1.getBumper(Hand.kRight) && Constants.xbox1.getPOV() != 180) {
			in.set(position.SCORE_MEDIUM);
		} else if (Constants.xbox1.getBumper(Hand.kRight) && Constants.xbox1.getPOV() == 180) {
			in.set(position.SCORE_HARD);
		} else {
			in.set(position.DISABLE);
		}

		// CUBE PNEUMATIC
		if (pnueToggle && Constants.xbox1.getTriggerAxis(Hand.kRight) >= .5) {
			pnueToggle = false;
			if (pnue) {
				pnue = false;
				Constants.cubePneumatic.set(Value.kForward);
			} else {
				pnue = true;
				Constants.cubePneumatic.set(Value.kReverse);
			}
		} else if (!(Constants.xbox1.getTriggerAxis(Hand.kRight) >= .5)) {
			if (cubeInit == false) {
				Constants.cubePneumatic.set(Value.kReverse);
				cubeInit = true;
			}
			pnueToggle = true;

		}

		// ARM
		if (Constants.xbox1.getTriggerAxis(Hand.kLeft) > .6) {
//			if (Constants.xbox1.getBButton()) { // Joystick is to the right
//				SmartDashboard.putNumber("Angle", Constants.intake);
//			} else if (Constants.xbox1.getYButton()) { // Joystick is up
//				SmartDashboard.putNumber("Angle", Constants.switchShot);
//			} else if (Constants.xbox1.getXButton()) { // Joystick is to the left
//				SmartDashboard.putNumber("Angle", Constants.backShot);
//			}
//			angle = SmartDashboard.getNumber("Angle", 0);
			// arm.set(angle);
		} else {

			double voltage = .14;
			double stuff = Math.cos(((Constants.armLeft.getSelectedSensorPosition(0) / 240) * Math.PI) / 2048)
					* voltage;
			Constants.armLeft.set(ControlMode.PercentOutput, (-(Constants.xbox1.getY(Hand.kLeft)) * .5) + stuff);
			SmartDashboard.putNumber("Position", arm.getPosition());
		}

		// if (Constants.xbox1.getRawButton(9)) {
		// Constants.armSensor.setQuadraturePosition(0, 200);
		// }

		// CLIMB

		// if (DriverStation.getInstance().getMatchTime() >= 1.5) {
		// if (toggle && Constants.xbox1.getY(Hand.kRight) > .7) {
		// toggle = false;
		// if (belt) {
		// belt = false;
		// Constants.PTO.set(Value.kForward);
		// } else {
		// belt = true;
		// Constants.PTO.set(Value.kReverse);
		// }
		// } else if (!(Constants.xbox1.getY(Hand.kRight) > .7)) {
		// if (ptoInit == false) {
		// Constants.PTO.set(Value.kForward);
		// ptoInit = true;
		// }
		// toggle = true;
		// }
		if (toggle && Constants.xbox2.getBButton()) {
			toggle = false;
			if (belt) {
				belt = false;
				Constants.PTO.set(Value.kForward);
				SmartDashboard.putBoolean("PTO Engaged?", false);
			} else {
				belt = true;
				Constants.PTO.set(Value.kReverse);
				SmartDashboard.putBoolean("PTO Engaged?", true);
			}
		} else if (!(Constants.xbox2.getBButton())) {
			if (ptoInit == false) {
				Constants.PTO.set(Value.kForward);
				SmartDashboard.putBoolean("PTO Engaged?", false);
				ptoInit = true;
			}
			toggle = true;
		}
		// } else {
		// Constants.PTO.set(Value.kForward);
		// }

		// angle = SmartDashboard.getNumber("Angle", 0);
		// arm.set(angle);

		// XBOX DRIVE
		Constants.leftFront.set(ControlMode.PercentOutput, Constants.xbox2.getY(Hand.kLeft));
		Constants.rightFront.set(ControlMode.PercentOutput, Constants.xbox2.getY(Hand.kRight));

		// WHEEL DRIVE

		if (Constants.joystick1.getRawButton(2)) {// turn in place button X

			drive.spinspot(.5);

		}

		else if (Constants.joystick1.getRawButton(4)) {// adjust button

			drive.spinspot(.25);

		} else if (Constants.joystick1.getRawButton(5)) {// adjust button

			drive.spinspot(-.25);

		} else if (Constants.joystick1.getRawButton(1)) {// TURBO NGHHHHHHH

			drive.move(-1, Constants.driveWheel.getX());

		} else if (Constants.joystick1.getRawButton(3)) {// slowwwwwwwwwwww

			drive.move(-0.3, Constants.driveWheel.getX());

		}

		else {
			// drive.move(Constants.joystick1.getY(), Constants.driveWheel.getX());

		}

	}
}
