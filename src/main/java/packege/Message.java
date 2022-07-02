package packege;

import org.json.JSONObject;
import shop.Command;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Objects;

public class Message {
    private int cType;
    private int bUserId;
    private byte[] message;

    public Message(ByteBuffer buffer, int wLen) {
        buffer.order(ByteOrder.BIG_ENDIAN);
        this.cType = buffer.getInt();
        this.bUserId = buffer.getInt();
        this.message = new byte[wLen - Integer.BYTES * 2];
        buffer.get(this.message, 0, wLen - Integer.BYTES * 2);
    }

    public Message(int cType, int bUserId, byte[] message) {

        this.cType = cType;
        this.bUserId = bUserId;
        this.message = message;
    }

    public int getcType() {
        return cType;
    }

    public int getbUserId() {
        return bUserId;
    }

    public byte[] getMessage() {
        return message;
    }

    int length() {
        return Integer.BYTES * 2 + this.message.length;
    }

    byte[] toByte() {
        return ByteBuffer.allocate(this.length())
                .order(ByteOrder.BIG_ENDIAN)
                .putInt(this.cType)
                .putInt(this.bUserId)
                .put(message)
                .array();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message massage1 = (Message) o;
        return cType == massage1.cType &&
                bUserId == massage1.bUserId &&
                Arrays.equals(message, massage1.message);
    }

/*
    @Override
    public int hashCode() {
        int result = Objects.hash(cType, bUserId);
        result = 31 * result + Arrays.hashCode(massage);
        return result;
    }
*/

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject(new String(message));
        return "Message{" +
                "cType=" + Command.values()[cType] +
                ", bUserId=" + bUserId +
                ", message=" + jsonObject.toString() +
                '}';
    }
}
