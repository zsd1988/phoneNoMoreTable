package com.qingpu.phone.common.utils;

import com.qingpu.phone.common.entity.PublicEnum;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.user.entity.Permissions;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.File;
import java.net.Socket;
import java.util.*;

/**
 * @Desc 杂项工具函数
 * @author Gangyahaidao
 */
public class CommonUtils {
	/**
	 * @Desc 从指定的Map中获取value对应的key值，由value获取key
	 * */
	public static Object getMapKey(Map<String, Socket> map, Object value) {
		Object key = null;

		for (Object getKey : map.keySet()) {
			if (map.get(getKey).equals(value)) {
				key = getKey;
			}
		}
		return key;
	}

	/**
	 * @Desc 获取map占用所有Key，存储在数组中返回
	 * */
	public static List getAllKey(HashMap hm, String value) {
		List list = new ArrayList();
		for (Object getKey : hm.keySet()) {
			if (hm.get(getKey).equals(value)) {
				list.add(getKey);
			}
		}
		return list;
	}

	/**
	 * @Desc 获取不重复的随机数
	 * @param t
	 *            选取数据的原数组
	 *            从原数组中选取的个数
	 * @return 返回选取随机数的数组
	 * */
	public static int[] getNoRepeatNumByChange(int[] t, int noRepeatNums) {
		int[] newArr = new int[noRepeatNums];
		for (int i = 0; i < noRepeatNums; i++) {
			Random random = new Random();
			int s = random.nextInt(t.length - 1) % (t.length - 1 - i + 1) + i;
			int temp = t[i];
			t[i] = t[s];
			t[s] = temp;
			newArr[i] = t[i];
		}
		return newArr;
	}

	/**
	 * 方法二：set排异法
	 * */
	public static Set<Integer> getNoRepeatNumBySet(int[] arr, int noRepeatNums) {
		Random random = new Random();
		Set<Integer> set = new HashSet<Integer>();
		while (true) {
			int s = random.nextInt(arr.length);
			set.add(Integer.valueOf(arr[s]));
			if (set.size() == noRepeatNums) {
				break;
			}
		}
		return set;
	}

	/**
	 * 产生一个指定范围的随机数 min<= ret <=max
	 * */
	public static int getRandomNum(int min, int max) {
		max += 1;
		Random random = new Random();

		return random.nextInt(max) % (max - min + 1) + min;
	}


	/**
	 * 根据参数获取Range
	 * @param param
	 * @return
	 */
	public static Range getRange(String param) {
		JSONObject jsonObj = new JSONObject(param);
		try{
			Integer pageIndex = jsonObj.getInt("pageIndex");
			Integer pageSize = jsonObj.getInt("pageSize");
			return new Range((pageIndex - 1) * pageSize, pageSize);
		}catch (Exception e){
			return null;
		}
	}

	/**
	 * 获取排序
	 * @param param
	 * @return
	 */
	public static Sorter getSorter(String param) {
		JSONObject jsonObj = new JSONObject(param);
		Sorter sorter = null;
		try {
			String sortName = jsonObj.getString("sortName");
			if(StringUtils.isNotBlank(sortName)){
				String sortOrder = jsonObj.getString("sortOrder");
				if(StringUtils.isNotBlank(sortOrder)){
					sorter = new Sorter();
					if(sortOrder.equals("asc")){
						sorter.asc(sortName);
					}else{
						sorter.desc(sortName);
					}
				}
			}
			return sorter;
		}catch (Exception ex){
			return sorter;
		}
	}

	/**
	 * 获取分页数据
	 * @param paginationSupport
	 * @return
	 */
	public static Map<String, Object> getPageMap(PaginationSupport paginationSupport){
		if(paginationSupport == null){
			return null;
		}
		Integer totalCount = paginationSupport.getTotalCount();
		if(totalCount == 0){
			return null;
		}
		Map<String, Object> result = new HashMap<>();
		result.put("totalCount", totalCount);
		result.put("result", paginationSupport.getItems());
		return result;
	}

	/**
	 * 获取分页数据
	 * @param paginationSupport
	 * @return
	 */
	public static Map<String, Object> toTableMap(PaginationSupport paginationSupport){
		if(paginationSupport == null){
			return null;
		}
		Integer totalCount = paginationSupport.getTotalCount();
		if(totalCount == 0){
			return null;
		}
		Map<String, Object> result = new HashMap<>();
		result.put("totalCount", totalCount);
		List<?> list = paginationSupport.getItems();
		List<Map<String, Object>> mapList = new ArrayList<>();
		for(Object object : list){
			Map<String, Object> map = AutoEvaluationUtil.domainToMap(object);
			mapList.add(map);
		}
		result.put("result", mapList);
		return result;
	}


	/**
	 * 获取枚举值
	 * @param type
	 * @return
	 */
	public static String getEnumKeyValueString(String type){
		Map<String, String> map = new LinkedHashMap<>();
		switch (type){
			case "permissionsTag":
				for(Permissions.Tag tag : Permissions.Tag.values()){
					if(tag != Permissions.Tag.Admin){
						map.put(tag.getType(), tag.getName());
					}
				}
				break;
		}
		return JsonUtil.getJsonStrFromEntity(map);
	}


	/**
	 * 检测创建实体是否合法（字段是否不为空，字段是否有值）
	 * @param obj
	 * @param <T>
	 * @return
	 * @throws Exception
	 */
	public static <T> T checkEntity(T obj )  throws Exception {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<T>> set = validator.validate(obj);
		for (ConstraintViolation<T> constraintViolation : set){
			throw new Exception(constraintViolation.getMessage());
		}
		return obj;
	}

	/**
	 * 从错误信息判断表字段是否为唯一值
	 * @param error
	 * @param key
	 * @return
	 */
	public static Boolean checkIsNotUniqueForEntity(String error, String key){
		if(StringUtils.isNotBlank(error)){
			if(error.contains("Duplicate entry '") && error.contains("' for key '" + key + "'")){
				return true;
			}
		}
		return false;
	}
	
	/**
     * 字符串转数组
     * @param str1
     * @return
     */
    public static List<String> strToList(String str1){
        List<String> objectList=new ArrayList<String>();
        if(StringUtils.isNoneBlank(str1)){
            String [] strs=str1.split(",");
            for(String str:strs){
                objectList.add(str);
            }
        }
        return objectList;
    }

	/**
	 * 数据多余数据比较
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static List<Object> remainList(List<?> o1,List<?> o2){
		List<Object> objectList=new ArrayList<Object>();
		for( Object o:o1){
			if(!o2.contains(o)){
				objectList.add(o);
			}
		}
		return objectList;
	}
	/**
	 * 数组转字符串
	 * @return
	 */
	public static String arrayToStr(List<?> o1){
		String str="";
		for( Object o:o1){
			str+=",\""+o.toString()+"\"";
		}
		if(StringUtils.isNotBlank(str)){
			str=str.substring(1);
		}
		return str;
	}

	/**
	 * 获取视频、音频时长
	 * @param path
	 * @return
	 */
	public static int sourceTime(String path){
		File source = new File(path);
		if(source.exists()){
			Encoder encoder = new Encoder();
			try {
				MultimediaInfo m = encoder.getInfo(source);
				long ls = m.getDuration();
				return (int)(ls/1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * 秒转时间格式
	 * @param secondTime
	 * @return
	 */
	public static String secondTimeToStr(int secondTime){
		int temp=0;
		StringBuffer sb=new StringBuffer();
		temp = secondTime/3600;
		sb.append((temp<10)?"0"+temp+":":""+temp+":");

		temp=secondTime%3600/60;
		sb.append((temp<10)?"0"+temp+":":""+temp+":");

		temp=secondTime%3600%60;
		sb.append((temp<10)?"0"+temp:""+temp);

		return sb.toString();
	}

	/**
	 * Map按Value排序，先转成List再排序
	 * @param map
	 * @return
	 */
	public static List<Map.Entry<String, String>> sortMapList(Map<String, String> map){
		List<Map.Entry<String, String>> infoIds =
				new ArrayList<>(map.entrySet());
		Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
			public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});
		return infoIds;
	}
}
