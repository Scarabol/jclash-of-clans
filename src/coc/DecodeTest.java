package coc;

import java.io.UnsupportedEncodingException;

import coc.protocol.ByteStream;
import coc.protocol.Constants;

public class DecodeTest {

    private static char[] login = { // insert packet here
            };

    public static void main(String[] args) throws UnsupportedEncodingException {
        ByteStream stream = new ByteStream(new String(login).getBytes("utf-8"));
        int messageType = (int) stream.read(2, true);
        System.out.println("message type is " + messageType);
        int messageSize = (int) stream.read(2);
        messageSize += stream.read(2);
        System.out.println("message size is " + messageSize);
        stream.skip();
        if (stream.isEnd(messageSize)) {
            return;
        }
        byte[] payload = stream.clone(messageSize);
        RC4 rc4 = new RC4((Constants.RC4_KEY + "nonce").getBytes());
        rc4.generate(rc4.getKeylen());
        byte[] data = rc4.encrypt(payload, false);
        Utils.displayBytes(data);
    }

}
