package org.usfirst.frc.team3859.robot;

import org.usfirst.frc.team3859.robot.Cube.position;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//
//import com.ctre.phoenix.motorcontrol.ControlMode;
//
////import edu.wpi.first.wpilibj.PIDController;
//import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//
////
public class Autonomous {
	Timer autoTimer = new Timer();
	OI oi = new OI();
	boolean init = false;
	double order_ = 1;
	double currangle = 50;
	double goal;
	int times = 0;
	boolean initt = false;
	double speed = .3;
	double output = 0;
	PIDcontrol turnPID = new PIDcontrol(0, 0, 0);
	PIDcontrol drivePID = new PIDcontrol(0, 0, 0);

	/**
	 * 
	 * @param encDistance
	 * @param order
	 */
	public void drive(double encDistance, double order) {
		// if (init == false) {
		//
		// init = true;
		// }
		double P = SmartDashboard.getNumber("P", 0);
		double I = SmartDashboard.getNumber("I", 0);
		double D = SmartDashboard.getNumber("D", 0);
		if (order == order_) {
			double error = encDistance - oi.drive.getRightEncDistance();
			// if (error >= 1) {
			SmartDashboard.putNumber("Error", error);
			drivePID.setPID(P, I, D);
			Constants.rightFront.set(ControlMode.PercentOutput, -drivePID.calculate(error));
			Constants.leftFront.set(ControlMode.PercentOutput, -drivePID.calculate(error));
			// } else {
			// Constants.leftFront.set(ControlMode.PercentOutput, 0);
			// Constants.rightFront.set(ControlMode.PercentOutput, 0);
			// order_++;
			// init = false;
			// }
		}
	}

	/**
	 * 
	 * 
	 * @param time
	 * @param speed
	 * @param order
	 */
	public void timedrive(double time, double speed, double order) {
		// oi.driver.SetUp();
		if (init == false) {
			autoTimer.reset();
			init = true;
		}

		// SmartDashboard.putNumber("Auto Time", autoTimer.get());
		if (order == order_) {
			if (autoTimer.get() <= time) {
				// Constants.leftFront.set(ControlMode.PercentOutput,-speed);
				// to change the voltage for right. change the value below(the one after "-speed
				// -"
				Constants.rightFront.set(ControlMode.PercentOutput, -speed);
				Constants.leftFront.set(ControlMode.PercentOutput, -speed);
			} else {
				Constants.leftFront.set(ControlMode.PercentOutput, 0);
				Constants.rightFront.set(ControlMode.PercentOutput, 0);
				order_++;
				init = false;
				// SmartDashboard.putNumber("Auto Current Order", order);
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
		double I = .0000045;
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
			// if (error > .5) {
			if (direction == "left") {
				Constants.rightFront.set(ControlMode.PercentOutput, -turnPID.calculate(error));
				Constants.leftFront.set(ControlMode.PercentOutput, turnPID.calculate(error));

			} else if (direction == "right") {
				Constants.rightFront.set(ControlMode.PercentOutput, turnPID.calculate(error));
				Constants.leftFront.set(ControlMode.PercentOutput, -turnPID.calculate(error));

			}
			// } else if (error <= .5) {
			// Constants.rightFront.set(ControlMode.PercentOutput, 0);
			// Constants.leftFront.set(ControlMode.PercentOutput, 0);
			// order_++;
			// }
		}

	}

	/**
	 * 
	 * @param time
	 * @param order
	 */
	void cubeDeliver(double order) {
		if (init == false) {
			autoTimer.reset();
			init = true;
		}
		if (order == order_) {
			if (autoTimer.get() < 2) {
				oi.cube.set(position.SCORE);
				// deliver
			} else if (autoTimer.get() >= 2) {
				// no deliver
				oi.cube.set(position.DISABLE);
				order_++;
			}
		}

	}

	/**
	 * 
	 * 
	 * @param current
	 * @param goal
	 * @param speed
	 * @return
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
	 * @param direction
	 * @param goal
	 * @param speed
	 * @param order
	 */

	void turnToAngle(String direction, double goal, double speed, double order) {
		if (init == false) {
			Constants.navx.reset();
			autoTimer.reset();
			init = true;
		}
		oi.drive.setUp(true);
		double negative = speed * -1;
		currangle = Constants.navx.getAngle();
		double RDistance;
		double NegGoal = goal - 360;
		double LeftDistance = NegGoal - currangle;
		double product = currangle / goal;
		double difference = Math.abs(currangle) / Math.abs(goal);
		LeftDistance = LeftDistance * -1;
		LeftDistance = LeftDistance % 360;
		LeftDistance = LeftDistance * -1;
		if (difference < 0) {
			RDistance = goal - currangle;
			RDistance = RDistance * -1;
			RDistance = RDistance % 360;
			RDistance = RDistance * -1;
			RDistance = RDistance + 360;
		} else {
			RDistance = goal - currangle;
		}
		if (order == order_) {
			if (autoTimer.get() > .5) {
				switch (direction) {
				case "right":
					if (RDistance > 4) {
						Constants.rightFront.set(ControlMode.PercentOutput,
								jankyPID(Constants.navx.getAngle(), goal, speed));
						Constants.leftFront.set(ControlMode.PercentOutput,
								-jankyPID(Constants.navx.getAngle(), goal, speed));

					} else if (RDistance < 4 && RDistance > -4) {
						Constants.leftFront.set(ControlMode.PercentOutput, 0);
						Constants.rightFront.set(ControlMode.PercentOutput, 0);
						Constants.navx.reset();
						order_++;
						init = false;
					}

					break;
				case "left":
					if (LeftDistance < -4) {
						Constants.leftFront.set(ControlMode.PercentOutput,
								jankyPID(Constants.navx.getAngle(), goal, speed));
						Constants.rightFront.set(ControlMode.PercentOutput,
								-jankyPID(Constants.navx.getAngle(), goal, speed));

					} else if (LeftDistance > -2 && LeftDistance < 2) {
						Constants.leftFront.set(ControlMode.PercentOutput, 0);
						Constants.rightFront.set(ControlMode.PercentOutput, 0);
						Constants.navx.reset();
						order_++;
						init = false;
					}
					break;
				}
			}
		}

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

	// public String autoMode = "Test";
	// CANTalon geary = new CANTalon(9);

	boolean init1 = false;
	boolean initiate = false;

	public void autoset(String autoMode, char side1, char side2) {
		if (initiate == false) {
			autoTimer.start();
			initiate = true;
		} else if (initiate == true) {
			oi.drive.setUp(false);
			switch (autoMode) {
			case "Right":
				if (side1 == 'R') {
					nothing(4, 1);
					drive(144, 2);
					turnToAngle("left", -90, 3);
					drive(31.2, 4);
					cubeDeliver(5);
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
					cubeDeliver(8);

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
					cubeDeliver(5);
					drive(31.2, 6);
					turnToAngle("left", -90, 7);
					drive(24, 8);
					turnToAngle("right", 90, 9);

				} else if (side1 == 'R') {
					timedrive(5, speed, 1);
					turnToAngle("right", 90, 2);
					timedrive(7, speed, 3);
					turnToAngle("right", 90, 4);
					timedrive(1.1, speed, 5);
					turnToAngle("right", 90, 6);
					timedrive(1, speed, 7);
					cubeDeliver(8);
				}
				break;
			case "Middle":
				if (side1 == 'L') {
					drive(34.68, 1);
					turnToAngle("left", -45, 2);
					drive(56.16, 3);
					turnToAngle("right", 45, 4);
					drive(16.44, 5);
					cubeDeliver(6);
				} else if (side1 == 'R') {
					drive(34.68, 1);
					turnToAngle("right", 45, 2);
					drive(30.12, 3);
					turnToAngle("left", -45, 4);
					drive(16.44, 5);
					cubeDeliver(6);
				}

				break;
			case "turn":
				turnToAngle("right", 270, .3, 1);
				break;
			case "PID turn":
				// PIDturn(autoMode, currangle, currangle, currangle);

				break;
			case "test":
				double drive = SmartDashboard.getNumber("Drive Position", 0);
				drive(100, 1);

				// double turnAngle = SmartDashboard.getNumber("Turn Angle", 0);
				// turnToAngle("right", turnAngle, 1);
				break;
			}
		}
	}
}
