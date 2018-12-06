package com.fufu.controller;

import com.fufu.service.FileService;
import com.fufu.tools.FileTypeUtil;
import com.fufu.tools.HttpClient;
import com.fufu.tools.UrlUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/test1")
public class FileController {
    private static final Logger log = LoggerFactory.getLogger(FileController.class);
    @Value("${upload.filePath}")
    private String filePath;
    @Value("${serviceIP}")
    private String serviceIP;
    @Value("${servicePort}")
    private String servicePort;
    @Value("${rootPath}")
    private String rootPath;
    @Autowired FileService fileService;
    //接收单个file
    @PostMapping("/test/upload1")
    public Map<String, String> upload1(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("[文件类型] - [{}]", file.getContentType());
        log.info("[文件名称] - [{}]", file.getOriginalFilename());
        log.info("[文件大小] - [{}]", file.getSize());
        try {
            file.transferTo(new File(filePath + file.getOriginalFilename()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, String> result = new HashMap<>(16);
        result.put("contentType", file.getContentType());
        result.put("fileName", file.getOriginalFilename());
        result.put("fileSize", file.getSize() + "");
        return result;
    }

    //接收多个file
    @PostMapping("/test/upload2")
    public List<Map<String, String>> upload2(@RequestParam("file") MultipartFile[] files) throws IOException {
        if (files == null || files.length == 0) {
            return null;
        }
        List<Map<String, String>> results = new ArrayList<>();
        for (MultipartFile file : files) {
            file.transferTo(new File(filePath + file.getOriginalFilename()));
            Map<String, String> map = new HashMap<>(16);
            map.put("contentType", file.getContentType());
            map.put("fileName", file.getOriginalFilename());
            map.put("fileSize", file.getSize() + "");
            results.add(map);
        }
        return results;
    }

    //接收base64图片字符串
    @PostMapping("/test/upload3")
    public void upload2(String base64) throws IOException {
        final File tempFile = new File(filePath+"upload3.jpg");
        //防止有的没传 data:image/jpeg;base64, 的情况
        String[] d = base64.split("base64,");
        final byte[] bytes = Base64Utils.decodeFromString(d.length > 1 ? d[1] : d[0]);
        FileCopyUtils.copy(bytes, tempFile);

    }

    //根据http协议下载图片
    @GetMapping("/test/upload4")
    public String downloadFileByURL(String urlStr) throws IOException {
        URL url = new URL(UrlUtil.toUtf8String(urlStr));
        String picPath = filePath+"upload4.jpg";
        File file = new File(picPath);
        // 打开链接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设置请求方式为"GET"
        conn.setRequestMethod("GET");
        // 超时响应时间为5秒
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");

        // 通过输入流获取图片数据
        InputStream inStream = conn.getInputStream();
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

        return picPath;
    }

    //实现从本地取图片上传到对应服务器上
    @GetMapping(value="/uploadLocalFile")
    public void uploadLocalFile(){
        String relativePath = "test/haha.jpg";
        String picUrl = null;
        try {
            picUrl = HttpClient.getInstance().uploadFileImpl("http://"+serviceIP+":"+servicePort+"/test1/uploadFile", rootPath + relativePath, "aTestFileName", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-----picUrl:"+picUrl+"-----");
    }

    /**
     * 单个文件上传
     * @param file
     * @param folder 文件夹根据上传文件type自动生成
     * @param id
     * @param request
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @ApiOperation(value = "单个文件上传", notes= "根据type值将所上传图片分文件夹存放，如type=我是一个莫得感情的文件夹，文件路径将为“/我是一个莫得感情的文件夹/20181111/110.jpg”")
    @PostMapping(value="/uploadFile")
    public @ResponseBody String uploadFile( MultipartFile file, @RequestParam(defaultValue = "Common") String type, HttpServletRequest request) throws IOException, ServletException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String relativePath = null;
        String finalName = null;
        if(file != null) {
            String fileName = file.getOriginalFilename();
            //获取文件后缀名
            String ext= FileTypeUtil.getFileSuffix(fileName);
            if(!"jpg".equalsIgnoreCase(ext)&& !"png".equalsIgnoreCase(ext)&& !"bmp".equalsIgnoreCase(ext) && !"jpeg".equalsIgnoreCase(ext))
                return "上传的图片文件格式不正确";
            relativePath = type + "/" + sf.format(new Date()) +"/";
            String finalPath = filePath + relativePath;
            try {
                finalName = new Random().nextInt(999999) + "."+ ext;
                String fullPath = fileService.uploadFile(file.getBytes(), finalPath, finalName);
                if(fullPath.isEmpty()) {
                    return null;
                }
            }catch (Exception e) {
                e.printStackTrace();
                return "uploadimg failure: " + e.getMessage();
            }
        }else {
            Collection<Part> parts = request.getParts();
            for (Iterator<Part> iterator = parts.iterator(); iterator.hasNext();) {
                Part part = iterator.next();
                finalName = part.getSubmittedFileName();
                relativePath = part.getName() + "/" + sf.format(new Date()) + "/";
                String finalPath = filePath + relativePath;
                fileService.uploadFile(part.getInputStream(), finalPath, finalName);
            }
        }
        return relativePath + finalName;
    }
}
