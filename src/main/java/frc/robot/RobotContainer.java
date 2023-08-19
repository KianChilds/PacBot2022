// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.Drive;
import frc.robot.subsystems.Compressor;
import frc.robot.subsystems.Drivetrain;

public class RobotContainer {
  private final XboxController driveController = new XboxController(0);
  private final Drivetrain driveSubsystem = new Drivetrain();
  private final Compressor compressor = new Compressor();


  public RobotContainer() {
    configureBindings();
    compressor.lowGear();
    driveSubsystem.setDefaultCommand(new Drive(() -> -driveController.getLeftY(), driveController::getRightX, driveSubsystem));
  }

  private void configureBindings() {
    new Trigger(() -> driveController.getRightBumper())
    .onTrue(Commands.runOnce(() -> compressor.highGear(), compressor))
    .onFalse(Commands.runOnce(() -> compressor.lowGear(), compressor));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
