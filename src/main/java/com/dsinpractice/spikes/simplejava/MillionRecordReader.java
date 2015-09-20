package com.dsinpractice.spikes.simplejava;

import org.mapdb.DBMaker;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;

public class MillionRecordReader {

    public static final String FILE_NAME = "/Users/yhemanth/Downloads/datasets/adnear/temp_labels.dat.small";
    private static final String OUT_FILE_NAME = "/Users/yhemanth/Downloads/datasets/adnear/temp_labels.dat.bin";

    private Map<String, String> map = new HashMap<String, String>();

    enum TimeWindow {
        AN (1),
        EE (2),
        EM (3),
        LE (4),
        LM (5),
        LN (6),
        MM (7),
        MN (8),
        NH (9);
        private final byte bVal;

        TimeWindow(int bVal) {
            this.bVal = (byte) bVal;
        }

        static TimeWindow fromValue(int bVal) {
            TimeWindow[] values = TimeWindow.values();
            for (TimeWindow value : values) {
                if (value.bVal == bVal) return value;
            }
            return null;
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        MillionRecordReader millionRecordReader = new MillionRecordReader();
        millionRecordReader.generate();
        millionRecordReader.read();
        //Thread.sleep(120000);
    }

    private void read() throws IOException {
        DataInputStream inputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(OUT_FILE_NAME)));
        long timeBefore = System.currentTimeMillis();
        while (true) {
            try {
                String latitude = String.valueOf(inputStream.readFloat());
                String longitude = String.valueOf(inputStream.readFloat());
                String timeWindow = TimeWindow.fromValue(inputStream.readByte()).name();
                String locationCategory = String.valueOf((char) inputStream.readByte());
                String latLongTimeWindow = String.format("%s,%s,%s", latitude, longitude, timeWindow);
                map.put(latLongTimeWindow, locationCategory);
            } catch (EOFException eof) {
                System.out.println("Read all records " + map.size());
                System.out.println("Time taken " + (System.currentTimeMillis() - timeBefore));
                break;
            }
        }
        inputStream.close();
    }

    private void generate() throws IOException {

        DataOutputStream stream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(OUT_FILE_NAME, false)));

        BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME));
        String line = bufferedReader.readLine();
        int nRecords = 0;
        while (line != null) {
            String[] tokens = line.split("\\t");
            String latLongTimeWindow = tokens[0];
            String[] locationDetails = latLongTimeWindow.split(",");
            float latitude = Float.parseFloat(locationDetails[0]);
            float longitude = Float.parseFloat(locationDetails[1]);
            byte timeWindow = TimeWindow.valueOf(locationDetails[2]).bVal;
            byte b = (byte) tokens[1].charAt(0);
            stream.writeFloat(latitude);
            stream.writeFloat(longitude);
            stream.writeByte(timeWindow);
            stream.writeByte(b);
            nRecords++;
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        stream.flush();
        stream.close();
        System.out.println("Finished writing records " + nRecords);
    }
}
