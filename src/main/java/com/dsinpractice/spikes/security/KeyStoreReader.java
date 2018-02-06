package com.dsinpractice.spikes.security;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Enumeration;

public class KeyStoreReader {

    private final KeyStore keystore;

    public KeyStoreReader(String keyStoreFile, String passwd) {
        keystore = init(keyStoreFile, passwd);
    }

    private KeyStore init(String keyStoreFile, String passwd) {
        KeyStore keystore;
        try {
            keystore = KeyStore.getInstance("jceks");
        } catch (KeyStoreException e) {
            throw new RuntimeException("Cannot initialize jceks keystore type", e);
        }

        FileInputStream keyStoreFileInputStream;
        try {
            keyStoreFileInputStream = new FileInputStream(keyStoreFile);
            keystore.load(keyStoreFileInputStream, passwd.toCharArray());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Cannot find keystore file " + keyStoreFile, e);
        } catch (CertificateException e) {
            throw new RuntimeException("Certificate exception from keystore file " + keyStoreFile, e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithm exception from keystore file " + keyStoreFile, e);
        } catch (IOException e) {
            throw new RuntimeException("IOException from keystore file " + keyStoreFile, e);
        }

        return keystore;
    }

    private void readEntries(String passwd) {
        Enumeration<String> aliases;
        try {
            aliases = keystore.aliases();
        } catch (KeyStoreException e) {
            throw new RuntimeException("Cannot load aliases from keystore");
        }
        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            try {
                if (alias.isEmpty()) {
                    System.out.println("Ignoring empty alias");
                    continue;
                }
                Key key = keystore.getKey(alias, passwd.toCharArray());
                System.out.println(String.format("Alias: %s, Value: %s", alias, new String(key.getEncoded())));
            } catch (KeyStoreException e) {
                throw new RuntimeException("Keystore exception in reading alias " + alias, e);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("NoSuchAlgorithm exception in reading alias " + alias, e);
            } catch (UnrecoverableKeyException e) {
                throw new RuntimeException("UnrecoverableKey exception in reading alias " + alias, e);
            }
        }
    }

    public static void main(String[] args) {
        KeyStoreReader keyStoreReader = new KeyStoreReader(args[0], args[1]);
        keyStoreReader.readEntries(args[1]);
    }

}
