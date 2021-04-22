package com.littleBeasts.gameLogic;

import client.Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static config.GlobalConfig.DEBUG_CONSOLE_OUT;

public class LitiClient {
    private static Client client;
    private static List<String> bufferedMessages;
    private static boolean onlineGame;

    public static void readBufferedMessages() {
        if (client.getClientListener().messagesBuffered()) {
            if (DEBUG_CONSOLE_OUT) System.out.println("buffered Messages");
            bufferedMessages = client.getClientListener().getMessageBuffer();
        }
    }

    public static void sendMessageToServer(String message) throws IOException {
        client.sendMessage(message);
    }

    public static List<String> getBufferedMessages() {
        if (bufferedMessages == null)
            return null;
        List<String> tmp = new ArrayList<>(bufferedMessages);
        bufferedMessages.clear();
        return tmp;
    }

    public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        LitiClient.client = client;
    }

    public static boolean isOnlineGame() {
        return onlineGame;
    }

    public static void setOnlineGame(boolean onlineGame) {
        LitiClient.onlineGame = onlineGame;
    }
}
