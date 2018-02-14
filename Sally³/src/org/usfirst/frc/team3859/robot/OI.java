package org.usfirst.frc.team3859.robot;

import org.usfirst.frc.team3859.robot.Arm.armPos;
import org.usfirst.frc.team3859.robot.Cube.position;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.Hand;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {

	Drive drive = new Drive();
	Cube cube = new Cube();
	Arm arm = new Arm();
	boolean belt = false;
	boolean toggle = true;
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

		if (Constants.Xbox1.getBumper(Hand.kLeft)) {
			cube.set(position.INTAKE);
		} else if (Constants.Xbox1.getBumper(Hand.kRight)) {
			cube.set(position.SCORE);
		} else {
			if (SmartDashboard.getBoolean("Cube Present?", false) == true) {
				cube.set(position.DISABLE_IN);
			} else {
				cube.set(position.DISABLE_OUT);
			}
		}
		if (DriverStation.getInstance().getMatchTime() >= 2) {
			if (toggle && Constants.Xbox1.getBButton()) { // Only execute once per Button push
				toggle = false; // Prevents this section of code from being called again until the Button is
								// released and re-pressed
				if (belt) { // Decide which way to set the motor this time through (or use this as a motor
							// value instead)
					belt = false;
					Constants.PTO.set(Value.kForward);
				} else {
					belt = true;
					Constants.PTO.set(Value.kReverse);
				}
			} else if (!Constants.Xbox1.getBButton()) {
				toggle = true; // Button has been released, so this allows a re-press to activate the code
								// above.
			}
		} else {
			Constants.PTO.set(Value.kReverse);
		}
		if (Constants.Xbox1.getPOV(0) == 90) {
			SmartDashboard.putNumber("Angle", 0);
		} else if (Constants.Xbox1.getPOV(0) == 45) {
			SmartDashboard.putNumber("Angle", 80);
		} else if (Constants.Xbox1.getPOV(0) == 0) {
			SmartDashboard.putNumber("Angle", 110);
		}
		angle = SmartDashboard.getNumber("Angle", 0);
		arm.set(angle);
//		arm.set(110);

		// Constants.leftfront.(ControlMode.PercentOutput,
		// Constants.Xbox1.getY(Hand.kLeft)*.5);
		// Constants.rightfront.set(ControlMode.PercentOutput,
		// Constants.Xbox1.getY(Hand.kRight)*.5);

	}
}
