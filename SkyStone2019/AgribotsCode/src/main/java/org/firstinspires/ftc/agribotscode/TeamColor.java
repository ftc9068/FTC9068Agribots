package org.firstinspires.ftc.agribotscode;

public enum TeamColor {
    RED, BLUE;

    public static TeamColor fromOrdinal(int n) {
        return values()[n];
    }
}
