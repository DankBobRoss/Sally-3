package org.usfirst.frc.team3859.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Cube {
	Timer cubeTimer = new Timer();
	boolean init = false;

	enum position {
		INTAKE, SCORE, DISABLE_IN, DISABLE_OUT;
	}

	public void set(position state) {
		switch (state) {
		case INTAKE:
			if (SmartDashboard.getBoolean("Cube Present?", false) == true) {
				Constants.cubeLeft.set(ControlMode.PercentOutput, 0);
				Constants.cubeRight.set(ControlMode.PercentOutput, 0);

				// if (SmartDashboard.getBoolean("Cube Present?", false) == true) {
				Constants.Xbox1.setRumble(RumbleType.kLeftRumble, 1);
				Timer.delay(1);
				Constants.Xbox1.setRumble(RumbleType.kLeftRumble, 0);
				// }

				// Constants.cubePneumatic.set(Value.kForward);

			} else {
				Constants.cubePneumatic.set(Value.kReverse);
				Constants.cubeLeft.set(ControlMode.PercentOutput, -.75);
				Constants.cubeRight.set(ControlMode.PercentOutput, -.75);
				Constants.roller.set(ControlMode.PercentOutput, -.75);
				// if (SmartDashboard.getBoolean("Cube Present?", false) == true) {
				// Constants.Xbox1.setRumble(RumbleType.kLeftRumble, 1);
				// Timer.delay(1);
				// Constants.Xbox1.setRumble(RumbleType.kLeftRumble, 0);
				// }
			}
			break;
		case SCORE:
			 Constants.cubePneumatic.set(Value.kForward);

			Constants.cubeLeft.set(ControlMode.PercentOutput, .8);
			Constants.cubeRight.set(ControlMode.PercentOutput, .8);
			break;
		case DISABLE_OUT:
			Constants.cubeLeft.set(ControlMode.PercentOutput, 0);
			Constants.cubeRight.set(ControlMode.PercentOutput, 0);

			 Constants.cubePneumatic.set(Value.kReverse);
			break;
		case DISABLE_IN:
			Constants.cubeLeft.set(ControlMode.PercentOutput, 0);
			Constants.cubeRight.set(ControlMode.PercentOutput, 0);

			 Constants.cubePneumatic.set(Value.kForward);
			break;
		}

	}
}