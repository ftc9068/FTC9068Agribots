package org.firstinspires.ftc.agribotscode;

import android.os.Environment;

import com.qualcomm.robotcore.hardware.HardwareMap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class SkystoneRobot {
    private static final String PROPERTIES_FILE = "SkystoneRobot.properties";
    private Properties properties = new Properties();

    public SkystoneRobot() {
    }

    public void initialize(HardwareMap hardwareMap){
        loadProperies();
    }

    public double getAutonomousDelay(){
        double delay = 0;
        String delayStr = properties.getProperty("autonomous.delay");
        if (delayStr != null) {
            delay = Double.parseDouble(delayStr);
        }
        return delay;
    }

    private void loadProperies() {
        // Get public external storage folder ( /storage/emulated/0 ).

        try {
            File externalDir = Environment.getExternalStorageDirectory();
            File licenseFile = new File(externalDir, PROPERTIES_FILE);

            try (FileReader reader = new FileReader(licenseFile)) {
                try (BufferedReader br = new BufferedReader(reader)) {
                    properties.load(reader);
                }
            }
        } catch (IOException e) {
            //log error
        }
    }
}
