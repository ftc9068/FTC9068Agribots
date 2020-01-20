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

    public void moveXYR(double x, double y, double r) {
        // Other function for computing powers, need to test.
        // front right = sin(y - x) * sqrt(x^2 + y^2) - r

        frontRightPower = -y - x - r;
        rearRightPower = -y + x - r;
        rearLeftPower = -(-y - x) - r;
        frontLeftPower = -(-y + x) - r;
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

    private void setPowers(){
        frontRight.setPower(frontRightPower);
        rearRight.setPower(rearRightPower);
        rearLeft.setPower(rearLeftPower);
        frontLeft.setPower(frontLeftPower);
    }

    private double maxAbsPower(){
        return Math.max(Math.max(Math.max(Math.abs(frontLeftPower),
                Math.abs(frontRightPower)),
                Math.abs(rearLeftPower)),
                Math.abs(rearRightPower));
    }
    /**
     * Reduce the power levels so that the max power to any motor is no more than 1.0
     */
    private void scale() {
        double max = maxAbsPower();
        double scale = 1;
        if (max > 1){
            scale  = 1.0 / max;
        }
        frontRightPower = frontRightPower * scale * speedFactor;
        rearRightPower = rearRightPower * scale * speedFactor;
        rearLeftPower = rearLeftPower * scale * speedFactor;
        frontLeftPower = frontLeftPower * scale * speedFactor;
    }
//I changed 2.0 f to 1.0 f
}
