package com.thoughtworks.spikes;

public interface MemoryMappedFileConstants {
    static final int NUMBER_OF_RECORDS = 1024 * 1024;
    static final int SIZE_OF_RECORD = 1024;
    static final int READ_BLOCK_SIZE = 128 * 1024 * 1024;
    static final int WRITE_BLOCK_SIZE = NUMBER_OF_RECORDS * SIZE_OF_RECORD;
    static final String FILE_NAME = "/tmp/largefile.txt";
}
