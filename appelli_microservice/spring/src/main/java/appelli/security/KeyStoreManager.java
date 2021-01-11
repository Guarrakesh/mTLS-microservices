package appelli.security;

import java.io.*;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Enumeration;

public class KeyStoreManager {

    static KeyStoreManager _instance;

    private KeyStoreManager() {};


    private KeyStore keyStore;

    private char[] keyPassword;

    public static KeyStoreManager getInstance() {
        if (KeyStoreManager._instance == null) {
            KeyStoreManager._instance = new KeyStoreManager();
            KeyStoreManager._instance.init();
        }
        return _instance;
    }

    private void init()
    {
        KeyManager.getInstance();
        try {
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void loadKeyStore(String password, InputStream keyStoreData) throws IOException, NoSuchAlgorithmException, CertificateException {
        keyPassword = password.toCharArray();
        keyStore.load(keyStoreData, keyPassword);
    }


    public KeyStore.Entry getKey(String name)
    {
        try {
            return keyStore.getEntry(name, new KeyStore.PasswordProtection(keyPassword));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setKey(String name, KeyStore.SecretKeyEntry key)
    {
        try {
            keyStore.setEntry(name, key, new KeyStore.PasswordProtection(keyPassword));
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    public void setKey(String name, KeyStore.PrivateKeyEntry key)
    {
        try {
            keyStore.setEntry(name, key, new KeyStore.PasswordProtection(keyPassword));
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }
    public void saveKeyStore(String password, OutputStream keyStoreData)
    {
        try {
            keyStore.store(keyStoreData, password.toCharArray());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Enumeration<String> getAllEntries() throws KeyStoreException {
        return keyStore.aliases();
    }

    public void update(File f)
    {
        try {
            this.saveKeyStore(new String(keyPassword), new FileOutputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Certificate getCertificate(String alias)
    {
        try {
            return this.keyStore.getCertificate(alias);
        } catch (KeyStoreException e) {
            return null;
        }
    }
}
