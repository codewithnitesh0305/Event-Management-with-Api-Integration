package com.springboot.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class ApiResponse<T> {
    private String status;
    private int statusCode;
    private T events;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalEvents;
    private Integer totalPages;

    public ApiResponse() {
    }

    public ApiResponse(String status, int statusCode, T events, Integer pageNumber, Integer pageSize, Long totalEvents, Integer totalPages) {
        this.status = status;
        this.statusCode = statusCode;
        this.events = events;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalEvents = totalEvents;
        this.totalPages = totalPages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public T getEvents() {
        return events;
    }

    public void setEvents(T events) {
        this.events = events;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalEvents() {
        return totalEvents;
    }

    public void setTotalEvents(Long totalEvents) {
        this.totalEvents = totalEvents;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
