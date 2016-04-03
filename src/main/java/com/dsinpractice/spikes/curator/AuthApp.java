package com.dsinpractice.spikes.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class AuthApp {
    private final String zookeeperConnect;
    private final String username;
    private final String password;
    private String nodename;

    public AuthApp(String zookeeperConnect, String username, String password, String nodename) {
        this.zookeeperConnect = zookeeperConnect;
        this.username = username;
        this.password = password;
        this.nodename = nodename;
    }

    public static void main(String[] args) throws Exception {
        if (args.length!=4) {
            System.out.println("Usage: java AuthApp <zookeeper connect string> <username> <password> <nodename>");
            System.out.println("where:");
            System.out.println("* username and password are as setup in zookeeper's addauth command with " +
                    "'digest' as auth scheme.");
            System.out.println("* nodename has been setup with an open ACL for this auth");
            System.exit(1);
        }
        AuthApp authApp = new AuthApp(args[0], args[1], args[2], args[3]);
        authApp.run();
    }

    private void run() throws Exception {
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder().
                connectString(zookeeperConnect).
                authorization("digest", (username + ":" + password).getBytes()).
                retryPolicy(new ExponentialBackoffRetry(1000, 3));
        CuratorFramework client = builder.build();
        client.start();
        System.out.println("Getting data for node: " + nodename);
        System.out.println("Data: " + new String(client.getData().forPath(nodename)));
        client.close();
        System.out.println("Closed client.");
    }
}
