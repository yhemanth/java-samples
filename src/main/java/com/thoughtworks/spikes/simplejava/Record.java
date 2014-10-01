package com.thoughtworks.spikes.simplejava;

import com.thoughtworks.spikes.simplejava.MemoryMappedFileConstants;

import java.nio.MappedByteBuffer;

public class Record implements MemoryMappedFileConstants {
    private byte[] data;

    public Record(byte[] data) {
        this.data = data;
    }

    public void write(MappedByteBuffer buffer) {
        buffer.put(data);
    }

    public static Record newFromBuffer(MappedByteBuffer buffer) {
        byte[] data = new byte[SIZE_OF_RECORD];
        buffer.get(data, 0, SIZE_OF_RECORD);
        return new Record(data);
    }
}
