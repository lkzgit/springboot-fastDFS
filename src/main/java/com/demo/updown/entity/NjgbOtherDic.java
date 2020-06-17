package com.demo.updown.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 字典
 * </p>
 *
 * @author wususu
 * @since 2020-05-21
 */
@Data
public class NjgbOtherDic implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 类别关键字
     */
    @TableField("ID")
    private String id;
    /**
     * 类别关键字
     */
    @TableField("TYPENAME")
    private String typename;

    /**
     * 名称
     */
    @TableField("DICNAME")
    private String dicname;

    /**
     * 父节点
     */
    @TableField("PARENTID")
    private String parentid;

    /**
     * 排序
     */
    @TableField("ORDERKEY")
    private Integer orderkey;

    /**
     * 创建人
     */
    @TableField("CREATER")
    private String creater;

    /**
     * 创建时间
     */
    @TableField("CREATETIME")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createtime;

    // 非数据库字段
    @TableField(exist = false)
    private String parentName;


    // 非数据库字段
    @TableField(exist = false)
    private List<NjgbOtherDic> children;


    public static final String ID = "ID";

    public static final String TYPENAME = "TYPENAME";

    public static final String NAME = "NAME";

    public static final String PARENTID = "PARENTID";

    public static final String ORDERKEY = "ORDERKEY";

    public static final String CREATER = "CREATER";

    public static final String CREATETIME = "CREATETIME";

}
