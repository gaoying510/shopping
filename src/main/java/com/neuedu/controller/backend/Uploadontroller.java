package com.neuedu.controller.backend;

import com.neuedu.common.ServerResponse;
import com.neuedu.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/manage/product")
public class Uploadontroller {
    @Autowired
    IProductService productService;

    @RequestMapping(value = "/upload",method = RequestMethod.GET)
    public String upload(){
        return "upload";//逻辑视图
    }


    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse upload1(@RequestParam(value = "upload_file",required = false)MultipartFile file, HttpServletRequest request)
    {
        // todo 修改文件的上传逻辑
       String path=request.getSession().getServletContext().getRealPath("upload");
        return productService.upload(file,path);//逻辑视图
    }

}

