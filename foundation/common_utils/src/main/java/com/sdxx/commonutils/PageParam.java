package com.sdxx.commonutils;

public class PageParam {
    /**
     * 每页显示数
     */
    private Integer rows;

    /**
     * 页数(从1开始)
     */
    private Integer page;

    public int getRows() {
        if(rows == null){
            rows =  20;
        }
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getPage() {
        if(page == null){
            page = 1;
        }
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
