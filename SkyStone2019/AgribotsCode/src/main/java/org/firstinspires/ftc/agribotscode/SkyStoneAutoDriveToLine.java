package org.firstinspires.ftc.agribotscode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;


/**
 * Drive the robot to a red or blue line and stop.
 */

@Autonomous(name = "SkyStone Drive to Line", group = "SkyStone")
public class SkyStoneAutoDriveToLine extends LinearOpMode {

    private SkystoneRobot robot = new SkystoneRobot();

    @Override
    public void runOpMode() throws InterruptedException {

        robot.initialize(hardwareMap);

        waitForStart();
        delayedStart();

        driveToLine();

        while (opModeIsActive()) {
            telemetry.update();
            idle();
        }
    }

    private void driveToLine() {
        boolean foundLine = false;
        robot.getGroundSensor().enableLed(true);
        while (opModeIsActive() && !foundLine) {
            int red = robot.getGroundSensor().red();
            int green = robot.getGroundSensor().green();
            int blue = robot.getGroundSensor().blue();

            // if red or blue are above 100 then stop.
            if (red > 100 || blue > 100) {
                foundLine = true;
            }

            if (!foundLine) {
                robot.getDriveSystem().moveXYR(0.5, 0, 0);
            } else {
                robot.getDriveSystem().moveXYR(.0, 0, 0);
            }

            telemetry.addLine("Dive to line.");
            telemetry.addData("Red  ", red);
            telemetry.addData("Green", green);
            telemetry.addData("Blue ", blue);
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