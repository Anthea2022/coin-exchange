package camellia.controller;

import camellia.common.BaseResponse;
import cn.hutool.core.date.DateUtil;
import com.aliyun.oss.OSS;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 墨染盛夏
 * @version 2023/11/27 21:00
 */
@Api(tags = "oss对象存储存储文件")
@RestController
@RequestMapping("/admin/oss")
public class OssController {
    @Autowired
    private OSS ossClient;

    @Value("kkkoke-cloud")
    private String bucketName;


    @Value("https://")
    private String prefix;

    @Value("${spring.cloud.alicloud.oss.endpoint}")
    private String endpoint;

    @ApiOperation("上传文件")
    @PostMapping("/file/upload")
    public BaseResponse<Object> fileUpload(@RequestParam("file")MultipartFile file) throws IOException {
        String fileName = DateUtil.today().replaceAll("-", "/") + "/" + file.getOriginalFilename();
        ossClient.putObject(bucketName, fileName, file.getInputStream());
        return BaseResponse.success(prefix + bucketName + "." + endpoint + "/" + fileName, "文件上传成功");
    }
}
