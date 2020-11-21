package org.firstinspires.ftc.agribotscode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class Arm {
    private static final double CLAW_OPEN_POS = 1.0;     // Maximum rotational position
    private static final double CLAW_CLOSED_POS = 0.0;     // Minimum rotational position
    private static final int SHOULDER_COUNTS_PER_MOTOR_REV = 1120;    // Rev Hex motor
    private static final double SHOULDER_DRIVE_GEAR_REDUCTION = 8.0;
    private static final double SHOULDER_TICKS_PER_DEGREE = (SHOULDER_COUNTS_PER_MOTOR_REV * SHOULDER_DRIVE_GEAR_REDUCTION) / 360;
    private static final int SHOULDER_MIN_POSITION = 0;
    private static final int SHOULDER_MAX_POSITION = (int) (180.0 * SHOULDER_TICKS_PER_DEGREE);
    private static final double SHOULDER_SPEED = 0.5;

    private Servo claw;
    private DcMotor shoulder;

    public Arm() {
    }

    public void init() {
        shoulder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //shoulder.setDirection(DcMotorSimple.Direction.REVERSE);
        shoulder.setTargetPosition(0);
        shoulder.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        shoulder.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void update(){
        if (getArmStopPosition()){
            shoulder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }

    public void setClaw(Servo claw) {
        this.claw = claw;
    }

    public void setShoulder(DcMotor shoulder) {
        this.shoulder = shoulder;
    }

    public void setShoulderStopSensor(TouchSensor shoulderStopSensor){
        //this.shoulderStopSensor = shoulderStopSensor;
    }

    public boolean getArmStopPosition() {
        return false;//return shoulderStopSensor.isPressed();
    }


    public void moveShoulder(double power) {
        int currentPosition = shoulder.getCurrentPosition();
        if(power < -0.1 || power > 0.1) {
            int targetPosition = limitShoulderPosition(currentPosition + (int) (power * 100));
            shoulder.setTargetPosition(targetPosition);
            shoulder.setPower(power);
        }
    }

    /**
     * Rotate the arm shoulder to the specified position in degrees.
     * @param deg
     */
    public void rotateShoulder(double deg) {
        int targetPosition = limitShoulderPosition((int) (deg * SHOULDER_TICKS_PER_DEGREE));
        shoulder.setTargetPosition(targetPosition);
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

    /**
     * Returns a number that is within the shoulder position limits.
     * @param targetPosition
     * @return
     */
    private int limitShoulderPosition(int targetPosition){
        return Math.min(SHOULDER_MAX_POSITION, Math.max(SHOULDER_MIN_POSITION, targetPosition));
    }

}

