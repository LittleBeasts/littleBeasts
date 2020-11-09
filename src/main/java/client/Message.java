package client;

import static config.MessageProtocolConstants.*;

public class Message {


    public static String createMessage(String message) {
        return STARTOFHEADING + OUTGOINGMESSAGE + STARTOFTEXT + message + ENDOFTEXT + ENDOFTRANSMISSION;
    }
}
