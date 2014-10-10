package com.dsinpractice.spikes.simplejava;

import com.dsinpractice.spikes.simplejava.Record;

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
        RandomAccessFile file = new RandomAccessFile(FILE_NAME, "rw");
        MappedByteBuffer buffer = file.getChannel().map(
                FileChannel.MapMode.READ_WRITE, 0, WRITE_BLOCK_SIZE);
        Random random = new Random();
        byte[] bytes = new byte[SIZE_OF_RECORD];
        random.nextBytes(bytes);
        for (int i=0; i<NUMBER_OF_RECORDS; i++) {
            new Record(bytes).write(buffer);
        }
        file.close();
    }

}
