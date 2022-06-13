package com.sdxx.oss.controller;

import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ResponseFile;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/dataoss/file")
public class FileController {

    /**
     * 图片保存路径，自动从yml配置文件中获取数据
     */
    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Value("${file.ip}")
    private String ip;

    private static List<String> suffixFilterList = Arrays.asList(".pdf");

    // 富文本文件上传接口，包括视频和图片
    @PostMapping("/upload")
    @ApiOperation(value = "上传文件")
    public R upload(MultipartFile file, HttpServletRequest request) throws IOException {
//        MultipartFile file;
//        if (fileParam != null) {
//            file = fileParam;
//        } else if (filePic != null) {
//            file = filePic;
//        } else if (fileMedia != null) {
//            file = fileMedia;
//        } else {
//            return R.error();
//        }

        String filename = file.getOriginalFilename();
        //图片名后缀：.jps  .png
//        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //生成uuid
        String uuid = UUID.randomUUID().toString();
        Date todayDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(todayDate);
        //拼接文件新名称
        String newFileName = uuid + "." + today + "." + filename;
        //创建保存上传文件的文件夹
        File folder = new File(uploadFolder + newFileName);
        //判断是否有文件
        if (!folder.getParentFile().exists()) {
            //没有则创建
            folder.getParentFile().mkdir();
        }
        //文件写入到该文件夹下
        file.transferTo(folder);
        //获取本机Ip（获取的是服务器的ip）
//        InetAddress inetAddress = InetAddress.getLocalHost();
//        String ip = inetAddress.getHostAddress();
//        System.out.println("ip========="+ip);
        //返回保存的url，根据url可以进行文件查看或者下载
        request.setCharacterEncoding("utf-8");
        String fileDownloaderUrl = request.getScheme() + "://" + ip + ":" + request.getServerPort()
                + "/api/file/" + newFileName;
        //ps；把拼接的路径url可以保存到数据库
        return ResponseFile.newInstance().setUrl(fileDownloaderUrl).setAlt(filename).setHref("").success();
    }

    @PostMapping("/uploadDocument")
    @ApiOperation(value = "上传文档")
    public R uploadDocument(MultipartFile file, HttpServletRequest request) throws IOException {
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        if (StringUtils.isEmpty(suffix) || !suffixFilterList.contains(suffix.trim().toLowerCase())) {
            return R.error().message("文件类型不正确");
        }

        //生成uuid
        String uuid = UUID.randomUUID().toString();
        Date todayDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(todayDate);
        //拼接文件新名称
        String newFileName = uuid + "." + today + "." + filename;
        //创建保存上传文件的文件夹
        File folder = new File(uploadFolder + newFileName);
        //判断是否有文件
        if (!folder.getParentFile().exists()) {
            //没有则创建
            folder.getParentFile().mkdir();
        }
        //文件写入到该文件夹下
        file.transferTo(folder);

        //返回保存的url，根据url可以进行文件查看或者下载
        request.setCharacterEncoding("utf-8");
        String fileDownloaderUrl = request.getScheme() + "://" + ip + ":" + request.getServerPort() + "/api/file/" + newFileName;

        //ps；把拼接的路径url可以保存到数据库
        return ResponseFile.newInstance().setUrl(fileDownloaderUrl).setAlt(filename).setHref("").success();
    }

    // 富文本文件上传接口，包括视频和图片
    @PostMapping("/uploadPicture")
    @ApiOperation(value = "上传文件")
    public R uploadPicture(@ApiParam(value = "fileParam为null，则使用filePic") @RequestParam(required = false) MultipartFile file,
                    HttpServletRequest request) throws IOException {
//        MultipartFile file;
//        if (fileParam != null) {
//            file = fileParam;
//        } else if (filePic != null) {
//            file = filePic;
//        } else if (fileMedia != null) {
//            file = fileMedia;
//        } else {
//            return R.error();
//        }

        String filename = file.getOriginalFilename();
        //图片名后缀：.jps  .png
//        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //生成uuid
        String uuid = UUID.randomUUID().toString();
        Date todayDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(todayDate);
        //拼接文件新名称
        String newFileName = uuid + "." + today + "." + filename;
        //创建保存上传文件的文件夹
        File folder = new File(uploadFolder + newFileName);
        //判断是否有文件
        if (!folder.getParentFile().exists()) {
            //没有则创建
            folder.getParentFile().mkdir();
        }
        //文件写入到该文件夹下
        file.transferTo(folder);
        //获取本机Ip（获取的是服务器的ip）
//        InetAddress inetAddress = InetAddress.getLocalHost();
//        String ip = inetAddress.getHostAddress();
//        System.out.println("ip========="+ip);
        //返回保存的url，根据url可以进行文件查看或者下载
        request.setCharacterEncoding("utf-8");
        String fileDownloaderUrl = request.getScheme() + "://" + ip + ":" + request.getServerPort()
                + "/api/file/" + newFileName;
        //ps；把拼接的路径url可以保存到数据库
        return ResponseFile.newInstance().setUrl(fileDownloaderUrl).setAlt(filename).setHref("").success();
    }

    // 富文本文件上传接口，包括视频和图片
    @PostMapping("/uploadVideo")
    @ApiOperation(value = "上传文件")
    public R uploadVideo(@ApiParam(value = "filePic为null，使用fileMedia，都为null，返回错误信息") @RequestParam(required = false) MultipartFile file,
                    HttpServletRequest request) throws IOException {
//        MultipartFile file;
//        if (fileParam != null) {
//            file = fileParam;
//        } else if (filePic != null) {
//            file = filePic;
//        } else if (fileMedia != null) {
//            file = fileMedia;
//        } else {
//            return R.error();
//        }

        String filename = file.getOriginalFilename();
        //图片名后缀：.jps  .png
//        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //生成uuid
        String uuid = UUID.randomUUID().toString();
        Date todayDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(todayDate);
        //拼接文件新名称
        String newFileName = uuid + "." + today + "." + filename;
        //创建保存上传文件的文件夹
        File folder = new File(uploadFolder + newFileName);
        //判断是否有文件
        if (!folder.getParentFile().exists()) {
            //没有则创建
            folder.getParentFile().mkdir();
        }
        //文件写入到该文件夹下
        file.transferTo(folder);
        //获取本机Ip（获取的是服务器的ip）
//        InetAddress inetAddress = InetAddress.getLocalHost();
//        String ip = inetAddress.getHostAddress();
//        System.out.println("ip========="+ip);
        //返回保存的url，根据url可以进行文件查看或者下载
        request.setCharacterEncoding("utf-8");
        String fileDownloaderUrl = request.getScheme() + "://" + ip + ":" + request.getServerPort()
                + "/api/file/" + newFileName;
        //ps；把拼接的路径url可以保存到数据库
        return ResponseFile.newInstance().setUrl(fileDownloaderUrl).setAlt(filename).setHref("").success();
    }
}
