package org.firstinspires.ftc.agribotscode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="Will and Brianna Telop:",group="Agribots")
public class WBTelop extends OpMode{
    /* Declare OpMode members. */
    HardwarePushbot robot       = new HardwarePushbot();

    ElapsedTime xElaped = new ElapsedTime();
    boolean xToggle = false;
    double xToggleInterval = 0.5;


    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Brianna or 9068 team");    //
    }


    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }


    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
    }


    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
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
        toggleXEvent();

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
        void  launchertoggle() {
            if (gamepad1.x && xElaped.seconds() > xToggleInterval){
                launchertoggle = !xToggle;
                launchertoggle.reset();
            }
            telemetry.addLine("X Toggle").addData("",  xToggle);
        }

        //

    }
}
