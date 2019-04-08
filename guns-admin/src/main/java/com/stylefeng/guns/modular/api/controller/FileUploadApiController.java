package com.stylefeng.guns.modular.api.controller;

import com.stylefeng.guns.modular.api.apiparam.ResponseData;
import com.stylefeng.guns.modular.system.controller.UserMgrController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Scope("prototype")
@RequestMapping("/api/fileuploadapi")
@Api(description = "文件上传")
public class FileUploadApiController {
    @Autowired
    private UserMgrController userMgrController;
    @RequestMapping(value = "/photoUpload", method = RequestMethod.POST)
    @ApiOperation("拍照图片上传")
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "userId", value = "操作人id", paramType = "query"),
            @ApiImplicitParam(required = true, name = "deptId", value = "操作人部门id", paramType = "query"),
    })
    public ResponseData photoUpload(@RequestPart("file") MultipartFile picture, HttpServletRequest request) throws Exception {
        ResponseData responseData=new ResponseData();
        try {
           String pictureName= userMgrController.upload(picture,request);
            responseData.setDataCollection(pictureName);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("系统错误 文件上传失败");
        }
        return responseData;
    }
}
