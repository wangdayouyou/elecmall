package com.sdxx.oss.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    /**
     * 图片保存路径，自动从yml配置文件中获取数据
     */
    @Value("${file.uploadFolder}")
    private String uploadFolder;
    @Value("${file.ip}")
    private String ip;


    /**
     * 文件（图片）上传
     *
     * @param file    图片文件
     * @param request
     * @return
     */
    @SneakyThrows
    @Override
    public String uploadFileAvatar(MultipartFile file, HttpServletRequest request) {
        //文件原名称
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
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedEncodingException();
        }
        String fileDownloaderUrl = request.getScheme() + "://" + ip + ":" + request.getServerPort()
                + "/api/file/" + newFileName;
        //ps；把拼接的路径url可以保存到数据库
        //返回保存的路径
        return fileDownloaderUrl;
    }


    //上传头像到oss
//    @Override
//    public String uploadFileAvatar(MultipartFile file) {
////        // 工具类获取值
//        String endpoint = ConstantPropertiesUtils.END_POIND;
//        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
//        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
//        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
//
//        try {
//            // 创建OSS实例。
//                       OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//
//            //获取上传文件输入流
//            InputStream inputStream = file.getInputStream();
//            //获取文件名称
//            String fileName = file.getOriginalFilename();
//
//            //1 在文件名称里面添加随机唯一的值
//            String uuid = UUID.randomUUID().toString().replaceAll("-","");
//            // yuy76t5rew01.jpg
//            fileName = uuid+fileName;
//
//            //2 把文件按照日期进行分类
//            //获取当前日期
//            //   2019/11/12
//            String datePath = new DateTime().toString("yyyy/MM/dd");
//            //拼接
//            //  2019/11/12/ewtqr313401.jpg
//            fileName = datePath+"/"+fileName;
//
//            //调用oss方法实现上传
//            //第一个参数  Bucket名称
//            //第二个参数  上传到oss文件路径和文件名称   aa/bb/1.jpg
//            //第三个参数  上传文件输入流
//            ossClient.putObject(bucketName,fileName , inputStream);
//
//            // 关闭OSSClient。
//            ossClient.shutdown();
//
//            //把上传之后文件路径返回
//            //需要把上传到阿里云oss路径手动拼接出来
//            //  https://edu-guli-1010.oss-cn-beijing.aliyuncs.com/01.jpg
//            String url = "https://"+bucketName+"."+endpoint+"/"+fileName;
//            return url;
//        }catch(Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }


    // 上传 课件  作业
    @Override
    public String uploadFile(MultipartFile file) {
        // 工具类获取值
        String endpoint = ConstantPropertiesUtils.END_POIND;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            String filename = file.getOriginalFilename();
            //1 在文件名称里面添加随机唯一的值
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            // yuy76t5rew01.jpg
            String filename1 = uuid + "-" + filename;
            String datePath = "homeworkOrCourseware";
            filename = datePath + "/" + filename1;
            // 创建PutObjectRequest对象。
            // 填写Bucket名称、Object完整路径和本地文件的完整路径。Object完整路径中不能包含Bucket名称。
            // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filename, file.getInputStream());
            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);
            // 上传文件。
            ossClient.putObject(putObjectRequest);

            // 关闭OSSClient。
            ossClient.shutdown();
//            String url = "https://"+bucketName+"."+endpoint+"/"+filename;
            return filename1;
        } catch (OSSException e) {
            e.printStackTrace();
            return null;
        } catch (ClientException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    // 下载课件 作业
    @Override
    public void downloadFile(String filename) {
        // 工具类获取值
        String endpoint = ConstantPropertiesUtils.END_POIND;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        //文件名称
        String fileName = filename;
        // 创建OSSClient实例。
        OSS ossClient = null;

        try {
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            String datePath = "homeworkOrCourseware";
            filename = datePath + "/" + filename;
// ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
            OSSObject ossObject = ossClient.getObject(bucketName, filename);

            // 读取文件内容。
            System.out.println("Object content:");
            BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
            ArrayList<Object> arrayList = new ArrayList<>();
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                arrayList.add(line);
            }
            File file = new File("D:\\homeworkOrCourseware");
            if (!file.exists()) {//如果文件夹不存在
                file.mkdir();//创建文件夹
            }
            //如果homeworkList文件夹下没有Qiju_Li.txt就会创建该文件
            BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\homeworkOrCourseware\\" + fileName));
            System.out.println("\n" + arrayList);
            for (int k = 0; k < arrayList.size(); k++) {
                bw.write(arrayList.get(k).toString() + " ");
                bw.newLine();
            }
            bw.close();//一定要关闭文件

//// 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
            reader.close();
// 关闭OSSClient。
            ossClient.shutdown();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    // 删除文件
//    @Override
//    public void delFile(String filename) {
//        // 工具类获取值
//        String endpoint = ConstantPropertiesUtils.END_POIND;
//        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
//        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
//        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
//        // 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//        String datePath = "homeworkOrCourseware";
//        filename = datePath+"/"+filename;
//        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
//        ossClient.deleteObject(bucketName, filename);
//       // 关闭OSSClient。
//        ossClient.shutdown();
//    }


}
