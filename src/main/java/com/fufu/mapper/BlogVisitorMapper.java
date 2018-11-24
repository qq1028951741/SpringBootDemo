package com.fufu.mapper;

import com.fufu.entity.BlogVisitor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
public interface BlogVisitorMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BlogVisitor record);

    int insertSelective(BlogVisitor record);

    BlogVisitor selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BlogVisitor record);

    int updateByPrimaryKey(BlogVisitor record);

    List<BlogVisitor> selectAll();
}