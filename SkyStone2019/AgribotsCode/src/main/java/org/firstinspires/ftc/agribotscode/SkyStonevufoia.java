package org.firstinspires.ftc.agribotscode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.vuforia.Trackable;

import org.firstinspires.ftc.agribotscode.VisionSystem;
import org.firstinspires.ftc.agribotscode.VisionSystemOpMode;




/**
 * Created by Daniel Miller on 1/9/2018.
 *
 */

@Autonomous (name = "SkyStonevufoia", group = "SkyStone")
public class SkyStonevufoia extends LinearOpMode {

    private SkystoneRobot robot = new SkystoneRobot();

    private Arm arm;

    private OmniWheelDriveSystem drive;

    private ColorSensor sensorColor;

    @Override
    public void runOpMode() throws InterruptedException {

        robot.initialize(hardwareMap);

        arm = new Arm();
        arm.setShoulder(hardwareMap.dcMotor.get("shoulder"));
        arm.setClaw(hardwareMap.servo.get("claw"));


        drive = new OmniWheelDriveSystem();
        drive.setFrontRight(hardwareMap.dcMotor.get("motor_0"));
        drive.setRearRight(hardwareMap.dcMotor.get("motor_1"));
        drive.setRearLeft(hardwareMap.dcMotor.get("motor_2"));
        drive.setFrontLeft(hardwareMap.dcMotor.get("motor_3"));

        sensorColor = hardwareMap.get(ColorSensor.class, "ground_sensor");
        boolean foundLine = false;


        waitForStart();
        delayedStart();

        while (opModeIsActive()) {

            telemetry.addData("Red  ", sensorColor.red());
            telemetry.addData("Green", sensorColor.green());
            telemetry.addData("Blue ", sensorColor.blue());
            // if red or blue are above 100 then stop.
            if (sensorColor.red() > 100 || sensorColor.blue() > 100  ){
                foundLine=true;
            }
            drive.moveXYR(.0, 0.5, 0);
            if (Trackable.getName().equals("Stone Target")){
                drive.moveXYR(.0, 0, 0);



            }

            telemetry.update();
            idle();
        }
    }

    private void delayedStart() {
        this.resetStartTime();
        double remaining = robot.getAutonomousDelay();
        while (opModeIsActive() && remaining > 0) {
            remaining = robot.getAutonomousDelay() - this.getRuntime();
            telemetry.addData("Starting in", "%.1f seconds", remaining);
            telemetry.update();
            idle();
        }
    }
}