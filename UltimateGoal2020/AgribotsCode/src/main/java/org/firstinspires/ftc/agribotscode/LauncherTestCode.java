package org.firstinspires.ftc.agribotscode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

//import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

@TeleOp(name="LauncherTestCode", group="Pushbot")

public class LauncherTestCode extends OpMode{

    /* Declare OpMode members. */
    HardwarePushbot robot       = new HardwarePushbot(); // use the class created to define a Pushbot's hardware
    double          launcherMotorPower  = 0.0;
    boolean         launcherOn = false;

    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
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
        telemetry.addLine("B | ")
                .addData("x", gamepad1.x)
                .addData("y", gamepad1.y)
                .addData("a", gamepad1.a)
                .addData("b", gamepad1.b)
                .addData("lancher", launcherOn)
                .addData("power", launcherMotorPower);
    }}
