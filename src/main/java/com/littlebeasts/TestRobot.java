package com.littlebeasts;

import config.TestConfig;

import java.awt.*;

public class TestRobot {
    public static void robotButtonPress(int i) {
        try {
            Robot robert = new Robot();
            Thread.sleep(TestConfig.ROBOT_SLEEP);
            robert.keyPress(i);
            Thread.sleep(100);
            robert.keyRelease(i);
            Thread.sleep(TestConfig.ROBOT_SLEEP);
        } catch (AWTException | InterruptedException awtException) {
            awtException.printStackTrace();
        }
    }
}
