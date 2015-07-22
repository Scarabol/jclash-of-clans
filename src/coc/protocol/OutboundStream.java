package coc.protocol;

import java.util.Stack;

import coc.Utils;

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
            // write header
            stream.write(current.getMessageType(), 2, true);
            int extraSize = 0;
            int size = stm.getSize();
            if (size > 0xFFFF) {
                extraSize = (size >> 16) & 0xFFFF;
                size &= 0xFFFF;
            }
            stream.write(extraSize, 2);
            stream.write(size, 2);
            stream.write(6);
            // write payload
            byte[] payload = stm.copy();
            byte[] key = rc4.generate(stm.getSize());
            if (Constants.DEBUG_PACKETS) {
                System.out.println("sending message: " + current.getMessageType());
                Utils.displayBytes(payload);
            }
            stream.add(xor(payload, key));
            // handle listener
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

}
