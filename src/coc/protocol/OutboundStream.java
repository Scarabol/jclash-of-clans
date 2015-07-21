package coc.protocol;

import java.util.Stack;

/**
 * Process messages from client (encryption)
 * 
 */
public class OutboundStream extends Stream {

    private Stack<Message> queue = new Stack<>();

    @Override
    public int queue(int maxMessages) {

        int total = 0;
        Message current;
        while (queue.size() > 0 && (current = queue.pop()) != null) {
            ByteStream stm = new ByteStream();
            current.build(stm);
            writeHeader(current.getMessageType(), stm.getSize());
            encode(stm);
            // stream.add(stm);
            if (dataListenner != null) {
                if (!dataListenner.onData(this, stream)) {
                    break;
                }
            }
            total++;
            if (maxMessages > 0 && total == maxMessages) {
                break;
            }
        }
        return total;
    }

    @Override
    public void update(Message message) {
        queue.add(message);
    }

    private void encode(ByteStream stm) {
        byte[] payload = stm.copy();
        byte[] key = rc4.generate(stm.getSize());
        stream.add(xor(payload, key));
    }

    private void writeHeader(int messageType, int size) {
        stream.write(messageType, 2, true);
        int extraSize = 0;
        if (size > 0xFFFF) {
            extraSize = (size >> 16) & 0xFFFF;
            size &= 0xFFFF;
        }
        stream.write(extraSize, 2);
        stream.write(size, 2);
        stream.write(6);
    }

}
