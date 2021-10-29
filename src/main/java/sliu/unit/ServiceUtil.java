package sliu.unit;

import java.util.Map;

public class ServiceUtil {
    public static String KEY_PAGE_NUM = "pageNum";
    public static String KEY_PAGE_SIZE = "pageSize";
    public static String KEY_OFFSET = "offset";
    public static String KEY_LIMIT = "limit";

    public ServiceUtil() {
    }

    public static void checkPageParams(Map params) {
        if (params != null && !params.isEmpty()) {
            Boolean paramsError = (!params.containsKey(KEY_PAGE_NUM) || !params.containsKey(KEY_PAGE_SIZE)) && (!params.containsKey(KEY_OFFSET) || !params.containsKey(KEY_LIMIT));
            if (paramsError) {
                throw new IllegalArgumentException("分页参数不正确");
            } else {
                if (!params.containsKey(KEY_PAGE_NUM) || !params.containsKey(KEY_PAGE_SIZE)) {
                    int offset = Integer.parseInt(params.get(KEY_OFFSET).toString());
                    int limit = Integer.parseInt(params.get(KEY_LIMIT).toString());
                    int pageNum = offset / limit + 1;
                    params.put(KEY_PAGE_NUM, pageNum);
                    params.put(KEY_PAGE_SIZE, limit);
                }

            }
        } else {
            throw new IllegalArgumentException("分页参数不正确");
        }
    }
}
