package org.firstinspires.ftc.agribotscode;

import com.qualcomm.robotcore.hardware.DcMotor;

public class OmniWheelDriveSystem {

    private DcMotor frontRight;
    private DcMotor rearRight;
    private DcMotor rearLeft;
    private DcMotor frontLeft;
    private double speedFactor;

    public void setFrontRight(DcMotor frontRight) {
        this.frontRight = frontRight;
    }

    public void setRearRight(DcMotor rearRight) {
        this.rearRight = rearRight;
    }

    public void setRearLeft(DcMotor rearLeft) {
        this.rearLeft = rearLeft;
    }

    public void setFrontLeft(DcMotor motor_3) {
        this.frontLeft = motor_3;
    }

    public void moveXYR(double x, double y, double r){
        frontRight.setPower(scale(-y - x - r));
        rearRight.setPower(scale(-y + x - r));
        rearLeft.setPower(scale(-(-y - x) - r));
        frontLeft.setPower(scale(-(-y + x) - r));
    }

    public double getSpeedFactor() {
        return speedFactor;
    }

    public void setSpeedFactor(double speedFactor) {
        this.speedFactor = speedFactor;
    }

    private double scale(double value) {
        return value / 2.0f*speedFactor;
    }
//I changed 2.0 f to 1.0 f
}
