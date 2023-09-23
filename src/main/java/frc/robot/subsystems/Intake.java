package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    TalonSRX right;
    TalonSRX left;

    public Intake(){
        right = new TalonSRX(8);
        left = new TalonSRX(7);
    }

    public void rollersIn(){
        right.set(ControlMode.PercentOutput, -0.65);
        left.set(ControlMode.PercentOutput, 0.65);
    }

    public void rollersOut(){
        right.set(ControlMode.PercentOutput, 1);
        left.set(ControlMode.PercentOutput, -1);
    }

    public void idle(){
        right.set(ControlMode.PercentOutput, -0.1);
        left.set(ControlMode.PercentOutput, 0.1);
    }

    public void stop(){
        right.set(ControlMode.PercentOutput, 0);
        left.set(ControlMode.PercentOutput, 0);
    }
}
