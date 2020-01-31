package org.firstinspires.ftc.agribotscode;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class SkystoneRobot {
    public static final String AUTONOMOUS_DELAY_KEY = "autonomous.delay";
    public static final String AUTONOMOUS_RED_THRESHOLD = "autonomous.redThreshold";
    public static final String AUTONOMOUS_BLUE_THRESHOLD = "autonomous.blueThreshold";
    public static final String TEAM_COLOR_KEY = "team.color";
    private static final String PROPERTIES_FILE = "SkystoneRobot.properties";
    private Properties properties = new Properties();
    private Arm arm;
    private OmniWheelDriveSystem drive;
    private ColorSensor sensorColor;


    public SkystoneRobot() {
    }

    public void initialize(HardwareMap hardwareMap){
        loadProperies();
        arm = new Arm();
        arm.setShoulder(hardwareMap.dcMotor.get("shoulder"));
        arm.setClaw(hardwareMap.servo.get("claw"));


        drive = new OmniWheelDriveSystem();
        drive.setFrontRight(hardwareMap.dcMotor.get("motor_0"));
        drive.setRearRight(hardwareMap.dcMotor.get("motor_1"));
        drive.setRearLeft(hardwareMap.dcMotor.get("motor_2"));
        drive.setFrontLeft(hardwareMap.dcMotor.get("motor_3"));

        sensorColor = hardwareMap.get(ColorSensor.class, "ground_sensor");

    }

    public TeamColor getTeamColor(){
        TeamColor color = TeamColor.BLUE;
        String colorStr = properties.getProperty(TEAM_COLOR_KEY);
        if (colorStr != null){
            color = TeamColor.valueOf(colorStr);
        } else {
            setTeamColor(color);
        }
        return color;
    }

    public void setTeamColor(TeamColor color) {
        properties.setProperty(TEAM_COLOR_KEY, color.name());
    }

    public double getAutonomousDelay(){
        double delay = 0;
        String delayStr = properties.getProperty(AUTONOMOUS_DELAY_KEY);
        if (delayStr != null) {
            delay = Double.parseDouble(delayStr);
        } else{
            setAutonomousDelay(delay);
        }
        return delay;
    }

    public void setAutonomousDelay(double delay) {
        properties.setProperty(AUTONOMOUS_DELAY_KEY, Double.toString(delay));
    }

    public int getAutonomousRedThreshold(){
        int redThreshold = 0;
        String redThresholdStr = properties.getProperty(AUTONOMOUS_RED_THRESHOLD);
        if (redThresholdStr != null) {
            redThreshold = Integer.parseInt(redThresholdStr);
        } else{
            setAutonomousRedThreshold(redThreshold);
        }
        return redThreshold;
    }

    public void setAutonomousRedThreshold(int redThreshold) {
        properties.setProperty(AUTONOMOUS_RED_THRESHOLD, Integer.toString(redThreshold));
    }


    public int getAutonomousBlueThreshold(){
        int blueThreshold = 0;
        String blueThresholdStr = properties.getProperty(AUTONOMOUS_BLUE_THRESHOLD);
        if (blueThresholdStr != null) {
            blueThreshold = Integer.parseInt(blueThresholdStr);
        } else{
            setAutonomousRedThreshold(blueThreshold);
        }
        return blueThreshold;
    }

    public void setAutonomousBlueThreshold(int blueThreshold) {
        properties.setProperty(AUTONOMOUS_BLUE_THRESHOLD, Integer.toString(blueThreshold));
    }

    public Properties getProperties(){

        return properties;
    }

    public void saveProperties() {
        File externalDir = Environment.getExternalStorageDirectory();
        File propFile = new File(externalDir, PROPERTIES_FILE);

        try (OutputStream output = new FileOutputStream(propFile)) {
            properties.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public void loadProperies() {
        // SharedPreferences would be a better android app solution, but modifications to
        // FTC classes would be needed to get the context..
        // SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);


        try {
            // Get public external storage folder ( /storage/emulated/0 ).
            File externalDir = Environment.getExternalStorageDirectory();
            File propFile = new File(externalDir, PROPERTIES_FILE);

            try (FileReader reader = new FileReader(propFile)) {
                try (BufferedReader br = new BufferedReader(reader)) {
                    properties.load(reader);
                }
            }
        } catch (IOException e) {
            //log error
        }
    }
}
