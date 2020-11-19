/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.agribotscode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.Properties;


/**
 *
 */
@TeleOp(name="Configure Skystone Robot", group ="Skystone")
public class RobotConfigurationOpMode extends LinearOpMode {


    private SkystoneRobot robot;

    public RobotConfigurationOpMode() {
        robot = new SkystoneRobot();
    }

    @Override public void runOpMode() {
        //robot.initialize(hardwareMap);
        robot.loadProperies();

        //  Getting all the properties will initialize them if the file is not already there.
        robot.getAutonomousDelay();
        robot.getTeamColor();
        robot.getAutonomousBlueThreshold();
        robot.getAutonomousRedThreshold();

        Properties properties = robot.getProperties();
        String[] keys = new String[properties.size()];
        properties.stringPropertyNames().toArray(keys);
        for (String key : keys) {
            telemetry.addData(key, "%s%s", properties.getProperty(key));
        }
        telemetry.update();

        int propIndex = 0;
        String selectedKey = keys[propIndex];

        while (!isStopRequested()) {
            telemetry.addData("Configure Robot","");

            if (gamepad1.dpad_down) {
                while(gamepad1.dpad_down) {
                    sleep(1);
                }
                propIndex = ++propIndex % keys.length;
            }else if (gamepad1.dpad_up) {
                while(gamepad1.dpad_up) {
                    sleep(1);
                }
                propIndex = --propIndex % keys.length;
            }
            selectedKey = keys[propIndex];

            for (String key : keys) {
                String indicator = "";
                if(key.equals(selectedKey)){
                    indicator = "   *";
                }
                telemetry.addData(key, "%s%s", properties.getProperty(key), indicator);
            }

            if(selectedKey.equals(SkystoneRobot.AUTONOMOUS_DELAY_KEY)){
                if(gamepad1.dpad_right){
                    while(gamepad1.dpad_right) {
                        sleep(1);
                    }
                    robot.setAutonomousDelay(robot.getAutonomousDelay() + 1.0);
                    robot.saveProperties();
                } else if (gamepad1.dpad_left){
                    while(gamepad1.dpad_left) {
                        sleep(1);
                    }
                    robot.setAutonomousDelay(robot.getAutonomousDelay() - 1.0);
                    robot.saveProperties();
                }
            } else if(selectedKey.equals(SkystoneRobot.TEAM_COLOR_KEY)) {
                TeamColor color = robot.getTeamColor();
                int numColors = TeamColor.values().length;
                if(gamepad1.dpad_right){
                    while(gamepad1.dpad_right) {
                        sleep(1);
                    }
                    color = TeamColor.fromOrdinal((color.ordinal() + 1) % numColors );
                    robot.setTeamColor(color);
                    robot.saveProperties();
                } else if (gamepad1.dpad_left){
                    while(gamepad1.dpad_left) {
                        sleep(1);
                    }
                    color = TeamColor.fromOrdinal((color.ordinal() - 1) % numColors );
                    robot.setTeamColor(color);
                    robot.saveProperties();
                }

            } else if(selectedKey.equals(SkystoneRobot.AUTONOMOUS_RED_THRESHOLD)) {
                int redThreshold = robot.getAutonomousRedThreshold();
                if(gamepad1.dpad_right){
                    while(gamepad1.dpad_right) {
                        sleep(1);
                    }
                    redThreshold++;
                }else if (gamepad1.dpad_left){
                    while(gamepad1.dpad_left) {
                        sleep(1);
                    }
                    redThreshold--;
                }
                robot.setAutonomousRedThreshold(redThreshold);
                robot.saveProperties();
            } else if(selectedKey.equals(SkystoneRobot.AUTONOMOUS_BLUE_THRESHOLD)) {
                int blueThreshold = robot.getAutonomousBlueThreshold();
                if(gamepad1.dpad_right){
                    while(gamepad1.dpad_right) {
                        sleep(1);
                    }
                    blueThreshold++;
                }else if (gamepad1.dpad_left){
                    while(gamepad1.dpad_left) {
                        sleep(1);
                    }
                    blueThreshold--;
                }
                robot.setAutonomousBlueThreshold(blueThreshold);
                robot.saveProperties();
            }
            telemetry.update();
        }
    }
}
