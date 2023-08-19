package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Compressor extends SubsystemBase{
    private final PneumaticsControlModule PCM;

    // 0 is intake out'
    // 1 is intake in
    // 2 is shifter A
    // 3 is shifter B
    // 

    public Compressor(){
        PCM = new PneumaticsControlModule(9);
        PCM.enableCompressorDigital();
        PCM.setOneShotDuration(2, 40);
        PCM.setOneShotDuration(3, 40);
    }

    public void lowGear(){
        PCM.fireOneShot(2);
        PCM.fireOneShot(2);
    }
    
    public void highGear(){
        PCM.fireOneShot(3);
        PCM.fireOneShot(3);
    }
}
