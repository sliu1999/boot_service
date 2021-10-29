package sliu.unit;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.primitives.Ints;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuavaUtil {
	/*
	 * 将字符串用指定的分隔符隔开
	 */
	public static String append(String on, String... args) {
		StringBuilder sb = new StringBuilder();
		Joiner.on(on).skipNulls().appendTo(sb, args);
		return sb.toString();
	}

	/*
	 * 将用特定分隔符隔开的字符串转成list集合
	 */
	public static List<String> split2list(String on, String string) {
		if(null ==string||string.isEmpty()) {
			return new ArrayList<String>();
		}
		List<String> result = Splitter.on(on).splitToList(string);
		return result;
	}

	/*
	 * 将list集合转成用指定分隔符分隔开的字符串
	 */
	public static String list2string(List<String> list, String on) {
		String result = Joiner.on(on).join(list);
		return result;
	}
	
	public static String list3string(List<Object> list, String on) {
		String result = Joiner.on(on).join(list);
		return result;
	}

	/**
	 *  将键值形式中间有分隔符隔开的字符串转换成map集合 例如：password=1111&username=wangjianguo
	 * @param on  间隔符
	 * @param space  键值连接符
	 * @param string 
	 * @return
	 */
	public static Map<String, String> split2map(String on, String space, String string) {
		Map<String, String> result = Splitter.on(on).withKeyValueSeparator(space).split(string);
		return result;
	}

	/**
	 * 将map集合转换成指定形式的字符串
	 * @param map
	 * @param on  间隔符
	 * @param space  键值连接符
	 * @return
	 */
	public static String map2string(Map<String, String> map, String on, String space) {
		StringBuilder sb = new StringBuilder();
		Joiner.on(on).withKeyValueSeparator(space).appendTo(sb, map);
		return sb.toString();
	}

	/**
	 * 使用正则将不同分隔符隔开的字符串转换成list集合 
	 * @param pattern 分隔符正则表达式
	 * @param string  例如：zhao,qian.sun.li,,zhou,wu.zheng,wang patern参数应为  .|,
	 * @param withNull 是否去除空字符串
	 * @return
	 */
	public static List<String> str2list(String pattern, String string, Boolean withNull) {
		List<String> result = new ArrayList<String>();
		// 是否包含空值
		if (withNull) {
			result = Splitter.onPattern("[" + pattern + "]").splitToList(string);
		} else {
			result = Splitter.onPattern("[" + pattern + "]").omitEmptyStrings().splitToList(string);
		}
		return result;
	}

	/**
	 * 比较大小
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static int compare(int a, int b) {
		return Ints.compare(a, b);
	}
	/**
	 * 表单控件数据类型库
	 * @return
	 */
	public static Map<String, Object> getCtrlMap() {
		Map<String, Object> ctrlMap = new HashMap<String, Object>();
		ctrlMap.put("textarea", "text");
		ctrlMap.put("logs", "text");
		ctrlMap.put("comments", "text");
		ctrlMap.put("number", "varchar(255)");
		ctrlMap.put("formula", "float(11,2)");
		ctrlMap.put("readnum", "float(11,2)");
		ctrlMap.put("sum", "float(11,2)");
		ctrlMap.put("datediff", "varchar(255)");
		ctrlMap.put("datetime", "datetime");
		ctrlMap.put("datecal", "varchar(255)");
		ctrlMap.put("currency", "decimal(11,2)");
		ctrlMap.put("select", "text");
		ctrlMap.put("dictionary", "text");
		ctrlMap.put("sequence", "varchar(255)");
		ctrlMap.put("current", "datetime");
		ctrlMap.put("time", "time");
		ctrlMap.put("checkbox", "bit");
		ctrlMap.put("nodes", "text");
		ctrlMap.put("text", "text");
		return ctrlMap;
	}

	/**
	 * 对前台传送的表单字段属性ctrl进行转换
	 * 
	 * @param fields
	 * @return
	 */
	public static List<Map<String, Object>> turnCtrl(List<Map<String, Object>> fields) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		String dataType = null;
		if (fields != null) {
			for (Map<String, Object> map : fields) {
				// 默认选择varchar(255)
				String ctrl = map.get("ctrl") == null ? null : map.get("ctrl").toString();
				dataType = getCtrlMap().get(ctrl) == null ? "varchar(255)" : (String) getCtrlMap().get(ctrl);
				map.put("ctrl", dataType);
				result.add(map);
			}
		}
		return result;
	}

	/**
	 * 将map集合转换成实体类
	 * 
	 * @param entity
	 * @param map
	 */
	public static void map2entity(Object entity, Map<String, ? extends Object> map) {
		try {
			BeanUtils.populate(entity, map);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 将实体类转成map对象，存在一个问题当实体类中有日期类型的属性时会转成时间戳
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> entity2map(Object object) {
		String json = JSON.toJSONString(object);
		Map<String,Object> map = JSON.parseObject(json, Map.class);
		System.out.println("map:"+map);
		return map;
	}
	@SuppressWarnings("unchecked")
	public static List<Map<String,Object>> handle(List<Map<String,Object>> data,List<Map<String,Object>>list){
		for(Map<String,Object> map:data) {
			Map<String,Object> resultMap = new HashMap<String,Object>();
			if(null!=map.get("ip")&&!map.get("ip").toString().isEmpty()){
				resultMap.put("jkyId",map.get("id"));
				resultMap.put("ip", map.get("ip"));
				resultMap.put("name", map.get("name"));
				list.add(resultMap);
			}
			if(map.containsKey("subtree")) {
				handle((List<Map<String,Object>>)map.get("subtree"),list);
			}
		}
		return list;
	}
}
