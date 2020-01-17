package org.firstinspires.ftc.agribotscode;

import android.os.Environment;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

public class VisionSystem {
    // IMPORTANT:  For Phone Camera, set 1) the camera source and 2) the orientation, based on how your phone is mounted:
    // 1) Camera Source.  Valid choices are:  BACK (behind screen) or FRONT (selfie side)
    // 2) Phone Orientation. Choices are: PHONE_IS_PORTRAIT = true (portrait) or PHONE_IS_PORTRAIT = false (landscape)
    //
    // NOTE: If you are running on a CONTROL HUB, with only one USB WebCam, you must select CAMERA_CHOICE = BACK; and PHONE_IS_PORTRAIT = false;
    //
    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final boolean PHONE_IS_PORTRAIT = true;

    // Since ImageTarget trackables use mm to specifiy their dimensions, we must use mm for all the physical dimension.
    // We will define some constants and conversions here
    private static final float MM_PER_INCH = 25.4f;
    private static final float mmTargetHeight = (6) * MM_PER_INCH;          // the height of the center of the target image above the floor

    // Constant for Stone Target
    private static final float stoneZ = 2.00f * MM_PER_INCH;

    // Constants for the center support targets
    private static final float bridgeZ = 6.42f * MM_PER_INCH;
    private static final float bridgeY = 23 * MM_PER_INCH;
    private static final float bridgeX = 5.18f * MM_PER_INCH;
    private static final float bridgeRotY = 59;                                 // Units are degrees
    private static final float bridgeRotZ = 180;

    // Constants for perimeter targets
    private static final float halfField = 72 * MM_PER_INCH;
    private static final float quadField = 36 * MM_PER_INCH;

    // Class Members
    private OpenGLMatrix lastLocation = null;
    private VuforiaLocalizer vuforia = null;
    private boolean targetVisible = false;
    private float phoneXRotate = 0;
    private float phoneYRotate = 0;
    private float phoneZRotate = 0;

    private static final String LICENSE_KEY_FILE = "VuforiaLicense.txt";
    private static final int STONE_TARGET = 0;

    VuforiaTrackables skyStoneTargets;

    public VisionSystem(int cameraMonitorViewId) {
        init(cameraMonitorViewId);
    }

    public void activate() {
        skyStoneTargets.activate();
    }

    public void deactivate() {
        skyStoneTargets.deactivate();
    }

    public Position getSkystoneLocation() {
        Position pos = null;
        VuforiaTrackable stoneTarget = skyStoneTargets.get(STONE_TARGET);
        VuforiaTrackableDefaultListener listener = (VuforiaTrackableDefaultListener)stoneTarget.getListener();
        if (listener.isVisible()){
            OpenGLMatrix cameraFromTarget = listener.getFtcCameraFromTarget();
            VectorF translation = cameraFromTarget.getTranslation();
            double x = translation.get(1) / MM_PER_INCH;
            double y = translation.get(2) / MM_PER_INCH;
            double z = translation.get(0) / MM_PER_INCH;
            Orientation rotation = Orientation.getOrientation(cameraFromTarget, EXTRINSIC, XYZ, DEGREES);
            double r = rotation.secondAngle;
            pos = new Position(x, y, z, r);
            pos.matrix = cameraFromTarget.formatAsTransform();
        }

        return pos;
    }

    public void setCameraLocation() {
        // We need to rotate the camera around it's long axis to bring the correct camera forward.
        if (CAMERA_CHOICE == BACK) {
            phoneYRotate = -90;
        } else {
            phoneYRotate = 90;
        }

        // Rotate the phone vertical about the X axis if it's in portrait mode
        if (PHONE_IS_PORTRAIT) {
            phoneXRotate = 90 ;
        }

        // Next, translate the camera lens to where it is on the robot.
        // In this example, it is centered (left to right), but forward of the middle of the robot, and above ground level.
        final float CAMERA_FORWARD_DISPLACEMENT  = 0.0f * MM_PER_INCH;   // eg: Camera is 4 Inches in front of robot center
        final float CAMERA_VERTICAL_DISPLACEMENT = 0.0f * MM_PER_INCH;   // eg: Camera is 8 Inches above ground
        final float CAMERA_LEFT_DISPLACEMENT     = 0;     // eg: Camera is ON the robot's center line

        OpenGLMatrix robotFromCamera = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, phoneYRotate, phoneZRotate, phoneXRotate));
        // For convenience, gather together all the trackable objects in one easily-iterable collection */
        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(skyStoneTargets);

        /**  Let all the trackable listeners know where the phone is.  */
        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, CAMERA_CHOICE);
        }

    }

    private void init(int cameraMonitorViewId) {

        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         * We can pass Vuforia the handle to a camera preview resource (on the RC phone);
         * If no camera monitor is desired, use the parameter-less constructor instead (commented out below).
         */
        //VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();


        parameters.vuforiaLicenseKey = readLicenseKey();
        parameters.cameraDirection = CAMERA_CHOICE;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Load the data sets for the trackable objects. These particular data
        // sets are stored in the 'assets' part of our application.
        skyStoneTargets = this.vuforia.loadTrackablesFromAsset("Skystone");

        VuforiaTrackable stoneTarget = skyStoneTargets.get(STONE_TARGET);
        stoneTarget.setName("Stone Target");

        // Set the position of the Stone Target.  Since it's not fixed in position, assume it's at the field origin.
        // Rotated it to to face forward, and raised it to sit on the ground correctly.
        // This can be used for generic target-centric approach algorithms
        //stoneTarget.setLocation(OpenGLMatrix
        //        .translation(0, 0, stoneZ)
        //        .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));


    }


    private String readLicenseKey() {
        // Get public external storage folder ( /storage/emulated/0 ).
        String licenseKey = "";
        try {
            File externalDir = Environment.getExternalStorageDirectory();
            File licenseFile = new File(externalDir, LICENSE_KEY_FILE);

            try (FileReader reader = new FileReader(licenseFile)) {
                try (BufferedReader br = new BufferedReader(reader)) {
                    licenseKey = br.readLine();
                }
            }
        } catch (IOException e) {
            //log error
        }
        return licenseKey;
    }
};

