package org.firstinspires.ftc.agribotscode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


/**
 * Created by Daniel Miller on 1/9/2018.
 */
@TeleOp(name = "SkyStone_TeleOp", group = "SkyStone")
public class SkyStone_TeleOp extends LinearOpMode {

    private Arm arm;

    private OmniWheelDriveSystem drive;

    @Override
    public void runOpMode() throws InterruptedException {

        arm = new Arm();
        arm.setShoulder(hardwareMap.dcMotor.get("shoulder"));
        arm.setClaw(hardwareMap.servo.get("claw"));

        drive = new OmniWheelDriveSystem();
        drive.setFrontRight(hardwareMap.dcMotor.get("motor_0"));
        drive.setRearRight(hardwareMap.dcMotor.get("motor_1"));
        drive.setRearLeft(hardwareMap.dcMotor.get("motor_2"));
        drive.setFrontLeft(hardwareMap.dcMotor.get("motor_3"));

        waitForStart();

        while (opModeIsActive()) {

            double armPower = gamepad2.right_stick_y;

            float x = gamepad1.left_stick_x;
            float r = gamepad1.right_stick_x;
            float y = gamepad1.left_stick_y;
            telemetry.addData("Drive x", x);
            telemetry.addData("Drive r", r);
            telemetry.addData("Drive y", y);

            //TODO right and left need to be switched
            //x is - when moving left
            drive.moveXYR(x, y, r);

            arm.shoulderUp(armPower);

            if (gamepad2.dpad_down) {

                arm.closedClaw();
            }
            if (gamepad2.dpad_up) {
                arm.openClaw();

            }

            telemetry.update();
            idle();
        }
    }
}