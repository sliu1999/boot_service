package sliu.unit;


import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class RestTemplateUtil {

    public static String method(String url,String method,Map<String ,Object> hashMap){
        if(method.equals("get")){
            return methodGet(url,hashMap);
        }else if(method.equals("delete")){
            return methodDelete(url,hashMap);
        }else if(method.equals("post")){
            return methodPost(url,hashMap);
        }else{
            return methodPut(url,hashMap);
        }

    }

    //get
    /**
     * Map param = new HashMap(3);
     *             param.put("city","滁州市");
     *             param.put("key",appKey);
     *             param.put("address",address);
     *             String result = RestTemplateUtil.method(url+"?key={key}&city={city}&address={address}","get",param);
     * @param url
     * @param hashMap
     * @return
     */
    private static String methodGet(String url,Map<String ,Object> hashMap){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "application/json");
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<Object>(hashMap,requestHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> test = restTemplate.exchange(url, HttpMethod.GET,httpEntity,String.class,hashMap);

        return test.getBody();
    }

    //delete
    private static String methodDelete(String url,Map<String ,Object> hashMap){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "application/json");
        //设置请求头类型，
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<Object>(hashMap,requestHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> test = restTemplate.exchange(url, HttpMethod.DELETE,httpEntity,String.class,hashMap);
        return test.getBody();
    }

    //post
    /**
     * Map param = new HashMap(3);
     *         param.put("appId",appId);
     *         param.put("appKey",appKey);
     *         param.put("deviceList",deviceList);
     *         String url = baseUrl+"deviceLocationQuery";
     *         String result = RestTemplateUtil.method(url,"post",param);
     * @param url
     * @param reqJsonStr
     * @return
     */
    private static String methodPost(String url,Map<String ,Object> reqJsonStr){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "application/json");
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<Object>(reqJsonStr,requestHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> test = restTemplate.exchange(url, HttpMethod.POST,httpEntity,String.class);
        return test.getBody();
    }

    //put
    private static String methodPut(String url,Map<String ,Object> reqJsonStr){
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "application/json");
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<Object>(reqJsonStr,requestHeaders);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> test = restTemplate.exchange(url, HttpMethod.PUT,httpEntity,String.class);
        return test.getBody();
    }

}
