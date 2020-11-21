package org.firstinspires.ftc.agribotscode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


/**
 *
 */
@TeleOp(name = "SkyStone Duo", group = "SkyStone")
public class SkyStoneTeleOpDuo extends LinearOpMode {
    private SkystoneRobot robot;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new SkystoneRobot();
        robot.initialize(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {

            double armPower = gamepad2.right_stick_y;

            float x = gamepad1.left_stick_x;
            float r = gamepad1.right_stick_x;
            float y = gamepad1.left_stick_y;


            robot.getDriveSystem().moveXYR(x, y, r);
            robot.getDriveSystem().displayStatus(telemetry);

            robot.getArm().moveShoulder(armPower);

            if (gamepad2.dpad_down) {
                robot.getArm().closedClaw();
            }
            if (gamepad2.dpad_up) {
                robot.getArm().openClaw();
            }

            telemetry.addData("Drive x", x);
            telemetry.addData("Drive r", r);
            telemetry.addData("Drive y", y);
            telemetry.update();
            idle();
        }
    }
}