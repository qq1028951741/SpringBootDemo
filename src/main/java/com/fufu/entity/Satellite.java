package com.fufu.entity;

import com.fufu.annotation.Excel;

import java.util.Date;

public class Satellite implements java.io.Serializable{
	private Long id;
	@Excel(name = "卫星名称")
	private String satelliteName;
	@Excel(name = "编号")
	private String satelliteNo;
	@Excel(name = "国别")
	private String country;
	@Excel(name = "类别")
	private Integer satelliteType;
	@Excel(name = "时间段开始")
	private Date startTime;
	@Excel(name = "时间段结束")
	private Date endTime;
	@Excel(name = "安全时长")
	private Integer safeTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getSatelliteName() {
		return satelliteName;
	}
	public void setSatelliteName(String satelliteName) {
		this.satelliteName = satelliteName;
	}
	
	public String getSatelliteNo() {
		return satelliteNo;
	}
	public void setSatelliteNo(String satelliteNo) {
		this.satelliteNo = satelliteNo;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public Integer getSatelliteType() {
		return satelliteType;
	}
	public void setSatelliteType(Integer satelliteType) {
		this.satelliteType = satelliteType;
	}
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public Integer getSafeTime() {
		return safeTime;
	}
	public void setSafeTime(Integer safeTime) {
		this.safeTime = safeTime;
	}
	
}
