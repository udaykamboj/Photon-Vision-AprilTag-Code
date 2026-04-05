package frc.robot.subsystems.vision;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


import frc.robot.Constants;

public class VisionSubsystem extends SubsystemBase {
	private final VisionIO visionIO;
	private final VisionIO.VisionIOInputs inputs = new VisionIO.VisionIOInputs();

	//Default constructor: uses the camera name from Constants (fallback to "Arducam_OV9281" when the constant is empty).
	public VisionSubsystem() {
		String cameraName = Constants.VisionConstants.CAMERA_NAME;

		if (cameraName == null || cameraName.isEmpty()) {
			cameraName = "Arducam_OV9281"; // sensible default if not configured
		}
		
        this.visionIO = new VisionIOArduCam_PV(cameraName);
	}


	
	//Constructor that accepts a VisionIO instance. Useful for testing/mocking.
	public VisionSubsystem(VisionIO visionIO) {
		this.visionIO = visionIO;
	}


	@Override
	public void periodic() {
		visionIO.updateInputs(inputs);

	}

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
