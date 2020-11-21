package org.firstinspires.ftc.agribotscode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;


/**
 *
 */
@Autonomous (name = "SkyStone Expiremental", group = "SkyStone")
public class SkyStoneAutoExpiremental extends LinearOpMode {

    private SkystoneRobot robot = new SkystoneRobot();

    @Override
    public void runOpMode() throws InterruptedException {

        robot.initialize(hardwareMap);


        waitForStart();
        delayedStart();

        robot.getArm().rotateShoulder (5.0);

        robot.getDriveSystem().rotate(360.0, 0.3);
        while (opModeIsActive() && robot.getDriveSystem().isBusy()) {
            robot.getDriveSystem().displayStatus(telemetry);
            telemetry.update();
        }

        while (opModeIsActive()) {

            telemetry.update();
            idle();
        }
        robot.stop();
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