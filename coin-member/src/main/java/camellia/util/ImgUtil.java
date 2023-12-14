package camellia.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author 墨染盛夏
 * @version 2023/12/12 10:08
 */
public class ImgUtil {
    @Value("${file.path}")
    private static String prefix;

    public static String storeImg(MultipartFile multipartFile) {
        String fileName = DateUtil.today().replaceAll("-", "/") + "/" + getName();
        File file = new File(prefix + fileName);
        try {
            multipartFile.transferTo(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return prefix + fileName;
    }

    public static String getName() {
        return UUID.randomUUID().toString();
    }
}
