package com.hutu.modules.admin.followup.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hutu.modules.common.entity.Solution;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 方案表
 * </p>
 *
 * @author generator
 * @since 2019-12-27
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("方案表")
public class SolutionFrom implements Serializable {

    private static final long serialVersionUID = 1L;

    private Solution solution;

	private List<SolutionNodeFrom> solutionNodeList;

}
