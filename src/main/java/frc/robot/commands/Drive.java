package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class Drive extends CommandBase {
    private final Drivetrain subsystem;
    private final DoubleSupplier xSupplier; 
    private final DoubleSupplier ySupplier;

    public Drive(DoubleSupplier xSupplier, DoubleSupplier ySupplier, Drivetrain subsystem){
        this.subsystem = subsystem;
        this.xSupplier = xSupplier;
        this.ySupplier = ySupplier;
        addRequirements(subsystem);
    }
    

    @Override
    public void execute(){
        subsystem.demand(ySupplier.getAsDouble() + xSupplier.getAsDouble(), ySupplier.getAsDouble() - xSupplier.getAsDouble());
    }
}
