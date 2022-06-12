package practice1;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Objects;

public class Massage {
    private int cType;
    private int bUserId;
    private byte[] massage;

    public Massage(ByteBuffer buffer, int wLen){
        buffer.order(ByteOrder.BIG_ENDIAN);
        this.cType = buffer.getInt();
        this.bUserId = buffer.getInt();
        this.massage = new byte[wLen - Integer.BYTES*2];
        buffer.get(this.massage, 0, wLen - Integer.BYTES*2);
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
                .order(ByteOrder.BIG_ENDIAN)
                .putInt(this.cType)
                .putInt(this.bUserId)
                .put(massage)
                .array();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Massage massage1 = (Massage) o;
        return cType == massage1.cType &&
                bUserId == massage1.bUserId &&
                Arrays.equals(massage, massage1.massage);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(cType, bUserId);
        result = 31 * result + Arrays.hashCode(massage);
        return result;
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
