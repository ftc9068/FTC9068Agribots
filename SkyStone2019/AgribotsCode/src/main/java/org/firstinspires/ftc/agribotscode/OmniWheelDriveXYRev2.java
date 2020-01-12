package org.firstinspires.ftc.agribotscode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


/**
 * Created by Daniel Miller on 1/9/2018.
 */
@TeleOp(name = "SkyStone TeleOp", group = "SkyStone")
public class OmniWheelDriveXYRev2 extends LinearOpMode {
    //private DcMotor motor_3;
    //private DcMotor motor_2;
    //private DcMotor motor_0;
    //private DcMotor motor_1;

    private Arm arm;

    private OmniWheelDriveSystem drive;

    private float scale(float value) {
        return value / 2.0f;
    }

    @Override
    public void runOpMode() throws InterruptedException {
        //motor_3 = hardwareMap.dcMotor.get("motor_3");

        //motor_2 = hardwareMap.dcMotor.get("motor_2");

        //motor_0 = hardwareMap.dcMotor.get("motor_0");

        //motor_1 = hardwareMap.dcMotor.get("motor_1");


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
            float x = gamepad1.left_stick_x;
            float y = gamepad1.left_stick_y;
            float z = gamepad1.right_stick_x;
            double armPower = gamepad2.right_stick_y;
            telemetry.addData("Drive X", x);
            telemetry.addData("Drive Y", y);
            telemetry.addData("Drive R", z);

            // TODO right and left need to be switched
            // x is - when moving left
            drive.moveXYR(x, y, z);
            //motor_3.setPower(scale(-y + x + z));
            //motor_3.setPower(scale(-(-y - x) - z));
            //motor_2.setPower(scale(-(-y + x) - z));
            //motor_0.setPower(scale(-y + x - z));
            //motor_1.setPower(scale(-y - x - z));

            arm.shoulderUp(armPower);

            if (gamepad2.dpad_down) {

                arm.closedClaw();
            }
            if (gamepad2.dpad_up) {
                arm.openClaw();

            }


            //*arm1.shoulderUp(armPower);
            /* Doug's code
            front left (-(y-x)+z)   -(1-(-1)0 = -2     -(1-0)=-1    -(1-1)=0  etc
            front right (y+x+z)       1+(-1) = 0         1+0 =1        1+1=2
            rear left (-(y+x) +z)   -(1+(-1) = 0     -(1+0) =-1  -(1+1)=-2
            rear right(y-x +z)          1-(-1)= 2           1-0=1         1-1=0
             */

            /*
            motorTopLeft.setPower(scale(-y + x + z));
            motorBottomLeft.setPower(scale((-y + x + z)));
            motorTopRight.setPower(scale(-y - x + z));
            motorBottomRight.setPower(scale((-y + x + z)));

             */

            /*
            frontright.setPower(scale(y+x-z));
            frontleft.setPower(scale(y-x+z));
            backright.setPower(scale(y-x-z));
            backleft.setPower(scale(y+x+z));
             */
            telemetry.update();
            idle();
        }
    }
}