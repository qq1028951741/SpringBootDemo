package com.fufu.mapper;

import com.fufu.entity.Satellite;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SatelliteMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Satellite record);

    int insertSelective(Satellite record);

    Satellite selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Satellite record);

    int updateByPrimaryKey(Satellite record);

    void deleteAll();
}