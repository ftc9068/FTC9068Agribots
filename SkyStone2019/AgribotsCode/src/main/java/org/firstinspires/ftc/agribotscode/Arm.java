package org.firstinspires.ftc.agribotscode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Arm {
    private Servo claw;
    private DcMotor shoulder;

    static final double CLAW_OPEN_POS = 1.0;     // Maximum rotational position
    static final double CLAW_CLOSED_POS = 0.0;     // Minimum rotational position

    public Arm() {
    }

    public void init(DcMotor shoulder, Servo claw) {
        this.shoulder = shoulder; //hardwareMap.dcMotor.get("shoulder");
        this.claw = this.claw;//hardwareMap.get(Servo.class, "claw");
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

