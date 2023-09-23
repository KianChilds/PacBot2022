package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {    
    TalonSRX l1Srx;
    VictorSPX l2Vic;
    VictorSPX l3Vic;

    TalonSRX r1Srx;
    VictorSPX r2Vic;
    VictorSPX r3Vic;
    
    public Drivetrain(){
        l1Srx = new TalonSRX(1);
        l2Vic = new VictorSPX(2);
        l3Vic = new VictorSPX(3);

        r1Srx = new TalonSRX(4);
        r2Vic = new VictorSPX(5);
        r3Vic = new VictorSPX(6);

        l2Vic.follow(l1Srx);
        l3Vic.follow(l1Srx);

        r2Vic.follow(r1Srx);
        r3Vic.follow(r1Srx);
    }

    public void update(){

    }

    public void demand(double rSpeed, double lSpeed){
        r1Srx.set(TalonSRXControlMode.Velocity, 32000*rSpeed);
        l1Srx.set(TalonSRXControlMode.Velocity, 32000*lSpeed);
    }
}
