package com.littlebeasts;

import java.awt.*;

import static config.TestConstants.ROBOT_SLEEP;

public class TestRobotUtils {
    public static void robotButtonPress(int i) {
        try {
            Robot robert = new Robot();
            Thread.sleep(ROBOT_SLEEP);
            robert.keyPress(i);
            Thread.sleep(100);
            robert.keyRelease(i);
            Thread.sleep(ROBOT_SLEEP);
        } catch (AWTException | InterruptedException awtException) {
            awtException.printStackTrace();
        }
    }
}
