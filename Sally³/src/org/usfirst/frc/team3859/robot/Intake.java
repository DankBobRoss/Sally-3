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

	public void shootPneumaticSet(Value state) {
		Constants.shootPneumatic.set(state);
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
				rollerSet(0.55);
				if (SmartDashboard.getBoolean("Cube Present?", false) == true) {
					Constants.Xbox1.setRumble(RumbleType.kLeftRumble, 1);
				}
			}
			break;
		case SCORE:
			if (scoreInit == false) {
				cubeTimer.reset();
				scoreInit = true;
			}
			if (cubeTimer.get() > .5) {
				shootPneumaticSet(Value.kReverse);
			} else {
				shootPneumaticSet(Value.kForward);
			}
			cubeLeftSet(1);
			cubeRightSet(-1);
			rollerSet(0);
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
			shootPneumaticSet(Value.kReverse);
			break;
		case DEJAM:
			if (dejamInit == false) {
				cubeTimer.reset();
				dejamInit = true;
			}
			if (cubeTimer.get() < .4) {
				cubeLeftSet(.45);
				cubeRightSet(.6);
				rollerSet(.4);
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
						Constants.Xbox1.setRumble(RumbleType.kLeftRumble, 1);
					} else {
						Constants.Xbox1.setRumble(RumbleType.kLeftRumble, 0);
					}

				}
			}
			shootPneumaticSet(Value.kReverse);
			break;
		}
	}
}