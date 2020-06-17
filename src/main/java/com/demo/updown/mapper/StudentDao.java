package com.demo.updown.mapper;

import com.demo.updown.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author root
 * @since 2020-04-28
 */

@Mapper
public interface StudentDao extends BaseMapper<Student> {

}
