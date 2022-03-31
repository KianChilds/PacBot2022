// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.ResourceBundle.Control;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import frc.robot.subsystems.Drivetrain;

/*
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
 
  private DifferentialDrivetrainSim driveSim;
  private WPI_TalonSRX leftIntakeTalonSRX = new WPI_TalonSRX(7);
  private WPI_TalonSRX rightIntakeTalonSRX = new WPI_TalonSRX(8);
  private WPI_TalonSRX rotateArmTalonSRX = new WPI_TalonSRX(9);
  
  XboxController joystick = new XboxController(0); 

  private double autoLoopCount = 0;

  private final DoubleSolenoid mSolenoidArm = new DoubleSolenoid(9, PneumaticsModuleType.CTREPCM, 0, 1);
  private final DoubleSolenoid mSolenoidGear = new DoubleSolenoid(9, PneumaticsModuleType.CTREPCM, 2, 3);
 
  private boolean armsOpen;
  private boolean highGear;

  private Drivetrain drivetrain = new Drivetrain();

  @Override 
  public void robotInit() { 
    leftIntakeTalonSRX.configFactoryDefault();
    rightIntakeTalonSRX.configFactoryDefault();
    rotateArmTalonSRX.configFactoryDefault();

    // a boolean statement to make changing the Lead motors easier

    leftIntakeTalonSRX.setInverted(true); 
    rightIntakeTalonSRX.setInverted(false);

  }

  @Override
  public void teleopInit() {
    rotateArmTalonSRX.config_kP(0, .029971);
    rotateArmTalonSRX.config_kF(0, .013563);
    rotateArmTalonSRX.configMotionCruiseVelocity(20480);
    rotateArmTalonSRX.configMotionAcceleration(24094);

    rotateArmTalonSRX.set(ControlMode.PercentOutput, 0);

    armsOpen = false;
    highGear = false;

  }

  @Override
  public void teleopPeriodic() { 

    double x;
    double y;

   //shooter code

  //intake
  if(joystick.getLeftTriggerAxis() > 0.0) {

  leftIntakeTalonSRX.set(-.5);
  rightIntakeTalonSRX.set(-.5);
  
  }
  //shooter 
  else if(joystick.getRightTriggerAxis() > 0.0) {
    leftIntakeTalonSRX.set(1.0);
    rightIntakeTalonSRX.set(1.0);
    

  }
  else if(joystick.getLeftTriggerAxis() == 0.0 ||  joystick.getRightTriggerAxis()  == 0.0){
    leftIntakeTalonSRX.set(0);
    rightIntakeTalonSRX.set(0);
    


  }
  /*
  //Triggers solenoid to open
  if(joystick.getAButtonPressed()){
    mSolenoid.set(DoubleSolenoid.Value.kForward);
  }
  //Triggers solenoid to close
  else if(joystick.getBButtonPressed()){
    mSolenoid.set(DoubleSolenoid.Value.kReverse);
  }
  else if (joystick.getAButtonReleased() || joystick.getBButtonReleased()) {
    mSolenoid.set(DoubleSolenoid.Value.kOff);
  }
  */

  if(joystick.getRightBumperPressed()){
    if(armsOpen == false){
      mSolenoidArm.set(DoubleSolenoid.Value.kForward);
      armsOpen = true;
    }
    else{
      mSolenoidArm.set(DoubleSolenoid.Value.kReverse);
      armsOpen = false;
    }
  }
  else if(joystick.getRightBumperReleased()){
    mSolenoidArm.set(DoubleSolenoid.Value.kOff);
  }

  //Rotates arm down
  if(joystick.getPOV() == 180){
    rotateArmTalonSRX.set(ControlMode.MotionMagic, -523253);
  }
  //Rotates arm up
  else if(joystick.getPOV() == 0){
    rotateArmTalonSRX.set(ControlMode.MotionMagic, 0);
  }



  //Enter high gear
  if(joystick.getLeftBumperPressed() && highGear == false){
    mSolenoidGear.set(DoubleSolenoid.Value.kReverse);
    highGear = true;
  }
  //Exit high gear
  else if(joystick.getLeftBumperReleased()){
    mSolenoidGear.set(DoubleSolenoid.Value.kForward);
    highGear = false;
  }
  //Turn off solenoid
  else{
    mSolenoidGear.set(DoubleSolenoid.Value.kOff);
  }

  //Drivetrain Stuff
  drivetrain.drive(joystick.getLeftY(), joystick.getRightX(), highGear);
 }

 @Override
 public void autonomousInit() {
  //  Auto-generated method stub
   super.autonomousInit();
   this.autoLoopCount = 0; 
 }
 
 //autonomous drive
 @Override
  public void autonomousPeriodic() {
    
  }
}