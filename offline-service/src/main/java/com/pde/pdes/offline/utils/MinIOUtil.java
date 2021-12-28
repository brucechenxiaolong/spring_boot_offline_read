package com.pde.pdes.offline.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

import io.minio.MinioClient;
/**
 * minIO测试类，参考:
   http://jvm123.com/2020/02/minio-jian-jie-java-api.html

 */
public class MinIOUtil {

	private static final String endpoint = "http://192.168.12.130:9555/";

	private static final String accessKey = "pde";

	private static final String secretKey = "pde88888";

	private static final String bucket = "template";//"demo-bucket";//桶

	public static boolean upload(InputStream stream, String contentType) throws Exception {
		MinioClient minioClient = new MinioClient(endpoint, accessKey, secretKey);
		boolean isExist = minioClient.bucketExists(bucket);
		if (isExist)
			System.out.println("Bucket {} already exists.");
		else
			minioClient.makeBucket(bucket);
		minioClient.putObject(bucket, "offline/offline.zip", stream, contentType);
		return true;
	}
	
	public static InputStream getStream(String bucket, String fileName) {
        InputStream is = null;
        try {
            MinioClient minioClient = new MinioClient(endpoint, accessKey, secretKey); // 再实例化一个客户端
            is = minioClient.getObject(bucket, fileName);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return is;
    }
	
	public static File getFile(String bucket, String fileName) {
        InputStream is = getStream(bucket, fileName);
        File dir = new File("C:\\Users\\Administrator\\Desktop\\minIO");
        if (!dir.exists() || dir.isFile()) {
            dir.mkdirs();
        }
        File file = new File("C:\\Users\\Administrator\\Desktop\\minIO\\1.zip");
        try {
            FileUtils.copyToFile(is, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
	
	public static void main(String[] args) {
////		System.out.println(System.getProperty("user.dir"));
//		
//		
//		//下载
////		getFile(bucket,"a/1.zip");
		
		//上传
		File f = new File("C:\\Users\\Administrator\\Desktop\\minIO\\offline.zip");
		InputStream is = null;
		try {
			is = new FileInputStream(f);
		    upload(is,"zip");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
