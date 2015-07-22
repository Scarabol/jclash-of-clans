package coc.protocol.messages;

import coc.protocol.Field;
import coc.protocol.Message;

public class LoginFailed extends Message {

    // structure from https://github.com/clanner/cocdp/wiki/Messages-LoginFailed

    private Integer failureReason;
    private String fingerprint; // fingerprint.json contents
    private String hostname;
    private String url; // XXXXX-XXXXX.rNN.MMM.rackcdn.com
    private String appStore; // itms-apps://itunes.apple.com/app/id529479190
    private String unknown;
    private String unknown2;
    private Byte unknown3;
    private String compressed; // compressed fingerprint.json (since version 7)
    private String unknown4;

    public LoginFailed() {
        super(20103);
        addField(Field.INT, failureReason);
        addField(Field.STRING, fingerprint);
        addField(Field.STRING, hostname);
        addField(Field.STRING, url);
        addField(Field.STRING, appStore);
        addField(Field.STRING, unknown);
        addField(Field.STRING, unknown2);
        addField(Field.BYTE, unknown3);
        addField(Field.STRING, compressed);
        addField(Field.STRING, unknown4);
    }

}
