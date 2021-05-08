package org.firstinspires.ftc.agribotscode;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class AgribotsTelemetry {
    Telemetry telemetry;
    boolean displayLeftStick = true;
    boolean displayRightStick = true;
    boolean displayDpad = true;
    boolean displayButtons = true;
    boolean displayLeft = true;
    boolean displayRight = true;

    public AgribotsTelemetry(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    public static AgribotsTelemetry create(Telemetry telemetry){
        return new AgribotsTelemetry(telemetry);
    }

    public AgribotsTelemetry displayGamepad(Gamepad gamepad){
        //telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);
        String caption = String.format("GP %s ", gamepad.getUser());
        if (displayLeftStick) {
            telemetry.addLine(caption + "left  ")
                    .addData("x", "% .3f", gamepad.left_stick_x)
                    .addData("y", "% .3f", gamepad.left_stick_y)
                    .addData("button", "%5s", gamepad.left_stick_button);
        }
        if (displayRightStick) {
            telemetry.addLine(caption + "right ")
                    .addData("x", "% .3f", gamepad.right_stick_x)
                    .addData("y", "% .3f", gamepad.right_stick_y)
                    .addData("button", "%5s", gamepad.right_stick_button);
        }
        if (displayLeft) {
            telemetry.addLine(caption + "left ")
                    .addData("bumper", "%5s", gamepad.left_bumper)
                    .addData("trigger", "% .3f", gamepad.left_trigger);
        }
        if (displayRight) {
            telemetry.addLine(caption + "right ")
                    .addData("bumper", "%5s", gamepad.right_bumper)
                    .addData("trigger", "% .3f", gamepad.right_trigger);
        }
        if (displayDpad) {
            telemetry.addLine(caption + "dpad ")
                    .addData("u","%5s", gamepad.dpad_up)
                    .addData("d","%5s", gamepad.dpad_down)
                    .addData("l","%5s", gamepad.dpad_left)
                    .addData("r","%5s", gamepad.dpad_right);
        }
        if (displayButtons) {
            telemetry.addLine(caption + "button ")
                    .addData("x", "%5s", gamepad.x)
                    .addData("y", "%5s", gamepad.y)
                    .addData("a", "%5s", gamepad.a)
                    .addData("b", "%5s", gamepad.b);
        }

        return this;
    }

    public void setTelemetry(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    public void setDisplayLeftStick(boolean displayLeftStick) {
        this.displayLeftStick = displayLeftStick;
    }

    public void setDisplayRightStick(boolean displayRightStick) {
        this.displayRightStick = displayRightStick;
    }

    public void setDisplayDpad(boolean displayDpad) {
        this.displayDpad = displayDpad;
    }

    public void setDisplayButtons(boolean displayButtons) {
        this.displayButtons = displayButtons;
    }

    public void setDisplayLeft(boolean displayLeft) {
        this.displayLeft = displayLeft;
    }

    public void setDisplayRight(boolean displayRight) {
        this.displayRight = displayRight;
    }


}
