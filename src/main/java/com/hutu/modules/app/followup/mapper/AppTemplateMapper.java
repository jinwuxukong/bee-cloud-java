package com.hutu.modules.app.followup.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.modules.common.entity.Template;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 表单模板表 Mapper 接口
 * </p>
 *
 * @author generator
 * @since 2019-07-09
 */
@Mapper
public interface AppTemplateMapper extends BaseMapper<Template> {

    IPage<Template> getPage(Page page, @Param("name") String name, @Param("type") String type, @Param("formType") String formType, @Param("userId") Integer userId);

    List<Template> getList(@Param("date")Template date);
}
