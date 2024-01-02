package camellia.util;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author 墨染盛夏
 * @version 2023/12/29 20:41
 */
public class FileUtil {
    public static void setExcelResponse(HttpServletResponse response, String fileName) {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            return response;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
