package com.demo.updown.controller;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.updown.entity.Student;
import com.demo.updown.mapper.StudentDao;
import com.demo.updown.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author root
 * @since 2020-04-28
 */
@RestController
@RequestMapping("/student")
public class StudentController {


    @Autowired
    private StudentService studentService;
    @Resource
    private StudentDao studentDao;

    @RequestMapping("/list")
    public Page<Student> getList(){

        Page<Student> studentPage = new Page<>(1, 3);
        long size = studentPage.getSize();
        long total = studentPage.getTotal();
        System.out.println("size"+size+"----total"+total);
        IPage<Student> studentIPage = studentService.getBaseMapper().selectPage(studentPage, null);
        List<Student> records = studentIPage.getRecords();
       studentPage.setRecords(records);
        // 默认查询所有
        List<Student> students = studentService.getBaseMapper().selectList(null);

        //条件查询
       // studentService.page(studentPage,new QueryWrapper<Student>().like("sname","风"));
        return studentPage;
    }
    @RequestMapping("/findById")
    public List<Map<String, Object>> getById(@RequestParam("sid")Integer sid){
        //注意QueryWrapper一定要指定对象数据，否则表达式不能使用
        QueryWrapper<Student> qw = new QueryWrapper<>();
        qw.lambda().like(Student::getSname,"李");
        //Student st = studentService.getOne(qw);
        // Student st = studentService.getById(1);
        // 返回list的map集合 和list一样 查询列表
     //   List<Map<String, Object>> maps = studentService.listMaps(qw);
      List<Map<String, Object>> maps = studentService.listMaps();
        //查询结果返回的是数值
       // List<Object> list = studentService.listObjs();
        return maps;
    }
    @RequestMapping("/insert")
    public  void insert(){
        Student st=new Student("aea985a4b915","张一82","男");
        Student st2=new Student(UUID.randomUUID().toString(),"张一2","男");
        Student st3=new Student(UUID.randomUUID().toString(),"张一3","男");
        List<Student> list=new ArrayList<>();
        list.add(st);list.add(st2);list.add(st3);
        //添加一个
        //boolean b = studentService.save(st);
        //修改或者添加
        boolean b = studentService.saveOrUpdate(st);
        // boolean b = studentService.saveBatch(list,2);
        System.out.println("添加结果："+b);
    }
    @RequestMapping("/delete")
    public void deleteById(){
        boolean b = studentService.removeById("f98aad18-9be3-40e2-94c2-aea985a4b915");

        System.out.println("删除结果:"+b);
    }
}