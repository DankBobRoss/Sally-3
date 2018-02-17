package org.usfirst.frc.team3859.robot;
import java.util.List;

import edu.wpi.first.wpilibj.RobotState;

//import edu.wpi.first.wpilibj.RobotState;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoRunner {

	public void Run(Auto state) {
		state.Init();
		//SmartDashboard.putBoolean("robotstate", RobotState.isAutonomous());
		//while(state.isFinished()==false&&RobotState.isAutonomous()) {
		while(state.isFinished()==false && RobotState.isAutonomous()) {
		state.Run();
		
		}
		state.Finished();	
	}
	public void Run(List<Auto>Autos) {
		int i=0;
		for(Auto CurrentRoutine:Autos) {
			CurrentRoutine.Init();
		}
		while(Autos.size()!=i && RobotState.isAutonomous()) {
			i=0; //reset count variable
			//System.out.println("i is now 0");
			for(Auto CurrentRoutine:Autos) {
				if(CurrentRoutine.isFinished()==false) {
					CurrentRoutine.Run();
				}
				if(CurrentRoutine.isFinished()==true) {
					CurrentRoutine.Finished();
					i++;
					//System.out.println("i is ");System.out.println (i);
				}
			
			}
		
		}
	
		//System.out.println("escaped the while loop");
	}

}
