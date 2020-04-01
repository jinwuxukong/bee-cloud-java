package com.hutu.modules.admin.followup.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
@Data
public class ResultsVo implements Serializable {
    Integer templateId;
    ArrayList<KeyValueVo> resultList = new ArrayList();
}
