package org.usfirst.frc.team3859.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous {
	Timer autoTimer = new Timer();
	OI oi = new OI();
	PIDController pid = new PIDController(1, 0, 0);
	boolean init = false;
	double order_ = 1;
	double currangle = 50;
	double goal;
	int times = 0;
	boolean initt = false;
	double speed = .3;
	double output = 0;

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
			// Constants.rightmiddle.reset
			init = true;
		}

		// SmartDashboard.putNumber("Auto Time", autoTimer.get());
		if (order == order_) {
			if (Constants.rightmiddle.getSelectedSensorPosition(0) <= encDistance) {
				// Constants.leftfront.set(ControlMode.PercentOutput,-speed);
				// to change the voltage for right. change the value below(the one after "-speed
				// -"
				Constants.rightfront.set(ControlMode.PercentOutput, -speed - .07);
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
//	public void pidTurn(double current, String direction) {
//		
//	}

	// public void turnPID(double goal, double order, String direction) {
	// if (init == false) {
	// if (order_ == 1) {
	// autoTimer.start();
	// }
	// Constants.navx.reset();
	// init = true;
	// }
	// double angle = Constants.navx.getAngle();
	// angle = angle % 360;
	// // pid.setou
	// pid.setP(1 / 90);
	// // pid.setOutputLimits(-, maximum);
	// // pid.setI(1);
	// if (direction == "left") {
	// Constants.leftfront.set(ControlMode.PercentOutput, pid.getOutput(angle,
	// goal));
	// Constants.rightfront.set(ControlMode.PercentOutput, pid.getOutput(angle,
	// goal));
	// if (pid.getOutput(angle, goal) == 0) {
	// Constants.leftfront.set(ControlMode.PercentOutput, 0);
	// Constants.rightfront.set(ControlMode.PercentOutput, 0);
	// order_++;
	// }
	// } else if (direction == "right") {
	// Constants.leftfront.set(ControlMode.PercentOutput, pid.getOutput(angle,
	// goal));
	// Constants.rightfront.set(ControlMode.PercentOutput, pid.getOutput(angle,
	// goal));
	// if (pid.getOutput(angle, goal) == 0) {
	// Constants.leftfront.set(ControlMode.PercentOutput, 0);
	// Constants.rightfront.set(ControlMode.PercentOutput, 0);
	// order_++;
	// }
	// }
	// }

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
	public double jankPID(double current, double goal, double speed) {
		double progress = Math.abs(current) / Math.abs(goal);
		if (progress < .3) {
			output = speed;
		} else if (progress > .3 && progress < .4) {
			output = (speed * 2) / 3;
		} else if (progress > .4 && progress < .5) {
			output = speed / 2;
		} else if (progress > .5 && progress < .6) {
			output = speed * .4;
		} else if (progress > .6 && progress < .7) {
			output = speed / 3;
		} else if (progress > .7 && progress < .8) {
			output = (speed * 7) / 30;
		} else if (progress > .8 && progress < .85) {
			output = (speed * 4) / 30;
		} else if (progress > .85 && progress < .9) {
			output = (speed * 2) / 30;
		} else if (progress > .9) {
			output = speed * .0333;
		}
		return output;
	}

	/**
	 * 
	 * 
	 * @param goal
	 * @param speed
	 * @param order
	 * @param direction
	 */

	void turnToAngle(String direction, double goal, double speed, double order) {
		if (init == false) {
			if (order == 1) {
				autoTimer.start();
				// order_ = 1;
			}
			Constants.navx.reset();
			autoTimer.reset();
			// Constants.climb.set(.5);
			// Constants.navx.zeroYaw();
			init = true;
			// goal = goal - 5;
			// goal = goal + 20;
		}
		oi.drive.SetUp(true);
		double negative = speed * -1;
		// currangle = Constants.navx.getAngle();
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
			SmartDashboard.putNumber("RightDistance", RDistance);
		} else {
			RDistance = goal - currangle;
			SmartDashboard.putNumber("RightDistance", RDistance);
		}
		SmartDashboard.putNumber("Left Distance", LeftDistance);
		SmartDashboard.putNumber("Progress", difference);
		SmartDashboard.putNumber("Current Angle", currangle);
		if (order == order_) {
			if (autoTimer.get() > .5) {
				// OI.geary.setPos(gear.position.UP);
				// if (Math.abs(LeftDistance) < Math.abs(RDistance)) {
				switch (direction) {
				case "right":
					SmartDashboard.putString("Direction", "Right");
					if (RDistance > 4) {
						Constants.rightfront.set(ControlMode.PercentOutput, jankPID(Constants.navx.getAngle(), goal, speed));
						Constants.leftfront.set(ControlMode.PercentOutput, -jankPID(Constants.navx.getAngle(), goal, speed));

					} else if (RDistance < 4 && RDistance > -4) {
						Constants.leftfront.set(ControlMode.PercentOutput, 0);
						Constants.rightfront.set(ControlMode.PercentOutput, 0);
						Constants.navx.reset();
						order_++;
						init = false;
					}

					break;
				case "left":
					// } else if (Math.abs(RDistance) < Math.abs(LeftDistance)) {
					SmartDashboard.putString("Direction", "Left");
					if (LeftDistance < -7) {
						Constants.leftfront.set(ControlMode.PercentOutput, jankPID(Constants.navx.getAngle(), goal, speed));
						Constants.rightfront.set(ControlMode.PercentOutput, -jankPID(Constants.navx.getAngle(), goal, speed));

					} else if (LeftDistance > -7 && LeftDistance < 7) {
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
			// OI.driver.SetUp(false);
			// goal = SmartDashboard.getNumber("Goal");
			// speed = SmartDashboard.getNumber("Speed");
			initiate = true;
		} else if (initiate == true) {
			oi.drive.SetUp(true);
			switch (autoMode) {
			case "Right":
				if (side1 == 'R') {
					timedrive(3, speed, 1);
					turnToAngle("left", 90, .3, 2);
					nothing(.5, 3);
					timedrive(1, speed, 4);
					cubeDeliver(.5, 5);
					timedrive(1, -speed, 6);
					turnToAngle("left", 90, .3, 7);
					timedrive(3, speed, 1);
					turnToAngle("right", 90, .3, 2);

				} else if (side1 == 'L') {
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
			case "Left":
				if (side1 == 'L') {
					timedrive(3, speed, 1);
					turnToAngle("right", 90, .3, 2);
					nothing(.5, 3);
					timedrive(1, speed, 4);
					cubeDeliver(.5, 5);
					timedrive(1, -speed, 6);
					turnToAngle("left", 90, .3, 7);
					timedrive(3, speed, 1);
					turnToAngle("right", 90, .3, 2);

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
					// Put left auto code here
					// 20 inches
					timedrive(.5, speed, 1);
					// nothing(.2, 2);
					turnToAngle("left", -15, .3, 2);
					// // // 76 inches
					timedrive(2, speed, 3);
					// turnToAngle(15, .3, 4, "right");
					// timedrive(.75, speed, 5);
				} else if (side1 == 'R') {
					// Put left auto code here
					// 20 inches
					timedrive(1, .6, 1);
					// turnToAngle("left", -45, .3, 2);
					turnToAngle("right", 45, .3, 2);
					// // // 76 inches
					timedrive(2.4, .7, 3);
					turnToAngle("left", -45, .3, 4);
					// // // 70 inches
					timedrive(1.35, .6, 5);
					// cubeDeliver(1, 6);
					// timedrive(1.35,-.6,7);
					// turnToAngle("left",-25,.3,8);
					// timedrive(1,.6,9);
					// turnToAngle("right",25,.3,10);
					// timedrive(1,.6,11);

					// // Put left auto code here
					// // 20 inches
					// timedrive(1, .6, 1);
					// turnToAngle(45, .3, 2, "right");
					// // // // 76 inches
					// timedrive(3.1, .7, 3);
					// turnToAngle(-45, .3, 4, "left");
					// // // // 70 inches
					// timedrive(1.25, .6, 5);
					// // deliver
				}

				break;
			case "turn":
				timedrive(.5, .3, 1);
				turnToAngle("left", -45, .3, 2);
				// timedrive(.25, .4, 2);

				break;
			case "test":
					Constants.climb.set(ControlMode.PercentOutput, jankPID( oi.drive.getEncDistance(), 50, .6));
				
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
