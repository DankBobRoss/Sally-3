package org.usfirst.frc.team3859.robot;

public class PIDcontrol {
	double kP,kI,kD,PrevError = 0,Error = 0,result,deltaError;
	double sum = 0; ;
	/**
	 * 
	 * @param P
	 * @param I
	 * @param D
	 */
	PIDcontrol(double P,double I,double D){
		kP = P;
		kI = I;
		kD = D;
	}
	/**
	 * 
	 * 
	 * @param P
	 * @param I
	 * @param D
	 */
	public void setPID(double P,double I,double D) {
		kP = P;
		kI = I;
		kD = D;
	}
	
	public double calculate(double error) {
		Error = error;
		sum += Error;
		deltaError = Error - PrevError;
		
		result = (kP * error) + (kI * sum) + (kD * deltaError);
		PrevError = error;
		return result;
	}
	
}
