package org.usfirst.frc.team3859.robot;

import org.usfirst.frc.team3859.robot.Constants.armPos;
import org.usfirst.frc.team3859.robot.Constants.position;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous {
	Timer autoTimer = new Timer();
	OI oi = new OI();
	private boolean init = false;
	double order_ = 1;
	double armOrder_ = 1;
	double currangle = 50;
	double goal;
	int times = 0;
	boolean initt = false;
	double speed = .3;
	double output = 0;
	boolean finished = false;
	PIDcontrol turnPID = new PIDcontrol(0, 0, 0);
	PIDcontrol drivePID = new PIDcontrol(0, 0, 0);

	/**
	 * 
	 * @param encDistance
	 * @param order
	 */
	public void drive(double encDistance, double order) {
		// double P = SmartDashboard.getNumber("P", 0);
		// double I = SmartDashboard.getNumber("I", 0);
		// double D = SmartDashboard.getNumber("D", 0);
		double P = 0.012;
		double I = 0.00001;
		double D = 0;
		if (init == false) {
			encDistance = encDistance + 17;
			init = true;
		}
		if (order == order_) {
			double error = encDistance - oi.drive.getRightEncDistance();
			if (error >= 2) {
				drivePID.setPID(P, I, D);
				Constants.rightFront.set(ControlMode.PercentOutput, drivePID.calculate(error));
				Constants.leftFront.set(ControlMode.PercentOutput, -drivePID.calculate(error));
			} else if (error < 2) {
				Constants.leftFront.set(ControlMode.PercentOutput, 0);
				Constants.rightFront.set(ControlMode.PercentOutput, 0);
				order_++;
				init = false;
				oi.drive.rightEncInit = false;
			}
		}
	}

	/**
	 * PID TURN
	 * 
	 * @param direction
	 * @param goal
	 * @param order
	 */
	public void turnToAngle(String direction, double goal, double order) {
		// double P = SmartDashboard.getNumber("P", 0);/*
		// double I = SmartDashboard.getNumber("I", 0);
		// double D = SmartDashboard.getNumber("D", 0);*/

		double P = 0.005;
		double I = 0.0000045;
		double D = 0;
		if (init == false) {
			Constants.navx.reset();
			init = true;
		}
		double angle = Constants.navx.getAngle();

		turnPID.setPID(P, I, D);
		if (goal == 45) {
			goal = 50;
		}
		double error = Math.abs(goal) - Math.abs(angle);
		if (order == order_) {
			if (error > .5) {
				if (direction == "left") {
					Constants.rightFront.set(ControlMode.PercentOutput, turnPID.calculate(error));
					Constants.leftFront.set(ControlMode.PercentOutput, turnPID.calculate(error));

				} else if (direction == "right") {
					Constants.rightFront.set(ControlMode.PercentOutput, -turnPID.calculate(error));
					Constants.leftFront.set(ControlMode.PercentOutput, -turnPID.calculate(error));

				}
			} else if (error <= .5) {
				Constants.rightFront.set(ControlMode.PercentOutput, 0);
				Constants.leftFront.set(ControlMode.PercentOutput, 0);
				order_++;
				init = false;
			}
		}

	}

	void armSet(Constants.armPos state, double armOrder) {
		if (init == false) {
			autoTimer.reset();
			init = true;
		}
		if (armOrder == armOrder_) {
			switch (state) {
			case INTAKE:
				Constants.cubePneumatic.set(Value.kReverse);
				SmartDashboard.putNumber("Angle", Constants.intake);
				break;
			case SWITCHSHOT:
				Constants.cubePneumatic.set(Value.kForward);
				SmartDashboard.putNumber("Angle", Constants.switchShot);
				break;
			case BACKSHOT:
				Constants.cubePneumatic.set(Value.kForward);
				SmartDashboard.putNumber("Angle", Constants.backShot);
				break;
			}
			double angle = SmartDashboard.getNumber("Angle", 0);
			oi.arm.set(angle);
		}

	}

	/**
	 * 
	 * @param state
	 * @param order
	 */
	void cubeStuff(Constants.position state, double order) {
		if (init == false) {
			autoTimer.reset();
			init = true;
		}
		if (order == order_) {
			if (oi.arm.done == true) {
				if (autoTimer.get() < 3) {
					oi.in.set(state);
				} else if (autoTimer.get() >= 3) {
					oi.in.set(position.DISABLE);
					order_++;
					armOrder_++;
					init = false;
				}
			} else {
				oi.in.set(position.DISABLE);
			}
		}

	}

	/**
	 * 
	 * 
	 * @param current
	 * @param goal
	 * @param speed
	 * @return output
	 */
	public double jankyPID(double current, double goal, double speed) {
		double progress = Math.abs(current) / Math.abs(goal);
		double output = speed
				* (1 + (4.7 * progress) - (19.65 * Math.pow(progress, 2)) + (23.76715 * Math.pow(progress, 3))
						- (11.9661 * Math.pow(progress, 4)) + (2.216634 * Math.pow(progress, 5)));
		return output;
	}

	/**
	 * 
	 * 
	 * @param time
	 * @param order
	 */
	public void nothing(double time, double order) {
		if (init == false) {
			autoTimer.reset();
			init = true;
		}
		if (order == order_) {
			if (autoTimer.get() < time) {
				Constants.leftFront.set(ControlMode.PercentOutput, 0);
				Constants.rightFront.set(ControlMode.PercentOutput, 0);
			} else if (autoTimer.get() >= time) {
				order_++;
				init = false;
				// SmartDashboard.putNumber("Auto Current Order", order);
			}
		}
	}

	public void startConfig() {
		if (finished == false) {
			if (Constants.limitSwitch.get() == false) {
				Constants.armLeft.set(ControlMode.PercentOutput, -.2);
			} else if (Constants.limitSwitch.get() == true) {
				Constants.armLeft.set(ControlMode.PercentOutput, 0);
				Constants.armSensor.setQuadraturePosition(0, 200);
				finished = true;
			}
		}
	}

	boolean init1 = false;
	boolean initiate = false;

	public void autoset(String autoMode, String autoChoice, char side1, char side2) {
		if (initiate == false) {
			autoTimer.start();
			initiate = true;
		} else if (initiate == true) {
			oi.drive.setUp(false);
			if (autoChoice == "deliver") {
				switch (autoMode) {
				case "Right":
					if (side1 == 'R') {
						nothing(4, 1);
						drive(144, 2);
						turnToAngle("left", -90, 3);
						drive(31.2, 4);
						cubeStuff(position.SCORE_HARD, 5);
						drive(31.2, 6);
						turnToAngle("right", 90, 7);
						drive(24, 8);
						turnToAngle("left", -90, 9);

						// other
						nothing(4, 1);
						drive(81.72, 2);
						turnToAngle("left", -45, 2);
						// nothing(.5, 3);
						// timedrive(1, speed, 4);
						// cubeDeliver(.5, 5);
						// timedrive(1, -speed, 6);
						// turnToAngle("left", 90, .3, 7);
						// timedrive(3, speed, 1);
						// turnToAngle("right", 90, .3, 2);

					} else if (side1 == 'L') {
						drive(168, 1);
						turnToAngle("left", -90, 2);
						drive(233.04, 3);
						turnToAngle("left", -90, 4);
						drive(43.44, 5);
						turnToAngle("left", -90, 6);
						drive(24, 7);
						cubeStuff(position.SCORE_HARD, 8);

						// timedrive(5, speed, 1);
						// turnToAngle("right", 90, .3, 2);
						// timedrive(7, speed, 3);
						// turnToAngle("right", 90, .3, 4);
						// timedrive(1.1, speed, 5);
						// turnToAngle("right", 90, .3, 6);
						// timedrive(1, speed, 7);
					}

					break;
				case "Left":
					if (side1 == 'L') {
						nothing(4, 1);
						drive(144, 2);
						turnToAngle("right", 90, 3);
						drive(31.2, 4);
						cubeStuff(position.SCORE_HARD, 5);
						drive(31.2, 6);
						turnToAngle("left", -90, 7);
						drive(24, 8);
						turnToAngle("right", 90, 9);

					} else if (side1 == 'R') {
						nothing(4, 1);
						drive(144, 2);
						turnToAngle("left", -90, 3);
						drive(31.2, 4);
						cubeStuff(position.SCORE_HARD, 5);
						drive(31.2, 6);
						turnToAngle("right", 90, 7);
						drive(24, 8);
						turnToAngle("left", -90, 9);
					}
					break;
				case "Middle":
					if (side1 == 'L') {
						drive(34.68, 1);
						turnToAngle("left", -45, 2);
						drive(56.16, 3);
						turnToAngle("right", 45, 4);
						drive(16.44, 5);
						cubeStuff(position.SCORE_HARD, 6);
					} else if (side1 == 'R') {
						drive(34.68, 1);
						turnToAngle("right", 45, 2);
						drive(30.12, 3);
						turnToAngle("left", -45, 4);
						drive(16.44, 5);
						cubeStuff(position.SCORE_HARD, 6);
					}

					break;
				}
			} else if (autoChoice == "cross") {

			} else if (autoChoice == "test") {
				// Constants.PTO.set(Value.kForward);
				// if (Constants.c.getClosedLoopControl() == false) {
				// drive(33,1);
				// armSet(armPos.INTAKE,1);
				// cubeStuff(position.INTAKE, 2);
				// drive(4,2);
				// drive(-33,3);
				startConfig();
				if (finished == true) {
					if (side1 == 'R') {
						drive(20, 1);
						turnToAngle("right", 45, 2);
						drive(40, 3);
						turnToAngle("left", -45, 4);
						nothing(1, 5);
						armSet(armPos.SWITCHSHOT, 1);
						cubeStuff(position.SCORE_HARD, 6);
						armSet(armPos.INTAKE, 2);
						cubeStuff(position.DISABLE, 7);
					} else if (side1 == 'L') {
						drive(20, 1);
						turnToAngle("left", -45, 2);
						drive(40, 3);
						turnToAngle("right", 45, 4);
						nothing(1, 5);
						armSet(armPos.SWITCHSHOT, 1);
						cubeStuff(position.SCORE_HARD, 6);
						armSet(armPos.INTAKE, 2);
						cubeStuff(position.DISABLE, 7);
					}
				}
				//
				// }
				// }
				// double turnAngle = SmartDashboard.getNumber("Turn Angle", 0);
				// turnToAngle("right", turnAngle, 1);

			}
		}
	}
}
