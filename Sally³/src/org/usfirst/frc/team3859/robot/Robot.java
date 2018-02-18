package org.usfirst.frc.team3859.robot;

import org.usfirst.frc.team3859.robot.Constants.armPos;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.SensorCollection;

import edu.wpi.cscore.UsbCamera;
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
	boolean init = false;;

	SendableChooser<String> chooser = new SendableChooser<String>();
	SendableChooser<String> autoChoice = new SendableChooser<String>();

	// UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(0);

	final String middleAuto = "Middle";
	final String leftAuto = "Left";
	final String rightAuto = "Right";
	final String turnPID = "PID turn";
	// public int percent = TalonSRX.ControlMode.PercentOutput();

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		double voltage = .14;
		double stuff = Math.cos(((Constants.armLeft.getSelectedSensorPosition(0) / 240) * Math.PI) / 2048) * voltage;
		SmartDashboard.putNumber("Stuff", stuff);

		autoChoice.addDefault("Deliver", "deliver");
		autoChoice.addObject("Cross", "cross");
		autoChoice.addObject("Test", "test");

		chooser.addDefault("Default", "default");
		chooser.addObject("Middle ", middleAuto);
		chooser.addObject("Left", leftAuto);
		chooser.addObject("Right", rightAuto);
		SmartDashboard.putData("AutoMode", chooser);
		SmartDashboard.putData("Auto Choice", autoChoice);
		Constants.c.setClosedLoopControl(true);
		// camera.setResolution(1080, 1080);
		CameraServer.getInstance().startAutomaticCapture(0);

	}

	@Override
	public void autonomousInit() {
		int startAngle = 57;
		startAngle = ((startAngle * 240) / 90) * 1024;

		SmartDashboard.putNumber("Start Angle", startAngle);

		auto.oi.drive.setUp(true);
		Constants.navx.reset();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		if (init == false) {
			// Constants.armSensor.setQuadraturePosition(startAngle, 200); // this is where
			// we set up the starting config
			Constants.armSensor.setQuadraturePosition(0, 200);
			init = true;
		}
		// auto.oi.arm.set(armPos.INTAKE);
		if (Constants.sharp.getValue() > 500) {
			SmartDashboard.putBoolean("Cube Present?", true);
		} else {
			SmartDashboard.putBoolean("Cube Present?", false);
		}
		SmartDashboard.putNumber("NavX Angle", Constants.navx.getAngle());
		SmartDashboard.putNumber("Right Enc", auto.oi.drive.getRightEncDistance());
		SmartDashboard.putNumber("Left Enc", auto.oi.drive.getLeftEncDistance());
		Constants.PTO.set(Value.kForward);

		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		game1 = gameData.charAt(0);
		game2 = gameData.charAt(1);

		auto.autoset(chooser.getSelected(), autoChoice.getSelected(), game1, game2);
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		int startAngle = 57;
		startAngle = ((startAngle * 240) / 90) * 1024;

		SmartDashboard.putNumber("Start Angle", startAngle);
		SmartDashboard.putNumber("Arm Position RAW", Constants.armLeft.getSelectedSensorPosition(0));
		if (init == false) {
			Constants.armSensor.setQuadraturePosition(0, 200);
			init = true;
		}
		auto.oi.drive.setUp(false);
		auto.oi.arm.setUp();
		auto.oi.enable();

		SmartDashboard.putNumber("Position", auto.oi.arm.getPosition());
		SmartDashboard.putNumber("Right Enc", auto.oi.drive.getRightEncDistance());
		SmartDashboard.putNumber("Left Enc", auto.oi.drive.getLeftEncDistance());
		SmartDashboard.putNumber("NavX Angle", Constants.navx.getAngle());

		if (Constants.sharp.getValue() > 500) {
			SmartDashboard.putBoolean("Cube Present?", true);
		} else {
			SmartDashboard.putBoolean("Cube Present?", false);
		}

		SmartDashboard.putNumber("Left Enc Native", Constants.leftFront.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Right Enc Native", Constants.rightFront.getSelectedSensorPosition(0));
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}
