package org.firstinspires.ftc.agribotscode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


/**
 * This Op mode allows the robot to be controlled by one driver.
 */
@TeleOp(name = "SkyStone Single", group = "SkyStone")
public class SkyStoneTeleOpSingle extends LinearOpMode {

    private SkystoneRobot robot;

    private double minSpeedFactor = 0.5;
    private double speedFactor = 1;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new SkystoneRobot();
        robot.initialize(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {

            // Read in the game controls
            float trigger = gamepad1.right_trigger;
            speedFactor = minSpeedFactor + trigger * (1 - minSpeedFactor);

            double armPower = gamepad1.right_stick_y;

            float x = gamepad1.left_stick_x;
            float r = gamepad1.right_stick_x / 2.0f;
            float y = gamepad1.left_stick_y;

            // Send the commands to the robot
            robot.getDriveSystem().setSpeedFactor(speedFactor);
            robot.getDriveSystem().moveXYR(x, y, r);
            robot.getArm().moveShoulder(armPower);

            if (gamepad1.dpad_up) {
                robot.getArm().closedClaw();
            }
            if (gamepad1.dpad_down) {
                robot.getArm().openClaw();
            }

            // Display feedback to the driver station.
            telemetry.addData("Drive x", x);
            telemetry.addData("Drive r", r);
            telemetry.addData("Drive y", y);
            telemetry.addData("Shoulder Position", robot.getArm().getShoulderPosition());
            robot.getDriveSystem().displayStatus(telemetry);
            telemetry.update();
            idle();
        }
    }
}