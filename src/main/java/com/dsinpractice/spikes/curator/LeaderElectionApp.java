package com.dsinpractice.spikes.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class LeaderElectionApp implements LeaderLatchListener {

    public static final int BASE_SLEEP_TIME_MS = 1000;
    public static final int MAX_RETRIES = 5;
    public static final String LATCH_PATH_NAME = "/app_latch_path";
    private final String zkHost;
    private final int zkPort;
    private String id;
    private LeaderLatch leaderLatch;
    private CuratorFramework curatorFramework;

    public LeaderElectionApp(String zkHost, int zkPort, String id) {
        this.zkHost = zkHost;
        this.zkPort = zkPort;
        this.id = id;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: java LeaderElectionApp <id>");
            System.exit(-1);
        }
        LeaderElectionApp service = new LeaderElectionApp("localhost", 2181, args[0]);
        try {
            service.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void start() throws Exception {
        startLeaderElection();
        while (true) {
            try {
                Thread.sleep(60000);
            } catch (InterruptedException ie) {
                leaderLatch.close();
                curatorFramework.close();
            }
        }
    }

    private void startLeaderElection() throws Exception {
        System.out.println("Starting leader election for " + id);
        curatorFramework = CuratorFrameworkFactory.newClient(getConnectionString(),
                new ExponentialBackoffRetry(BASE_SLEEP_TIME_MS, MAX_RETRIES));
        curatorFramework.start();
        System.out.println("Starting leader latch for " + id);
        leaderLatch = new LeaderLatch(curatorFramework, LATCH_PATH_NAME, id);
        leaderLatch.addListener(this);
        leaderLatch.start();
    }

    private String getConnectionString() {
        return String.format("%s:%d", zkHost, zkPort);
    }

    @Override
    public void isLeader() {
        System.out.println(String.format("App with ID %s is leader", id));
    }

    @Override
    public void notLeader() {
        System.out.println(String.format("App with ID %s is not leader", id));
    }
}
