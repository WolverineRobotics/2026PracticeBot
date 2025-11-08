package frc.robot.subsystems;

import java.io.File;
import java.security.PublicKey;
import java.util.Optional;

import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;
import frc.robot.Constants.DriveConstants;
import frc.robot.LimelightHelpers.PoseEstimate;
import frc.robot.commands.DefaultDriveCommand;
import swervelib.SwerveDrive;
import swervelib.parser.SwerveParser;
import swervelib.telemetry.SwerveDriveTelemetry;
import swervelib.telemetry.SwerveDriveTelemetry.TelemetryVerbosity;

 

public class DriveSubsystem extends SubsystemBase {
    // Declaring Constants
    private SwerveDrive swerveDrive; 
    private final double maxSpeed;
    private final String name = "limelight";


    


    public DriveSubsystem(double maxSpeed) {
        // Creating swerve drive from YAGSL config
        try {
            swerveDrive = new SwerveParser(new File(Filesystem.getDeployDirectory(), "swerve")).createSwerveDrive(maxSpeed);
            System.out.println("Swerve Created");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Setting default Command and max speed
        this.setDefaultCommand(new DefaultDriveCommand(this));
        this.maxSpeed = maxSpeed;
        SwerveDriveTelemetry.verbosity = TelemetryVerbosity.HIGH;
        //poseEstimator.addVisionMeasurement(null, maxSpeed);
       
          


    }

    public void drive(double xSpeed, double ySpeed, double rotSpeed) {
        swerveDrive.drive(new Translation2d(xSpeed * maxSpeed , ySpeed * maxSpeed), rotSpeed * (Math.PI / 2), true, false);
    }

    @Override
    public void periodic() {
        double distance = (DriveConstants.tagHeight-DriveConstants.limelightHeight) / Math.tan(Units.degreesToRadians(LimelightHelpers.getTY(name) / 2));
        SmartDashboard.putNumber("Angle", LimelightHelpers.getTY(name) / 2);
        SmartDashboard.putNumber("Distance", distance);
    }

    public Pose2d getPoseEstimate() {
        // Optional<Alliance> ally = DriverStation.getAlliance();
        // if (ally.isPresent()) {
        //     if (ally.get() == Alliance.Red) {
        //         return LimelightHelpers.getBotPoseEstimate_wpiRed_MegaTag2(name);
        //     }
        //     else if (ally.get() == Alliance.Blue) {
        //         return LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(name);
        //     }

        //     else {
        //         return new PoseEstimate();
        //     }
        // }

        // else {
        //     return new PoseEstimate();
        // }
        return LimelightHelpers.getBotPose3d_TargetSpace(name).toPose2d();
                
    }
}
