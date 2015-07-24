package coc.protocol;

public class Field {

    enum FieldType {
        TYPE_QWORD, TYPE_DWORD, TYPE_BYTE, TYPE_STRING, TYPE_BOOLEAN
    }

    public static final Field QWORD = new Field(FieldType.TYPE_QWORD, 8);
    public static final Field DWORD = new Field(FieldType.TYPE_DWORD, 4);

    public static final Field LONG = QWORD;
    public static final Field INT = DWORD;

    public static final Field BYTE = new Field(FieldType.TYPE_BYTE, 1);
    public static final Field BOOL = new Field(FieldType.TYPE_BOOLEAN, 1);
    public static final Field STRING = new Field(FieldType.TYPE_STRING);

    private FieldType type;
    private int size;
    private Object value;
    private byte[] raw;

    public Field(FieldType type) {
        this.type = type;
    }

    public Field(FieldType type, int sizeInBytes) {
        this(type);
        this.size = sizeInBytes;
    }

    public Field(FieldType type, int size, Object value) {
        this.type = type;
        this.size = size;
        this.value = value;
    }

    public Field clone() {
        return new Field(type, size, value);
    }

    public boolean parse(ByteStream message) {
        switch (type) {
        case TYPE_BOOLEAN:
        case TYPE_BYTE:
        case TYPE_DWORD:
        case TYPE_QWORD:
            if (!message.isEnd(size)) {
                value = message.read(size, true);
                return true;
            }
            break;
        case TYPE_STRING:
            int size = (int) message.read(4, true);
            System.out.println("parsing " + type.toString() + " with length " + size);
//            byte[] bsize = new byte[] { message.read(), message.read(), message.read(), message.read() };
//            ByteBuffer buffer = ByteBuffer.wrap(bsize);
//            buffer.order(ByteOrder.BIG_ENDIAN);
//            int size = buffer.getInt();
            if (size > 0 && size != -1) {
                if (message.isEnd(size))
                    return false;
                this.raw = message.clone(size);
                value = new String(this.raw, 0, size);
            }
            return true;
        }
        return false;
    }

    public int build(ByteStream message) {
        switch (type) {
        case TYPE_BYTE:
            message.write(((Byte) value).intValue(), size, true);
            return size;
        case TYPE_BOOLEAN:
        case TYPE_DWORD:
            message.write((Integer) value, size, true);
            return size;
        case TYPE_QWORD:
            message.write((Long) value, size, true);
            return size;
        case TYPE_STRING:
            String s = getString();
            // TODO this could be nicer
            int leng = (s != null && s.length() < 0xFFFFFFFFl) ? s.length() : 0xFFFFFFFF;
            message.write(leng, 4, true);
            if (s != null && s.length() > 0 && s.length() < 0xFFFFFFFFl) {
                message.write(s.getBytes());
            }
            return 4 + leng;
        }
        return 0;
    }

    public byte[] getRaw() {
        return raw;
    }

    public FieldType getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setString(String value) {
        setValue(value);
    }

    public void setInteger(int value) {
        setValue(value);
    }

    public void setBoolean(boolean value) {
        setBoolean(value, 0);
    }

    public void setBoolean(boolean value, int index) {
        int v = value ? 1 : 0;
        v <<= index;
        v |= getNumber();
        setValue(v);
    }

    public String getString() {
        if (value instanceof String) {
            return (String) value;
        }
        return null;
    }

    public Integer getNumber() {
        if (value instanceof Integer) {
            return (Integer) value;
        }
        return null;
    }

    public Long getLong() {
        if (value instanceof Long) {
            return (Long) value;
        }
        return null;
    }

    public Boolean getBoolean() {
        return getBoolean(0);
    }

    public Boolean getBoolean(int pos) {
        if (value instanceof Integer) {
            int n = (Integer) value;

            n >>= pos;
            return (n & 0x1) == 1;

        }
        return null;
    }

    @Override
    public String toString() {
        return type + " = " + value;
    }

}
