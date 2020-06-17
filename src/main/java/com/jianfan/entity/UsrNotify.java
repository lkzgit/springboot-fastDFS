package com.jianfan.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lkz
 * @since 2020-06-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="UsrNotify对象", description="")
public class UsrNotify extends Model<UsrNotify> {

    private static final long serialVersionUID = 1L;

    private String id;

    private String modeid;

    @TableField("delayH")
    private Integer delayH;

    @TableField("notifyMethod")
    private String notifyMethod;

    @TableField("notifyUser")
    private String notifyUser;

    private String creater;

    private String createtime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
