package com.dsinpractice.spikes.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;

public class LockApp {

    public static final int BASE_SLEEP_TIME_MS = 1000;
    public static final int MAX_RETRIES = 3;
    public static final String LOCK_PATH = "/curator_spikes/lock";
    public static final int SLEEP_TIME_MS = 60000;
    public static final String SETUP_PATH = "/curator_spikes/setup_done";
    private final CuratorFramework client;
    private final InterProcessMutex interProcessMutex;

    public LockApp(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("Usage: java LockApp <zookeeper_connect_string>");
        }
        client = CuratorFrameworkFactory.newClient(args[0],
                new ExponentialBackoffRetry(BASE_SLEEP_TIME_MS, MAX_RETRIES));
        client.start();
        interProcessMutex = new InterProcessMutex(client, LOCK_PATH);
    }

    public static void main(String[] args) throws Exception {
        LockApp lockApp = new LockApp(args);
        lockApp.run();
    }

    private void run() throws Exception {
        System.out.println("Trying to acquire lock.");
        try {
            interProcessMutex.acquire();
            System.out.println("Acquired lock.");
            doSetup();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            interProcessMutex.release();
            System.out.println("Released lock");
            client.close();
        }
    }

    private void doSetup() throws Exception {
        Stat stat = client.checkExists().forPath(SETUP_PATH);
        if (stat == null) {
            client.create().forPath(SETUP_PATH);
        }
        byte[] bytes = client.getData().forPath(SETUP_PATH);
        boolean setupDone = false;
        if (bytes != null) {
            setupDone = Boolean.valueOf(new String(bytes));
        }
        if (!setupDone) {
            System.out.println("Doing setup");
            Thread.sleep(SLEEP_TIME_MS);
            client.setData().forPath(SETUP_PATH, "true".getBytes());
            System.out.println("Done setup");
        } else {
            System.out.println("Setup already done. Exiting.");
        }
    }
}
