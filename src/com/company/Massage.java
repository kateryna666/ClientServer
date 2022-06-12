package com.company;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class Massage {
    private int cType;
    private int bUserId;
    private byte[] massage;

    public Massage(ByteBuffer buffer, int wLen){
        this.cType = buffer.getInt();
        this.bUserId = buffer.getInt();
        this.massage = new byte[wLen];
        buffer.get(this.massage, Integer.BYTES*2, wLen);
    }
    byte[] getMassage(){
        return ByteBuffer.allocate(14).putInt(this.cType).putInt(this.bUserId).put(massage).array();
    }

    public int getcType() {
        return cType;
    }

    public void setcType(int cType) {
        this.cType = cType;
    }

    public int getbUserId() {
        return bUserId;
    }

    public void setbUserId(int bUserId) {
        this.bUserId = bUserId;
    }

    public void setMassage(byte[] massage) {
        this.massage = massage;
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
