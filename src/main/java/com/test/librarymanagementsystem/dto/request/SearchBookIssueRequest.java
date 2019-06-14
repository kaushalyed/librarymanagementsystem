package com.test.librarymanagementsystem.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Map;

public class SearchBookIssueRequest {

    @Positive
    @NotNull
    private Integer pageSize = 20;

    @Positive
    @NotNull
    private Integer pageNo = 1;

    private List<String> sortFields;

    private String sortOrder;

    private Map<String,String> searchFieldMap;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public List<String> getSortFields() {
        return sortFields;
    }

    public void setSortFields(List<String> sortFields) {
        this.sortFields = sortFields;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Map<String, String> getSearchFieldMap() {
        return searchFieldMap;
    }

    public void setSearchFieldMap(Map<String, String> searchFieldMap) {
        this.searchFieldMap = searchFieldMap;
    }
}
