package sliu.config.jwt.vo;

/**
 * 返回值
 * @param <T>
 */
public class ResponseResult<T> {

             private int errorCode = 0;

             private String msg;

             private T data;

             public ResponseResult(){}

             public ResponseResult(int errorCode, String msg) {
                 this.errorCode = errorCode;
                 this.msg = msg;
             }

             public ResponseResult(int errorCode, String msg, T data) {
                 this.errorCode = errorCode;
                 this.msg = msg;
                 this.data = data;
             }

             public static ResponseResult success() {
                 return new ResponseResult(ResponseCodeEnum.SUCCESS.getErrorCode(), ResponseCodeEnum.SUCCESS.getMessage());
             }

             public static <T> ResponseResult<T> success(T data) {
                 return new ResponseResult(ResponseCodeEnum.SUCCESS.getErrorCode(), ResponseCodeEnum.SUCCESS.getMessage(), data);
             }

             public static ResponseResult error(int errorCode, String msg) {
                 return new ResponseResult(errorCode, msg); }

             public static <T> ResponseResult<T> error(int errorCode, String msg, T data) {
                          return new ResponseResult(errorCode, msg, data);
             }

             public boolean isSuccess() {
                 return errorCode == 0;
             }

             public int getErrorCode() {
                 return errorCode;
             }

             public void setErrorCode(int code) {
                 this.errorCode = errorCode;
             }

             public String getMsg() {
                 return msg;
             }

             public void setMsg(String msg) {
                 this.msg = msg;
             }

             public T getData() {
                 return data;
             }

             public void setData(T data) {
                 this.data = data;
             }
 }