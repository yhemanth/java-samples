package com.thoughtworks.spikes;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Random;

public class MemoryMappedFileWriter implements MemoryMappedFileConstants {

    public static void main(String[] args) throws IOException {
        new MemoryMappedFileWriter().writeLargeFile();
    }

    public void writeLargeFile() throws IOException {
        RandomAccessFile file = new RandomAccessFile("/tmp/largefile.txt", "rw");
        MappedByteBuffer buffer = file.getChannel().map(
                FileChannel.MapMode.READ_WRITE, 0, SIZE_OF_RECORD * NUMBER_OF_RECORDS);
        Random random = new Random();
        for (int i=0; i<NUMBER_OF_RECORDS; i++) {
            new Record().write(buffer, random);
        }
        file.close();
    }

}
