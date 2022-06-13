package com.sdxx.oss.controller;

import com.atguigu.commonutils.R;
import com.atguigu.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/dataoss/fileoss")
@Api(description = "上传文件")
//@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    //上传头像的方法
    @PostMapping
    @ApiOperation(value = "上传文件")
    public R uploadOssFile(MultipartFile file,HttpServletRequest request) throws UnsupportedEncodingException {
        //获取上传文件  MultipartFile
        //返回上传到oss的路径
        String url = ossService.uploadFileAvatar(file,request);
        return R.ok().data("url",url);
    }

//    //上传头像的方法
//    // 不是txt 文件上传方法 返回url 点击可直接下载
//    @PostMapping
//    public R uploadOssFile(MultipartFile file) {
//        //获取上传文件  MultipartFile
//        //返回上传到oss的路径
//        String url = ossService.uploadFileAvatar(file);
//        return R.ok().data("url",url);
//    }
//
//    //上传作业 课件
//    @PostMapping("/file")
//    public R uploadFile(MultipartFile file) {
//        String fileName = ossService.uploadFile(file);
//        System.out.println(fileName);
//        return R.ok().data("fileName",fileName);
//    }
//
//    //下载作业 课件 传 带参数 表中字段  homework   courseWare
//    @PostMapping("/downloadFile/{filename}")
//    public R downloadFile(@PathVariable("filename") String filename) {
//        if (filename!=null || filename!=""){
//            ossService.downloadFile(filename);
//            return R.ok();
//        }else {
//            return R.error().data("暂无文件",2001);
//        }
//    }

    //删除作业 课件 传 带参数 表中字段  homework   courseWare
//    @DeleteMapping("/delFile/{filename}")
//    public R delFile(@PathVariable("filename") String filename) {
//        //获取上传文件  MultipartFile
//        //返回上传到oss的路径
//        ossService.delFile(filename);
//        return R.ok();
//    }


}
