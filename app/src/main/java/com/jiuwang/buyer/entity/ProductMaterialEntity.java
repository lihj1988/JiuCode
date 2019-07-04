package com.jiuwang.buyer.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/8/3.
 */

public class ProductMaterialEntity {
    private List<ResourceListBean> resourceList;

    public void setResourceList(List<ResourceListBean> resourceList) {
        this.resourceList = resourceList;
    }

    public List<ResourceListBean> getResourceList() {
        return resourceList;
    }

    public  class ResourceListBean{
        private String listing_cd;//已挂资源id
        private String resource_cd;//资源Id
        private String item_cd;//品名Id
        private String item_price;//挂牌价
        private String item_quantity;//件数
        private String item_left_weight;//剩余重量
        private String item_left_quantity;//剩余件数
        private String item_total_weight;//重量
        private String item_name;//品名
        private String warehouse_no;//仓库id
        private String warehouse_name;//仓库名称
        private String storage_date;
        private String item_unit;//单位
        private String item_metering;//计量方式
        private String item_model;//规格
        private String item_texture;//材质
        private String item_producing_area;//产地
        private String item_separable;//是否可拆分 y 是
        private String record_user_cd;//操作人id
        private String item_weight;//件重
        private String recommend_flag;//是否为推荐资源，0不是推荐资源，1是推荐资源
        private String record_date;//操作时间
        private String app_cd;//资源所属平台id
        private String app_company;//资源所属平台
        private String resource_origin;//资源来自哪个渠道，1是普通资源，2是仓库资源
        private String resource_code;//资源唯一id
        private String category_cd;//品种id
        private String sale_price;//销售价
        private String item_length;
        private String product_code;//产品编码
        private String order_top;
        private String two_transport;
        private String oriented;
        private String r;

        public void setItem_left_weight(String item_left_weight) {
            this.item_left_weight = item_left_weight;
        }

        public void setItem_left_quantity(String item_left_quantity) {
            this.item_left_quantity = item_left_quantity;
        }

        public String getItem_left_weight() {
            return item_left_weight;
        }

        public String getItem_left_quantity() {
            return item_left_quantity;
        }

        public void setListing_cd(String listing_cd) {
            this.listing_cd = listing_cd;
        }

        public void setResource_cd(String resource_cd) {
            this.resource_cd = resource_cd;
        }

        public void setItem_cd(String item_cd) {
            this.item_cd = item_cd;
        }

        public void setItem_price(String item_price) {
            this.item_price = item_price;
        }

        public void setItem_quantity(String item_quantity) {
            this.item_quantity = item_quantity;
        }

        public void setItem_total_weight(String item_total_weight) {
            this.item_total_weight = item_total_weight;
        }

        public void setItem_name(String item_name) {
            this.item_name = item_name;
        }

        public void setWarehouse_no(String warehouse_no) {
            this.warehouse_no = warehouse_no;
        }

        public void setWarehouse_name(String warehouse_name) {
            this.warehouse_name = warehouse_name;
        }

        public void setStorage_date(String storage_date) {
            this.storage_date = storage_date;
        }

        public void setItem_unit(String item_unit) {
            this.item_unit = item_unit;
        }

        public void setItem_metering(String item_metering) {
            this.item_metering = item_metering;
        }

        public void setItem_model(String item_model) {
            this.item_model = item_model;
        }

        public void setItem_texture(String item_texture) {
            this.item_texture = item_texture;
        }

        public void setItem_producing_area(String item_producing_area) {
            this.item_producing_area = item_producing_area;
        }

        public void setItem_separable(String item_separable) {
            this.item_separable = item_separable;
        }

        public void setRecord_user_cd(String record_user_cd) {
            this.record_user_cd = record_user_cd;
        }

        public void setItem_weight(String item_weight) {
            this.item_weight = item_weight;
        }

        public void setRecommend_flag(String recommend_flag) {
            this.recommend_flag = recommend_flag;
        }

        public void setRecord_date(String record_date) {
            this.record_date = record_date;
        }

        public void setApp_cd(String app_cd) {
            this.app_cd = app_cd;
        }

        public void setApp_company(String app_company) {
            this.app_company = app_company;
        }

        public void setResource_origin(String resource_origin) {
            this.resource_origin = resource_origin;
        }

        public void setResource_code(String resource_code) {
            this.resource_code = resource_code;
        }

        public void setCategory_cd(String category_cd) {
            this.category_cd = category_cd;
        }

        public void setSale_price(String sale_price) {
            this.sale_price = sale_price;
        }

        public void setItem_length(String item_length) {
            this.item_length = item_length;
        }

        public void setProduct_code(String product_code) {
            this.product_code = product_code;
        }

        public void setOrder_top(String order_top) {
            this.order_top = order_top;
        }

        public void setTwo_transport(String two_transport) {
            this.two_transport = two_transport;
        }

        public void setOriented(String oriented) {
            this.oriented = oriented;
        }

        public void setR(String r) {
            this.r = r;
        }

        public String getListing_cd() {
            return listing_cd;
        }

        public String getResource_cd() {
            return resource_cd;
        }

        public String getItem_cd() {
            return item_cd;
        }

        public String getItem_price() {
            return item_price;
        }

        public String getItem_quantity() {
            return item_quantity;
        }

        public String getItem_total_weight() {
            return item_total_weight;
        }

        public String getItem_name() {
            return item_name;
        }

        public String getWarehouse_no() {
            return warehouse_no;
        }

        public String getWarehouse_name() {
            return warehouse_name;
        }

        public String getStorage_date() {
            return storage_date;
        }

        public String getItem_unit() {
            return item_unit;
        }

        public String getItem_metering() {
            return item_metering;
        }

        public String getItem_model() {
            return item_model;
        }

        public String getItem_texture() {
            return item_texture;
        }

        public String getItem_producing_area() {
            return item_producing_area;
        }

        public String getItem_separable() {
            return item_separable;
        }

        public String getRecord_user_cd() {
            return record_user_cd;
        }

        public String getItem_weight() {
            return item_weight;
        }

        public String getRecommend_flag() {
            return recommend_flag;
        }

        public String getRecord_date() {
            return record_date;
        }

        public String getApp_cd() {
            return app_cd;
        }

        public String getApp_company() {
            return app_company;
        }

        public String getResource_origin() {
            return resource_origin;
        }

        public String getResource_code() {
            return resource_code;
        }

        public String getCategory_cd() {
            return category_cd;
        }

        public String getSale_price() {
            return sale_price;
        }

        public String getItem_length() {
            return item_length;
        }

        public String getProduct_code() {
            return product_code;
        }

        public String getOrder_top() {
            return order_top;
        }

        public String getTwo_transport() {
            return two_transport;
        }

        public String getOriented() {
            return oriented;
        }

        public String getR() {
            return r;
        }
    }
}
