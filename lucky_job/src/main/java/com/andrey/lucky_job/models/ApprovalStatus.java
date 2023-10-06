package com.andrey.lucky_job.models;

import jakarta.persistence.*;

@Entity
@Table(name = "approval_status")
public class ApprovalStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "searcher_id")
    private Long searcherId;
    @Column(name = "employer_id")
    private Long employerId;
    @Column(name = "approved")
    private boolean approved;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getSearcherId() {
        return searcherId;
    }
    public void setSearcherId(Long searcherId) {
        this.searcherId = searcherId;
    }
    public Long getEmployerId() {
        return employerId;
    }
    public void setEmployerId(Long employerId) {
        this.employerId = employerId;
    }
    public boolean isApproved() {
        return approved;
    }
    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public ApprovalStatus() {
    }

    public ApprovalStatus(Long searcherId, Long employerId, boolean approved) {
        this.searcherId = searcherId;
        this.employerId = employerId;
        this.approved = approved;
    }
}