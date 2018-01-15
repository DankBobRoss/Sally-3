package org.usfirst.frc.team3859.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class Cube {
	enum position {
		INTAKE, SCORE
	}

	public void set(position state) {
		switch (state) {
		case INTAKE:
			Constants.cube.set(ControlMode.PercentOutput,1);
			break;
		case SCORE:
			Constants.cube.set(ControlMode.PercentOutput,1);
			break;
		}

	}
}
