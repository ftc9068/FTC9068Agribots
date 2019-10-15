package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Daniel Miller on 1/9/2018.
 */
@TeleOp(name = "OmniWheelDriveXYRev2", group = "Tutorials")
public class OmniWheelDriveXYRev2 extends LinearOpMode
{
    private DcMotor motorTopLeft;
    private DcMotor motorBottomLeft;
    private DcMotor motorTopRight;
    private DcMotor motorBottomRight;


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



        waitForStart();

        while (opModeIsActive())
        {
            float x=gamepad1.left_stick_x;
            float y=gamepad1.left_stick_y;
            float z=gamepad1.right_stick_x;


            //motorTopLeft.setPower(scale(-y + x + z));
            motorTopLeft.setPower(scale(-(-y-x)-z));
            motorBottomLeft.setPower(scale(-(-y+x) -z));
            motorTopRight.setPower(scale(-y+x-z));
            motorBottomRight.setPower(scale(-y-x -z));

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