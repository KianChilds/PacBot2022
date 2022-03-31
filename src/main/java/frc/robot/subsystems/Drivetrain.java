package frc.robot.subsystems;

import javax.swing.text.DefaultStyledDocument.ElementSpec;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class Drivetrain {
    
    private WPI_TalonSRX leftLead = new WPI_TalonSRX(1); 
    private WPI_VictorSPX leftFollower1 = new WPI_VictorSPX(2);
    private WPI_VictorSPX leftFollower2 = new WPI_VictorSPX(3);
    private WPI_TalonSRX rightLead = new WPI_TalonSRX(4);
    private WPI_VictorSPX rightFollower1 = new WPI_VictorSPX(5);
    private WPI_VictorSPX rightFollower2 = new WPI_VictorSPX(6);
    private int maxVelocity;
    private int turning;

    public Drivetrain(){
        
        rightLead.configFactoryDefault(); 
        rightFollower1.configFactoryDefault();
        rightFollower2.configFactoryDefault();
        leftLead.configFactoryDefault();
        leftFollower1.configFactoryDefault();
        leftFollower2.configFactoryDefault();

        leftFollower1.follow(leftLead);
        leftFollower2.follow(leftLead);
        rightFollower1.follow(rightLead);
        rightFollower2.follow(rightLead);

        boolean leftInvert = false;
        boolean rightInvert = true;

        leftLead.setInverted(leftInvert);
        rightLead.setInverted(rightInvert);
        
        leftFollower1.setInverted(leftInvert);
        leftFollower2.setInverted(leftInvert);
        rightFollower1.setInverted(rightInvert);
        rightFollower2.setInverted(rightInvert);

        leftLead.setSensorPhase(false);
        rightLead.setSensorPhase(false);

        leftLead.config_kF(0, 0.13884);
        leftLead.config_kP(0, 0.0);
        rightLead.config_kF(0, 0.13884);
        rightLead.config_kP(0, 0.0);
        leftLead.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 20);
        rightLead.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 20);
        
        maxVelocity = 7861;
    }

    public void drive(double y, double x, boolean highGear){

        if(highGear){
            maxVelocity = 30000;
            leftLead.config_kF(0, 0.028998);
            leftLead.config_kP(0, 0.021277);
            rightLead.config_kF(0, 0.028998);
            rightLead.config_kP(0, 0.021277);
        }
        else{
            maxVelocity = 6681;
            leftLead.config_kF(0,0.13884);
            leftLead.config_kP(0, 0.153099);
            rightLead.config_kF(0, 0.13884);
            rightLead.config_kP(0, 0.153099);
        }

        y = setDeadband(y);
        x = setDeadband(x);

        leftLead.set(ControlMode.Velocity, (y*maxVelocity)+(x*-maxVelocity/3));
        rightLead.set(ControlMode.Velocity, (y*maxVelocity)+(x*maxVelocity/3));
    }

    public double setDeadband(double num){
        if(Math.abs(num)<.1){
            num = 0;
        }
        else{
            if(num > 0)
                num = 1.11*(num-.1);
            else
                num = 1.11*(num+.1);
        }
        if(num > 1.0)
            num = 1.0;
        else if(num < -1.0)
            num = -1.0;
        return num;
    }

}
