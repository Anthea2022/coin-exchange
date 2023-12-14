package camellia.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static camellia.constant.IdentifyAuthConstant.APP_CODE;
import static camellia.constant.IdentifyAuthConstant.URL;

/**
 * @author 墨染盛夏
 * @version 2023/12/12 20:55
 */
@Configuration
public class IdentifyAuthConfiguration {
    private static RestTemplate restTemplate = new RestTemplate();

    public static Boolean authCheck(String realName, String idCard) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization","APPCODE "+ APP_CODE);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                String.format(URL, idCard, realName),
                HttpMethod.GET,
                new HttpEntity<>(null, httpHeaders),
                String.class
        );
        if(responseEntity.getStatusCode()== HttpStatus.OK){
            String body = responseEntity.getBody();
            JSONObject jsonObject = JSON.parseObject(body);
            String status = jsonObject.getString("status");
            if("01".equals(status)){ // 验证成功
                return true ;
            }
        }
        return  false ;
    }
}
