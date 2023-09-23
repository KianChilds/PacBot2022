package frc.robot.subsystems;

import com.fasterxml.jackson.databind.ser.impl.FailingSerializer;

import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Compressor extends SubsystemBase{
    private final PneumaticsControlModule PCM;
    private boolean intakeOut = false;

    // 0 is intake out'
    // 1 is intake in
    // 2 is shifter A
    // 3 is shifter B
    // 

    public Compressor(){
        PCM = new PneumaticsControlModule(9);
        PCM.enableCompressorDigital();

        PCM.setOneShotDuration(0, 40);
        PCM.setOneShotDuration(1, 40);
        PCM.setOneShotDuration(2, 40);
        PCM.setOneShotDuration(3, 40);
    }

    public void intakeOut(){
        PCM.fireOneShot(0);
        intakeOut = true;
    }

    public void intakeIn(){
        PCM.fireOneShot(1);
        intakeOut = false;
    }

    public void lowGear(){
        PCM.fireOneShot(2);
    }
    
    public void highGear(){
        PCM.fireOneShot(3);
    }

    public void toggleIntake() {
        if(intakeOut) intakeIn();
        else intakeOut();
    }
}
