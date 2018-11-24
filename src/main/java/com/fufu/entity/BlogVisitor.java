package com.fufu.entity;

import java.util.Date;

public class BlogVisitor {
    private Long id;

    private String visitorName;

    private String visitorGender;

    private String visitorReason;

    private Date visitTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName == null ? null : visitorName.trim();
    }

    public String getVisitorGender() {
        return visitorGender;
    }

    public void setVisitorGender(String visitorGender) {
        this.visitorGender = visitorGender == null ? null : visitorGender.trim();
    }

    public String getVisitorReason() {
        return visitorReason;
    }

    public void setVisitorReason(String visitorReason) {
        this.visitorReason = visitorReason == null ? null : visitorReason.trim();
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }
}