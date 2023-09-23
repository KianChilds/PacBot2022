// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.Drive;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Compressor;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;

public class RobotContainer {
  private final XboxController driveController = new XboxController(0);
  private final Drivetrain driveSubsystem = new Drivetrain();
  private final Compressor compressor = new Compressor();
  private final Intake intake = new Intake();
  private final Arm arm = new Arm();


  public RobotContainer() {
    configureBindings();
    compressor.lowGear();
    driveSubsystem.setDefaultCommand(new Drive(() -> -driveController.getLeftY(), () -> -driveController.getRightX()*0.5, driveSubsystem));
  }

  private void configureBindings() {
    new Trigger(() -> driveController.getRightBumper())
    .onTrue(Commands.runOnce(() -> compressor.highGear(), compressor))
    .onFalse(Commands.runOnce(() -> compressor.lowGear(), compressor));

    new Trigger(() -> driveController.getLeftBumper())
    .onTrue(Commands.runOnce(() -> compressor.toggleIntake(), compressor));

    new Trigger(() -> driveController.getLeftTriggerAxis() > 0.3)
    .onTrue(Commands.runOnce(intake::rollersIn, intake))
    .onFalse(Commands.runOnce(intake::idle, intake));

    new Trigger(() -> driveController.getRightTriggerAxis() > 0.3)
    .onTrue(Commands.runOnce(intake::rollersOut, intake))
    .onFalse(Commands.runOnce(intake::stop, intake));

    new Trigger(driveController::getXButton)
    .onTrue(Commands.runOnce(arm::gotoMid, arm));

    new Trigger(driveController::getAButton)
    .onTrue(Commands.runOnce(arm::toggleIntake, arm));
  }

  public Command getAutonomousCommand() {
    return new SequentialCommandGroup(
      Commands.runOnce(arm::gotoMid, arm),
      new WaitCommand(1.5),
      Commands.runOnce(intake::rollersOut, intake),
      new WaitCommand(1),
      Commands.runOnce(intake::stop, intake),
      new Drive(() -> {return -1;}, () -> {return 0;}, driveSubsystem).withTimeout(2)
    );
  }
}
