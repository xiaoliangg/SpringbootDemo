package com.cdtelecom.pojo.request;

import java.io.Serializable;
import java.util.List;


/**
 * 运营商接入网关接口请求POJO基类
 * @author 
 *
 */
public class BasicRequest{
	//公共属性
	private String commSeq;
	private String supplierCode;
	private String supplierName;
	private String busiType;

	//
	private String iccid;
	private String msisdn;
	private String imsi;

	//开通,关闭,重新开通流量,限速
	private String packageCode;
	private String newPackageCode;
	private String oldPackageCode;

	//机卡重绑
	private String bindType;
	private String imei;

	//SIM卡列表查询
	private String pageIndex;

	//流量查询(时间段)
	private String needDtl;
	private String startDate;
	private String endDate;

	@Override
	public String toString() {
		return "BasicRequest{" +
				"commSeq='" + commSeq + '\'' +
				", supplierCode='" + supplierCode + '\'' +
				", supplierName='" + supplierName + '\'' +
				", busiType='" + busiType + '\'' +
				", iccid='" + iccid + '\'' +
				", msisdn='" + msisdn + '\'' +
				", imsi='" + imsi + '\'' +
				", packageCode='" + packageCode + '\'' +
				", newPackageCode='" + newPackageCode + '\'' +
				", oldPackageCode='" + oldPackageCode + '\'' +
				", bindType='" + bindType + '\'' +
				", imei='" + imei + '\'' +
				", pageIndex='" + pageIndex + '\'' +
				", needDtl='" + needDtl + '\'' +
				", startDate='" + startDate + '\'' +
				", endDate='" + endDate + '\'' +
				", monthDate='" + monthDate + '\'' +
				'}';
	}

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	public String getNewPackageCode() {
		return newPackageCode;
	}

	public void setNewPackageCode(String newPackageCode) {
		this.newPackageCode = newPackageCode;
	}

	public String getOldPackageCode() {
		return oldPackageCode;
	}

	public void setOldPackageCode(String oldPackageCode) {
		this.oldPackageCode = oldPackageCode;
	}

	public String getBindType() {
		return bindType;
	}

	public void setBindType(String bindType) {
		this.bindType = bindType;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getNeedDtl() {
		return needDtl;
	}

	public void setNeedDtl(String needDtl) {
		this.needDtl = needDtl;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getMonthDate() {
		return monthDate;
	}

	public void setMonthDate(String monthDate) {
		this.monthDate = monthDate;
	}

	//套餐使用量查询
	private String monthDate;



	public String getCommSeq() {
		return commSeq;
	}

	public void setCommSeq(String commSeq) {
		this.commSeq = commSeq;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
}
