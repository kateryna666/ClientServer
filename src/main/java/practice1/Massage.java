package practice1;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class Massage {
    private int cType;
    private int bUserId;
    private byte[] massage;

    public Massage(ByteBuffer buffer, int wLen){
        this.cType = buffer.getInt();
        this.bUserId = buffer.getInt();
        this.massage = new byte[wLen - Integer.BYTES*2];
        buffer.get(this.massage, Integer.BYTES*2, wLen);
    }
    public Massage(int cType, int bUserId, byte[] massage ){
        this.cType = cType;
        this.bUserId = bUserId;
        this.massage = massage;
    }
    int length(){
        return Integer.BYTES*2+this.massage.length;
    }
    byte[] toByte(){
        return ByteBuffer.allocate(this.length())
                .putInt(this.cType)
                .putInt(this.bUserId)
                .put(massage)
                .array();
    }


    @Override
    public String toString() {
        return "Massage{" +
                "cType=" + cType +
                ", bUserId=" + bUserId +
                ", massage=" + Arrays.toString(massage) +
                '}';
    }
}
