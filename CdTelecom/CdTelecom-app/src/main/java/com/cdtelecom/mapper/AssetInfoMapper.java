package com.cdtelecom.mapper;

import com.cdtelecom.po.AssetInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AssetInfoMapper {
    @Select("SELECT * FROM asset_info_t limit 10")
    List<AssetInfo> getAll();

    @Insert("INSERT INTO asset_info_t(asset_id,iccid,imsi) VALUES(#{assetId}, #{iccid}, #{imsi})")
    void insert(AssetInfo assetInfo);

    @Select("SELECT * FROM asset_info_t WHERE id = #{id}")
    AssetInfo getOne(Long id);

    @Update("UPDATE asset_info_t SET iccid=#{iccid},imsi=#{imsi} WHERE id =#{id}")
    void update(AssetInfo assetInfo);

    @Update("UPDATE asset_info_t SET msisdn=#{msisdn} WHERE asset_id =#{assetId}")
    void updateMsisdn(AssetInfo assetInfo);

    @Update("UPDATE asset_info_t SET outside_asset_id=#{outsideAssetId} WHERE asset_id =#{assetId}")
    void updateOutsideAssetId(AssetInfo assetInfo);
}
