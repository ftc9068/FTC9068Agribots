/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.agribotscode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="Arm Test", group="Test")
public class ArmTest extends LinearOpMode {

    public DcMotor shoulder   = null;

    private ElapsedTime     runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                                                      (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;

    @Override
    public void runOpMode() {
        shoulder  = hardwareMap.get(DcMotor.class, "shoulder");
        //shoulder  = hardwareMap.get(DcMotor.class, "motor_2");
        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        shoulder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //shoulder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        shoulder.setTargetPosition(0);
        shoulder.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        shoulder.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        shoulder.setPower(1.0);

        shoulder.setTargetPosition(1000);
        while (opModeIsActive() &&  shoulder.isBusy()){
            telemetry.addData("Shoulder Position", shoulder.getCurrentPosition());
            telemetry.update();
        }

        shoulder.setTargetPosition(0);
        while (opModeIsActive() &&  shoulder.isBusy()){
            telemetry.addData("Shoulder Position", shoulder.getCurrentPosition());
            telemetry.update();
        }
        int targetPosition = shoulder.getCurrentPosition();
        while (opModeIsActive()) {
            double armPower = gamepad1.right_stick_y;
            int currentPosition = shoulder.getCurrentPosition();
            if(armPower < -0.1 || armPower > 0.1) {
                targetPosition = currentPosition + (int) (armPower * 100);
                shoulder.setTargetPosition(targetPosition);
            }
            //shoulder.setPower(armPower);
            // Send telemetry message to indicate successful Encoder reset
            telemetry.addData("Shoulder Position", "%7d - %7d",
                    currentPosition, targetPosition);
            telemetry.update();
        }
        shoulder.setPower(0);
        shoulder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
