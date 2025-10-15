package frc.robot.subsystems;

import java.io.File;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import swervelib.SwerveDrive;
import swervelib.parser.SwerveParser;

 

public class DriveSubsystem extends SubsystemBase {
    SwerveDrive swerveDrive; 

    public DriveSubsystem(double maxSpeed) {
        try {
            swerveDrive = new SwerveParser(new File(Filesystem.getDeployDirectory(), "swerve")).createSwerveDrive(maxSpeed);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void drive(double xSpeed, double ySpeed, double rotSpeed) {
        swerveDrive.drive(new Translation2d(xSpeed, ySpeed), rotSpeed, true, false);;
    }
}
