package com.jiuwang.buyer.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class NewsEntity implements Serializable {
    private ResultBean result;
    private String msgcode;
    private String msg;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getMsgcode() {
        return msgcode;
    }

    public void setMsgcode(String msgcode) {
        this.msgcode = msgcode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class ResultBean {
        private List<NewsEntityDatas> data;
        private ArrayList<NewsEntityDatas> carouselFigureList;
        private ArrayList<NewsEntityDatas> recommonDataList;
        private ArrayList<NewsEntityDatas> allDataList;
        private ArrayList<NewsEntityDatas> newsList;

        public ArrayList<NewsEntityDatas> getNewsList() {
            return newsList;
        }

        public void setNewsList(ArrayList<NewsEntityDatas> newsList) {
            this.newsList = newsList;
        }

        public List<NewsEntityDatas> getData() {
            return data;
        }

        public void setData(List<NewsEntityDatas> data) {
            this.data = data;
        }

        public ArrayList<NewsEntityDatas> getCarouselFigureList() {
            return carouselFigureList;
        }

        public void setCarouselFigureList(ArrayList<NewsEntityDatas> carouselFigureList) {
            this.carouselFigureList = carouselFigureList;
        }

        public ArrayList<NewsEntityDatas> getRecommonDataList() {
            return recommonDataList;
        }

        public void setRecommonDataList(ArrayList<NewsEntityDatas> recommonDataList) {
            this.recommonDataList = recommonDataList;
        }

        public ArrayList<NewsEntityDatas> getAllDataList() {
            return allDataList;
        }

        public void setAllDataList(ArrayList<NewsEntityDatas> allDataList) {
            this.allDataList = allDataList;
        }

        public static class NewsEntityDatas implements Serializable {
            private String infoType;
            private String ID;
            private String infono;
            private String title;
            private String author;
            private String content;
            private String picPath;
            private String inDBDate;
            private String readNum;
            private String praiseNum;
            private String htmlurl;
            private String isPraise;
            private String isRead;
            private String isStore;
            private String image;
            private String me_title_below;
            private String position;// 记录点击的位置
            private String isImportant;//1:是打勾重要的新闻 0:不是重要新闻
            private String recordNo;// 单条价格行情的ID
            private String htmlTitle;// 价格标题
            private String lastPublishTime;// 价格时间
            private String htmlPath;// 详细加载的html页面
            private String infoAddress;//招采链接地址
            private String city;
            private String cityID;
            private String htmlDate;// 价格时间
            private String priceType;// 价格的种类
            private String areaName;
            private String areaID;
            private ArrayList<NewsEntityChildDatas> detailCityList;

            public NewsEntityDatas() {

            }

            public String getInfoType() {
                return infoType;
            }

            public void setInfoType(String infoType) {
                this.infoType = infoType;
            }

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public String getInfono() {
                return infono;
            }

            public void setInfono(String infono) {
                this.infono = infono;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getPicPath() {
                return picPath;
            }

            public void setPicPath(String picPath) {
                this.picPath = picPath;
            }

            public String getInDBDate() {
                return inDBDate;
            }

            public void setInDBDate(String inDBDate) {
                this.inDBDate = inDBDate;
            }

            public String getReadNum() {
                return readNum;
            }

            public void setReadNum(String readNum) {
                this.readNum = readNum;
            }

            public String getPraiseNum() {
                return praiseNum;
            }

            public void setPraiseNum(String praiseNum) {
                this.praiseNum = praiseNum;
            }

            public String getHtmlurl() {
                return htmlurl;
            }

            public void setHtmlurl(String htmlurl) {
                this.htmlurl = htmlurl;
            }

            public String getIsPraise() {
                return isPraise;
            }

            public void setIsPraise(String isPraise) {
                this.isPraise = isPraise;
            }

            public String getIsRead() {
                return isRead;
            }

            public void setIsRead(String isRead) {
                this.isRead = isRead;
            }

            public String getIsStore() {
                return isStore;
            }

            public void setIsStore(String isStore) {
                this.isStore = isStore;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getMe_title_below() {
                return me_title_below;
            }

            public void setMe_title_below(String me_title_below) {
                this.me_title_below = me_title_below;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getIsImportant() {
                return isImportant;
            }

            public void setIsImportant(String isImportant) {
                this.isImportant = isImportant;
            }

            public String getRecordNo() {
                return recordNo;
            }

            public void setRecordNo(String recordNo) {
                this.recordNo = recordNo;
            }

            public String getHtmlTitle() {
                return htmlTitle;
            }

            public void setHtmlTitle(String htmlTitle) {
                this.htmlTitle = htmlTitle;
            }

            public String getLastPublishTime() {
                return lastPublishTime;
            }

            public void setLastPublishTime(String lastPublishTime) {
                this.lastPublishTime = lastPublishTime;
            }

            public String getHtmlPath() {
                return htmlPath;
            }

            public void setHtmlPath(String htmlPath) {
                this.htmlPath = htmlPath;
            }

            public String getInfoAddress() {
                return infoAddress;
            }

            public void setInfoAddress(String infoAddress) {
                this.infoAddress = infoAddress;
            }

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

            public String getHtmlDate() {
                return htmlDate;
            }

            public void setHtmlDate(String htmlDate) {
                this.htmlDate = htmlDate;
            }

            public String getPriceType() {
                return priceType;
            }

            public void setPriceType(String priceType) {
                this.priceType = priceType;
            }

            public String getAreaName() {
                return areaName;
            }

            public void setAreaName(String areaName) {
                this.areaName = areaName;
            }

            public String getAreaID() {
                return areaID;
            }

            public void setAreaID(String areaID) {
                this.areaID = areaID;
            }

            public ArrayList<NewsEntityChildDatas> getDetailCityList() {
                return detailCityList;
            }

            public void setDetailCityList(ArrayList<NewsEntityChildDatas> detailCityList) {
                this.detailCityList = detailCityList;
            }

            public class NewsEntityChildDatas implements Serializable {
                private String city;
                private String cityID;
                private String isfollow;

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

                public String getIsfollow() {
                    return isfollow;
                }

                public void setIsfollow(String isfollow) {
                    this.isfollow = isfollow;
                }
            }
        }
    }
}
