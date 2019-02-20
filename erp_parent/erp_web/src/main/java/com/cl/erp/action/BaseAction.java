package com.cl.erp.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.cl.erp.biz.IBaseBiz;
import com.cl.erp.biz.impl.BaseBiz;


public class BaseAction<T> {

	private IBaseBiz<T> baseBiz;
	/**
	 * 条件查询属性驱动
	 */
	private T t1;
	private int page;//页码
	private int rows;//每页的记录数
	private Object param;
	private T t2;

	public IBaseBiz<T> getBaseBiz() {
		return baseBiz;
	}

	public void setBaseBiz(IBaseBiz<T> baseBiz) {
		this.baseBiz = baseBiz;
	}

	public T getT1() {
		return t1;
	}

	public void setT1(T t1) {
		this.t1 = t1;
	}

	public T getT2() {
		return t2;
	}

	public void setT2(T t2) {
		this.t2 = t2;
	}

	public Object getParam() {
		return param;
	}

	public void setParam(Object param) {
		this.param = param;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void getList(){
		//System.out.println(t1+" 页码："+page+" 记录数："+rows);
		int firstResult = (page-1)*rows;
		List<T> list = baseBiz.getList(t1,t2,param,firstResult,rows);
		long total = baseBiz.getCount(t1,t2,param);
		//[total:total,rows:[]]
		Map<String,Object> mapData = new HashMap<String,Object>();
		
		mapData.put("total", total);
		mapData.put("rows", list);
		//把部门列表转换成json字符串
		String listString = JSON.toJSONString(mapData);
		write(listString);
	}
	/**
	 * 新增，修改
	 */
	private T t;
	
	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	public void add(){
		//{"success":true,"message":""}
		//返回前端的json数据
		try {
			baseBiz.add(t);
			ajaxReturn(true, "新增成功");
		} catch (Exception e) {
			ajaxReturn(false, "新增失败");
			e.printStackTrace();
		}
	}
	//删除的id
	private long id;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 删除
	 * @param uuid
	 */
	public void delete(){
		try {
			baseBiz.delete(id);
			ajaxReturn(true, "删除成功");
		} catch (Exception e) {
			ajaxReturn(false, "删除失败");
			e.printStackTrace();
		}
		
	}
	/**
	 * 查询对象
	 */
	public void get(){
		T T = baseBiz.get(id);
		String jsonString = JSON.toJSONString(T);
		//转换Map对象
		String jsonStringAfter=mapData(jsonString, "t");
		System.out.println("转换前："+jsonString+"转换后："+jsonStringAfter);
		write(jsonStringAfter);
	}
	
	public void update(){
		try {
			System.out.println("update");
			System.out.println(t);
			baseBiz.update(t);
			ajaxReturn(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxReturn(false, "修改失败");
		}
	}
	/**
	 * 
	 * @param jsonString:{"name":"管理员组","tele":"000000","uuid":2}
	 * @param prefix::{"T.name":"管理员组","T.tele":"000000","T.uuid":2}
	 * @return
	 */
	public String mapData(String jsonString,String prefix){
		Map<String,Object> map = JSON.parseObject(jsonString);
		//转换后的map
		Map<String,Object> dataMap = new HashMap<String,Object>();
		//给每个key加前缀
		for(String key:map.keySet()){
			dataMap.put(prefix+"."+key, map.get(key));
		}
		//map转成json
		return JSON.toJSONString(dataMap);
	}
	
	public void ajaxReturn(boolean success,String message){
		Map<String,Object> rtn = new HashMap<String,Object>();
		rtn.put("success", success);
		rtn.put("message", message);
		write(JSON.toJSONString(rtn));
		
	}
	
	public void write(String jsonString){
		try {
			//输出给页面
			ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
			ServletActionContext.getResponse().getWriter().write(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
