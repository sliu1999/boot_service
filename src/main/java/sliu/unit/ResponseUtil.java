package sliu.unit;


import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {
    public ResponseUtil() {
    }

    public static ResponseEntity<Map> success(Object data) {
        Map result = buildBase(0, "");
        result.put("data", data);
        return ResponseEntity.ok(result);
    }

    public static ResponseEntity<Map> success(int status,Object data) {
        Map result = buildBase(status, "");
        result.put("data", data);
        return ResponseEntity.ok(result);
    }

    public static ResponseEntity<Map> success(String msg,Object data) {
        Map result = buildBase(0, msg);
        result.put("data", data);
        return ResponseEntity.ok(result);
    }

    public static ResponseEntity<Map> success(int status,String msg,Object data) {
        Map result = buildBase(status, msg);
        result.put("data", data);
        return ResponseEntity.ok(result);
    }


    public static ResponseEntity<Map> error(String msg) {
        Map result = buildBase(-1, msg);
        result.put("data", "");
        return ResponseEntity.status(500).body(result);
    }

    public static ResponseEntity<Map> error(int status, String msg) {
        Map result = buildBase(status, msg);
        result.put("data", "");
        return ResponseEntity.status(status).body(result);
    }




    private static Map<String, Object> buildBase(int errorCode, String msg) {
        Map<String, Object> data = new HashMap(16);
        data.put("errorCode", errorCode);
        data.put("msg", msg);
        return data;
    }

    //返回值组成 成功errorCode=0,不成功errorCode=-1,自定义返回值成功>=0,错误<0
    public class Response {
        private Object data;
        private String msg;
        private int errorCode;

        public Response() {
        }

        public Object getData() {
            return this.data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public String getMsg() {
            return this.msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getErrorCode() {
            return this.errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }
    }
}
