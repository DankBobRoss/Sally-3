package org.usfirst.frc.team3859.robot;

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
	 * 
	 * 
	 * @param encDistance
	 * @param speed
	 * @param order
	 */
	public void drive(double encDistance, double speed, double order) {
		if (init == false) {
			if (order == 1) {
				autoTimer.start();
			}
			init = true;
		}

		if (order == order_) {
			double error = Math.abs(encDistance) - Math.abs(oi.drive.getEncDistance());
			if (error >= 1) {
				drivePID.setPID(0, 0, 0);
				Constants.rightfront.set(ControlMode.PercentOutput, -drivePID.calculate(error));
				Constants.leftfront.set(ControlMode.PercentOutput, -drivePID.calculate(error));
				System.out.print("\n Error: " + turnPID.calculate(error));
				System.out.print("Time: " + autoTimer.get());
				// Constants.rightfront.set(ControlMode.PercentOutput,
				// -jankyPID(oi.drive.getEncDistance(),encDistance,speed));
				// Constants.leftfront.set(ControlMode.PercentOutput,
				// -jankyPID(oi.drive.getEncDistance(),encDistance,speed));
			} else {
				Constants.leftfront.set(ControlMode.PercentOutput, 0);
				Constants.rightfront.set(ControlMode.PercentOutput, 0);
				order_++;
				init = false;
			}
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
			if (order == 1) {
				autoTimer.start();
			}
			autoTimer.reset();
			init = true;
		}

		// SmartDashboard.putNumber("Auto Time", autoTimer.get());
		if (order == order_) {
			if (autoTimer.get() <= time) {
				// Constants.leftfront.set(ControlMode.PercentOutput,-speed);
				// to change the voltage for right. change the value below(the one after "-speed
				// -"
				Constants.rightfront.set(ControlMode.PercentOutput, -speed);
				Constants.leftfront.set(ControlMode.PercentOutput, -speed);
			} else {
				Constants.leftfront.set(ControlMode.PercentOutput, 0);
				Constants.rightfront.set(ControlMode.PercentOutput, 0);
				order_++;
				init = false;
				// SmartDashboard.putNumber("Auto Current Order", order);
			}
		}
	}

	/**
	 * 
	 * 
	 * @param goal
	 * @param order
	 * @param direction
	 */
	public void PIDturn(double goal, double order, String direction) {
		double P = 0;
		double I = 0;
		double D = 0;
		if (init == false) {
			if (order_ == 1) {
				autoTimer.start();
			}
			// Constants.navx.reset();
			init = true;
		}
		double angle = Constants.navx.getAngle();

		// tu = .50001
		// turnPID.setPID(0.010555555, 0, 0.116);
		turnPID.setPID(0.010555555, 0, 0.119);

		double error = Math.abs(goal) - Math.abs(angle);
		if (order == order_) {

			//
			//
			//
			//
			// .41785
			// if (error > 2) {
			if (direction == "left") {
				Constants.rightfront.set(ControlMode.PercentOutput, -turnPID.calculate(error));
				Constants.leftfront.set(ControlMode.PercentOutput, turnPID.calculate(error));
				System.out.print(turnPID.calculate(error) + autoTimer.get());
				System.out.print("\nTime: " + autoTimer.get() + "\n\n");

			} else if (direction == "right") {
				times++;
				if (times == 3) {
					System.out.print("\n Error: " + turnPID.calculate(error));
					System.out.print("\n Time: " + autoTimer.get() + "\n");
					times = 0;

				}
				Constants.rightfront.set(ControlMode.PercentOutput, turnPID.calculate(error));
				Constants.leftfront.set(ControlMode.PercentOutput, -turnPID.calculate(error));

			}
			// } else if (error <= 1) {
			// Constants.rightfront.set(ControlMode.PercentOutput, 0);
			// Constants.leftfront.set(ControlMode.PercentOutput, 0);
			// order_++;
			// }
		}

	}

	/**
	 * 
	 * @param time
	 * @param order
	 */
	void cubeDeliver(double time, double order) {
		if (init == false) {
			if (order == 1) {
				autoTimer.start();
			}
			autoTimer.reset();
			init = true;
		}
		if (order == order_) {
			if (time < autoTimer.get()) {
				// deliver
			} else if (time >= autoTimer.get()) {
				// no deliver

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
			if (order == 1) {
				autoTimer.start();
			}
			Constants.navx.reset();
			autoTimer.reset();
			init = true;
		}
		oi.drive.SetUp(true);
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
						Constants.rightfront.set(ControlMode.PercentOutput,
								jankyPID(Constants.navx.getAngle(), goal, speed));
						Constants.leftfront.set(ControlMode.PercentOutput,
								-jankyPID(Constants.navx.getAngle(), goal, speed));

					} else if (RDistance < 4 && RDistance > -4) {
						Constants.leftfront.set(ControlMode.PercentOutput, 0);
						Constants.rightfront.set(ControlMode.PercentOutput, 0);
						Constants.navx.reset();
						order_++;
						init = false;
					}

					break;
				case "left":
					if (LeftDistance < -4) {
						Constants.leftfront.set(ControlMode.PercentOutput,
								jankyPID(Constants.navx.getAngle(), goal, speed));
						Constants.rightfront.set(ControlMode.PercentOutput,
								-jankyPID(Constants.navx.getAngle(), goal, speed));

					} else if (LeftDistance > -2 && LeftDistance < 2) {
						Constants.leftfront.set(ControlMode.PercentOutput, 0);
						Constants.rightfront.set(ControlMode.PercentOutput, 0);
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
			if (order == 1) {
				autoTimer.start();
				// order_ = 1;
			}
			autoTimer.reset();
			init = true;
		}
		if (order == order_) {
			if (autoTimer.get() < time) {
				Constants.leftfront.set(ControlMode.PercentOutput, 0);
				Constants.rightfront.set(ControlMode.PercentOutput, 0);
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
			oi.drive.SetUp(true);
			switch (autoMode) {
			case "Right":
				if (side1 == 'R') {
					nothing(4, 1);
					drive(144, speed, 2);
					turnToAngle("left", -90, .3, 3);
					drive(31.2, speed, 4);
					cubeDeliver(2, 5);
					drive(31.2, -speed, 6);
					turnToAngle("right", 90, .3, 7);
					drive(24, speed, 8);
					turnToAngle("left", -90, .3, 9);

					// other
					nothing(4, 1);
					drive(81.72, speed, 2);
					turnToAngle("left", -45, .3, 2);
					// nothing(.5, 3);
					// timedrive(1, speed, 4);
					// cubeDeliver(.5, 5);
					// timedrive(1, -speed, 6);
					// turnToAngle("left", 90, .3, 7);
					// timedrive(3, speed, 1);
					// turnToAngle("right", 90, .3, 2);

				} else if (side1 == 'L') {
					drive(168, speed, 1);
					turnToAngle("left", -90, .3, 2);
					drive(233.04, speed, 3);
					turnToAngle("left", -90, .3, 4);
					drive(43.44, speed, 5);
					turnToAngle("left", -90, .3, 6);
					drive(24, speed, 7);
					cubeDeliver(1, 8);

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
					drive(144, speed, 2);
					turnToAngle("right", 90, .3, 3);
					drive(31.2, speed, 4);
					cubeDeliver(2, 5);
					drive(31.2, -speed, 6);
					turnToAngle("left", -90, .3, 7);
					drive(24, speed, 8);
					turnToAngle("right", 90, .3, 9);

				} else if (side1 == 'R') {
					timedrive(5, speed, 1);
					turnToAngle("right", 90, .3, 2);
					timedrive(7, speed, 3);
					turnToAngle("right", 90, .3, 4);
					timedrive(1.1, speed, 5);
					turnToAngle("right", 90, .3, 6);
					timedrive(1, speed, 7);
					cubeDeliver(1, 8);
				}
				break;
			case "Middle":
				if (side1 == 'L') {
					drive(34.68, speed, 1);
					turnToAngle("left", -45, .3, 2);
					drive(56.16, speed, 3);
					turnToAngle("right", 45, .3, 4);
					drive(16.44, speed, 5);
					cubeDeliver(.2, 6);
				} else if (side1 == 'R') {
					drive(34.68, speed, 1);
					turnToAngle("right", 45, .3, 2);
					drive(30.12, speed, 3);
					turnToAngle("left", -45, .3, 4);
					drive(16.44, speed, 5);
					cubeDeliver(.2, 6);
				}

				break;
			case "turn":
				turnToAngle("right", 270, .3, 1);
				break;
			case "PID turn":
				PIDturn(90, 1, "right");

				break;
			case "test":
//				Constants.climb.set(ControlMode.PercentOutput, jankyPID(oi.drive.getEncDistance(), 50, .6));

				SmartDashboard.putNumber("Encoder Distance", oi.drive.getEncDistance());
				double position = .001 * (Constants.rightfront.getSelectedSensorPosition(0));
				SmartDashboard.putNumber("Talon Position", position);
				SmartDashboard.putNumber("Talon Velocity", Constants.rightfront.getSelectedSensorVelocity(0));
				break;
			}
			if (order_ > 10) {
				autoMode = "test";
			}
		}
	}
}
