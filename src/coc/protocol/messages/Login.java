package coc.protocol.messages;

import coc.protocol.Field;
import coc.protocol.Message;

public class Login extends Message {

    // structure from https://github.com/clanner/cocdp/wiki/Messages-Login

    private Long userid = (long) 0;
    private String usertoken = null;
    private Integer majorVersion = 7;
    private Integer unknown = 0; // this field is always zero
    private Integer minorVersion = 1;
    private String masterhash = "8f4d85fafcb47dd4f357b429803d23666867402b";
    private String unknown2 = null;
    private String openUdid = "c44afc8352480528";
    private String mac = "00:16:3e:90:bb:3d";
    private String phoneModel = "GT-I9100";
    private Integer localeKey = 2000003;
    private String language = "es";
    private String advertisingGuid = "97fd4f3a-70ca-485c-bcb0-b5d38ba3b466";
    private String osVersion = "4.4.4";
    private Byte isAndroid = 1; // if this is 0 coc suggests apple, 1 is android
    private String unknown4 = null;
    private String androidDeviceid = "aaaaaaaaaaaaaa";
    private String facebookAttributionId = null;
    private Byte isAdvertisingTrackingEnabled = 0;
    private String vendorUuid = null;
    private Integer clientSeed = 1177687346;

    public Login() {
        super(10101);
        addField(Field.QWORD, userid);
        addField(Field.STRING, usertoken);
        addField(Field.DWORD, majorVersion);
        addField(Field.DWORD, unknown);
        addField(Field.DWORD, minorVersion);
        addField(Field.STRING, masterhash);
        addField(Field.STRING, unknown2);
        addField(Field.STRING, openUdid);
        addField(Field.STRING, mac);
        addField(Field.STRING, phoneModel);
        addField(Field.DWORD, localeKey);
        addField(Field.STRING, language);
        addField(Field.STRING, advertisingGuid);
        addField(Field.STRING, osVersion);
        addField(Field.BYTE, isAndroid);
        addField(Field.STRING, unknown4);
        addField(Field.STRING, androidDeviceid);
        addField(Field.STRING, facebookAttributionId);
        addField(Field.BYTE, isAdvertisingTrackingEnabled);
        addField(Field.STRING, vendorUuid);
        addField(Field.DWORD, clientSeed);
    }

}
