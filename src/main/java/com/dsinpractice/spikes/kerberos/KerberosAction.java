package com.dsinpractice.spikes.kerberos;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.SecurityUtil;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;

import static org.apache.hadoop.security.UserGroupInformation.AuthenticationMethod.KERBEROS;

public class KerberosAction {

    private final String user;
    private final String keytabFilePath;

    public KerberosAction(String user, String keytabFilePath) {
        this.user = user;
        this.keytabFilePath = keytabFilePath;
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: java com.dsinpractice.spikes.kerberos.KerberosAction <username> <keytab file>");
            System.exit(-1);
        }
        KerberosAction kerberosAction = new KerberosAction(args[0], args[1]);
        kerberosAction.run();
    }

    private void run() throws IOException {
        printUserInfo("Before login");
        Configuration conf = new Configuration();
        SecurityUtil.setAuthenticationMethod(KERBEROS, conf);
        UserGroupInformation.setConfiguration(conf);
        UserGroupInformation.loginUserFromKeytab(user, keytabFilePath);
        printUserInfo("After login");
    }

    private void printUserInfo(String message) throws IOException {
        UserGroupInformation currentUser = UserGroupInformation.getCurrentUser();
        UserGroupInformation loginUser = UserGroupInformation.getLoginUser();
        System.out.println(String.format("%s: Current user: %s, Login user: %s",
                message, currentUser.getUserName(), loginUser.getUserName()));
    }
}
