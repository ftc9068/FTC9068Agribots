package org.firstinspires.ftc.agribotscode;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class OmniWheelDriveSystem {

    private DcMotor frontRight;
    private DcMotor rearRight;
    private DcMotor rearLeft;
    private DcMotor frontLeft;
    private double frontRightPower;
    private double rearRightPower;
    private double rearLeftPower;
    private double frontLeftPower;
    private double speedFactor = 1;

    public OmniWheelDriveSystem() {
    }

    public void init() {
    }

    public void update() {

    }

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


    public void stop() {
        frontRightPower = 0.0;
        rearRightPower = 0.0;
        rearLeftPower = 0.0;
        frontLeftPower = 0.0;
        setPowers();
    }

    public void moveXYR(double x, double y, double r) {
        // Other function for computing powers, need to test.
        // front right = sin(y - x) * sqrt(x^2 + y^2) - r
        //If all values are 1
        frontRightPower = -y - x - r;      //=-3
        rearRightPower = -y + x - r;       //=-1
        rearLeftPower = -(-y - x) - r;     //=1
        frontLeftPower = -(-y + x) - r;    //=-1
        scale();
        setPowers();
    }

    public double getSpeedFactor() {
        return speedFactor;
    }

    public void setSpeedFactor(double speedFactor) {
        this.speedFactor = speedFactor;
    }

    public void displayPower(Telemetry telemetry) {
        telemetry.addData("Front Left Power", frontLeftPower);
        telemetry.addData("Front Right Power", frontRightPower);
        telemetry.addData("Rear Left Power", rearLeftPower);
        telemetry.addData("Rear Right Power", rearRightPower);
    }

    /**
     * Set the motor drive powers.
     */
    private void setPowers() {
        frontRight.setPower(frontRightPower);
        rearRight.setPower(rearRightPower);
        rearLeft.setPower(rearLeftPower);
        frontLeft.setPower(frontLeftPower);
    }

    /**
     * Calculate the maximum absolute value of all the powers. This is used to scale power levels.
     * @return
     */
    private double maxAbsPower() {
        return Math.max(Math.max(Math.max(Math.abs(frontLeftPower),
                Math.abs(frontRightPower)),
                Math.abs(rearLeftPower)),
                Math.abs(rearRightPower));
    }

    /**
     * Reduce the power levels so that the power to any motor is between -1.0 and 1.0.
     */
    private void scale() {
        double max = maxAbsPower();
        double scale = 1;
        if (max > 1) {
            scale = 1.0 / max;
        }
        frontRightPower = frontRightPower * scale * speedFactor;
        rearRightPower = rearRightPower * scale * speedFactor;
        rearLeftPower = rearLeftPower * scale * speedFactor;
        frontLeftPower = frontLeftPower * scale * speedFactor;
    }
}
