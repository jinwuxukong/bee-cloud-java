package com.hutu.modules.admin.followup.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hutu.modules.common.entity.Template;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 表单模板表 Mapper 接口
 * </p>
 *
 * @author generator
 * @since 2019-07-09
 */
@Mapper
public interface AdminTemplateMapper extends BaseMapper<Template> {

    List<Template> getList(@Param("date")Template date);

    IPage<Template> getPage(Page objectPage,@Param("params") Map<String, Object> params);

    List<Template> getVersionList(@Param("id")Integer id,@Param("templateNumber") String templateNumber);

    Float getVersion(@Param("templateNumber")String templateNumber);
}
