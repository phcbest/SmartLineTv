package com.phc.smartlinetv.bean;

/**
 * 版权：没有版权 看得上就用
 *
 * @author peng
 * 创建日期：2021/2/17 20
 * 描述：
 */
public class SearchBean {

    /**
     * url : http://www.feijisu5.com/tv/40150/
     * thumb : https://inews.gtimg.com/newsapp_ls/0/11933534843/0
     * title : 如懿传之甄嬛传续集
     * time : 2018
     * catid : 2
     * star : 周迅 霍建华 张钧甯 董洁 辛芷蕾 童瑶 李纯 邬君梅 胡可
     * lianzaijs : 87
     * beizhu :
     * alias_full :
     * area : 国产
     * sort :
     */

    private String url;
    private String thumb;
    private String title;
    private String time;
    private String catid;
    private String star;
    private String lianzaijs;
    private String beizhu;
    private String alias_full;
    private String area;
    private String sort;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getLianzaijs() {
        return lianzaijs;
    }

    public void setLianzaijs(String lianzaijs) {
        this.lianzaijs = lianzaijs;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getAlias_full() {
        return alias_full;
    }

    public void setAlias_full(String alias_full) {
        this.alias_full = alias_full;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSort() {
        return sort;
    }

    @Override
    public String toString() {
        return "SearchBean{" +
                "url='" + url + '\'' +
                ", thumb='" + thumb + '\'' +
                ", title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", catid='" + catid + '\'' +
                ", star='" + star + '\'' +
                ", lianzaijs='" + lianzaijs + '\'' +
                ", beizhu='" + beizhu + '\'' +
                ", alias_full='" + alias_full + '\'' +
                ", area='" + area + '\'' +
                ", sort='" + sort + '\'' +
                '}';
    }

    public void setSort(String sort) {

        this.sort = sort;
    }
}

