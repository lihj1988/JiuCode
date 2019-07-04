package com.jiuwang.buyer.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/10/23.
 */

public class FreeCityBean {

    /**
     * msg : 查询成功
     * msgcode : 1
     * result : {"userType":"f","allDataList":[{"city":"太原","cityID":"A0401"},{"city":"临汾","cityID":"A0407"},{"city":"运城","cityID":"A0411"},{"city":"西安","cityID":"D0102"},{"city":"成都","cityID":"E0103"},{"city":"重庆","cityID":"E0301"},{"city":"郑州","cityID":"F0501"}]}
     */

    private String msg;
    private String msgcode;//1 查询成功
    private ResultBean result;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgcode() {
        return msgcode;
    }

    public void setMsgcode(String msgcode) {
        this.msgcode = msgcode;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * userType : f
         * allDataList : [{"city":"太原","cityID":"A0401"},{"city":"临汾","cityID":"A0407"},{"city":"运城","cityID":"A0411"},{"city":"西安","cityID":"D0102"},{"city":"成都","cityID":"E0103"},{"city":"重庆","cityID":"E0301"},{"city":"郑州","cityID":"F0501"}]
         */

        private String userType;//用户类型   f 不是会员  t 是会员
        private List<AllDataListBean> allDataList;//免费城市列表

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public List<AllDataListBean> getAllDataList() {
            return allDataList;
        }

        public void setAllDataList(List<AllDataListBean> allDataList) {
            this.allDataList = allDataList;
        }

        public static class AllDataListBean {
            /**
             * city : 太原
             * cityID : A0401
             */

            private String city;
            private String cityID;

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCityID() {
                return cityID;
            }

            public void setCityID(String cityID) {
                this.cityID = cityID;
            }
        }
    }
}
