package org.firstinspires.ftc.agribotscode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Arm {
    private Servo claw;
    private DcMotor shoulder;

    public void setClaw(Servo claw) {
        this.claw = claw;
    }

    public void setShoulder(DcMotor shoulder) {
        this.shoulder = shoulder;
    }

    static final double CLAW_OPEN_POS = 1.0;     // Maximum rotational position
    static final double CLAW_CLOSED_POS = 0.0;     // Minimum rotational position

    public Arm() {
    }

    public void init() {

    }

    public void openClaw() {
        claw.setPosition(CLAW_OPEN_POS);

    }

    public void closedClaw() {
        claw.setPosition(CLAW_CLOSED_POS);

    }

    public void shoulderUp(double power) {
        shoulder.setPower(power);

    }

    public void shoulderDown(double power) {
        shoulder.setPower(power * -1.0);

    }

}

