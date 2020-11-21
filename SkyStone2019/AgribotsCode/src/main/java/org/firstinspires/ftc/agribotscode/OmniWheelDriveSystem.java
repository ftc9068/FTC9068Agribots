package org.firstinspires.ftc.agribotscode;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class OmniWheelDriveSystem {
    private static final int MOTOR_COUNTS_PER_MOTOR_REV = 28 * 40;    // Andy Mark 40:1
    private static final double WHEEL_RADIOUS = 1.89;
    private static final double DRIVE_SYSTEM_RADIOUS = 10.25;
    private static final double COUNTS_PER_DEGREE_ROTATION =
            DRIVE_SYSTEM_RADIOUS / WHEEL_RADIOUS * (double)(MOTOR_COUNTS_PER_MOTOR_REV) / 360.0;
            // Robot circumference / wheel circumference * encoder count per motor rotation / degrres per circle
            //(2 * Math.PI * DRIVE_SYSTEM_RADIOUS) / (2 * Math.PI * WHEEL_RADIOUS) * (double)(MOTOR_COUNTS_PER_MOTOR_REV) / 360.0;

    private DcMotor frontLeft;
    private double frontLeftPower;
    private DcMotor frontRight;
    private double frontRightPower;
    private DcMotor rearLeft;
    private double rearLeftPower;
    private DcMotor rearRight;
    private double rearRightPower;


    private double speedFactor = 1;

    public OmniWheelDriveSystem() {
    }

    public void init() {
    }

    public void update() {

    }

    /**
     * Display some status information about the drive system.
     * @param telemetry
     */
    public void displayStatus(Telemetry telemetry) {
        displayMotorStatus(telemetry, "Front Left", frontLeft);
        displayMotorStatus(telemetry, "Front Right", frontRight);
        displayMotorStatus(telemetry, "Rear Left", rearLeft);
        displayMotorStatus(telemetry, "Rear Right", rearRight);
    }

    public void setFrontLeft(DcMotor motor_3) {
        this.frontLeft = motor_3;
    }

    public void setFrontRight(DcMotor frontRight) {
        this.frontRight = frontRight;
    }

    public void setRearLeft(DcMotor rearLeft) {
        this.rearLeft = rearLeft;
    }

    public void setRearRight(DcMotor rearRight) {
        this.rearRight = rearRight;
    }

    /**
     * Stop the drive system.
     */
    public void stop() {
        frontLeftPower = 0.0;
        frontRightPower = 0.0;
        rearLeftPower = 0.0;
        rearRightPower = 0.0;
        setPowers();
    }

    /**
     * Move with the specified direction where positive y is the forward direction.
     * @param x The x axis with the positive direction going to the right of the robot.
     * @param y The y axis with the positive direction going to the front of the robot.
     * @param r The rotation of the robot.
     */
    public void moveXYR(double x, double y, double r) {
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rearLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rearRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Other function for computing powers, need to test.
        // front right = sin(y - x) * sqrt(x^2 + y^2) - r
        //If all values are 1
        frontLeftPower = -(-y + x) - r;    //=-1
        frontRightPower = -y - x - r;      //=-3
        rearLeftPower = -(-y - x) - r;     //=1
        rearRightPower = -y + x - r;       //=-1

        scale();
        setPowers();
    }

    /**
     * Move the robot the specified number of inches in the specified direction.
     * @param inches
     * @param degrees
     * @param speed
     */
    public void moveTo(double inches, double degrees, double speed) {

    }

    /**
     * Rotate the robot the specified number of degrees.
     * @param degrees
     * @param power
     */
    public void rotate(double degrees, double power){
        int target = (int)(COUNTS_PER_DEGREE_ROTATION * degrees) * -1;
        setMotorTarget(frontLeft, target);
        setMotorTarget(frontRight, target);
        setMotorTarget(rearLeft, target);
        setMotorTarget(rearRight, target);

        frontLeft.setPower(power);
        frontRight.setPower(power);
        rearLeft.setPower(power);
        rearRight.setPower(power);
    }

    private void setMotorTarget(DcMotor motor, int target){
        if(motor.getMode() != DcMotor.RunMode.RUN_TO_POSITION){
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
        motor.setTargetPosition(motor.getCurrentPosition() + target);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    /**
     * Returns true if the any drive motor is currently moving to a target position.
     * @return true if the any drive motor is currently moving to a target position.
     */
    public boolean isBusy(){
        return frontLeft.isBusy() || frontRight.isBusy() || rearLeft.isBusy() || rearRight.isBusy();
    }

    /**
     * A value between 0 and 1 that is used to control the max speed of the robot for better control.
     * @param speedFactor
     */
    public void setSpeedFactor(double speedFactor) {
        this.speedFactor = speedFactor;
    }


    private void displayMotorStatus(Telemetry telemetry, String caption, DcMotor motor){
        telemetry.addData(caption,"Position %4d, Target %4d, Power: %.1f", motor.getCurrentPosition(), motor.getTargetPosition(), motor.getPower());
    }

    /**
     * Set the motor drive powers.
     */
    private void setPowers() {
        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        rearLeft.setPower(rearLeftPower);
        rearRight.setPower(rearRightPower);
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
