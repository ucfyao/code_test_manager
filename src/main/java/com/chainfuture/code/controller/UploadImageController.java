package com.chainfuture.code.controller;

import com.chainfuture.code.common.FileUploadUtil;
import com.chainfuture.code.common.WwSystem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

@Controller
@RequestMapping("/uploadimage")
public class UploadImageController {

    // 上传图片
    @RequestMapping(value = "/upload")
    public void upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        String fileElementId = request.getParameter("fileElementId");
        String savePath = request.getParameter("savePath");
        int maxWidth = WwSystem.ToInt(request.getParameter("maxWidth"));

        if (savePath == null || savePath.isEmpty()) {
            savePath = "/public/file_list/images/";// 默认保存路径
        } else {
            savePath = "/" + savePath;// 保存路径要加"/"
        }

        FileUploadUtil.UploadResult res = FileUploadUtil.updateOneImage(request, savePath, fileElementId);
        if (!res.success) {
            response.getWriter()
                    .write("{status:'success',success:false,fileName:'" + res.srcFileName + "',message:'上传图片失败'}");
            return;
        }

        response.getWriter().write("{status:'success',success:true,fileName:'" + res.newFileName + "',message:''}");
    }

    @RequestMapping(value = "/getimage")
    public void getImage(String savePath, String fileName, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        if (savePath == null || savePath.isEmpty()) {
            savePath = "/private/images/";// 默认保存路径
        } else {
            savePath = "/" + savePath;// 保存路径要加"/"
        }

        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        if (fileName == null || fileName.isEmpty()) {
            return;
        }

        String path = request.getSession().getServletContext().getRealPath(savePath);
        path = path.replaceAll("\\\\", "/");// 主要这里是正则表达式\\\\代表一个\

        path += "/" + fileName;
        path = path.replaceAll("//", "/");

        if (path.isEmpty()) {
            return;
        }
        File f = new File(path);
        if (!f.exists()) {
            return;
        }
        // 读取文件
        InputStream in = new FileInputStream(path);
        OutputStream out = response.getOutputStream();
        // 写文件
        int b;
        while ((b = in.read()) != -1) {
            out.write(b);
        }
        in.close();
        out.close();
    }
}
