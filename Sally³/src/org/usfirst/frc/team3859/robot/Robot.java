package org.usfirst.frc.team3859.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.SensorCollection;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
//import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	// Encoder enco = new Encoder(1, 0, false, Encoder.EncodingType.k4X);
	Autonomous auto = new Autonomous();
	// Autonomous2 auto2 = new Autonomous2();
	String automode;
	String autoSelected;
	char game1;
	char game2;
	boolean init;
	SendableChooser<String> chooser = new SendableChooser<String>();
	XboxController xbox = new XboxController(1);
	final String middleAuto = "Middle";
	final String leftAuto = "Left";
	final String rightAuto = "Right";
	final String turnPID = "PID turn";
	SensorCollection sensor = new SensorCollection(Constants.armLeft);
	// public int percent = TalonSRX.ControlMode.PercentOutput();

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Default", "default");
		chooser.addObject("Middle ", middleAuto);
		chooser.addObject("Left", leftAuto);
		chooser.addObject("Right", rightAuto);
		SmartDashboard.putData("AutoMode", chooser);
//		sensor.setQuadraturePosition(0, 20000000);
		Compressor c = new Compressor(0);
		c.setClosedLoopControl(true);
		// CameraServer.getInstance().startAutomaticCapture(0);

		// SmartDashboard.putNumber("Left Distance", 0);
		// SmartDashboard.putNumber("Right Distance", 0);
		// drive.SetUp();
		// Constants.navx.reset();

	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString line to get the
	 * auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the SendableChooser
	 * make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		autoSelected = chooser.getSelected();
		System.out.println("Auto selected: " + autoSelected);
		// Constants.navx.reset();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		// char game1 = gameData.charAt(0);
		char game2 = gameData.charAt(1);
		// auto.autoset(autoSelected, game1, game2);
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		auto.oi.drive.SetUp(false);
		auto.oi.arm.setUp();
		auto.oi.enable();
		Constants.shootPneumatic.set(Value.kReverse);
		SmartDashboard.putNumber("Position", auto.oi.arm.getPosition());
//		double angle = (Constants.armLeft.getSelectedSensorPosition(0)) / 240;
		// if (init == false) {
		// // Constants.leftmiddle.reset
		// init = true;
		// }
		// Constants.rightmiddle.config
		// Constants.rightfront.setd
		// Constants.rightfront.getSelectedSensorPosition(arg0)
		// double position = .001 * (Constants.rightfront.getSelectedSensorPosition(0));
		// SmartDashboard.putNumber("Talon Position", position);
		// SmartDashboard.putNumber("Talon Velocity",
		// Constants.rightfront.getSelectedSensorVelocity(0));
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}
