package coc.protocol.messages;

import java.util.List;

import coc.protocol.Field;
import coc.protocol.Message;

public class EndClientTurnMessage extends Message {

    public EndClientTurnMessage(int messageType, List<Field> fields) {
        super(messageType, fields);
    }

    public EndClientTurnMessage(int messageType) {
        super(messageType);
    }

}
