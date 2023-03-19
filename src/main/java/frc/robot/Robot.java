// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
//Carter's weird and smelly
//import com.ctre.phoenix.motorcontrol.can.VictorSPX;

//import com.ctre.phoenix.motorcontrol.ControlMode;
//This is a test
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
//import edu.wpi.first.wpilibj.PneumaticsBase;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.cameraserver.CameraServer;
//import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;



/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private final Timer m_timer = new Timer();
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private PWMVictorSPX leftDrive = new PWMVictorSPX(0);
  private PWMVictorSPX rightDrive = new PWMVictorSPX(1);
  private PWMVictorSPX feederDrive = new PWMVictorSPX(3);
  private PWMVictorSPX liftDrive = new PWMVictorSPX(4);
  // private PWMVictorSPX pivotDrive = new PWMVictorSPX(5);
  // private PWMVictorSPX extenderDrive = new PWMVictorSPX(2);
 //The number correspond to roborio 
 //motors

private Joystick myJoystick = new Joystick(0); //create joystick
private Joystick yourJoystick = new Joystick(1);

private DifferentialDrive myDrive = new DifferentialDrive(leftDrive, rightDrive); //Tells what motor controllers to use
//private final Compressor comp = new Compressor(PneumaticsModuleType.CTREPCM);
private final DoubleSolenoid  doublesolenoid1 = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1); 
private final DoubleSolenoid  doublesolenoid2 = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 2, 3); 
/**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    CameraServer.startAutomaticCapture();
    //rightDrive.setinverted(true);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
   //get a time for auton start to do events based on time later 
  m_timer.reset();
  m_timer.start();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }

   if (m_timer.get() < 2) { //this is spot 1 & 2
        feederDrive.set(1);}
    else{
    feederDrive.stopMotor();
    }

     if (m_timer.get() > 2.0) {
      leftDrive.set(0.26);
      rightDrive.set(-0.26);
    } 
    if (m_timer.get() > 6) {
    leftDrive.set(0);
    rightDrive.set(0); 
    }

// CODE FOR ALTERNATE AUTONOMOUS POSITIONS
    //   if (m_timer.get() < 2) { //this is spot 3
//       feederDrive.set(1);}
//   else{
//   feederDrive.stopMotor();
//   }
//   if (m_timer.get() > 2.5) {
//     leftDrive.set(0.35);
//     rightDrive.set(-0.35);
//   }
//   if (m_timer.get() >3.6) {
//   leftDrive.set(0);
//   rightDrive.set(0);}

// if (m_timer.get() > 4) {
// leftDrive.set(.5);
// rightDrive.set(.5);}

// if (m_timer.get() > 5.2) {
//   leftDrive.set(0);
//   rightDrive.set(0);}

// if (m_timer.get() > 6.5) {
//   leftDrive.set(0.26);
//   rightDrive.set(-0.26);}
// if (m_timer.get() > 10.5) {
//   leftDrive.set(0);
//   rightDrive.set(0);
// }
}



  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

  myDrive.arcadeDrive(myJoystick.getRawAxis(0), myJoystick.getRawAxis(1));
  //myDrive.arcadeDrive(yourJoystick.getRawAxis(0), yourJoystick.getRawAxis(1));

//FEEDER DRIVE CODE
if (myJoystick.getRawButton(1))//This is A
{feederDrive.set(.8);}
else if (myJoystick.getRawButton(2)) //This is B
{feederDrive.set(-.8);}
else {feederDrive.set(0);}

//LIFT DRIVE CODE
   if (yourJoystick.getRawButton(3)) //This is Button x or y??
    {liftDrive.set(-1);}
     else if (yourJoystick.getRawButton(4)) //This is Button x or y??
     {liftDrive.set(1);}
      else {liftDrive.set(0);}
  

    
//OLD ELECTRIC ACTUATOR CODE FOR PIVOT AND EXTEND
      // if (yourJoystick.getRawButton(1)) //This is A
      // {pivotDrive.set(-1);}
      //  else if (yourJoystick.getRawButton(2)) //This is B
      // {pivotDrive.set(1);}
      //   else {pivotDrive.set(0);}

      //  if (yourJoystick.getRawButton(5)) //This is LBumper
      //   {extenderDrive.set(1);}
      //    else if (yourJoystick.getRawButton(6)) //This is RBumper
      //    {extenderDrive.set(-1);}
      //     else {extenderDrive.set(0);}
          
//NEW PNEUMATICS CODE FOR PIVOT AND EXTEND

//EXTENDER CODE
     if (yourJoystick.getRawButton(5)) //This is LBumper
     {
       doublesolenoid1.set(DoubleSolenoid.Value.kForward);
     } 
     else if(yourJoystick.getRawButton(6)) //This is RBumper
     {
       doublesolenoid1.set(DoubleSolenoid.Value.kReverse);
     }
//COMPRESSOR MANUAL CONTROL CODE
//     if (yourJoystick.getRawButton(3))
//     {
//       comp.enableDigital();
//     }
//     else if (yourJoystick.getRawButton(4));
//     {
//       comp.disable();
//     }

//PIVOT CODE
     if (yourJoystick.getRawButton(1)) //Button A - Down
     {
       doublesolenoid2.set(DoubleSolenoid.Value.kForward);
     }
     else if(yourJoystick.getRawButton(2))  //Button B - Up
     {
       doublesolenoid2.set(DoubleSolenoid.Value.kReverse);
     }
    }
  

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}}

  // /** This function is called once when test mode is enabled. */
  // @Override
  // public void testInit() {
  //   m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
   // System.out.println("Auto selected: " + m_autoSelected);
   //get a time for auton start to do events based on time later 
  //m_timer.reset();
  //m_timer.start();

