package com.demo.updown.controller;


import com.demo.updown.config.UploadService;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Controller
public class UploadControlller {

    protected static final Logger logger = LoggerFactory.getLogger(UploadControlller.class);

    @Resource
    private UploadService uploadService;
    @Autowired
    private FastFileStorageClient storageClient;

    @RequestMapping("/toindex")
    public String toindex() {
        return "/index";
    }

    @PostMapping("/uploads")
    @ResponseBody
    public String uploads(MultipartFile[] uploadfiles, HttpServletRequest request) {
        logger.info("打印信息:tt{}" + uploadfiles);
        //上传文件数组做判断为空操作
        if (uploadfiles == null || uploadfiles.length < 1) {
            return "文件不能为空";
        }
        //定义文件的存储路径
        String realPath = request.getSession().getServletContext().getRealPath("/uploadFile/");
        File dir = new File(realPath);
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }
        try {
            String filePaths = "";
            //3.遍历文件数组，一个个上传
            for (int i = 0; i < uploadfiles.length; i++) {

                MultipartFile uploadFile = uploadfiles[i];
                String filename = uploadFile.getOriginalFilename();
                //服务端保存的文件对象
                File fileServer = new File(dir, filename);
                System.out.println("file文件的真实路径:" + fileServer.getAbsolutePath());
                //实现上传
                uploadFile.transferTo(fileServer);
                String filePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/uploadFile/" + filename;
                filePaths = filePaths + "\n" + filePath;
                //4.返回可控访问的网络路径
                return filePaths;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "上传失败";
    }


    //多文件上传
    @RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
    @ResponseBody
    public String handleFileUpload(HttpServletRequest request) {
        //获取页面传来的文件名称
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("uploadfiles");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); i++) {
            file = files.get(i);
            //设置文件存贮路径
            String filePath = "D://";
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(new File(filePath + file.getOriginalFilename())));
                    stream.write(bytes);//写入
                    stream.close();
                } catch (Exception w) {
                    stream = null;
                    return "the" + i + "file upload failuer";

                }
            } else {
                return "the" + i + "file is empty";
            }
        }
        return "文件上传成功";
    }

    //测试fsatDFS
    @RequestMapping(value = "/uploadFastDFS", method = RequestMethod.POST)
    @ResponseBody
    public String testFastDFS(MultipartFile uploadfiles) {
        System.out.println(uploadfiles.getOriginalFilename());
        Map<String, Object> map = new HashMap<>();
        String filePath = uploadService.uploadImage(uploadfiles);

        return filePath;
    }

    @PostMapping("myUpload")
    @ResponseBody
    public String upload(MultipartFile myFile) throws IOException {
        // myFile.getOriginalFilename():取到文件的名字
        // FilenameUtils.getExtension(""):取到一个文件的后缀名
        String extension = FilenameUtils.getExtension(myFile.getOriginalFilename());

        // group1:指storage服务器的组名
        // myFile.getInputStream():指这个文件中的输入流
        // myFile.getSize():文件的大小
        // 这一行是通过storageClient将文件传到storage容器
        StorePath uploadFile = storageClient.uploadFile("group1", myFile.getInputStream(), myFile.getSize(), extension);

        // 上传数据库
        String sql = "insert into file(filename,groupname,filepath) values(?,?,?)";
        //jdbcTemplate.update(sql, myFile.getOriginalFilename(), uploadFile.getGroup(), uploadFile.getPath());

        // 返回它在storage容器的的路径
        return uploadFile.getFullPath();
    }
    //文件下载
  /*  @GetMapping("/fdownload/{id}")
    public void download(@PathVariable String id, HttpServletResponse response) throws IOException {

        List query = jdbcTemplate.query("select * from file where fileid=" + id, new ColumnMapRowMapper());
        Map map = (Map) query.get(0);
        String filename = URLEncoder.encode(map.get("filename").toString(), "utf-8"); // 解决中文文件名下载后乱码的问题
        // 告诉浏览器 下载的文件名
        response.setHeader("Content-Disposition", "attachment; filename=" + filename + "");
        String groupName = map.get("groupName").toString();
        String filepath = map.get("filepath").toString();
        // 将文件的内容输出到浏览器 fastdfs
        byte[] downloadFile = storageClient.downloadFile(groupName, filepath);
        response.getOutputStream().write(downloadFile);
    }*/


    // 文件下载
    @RequestMapping(value = "/findFile", method = RequestMethod.GET)
    public void findFile(HttpServletRequest request, HttpServletResponse response) {

        ServletOutputStream out = null;
        FileInputStream ips = null;

        try {
            java.io.File file = ResourceUtils.getFile("classpath:templates/text.txt");
            if (!file.exists()) {
                System.out.println("文件不存在");
                return;
            }
            String name = file.getName();
            String newName = UUID.randomUUID().toString() + name;
            System.out.println(newName);
            ips = new FileInputStream(file);
            response.setContentType("multipart/form-data");
            //为文件重新设置名字，采用数据库内存储的文件名称
            response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(newName.getBytes("UTF-8"), "ISO8859-1") + "\"");
            out = response.getOutputStream();
            //读取文件流
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = ips.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        } catch (Exception o) {
            o.printStackTrace();
            System.out.println("IO出现异常");
        } finally {
            try {
                out.close();
                ips.close();
            } catch (IOException o) {
                System.out.println("关闭异常");
                o.fillInStackTrace();
            }
        }
    }

}
