package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Daniel Miller on 1/9/2018.
 */
@TeleOp(name = "OmniWheelandArm", group = "Tutorials")
public class OmniWheelandArm extends LinearOpMode
{
    private DcMotor motorTopLeft;
    private DcMotor motorBottomLeft;
    private DcMotor motorTopRight;
    private DcMotor motorBottomRight;

    private DcMotor motorArmEnd;
    private DcMotor motorArmMiddle;
    private DcMotor motorArm;
    private DcMotor fan;



    private float scale(float value){
        return value /2.0f;
    }

    @Override
    public void runOpMode() throws InterruptedException
    {
        motorTopLeft = hardwareMap.dcMotor.get("motorTopLeft");

        motorBottomLeft = hardwareMap.dcMotor.get("motorBottomLeft");

        motorTopRight = hardwareMap.dcMotor.get("motorTopRight");

        motorBottomRight = hardwareMap.dcMotor.get("motorBottomRight");

        motorArm = hardwareMap.dcMotor.get("motorArm");
        motorArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorArmMiddle = hardwareMap.dcMotor.get("motorArmMiddle");
        motorArmMiddle.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        motorArmEnd = hardwareMap.dcMotor.get("motorArmEnd");
        motorArmEnd.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        fan = hardwareMap.dcMotor.get("fan");




        waitForStart();

        while (opModeIsActive())
        {
            float x=gamepad1.left_stick_x;
            float y=gamepad1.left_stick_y;
            float z=gamepad1.right_stick_x;

            int position = motorArm.getCurrentPosition();
            telemetry.addData("Encoder Position", position);
            telemetry.update();


            //motorTopLeft.setPower(scale(-y + x + z));
            motorTopLeft.setPower(scale(-(-y-x)-z));
            motorBottomLeft.setPower(scale(-(-y+x) -z));
            motorTopRight.setPower(scale(-y+x-z));
            motorBottomRight.setPower(scale(-y-x -z));

            motorArm.setPower(-gamepad2.left_stick_y * 0.5);
            motorArmMiddle.setPower(-gamepad2.right_stick_y * 0.5);
            motorArmEnd.setPower(gamepad2.right_stick_x * 0.25);
            fan.setPower(gamepad2.right_trigger);

            motorArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motorArmMiddle.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motorArmEnd.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


            /* dougs code
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

            idle();
        }
    }
}