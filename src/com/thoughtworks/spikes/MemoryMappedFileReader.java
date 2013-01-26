package com.thoughtworks.spikes;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MemoryMappedFileReader implements MemoryMappedFileConstants {

    public static void main(String[] args) throws IOException {
        new MemoryMappedFileReader().readLargeFile();
    }

    private void readLargeFile() throws IOException {
        RandomAccessFile file = new RandomAccessFile(FILE_NAME, "r");
        readRecords(file);
    }

    private void readRecords(RandomAccessFile file) throws IOException {
        FileChannel channel = file.getChannel();
        long startOffsetOfBlock = 0L;
        for (int i=0; i < channel.size()/READ_BLOCK_SIZE; i++) {
            System.out.println("Reading block number " + (i+1));
            MappedByteBuffer buffer = channel.map(
                    FileChannel.MapMode.READ_ONLY, startOffsetOfBlock, READ_BLOCK_SIZE);
            int numberOfRecords = READ_BLOCK_SIZE / SIZE_OF_RECORD;
            for (int j=0; j < numberOfRecords; j++) {
                byte[] data = new byte[SIZE_OF_RECORD];
                buffer.get(data, 0, SIZE_OF_RECORD);
            }
            startOffsetOfBlock += READ_BLOCK_SIZE;
        }
    }
}
