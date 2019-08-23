package com.cdtelecom.service;

import com.cdtelecom.mapper.AssetInfoMapper;
import com.cdtelecom.po.AssetInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Qualifier("transactionTestService")
public class TransactionTestService {
    @Autowired
    private AssetInfoMapper assetInfoMapper;

    @Transactional(rollbackFor=Exception.class) //测试通过
    public void testTransaction() throws Exception {
        AssetInfo info = new AssetInfo();
        info.setAssetId("860729040002563");
        info.setMsisdn("11111");
        assetInfoMapper.updateMsisdn(info);

        info.getAbandonedCause().toString();

        info = new AssetInfo();
        info.setAssetId("860729040002563");
        info.setOutsideAssetId("22222");
        assetInfoMapper.updateOutsideAssetId(info);
    }
}
