package com.android.utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-27
 * Time: 下午4:44
 * To change this template use File | Settings | File Templates.
 */
public class EncryptUtil {

    private static final int CACHE_SIZE = 1024;



    public static void copy(InputStream inputStream, String strOutFileName) throws IOException {
        File destFile = new File(strOutFileName);

        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        destFile.createNewFile();
        OutputStream myOutput = new FileOutputStream(destFile);
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = inputStream.read(buffer)) != -1) {
            myOutput.write(buffer, 0, length);
            myOutput.flush();
        }


        inputStream.close();
        myOutput.close();
    }


    // 加密解密文件//
    public static boolean enOrDecryptFile(byte[] paramArrayOfByte,
                                          String sourceFilePath, String destFilePath, int mode) {
        File sourceFile = new File(sourceFilePath);
        File destFile = new File(destFilePath);
        CipherOutputStream cout = null;
        FileInputStream in = null;
        FileOutputStream out = null;
        if (sourceFile.exists() && sourceFile.isFile()) {
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
            }
            try {
                destFile.createNewFile();
                in = new FileInputStream(sourceFile);
                out = new FileOutputStream(destFile);
                // 获取密钥//
                KeyGenerator kgen = KeyGenerator.getInstance("AES");
//                kgen.init(128, new SecureRandom("aasdaqr1235134".getBytes()));
                SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG", "Crypto");
                secureRandom.setSeed("aasdaqr1235134".getBytes());
                kgen.init(128, secureRandom);

                SecretKey secretKey = kgen.generateKey();
                byte[] enCodeFormat = secretKey.getEncoded();
                SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");


//                Cipher cipher = Cipher.getInstance("AES");// 创建密码器 4.0 4.2 测试通过
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");         //4.0 4.2 4.4 测试通过
                SecretKeySpec secretKeySpec = new SecretKeySpec(key.getEncoded(), "AES");

                cipher.init(mode, secretKeySpec);
                cout = new CipherOutputStream(out, cipher);
                byte[] cache = new byte[CACHE_SIZE];
                int nRead = 0;
                while ((nRead = in.read(cache)) != -1) {
                    cout.write(cache, 0, nRead);
                    cout.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return false;
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
                return false;
            } catch (InvalidKeyException e) {
                e.printStackTrace();
                return false;
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            } finally {
                if (cout != null) {
                    try {
                        cout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return true;
        }
        return false;
    }

    public static void loadJar(String sourceFilePath, String dexFilePath) {
        enOrDecryptFile(null, sourceFilePath, dexFilePath, Cipher.DECRYPT_MODE);
    }


}
