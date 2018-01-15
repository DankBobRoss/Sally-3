package org.usfirst.frc.team3859.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Sensor {
	 

	// Encoder enceo = new Encoder(null, null, false, EncodingType.k4X);
	AnalogInput sharp = new AnalogInput(1);

	public void encoder() {
//		double count = enco.get();
//		SmartDashboard.putNumber("encValue", count);
//
//		enco.setMaxPeriod(1);
//		enco.setMinRate(1);
//		enco.setDistancePerPulse(4);
//		enco.setReverseDirection(true);
//		enco.setSamplesToAverage(7);
		
//		Constants.leftmiddle.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
//		double position = Constants.leftmiddle.getSelectedSensorPosition(0);
//		SmartDashboard.putNumber("TALON ENCODER", position);
	}

	public void sharp() {

		int raw = sharp.getValue();

		SmartDashboard.putNumber("Sharp", raw);
	}
}
