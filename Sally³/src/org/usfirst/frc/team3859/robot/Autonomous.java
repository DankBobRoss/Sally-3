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
	double goal, speed;
	double output = 0;
	int times = 0;
	double progress;
	boolean initt = false;

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
				Constants.rightfront.set(ControlMode.PercentOutput, -speed - .03);
				Constants.leftfront.set(ControlMode.PercentOutput, speed);
			} else {
				Constants.leftfront.set(ControlMode.PercentOutput, 0);
				Constants.rightfront.set(ControlMode.PercentOutput, 0);
				order_++;
				init = false;
				// SmartDashboard.putNumber("Auto Current Order", order);
			}
		}
	}

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
	// // pid.setMaxIOutput(.9);
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
	 * 
	 * @param direction
	 *            (left/right)
	 * @param goal
	 * @param speed
	 * @param order
	 * 
	 */

	/*
	 * void angle(String direction, double goal, double speed, double order) { if
	 * (init == false) { if (order == 1) { autoTimer2.start(); // order_ = 1; }
	 * Constants.navx.reset(); // Constants.climb.set(ControlMode.PercentOutput,.5);
	 * // Constants.navx.zeroYaw(); init = true; // goal = goal - 5; // goal = goal
	 * + 20; } oi.drive.SetUp(); double negative = speed * -1; // currangle =
	 * Constants.navx.getAngle(); currangle = Constants.navx.getAngle(); double
	 * RDistance; double NegGoal = goal - 360; double LeftDistance = NegGoal -
	 * currangle; double progress = currangle / goal; double difference = goal -
	 * currangle; LeftDistance = LeftDistance * -1; LeftDistance = LeftDistance %
	 * 360; LeftDistance = LeftDistance * -1; if (difference < 0) { RDistance = goal
	 * - currangle; RDistance = RDistance * -1; RDistance = RDistance % 360;
	 * RDistance = RDistance * -1; RDistance = RDistance + 360;
	 * SmartDashboard.putNumber("RightDistance", RDistance); } else { RDistance =
	 * goal - currangle; SmartDashboard.putNumber("RightDistance", RDistance); }
	 * SmartDashboard.putNumber("Left Distance", LeftDistance);
	 * SmartDashboard.putNumber("Current Angle", currangle); if (order == order_) {
	 * // if (Math.abs(LeftDistance) < Math.abs(RDistance)) { switch (direction) {
	 * case "left": SmartDashboard.putString("Direction", "Left"); if (LeftDistance
	 * < -4) { if (progress < .5) {
	 * Constants.leftfront.set(ControlMode.PercentOutput, speed);
	 * Constants.rightfront.set(ControlMode.PercentOutput, -speed); } else if
	 * (progress > .5 && progress < .6) {
	 * Constants.leftfront.set(ControlMode.PercentOutput, speed - .9);
	 * Constants.rightfront.set(ControlMode.PercentOutput, -speed + .9); } else if
	 * (progress > .6 && progress < .7) {
	 * Constants.leftfront.set(ControlMode.PercentOutput, speed - .15);
	 * Constants.rightfront.set(ControlMode.PercentOutput, -speed + .15); } else if
	 * (progress > .8 && progress < .85) {
	 * Constants.leftfront.set(ControlMode.PercentOutput, speed - .18);
	 * Constants.rightfront.set(ControlMode.PercentOutput, -speed + .18); } else if
	 * (progress > .85 && progress < .9) {
	 * Constants.leftfront.set(ControlMode.PercentOutput, speed - .2);
	 * Constants.rightfront.set(ControlMode.PercentOutput, -speed + .2); } else if
	 * (progress > .9) { Constants.leftfront.set(ControlMode.PercentOutput, speed -
	 * .24); Constants.rightfront.set(ControlMode.PercentOutput, -speed + .24);
	 * SmartDashboard.putNumber("Left Distance", LeftDistance);
	 * SmartDashboard.putNumber("Right Distance", RDistance); } } else if
	 * (LeftDistance > -4 && LeftDistance < 4) {
	 * Constants.leftfront.set(ControlMode.PercentOutput, 0);
	 * Constants.rightfront.set(ControlMode.PercentOutput, 0);
	 * Constants.navx.reset(); order_++; init = false; } break; case "right": // }
	 * else if (Math.abs(RDistance) < Math.abs(LeftDistance)) {
	 * SmartDashboard.putString("Direction", "Right"); if (RDistance > 4) { if
	 * (progress < .5) { Constants.leftfront.set(ControlMode.PercentOutput, -speed);
	 * Constants.rightfront.set(ControlMode.PercentOutput, speed); } else if
	 * (progress > .5 && progress < .6) {
	 * Constants.leftfront.set(ControlMode.PercentOutput, -speed + .09);
	 * Constants.rightfront.set(ControlMode.PercentOutput, speed - .09); } else if
	 * (progress > .6 && progress < .7) {
	 * Constants.leftfront.set(ControlMode.PercentOutput, -speed + .15);
	 * Constants.rightfront.set(ControlMode.PercentOutput, speed - .15); } else if
	 * (progress > .8 && progress < .85) {
	 * Constants.leftfront.set(ControlMode.PercentOutput, -speed + .18);
	 * Constants.rightfront.set(ControlMode.PercentOutput, speed - .18); } else if
	 * (progress > .85 && progress < .9) {
	 * Constants.leftfront.set(ControlMode.PercentOutput, -speed + .2);
	 * Constants.rightfront.set(ControlMode.PercentOutput, speed - .2); } else if
	 * (progress > .9) { Constants.leftfront.set(ControlMode.PercentOutput, -speed +
	 * .24); Constants.rightfront.set(ControlMode.PercentOutput, speed - .24);
	 * SmartDashboard.putNumber("Left Distance", LeftDistance);
	 * SmartDashboard.putNumber("Right Distance", RDistance); } } else if (RDistance
	 * < 4 && RDistance > -4) { Constants.leftfront.set(ControlMode.PercentOutput,
	 * 0); Constants.rightfront.set(ControlMode.PercentOutput, 0);
	 * Constants.navx.reset(); order_++; init = false; } break; } } }
	 */
	public double jankPID(double current, double goal, double speed, double distance) {
		double totaldis = 0, togodis;
		if (initt == false) {
			totaldis = Math.abs(goal - current);
			initt = true;
		}
		togodis = Math.abs(goal - current);
		double progress = togodis / totaldis;
		if (progress <= .2) {
			output = speed;
		} else if (progress > .1 && progress <= .85) {
			if (progress > .2 && progress <= .3) {
				output = speed * .85;
			} else if (progress > .3 && progress <= .4) {
				output = speed * .80;
			} else if (progress > .4 && progress <= .5) {
				output = speed * .70;
			} else if (progress > .5 && progress <= .6) {
				output = speed * .55;
			} else if (progress > .6 && progress <= .7) {
				output = speed * .40;
			} else if (progress > .7 && progress <= .8) {
				output = speed * .30;
			} else if (progress > .8 && progress <= .85) {
				output = speed * .2;
			}
		} else if (progress > .85 || togodis <= 20) {
			if(togodis <20 && togodis >=15) {
				output = .2;
			}if(togodis <15 && togodis >=10) {
				output = .15;
			}if(togodis <10 && togodis >=5) {
				output = .1;
			}
		}
		return output;
	}

	/**
	 * 
	 * 
	 * 
	 * @param direction
	 *            (left/right)
	 * @param goal
	 * @param speed
	 * @param order
	 * 
	 */

	void angle(String direction, double goal, double speed, double order) {
		if (init == false) {
			if (order == 1) {
				autoTimer.start();
				// order_ = 1;
			}
			Constants.navx.reset();
			// Constants.climb.set(ControlMode.PercentOutput,.5);
			// Constants.navx.zeroYaw();
			init = true;
			// goal = goal - 5;
			// goal = goal + 20;
		}
		oi.drive.SetUp();
		double negative = speed * -1;
		// currangle = Constants.navx.getAngle();
		currangle = Constants.navx.getAngle();
		double RDistance;
		double NegGoal = goal - 360;
		double LeftDistance = NegGoal - currangle;
		double divident = currangle / goal;
		double difference = goal - currangle;
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
		SmartDashboard.putNumber("Current Angle", currangle);
		if (order == order_) {
			// if (Math.abs(LeftDistance) < Math.abs(RDistance)) {
			switch (direction) {
			case "left":
				SmartDashboard.putString("Direction", "Left");
				if (LeftDistance < -4) {
					Constants.leftfront.set(ControlMode.PercentOutput, -jankPID(currangle, goal, speed, LeftDistance));
					Constants.rightfront.set(ControlMode.PercentOutput, jankPID(currangle, goal, speed, LeftDistance));
					SmartDashboard.putNumber("Left Distance", LeftDistance);
					SmartDashboard.putNumber("Right Distance", RDistance);
					SmartDashboard.putNumber("JankPid", -jankPID(currangle, goal, speed, LeftDistance));
				} else if (LeftDistance > -4 && LeftDistance < 4) {
					Constants.leftfront.set(ControlMode.PercentOutput, 0);
					Constants.rightfront.set(ControlMode.PercentOutput, 0);
					Constants.navx.reset();
					order_++;
					init = false;
				}
				break;
			case "right":
				// } else if (Math.abs(RDistance) < Math.abs(LeftDistance)) {
				SmartDashboard.putString("Direction", "Right");
				if (RDistance > 4) {
					Constants.leftfront.set(ControlMode.PercentOutput, jankPID(currangle, goal, speed, RDistance));
					Constants.rightfront.set(ControlMode.PercentOutput, -jankPID(currangle, goal, speed, RDistance));
					SmartDashboard.putNumber("Left Distance", LeftDistance);
					SmartDashboard.putNumber("Right Distance", RDistance);
					SmartDashboard.putNumber("JankPid", jankPID(currangle, goal, speed, RDistance));
				} else if (RDistance < 4 && RDistance > -4) {
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

	/**
	 * 
	 * 
	 * @param time
	 * @param speed
	 * @param order
	 */
	public void leftTurn(double time, double speed, double order) {
		if (init == false) {
			if (order == 1) {
				autoTimer.start();
				order_ = 1;
			}
			autoTimer.reset();
			init = true;
		}
		if (order == order_) {
			if (autoTimer.get() < time) {
				Constants.leftfront.set(ControlMode.PercentOutput, speed);
				Constants.rightfront.set(ControlMode.PercentOutput, -speed);

			} else if (autoTimer.get() >= time) {
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
	public void rightTurn(double time, double speed, double order) {
		if (init == false) {
			if (order == 1) {
				autoTimer.start();
				order_ = 1;
			}
			autoTimer.reset();
			init = true;
		}
		if (order == order_) {
			if (autoTimer.get() < time) {
				// Constants.lefttfront.set(ControlMode.PercentOutput,-speed);
				Constants.rightfront.set(ControlMode.PercentOutput, speed);
			} else if (autoTimer.get() >= time) {
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
			switch (autoMode) {
			case "Right":
				if (side1 == 'R') {
					timedrive(3, .75, 1);
					angle("left", 90, .3, 2);
					nothing(.5, 3);
					timedrive(1, .6, 4);
					cubeDeliver(.5, 5);
					timedrive(1, -.6, 6);
					angle("left", 90, .3, 7);
					timedrive(3, .75, 1);
					angle("right", 90, .3, 2);

				} else if (side1 == 'L') {
					timedrive(5, .7, 1);
					angle("right", 90, .3, 2);
					timedrive(7, .7, 3);
					angle("right", 90, .3, 4);
					timedrive(1.1, .7, 5);
					angle("right", 90, .3, 6);
					timedrive(1, .7, 7);
					cubeDeliver(1, 8);
				}

				break;
			case "Left":
				if (side1 == 'L') {
					timedrive(3, .75, 1);
					angle("right", 90, .3, 2);
					nothing(.5, 3);
					timedrive(1, .6, 4);
					cubeDeliver(.5, 5);
					timedrive(1, -.6, 6);
					angle("left", 90, .3, 7);
					timedrive(3, .75, 1);
					angle("right", 90, .3, 2);

				} else if (side1 == 'R') {
					timedrive(5, .7, 1);
					angle("right", 90, .3, 2);
					timedrive(7, .7, 3);
					angle("right", 90, .3, 4);
					timedrive(1.1, .7, 5);
					angle("right", 90, .3, 6);
					timedrive(1, .7, 7);
					cubeDeliver(1, 8);
				}
				break;
			case "Middle":
				if (side1 == 'L') {
					// Put left auto code here
					// 20 inches
					timedrive(1, .6, 1);
					angle("left", -45, .3, 2);
					// // // 76 inches
					timedrive(2.4, .7, 3);
					angle("right", 45, .3, 4);
					// // // 70 inches
					timedrive(1.35, .6, 5);
					cubeDeliver(1, 6);
					// timedrive(1.35,-.6,7);
					// angle("left",-25,.3,8);
					// timedrive(1,.6,9);
					// angle("right",25,.3,10);
					// timedrive(1,.6,11);
				} else if (side1 == 'R') {
					// Put left auto code here
					// 20 inches
					timedrive(1, .6, 1);
					angle("right", 45, .3, 2);
					// // 76 inches
					timedrive(3.1, .7, 3);
					angle("left", -45, .3, 4);
					// // 70 inches
					timedrive(1.25, .6, 5);
					// deliver
				}

				break;
			case "turn":
				angle("left", -90, .4, 1);

				break;
			}
			if (order_ > 10) {
				autoMode = "test";
			}
		}
	}
}
