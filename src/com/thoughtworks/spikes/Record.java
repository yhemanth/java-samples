package com.thoughtworks.spikes;

import java.nio.MappedByteBuffer;
import java.util.Random;

public class Record implements MemoryMappedFileConstants {
    public void write(MappedByteBuffer buffer, Random random) {
        byte[] data = new byte[SIZE_OF_RECORD];
        random.nextBytes(data);
        buffer.put(data);
    }
}
