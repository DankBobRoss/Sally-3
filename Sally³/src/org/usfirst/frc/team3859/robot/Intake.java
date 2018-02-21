package org.usfirst.frc.team3859.robot;

import org.usfirst.frc.team3859.robot.Constants.position;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake {
	Timer cubeTimer = new Timer();
	boolean scoreInit = false;
	boolean init = false;
	boolean dejamInit = false;

	public void cubeLeftSet(double power) {
		Constants.cubeLeft.set(ControlMode.PercentOutput, power);
	}

	public void cubeRightSet(double power) {
		Constants.cubeRight.set(ControlMode.PercentOutput, power);
	}

	public void rollerSet(double power) {
		Constants.roller.set(ControlMode.PercentOutput, power);
	}

	public void shootPneumaticSet(boolean state) {
		if (state == true) { // OUT
			Constants.superPunch0.set(true);
			Constants.superPunch2.set(true);
			Constants.superPunch1.set(false);
			Constants.superPunch3.set(false);
		} else if (state == false) { /// IN
			Constants.superPunch0.set(false);
			Constants.superPunch2.set(false);
			Constants.superPunch1.set(true);
			Constants.superPunch3.set(true);
		}

	}

	public void set(position state) {
		switch (state) {
		case INTAKE:
			if (SmartDashboard.getBoolean("Cube Present?", false) == true) {
				cubeLeftSet(0);
				cubeRightSet(0);
				rollerSet(0);
			} else {
				cubeLeftSet(-0.75);
				cubeRightSet(0.75);
				rollerSet(0.45);
				if (SmartDashboard.getBoolean("Cube Present?", false) == true) {
					Constants.xbox1.setRumble(RumbleType.kLeftRumble, 1);
				}
			}
			break;
		case SCORE_HARD:
			if (scoreInit == false) {
				cubeTimer.reset();
				scoreInit = true;
			}
			if (cubeTimer.get() < .5) {
				shootPneumaticSet(true);
			} else {
				shootPneumaticSet(false);
			}
			cubeLeftSet(1);
			cubeRightSet(-1);
			rollerSet(0);
			break;
		case SCORE_MEDIUM:
			cubeLeftSet(.9);
			cubeRightSet(-.9);
			rollerSet(0);
			shootPneumaticSet(false);
			break;
		case DISABLE:
			scoreInit = false;
			dejamInit = false;
			if (init == false) {
				cubeTimer.start();
				init = true;
			}
			cubeLeftSet(0);
			cubeRightSet(0);
			rollerSet(0);
			shootPneumaticSet(false);
			break;
		case DEJAM:
			if (dejamInit == false) {
				cubeTimer.reset();
				dejamInit = true;
			}
			if (cubeTimer.get() < .1) {
				cubeLeftSet(.45);
				cubeRightSet(.6);
				rollerSet(.4);
			} else {
				if (cubeTimer.get() >= .5 && SmartDashboard.getBoolean("Cube Present?", false) == false) {
					if (cubeTimer.get() < .7) {
						cubeLeftSet(.45);
						cubeRightSet(.6);
						rollerSet(.4);
					}
				} else {
					if (SmartDashboard.getBoolean("Cube Present?", false) == true) {
						cubeLeftSet(0);
						cubeRightSet(0);
						rollerSet(0);
					} else {
						cubeLeftSet(-0.75);
						cubeRightSet(0.75);
						rollerSet(0.55);
						if (SmartDashboard.getBoolean("Cube Present?", false) == true) {
							Constants.xbox1.setRumble(RumbleType.kLeftRumble, 1);
						} else {
							Constants.xbox1.setRumble(RumbleType.kLeftRumble, 0);
						}

					}
				}
			}
			shootPneumaticSet(false);
			break;
		}
	}
}