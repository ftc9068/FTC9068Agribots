package org.firstinspires.ftc.agribotscode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * This mode allows the driver to drive the robot diagonally like a tank.
 * It is also useful for testing the drive motors.
 */
@TeleOp(name = "Dual Tank Drive", group = "SkyStone")
public class DualTankDriveOmniWheel extends LinearOpMode
{
    private DcMotor motor_3;
    private DcMotor motor_2;
    private DcMotor motor_0;
    private DcMotor motor_1;

    @Override
    public void runOpMode() throws InterruptedException
    {
        motor_0 = hardwareMap.dcMotor.get("motor_0");
        motor_1 = hardwareMap.dcMotor.get("motor_1");
        motor_2 = hardwareMap.dcMotor.get("motor_2");
        motor_3 = hardwareMap.dcMotor.get("motor_3");

        waitForStart();

        while (opModeIsActive())
        {
            motor_0.setPower(gamepad1.left_stick_x*-1.0);
            motor_1.setPower(gamepad1.right_stick_y*-1.0);
            motor_2.setPower(gamepad1.right_stick_x);
            motor_3.setPower(gamepad1.left_stick_y);

            idle();
        }
    }
}