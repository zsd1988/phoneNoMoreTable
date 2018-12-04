package com.qingpu.phone.systemSetting.service;

import com.qingpu.phone.systemManager.phoneSocket.CallPhoneListener;
import com.qingpu.phone.systemSetting.entity.Parameter;
import com.qingpu.phone.systemSetting.condition.ParameterCondition;
import com.qingpu.phone.common.dao.BaseDao;
import com.qingpu.phone.common.utils.AutoEvaluationUtil;
import com.qingpu.phone.common.utils.CommonUtils;
import com.qingpu.phone.common.utils.UUIDGenerator;
import com.qingpu.phone.common.utils.criteria.PaginationSupport;
import com.qingpu.phone.common.utils.criteria.Range;
import com.qingpu.phone.common.utils.criteria.Sorter;
import com.qingpu.phone.constants.QingpuConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("parameterService")
@Transactional
public class ParameterService {
	private static Logger logger = Logger.getLogger(ParameterService.class);

	@Resource
	private BaseDao baseDao;

	@Resource
	private SessionFactory sessionFactory;

	/**
	 * 创建
	 * @param parameter
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Parameter create(Parameter parameter) throws Exception{
		if(parameter != null && StringUtils.isBlank(parameter.getId())){
			parameter.setId(UUIDGenerator.getUUID());
		}
		CommonUtils.checkEntity(parameter);
		return (Parameter) baseDao.save(parameter);
	}


	/**
	 * 更新
	 * @param parameter
	 */
	public void update(Parameter parameter)  throws Exception {
		CommonUtils.checkEntity(parameter);
		baseDao.update(parameter);
	}


	/**
	 * 根据参数更新
	 * @param paramsTestTable
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateByParams(Parameter paramsTestTable) throws Exception{
		Parameter parameter = this.get(paramsTestTable.getId());
		if(parameter == null){
			throw new Exception("未找到对象");
		}
		String content = paramsTestTable.getContent();
		String code = parameter.getCode();
		if(code.equals(QingpuConstants.Parameter_CS_Intention) || code.equals(QingpuConstants.Parameter_Interview_Intention)
				|| code.equals(QingpuConstants.Parameter_Post) || code.equals(QingpuConstants.Parameter_CS_Gold)
				|| code.equals(QingpuConstants.Parameter_Interview_Gold)){
			content = content.replaceAll("，", ",");
		}
		parameter.setContent(content);
		this.update(parameter);
		this.resetConstants(paramsTestTable);
	}

	/**
	 * 删除
	 * @param parameter
	 */
	public void delete(Parameter parameter) {
		parameter.setIsDel(true);
		baseDao.update(parameter);
	}

	/**
	 * 获取
	 * @param id
	 * @return
	 */
	public Parameter get(String id) {
		return (Parameter)baseDao.get(Parameter.class, id);
	}


	/**
	 * 按条件查找
	 * @param parameterCondition
	 * @return
	 */
	public List<Parameter> list(ParameterCondition parameterCondition){
		return  parameterCondition.list(sessionFactory);
	}

	/**
	 * 按条件排序
	 * @param parameterCondition
	 * @param sorter
	 * @return
	 */
	public List<Parameter> list(ParameterCondition parameterCondition, Sorter sorter){
		return  parameterCondition.list(sessionFactory, sorter);
	}


	/**
	 * 获取相应数量
	 * @param testTableCondition
	 * @param num
	 * @return
	 */
	public List<Parameter> listCustom(ParameterCondition testTableCondition, int num){
		return  AutoEvaluationUtil.toClassList(testTableCondition.list(sessionFactory, num));
	}

	/**
	 * 按条件分页查询
	 * @param parameterCondition
	 * @param range
	 * @param sorter
	 * @return
	 */
	public PaginationSupport<Parameter> list(ParameterCondition parameterCondition, Range range, Sorter sorter){
		PaginationSupport<Parameter> parameterPaginationSupport = (PaginationSupport<Parameter>)parameterCondition.pageList(sessionFactory, sorter, range);
		return parameterPaginationSupport;
	}

	/**
	 * 获取数量
	 * @param parameterCondition
	 * @return
	 */
	public Long countByCondition(ParameterCondition parameterCondition){
		return   parameterCondition.countByCondition(sessionFactory);
	}



	/**
	 * 初始化
	 */
	@Transactional(rollbackFor = Exception.class)
	public void init(String id, String name, String code, String content, Parameter.DataType dataType) throws Exception {
		Parameter parameter = this.get(id);
		if(parameter == null){
			parameter = new Parameter();
			parameter.setId(id);
			parameter.setName(name);
			parameter.setCode(code);
			parameter.setContent(content);
			parameter.setDataType(dataType);
			create(parameter);
		}
		this.resetConstants(parameter);
	}

	/**
	 * 初始化
	 */
	public void init(){
		try{
			init(QingpuConstants.Parameter_Mouth_Goal,"客服每月任务",
					QingpuConstants.Parameter_Mouth_Goal,"5000", Parameter.DataType.INTEGER);
			init(QingpuConstants.Parameter_Mouth_Interview_Goal,"约访每月任务",
					QingpuConstants.Parameter_Mouth_Interview_Goal,"150", Parameter.DataType.INTEGER);
			init(QingpuConstants.Parameter_Sip_IP,"SIP服务器地址",
					QingpuConstants.Parameter_Sip_IP,"192.168.1.199", Parameter.DataType.STRING);
			init(QingpuConstants.Parameter_Week_Gold,"每周个人初始纹银",
					QingpuConstants.Parameter_Week_Gold,"100", Parameter.DataType.INTEGER);
			init(QingpuConstants.Parameter_Interview_Intention,"约访等级",
					QingpuConstants.Parameter_Interview_Intention,"0,1,3,5,8,10", Parameter.DataType.STRING);
			init(QingpuConstants.Parameter_Post,"职位",
					QingpuConstants.Parameter_Post,"普通学生,小组长,科代表,文娱委员,学习委员,副班长,候选班长,各校班长,会长", Parameter.DataType.STRING);
			init(QingpuConstants.Parameter_CS_Intention,"客服等级",
					QingpuConstants.Parameter_CS_Intention,"0,4,12,20,24,45", Parameter.DataType.STRING);
			init(QingpuConstants.Parameter_CS_Gold,"客服意向金币",
					QingpuConstants.Parameter_CS_Gold,"3,5,8", Parameter.DataType.STRING);
			init(QingpuConstants.Parameter_Interview_Gold,"约访意向金币",
					QingpuConstants.Parameter_Interview_Gold,"10,30", Parameter.DataType.STRING);
			init(QingpuConstants.Parameter_GoldRate,"金币暴击比率",
					QingpuConstants.Parameter_GoldRate,"100", Parameter.DataType.INTEGER);
			init(QingpuConstants.Parameter_TransNoneRate,"识别成none的最大概率(0.3)",
					QingpuConstants.Parameter_TransNoneRate,"0.3", Parameter.DataType.FLOAT);
			init(QingpuConstants.Parameter_CSWeekIntentionGoal,"客服每周意向目标",
					QingpuConstants.Parameter_CSWeekIntentionGoal,"25", Parameter.DataType.INTEGER);
			init(QingpuConstants.Parameter_NickName,"昵称",
					QingpuConstants.Parameter_NickName,"小仙女,小可爱,亲爱的,萌萌哒,漂亮的,小姐姐", Parameter.DataType.STRING);
		}catch (Exception e){
			logger.error("初始化参数表失败：" + e.getMessage());
		}
	}

	/***
	 * 根据code获得对象
	 * @param code
	 * @return
	 */
	public Parameter getObjectByCode(String code){
		if(StringUtils.isBlank(code)){
			return null;
		}
		ParameterCondition condition = new ParameterCondition();
		condition.setCode(code);
		List<Parameter> list = listCustom(condition,1);
		if(list.size() == 0){
			return null;
		}
		return list.get(0);
	}

	/**
	 * 获取结果
	 * @param code
	 * @return
	 */
	public Integer getIntByCode(String code){
		Integer result = 0;
		String content = this.getContentByCode(code);
		if(StringUtils.isNotBlank(content)){
			result = Integer.parseInt(content);
		}
		return result;
	}

	/**
	 * 根据编码找到字符串类型的值
	 * @param code
	 * @return
	 */
	public String getContentByCode(String code){
		// 。。。。。。。。。。。。。。。。。。。。注意修改重置contants内容，下面的函数
		String result = null;
		if(QingpuConstants.Parameter_Sip_IP.equals(code)){
			result = CallPhoneListener._socketHost;
		}else {
			Parameter parameter = this.getObjectByCode(code);
			if(parameter != null){
				result = parameter.getContent();
			}
		}
		return result;
	}

	/**
	 * 重置contants内容
	 */
	@Transactional(rollbackFor = Exception.class)
	public void resetConstants(Parameter parameter) throws Exception{
		String code = parameter.getCode();
		String content = parameter.getContent();
		if(QingpuConstants.Parameter_Sip_IP.equals(code)){
			CallPhoneListener._socketHost =   content ;
		}else if(QingpuConstants.Parameter_Post.equals(code)){
			QingpuConstants._postName = content.split(",");
			if(QingpuConstants._postName.length != 9){
				throw new Exception("请设置9组职位");
			}
		}else if(QingpuConstants.Parameter_CS_Gold.equals(code)){
			QingpuConstants._csGold = this.strToIntArr(content);
			if(QingpuConstants._csGold.length != 3){
				throw new Exception("请设置3组客服意向金币");
			}
		}else if(QingpuConstants.Parameter_Interview_Gold.equals(code)){
			QingpuConstants._interviewGold = this.strToIntArr(content);
			if(QingpuConstants._interviewGold.length != 2){
				throw new Exception("请设置2组约访意向金币");
			}
		}else if(QingpuConstants.Parameter_CS_Intention.equals(code)){
			QingpuConstants._csIntention = this.strToIntArr(content);
			if(QingpuConstants._csIntention.length != 6){
				throw new Exception("请设置6组客服等级");
			}
		}else if(QingpuConstants.Parameter_Interview_Intention.equals(code)){
			QingpuConstants._interviewIntention = this.strToIntArr(content);
			if(QingpuConstants._interviewIntention.length != 6){
				throw new Exception("请设置6组约访等级");
			}
		}else if(QingpuConstants.Parameter_TransNoneRate.equals(code)){
			CallPhoneListener._transNoneRate = Double.parseDouble(content);
		}else if(QingpuConstants.Parameter_NickName.equals(code)){
			QingpuConstants._nickName = content.split(",");
		}
	}

	private int[]  strToIntArr(String content){
		String[] stringArr = content.split(",");
		int[] gold = new int[stringArr.length];
		for(int i = 0; i < stringArr.length; i++){
			gold[i] = Integer.parseInt(stringArr[i]);
		}
		return gold;
	}

	private void setSourceServerUrl(){
		// 修改资源服务器路径
		String os = System.getProperty("os.name");
		if( os.toLowerCase().startsWith("win")){
			QingpuConstants.FILE_UPLOAD_PATH = "\\\\" + QingpuConstants.File_Server_Ip + "\\upload\\";
		}else{
			QingpuConstants.FILE_UPLOAD_PATH = "/mnt/java/upload/";
		}
		QingpuConstants.FILE_UPLOAD_URL = "http://" + QingpuConstants.File_Server_Ip + ":" + QingpuConstants.File_Server_Port + "/upload/";
	}

	/**
	 * 根据编码查找
	 * @param code
	 * @return
	 */
	public List<Parameter> getByCode(String code){
		if(StringUtils.isBlank(code)){
			return null;
		}
		ParameterCondition condition = new ParameterCondition();
		condition.setCode(code);
		List<Parameter> list = list(condition);
		return list;
	}
}
