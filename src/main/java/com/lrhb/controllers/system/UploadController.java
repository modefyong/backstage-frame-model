package com.lrhb.controllers.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.lrhb.mgrframework.beans.response.IResult;
import com.lrhb.mgrframework.beans.response.ResultBean;
import com.lrhb.utils.Constants;
import com.lrhb.utils.FileUtil;
import com.lrhb.utils.PropertyUtil;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UploadController {


    /**
     * 上传文件
     * @param file 上传文件
     * @param type 文件业务类型 "userFace"-用户头像
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public IResult upload(MultipartFile file, String type) throws Exception {
        //返回json至前端的均返回ResultBean或者PageResultBean
    	log.debug("upload file name is "+file.getOriginalFilename());
        return new ResultBean<String>(FileUtil.saveAndReturnUrl(file.getBytes(), Constants.AttachmentType.USER_FACE.value,file.getOriginalFilename(), PropertyUtil.getProperty("web_base_url"),PropertyUtil.getProperty("file_root_path")));
    }
}
