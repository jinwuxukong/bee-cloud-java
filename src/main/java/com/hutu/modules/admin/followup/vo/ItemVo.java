package com.hutu.modules.admin.followup.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class ItemVo implements Serializable {
    String value;
    String label;

    public ItemVo(String value, String label) {
        this.value = value;
        this.label = label;
    }
    public ItemVo() {

    }

}
