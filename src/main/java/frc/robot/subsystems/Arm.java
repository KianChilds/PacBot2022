package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {
    private static final int LOWER_POSE = -502819;
    private static final int MID_POSE = -254927;
    private static final int UPPER_POSE = -112386;

    private TalonSRX armSrx = new TalonSRX(9);
    private boolean atUpperPose = true;

    public Arm(){
        armSrx.config_kP(0, 0.031385);
        armSrx.config_kI(0, 0);
        armSrx.config_kD(0, 0);
        armSrx.config_kF(0, 0.023019);
        armSrx.configMotionAcceleration(228165);
        armSrx.configMotionCruiseVelocity(228165);
        gotoUpper();
        
    }

    public void gotoUpper(){
        armSrx.set(ControlMode.MotionMagic, UPPER_POSE);
        atUpperPose = true;
    }

    public void gotoMid(){
        armSrx.set(ControlMode.MotionMagic, MID_POSE);
        atUpperPose = false;
    }

    public void gotoLower(){
        armSrx.set(ControlMode.MotionMagic, LOWER_POSE);
        atUpperPose = false;
    }

    public void toggleIntake(){
        if(atUpperPose) gotoLower();
        else gotoUpper();
    }
}
