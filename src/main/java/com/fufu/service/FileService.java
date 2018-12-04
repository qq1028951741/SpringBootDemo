package com.fufu.service;

import com.fufu.tools.UrlUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class FileService {
	
	@Value("${upload.filePath}")
	private String filePath;
	
	public String uploadFile(byte[] file, String finalPath, String finalName){
        File targetFile = new File(finalPath);
        if(!targetFile.exists()){    
            targetFile.mkdirs();    
        }       
        FileOutputStream out;
		try {
			out = new FileOutputStream(finalPath+finalName);
			
			out.write(file);
	        out.flush();
	        out.close();
	        
		} catch (IOException e) {
			e.printStackTrace();
			return "uploadimg failure: " + e.getMessage();
		}
        
		System.out.println(finalPath + finalName);
		return finalPath + finalName;
    }

	public void uploadFile(InputStream in, String finalPath,
                           String finalName) throws IOException {

		File targetFile = new File(finalPath);
        if(!targetFile.exists()){    
            targetFile.mkdirs();    
        }  
	    //创建一个文件输出流
	    FileOutputStream out = new FileOutputStream(finalPath + finalName);
	    //创建一个缓冲区
        byte buffer[] = new byte[1024];
        //判断输入流中的数据是否已经读完的标识
          int len = 0;
          //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
          while((len=in.read(buffer))>0){
              //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
              out.write(buffer, 0, len);
          }
        in.close();
        //关闭输出流
        out.close();
	}

	public String uploadFileByURL(String folder, String urlStr) throws IOException {
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		String suffix = HttpURLConnection.guessContentTypeFromName(urlStr).split("/")[1];
		String fileName = UUID.randomUUID()+"."+suffix;
		String relativePath = folder +"/" + sf.format(new Date()) +"/";
		String absolutePath=filePath.replace("\\", "/") + relativePath;
		
		File targetFile = new File(absolutePath);
		if(!targetFile.exists()){    
            targetFile.mkdirs();    
        } 
		URL url = new URL(UrlUtil.toUtf8String(urlStr));
		
		File file = new File(absolutePath+fileName);
		
		 // 打开链接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置请求方式为"GET"
        conn.setRequestMethod("GET");
        // 超时响应时间为5秒
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)"); 
        
        // 通过输入流获取图片数据
        InputStream inStream = conn.getInputStream();
        System.out.println(HttpURLConnection.guessContentTypeFromStream(inStream));
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        // 创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        // 每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        // 使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            // 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        // 关闭输入流
        inStream.close();
        // 创建输出流
        FileOutputStream outStream2 = new FileOutputStream(file);
        // 写入数据
        outStream2.write(outStream.toByteArray());
        // 关闭输出流
        outStream2.close();
        
        return relativePath+fileName;
	}
}
