package com.littlebeasts;

import config.TestConstants;

import java.awt.*;

public class TestRobot {
    public static void robotButtonPress(int i) {
        try {
            Robot robert = new Robot();
            Thread.sleep(TestConstants.ROBOT_SLEEP);
            robert.keyPress(i);
            Thread.sleep(100);
            robert.keyRelease(i);
            Thread.sleep(TestConstants.ROBOT_SLEEP);
        } catch (AWTException | InterruptedException awtException) {
            awtException.printStackTrace();
        }
    }
}
