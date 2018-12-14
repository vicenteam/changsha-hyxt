package com.stylefeng.guns.modular.api.base;


import com.stylefeng.guns.modular.api.apiparam.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BaseController {
    private final Logger log = LoggerFactory.getLogger(BaseController.class);
    public Map<String, Object> bodyParameters = new HashMap<>();


    /**
     * 使用@ExceptionHandler注解，继承此类的Controller发生异常时会自动执行该方法
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler
    public ResponseData exception(HttpServletRequest request, Exception e) {
        //对异常进行判断做相应的处理
        ResponseData responseData = new ResponseData();
        responseData.setResultCode("202");
        responseData.setResultMessage(e.getMessage());
        responseData.setDataCollection(new Object());
        e.printStackTrace();
        return responseData;
    }
}
