package com.cdtelecom.mapper;

import com.cdtelecom.po.AssetInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class AssetInfoMapperTest {

    @Autowired
    private AssetInfoMapper assetInfoMapper;

//    @Test
//    public void testInsert() throws Exception {
//        AssetInfo info = new AssetInfo();
//
//        info.setImsi("1234567");
//        info.setIccid("123456789");
//        assetInfoMapper.insert(info);
//    }

    @Test
    public void testQuery() throws Exception {
        List<AssetInfo> AssetInfos = assetInfoMapper.getAll();
        System.out.println(AssetInfos.toString());
        for(AssetInfo info:AssetInfos){
            System.out.println(info);
        }
    }


//    @Test
//    public void testUpdate() throws Exception {
//        AssetInfo assetInfo = assetInfoMapper.getOne(30l);
//        System.out.println(assetInfo.toString());
//        assetInfo.setImsi("11122");
//        assetInfoMapper.update(assetInfo);
//    }
}