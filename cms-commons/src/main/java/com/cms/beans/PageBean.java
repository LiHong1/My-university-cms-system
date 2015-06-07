package com.cms.beans;

import java.util.List;

public class PageBean {
    private static int PAGES=10;//每页的可显示页的记录数
    // 指定的或是页面参数
    private int currentPage; // 当前页
    private int pageSize; // 每页显示多少条

    // 查询数据库
    private long totalCount; // 总记录数
    private List<Object> datas; // 本页的数据列表
    
    // 计算
    private int pageCount; // 总页数
    private int beginPageIndex; // 页码列表的开始索引（包含）
    private int endPageIndex; // 页码列表的结束索引（包含）

    /**
     * 只接受前4个必要的属性，会自动的计算出其他3个属生的值
     * 
     * @param currentPage
     * @param pageSize
     */
    public PageBean(int currentPage, int pageSize, long totalCount, List<Object> datas) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.datas = datas;

        // 计算总页码
        pageCount = (int) ((totalCount + pageSize - 1) / pageSize);

        // 计算 beginPageIndex 和 endPageIndex
        // >> 总页数不多于10页，则全部显示
        if (pageCount <= PAGES) {
            beginPageIndex = 1;
            endPageIndex = pageCount;
        }
        // >> 总页数多于10页，则显示当前页附近的共10个页码
        else {
            // 当前页附近的共10个页码（前4个 + 当前页 + 后5个）
            beginPageIndex = currentPage - (PAGES-1)/2;
            endPageIndex = currentPage + PAGES/2;
            // 当前面的页码不足4个时，则显示前10个页码
            if (beginPageIndex < 1) {
                beginPageIndex = 1;
                endPageIndex = PAGES;
            }
            // 当后面的页码不足5个时，则显示后10个页码
            if (endPageIndex > pageCount) {
                endPageIndex = pageCount;
                beginPageIndex = pageCount - PAGES + 1;
            }
        }
    }
    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public long getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
    public List<Object> getDatas() {
        return datas;
    }
    public void setDatasList(List<Object> datas) {
        this.datas = datas;
    }
    public int getPageCount() {
        return pageCount;
    }
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
    public int getBeginPageIndex() {
        return beginPageIndex;
    }
    public void setBeginPageIndex(int beginPageIndex) {
        this.beginPageIndex = beginPageIndex;
    }
    public int getEndPageIndex() {
        return endPageIndex;
    }
    public void setEndPageIndex(int endPageIndex) {
        this.endPageIndex = endPageIndex;
    }
 
}
