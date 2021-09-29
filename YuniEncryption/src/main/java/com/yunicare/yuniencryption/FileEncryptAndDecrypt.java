package com.yunicare.yuniencryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class FileEncryptAndDecrypt {
    private static final String ALGO_VIDEO_ENCRYPTOR = "AES";
//    String encode = "1!4@AyyooRaama!_@#%vid^&**wath_)";
    public FileEncryptAndDecrypt() {
    }

    public static void encrypt(SecretKey key, InputStream in, OutputStream out)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException {
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(1, key);
            out = new CipherOutputStream((OutputStream) out, c);
            byte[] d = new byte[8388608];

            int b;
            while ((b = in.read(d)) != -1) {
                ((OutputStream) out).write(d, 0, b);
                ((OutputStream) out).flush();
            }

            ((OutputStream) out).close();
        } finally {
            ((OutputStream) out).close();
        }
    }

    public static void decrypt(SecretKey key, InputStream in, OutputStream out) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IOException {
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(2, key);
            out = new CipherOutputStream((OutputStream) out, c);
            byte[] d1 = new byte[8388608];

            int c1;
            while ((c1 = in.read(d1)) != -1) {
                ((OutputStream) out).write(d1, 0, c1);
                ((OutputStream) out).flush();
            }

            ((OutputStream) out).close();
        } finally {
            ((OutputStream) out).close();
        }
    }


    static void RecursivePrint(File[] arr, int level) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {

        for (File f : arr) {

            System.out.println("File inside for loop " + f);
            // tabs for internal levels
           /* for (int i = 0; i < level; i++) {
                System.out.print("\t");
            }*/
            if (f.isFile()) {

                File outFile = null;
                String encode = "dddaddfds";
                byte[] decodedKey = encode.getBytes();
                SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
                if (f.getAbsolutePath().contains("E:")) {
                    String output = f.getAbsolutePath().replace("AndroidLiveContents", "VidwathEncrypted");
                    outFile = new File(output);
                }
                encrypt(originalKey, new FileInputStream(f), new FileOutputStream(outFile));
//                }
            } else if (f.isDirectory()) {
                String outputFile = null;
                if (f.getAbsolutePath().contains("E:")) {
                    outputFile = f.getAbsolutePath().replace("AndroidLiveContents", "VidwathEncrypted");
                }
                File directory = null;
                if (outputFile != null) {
                    directory = new File(outputFile);
                    if (!directory.exists()) {
                        directory.mkdir();
                    }
                }

                RecursivePrint(f.listFiles(), level + 1);
            }
        }
    }

    // Driver Method
    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        // Provide full path for directory(change accordingly)
        String maindirpath = "E:\\AndroidLiveContents";

        // File object
        File maindir = new File(maindirpath);
        PrintWriter writer = new PrintWriter("Yuni Encryption Logs ", "UTF-8");
        writer.println("Encryption Completed");


        if (maindir.exists() && maindir.isDirectory()) {
            // array for files and sub-directories
            // of directory pointed by maindir
            File arr[] = maindir.listFiles();

            System.out.println("**********************************************");
            System.out.println("Files from main directory : " + maindir);
            System.out.println("**********************************************");

            // Calling recursive method
            RecursivePrint(arr, 0);
        }
        writer.println("Now it's time to check your encrypted files");
        writer.close();

    }
}