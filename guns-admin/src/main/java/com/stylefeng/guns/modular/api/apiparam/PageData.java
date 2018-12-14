package com.stylefeng.guns.modular.api.apiparam;



import java.util.List;

/**
 * Created by admin on 2018/4/4.
 */
public class PageData<T> {
    /**
     * 分页页码
     */
    private String pageNo;
    /**
     * 分页大小
     */
    private String pageSize;
    /**
     * 排序字段
     */
    private String sortField;
    /**
     * 排序方式
     */
    private String sortWay;

    /**
     * 返回参数
     */
    private List<T> pageConent;
    /**
     * 数据总条数
     */
    private int countNum;



    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }


    public List<T> getPageConent() {
        return pageConent;
    }

    public void setPageConent(List<T> pageConent) {
        this.pageConent = pageConent;
    }

    public int getCountNum() {
        return countNum;
    }

    public void setCountNum(int countNum) {
        this.countNum = countNum;
    }


    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortWay() {
        return sortWay;
    }

    public void setSortWay(String sortWay) {
        this.sortWay = sortWay;
    }


}