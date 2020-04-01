package com.hutu.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 表单填写记录表
 * </p>
 *
 * @author generator
 * @since 2019-07-11
 */
@Data
@Accessors(chain = true)
@TableName("t_form_survey")
public class Survey implements Serializable {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 表单json格式结果
     */
	private String resultJson;
	/**
	 * 表单模板json格式
	 */
	private String templateJson;
    /**
     * 所属表单模板id
     */
	private Integer templateId;
	/**
	 * 随访计划节点子表id
	 */
	private Integer nodeItemId;
    /**
     * 创建人名称
     */
	@TableField(fill = FieldFill.INSERT)
	private String createName;
    /**
     * 创建人ID
     */
	@TableField(fill = FieldFill.INSERT)
	private Integer createId;
    /**
     * 创建时间
     */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;
    /**
     * 更新人名称
     */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateName;
    /**
     * 更新人ID
     */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Integer updateId;
    /**
     * 更新时间
     */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;
    /**
     * 逻辑删除标记(已删除：1，未删除：0)
     */
    @TableLogic
	private Integer isDeleted;

	public Survey() {
	}

	public Survey(String resultJson, String templateJson, Integer templateId) {
		this.resultJson = resultJson;
		this.templateId = templateId;
		this.templateJson = templateJson;
	}
}
