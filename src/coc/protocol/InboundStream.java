package coc.protocol;

import coc.Utils;
import coc.protocol.messages.MessageFactory;

/**
 * Process message from server (decryption)
 *
 */
public class InboundStream extends Stream {

    @Override
    public int queue(int maxMessages) {
        int total = 0;
        stream.reset();
        while (!stream.isEnd()) {
            // offsets from https://github.com/clanner/cocdp/wiki/Protocol
            // alternate offsets http://www.androidgamehacks.net/forums/showthread.php?tid=33923&pid=324165#pid324165
            int messageType = (int) stream.read(2, true);
            int messageSize = (int) stream.read(2);
            messageSize += stream.read(2);
            stream.skip();
            if (stream.isEnd(messageSize)) {
                return total;
            }
            byte[] payload = stream.clone(messageSize);
            byte[] data = rc4.decrypt(payload);
            if (Constants.DEBUG_PACKETS) {
                System.out.println("received message: " + messageType);
                Utils.displayBytes(data);
            }
            Message message = MessageFactory.get(messageType);
            if (message != null) {
                if (message.parse(new ByteStream(data))) {
                    messages.add(message);
                    if (messageListener != null) {
                        messageListener.onMessage(this, message);
                    }
                    total++;
                } else {
                    System.out.println("Error parsing message " + messageType + " at field[" + message.getIndexError()
                            + "]: " + message.getFieldError());
                }
            } else {
                System.err.println("Received unknown message type " + messageType + "!");
            }
            stream.flush();
            if (maxMessages > 0 && total == maxMessages) {
                break;
            }
        }
        System.out.println("Total messages: " + total + " " + stream.getSize());
        return total;
    }

}
