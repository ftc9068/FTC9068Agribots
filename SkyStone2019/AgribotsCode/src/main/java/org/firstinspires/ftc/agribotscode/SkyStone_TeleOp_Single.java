package org.firstinspires.ftc.agribotscode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


/**
 * Created by Daniel Miller on 1/9/2018.
 */
@TeleOp(name = "SkyStone_TeleOp_Single", group = "SkyStone")
public class SkyStone_TeleOp_Single extends LinearOpMode {

    private Arm arm;

    //static final double     DRIVE_SPEED             = 1.0;
    //static final double     TURN_SPEED              = 0.8;

    private OmniWheelDriveSystem drive;

    @Override
    public void runOpMode() throws InterruptedException {

        //public void encoderDrive(double speed,
        //double leftInches, double rightInches,
        //double timeoutS)

        //encoderDrive(DRIVE_SPEED,  5,  5, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
        //encoderDrive(TURN_SPEED,   12, -12, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout
        //encoderDrive(DRIVE_SPEED, -204, -24, 4.0);  // S3: Reverse 24 Inches with 4 Sec timeout

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

            double armPower = gamepad1.right_stick_y;

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

            if (gamepad1.dpad_up) {
//Changed gamepad 2 to 1
                arm.closedClaw();
            }
            if (gamepad1.dpad_down) {
                arm.openClaw();

            }

            telemetry.update();
            idle();
        }
    }
}