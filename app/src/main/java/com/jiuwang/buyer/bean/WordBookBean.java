package com.jiuwang.buyer.bean;

/**
 * Created by Administrator on 2019/2/22.
 * 字典接口 标准版
 */

public class WordBookBean {
    /**
     * dict_type : proceeds_type
     * dict_code : 0
     * dict_name : 出金
     * sort_order : 1
     */
    private String dict_type;//类型
    private String dict_code;//类型编码
    private String dict_name;//类型名称
    private String  sort_order;

    public WordBookBean(String dict_type, String dict_code, String dict_name, String sort_order) {
        this.dict_type = dict_type;
        this.dict_code = dict_code;
        this.dict_name = dict_name;
        this.sort_order = sort_order;
    }

    public String getDict_type() {
        return dict_type;
    }

    public void setDict_type(String dict_type) {
        this.dict_type = dict_type;
    }

    public String getDict_code() {
        return dict_code;
    }

    public void setDict_code(String dict_code) {
        this.dict_code = dict_code;
    }

    public String getDict_name() {
        return dict_name;
    }

    public void setDict_name(String dict_name) {
        this.dict_name = dict_name;
    }

    public String getSort_order() {
        return sort_order;
    }

    public void setSort_order(String sort_order) {
        this.sort_order = sort_order;
    }
}
