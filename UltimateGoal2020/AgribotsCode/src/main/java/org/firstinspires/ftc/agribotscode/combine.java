package org.firstinspires.ftc.agribotscode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

//import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

@TeleOp(name="combine", group="Pushbot")

public class combine extends OpMode{

    /* Declare OpMode members. */
    HardwarePushbot robot       = new HardwarePushbot(); // use the class created to define a Pushbot's hardware

    AgribotsTelemetry telAg;
    double          launcherMotorPower  = 0.0;
    boolean         launcherOn = false;

    @Override
    public void init() {
       



        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);
        telAg = AgribotsTelemetry.create(telemetry);


        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Brianna or 9068 team");    //
    }


    @Override



    public void loop() {

        boolean xButton;
        boolean yButton;

        robot.launcherMotor.setPower(launcherMotorPower);

        if(launcherOn)
        {
            launcherMotorPower = 1.0;
        }
        else {
            launcherMotorPower=0.0;
        }

        //gets input from the x button, and turns the launcher on
        if(gamepad1.x){

            launcherOn=true;
        }

        //gets input from the y button, and turns it off
        if (gamepad1.y){
            launcherOn=false;
        }
        telAg.displayGamepad(gamepad1);
        /*
A/cross= arm up
b/circle= arm down
Y/ tri= turn on and off conveyor belt
X/sq=
Lt=reverse
Rt= control speed
Rb=sound effects
Lb=honk horns
D-pad
	Up=switch gears/program
          Down
Left
          Right
Ls= foward/backward
Rs=steer
Start
Stop
Turn on controller button



         */
        // Run wheels in POV mode (note: The joystick goes negative when pushed forwards, so negate it)
        // In this mode the Left stick moves the robot fwd and back, the Right stick turns left and right.
        // This way it's also easy to just drive straight, or just turn.
        double drive = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;

        // Combine drive and turn for blended motion.
        double left = drive + turn;
        double right = drive - turn;

        // Normalize the values so neither exceed +/- 1.0
        double max = Math.max(Math.abs(left), Math.abs(right));
        if (max > 1.0)
        {
            left /= max;
            right /= max;
        }

        // Output the safe vales to the motor drives.
        robot.leftDrive.setPower(left);
        robot.rightDrive.setPower(right);

        //



            /*
             * Code to run ONCE when the driver hits INIT
             */


            /*
             * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
             */


            /*
             * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
             */



            }
        }



