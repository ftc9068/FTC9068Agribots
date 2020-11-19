package org.firstinspires.ftc.agribotscode;

public class Position {
    private double x;
    private double y;
    private double z;
    private double rotation;

    public String matrix;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public Position(double x, double y, double z, double r) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.rotation = r;
    }
}
