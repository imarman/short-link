package com.arman.shortlink.admin.dao.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Arman
 */
@Data
public class BaseDo {

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /**
     * 删除表是(0:未删除，1:已删除)
     */
    @TableField(value = "del_flag", fill = FieldFill.INSERT)
    @TableLogic
    private Boolean delFlag;
}
