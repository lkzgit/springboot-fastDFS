package com.demo.updown.serviceImpl;

import com.demo.updown.entity.Student;
import com.demo.updown.mapper.StudentDao;
import com.demo.updown.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author root
 * @since 2020-04-28
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentDao, Student> implements StudentService {


}
