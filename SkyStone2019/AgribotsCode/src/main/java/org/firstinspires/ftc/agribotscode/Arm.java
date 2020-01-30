package org.firstinspires.ftc.agribotscode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Arm {
    static final double COUNTS_PER_MOTOR_REV = 2240;    // Rev Hex motor
    static final double DRIVE_GEAR_REDUCTION = 1.0;//40.0*8.0 ;     // 40 : 1 motor and 8: 1 gears
    static final double SHOULDER_SPEED = 1.0;
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
        shoulder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shoulder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void rotateShoulder(double deg) {
        shoulder.setTargetPosition((int) (deg * COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION));
        shoulder.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        shoulder.setPower(SHOULDER_SPEED);
    }

    public double getShoulderPosition() {
        return shoulder.getCurrentPosition();
    }

    public void stop() {
        shoulder.setPower(0.0);
    }

    public void openClaw() {
        claw.setPosition(CLAW_OPEN_POS);

    }

    public void closedClaw() {
        claw.setPosition(CLAW_CLOSED_POS);

    }

    public void shoulderUp(double power) {
        shoulder.setPower(power * 1.0);

    }

    public void shoulderDown(double power) {
        shoulder.setPower(power * -1.0);

    }

}

