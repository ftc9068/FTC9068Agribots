package org.firstinspires.ftc.agribotscode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;


/**
 * Created by Daniel Miller on 1/9/2018.
 *
 */

@Autonomous (name = "SkyStone_Autonomous", group = "SkyStone")
public class SkyStone_Autonomous extends LinearOpMode {

    private Arm arm;

    private OmniWheelDriveSystem drive;

    private ColorSensor sensorColor;

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

        sensorColor = hardwareMap.get(ColorSensor.class, "ground_sensor");
        boolean foundLine = false;


        waitForStart();

        while (opModeIsActive()) {

            telemetry.addData("Red  ", sensorColor.red());
            telemetry.addData("Green", sensorColor.green());
            telemetry.addData("Blue ", sensorColor.blue());
            // if red or blue are above 100 then stop.
            if (sensorColor.red() > 100 || sensorColor.blue() > 100  ){
                foundLine=true;
            }

            if (! foundLine){
                drive.moveXYR(0.1, 0, 0);
            }else {
                drive.moveXYR(.0, 0, 0);
            }







            telemetry.update();
            idle();
        }
    }
}