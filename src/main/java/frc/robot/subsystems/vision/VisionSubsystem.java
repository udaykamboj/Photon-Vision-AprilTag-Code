package frc.robot.subsystems.vision;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * Subsystem wrapper around the platform-specific VisionIO implementation.
 * Uses the camera name from {@link Constants#VisionConstants} with a safe
 * fallback to "Arducam_OV9281" when the constant is empty.
 */
public class VisionSubsystem extends SubsystemBase {
	private final VisionIO visionIO;
	private final VisionIO.VisionIOInputs inputs = new VisionIO.VisionIOInputs();

	/** Default constructor: uses the camera name from Constants (or a fallback). */
	public VisionSubsystem() {
		String cameraName = Constants.VisionConstants.CAMERA_NAME;
		if (cameraName == null || cameraName.isEmpty()) {
			cameraName = "Arducam_OV9281"; // sensible default if not configured
		}
		this.visionIO = new VisionIOArduCam_PV(cameraName);
	}

	/**
	 * Constructor that accepts a VisionIO instance. Useful for testing/mocking.
	 */
	public VisionSubsystem(VisionIO visionIO) {
		this.visionIO = visionIO;
	}

	@Override
	public void periodic() {
		// Update inputs from the underlying VisionIO implementation
		visionIO.updateInputs(inputs);

		// Publish a few useful values to the SmartDashboard for debugging/tuning
		SmartDashboard.putBoolean("Vision/HasTargets", inputs.hasTargets);
		SmartDashboard.putNumber("Vision/TargetYawDegrees", inputs.targetYawDegrees);
		SmartDashboard.putNumber("Vision/TargetPitchDegrees", inputs.targetPitchDegrees);
		SmartDashboard.putNumber("Vision/DistanceMeters", inputs.distanceToTargetMeters);
	}

	// Simple accessors so commands can read the latest vision values
	public boolean hasTargets() {
		return inputs.hasTargets;
	}

	public double getTargetYawDegrees() {
		return inputs.targetYawDegrees;
	}

	public double getTargetPitchDegrees() {
		return inputs.targetPitchDegrees;
	}

	public double getDistanceToTargetMeters() {
		return inputs.distanceToTargetMeters;
	}
}
