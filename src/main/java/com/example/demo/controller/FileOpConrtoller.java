package com.example.demo.controller;


import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import java.io.File;
import java.io.IOException;

@Controller
public class FileOpConrtoller {

        private static final Logger log = LoggerFactory.getLogger(FileOpConrtoller.class);


    @GetMapping("/fileOp")
    public String fileOp(){
        return "fileOp";
    }
    // 单个文件上传

    @ResponseBody
    @RequestMapping(value = "/upload")
    public String upload(@RequestParam("file") MultipartFile file) {

        try {
            if (file.isEmpty()) {
                return "文件为空";
            }
            // 获取文件名
            String fiNa = file.getOriginalFilename();
            // 获取文件前缀名
            String prefix = fiNa.substring(0,fiNa.lastIndexOf("."));
            log.info("上传的文件名为：" + fiNa);//写日志
            // 获取文件的后缀名
            String suffixName = fiNa.substring(fiNa.lastIndexOf("."));
            log.info("上传的文件后缀名为：" + suffixName);//写日志
            // 判断文件的后缀名
            String txt = ".txt";
                if(txt.equals(suffixName)){
                    //时间格式化格式
                    SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyyMMddHHmmssSSS");
                    //获取当前时间并作为时间戳
                    String timeStamp=simpleDateFormat.format(new Date());
                    //拼接新的文件名
                    String newName =timeStamp+prefix+txt;
                    // 设置文件存储路径
                    String filePath = "G:/javap/File/";
                    String path = filePath + newName;
                    File dest = new File(new File(path).getAbsolutePath());// dest为文件，有多级目录的文件
                    // 检测是否存在目录
                    if (!dest.getParentFile().exists()) {//因此这里使用.getParentFile()，目的就是取文件前面目录的路径
                        dest.getParentFile().mkdirs();// 新建文件夹
                    }

                    file.transferTo(dest);// 文件写入
                    dest.setReadOnly();// 文件只读
                    dest.setExecutable(false);// 文件不可执行
                    return "上传成功";
                }
                else{
                    return "上传失败！仅允许上传txt类型文件";
                }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

    // 多个文件一起上传
    @ResponseBody
    @PostMapping("/batch")
    public String handleFileUpload(HttpServletRequest request){

        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            // 获取文件名
            String fiNa = file.getOriginalFilename();
            // 获取文件前缀名
            String prefix = fiNa.substring(0,fiNa.lastIndexOf("."));
            // 获取文件的后缀名
            String suffixName = fiNa.substring(fiNa.lastIndexOf("."));
            // 判断文件的后缀名
            String txt = ".txt";
            if (txt.equals(suffixName)) {
                //时间格式化格式
                SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyyMMddHHmmssSSS");
                //获取当前时间并作为时间戳
                String timeStamp=simpleDateFormat.format(new Date());
                //拼接新的文件名
                String newName =timeStamp+prefix+txt;
                String filePath = "G:/javap/File/";       // 文件路径
                String path = filePath + newName;
                File dest = new File(filePath);
                // 检测是否存在目录
                if (!dest.exists()) {
                    dest.mkdirs();// 新建文件夹
                }
                if (!file.isEmpty()) {
                    try {
                        byte[] bytes = file.getBytes();
                        stream = new BufferedOutputStream(new FileOutputStream(
                                new File(filePath + newName)));//设置文件路径及名字
                        stream.write(bytes);// 写入
                        stream.close();
                        dest.setReadOnly();
                        dest.setExecutable(false);
                    } catch (Exception e) {
                        stream = null;
                        return "第 " + i + " 个文件上传失败： " + e.getMessage();
                    }
                } else {
                    return "第 " + i + " 个文件上传失败因为文件为空";
                }
            } else {
                return "上传失败！仅允许上传txt类型文件";
            }
        }
        return "上传成功";
    }

    @ResponseBody
    @RequestMapping("/download")
    public void download(String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取文件夹的路径
        String realPath = "G:/javap/File/";
        //通过流读取文件
        FileInputStream is = new FileInputStream(new File(realPath, fileName));
        //获得响应流
        ServletOutputStream os = response.getOutputStream();
        //设置响应头信息
        response.setHeader("content-disposition","attachment;fileName="+ URLEncoder.encode(fileName,"UTF-8"));
        response.setHeader("X-Frame-Options", "DENY");
        response.setHeader("Content-Security-Policy", "referrer https:;js-src https:;form-action https:;");
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-XSS-Protection", "1");
        //通过响应流将文件输入流读取到的文件写出
        IOUtils.copy(is,os);
        //关闭流
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(os);
    }
//    public static boolean checkWebUnSafesql(String word){
//        String str=word.replaceAll("'", "").replaceAll("\"", "").replaceAll("(?i)or", "").replaceAll("(?i)and", "").replaceAll("=", "")
//                .replaceAll("(?i)exec", "").replaceAll("(?i)execute", "").replaceAll("(?i)union", "").replaceAll("(?i)create", "").replaceAll("(?i)insert", "")
//                .replaceAll("(?i)select", "").replaceAll("(?i)delete", "").replaceAll("(?i)update", "").replaceAll("(?i)count", "").replaceAll("(?i)chr", "")
//                .replaceAll("(?i)mid", "").replaceAll("(?i)master", "").replaceAll("(?i)truncate", "").replaceAll("(?i)char", "").replaceAll("(?i)declare", "")
//                .replaceAll("(?i)xp_", "").replaceAll("(?i)sp_", "").replaceAll("(?i)Drop", "").replaceAll("(?i)table", "").replaceAll("(?i)0x", "")
//                .replaceAll("%", "").replaceAll("\\*", "");
//        if (word.length()!=str.length()) {
//            return true;
//        }
//        return false;
//    }
}
