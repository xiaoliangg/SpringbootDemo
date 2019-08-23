package com.cdtelecom.po;


import java.math.BigDecimal;

import javax.persistence.*;

@Table(name = "asset_info_t")
public class AssetInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "asset_id")
    private String assetId;
    /**
     * 生产时预置码号ICCID
     */
    @Column(name = "iccid")
    private String iccid;

    /**
     * 生产时预置码号的imsi
     */
    @Column(name = "imsi")
    private String imsi;

    /**
     * 生产时预置码号的MSISDN
     */
    @Column(name = "msisdn")
    private String msisdn;

    /**
     * 外部设备标识码
     */
    @Column(name = "outside_asset_id")
    private String outsideAssetId;
    /**
     * 模块封装形式
     */
    @Column(name = "asset_footprint")
    private String assetFootprint;

    /**
     * Roamingsim编码
     */
    @Column(name = "roamingsim_code")
    private String roamingsimCode;

    /**
     * Roamingsim版本号
     */
    @Column(name = "version")
    private String version;

    /**
     * crc
     */
    @Column(name = "crc")
    private String crc;

    /**
     * 芯片型号
     */
    @Column(name = "chip_model")
    private String chipModel;

    /**
     * 模块名称
     */
    @Column(name = "asset_name")
    private String assetName;

    /**
     * pn
     */
    @Column(name = "pn")
    private String pn;

    /**
     * 模块生命周期，取值包括如下：1：静默期；2：使用期；3：停机保服务期；4：废弃回收期
     */
    @Column(name = "life_cycle")
    private String lifecycle;

    /**
     * 生命周期开始时间，格式为YYYYMMDD
     */
    @Column(name = "lifecycle_start_time")
    private String lifecycleStartTime;

    /**
     * 生命周期结束时间，格式为YYYYMMDD
     */
    @Column(name = "lifecycle_end_time")
    private String lifecycleEndTime;

    /**
     * 生产批次号：PYYYYMMDD+4位数字序列号
     */
    @Column(name = "task_no")
    private String taskNo;

    /**
     * 生产厂商名称
     */
    @Column(name = "manufacturer_name")
    private String manufacturerName;


    /**
     * 生产厂商编码
     */
    @Column(name = "manufacturer_code")
    private String manufacturerCode;


    /**
     * 入库时间，即指该模块的生产完成时间
     */
    @Column(name = "in_time")
    private String inTime;

    /**
     * 激活标识，当收到模块第一次心跳后，需模块设备修改为已激活状态；取值及含义描述如下：
     0-未激活；
     1-已激活；

     */
    @Column(name = "activate_flag")
    private String activateFlag;

    /**
     * 激活时间；
     */
    @Column(name = "activate_time")
    private String activateTime;

    /**
     * 出售（分配）时间
     * 删除字段
     */


//    @Column(name = "sale_time")
//    private String saleTime;

    /**
     * 归属的合作伙伴名称
     */
    @Column(name = "partner_name")
    private String partnerName;

    /**
     * 合作伙伴编码
     */
    @Column(name = "partner_code")
    private String partnerCode;

    /**
     * 主号最近一次上报位置信息时间
     */
    @Column(name = "last_report_time")
    private String lastReportTime;

    /**
     * 最近一次上报位置的国家地区的国家码
     */
    @Column(name = "last_report_position_mcc")
    private String lastReportPositionMcc;

    /**
     * 最近一次上报位置的国家地区的名称
     */
    @Column(name = "last_report_position_name")
    private String lastReportPositionName;

    @Column(name = "last_report_operator_code")
    private String lastReportOperatorCode;

    @Column(name = "last_report_operator_name")
    private String lastReportOperatorName;

    /**
     * 被废弃的时间；
     */
    @Column(name = "abandoned_time")
    private String abandonedTime;

    /**
     * 被废弃的原因；
     */
    @Column(name = "abandoned_cause")
    private String abandonedCause;

    /**
     * 数据加密分散因子（与入库时初始主号或副号iccid相同）
     */
    @Column(name = "data_encrypt_factor")
    private String dataEncryptFactor;
    /**
     * 主号下bip参数是否更新过:0未更新 1已更新
     */
    @Column(name = "bip_is_update")
    private String bipIsUpdate;


    /**
     * 模块归属的项目编号
     * 删除字段
     */
//    @Column(name = "project_number")
//    private String projectNumber;

    /**
     * 模块归属的批次号
     * 删除字段
     */
//    @Column(name = "assign_batch_number")
//    private String assignBatchNumber;


    /**
     * 订购流量套餐类型
     */
    @Column(name = "package_type")
    private String packageType;

    /**
     * 正在使用的流量套餐编码
     */
    @Column(name = "package_code")
    private String packageCode;

    /**
     * 正在使用的流量套餐名称，取值定义如下：当正在使用套餐类型为1-基础包月流量套餐时则表示流量套餐名称，当正在使用套餐类型为2-流量池时则表示流量池名称
     */
    @Column(name = "package_name")
    private String packageName;


    @Column(name = "package_flow")
    private String packageFlow;//套餐流量

    @Column(name = "order_unint_dt")
    private String orderUnintDt;//当月订购周期已用流量

    @Column(name = "package_use_rate")
    private String packageUseRate;//流量使用率

    /**
     * 分配时间
     */
    @Column(name = "assign_time")
    private String assignTime;

    /**
     * 暂停模块服务时间
     * 删除字段
     */
//    @Column(name = "suspend_time")
//    private String suspendTime;

    /**
     * 暂停模块服务原因
     * 删除字段
     */
//    @Column(name = "suspend_cause")
//    private String suspendCause;

    /**
     * 是否暂停服务，取值设定如下：0-否，即未暂停流量服务；1-是，即暂停流量服务
     * 删除字段
     */
//    @Column(name = "suspend_flag")
//    private String suspendFlag;

    /**
     * 模块测试状态，取值定义如下：0-未测试；1-提交测试；2-测试中；3-测试结束
     * 删除字段
     */
//    @Column(name = "test_status")
//    private String testStatus;

    /**
     * 订购ID，格式为：YYYYMMDD+10位序列号）
     */
    @Column(name = "order_id")
    private String orderId;

    @Column(name = "is_online")
    private String isOnline;

    @Column(name = "last_login_country")
    private String lastLoginCountry;

    @Column(name = "last_login_operator")
    private String lastLoginOperator;

    @Column(name = "last_login_time")
    private String lastLoginTime;

    @Column(name = "silent_cycle")
    private String silentCycle;

    @Column(name = "stop_service_period")
    private String stopServicePeriod;

    /*运应商编码*/
    @Column(name = "operator_code")
    private String operatorCode;
    /*运应商名称*/
    @Column(name = "operator_name")
    private String operatorName;
    /*供应营商编码*/
    @Column(name = "supplier_code")
    private String supplierCode;
    /*供应商名称*/
    @Column(name = "supplier_name")
    private String supplierName;
    /*生产方式*/
    @Column(name = "manu_type")
    private String manuType;

    private String deviceForm;
    private String remark;
    private String initFlag;
    private String otaFlag;
    private String bipFlag;
    private String inuseIndustry;
    private String moreImsiFlag;
    private String otaProtocolVersion;
    private String bipParamName;
    private String deviceStatus;
    @Column(name = "is_purchase")
    private String isPurchase;
    //卡面面额
    @Column(name = "amount")
    private BigDecimal amount;


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getIsPurchase() {
        return isPurchase;
    }

    public void setIsPurchase(String isPurchase) {
        this.isPurchase = isPurchase;
    }

    public String getBipIsUpdate() {
        return bipIsUpdate;
    }

    public void setBipIsUpdate(String bipIsUpdate) {
        this.bipIsUpdate = bipIsUpdate;
    }

    private String cardMaterialCode;

    public String getManuType() {
        return manuType;
    }

    public void setManuType(String manuType) {
        this.manuType = manuType;
    }

    public String getDeviceForm() {
        return deviceForm;
    }

    public void setDeviceForm(String deviceForm) {
        this.deviceForm = deviceForm;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAssetId() {
        return assetId;
    }
    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }
    public String getIccid() {
        return iccid;
    }
    public void setIccid(String iccid) {
        this.iccid = iccid;
    }
    public String getImsi() {
        return imsi;
    }
    public void setImsi(String imsi) {
        this.imsi = imsi;
    }
    public String getMsisdn() {
        return msisdn;
    }
    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }
    public String getOutsideAssetId() {
        return outsideAssetId;
    }
    public void setOutsideAssetId(String outsideAssetId) {
        this.outsideAssetId = outsideAssetId;
    }
    public String getAssetFootprint() {
        return assetFootprint;
    }
    public void setAssetFootprint(String assetFootprint) {
        this.assetFootprint = assetFootprint;
    }
    public String getRoamingsimCode() {
        return roamingsimCode;
    }
    public void setRoamingsimCode(String roamingsimCode) {
        this.roamingsimCode = roamingsimCode;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getCrc() {
        return crc;
    }
    public void setCrc(String crc) {
        this.crc = crc;
    }
    public String getChipModel() {
        return chipModel;
    }
    public void setChipModel(String chipModel) {
        this.chipModel = chipModel;
    }
    public String getAssetName() {
        return assetName;
    }
    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }
    public String getPn() {
        return pn;
    }
    public void setPn(String pn) {
        this.pn = pn;
    }
    public String getLifecycle() {
        return lifecycle;
    }
    public void setLifecycle(String lifecycle) {
        this.lifecycle = lifecycle;
    }
    public String getLifecycleStartTime() {
        return lifecycleStartTime;
    }
    public void setLifecycleStartTime(String lifecycleStartTime) {
        this.lifecycleStartTime = lifecycleStartTime;
    }
    public String getLifecycleEndTime() {
        return lifecycleEndTime;
    }
    public void setLifecycleEndTime(String lifecycleEndTime) {
        this.lifecycleEndTime = lifecycleEndTime;
    }
    public String getTaskNo() {
        return taskNo;
    }
    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }
    public String getManufacturerName() {
        return manufacturerName;
    }
    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }
    public String getManufacturerCode() {
        return manufacturerCode;
    }
    public void setManufacturerCode(String manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }
    public String getInTime() {
        return inTime;
    }
    public void setInTime(String inTime) {
        this.inTime = inTime;
    }
    public String getActivateFlag() {
        return activateFlag;
    }
    public void setActivateFlag(String activateFlag) {
        this.activateFlag = activateFlag;
    }
    public String getActivateTime() {
        return activateTime;
    }
    public void setActivateTime(String activateTime) {
        this.activateTime = activateTime;
    }
    //	public String getSaleTime() {
//		return saleTime;
//	}
//	public void setSaleTime(String saleTime) {
//		this.saleTime = saleTime;
//	}
    public String getPartnerName() {
        return partnerName;
    }
    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }
    public String getPartnerCode() {
        return partnerCode;
    }
    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }
    public String getLastReportTime() {
        return lastReportTime;
    }
    public void setLastReportTime(String lastReportTime) {
        this.lastReportTime = lastReportTime;
    }
    public String getLastReportPositionMcc() {
        return lastReportPositionMcc;
    }
    public void setLastReportPositionMcc(String lastReportPositionMcc) {
        this.lastReportPositionMcc = lastReportPositionMcc;
    }
    public String getLastReportPositionName() {
        return lastReportPositionName;
    }
    public void setLastReportPositionName(String lastReportPositionName) {
        this.lastReportPositionName = lastReportPositionName;
    }
    public String getLastReportOperatorCode() {
        return lastReportOperatorCode;
    }
    public void setLastReportOperatorCode(String lastReportOperatorCode) {
        this.lastReportOperatorCode = lastReportOperatorCode;
    }
    public String getLastReportOperatorName() {
        return lastReportOperatorName;
    }
    public void setLastReportOperatorName(String lastReportOperatorName) {
        this.lastReportOperatorName = lastReportOperatorName;
    }
    public String getAbandonedTime() {
        return abandonedTime;
    }
    public void setAbandonedTime(String abandonedTime) {
        this.abandonedTime = abandonedTime;
    }
    public String getAbandonedCause() {
        return abandonedCause;
    }
    public void setAbandonedCause(String abandonedCause) {
        this.abandonedCause = abandonedCause;
    }
    public String getDataEncryptFactor() {
        return dataEncryptFactor;
    }
    public void setDataEncryptFactor(String dataEncryptFactor) {
        this.dataEncryptFactor = dataEncryptFactor;
    }
    //	public String getProjectNumber() {
//		return projectNumber;
//	}
//	public void setProjectNumber(String projectNumber) {
//		this.projectNumber = projectNumber;
//	}
//	public String getAssignBatchNumber() {
//		return assignBatchNumber;
//	}
//	public void setAssignBatchNumber(String assignBatchNumber) {
//		this.assignBatchNumber = assignBatchNumber;
//	}
    public String getPackageType() {
        return packageType;
    }
    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }
    public String getPackageCode() {
        return packageCode;
    }
    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }
    public String getPackageName() {
        return packageName;
    }
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    public String getPackageFlow() {
        return packageFlow;
    }
    public void setPackageFlow(String packageFlow) {
        this.packageFlow = packageFlow;
    }
    public String getOrderUnintDt() {
        return orderUnintDt;
    }
    public void setOrderUnintDt(String orderUnintDt) {
        this.orderUnintDt = orderUnintDt;
    }
    public String getPackageUseRate() {
        return packageUseRate;
    }
    public void setPackageUseRate(String packageUseRate) {
        this.packageUseRate = packageUseRate;
    }
    public String getAssignTime() {
        return assignTime;
    }
    public void setAssignTime(String assignTime) {
        this.assignTime = assignTime;
    }
    //	public String getSuspendTime() {
//		return suspendTime;
//	}
//	public void setSuspendTime(String suspendTime) {
//		this.suspendTime = suspendTime;
//	}
//	public String getSuspendCause() {
//		return suspendCause;
//	}
//	public void setSuspendCause(String suspendCause) {
//		this.suspendCause = suspendCause;
//	}
//	public String getSuspendFlag() {
//		return suspendFlag;
//	}
//	public void setSuspendFlag(String suspendFlag) {
//		this.suspendFlag = suspendFlag;
//	}
//	public String getTestStatus() {
//		return testStatus;
//	}
//	public void setTestStatus(String testStatus) {
//		this.testStatus = testStatus;
//	}
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getIsOnline() {
        return isOnline;
    }
    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }
    public String getLastLoginCountry() {
        return lastLoginCountry;
    }
    public void setLastLoginCountry(String lastLoginCountry) {
        this.lastLoginCountry = lastLoginCountry;
    }
    public String getLastLoginOperator() {
        return lastLoginOperator;
    }
    public void setLastLoginOperator(String lastLoginOperator) {
        this.lastLoginOperator = lastLoginOperator;
    }
    public String getLastLoginTime() {
        return lastLoginTime;
    }
    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    public String getSilentCycle() {
        return silentCycle;
    }
    public void setSilentCycle(String silentCycle) {
        this.silentCycle = silentCycle;
    }
    public String getStopServicePeriod() {
        return stopServicePeriod;
    }
    public void setStopServicePeriod(String stopServicePeriod) {
        this.stopServicePeriod = stopServicePeriod;
    }
    public String getOperatorCode() {
        return operatorCode;
    }
    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }
    public String getOperatorName() {
        return operatorName;
    }
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
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
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getInitFlag() {
        return initFlag;
    }
    public void setInitFlag(String initFlag) {
        this.initFlag = initFlag;
    }
    public String getOtaFlag() {
        return otaFlag;
    }
    public void setOtaFlag(String otaFlag) {
        this.otaFlag = otaFlag;
    }
    public String getBipFlag() {
        return bipFlag;
    }
    public void setBipFlag(String bipFlag) {
        this.bipFlag = bipFlag;
    }
    public String getInuseIndustry() {
        return inuseIndustry;
    }
    public void setInuseIndustry(String inuseIndustry) {
        this.inuseIndustry = inuseIndustry;
    }
    public String getMoreImsiFlag() {
        return moreImsiFlag;
    }
    public void setMoreImsiFlag(String moreImsiFlag) {
        this.moreImsiFlag = moreImsiFlag;
    }
    public String getOtaProtocolVersion() {
        return otaProtocolVersion;
    }
    public void setOtaProtocolVersion(String otaProtocolVersion) {
        this.otaProtocolVersion = otaProtocolVersion;
    }
    public String getBipParamName() {
        return bipParamName;
    }
    public void setBipParamName(String bipParamName) {
        this.bipParamName = bipParamName;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getCardMaterialCode() {
        return cardMaterialCode;
    }

    public void setCardMaterialCode(String cardMaterialCode) {
        this.cardMaterialCode = cardMaterialCode;
    }

    @Override
    public String toString() {
        return "AssetInfo{" +
                "id=" + id +
                ", assetId='" + assetId + '\'' +
                ", iccid='" + iccid + '\'' +
                ", imsi='" + imsi + '\'' +
                ", msisdn='" + msisdn + '\'' +
                ", outsideAssetId='" + outsideAssetId + '\'' +
                ", assetFootprint='" + assetFootprint + '\'' +
                ", roamingsimCode='" + roamingsimCode + '\'' +
                ", version='" + version + '\'' +
                ", crc='" + crc + '\'' +
                ", chipModel='" + chipModel + '\'' +
                ", assetName='" + assetName + '\'' +
                ", pn='" + pn + '\'' +
                ", lifecycle='" + lifecycle + '\'' +
                ", lifecycleStartTime='" + lifecycleStartTime + '\'' +
                ", lifecycleEndTime='" + lifecycleEndTime + '\'' +
                ", taskNo='" + taskNo + '\'' +
                ", manufacturerName='" + manufacturerName + '\'' +
                ", manufacturerCode='" + manufacturerCode + '\'' +
                ", inTime='" + inTime + '\'' +
                ", activateFlag='" + activateFlag + '\'' +
                ", activateTime='" + activateTime + '\'' +
                ", partnerName='" + partnerName + '\'' +
                ", partnerCode='" + partnerCode + '\'' +
                ", lastReportTime='" + lastReportTime + '\'' +
                ", lastReportPositionMcc='" + lastReportPositionMcc + '\'' +
                ", lastReportPositionName='" + lastReportPositionName + '\'' +
                ", lastReportOperatorCode='" + lastReportOperatorCode + '\'' +
                ", lastReportOperatorName='" + lastReportOperatorName + '\'' +
                ", abandonedTime='" + abandonedTime + '\'' +
                ", abandonedCause='" + abandonedCause + '\'' +
                ", dataEncryptFactor='" + dataEncryptFactor + '\'' +
                ", bipIsUpdate='" + bipIsUpdate + '\'' +
                ", packageType='" + packageType + '\'' +
                ", packageCode='" + packageCode + '\'' +
                ", packageName='" + packageName + '\'' +
                ", packageFlow='" + packageFlow + '\'' +
                ", orderUnintDt='" + orderUnintDt + '\'' +
                ", packageUseRate='" + packageUseRate + '\'' +
                ", assignTime='" + assignTime + '\'' +
                ", orderId='" + orderId + '\'' +
                ", isOnline='" + isOnline + '\'' +
                ", lastLoginCountry='" + lastLoginCountry + '\'' +
                ", lastLoginOperator='" + lastLoginOperator + '\'' +
                ", lastLoginTime='" + lastLoginTime + '\'' +
                ", silentCycle='" + silentCycle + '\'' +
                ", stopServicePeriod='" + stopServicePeriod + '\'' +
                ", operatorCode='" + operatorCode + '\'' +
                ", operatorName='" + operatorName + '\'' +
                ", supplierCode='" + supplierCode + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", manuType='" + manuType + '\'' +
                ", deviceForm='" + deviceForm + '\'' +
                ", remark='" + remark + '\'' +
                ", initFlag='" + initFlag + '\'' +
                ", otaFlag='" + otaFlag + '\'' +
                ", bipFlag='" + bipFlag + '\'' +
                ", inuseIndustry='" + inuseIndustry + '\'' +
                ", moreImsiFlag='" + moreImsiFlag + '\'' +
                ", otaProtocolVersion='" + otaProtocolVersion + '\'' +
                ", bipParamName='" + bipParamName + '\'' +
                ", deviceStatus='" + deviceStatus + '\'' +
                ", isPurchase='" + isPurchase + '\'' +
                ", amount=" + amount +
                ", cardMaterialCode='" + cardMaterialCode + '\'' +
                '}';
    }
}