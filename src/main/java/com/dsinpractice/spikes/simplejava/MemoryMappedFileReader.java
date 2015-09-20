package com.dsinpractice.spikes.simplejava;

import com.dsinpractice.spikes.simplejava.Record;
import com.dsinpractice.spikes.simplejava.MemoryMappedFileConstants;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MemoryMappedFileReader implements MemoryMappedFileConstants {

    public static void main(String[] args) throws IOException {
        new MemoryMappedFileReader().readLargeFile();
    }

    private void readLargeFile() throws IOException {

        for (int i=0; i<10; i++) {
            new Thread(new Runnable() {
                            @Override
                            public void run() {
                                RandomAccessFile file = null;
                                try {
                                    file = new RandomAccessFile(FILE_NAME, "r");
                                    for (int j=0; j<10; j++)
                                        readRecords(file);
                                    file.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, "reader" + i).start();
        }
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
                Record record = Record.newFromBuffer(buffer);
            }
            startOffsetOfBlock += READ_BLOCK_SIZE;
        }
    }

}
