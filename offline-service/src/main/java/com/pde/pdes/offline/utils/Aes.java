package com.pde.pdes.offline.utils;

import org.apache.commons.io.FileUtils;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * aes加密算法工具类
 * @author ZiTong
 *
 */

public class Aes {
    private static final String passKey = "yourhandissonice";
	/**
     * 初始化 AES Cipher
     * @param sKey
     * @param cipherMode
     * @return
     */
    public static Cipher initAESCipher(String sKey, int cipherMode) {
        //创建Key gen
        Cipher cipher = null;
        try {
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec key = new SecretKeySpec(raw, "AES");
            //cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");


            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"
            IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(cipherMode, key, iv);
            //初始化
//            cipher.init(cipherMode, key);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvalidKeyException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch(Exception e){
        	e.printStackTrace();
        }
        return cipher;
    }


    /**
     * 对文件进行AES加密
     * @param sourceFile
     * @param fileType
     * @param sKey
     * @return
     */
    public static File encryptFile(File sourceFile,String fileType, String sKey){
        //新建临时加密文件
        File encrypfile = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(sourceFile);
            encrypfile = new File("F:/ftp/a.jpg");
            System.out.println(encrypfile.getAbsolutePath()+"-------"+encrypfile.getName());
            outputStream = new FileOutputStream(encrypfile);
            Cipher cipher = initAESCipher(sKey,Cipher.ENCRYPT_MODE);
            //以加密流写入文件
            CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
            byte[] cache = new byte[1024];
            int nRead = 0;
            while ((nRead = cipherInputStream.read(cache)) != -1) {
                outputStream.write(cache, 0, nRead);
                outputStream.flush();
            }
            cipherInputStream.close();
        }  catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }  catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return encrypfile;
    }


    /**
     * 对文件进行AES加密
     * @param inputStream
     * @param targetFilePath
     * @return
     */
    public static File encryptFile(InputStream inputStream,String targetFilePath){
        //新建临时加密文件
        File encrypfile = null;

        OutputStream outputStream = null;
        try {
            encrypfile = new File(targetFilePath);
            System.out.println(encrypfile.getAbsolutePath()+"-------"+encrypfile.getName());
            outputStream = new FileOutputStream(encrypfile);
            Cipher cipher = initAESCipher(passKey,Cipher.ENCRYPT_MODE);
            //以加密流写入文件
            CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
            byte[] cache = new byte[1024];
            int nRead = 0;
            while ((nRead = cipherInputStream.read(cache)) != -1) {
                outputStream.write(cache, 0, nRead);
                outputStream.flush();
            }
            cipherInputStream.close();
        }  catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }  catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return encrypfile;
    }
    /**
     * AES方式解密文件
     * @param sourceFile
     * @return
     */
    public static File decryptFile(File sourceFile,String targetPath){
        File decryptFile = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            decryptFile = new File(targetPath);
            Cipher cipher = initAESCipher(passKey,Cipher.DECRYPT_MODE);
            inputStream = new FileInputStream(sourceFile);
            outputStream = new FileOutputStream(decryptFile);
            CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);
            byte [] buffer = new byte [1024];
            int r;
            while ((r = inputStream.read(buffer)) >= 0) {
                cipherOutputStream.write(buffer, 0, r);
            }
            cipherOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return decryptFile;
    }


	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		//加密1
        File file = new File("F:/ftp/offline_1639290421794/file/23FA4A4C7E3D47F5BCF16446B75E8838.JPG");
        File test = Aes.encryptFile(file, "JPG", "yourhandissonice");
        //加密2
        File encrypfile = Aes.encryptFile(FileUtils.openInputStream(file),"F:/ftp/offline_1639290421794/file/tmp/23FA4A4C7E3D47F5BCF16446B75E8838.JPG");

        //解密
//        Aes.decryptFile(encrypfile,"F:/ftp/offline_1639290421794/file/tmp1/23FA4A4C7E3D47F5BCF16446B75E8838.JPG");
	}


}
