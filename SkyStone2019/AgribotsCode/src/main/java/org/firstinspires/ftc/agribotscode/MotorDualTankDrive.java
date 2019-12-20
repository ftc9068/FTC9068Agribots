package org.firstinspires.ftc.agribotscode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Daniel Miller on 1/9/2018.
 */
@TeleOp(name = "MotorDualTankDrive", group = "Tutorials")
public class MotorDualTankDrive extends LinearOpMode
{
    private DcMotor motor_3;
    private DcMotor motor_2;
    private DcMotor motor_0;
    private DcMotor motor_1;


    private float scale(float value){
        return value /2.0f;
    }

    @Override
    public void runOpMode() throws InterruptedException
    {
        motor_3 = hardwareMap.dcMotor.get("motor_3");

        motor_2 = hardwareMap.dcMotor.get("motor_2");

        motor_0 = hardwareMap.dcMotor.get("motor_0");

        motor_1 = hardwareMap.dcMotor.get("motor_1");



        waitForStart();

        while (opModeIsActive())
        {
            float G1Lx=gamepad1.left_stick_x;
            float G1Ly=gamepad1.left_stick_y;
            float G1Rx=gamepad1.right_stick_x;
            float G1Ry=gamepad1.right_stick_y;


            //motor_3.setPower(scale(-y + x + z));
            motor_0.setPower(G1Rx*-1.0);
            motor_1.setPower(G1Ry);
            motor_2.setPower(G1Lx);
            motor_3.setPower(G1Ly*-1.0);

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

            idle();
        }
    }
}