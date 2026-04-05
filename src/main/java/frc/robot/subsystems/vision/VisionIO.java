package frc.robot.subsystems.vision;

import org.littletonrobotics.junction.AutoLog;

public interface VisionIO {
    
    default void updateInputs(VisionIOInputs inputs) {}

    @AutoLog
    class VisionIOInputs {
        // To check if AprilTag is detected
        public boolean hasTargets = false;
        
        // 2. Side-to-side angle from AprilTag. Feed this into PID to turn the robot to face the target.
        public double targetYawDegrees = 0.0;   
        
        // Pitch.  Feed this into a math formula (or direct PID) to maintain distance.
        public double targetPitchDegrees = 0.0; 
        
       //
        // using PhotonUtils.calculateDistanceToTargetMeters()
        public double distanceToTargetMeters = 0.0; 
    } 
}