package com.demo.updown.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;


import java.io.Serializable;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author root
 * @since 2020-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Student extends Model<Student> {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private String sid;


    private String Sname;


    private String Ssex;

    public Student(String sid, String sname, String ssex) {
        this.sid = sid;
        Sname = sname;
        Ssex = ssex;
    }

    public Student() {
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
