package client;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static config.MessageProtocolConstants.*;

public class Message {

    private static Calendar date = new GregorianCalendar();

    public static String encodeOutgoingMessageForClient(String name, String message) {
        return STARTOFHEADING + OUTGOINGMESSAGE + STARTOFTEXT + name + "|" + date.getTime() + "|" + message + ENDOFTEXT + ENDOFTRANSMISSION;
    }

    public static String decodeMessage(String message) {
        String decodedMessage = "";
        // System.out.println(message);
        String head = message.substring(message.indexOf(STARTOFHEADING) + 1, message.indexOf(STARTOFTEXT));
        System.out.println("Head: " + head);
        String body = message.substring(message.indexOf(STARTOFTEXT) + 1, message.indexOf(ENDOFTEXT));
        System.out.println("Body: " + body);
        System.out.println("Incoming " + head.contains(INCOMINGMESSAGE));
        System.out.println("Outgoing " + head.contains(OUTGOINGMESSAGE));
        if (head.contains(OUTGOINGMESSAGE)) {
            decodedMessage = decodeIncomingMessageBody(body);
        } else if (head.contains(INCOMINGMESSAGE)) {
            decodedMessage = decodeOutgoingMessageBody(body);
        }
        System.out.println("Decoded Message: " + decodedMessage);
        return decodedMessage;
    }

    private static String decodeOutgoingMessageBody(String body) {
        System.out.println("Dec Out Body: " + body);
        String[] messageArray = body.split("\\|");
        return messageArray[2] + ": " + messageArray[1];
    }

    private static String decodeIncomingMessageBody(String body) {
        System.out.println("Dec In Body: " + body);
        String[] messageArray = body.split("\\|");
        if (messageArray.length == 3)
            return messageArray[0] + ": " + messageArray[2];
        else {
            return messageArray[0] + ": " + messageArray[4];
        }
    }
}