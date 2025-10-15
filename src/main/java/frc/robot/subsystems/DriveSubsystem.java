package frc.robot.subsystems;

import java.io.File;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.DefaultDriveCommand;
import swervelib.SwerveDrive;
import swervelib.parser.SwerveParser;
import swervelib.telemetry.SwerveDriveTelemetry;
import swervelib.telemetry.SwerveDriveTelemetry.TelemetryVerbosity;

 

public class DriveSubsystem extends SubsystemBase {
    SwerveDrive swerveDrive; 

    public DriveSubsystem(double maxSpeed) {
        try {
            swerveDrive = new SwerveParser(new File(Filesystem.getDeployDirectory(), "swerve")).createSwerveDrive(maxSpeed);
            System.out.println("Swerve Created");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.setDefaultCommand(new DefaultDriveCommand(this));
        SwerveDriveTelemetry.verbosity = TelemetryVerbosity.HIGH;

    }

    public void drive(double xSpeed, double ySpeed, double rotSpeed) {
        swerveDrive.drive(new Translation2d(xSpeed, ySpeed), rotSpeed, true, false);;
    }
}
