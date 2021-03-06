package org.firstinspires.ftc.agribotscode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;


/**
 * Created by Daniel Miller on 1/9/2018.
 *
 */

@Autonomous (name = "SkyStone_Autonomous_testSystem", group = "SkyStone")
public class SkyStone_Autonomous_testSystem extends LinearOpMode {

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
        arm.init();


        drive = new OmniWheelDriveSystem();
        drive.setFrontRight(hardwareMap.dcMotor.get("motor_0"));
        drive.setRearRight(hardwareMap.dcMotor.get("motor_1"));
        drive.setRearLeft(hardwareMap.dcMotor.get("motor_2"));
        drive.setFrontLeft(hardwareMap.dcMotor.get("motor_3"));

        sensorColor = hardwareMap.get(ColorSensor.class, "ground_sensor");
        boolean foundLine = false;


        waitForStart();
        delayedStart();
        arm.rotateShoulder (5.0);

        while (opModeIsActive()) {

            telemetry.addData("Red  ", sensorColor.red());
            telemetry.addData("Green", sensorColor.green());
            telemetry.addData("Blue ", sensorColor.blue());
            telemetry.addData("Shoulder Position",arm.getShoulderPosition() );
            telemetry.addData("Arm Stop Sensor", arm.getArmStopPosition());
            // if red or blue are above 100 then stop.
            if (sensorColor.red() > 100 || sensorColor.blue() > 100  ){
                foundLine=true;
            }

            if (! foundLine){
                //drive.moveXYR(0.5, 0, 0);
            }else {
                drive.moveXYR(.0, 0, 0);
            }

            telemetry.update();
            idle();
        }
        arm.stop();
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