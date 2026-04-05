package frc.robot.subsystems.vision;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.util.Units;

public class VisionIOArduCam_PV implements VisionIO {
    private final PhotonCamera camera;

    
    private static final double CAMERA_HEIGHT_METERS = Units.inchesToMeters(10.0);
    private static final double TARGET_HEIGHT_METERS = Units.inchesToMeters(18.0); // Example AprilTag height
    private static final double CAMERA_PITCH_RADIANS = Units.degreesToRadians(0.0); // Angle your camera points up/down

    /**
     * @param cameraName The exact name of the camera as configured in the PhotonVision UI
     */
    public VisionIOArduCam_PV(String cameraName) {
        camera = new PhotonCamera(cameraName);
    }

    @Override
    public void updateInputs(VisionIOInputs inputs) {
        // Grab the absolute latest frame data from the coprocessor
        PhotonPipelineResult result = camera.getLatestResult();

        inputs.hasTargets = result.hasTargets();

        if (inputs.hasTargets) {
          
            PhotonTrackedTarget bestTarget = result.getBestTarget();

            inputs.targetYawDegrees = bestTarget.getYaw();
            inputs.targetPitchDegrees = bestTarget.getPitch();

         
            inputs.distanceToTargetMeters = PhotonUtils.calculateDistanceToTargetMeters(
                    CAMERA_HEIGHT_METERS,
                    TARGET_HEIGHT_METERS,
                    CAMERA_PITCH_RADIANS,
                    Units.degreesToRadians(inputs.targetPitchDegrees)
            );
        } else {
        
            inputs.targetYawDegrees = 0.0;
            inputs.targetPitchDegrees = 0.0;
            inputs.distanceToTargetMeters = 0.0;
        }
    }
}
